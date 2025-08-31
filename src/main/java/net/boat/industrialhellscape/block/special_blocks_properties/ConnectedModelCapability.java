package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

//INFO:
//-----
//This interface offloads similar code from other block classes that give the blocks connection ability when placed next to similar blocks
//This allows changing blockstates to custom models that allow blocks to seamlessly "connect", like tables of variable width.
//-----

public interface ConnectedModelCapability {
    DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING; //"FACING" is used to store DirectionProperty value of "north, south, east, west". For alignment with where player is facing.
    EnumProperty<Direction.Axis> PLANAR_AXIS = BlockStateProperties.AXIS;
    EnumProperty<Direction> SURFACE_DIRECTION = BlockStateProperties.FACING; //"SURFACE_DIRECTION" stores N,S,E,W, along with Up and Down. For alignment with surface clicked for placement.
    EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS; //"AXIS" is used to store which axis the block is aligned to
    EnumProperty<FurnitureConnectionState> TYPE = EnumProperty.create("type", FurnitureConnectionState.class);
    BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    //The following methods find the blockstate adjacent to the position clicked (this should correspond with a block being placed next to similar neighbors).
    //The blockstate of the neighboring block can also be used to identify what block that is too.

    //getStateRelativeLeft and Right are relative to the player
    default BlockState getStateRelativeLeft(Level level, BlockPos positionClicked, Direction directionClicked) {
        Direction relativeLeft = directionClicked.getCounterClockWise();
        BlockPos leftNeighborsPos = positionClicked.relative(relativeLeft);

        return level.getBlockState(leftNeighborsPos);
    }
    default BlockState getStateRelativeRight(Level level, BlockPos positionClicked, Direction directionClicked) {
        Direction relativeRight = directionClicked.getClockWise();
        BlockPos rightNeighborsPos = positionClicked.relative(relativeRight);

        return level.getBlockState(rightNeighborsPos);
    }

    //getStateRelativeForward and Backward are relative to the axes (X,Y,z). Note that it is not consistent with the above GRLeft and GRRight methods.
    default BlockState getStateAxisPositive(Level level, BlockPos pos, Direction.Axis horizontalAxis) {
        return level.getBlockState(pos.relative(Direction.fromAxisAndDirection(horizontalAxis, Direction.AxisDirection.POSITIVE)));
    }

    default BlockState getStateAxisNegative(Level level, BlockPos pos, Direction.Axis axis) {
        return level.getBlockState(pos.relative(Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE)));
    }

    //getStateRelativeTop checks the neighbor above and belows' blockstate.
    default BlockState getStateRelativeTop(Level level, BlockPos positionClicked, Direction directionClicked) {
        Direction relativeTop = directionClicked.getCounterClockWise();
        BlockPos leftNeighborsPos = positionClicked.above();

        return level.getBlockState(leftNeighborsPos);
    }

    default BlockState getStateRelativeBottom(Level level, BlockPos positionClicked, Direction directionClicked) {
        Direction relativeBottom = directionClicked.getClockWise();
        BlockPos rightNeighborsPos = positionClicked.below();

        return level.getBlockState(rightNeighborsPos);
    }

    //----------

    //The following methods check the neighbors of the block placed and figures out how the placed block should connect to neighboring blocks, and returns the appropriate block state property.
    //They are very similar. This is why I put them in one place. I may figure out a way to make them all use one method in the future.

    default FurnitureConnectionState getTypeAndFamily(BlockState state, BlockState neighbor1State, BlockState neighbor2state, TagKey<Block> ofBlockSetFamilyTag) {

        boolean neighbor1_IsSameBlock = (neighbor1State.is(state.getBlock()) || neighbor1State.is(ofBlockSetFamilyTag) )
                && state.getValue(FACING) == neighbor1State.getValue(FACING); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?

        boolean neighbor2_IsSameBlock = (neighbor2state.is(state.getBlock())|| neighbor2state.is(ofBlockSetFamilyTag) )
                && state.getValue(FACING) == neighbor2state.getValue(FACING); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?

        if (neighbor1_IsSameBlock && !neighbor2_IsSameBlock) return FurnitureConnectionState.RIGHT;
        else if (!neighbor1_IsSameBlock && neighbor2_IsSameBlock) return FurnitureConnectionState.LEFT;
        else if (neighbor1_IsSameBlock) return FurnitureConnectionState.MIDDLE;
        return FurnitureConnectionState.SOLO;
    }

    default PillarConnectionState getPipeType(BlockState state, BlockState forward, BlockState backward) {
        boolean blockstate_forward_is_same = forward.is(state.getBlock()) && state.getValue(PLANAR_AXIS) == forward.getValue(PLANAR_AXIS) && state.getValue(SURFACE_DIRECTION) == forward.getValue(SURFACE_DIRECTION);

        boolean blockstate_backward_is_same = backward.is(state.getBlock()) && state.getValue(PLANAR_AXIS) == backward.getValue(PLANAR_AXIS) && state.getValue(SURFACE_DIRECTION) == backward.getValue(SURFACE_DIRECTION);

        if (blockstate_forward_is_same && !blockstate_backward_is_same) return PillarConnectionState.NEGATIVE;
        else if (!blockstate_forward_is_same && blockstate_backward_is_same) return PillarConnectionState.POSITIVE;
        else if (blockstate_forward_is_same) return PillarConnectionState.MIDDLE;
        return PillarConnectionState.SOLO;
    }

    default PillarConnectionState getType(BlockState state, BlockState above, BlockState below) {
        boolean blockstate_above_is_same = above.is(state.getBlock()) && state.getValue(AXIS) == above.getValue(AXIS);
        boolean blockstate_below_is_same = below.is(state.getBlock()) && state.getValue(AXIS) == below.getValue(AXIS);

        //Where "above" and "below" refer to in the positive and negative axial direction respectively, like Y direction (height).
        if (blockstate_above_is_same && !blockstate_below_is_same) return PillarConnectionState.NEGATIVE;
        else if (!blockstate_above_is_same && blockstate_below_is_same) return PillarConnectionState.POSITIVE;
        else if (blockstate_above_is_same) return PillarConnectionState.MIDDLE;
        return PillarConnectionState.SOLO;
    }
}
