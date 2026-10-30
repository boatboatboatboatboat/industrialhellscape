package net.boat.industrialhellscape.block.block_experiments.CorpseBlock;

import net.boat.industrialhellscape.block.block_interfaces.HitboxRotationInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class HangeableCorpseBlock extends LanternBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private final VoxelShape SOLO_SHAPE_NORTH;
    private final VoxelShape SOLO_SHAPE_SOUTH;
    private final VoxelShape SOLO_SHAPE_EAST;
    private final VoxelShape SOLO_SHAPE_WEST;

    public HangeableCorpseBlock(Properties properties, VoxelShape soloShape) {
        super(properties);

        SOLO_SHAPE_NORTH = soloShape;
        SOLO_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, soloShape);
        SOLO_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, soloShape);
        SOLO_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, soloShape);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HANGING, false)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public @Nonnull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        //North is the default orientation assumed if no other cases met

        return switch (pState.getValue(FACING)) {
            case SOUTH -> SOLO_SHAPE_SOUTH;
            case EAST -> SOLO_SHAPE_EAST;
            case WEST -> SOLO_SHAPE_WEST;
            default -> SOLO_SHAPE_NORTH;
        };
    }

//    @Override
//    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
//        su
//        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
//        Direction facing = pContext.getHorizontalDirection().getOpposite(); //Gets the cardinal direction when player places new block
//
//        BlockState state = defaultBlockState().setValue(WATERLOGGED,fluidstate.getType() == Fluids.WATER); //check for waterlogging status
//        state = state.setValue(FACING, facing); //Defines facing direction of the block
//        return state;
//    }


    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        //Super defined waterlogging and hanging behavior
        Direction facing = context.getHorizontalDirection().getOpposite();
        return super.getStateForPlacement(context).setValue(FACING, facing);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, HANGING); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}
