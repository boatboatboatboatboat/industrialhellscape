package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.util.StringRepresentable;

//INFO:
//-----
//This enum dictates the possible block state a connected furniture block can have (left side connection, middle connection, right side connection, unconnected "solo")
//For different models and/or textures per section
//This is practically the same as the enum PillarConnection state, replacing positive and negative with left and right. But this exists to be human readable.
//-----

public enum FurnitureConnectionState implements StringRepresentable {
    LEFT("left"),
    MIDDLE("middle"),
    RIGHT("right"),
    SOLO("solo");

    private final String name;
    private FurnitureConnectionState(String type) {this.name = type;}
    public String toString() {return this.name;}
    public String getSerializedName() {return this.name;}
}
