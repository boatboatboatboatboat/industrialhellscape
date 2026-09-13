package net.boat.industrialhellscape.block.block_interfaces;

import net.minecraft.world.level.block.state.properties.BooleanProperty;

//To avoid creating multiple duplicate custom enums on unrelated block classes. The interface is a place where they are shared.
public interface CustomModEnumsInterface {
    static final BooleanProperty ALT_STATE = BooleanProperty.create("alt_state");
}
