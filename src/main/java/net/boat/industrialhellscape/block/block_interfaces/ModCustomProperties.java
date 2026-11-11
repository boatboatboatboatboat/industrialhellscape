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

public interface ModCustomProperties {
    BooleanProperty ALT_STATE = BooleanProperty.create("alt_state");
    EnumProperty<DynamicConnectionState> TYPE = EnumProperty.create("type", DynamicConnectionState .class);
    EnumProperty<SurfacePipeMountState> ORIENTATION = EnumProperty.create("axis", SurfacePipeMountState.class);

    BooleanProperty LEFT_FENCE = BooleanProperty.create("left");
    BooleanProperty RIGHT_FENCE = BooleanProperty.create("right");
    BooleanProperty NORTH_WEST_FENCE = BooleanProperty.create("north_west");
    BooleanProperty NORTH_EAST_FENCE = BooleanProperty.create("north_east");
    BooleanProperty SOUTH_WEST_FENCE = BooleanProperty.create("south_west");
    BooleanProperty SOUTH_EAST_FENCE = BooleanProperty.create("south_east");

    //TODO - figure out what to do about legacy enum that i don't like
    EnumProperty<RelativePlanarDirectionState> PLANE_DIRECTION = EnumProperty.create("plane_direction", RelativePlanarDirectionState.class);
}
