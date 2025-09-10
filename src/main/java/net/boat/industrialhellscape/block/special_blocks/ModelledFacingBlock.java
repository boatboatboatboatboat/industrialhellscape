package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.RotationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

//INFO:
//-----
// This block supports rotation of custom models. It also supports directional placement based on player.
// It supports waterlogging via the vanilla interface, "SimpleWaterloggedBlock"
// Custom models have their VoxelShape hitboxes chosen during registration by the HitboxGeometryCollection library.
// The custom voxel shapes will be rotated by the mod's interface class, Rotationhelper along the appropriate cardinal directions.
//-----

public class ModelledFacingBlock extends SimpleFacingBlock implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private VoxelShape SOLO_SHAPE_NORTH;
    private VoxelShape SOLO_SHAPE_SOUTH;
    private VoxelShape SOLO_SHAPE_EAST;
    private VoxelShape SOLO_SHAPE_WEST;

    public ModelledFacingBlock(Properties pProperties, VoxelShape soloShape) {
        super(pProperties);

        SOLO_SHAPE_NORTH = soloShape;
        SOLO_SHAPE_SOUTH = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, soloShape);
        SOLO_SHAPE_EAST = RotationHelper.rotateVoxelHorizontal(Direction.EAST, soloShape);
        SOLO_SHAPE_WEST = RotationHelper.rotateVoxelHorizontal(Direction.WEST, soloShape);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {

        //When placed, if the block's "TYPE" property is one of these cases, find its horizontal orientation and give it the correct hitbox
        //North is the default orientation assumed if no other cases met

        switch (pState.getValue(FACING)) {
            case SOUTH: return SOLO_SHAPE_SOUTH;
            case EAST: return SOLO_SHAPE_EAST;
            case WEST: return SOLO_SHAPE_WEST;
            default: return SOLO_SHAPE_NORTH;
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction directionClicked = pContext.getHorizontalDirection(); //Gets the cardinal direction when player places new block

        BlockState state = defaultBlockState().setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER)); //check for waterlogging status
        state = state.setValue(FACING, directionClicked); //Defines facing direction of the block
        return state;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED); //Block's blockstates; its NSEW orientation, its connection type defined
    }
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
