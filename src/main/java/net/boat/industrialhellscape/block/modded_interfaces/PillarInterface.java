package net.boat.industrialhellscape.block.modded_interfaces;

import net.boat.industrialhellscape.block.modded_block_state_properties.DynamicConnectionState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public interface PillarInterface {

    EnumProperty<Direction.Axis> getAxisProperty();

    default DynamicConnectionState getPillarType(BlockState state, BlockState aboveState, BlockState belowState) {
        boolean blockstate_above_is_same = aboveState.is(state.getBlock()) //Is the blockstate in positive axis direction the same as the current one, AND current orientation matches that blocks' orientation?
                && state.getValue(getAxisProperty()) == aboveState.getValue(getAxisProperty());
        boolean blockstate_below_is_same = belowState.is(state.getBlock()) //Is the blockstate in negative axis direction the same as the current one, AND current orientation matches that blocks' orientation?
                && state.getValue(getAxisProperty()) == belowState.getValue(getAxisProperty());

        // Where "above" and "below" refer to in the positive and negative axial direction respectively, like Y direction (height).
        if (blockstate_above_is_same && !blockstate_below_is_same) return DynamicConnectionState.NEGATIVE;
        else if (!blockstate_above_is_same && blockstate_below_is_same) return DynamicConnectionState.POSITIVE;
        else if (blockstate_above_is_same) return DynamicConnectionState.MIDDLE;
        return DynamicConnectionState.SOLO;
    }
}
