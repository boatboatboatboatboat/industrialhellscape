package net.boat.industrialhellscape.block.modded_interfaces;

import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public interface MultiBlockPlacementInterface {
    static BlockPos posToPlaceOtherHalf(BlockPos pPos, TwoBlockMultiBlockState pPart, Direction placementDirection, MultiBlockPlacementDirection multiBlockPlacementDirection) {
        //Used for multiple block methods in multiblock class.
        //returns the direction where the other half is to be placed.
        return switch (multiBlockPlacementDirection) {
            case VERTICAL -> pPos.relative(pPart == TwoBlockMultiBlockState.POSITIVE ? Direction.DOWN : Direction.UP);
            case HORIZONTAL -> pPos.relative(pPart == TwoBlockMultiBlockState.POSITIVE ? placementDirection.getCounterClockWise() : placementDirection.getClockWise());
            default -> pPos.relative(pPart == TwoBlockMultiBlockState.POSITIVE ? placementDirection.getOpposite() : placementDirection);
        };
    }
}
