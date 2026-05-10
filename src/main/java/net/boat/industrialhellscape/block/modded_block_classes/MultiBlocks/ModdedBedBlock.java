package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ModdedBedBlock extends Modelled2BMBlock implements EntityBlock, SimpleWaterloggedBlock {

    public static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;

    public ModdedBedBlock(Properties pProperties, VoxelShape hitboxPositiveShape, VoxelShape hitboxNegativeShape) {
        // When registering this block, pass in:
        // Block properties
        // VoxelShape hitbox for positive (forward half)
        // VoxelShape hitbox for negative (backwards half, closest towards player when placed down)
        super(pProperties, MultiBlockPlacementDirection.FORWARD,hitboxPositiveShape,  hitboxNegativeShape);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HALF_PART, TwoBlockMultiBlockState.NEGATIVE)
                .setValue(FACING,Direction.NORTH)
                .setValue(OCCUPIED, false)
        );
    }

    @Override
    public Direction getBedDirection(BlockState state, LevelReader level, BlockPos pos) {
        //Ensures player faces the correct direction when sleeping
        BlockState blockstate = level.getBlockState(pos);
        return blockstate.getBlock() instanceof ModdedBedBlock ? blockstate.getValue(FACING) : Direction.NORTH;
    }

    public boolean isBed(BlockState state, BlockGetter level, BlockPos pos, @Nullable Entity sleeper) {
        return true;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        //Color is arbitrary to fulfill the last parameter
        return new BedBlockEntity(pos, state, DyeColor.BLUE);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        //Code taken from vanilla BedBlock code with minor modifications
        if (level.isClientSide) {
            return InteractionResult.CONSUME;
        } else {
            //If the block interacted with is not the POSITIVE block
            //Redefine pos as that of the POSITIVE block by finding the pos behind the current block
            //If the block at that position is an instance of the same block
            //CONSUME
            if (state.getValue(HALF_PART) != TwoBlockMultiBlockState.POSITIVE) {
                pos = pos.relative(state.getValue(FACING));
                state = level.getBlockState(pos);
                if (!state.is(this)) {
                    return InteractionResult.CONSUME;
                }
            }
            //Explodes if you are disallowed to set spawn in certain dimensions
            if (!canSetSpawn(level)) {
                level.removeBlock(pos, false);
                BlockPos blockpos = pos.relative(state.getValue(FACING).getOpposite());
                if (level.getBlockState(blockpos).is(this)) {
                    level.removeBlock(blockpos, false);
                }

                Vec3 vec3 = pos.getCenter();
                level.explode(null, level.damageSources().badRespawnPointExplosion(vec3), null, vec3, 5.0F, true, Level.ExplosionInteraction.BLOCK);
            } else {
                //Forbids sleeping unless nighttime or thunderstorm, I think
                player.startSleepInBed(pos).ifLeft((p_49477_) -> {
                    if (p_49477_.getMessage() != null) {
                        player.displayClientMessage(p_49477_.getMessage(), true);
                    }

                });
            }
            return InteractionResult.SUCCESS;
        }
    }

    public static boolean canSetSpawn(Level level) {
        return level.dimensionType().bedWorks();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HALF_PART, OCCUPIED, WATERLOGGED);
    }

    public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull PathComputationType pType) {
        return false;
    }
}