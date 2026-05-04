package net.boat.industrialhellscape.block.modded_block_state_properties;

//INFO:
//-----
//This enum is used for the multi-part properties of stair-railing blocks, dictating
//Whether the left stair rail, right, or both, are present when the block is placed down in a given location.
//-----

import net.minecraft.util.StringRepresentable;

public enum StairRailState implements StringRepresentable {
    LEFT("left"),
    RIGHT("right"),
    DOUBLE("double"),
    NONE("none");

    private final String name;
    private StairRailState(String type) {this.name = type;}
    public String toString() {return this.name;}
    public String getSerializedName() {return this.name;}
}
