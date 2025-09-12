package net.boat.industrialhellscape.block.special_blocks_properties;

//INFO:
//-----
//This enum dictates the possible block state for an orientable inner corner or bracket - style block
//-----

import net.minecraft.util.StringRepresentable;

public enum MultiBlockPlacementDirection implements StringRepresentable {
    VERTICAL("vertical"),
    HORIZONTAL("horizontal"),
    FORWARD("forward");

    private final String name;
    private MultiBlockPlacementDirection(String type) {this.name = type;}
    public String toString() {return this.name;}
    public String getSerializedName() {return this.name;}
}
