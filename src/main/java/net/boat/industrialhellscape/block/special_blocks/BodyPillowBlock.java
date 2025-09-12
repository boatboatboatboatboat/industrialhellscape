package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.HitboxGeometryCollection;
import net.boat.industrialhellscape.block.special_blocks_properties.MultiBlockPlacementDirection;
import net.boat.industrialhellscape.block.special_blocks_properties.RotationHelper;
import net.boat.industrialhellscape.block.special_blocks_properties.TwoBlockMultiBlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class BodyPillowBlock extends TwoBlockMultiBlock implements EntityBlock {

    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;

    private final VoxelShape POSITIVE_SHAPE_NORTH;
    private final VoxelShape POSITIVE_SHAPE_SOUTH;
    private final VoxelShape POSITIVE_SHAPE_EAST;
    private final VoxelShape POSITIVE_SHAPE_WEST;

    private final VoxelShape NEGATIVE_SHAPE_NORTH;
    private final VoxelShape NEGATIVE_SHAPE_SOUTH;
    private final VoxelShape NEGATIVE_SHAPE_EAST;
    private final VoxelShape NEGATIVE_SHAPE_WEST;

    public BodyPillowBlock(Properties pProperties) {
        super(pProperties, MultiBlockPlacementDirection.FORWARD);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HALF_PART, TwoBlockMultiBlockState.NEGATIVE)
                .setValue(FACING,Direction.NORTH)
                .setValue(OCCUPIED, false)
        );

        POSITIVE_SHAPE_NORTH = HitboxGeometryCollection.PILLOW_POSITIVE();
        POSITIVE_SHAPE_SOUTH = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, POSITIVE_SHAPE_NORTH);
        POSITIVE_SHAPE_EAST = RotationHelper.rotateVoxelHorizontal(Direction.EAST, POSITIVE_SHAPE_NORTH);
        POSITIVE_SHAPE_WEST = RotationHelper.rotateVoxelHorizontal(Direction.WEST, POSITIVE_SHAPE_NORTH);

        NEGATIVE_SHAPE_NORTH = HitboxGeometryCollection.PILLOW_NEGATIVE();
        NEGATIVE_SHAPE_SOUTH = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, NEGATIVE_SHAPE_NORTH);
        NEGATIVE_SHAPE_EAST = RotationHelper.rotateVoxelHorizontal(Direction.EAST, NEGATIVE_SHAPE_NORTH);
        NEGATIVE_SHAPE_WEST = RotationHelper.rotateVoxelHorizontal(Direction.WEST, NEGATIVE_SHAPE_NORTH);
    }

    @Override
    public boolean isBed(BlockState state, BlockGetter level, BlockPos pos, @Nullable Entity sleeper) {
        return true;
    }

    public @Nonnull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {

        //When placed, if the block's "TYPE" property is one of these cases, find its horizontal orientation and give it the correct hitbox
        //North is the default orientation assumed if no other cases met
        if(pState.getValue(HALF_PART) == TwoBlockMultiBlockState.POSITIVE) {
            return switch (pState.getValue(FACING)) {
                case SOUTH -> POSITIVE_SHAPE_SOUTH;
                case EAST -> POSITIVE_SHAPE_EAST;
                case WEST -> POSITIVE_SHAPE_WEST;
                default -> POSITIVE_SHAPE_NORTH; //North case is the default case for brevity
            };

        } else {
            return switch (pState.getValue(FACING)) {
                case SOUTH -> NEGATIVE_SHAPE_SOUTH;
                case EAST -> NEGATIVE_SHAPE_EAST;
                case WEST -> NEGATIVE_SHAPE_WEST;
                default -> NEGATIVE_SHAPE_NORTH; //North case is the default case for brevity
            };
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@Nonnull BlockPos pPos, @Nonnull BlockState pState) {
        return new BedBlockEntity(pPos, pState, DyeColor.BLUE);
    }

    public @Nonnull InteractionResult use(@Nonnull BlockState pState, Level pLevel, @Nonnull BlockPos pPos, @Nonnull Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {

        if (pLevel.isClientSide) {
            return InteractionResult.CONSUME;
        } else {
            if (!canSetSpawn(pLevel)) {
                pLevel.removeBlock(pPos, false);
                BlockPos blockpos = pPos.relative(pState.getValue(FACING).getOpposite());
                if (pLevel.getBlockState(blockpos).is(this)) {
                    pLevel.removeBlock(blockpos, false);
                }

                Vec3 vec3 = pPos.getCenter();
                pLevel.explode(null, pLevel.damageSources().badRespawnPointExplosion(vec3), null, vec3, 5.0F, true, Level.ExplosionInteraction.BLOCK);
                return InteractionResult.SUCCESS;
            } else if (pState.getValue(OCCUPIED)) {
                if (!this.kickVillagerOutOfBed(pLevel, pPos)) {
                    pPlayer.displayClientMessage(Component.translatable("hud.industrialhellscape.pillow_occupied"), true);
                }

                return InteractionResult.SUCCESS;
            } else {
                pPlayer.startSleepInBed(pPos).ifLeft((p_49477_) -> {
                    if (p_49477_.getMessage() != null) {
                        pPlayer.displayClientMessage(Component.translatable("hud.industrialhellscape.pillow_sleep_text"), true);
                    }

                });
                return InteractionResult.SUCCESS;
            }
        }
    }

    public static boolean canSetSpawn(Level pLevel) {
        return pLevel.dimensionType().bedWorks();
    }

    private boolean kickVillagerOutOfBed(Level pLevel, BlockPos pPos) {
        List<Villager> list = pLevel.getEntitiesOfClass(Villager.class, new AABB(pPos), LivingEntity::isSleeping);
        if (list.isEmpty()) {
            return false;
        } else {
            list.get(0).stopSleeping();
            return true;
        }
    }

    public void fallOn(@Nonnull Level pLevel, @Nonnull BlockState pState, @Nonnull BlockPos pPos, @Nonnull Entity pEntity, float pFallDistance) {
        super.fallOn(pLevel, pState, pPos, pEntity, pFallDistance * 0.5F);
    }

    public void updateEntityAfterFallOn(@Nonnull BlockGetter pLevel, Entity pEntity) {
        if (pEntity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(pLevel, pEntity);
        } else {
            this.bounceUp(pEntity);
        }

    }

    private void bounceUp(Entity pEntity) {
        Vec3 vec3 = pEntity.getDeltaMovement();
        if (vec3.y < 0.0D) {
            double d0 = pEntity instanceof LivingEntity ? 1.0D : 0.8D;
            pEntity.setDeltaMovement(vec3.x, -vec3.y * (double)0.66F * d0, vec3.z);
        }

    }

    private static Direction getNeighbourDirection(TwoBlockMultiBlockState pPart, Direction pDirection) {
        return pPart == TwoBlockMultiBlockState.POSITIVE ? pDirection.getClockWise(): pDirection.getCounterClockWise();
    }

    public void playerWillDestroy(Level pLevel, @Nonnull BlockPos pPos, @Nonnull BlockState pState, @Nonnull Player pPlayer) {
        if (!pLevel.isClientSide && pPlayer.isCreative()) {
            TwoBlockMultiBlockState bedpart = pState.getValue(HALF_PART);
            if (bedpart == TwoBlockMultiBlockState.NEGATIVE) {
                BlockPos blockpos = pPos.relative(getNeighbourDirection(bedpart, pState.getValue(FACING)));
                BlockState blockstate = pLevel.getBlockState(blockpos);
                if (blockstate.is(this) && blockstate.getValue(HALF_PART) == TwoBlockMultiBlockState.POSITIVE) {
                    pLevel.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                    pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate));
                }
            }
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HALF_PART, OCCUPIED);
    }

    public long getSeed(BlockState pState, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(pState.getValue(FACING), pState.getValue(HALF_PART) == TwoBlockMultiBlockState.POSITIVE ? 0 : 1);
        return Mth.getSeed(blockpos.getX(), pPos.getY(), blockpos.getZ());
    }

    public boolean isPathfindable(@Nonnull BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull PathComputationType pType) {
        return false;
    }
}
