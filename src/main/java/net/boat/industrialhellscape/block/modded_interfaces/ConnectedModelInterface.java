package net.boat.industrialhellscape.block.modded_interfaces;

import net.boat.industrialhellscape.block.modded_block_state_properties.DynamicConnectionState;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

//INFO:
//-----
//This interface offloads similar code from other block classes that give the blocks connection ability when placed next to similar blocks
//This allows changing block-states to custom models that allow blocks to seamlessly "connect", like tables of variable width.
//-----

public interface ConnectedModelInterface {

    //---------- METHODS FOR CONNECTED FURNITURE BLOCKS ----------

        //Shared methods for (currently) two block classes that implement connective model capability
    default BlockState placeConnectableBlock(Block block, BlockPlaceContext pContext, TagKey<Block> BlockSetFamily, MultiBlockPlacementDirection placementDirection, DirectionProperty facingProperty, EnumProperty<DynamicConnectionState> typeProperty, BooleanProperty waterLoggedProperty) {
        Level level = pContext.getLevel();
        BlockState state = block.defaultBlockState();
        BlockPos positionClicked = pContext.getClickedPos(); //Get the position when player places new block
        FluidState fluidstate = level.getFluidState(pContext.getClickedPos());

        Direction directionClicked = pContext.getHorizontalDirection(); //gets PLAYER click direction. Needed to check left and right of the player's clicked direction.
        Direction facing = pContext.getHorizontalDirection().getOpposite(); //gets necessary BLOCK placement direction

        //assumed the MultiBlockPlacementDirection is either Horizontal or Vertical (fallback). Usage of "Forward" doesn't make sense here currently
        BlockState getPositiveState = placementDirection == MultiBlockPlacementDirection.HORIZONTAL ? getStateAtRelativeLeft(level, positionClicked, directionClicked) : getStateRelativeTop(level, positionClicked);
        BlockState getNegativeState = placementDirection == MultiBlockPlacementDirection.HORIZONTAL ? getStateAtRelativeRight(level, positionClicked, directionClicked) : getStateRelativeBottom(level, positionClicked);

        state = state.setValue(facingProperty, facing); //For example, if the player looks north and places the block, the "front" of it will face south towards the player, hence .getOpposite()
        state = state.setValue(typeProperty, getTypeAndFamily(state, getPositiveState, getNegativeState, BlockSetFamily, facingProperty)); //Second, defines connection type of the block
        state =  state.setValue(waterLoggedProperty, fluidstate.getType() == Fluids.WATER);
        return state;
    }

    default BlockState placeStackingRailing(Block block, BlockPlaceContext pContext, MultiBlockPlacementDirection placementDirection, BooleanProperty booleanFacingProperty, EnumProperty<DynamicConnectionState> typeProperty, BooleanProperty waterLoggedProperty) {
        //Seperate method because railings use boolean properties to determine direction placed instead of DirectionProperty
        Level level = pContext.getLevel();
        BlockState state = block.defaultBlockState();
        BlockPos positionClicked = pContext.getClickedPos(); //Get the position when player places new block
        FluidState fluidstate = level.getFluidState(pContext.getClickedPos());

        Direction facing = pContext.getHorizontalDirection().getOpposite(); //gets necessary BLOCK placement direction

        //assumed the MultiBlockPlacementDirection is either Horizontal or Vertical (fallback). Usage of "Forward" doesn't make sense here currently
        BlockState getPositiveState = getStateRelativeTop(level, positionClicked);
        BlockState getNegativeState = getStateRelativeBottom(level, positionClicked);

        state = state.setValue(typeProperty, getStackingRailType(state, getPositiveState, getNegativeState, booleanFacingProperty)); //Second, defines connection type of the block
        state =  state.setValue(waterLoggedProperty, fluidstate.getType() == Fluids.WATER);
        return state;
    }

    default void whenConnectedNeighborUpdated(BlockState state, Level level, BlockPos positionClicked, BlockPos fromPos, TagKey<Block> BlockSetFamily, MultiBlockPlacementDirection placementDirection,
                                              DirectionProperty facingProperty, EnumProperty<DynamicConnectionState> typeProperty, BooleanProperty waterLoggedProperty) {
        if (!level.isClientSide) {
            if (state.getValue(waterLoggedProperty)) {
                level.scheduleTick(fromPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
        }
        if (level.isClientSide) return;

        Direction directionClicked = state.getValue(facingProperty).getOpposite();

        BlockState getPositiveState = placementDirection == MultiBlockPlacementDirection.HORIZONTAL ? getStateAtRelativeLeft(level, positionClicked, directionClicked) : getStateRelativeTop(level, positionClicked);
        BlockState getNegativeState = placementDirection == MultiBlockPlacementDirection.HORIZONTAL ? getStateAtRelativeRight(level, positionClicked, directionClicked) : getStateRelativeBottom(level, positionClicked);

        DynamicConnectionState type = getTypeAndFamily(state, getPositiveState, getNegativeState, BlockSetFamily, facingProperty);
        if (state.getValue(typeProperty) == type) return;

        state = state.setValue(typeProperty, type);
        level.setBlock(positionClicked, state, 3); //3
    }

    default VoxelShape makeConnectedHitboxes(BlockState pState,
            VoxelShape LEFT_SHAPE_NORTH, VoxelShape LEFT_SHAPE_SOUTH, VoxelShape LEFT_SHAPE_EAST, VoxelShape LEFT_SHAPE_WEST,
            VoxelShape MIDDLE_SHAPE_NORTH, VoxelShape MIDDLE_SHAPE_SOUTH, VoxelShape MIDDLE_SHAPE_EAST, VoxelShape MIDDLE_SHAPE_WEST,
            VoxelShape RIGHT_SHAPE_NORTH, VoxelShape RIGHT_SHAPE_SOUTH, VoxelShape RIGHT_SHAPE_EAST, VoxelShape RIGHT_SHAPE_WEST,
            VoxelShape SOLO_SHAPE_NORTH, VoxelShape SOLO_SHAPE_SOUTH, VoxelShape SOLO_SHAPE_EAST, VoxelShape SOLO_SHAPE_WEST,
                                             DirectionProperty facingProperty,
                                             EnumProperty<DynamicConnectionState> typeProperty
               ) {

        return switch (pState.getValue(typeProperty)) {
            case POSITIVE -> switch (pState.getValue(facingProperty)) {
                case SOUTH -> LEFT_SHAPE_SOUTH;
                case EAST -> LEFT_SHAPE_EAST;
                case WEST -> LEFT_SHAPE_WEST;
                default -> LEFT_SHAPE_NORTH;
            };
            case MIDDLE -> switch (pState.getValue(facingProperty)) {
                case SOUTH -> MIDDLE_SHAPE_SOUTH;
                case EAST -> MIDDLE_SHAPE_EAST;
                case WEST -> MIDDLE_SHAPE_WEST;
                default -> MIDDLE_SHAPE_NORTH;
            };
            case NEGATIVE -> switch (pState.getValue(facingProperty)) {
                case SOUTH -> RIGHT_SHAPE_SOUTH;
                case EAST -> RIGHT_SHAPE_EAST;
                case WEST -> RIGHT_SHAPE_WEST;
                default -> RIGHT_SHAPE_NORTH;
            };
            default -> switch (pState.getValue(facingProperty)) { //Default case is assumed solo case
                case SOUTH -> SOLO_SHAPE_SOUTH;
                case EAST -> SOLO_SHAPE_EAST;
                case WEST -> SOLO_SHAPE_WEST;
                default -> SOLO_SHAPE_NORTH;
            };
        };
    }
    //---------- METHODS FOR CONNECTED FURNITURE BLOCKS ----------
        //The following methods find the block-state adjacent to the position clicked (this should correspond with a block being placed next to similar neighbors).
        //The block-state of the neighboring block can also be used to identify what block that is too.

    //getStateRelativeLeft and Right are relative to the player
    static BlockState getStateAtRelativeLeft(Level level, BlockPos positionClicked, Direction directionClicked) {
        Direction relativeLeft = directionClicked.getCounterClockWise(); //If facing NORTH, Turn CCW to face left/West
        BlockPos leftNeighborsPos = positionClicked.relative(relativeLeft);
        return level.getBlockState(leftNeighborsPos);
    }
    static BlockState getStateAtRelativeRight(Level level, BlockPos positionClicked, Direction directionClicked) {
        Direction relativeRight = directionClicked.getClockWise(); //If facing NORTH, Turn CW to face right/East
        BlockPos rightNeighborsPos = positionClicked.relative(relativeRight);
        return level.getBlockState(rightNeighborsPos);
    }

    //getStateRelativeForward and Backward are relative to the axes (X,Y,z). Note that it is not consistent with the above GRLeft and GRRight methods.
    static BlockState getStateAtAxisPositive(Level level, BlockPos pos, Direction.Axis horizontalAxis) {
        return level.getBlockState(pos.relative(Direction.fromAxisAndDirection(horizontalAxis, Direction.AxisDirection.POSITIVE)));
    }
    static BlockState getStateAtAxisNegative(Level level, BlockPos pos, Direction.Axis axis) {
        return level.getBlockState(pos.relative(Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE)));
    }

    //getStateRelativeTop checks the neighbor above and belows' blockstate. Used for strictly vertical aligned blocks
    static BlockState getStateRelativeTop(Level level, BlockPos positionClicked) {
        BlockPos leftNeighborsPos = positionClicked.above();

        return level.getBlockState(leftNeighborsPos);
    }

    static BlockState getStateRelativeBottom(Level level, BlockPos positionClicked) {
        BlockPos rightNeighborsPos = positionClicked.below();

        return level.getBlockState(rightNeighborsPos);
    }

    default DynamicConnectionState getStackingRailType(BlockState state, BlockState aboveState, BlockState belowState, BooleanProperty booleanFacingProperty) {
        // Requires an additional tag parameter to connect with other similar blocks
        boolean neighborBelowIsSameBlock = (aboveState.is(state.getBlock()))
                && state.getValue(booleanFacingProperty) == aboveState.getValue(booleanFacingProperty); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?
        boolean neighborAboveIsSameBlock = (belowState.is(state.getBlock()))
                && state.getValue(booleanFacingProperty) == belowState.getValue(booleanFacingProperty); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?

        if (neighborBelowIsSameBlock && !neighborAboveIsSameBlock) return DynamicConnectionState.NEGATIVE;
        else if (!neighborBelowIsSameBlock && neighborAboveIsSameBlock) return DynamicConnectionState.POSITIVE;
        else if (neighborBelowIsSameBlock) return DynamicConnectionState.MIDDLE;
        return DynamicConnectionState.SOLO;
    }

    default DynamicConnectionState getTypeAndFamily(BlockState state, BlockState leftState, BlockState rightState, TagKey<Block> ofBlockSetFamilyTag, DirectionProperty facingProperty) {
        // Requires an additional tag parameter to connect with other similar blocks
        boolean left_neighbor_is_same_block = (leftState.is(state.getBlock()) || leftState.is(ofBlockSetFamilyTag) )
                && state.getValue(facingProperty) == leftState.getValue(facingProperty); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?
        boolean right_neighbor_is_same_block = (rightState.is(state.getBlock())|| rightState.is(ofBlockSetFamilyTag) )
                && state.getValue(facingProperty) == rightState.getValue(facingProperty); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?

        if (left_neighbor_is_same_block && !right_neighbor_is_same_block) return DynamicConnectionState.NEGATIVE;
        else if (!left_neighbor_is_same_block && right_neighbor_is_same_block) return DynamicConnectionState.POSITIVE;
        else if (left_neighbor_is_same_block) return DynamicConnectionState.MIDDLE;
        return DynamicConnectionState.SOLO;
    }

    default DynamicConnectionState getPillarType(BlockState state, BlockState aboveState, BlockState belowState, EnumProperty<Direction.Axis> axisEnumProperty) {
        boolean blockstate_above_is_same = aboveState.is(state.getBlock()) //Is the blockstate in positive axis direction the same as the current one, AND current orientation matches that blocks' orientation?
                && state.getValue(axisEnumProperty) == aboveState.getValue(axisEnumProperty);
        boolean blockstate_below_is_same = belowState.is(state.getBlock()) //Is the blockstate in negative axis direction the same as the current one, AND current orientation matches that blocks' orientation?
                && state.getValue(axisEnumProperty) == belowState.getValue(axisEnumProperty);

        // Where "above" and "below" refer to in the positive and negative axial direction respectively, like Y direction (height).
        if (blockstate_above_is_same && !blockstate_below_is_same) return DynamicConnectionState.NEGATIVE;
        else if (!blockstate_above_is_same && blockstate_below_is_same) return DynamicConnectionState.POSITIVE;
        else if (blockstate_above_is_same) return DynamicConnectionState.MIDDLE;
        return DynamicConnectionState.SOLO;
    }
}
