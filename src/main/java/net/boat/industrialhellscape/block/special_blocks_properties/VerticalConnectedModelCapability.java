package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

//INFO:
//-----
//This interface offloads similar code from other block classes that give the blocks furniture connection ability.
//-----

public interface VerticalConnectedModelCapability {
    DirectionProperty FACING = BlockStateProperties.VERTICAL_DIRECTION; //"FACING" is used to store DirectionProperty value of "north, south, east, west" //KJ


    //Create method to find and return blockstate of the block in the relative left direction of the placed block
    default BlockState getRelativeTop(Level level, BlockPos positionClicked, Direction directionClicked) {
        Direction relativeLeft = directionClicked.getCounterClockWise();
        BlockPos leftNeighborsPos = positionClicked.relative(relativeLeft);

        return level.getBlockState(leftNeighborsPos);
    }
    //Create method to find and return blockstate of the block in the relative right direction of the placed block
    default BlockState getRelativeBottom(Level level, BlockPos positionClicked, Direction directionClicked) {
        Direction relativeRight = directionClicked.getClockWise();
        BlockPos rightNeighborsPos = positionClicked.relative(relativeRight);

        return level.getBlockState(rightNeighborsPos);
    }

    default FurnitureConnectionState getType(BlockState state, BlockState left, BlockState right, TagKey<Block> ofBlockSet) {

        boolean block_above_is_same = (left.is(state.getBlock()) || left.is(ofBlockSet) )
                && state.getValue(FACING) == left.getValue(FACING); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?


        boolean block_below_is_same = (right.is(state.getBlock())|| right.is(ofBlockSet) )
                && state.getValue(FACING) == right.getValue(FACING); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?

        //Defines the updated block's connection type based on neighbor checks above
        if (block_above_is_same && !block_below_is_same) return FurnitureConnectionState.RIGHT;
        else if (!block_above_is_same && block_below_is_same) return FurnitureConnectionState.LEFT;
        else if (block_above_is_same) return FurnitureConnectionState.MIDDLE;
        return FurnitureConnectionState.SOLO;
    }
}
