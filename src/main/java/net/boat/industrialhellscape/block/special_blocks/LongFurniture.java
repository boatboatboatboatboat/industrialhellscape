package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.FurnitureConnectionState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class LongFurniture extends HorizontalDirectionalBlock {

    public static final EnumProperty<FurnitureConnectionState> TYPE = EnumProperty.create("type", FurnitureConnectionState.class); //"TYPE" is used to store enum value of "solo, left, right, middle" for block connected variants
    public static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING; //"FACING" is used to store DirectionProperty value of "north, south, east, west" //KJ

    protected LongFurniture(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TYPE, FurnitureConnectionState.SOLO)); //Default state if placed with no player present
    }

    //Placement Faces the player
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel(); //Reads the "level" or world
        BlockPos positionClicked = pContext.getClickedPos(); //Get the position when player places new block
        Direction directionClicked = pContext.getHorizontalDirection(); //Gets the cardinal direction when player places new block

//        Direction relativeLeft = directionClicked.getCounterClockWise(); //Defines the cardinal direction left of the block placed
//        Direction relativeRight = directionClicked.getClockWise(); //Defines the cardinal direction right of the block placed
//
//        BlockPos leftNeighborPos = positionClicked.relative(relativeLeft); //Defines block position of block left of placed block
//        BlockPos rightNeighborPos = positionClicked.relative(relativeRight); //Defines block position of block right of placed block

        BlockState state = this.defaultBlockState().setValue(FACING, directionClicked);
        state = state.setValue(TYPE, getType(state, getRelativeLeft(level, positionClicked, directionClicked), getRelativeRight(level, positionClicked, directionClicked)));
        return state;
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    //Create method to find and return blockstate of the block in the relative left direction of the placed block
    public BlockState getRelativeLeft(Level level, BlockPos positionClicked, Direction directionClicked) {
        Direction relativeLeft = directionClicked.getCounterClockWise();
        BlockPos leftNeighborsPos = positionClicked.relative(relativeLeft);
        BlockState leftNeighborsBlockState = level.getBlockState(leftNeighborsPos);

        return leftNeighborsBlockState;
    }
    //Create method to find and return blockstate of the block in the relative right direction of the placed block
    public BlockState getRelativeRight(Level level, BlockPos positionClicked, Direction directionClicked) {
        Direction relativeRight = directionClicked.getClockWise();
        BlockPos rightNeighborsPos = positionClicked.relative(relativeRight);
        BlockState rightNeighborsBlockState = level.getBlockState(rightNeighborsPos);

        return rightNeighborsBlockState;
    }
    //Create method to find blockstate of the block in the relative right direction of the placed block
    //public BlockState getRelativeRight(Level level, BlockPos rightNeighborPos) {
    //    return level.getBlockState(rightNeighborPos);
    //}

    @Override //THIS TELLS THE NEIGHBORS TO UPDATE
    public void neighborChanged(BlockState state, Level level, BlockPos positionClicked, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide) return;

        Direction directionClicked = state.getValue(FACING); //<<<<<< Possibly here
        FurnitureConnectionState type = getType(state, getRelativeLeft(level, positionClicked, directionClicked), getRelativeRight(level, positionClicked, directionClicked));
        if (state.getValue(TYPE) == type) return;

        state = state.setValue(TYPE, type);
        level.setBlock(positionClicked, state, 3); //3
    }

    //Determine placed block's connection state in relation to adjacent blocks' states
    //Outputs the FurnitureConnectionState, the enum defined in another class for SOLO, LEFT, RIGHT, MIDDLE connection variants
    public FurnitureConnectionState getType(BlockState state, BlockState left, BlockState right) {

        boolean block_left_is_same = left.is(state.getBlock()) && state.getValue(FACING) == left.getValue(FACING); //Is the left block's blockstate AND the new block's blockstate facing the same direction?
        boolean block_right_is_same = right.is(state.getBlock()) && state.getValue(FACING) == right.getValue(FACING); //Is the right block's blockstate AND the new block's blockstate facing the same direction?

        //Defines the updated block's connection type based on neighbor checks above
        if (block_left_is_same && !block_right_is_same) return FurnitureConnectionState.RIGHT;
        else if (!block_left_is_same && block_right_is_same) return FurnitureConnectionState.LEFT;
        else if (block_left_is_same) return FurnitureConnectionState.MIDDLE;
        return FurnitureConnectionState.SOLO;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, TYPE); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}
