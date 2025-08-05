package net.boat.industrialhellscape.block.special_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
//INFO:
//-----
//This block supports rotation of custom models. It also supports directional placement based on player.
//It supports waterlogging.
//It supports interactions for light generation
//-----

public class LightableModelledFacingBlock extends ModelledFacingBlock {
    private DustParticleOptions particle;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public LightableModelledFacingBlock(Properties pProperties, VoxelShape soloShape) {
        super(pProperties, soloShape);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(WATERLOGGED, false));
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement (BlockPlaceContext ctx) {
        Direction directionClicked = ctx.getHorizontalDirection(); //Gets the cardinal direction when player places new block
        BlockState state = this.defaultBlockState().setValue(FACING, directionClicked); //First, defines facing direction of the block
        state = state.setValue(BlockStateProperties.FACING, ctx.getClickedFace());
        state = state.setValue(BlockStateProperties.LIT,
                ctx.getLevel().hasSignal(ctx.getClickedPos(), ctx.getClickedFace()));
        return state;
    }

    public static boolean shouldBeLit (BlockState state, Level level, BlockPos pos) {
        Direction attach = state.getValue(BlockStateProperties.FACING).getOpposite();
        return state.getValue(BlockStateProperties.INVERTED) ^ level.hasSignal(pos.relative(attach), attach);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighbor, BlockPos neighborPos, boolean bool) {
        Direction face = state.getValue(BlockStateProperties.FACING);
        if (pos.relative(face.getOpposite()).equals(neighborPos)) {
            BlockState next = toggle(state, level, pos);
        }
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        BlockState next = this.toggle(pState.cycle(BlockStateProperties.INVERTED), pLevel, pPos);
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            float pitch = next.getValue(BlockStateProperties.INVERTED) ? 0.6f : 0.5f;
            pState = pState.cycle(POWERED);
            pLevel.setBlock(pPos, pState, 2);
            pLevel.playSound((Player)null, pPos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3f, pitch);
            return InteractionResult.CONSUME;
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, POWERED); //Block's blockstates; its NSEW orientation, its connection type defined
    }

    private BlockState toggle (BlockState state, Level level, BlockPos pos) {
        if (level.isClientSide) {
            makeParticle(state, level, pos);
        }
        BlockState next = state.setValue(BlockStateProperties.LIT, shouldBeLit(state, level, pos));
        level.setBlock(pos, next, 3);
        return next;
    }

    private void makeParticle (BlockState state, LevelAccessor level, BlockPos pos) {
        Direction direction = state.getValue(BlockStateProperties.FACING);
        float x = pos.getX() + 0.5f + 0.1f * direction.getStepX();
        float y = pos.getY() + 0.5f + 0.1f * direction.getStepY();
        float z = pos.getZ() + 0.5f + 0.1f * direction.getStepZ();
        level.addParticle(particle, x, y, z, 0d, 0d, 0d);
    }
}


