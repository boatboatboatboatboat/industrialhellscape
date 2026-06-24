package net.boat.industrialhellscape.block.modded_block_state_properties;

import net.minecraft.util.StringRepresentable;

//INFO:
//-----
//This enum lists the directions diagonal to cardinal directions (e.g. NorthEast)
//-----

public enum IntercardinalDirectionState implements StringRepresentable {
    NE("north_east"), //Only these four states are allowed to be called from the enum DynamicConnectionState
    NW("north_west"),
    SE("south_east"),
    SW("south_west"),
    NONE("none");

    private final String name;
    private IntercardinalDirectionState(String type) {this.name = type;}
    public String toString() {return this.name;}
    public String getSerializedName() {return this.name;}
}
