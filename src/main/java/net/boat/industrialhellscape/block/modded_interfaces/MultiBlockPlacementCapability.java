package net.boat.industrialhellscape.block.modded_interfaces;

import net.boat.industrialhellscape.block.modded_block_entities.GenericContainerBE;
import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public interface MultiBlockPlacementCapability {
    EnumProperty<TwoBlockMultiBlockState> HALF_PART = EnumProperty.create("half", TwoBlockMultiBlockState.class);

    static BlockEntity newBlockEntityInNegativeBlock(BlockPos pos, BlockState state) {
        //Used when newBlockEntity() is called.
        //Used ONLY for multi-blocks that happen to also be block entity container blocks
        BlockEntity storageEntity = new GenericContainerBE(pos, state);

        if(state.getValue(HALF_PART) == TwoBlockMultiBlockState.POSITIVE) { //If the block is the POSITIVE block
            return null; //no new block entities will be generated
        }
        return storageEntity; //One block entity will be present in this multiblock: in the NEGATIVE block
    }

    static BlockPos posToPlaceOtherHalf(BlockPos pPos, TwoBlockMultiBlockState pPart, Direction placementDirection, MultiBlockPlacementDirection multiBlockPlacementDirection) {
        //Used for multiple block methods in multiblock class.
        //returns the direction where the other half is to be placed.
        return switch (multiBlockPlacementDirection) {
            case VERTICAL -> pPos.relative(pPart == TwoBlockMultiBlockState.POSITIVE ? Direction.DOWN : Direction.UP);
            case HORIZONTAL -> pPos.relative(pPart == TwoBlockMultiBlockState.POSITIVE ? placementDirection.getCounterClockWise() : placementDirection.getClockWise());
            default -> pPos.relative(pPart == TwoBlockMultiBlockState.POSITIVE ? placementDirection : placementDirection.getOpposite());
        };
    }
}
