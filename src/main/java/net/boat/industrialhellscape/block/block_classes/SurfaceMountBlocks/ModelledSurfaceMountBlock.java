package net.boat.industrialhellscape.block.block_classes.SurfaceMountBlocks;

import com.mojang.serialization.MapCodec;
import net.boat.industrialhellscape.block.block_interfaces.HitboxRotationInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/*
INFO:
-----
This block can be placed on all surfaces. It can be waterlogged.

*/

public class ModelledSurfaceMountBlock extends FaceAttachedHorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected final VoxelShape SHAPE_NORTH;
    protected final VoxelShape SHAPE_SOUTH;
    protected final VoxelShape SHAPE_EAST;
    protected final VoxelShape SHAPE_WEST;

    protected final VoxelShape SHAPE_FLOOR_NORTH;
    protected final VoxelShape SHAPE_FLOOR_SOUTH;
    protected final VoxelShape SHAPE_FLOOR_EAST;
    protected final VoxelShape SHAPE_FLOOR_WEST;

    protected final VoxelShape SHAPE_CEILING_NORTH;
    protected final VoxelShape SHAPE_CEILING_SOUTH;
    protected final VoxelShape SHAPE_CEILING_EAST;
    protected final VoxelShape SHAPE_CEILING_WEST;



    public ModelledSurfaceMountBlock(Properties pProperties, VoxelShape floorHitBox) {
        super(pProperties);

        this.SHAPE_FLOOR_NORTH = floorHitBox;
        this.SHAPE_FLOOR_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, SHAPE_FLOOR_NORTH);
        this.SHAPE_FLOOR_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, SHAPE_FLOOR_NORTH);
        this.SHAPE_FLOOR_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, SHAPE_FLOOR_NORTH);

        this.SHAPE_NORTH = HitboxRotationInterface.rotateVoxelXAxisIntTimes(1, floorHitBox); //Rotates to the north surface position
        this.SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, SHAPE_NORTH);
        this.SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, SHAPE_NORTH);
        this.SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, SHAPE_NORTH);

        this.SHAPE_CEILING_NORTH = HitboxRotationInterface.rotateVoxelXAxisIntTimes(2, floorHitBox);
        this.SHAPE_CEILING_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, SHAPE_CEILING_NORTH);
        this.SHAPE_CEILING_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, SHAPE_CEILING_NORTH);
        this.SHAPE_CEILING_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, SHAPE_CEILING_NORTH);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(FACE, AttachFace.WALL)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return true;
    }

    @Override
    public @Nonnull RenderShape getRenderShape(@Nonnull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends FaceAttachedHorizontalDirectionalBlock> codec() {
        return null;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction facing = pContext.getHorizontalDirection().getOpposite();
        Direction clickedFaceDirection = pContext.getClickedFace();

        BlockPos blockPos = pContext.getClickedPos();
        BlockState state; //Start with default blockstate

        if (clickedFaceDirection.getAxis() == Direction.Axis.Y) { //On Floor or Ceiling
            state = this.defaultBlockState()
                    .setValue(FACE, clickedFaceDirection == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING).setValue(FACING, facing);
        } else { //On walls
            state = this.defaultBlockState()
                    .setValue(FACE, AttachFace.WALL).setValue(FACING, clickedFaceDirection);
        }

        state = state.setValue(WATERLOGGED,fluidstate.getType() == Fluids.WATER);

        return state;
    }

    @Override
    public @Nonnull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {

        //When placed, if the block's "TYPE" property is one of these cases, find its horizontal orientation and give it the correct hitbox
        //North is the default orientation assumed if no other cases met

        if(pState.getValue(FACE) == AttachFace.WALL) {
            return switch (pState.getValue(FACING)) {
                case SOUTH -> SHAPE_SOUTH;
                case EAST -> SHAPE_EAST;
                case WEST -> SHAPE_WEST;
                default -> SHAPE_NORTH;
            };
        } else if(pState.getValue(FACE) == AttachFace.FLOOR) {
            return switch (pState.getValue(FACING)) {
                case SOUTH -> SHAPE_FLOOR_SOUTH;
                case EAST -> SHAPE_FLOOR_EAST;
                case WEST -> SHAPE_FLOOR_WEST;
                default -> SHAPE_FLOOR_NORTH;
            };
        } else {
            return switch (pState.getValue(FACING)) {
                case SOUTH -> SHAPE_CEILING_SOUTH;
                case EAST -> SHAPE_CEILING_EAST;
                case WEST -> SHAPE_CEILING_WEST;
                default -> SHAPE_CEILING_NORTH;
            };
        }
    }

    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, FACE); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}


