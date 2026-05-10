package net.boat.industrialhellscape.block.modded_interfaces;

import net.boat.industrialhellscape.block.modded_block_state_properties.DynamicConnectionState;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public interface PipeInterface {
    EnumProperty getOrientationProperty();
    EnumProperty getSurfaceDirectionProperty();

    default DynamicConnectionState getPipeType(BlockState state, BlockState forward, BlockState backward) {
        // Checks for axis compatibility like in getPillarType() above, but also has to check that neighbor blocks are attached to the same wall before
        // allowing connection
        boolean blockstate_forward_is_same = forward.is(state.getBlock())
                && state.getValue(getOrientationProperty()) == forward.getValue(getOrientationProperty()) && state.getValue(getSurfaceDirectionProperty()) == forward.getValue(getSurfaceDirectionProperty());
        boolean blockstate_backward_is_same = backward.is(state.getBlock())
                && state.getValue(getOrientationProperty()) == backward.getValue(getOrientationProperty()) && state.getValue(getSurfaceDirectionProperty()) == backward.getValue(getSurfaceDirectionProperty());

        if (blockstate_forward_is_same && !blockstate_backward_is_same) return DynamicConnectionState.NEGATIVE;
        else if (!blockstate_forward_is_same && blockstate_backward_is_same) return DynamicConnectionState.POSITIVE;
        else if (blockstate_forward_is_same) return DynamicConnectionState.MIDDLE;
        return DynamicConnectionState.SOLO;
    }
}
