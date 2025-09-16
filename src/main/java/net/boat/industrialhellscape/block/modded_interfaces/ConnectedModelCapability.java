package net.boat.industrialhellscape.block.modded_interfaces;

import net.boat.industrialhellscape.block.modded_block_state_properties.FurnitureConnectionState;
import net.boat.industrialhellscape.block.modded_block_state_properties.PillarConnectionState;
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
import net.minecraft.world.phys.shapes.VoxelShape;

//INFO:
//-----
//This interface offloads similar code from other block classes that give the blocks connection ability when placed next to similar blocks
//This allows changing block-states to custom models that allow blocks to seamlessly "connect", like tables of variable width.
//-----

public interface ConnectedModelCapability {

    //---------- PROPERTIES NECESSARY FOR INTERFACE ----------
    DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING; //"FACING" is used to store DirectionProperty value of "north, south, east, west". For alignment with where player is facing.
    EnumProperty<Direction> SURFACE_DIRECTION = BlockStateProperties.FACING; //"SURFACE_DIRECTION" stores N,S,E,W, along with Up and Down. For alignment with surface clicked for placement.
    EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS; //"AXIS" is used to store which axis the block is aligned to
    BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED; //Waterlogging is true or false

    EnumProperty<FurnitureConnectionState> TYPE = EnumProperty.create("type", FurnitureConnectionState.class); //CUSTOM ENUM, NOT VANILLA PROPERTY
    //---------- END OF PROPERTIES  ----------

    //---------- METHODS FOR CONNECTED FURNITURE BLOCKS ----------
        //Shared methods for (currently) two block classes that implement connective model capability
    static BlockState placeTheConnectableBlock(Block block, BlockPlaceContext pContext, TagKey<Block> BlockSetFamily) {
        Level level = pContext.getLevel();
        BlockState state = block.defaultBlockState();
        BlockPos positionClicked = pContext.getClickedPos(); //Get the position when player places new block
        FluidState fluidstate = level.getFluidState(pContext.getClickedPos());

        Direction directionClicked = pContext.getHorizontalDirection(); //gets PLAYER click direction. Needed to check left and right of the player's clicked direction.
        Direction facing = pContext.getHorizontalDirection().getOpposite(); //gets necessary BLOCK placement direction

        state = state.setValue(FACING, facing); //For example, if the player looks north and places the block, the "front" of it will face south towards the player, hence .getOpposite()
        state = state.setValue(TYPE, getTypeAndFamily(state, getStateAtRelativeLeft(level, positionClicked, directionClicked), getStateAtRelativeRight(level, positionClicked, directionClicked), BlockSetFamily)); //Second, defines connection type of the block
        state =  state.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        return state;
    }

    static void whenConnectedNeighborUpdated(BlockState state, Level level, BlockPos positionClicked, Block block, BlockPos fromPos, TagKey<Block> BlockSetFamily) {
        if (!level.isClientSide) {
            if (state.getValue(WATERLOGGED)) {
                level.scheduleTick(fromPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
        }
        if (level.isClientSide) return;
        Direction directionClicked = state.getValue(FACING).getOpposite();
        FurnitureConnectionState type = getTypeAndFamily(state, getStateAtRelativeLeft(level, positionClicked, directionClicked), getStateAtRelativeRight(level, positionClicked, directionClicked), BlockSetFamily);
        if (state.getValue(TYPE) == type) return;

        state = state.setValue(TYPE, type);
        level.setBlock(positionClicked, state, 3); //3
    }

    static VoxelShape makeConnectedHitboxes(BlockState pState,
            VoxelShape LEFT_SHAPE_NORTH, VoxelShape LEFT_SHAPE_SOUTH, VoxelShape LEFT_SHAPE_EAST, VoxelShape LEFT_SHAPE_WEST,
            VoxelShape MIDDLE_SHAPE_NORTH, VoxelShape MIDDLE_SHAPE_SOUTH, VoxelShape MIDDLE_SHAPE_EAST, VoxelShape MIDDLE_SHAPE_WEST,
            VoxelShape RIGHT_SHAPE_NORTH, VoxelShape RIGHT_SHAPE_SOUTH, VoxelShape RIGHT_SHAPE_EAST, VoxelShape RIGHT_SHAPE_WEST,
            VoxelShape SOLO_SHAPE_NORTH, VoxelShape SOLO_SHAPE_SOUTH, VoxelShape SOLO_SHAPE_EAST, VoxelShape SOLO_SHAPE_WEST
               ) {

        return switch (pState.getValue(TYPE)) {
            case LEFT -> switch (pState.getValue(FACING)) {
                case SOUTH -> LEFT_SHAPE_SOUTH;
                case EAST -> LEFT_SHAPE_EAST;
                case WEST -> LEFT_SHAPE_WEST;
                default -> LEFT_SHAPE_NORTH;
            };
            case MIDDLE -> switch (pState.getValue(FACING)) {
                case SOUTH -> MIDDLE_SHAPE_SOUTH;
                case EAST -> MIDDLE_SHAPE_EAST;
                case WEST -> MIDDLE_SHAPE_WEST;
                default -> MIDDLE_SHAPE_NORTH;
            };
            case RIGHT -> switch (pState.getValue(FACING)) {
                case SOUTH -> RIGHT_SHAPE_SOUTH;
                case EAST -> RIGHT_SHAPE_EAST;
                case WEST -> RIGHT_SHAPE_WEST;
                default -> RIGHT_SHAPE_NORTH;
            };
            default -> switch (pState.getValue(FACING)) { //Default case is assumed solo case
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
    default BlockState getStateAtAxisPositive(Level level, BlockPos pos, Direction.Axis horizontalAxis) {
        return level.getBlockState(pos.relative(Direction.fromAxisAndDirection(horizontalAxis, Direction.AxisDirection.POSITIVE)));
    }
    default BlockState getStateAtAxisNegative(Level level, BlockPos pos, Direction.Axis axis) {
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

    static FurnitureConnectionState getTypeAndFamily(BlockState state, BlockState leftState, BlockState rightState, TagKey<Block> ofBlockSetFamilyTag) {
        // Requires an additional tag parameter to connect with other similar blocks
        boolean left_neighbor_is_same_block = (leftState.is(state.getBlock()) || leftState.is(ofBlockSetFamilyTag) )
                && state.getValue(FACING) == leftState.getValue(FACING); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?
        boolean right_neighbor_is_same_block = (rightState.is(state.getBlock())|| rightState.is(ofBlockSetFamilyTag) )
                && state.getValue(FACING) == rightState.getValue(FACING); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?

        if (left_neighbor_is_same_block && !right_neighbor_is_same_block) return FurnitureConnectionState.RIGHT;
        else if (!left_neighbor_is_same_block && right_neighbor_is_same_block) return FurnitureConnectionState.LEFT;
        else if (left_neighbor_is_same_block) return FurnitureConnectionState.MIDDLE;
        return FurnitureConnectionState.SOLO;
    }

    static PillarConnectionState getPillarType(BlockState state, BlockState aboveState, BlockState belowState) {
        boolean blockstate_above_is_same = aboveState.is(state.getBlock()) //Is the blockstate in positive axis direction the same as the current one, AND current orientation matches that blocks' orientation?
                && state.getValue(AXIS) == aboveState.getValue(AXIS);
        boolean blockstate_below_is_same = belowState.is(state.getBlock()) //Is the blockstate in negative axis direction the same as the current one, AND current orientation matches that blocks' orientation?
                && state.getValue(AXIS) == belowState.getValue(AXIS);

        // Where "above" and "below" refer to in the positive and negative axial direction respectively, like Y direction (height).
        if (blockstate_above_is_same && !blockstate_below_is_same) return PillarConnectionState.NEGATIVE;
        else if (!blockstate_above_is_same && blockstate_below_is_same) return PillarConnectionState.POSITIVE;
        else if (blockstate_above_is_same) return PillarConnectionState.MIDDLE;
        return PillarConnectionState.SOLO;
    }

    static PillarConnectionState getPipeType(BlockState state, BlockState forward, BlockState backward) {
        // Checks for axis compatibility like in getPillarType() above, but also has to check that neighbor blocks are attached to the same wall before
        // allowing connection
        boolean blockstate_forward_is_same = forward.is(state.getBlock())
                && state.getValue(AXIS) == forward.getValue(AXIS) && state.getValue(SURFACE_DIRECTION) == forward.getValue(SURFACE_DIRECTION);
        boolean blockstate_backward_is_same = backward.is(state.getBlock())
                && state.getValue(AXIS) == backward.getValue(AXIS) && state.getValue(SURFACE_DIRECTION) == backward.getValue(SURFACE_DIRECTION);

        if (blockstate_forward_is_same && !blockstate_backward_is_same) return PillarConnectionState.NEGATIVE;
        else if (!blockstate_forward_is_same && blockstate_backward_is_same) return PillarConnectionState.POSITIVE;
        else if (blockstate_forward_is_same) return PillarConnectionState.MIDDLE;
        return PillarConnectionState.SOLO;
    }
}
