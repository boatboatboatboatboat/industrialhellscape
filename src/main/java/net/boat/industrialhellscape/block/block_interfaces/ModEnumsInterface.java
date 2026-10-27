package net.boat.industrialhellscape.block.block_interfaces;

import net.boat.industrialhellscape.block.block_state_enums.DynamicConnectionState;
import net.boat.industrialhellscape.block.block_state_enums.RelativePlanarDirectionState;
import net.boat.industrialhellscape.block.block_state_enums.SurfacePipeMountState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

/*
    Interface to avoid creating multiple duplicate custom enums.
    - They are "created" once here, and optionally used elsewhere by calling the
        appropriate field from this interface
 */

public interface ModEnumsInterface {
    BooleanProperty ALT_STATE = BooleanProperty.create("alt_state");
    EnumProperty<DynamicConnectionState> TYPE = EnumProperty.create("type", DynamicConnectionState .class);
    EnumProperty<SurfacePipeMountState> ORIENTATION = EnumProperty.create("axis", SurfacePipeMountState.class);

    //TODO - figure out what to do about legacy enum that i don't like
    EnumProperty<RelativePlanarDirectionState> PLANE_DIRECTION = EnumProperty.create("plane_direction", RelativePlanarDirectionState.class);
}
