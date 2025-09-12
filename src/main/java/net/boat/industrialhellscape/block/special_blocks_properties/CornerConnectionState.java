package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.util.StringRepresentable;

//INFO:
//-----
//This enum dictates the possible block state for an orientable inner corner or bracket - style block
//-----

public enum CornerConnectionState implements StringRepresentable {
    UP("up"),
    SIDE("side"),
    DOWN("down");

    private final String name;
    private CornerConnectionState(String type) {this.name = type;}
    public String toString() {return this.name;}
    public String getSerializedName() {return this.name;}
}
