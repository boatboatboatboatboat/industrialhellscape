package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.Integer2Blocks;

import net.boat.industrialhellscape.block.modded_interfaces.MultiBlockPlacementInterface;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        //Code taken from vanilla BedBlock code with minor modifications
        if (level.isClientSide) {

//            level.addParticle(ParticleTypes.HEART, pos.getX(), pos.getY()+0.5, pos.getZ(), 0, 2, 0);

            return InteractionResult.CONSUME;
        } else {
            //If the block interacted with is not the POSITIVE block
            //Redefine pos as that of the POSITIVE block by finding the pos behind the current block
            //If the block at that position is an instance of the same block
            //CONSUME
            if (state.getValue(PART) == 1) {
                pos = pos.relative(state.getValue(HorizontalDirectionalBlock.FACING));
                state = level.getBlockState(pos);
                if (!state.is(this)) {
                    return InteractionResult.CONSUME;
                }
            }
            //Explodes if you are disallowed to set spawn in certain dimensions
            if (!canSetSpawn(level)) {
                level.removeBlock(pos, false);
                BlockPos blockpos = pos.relative(state.getValue(HorizontalDirectionalBlock.FACING).getOpposite());
                if (level.getBlockState(blockpos).is(this)) {
                    level.removeBlock(blockpos, false);
                }
                Vec3 vec3 = pos.getCenter();
                level.explode(null, level.damageSources().badRespawnPointExplosion(vec3), null, vec3, 5.0F, true, Level.ExplosionInteraction.BLOCK);
            } else if (state.getValue(BlockStateProperties.OCCUPIED) && !multiplePeopleCanSleepOn) {
                //forbid sleeping if occupied
                player.displayClientMessage(Component.translatable("block.minecraft.bed.occupied"), true);
                return InteractionResult.SUCCESS;
            } else {
                //If no other conditions met, Initiates sleeping unless nighttime or thunderstorm, I think
                player.startSleepInBed(pos).ifLeft((p_49477_) -> {
                    if (p_49477_.getMessage() != null) {
                        player.displayClientMessage(p_49477_.getMessage(), true);
                    }

                });
            }
            if(player.isSleeping()) {
                level.playSound(null, pos, ModSounds.SNORE.get(), SoundSource.BLOCKS, 1f, 1f);
            }
            return InteractionResult.SUCCESS;
        }
    }

    public static boolean canSetSpawn(Level level) {
        return level.dimensionType().bedWorks();
    }

    protected boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType pathComputationType) {
        return false;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HorizontalDirectionalBlock.FACING, PART, BlockStateProperties.OCCUPIED, BlockStateProperties.WATERLOGGED);
    }
}
