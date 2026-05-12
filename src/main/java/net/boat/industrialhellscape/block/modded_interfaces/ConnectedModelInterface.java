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

    //--------- NECESSARY FIELDS ----------
    //Blocks that have connected model functionality MUST possess these three methods and supply their block state properties.
    //The other methods below will read these properties and return an appropriate value for use in the block class.
    EnumProperty<DynamicConnectionState> getTypeProperty();
    DirectionProperty getFacingProperty();
    BooleanProperty getWaterloggedProperty();
    //---------- END OF NECESSARY FIELDS FOR BLOCK ENTITY ----------

    //---------- METHODS FOR CONNECTED FURNITURE BLOCKS ----------

        //Shared methods for (currently) two block classes that implement connective model capability
    default BlockState placeTheConnectableBlock(Block block, BlockPlaceContext pContext, TagKey<Block> BlockSetFamily, MultiBlockPlacementDirection placementDirection) {
        Level level = pContext.getLevel();
        BlockState state = block.defaultBlockState();
        BlockPos positionClicked = pContext.getClickedPos(); //Get the position when player places new block
        FluidState fluidstate = level.getFluidState(pContext.getClickedPos());

        Direction directionClicked = pContext.getHorizontalDirection(); //gets PLAYER click direction. Needed to check left and right of the player's clicked direction.
        Direction facing = pContext.getHorizontalDirection().getOpposite(); //gets necessary BLOCK placement direction

        BlockState getPositiveState = placementDirection == MultiBlockPlacementDirection.HORIZONTAL ? getStateAtRelativeLeft(level, positionClicked, directionClicked) : getStateRelativeTop(level, positionClicked);
        BlockState getNegativeState = placementDirection == MultiBlockPlacementDirection.HORIZONTAL ? getStateAtRelativeRight(level, positionClicked, directionClicked) : getStateRelativeBottom(level, positionClicked);

        state = state.setValue(getFacingProperty(), facing); //For example, if the player looks north and places the block, the "front" of it will face south towards the player, hence .getOpposite()
        state = state.setValue(getTypeProperty(), getTypeAndFamily(state, getPositiveState, getNegativeState, BlockSetFamily)); //Second, defines connection type of the block
        state =  state.setValue(getWaterloggedProperty(), fluidstate.getType() == Fluids.WATER);
        return state;
    }

    default void whenConnectedNeighborUpdated(BlockState state, Level level, BlockPos positionClicked, Block block, BlockPos fromPos, TagKey<Block> BlockSetFamily, MultiBlockPlacementDirection placementDirection) {
        if (!level.isClientSide) {
            if (state.getValue(getWaterloggedProperty())) {
                level.scheduleTick(fromPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
        }
        if (level.isClientSide) return;
        Direction directionClicked = state.getValue(getFacingProperty()).getOpposite();

        BlockState getPositiveState = placementDirection == MultiBlockPlacementDirection.HORIZONTAL ? getStateAtRelativeLeft(level, positionClicked, directionClicked) : getStateRelativeTop(level, positionClicked);
        BlockState getNegativeState = placementDirection == MultiBlockPlacementDirection.HORIZONTAL ? getStateAtRelativeRight(level, positionClicked, directionClicked) : getStateRelativeBottom(level, positionClicked);

        DynamicConnectionState type = getTypeAndFamily(state, getPositiveState, getNegativeState, BlockSetFamily);
        if (state.getValue(getTypeProperty()) == type) return;

        state = state.setValue(getTypeProperty(), type);
        level.setBlock(positionClicked, state, 3); //3
    }

    default VoxelShape makeConnectedHitboxes(BlockState pState,
            VoxelShape LEFT_SHAPE_NORTH, VoxelShape LEFT_SHAPE_SOUTH, VoxelShape LEFT_SHAPE_EAST, VoxelShape LEFT_SHAPE_WEST,
            VoxelShape MIDDLE_SHAPE_NORTH, VoxelShape MIDDLE_SHAPE_SOUTH, VoxelShape MIDDLE_SHAPE_EAST, VoxelShape MIDDLE_SHAPE_WEST,
            VoxelShape RIGHT_SHAPE_NORTH, VoxelShape RIGHT_SHAPE_SOUTH, VoxelShape RIGHT_SHAPE_EAST, VoxelShape RIGHT_SHAPE_WEST,
            VoxelShape SOLO_SHAPE_NORTH, VoxelShape SOLO_SHAPE_SOUTH, VoxelShape SOLO_SHAPE_EAST, VoxelShape SOLO_SHAPE_WEST
               ) {

        return switch (pState.getValue(getTypeProperty())) {
            case POSITIVE -> switch (pState.getValue(getFacingProperty())) {
                case SOUTH -> LEFT_SHAPE_SOUTH;
                case EAST -> LEFT_SHAPE_EAST;
                case WEST -> LEFT_SHAPE_WEST;
                default -> LEFT_SHAPE_NORTH;
            };
            case MIDDLE -> switch (pState.getValue(getFacingProperty())) {
                case SOUTH -> MIDDLE_SHAPE_SOUTH;
                case EAST -> MIDDLE_SHAPE_EAST;
                case WEST -> MIDDLE_SHAPE_WEST;
                default -> MIDDLE_SHAPE_NORTH;
            };
            case NEGATIVE -> switch (pState.getValue(getFacingProperty())) {
                case SOUTH -> RIGHT_SHAPE_SOUTH;
                case EAST -> RIGHT_SHAPE_EAST;
                case WEST -> RIGHT_SHAPE_WEST;
                default -> RIGHT_SHAPE_NORTH;
            };
            default -> switch (pState.getValue(getFacingProperty())) { //Default case is assumed solo case
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

    default DynamicConnectionState getTypeAndFamily(BlockState state, BlockState leftState, BlockState rightState, TagKey<Block> ofBlockSetFamilyTag) {
        // Requires an additional tag parameter to connect with other similar blocks
        boolean left_neighbor_is_same_block = (leftState.is(state.getBlock()) || leftState.is(ofBlockSetFamilyTag) )
                && state.getValue(getFacingProperty()) == leftState.getValue(getFacingProperty()); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?
        boolean right_neighbor_is_same_block = (rightState.is(state.getBlock())|| rightState.is(ofBlockSetFamilyTag) )
                && state.getValue(getFacingProperty()) == rightState.getValue(getFacingProperty()); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?

        if (left_neighbor_is_same_block && !right_neighbor_is_same_block) return DynamicConnectionState.NEGATIVE;
        else if (!left_neighbor_is_same_block && right_neighbor_is_same_block) return DynamicConnectionState.POSITIVE;
        else if (left_neighbor_is_same_block) return DynamicConnectionState.MIDDLE;
        return DynamicConnectionState.SOLO;
    }
}
