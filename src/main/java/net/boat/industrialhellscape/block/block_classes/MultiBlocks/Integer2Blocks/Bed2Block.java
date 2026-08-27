package net.boat.industrialhellscape.block.block_classes.MultiBlocks.Integer2Blocks;

import net.boat.industrialhellscape.block.block_interfaces.MultiBlockPlacementInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Bed2Block extends Modelled2Block {

    protected boolean multiplePeopleCanSleepOn;

    public Bed2Block(BlockBehaviour.Properties pProperties, int[][] multiBlockPlacementMatrix, VoxelShape[] hitboxShapeArray, boolean multiplePeopleCanSleepOn) {
        super(pProperties, multiBlockPlacementMatrix, hitboxShapeArray);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, 0)
                .setValue(HorizontalDirectionalBlock.FACING,Direction.NORTH)
                .setValue(BlockStateProperties.WATERLOGGED,false)
                .setValue(BlockStateProperties.OCCUPIED, false)
        );

        this.multiplePeopleCanSleepOn = multiplePeopleCanSleepOn;
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos originPos = pContext.getClickedPos();
        level.getBlockState(originPos);
        BlockState pState;
        Direction facing = pContext.getHorizontalDirection(); //Facing is NOT opposite to facilitate proper sleeping orientation
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        pState = this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED,fluidstate.getType() == Fluids.WATER); //set default Block State for this block, THEN assign waterlogged state (so it doesnt try to do that for air)

        return MultiBlockPlacementInterface.placeOriginBlock(level, originPos, pState, HorizontalDirectionalBlock.FACING, facing, multiBlockPlacementMatrix);
    }

    @Override
    public @NotNull Direction getBedDirection(@NotNull BlockState state, LevelReader level, @NotNull BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos);
        return blockstate.getBlock() instanceof Bed2Block ? blockstate.getValue(HorizontalDirectionalBlock.FACING) : Direction.NORTH;
    }

    @Override
    public boolean isBed(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull LivingEntity sleeper) {
        return true;
    }
    //Respawning wil not work without this NeoForge interface method (1.21.1 only)
    @Override
    public @NotNull Optional<ServerPlayer.RespawnPosAngle> getRespawnPosition(BlockState state, EntityType<?> type, LevelReader levelReader, BlockPos pos, float orientation) {
        Direction blockFacingDirection = state.getValue(FACING);
        BlockPos centerOfBedFoot = pos.relative(blockFacingDirection.getOpposite()); //foot of bed

        //initialize loop variables
        BlockPos candidatePos = centerOfBedFoot;
        Vec3 vec3 = DismountHelper.findSafeDismountLocation(type, levelReader, candidatePos, true);
        boolean candidateVec3NotNull = false; //avoid spawning on top of bed by default (loop will start checking at -1,-1,-1 offset. would eventually return here anyway.)

        for(int i = -1; i < 2; i++) {
            for(int j = -1; j < 2; j++) {
                for(int k = -1; k < 2; k++) {
                    if(candidateVec3NotNull) {
                        return Optional.of(new ServerPlayer.RespawnPosAngle(vec3,blockFacingDirection.getOpposite().toYRot()));
                    }
                    candidatePos = centerOfBedFoot.offset(i,j,k);
                    vec3 = DismountHelper.findSafeDismountLocation(type, levelReader, candidatePos, true);
                    candidateVec3NotNull = (vec3 != null);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.CONSUME;
        } else {
            if (state.getValue(PART) != 1) { //reassign pos and state as "bed head" position if needed
                pos = pos.relative(state.getValue(FACING));
                state = level.getBlockState(pos);
                if (!state.is(this)) {
                    return InteractionResult.CONSUME;
                }
            }

            if (!canSetSpawn(level)) { //removes bed before triggering explosion
                level.removeBlock(pos, false);
                BlockPos blockpos = pos.relative(state.getValue(FACING).getOpposite());
                if (level.getBlockState(blockpos).is(this)) {
                    level.removeBlock(blockpos, false);
                }

                Vec3 vec3 = pos.getCenter();
                level.explode(null, level.damageSources().badRespawnPointExplosion(vec3), (ExplosionDamageCalculator)null, vec3, 5.0F, true, Level.ExplosionInteraction.BLOCK);
                return InteractionResult.SUCCESS;
            } else if (state.getValue(BlockStateProperties.OCCUPIED)) {
                return InteractionResult.SUCCESS;
            } else {
                player.startSleepInBed(pos).ifLeft((p_49477_) -> {
                    if (p_49477_.getMessage() != null) {
                        player.displayClientMessage(p_49477_.getMessage(), true);
                    }
                });

                return InteractionResult.SUCCESS;
            }
        }
    }

    public static boolean canSetSpawn(Level level) {
        return level.dimensionType().bedWorks();
    }

    protected boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType pathComputationType) {
        return true;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HorizontalDirectionalBlock.FACING, PART, BlockStateProperties.OCCUPIED, BlockStateProperties.WATERLOGGED);
    }
}
