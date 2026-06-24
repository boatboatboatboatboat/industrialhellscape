package net.boat.industrialhellscape.block.modded_block_state_properties;

import net.minecraft.util.StringRepresentable;

//INFO:
//-----
//This enum is used to determine the block orientation along any plane surface relative to player perspective.
//For example, an arrow-shaped block that can face left, up, right, or down on a wall, and also on a floor relative to the player viewing it
//The naming is arbitrary, a block can be rotated 90 degrees four times before returning to its original state, hence the four directions.
//-----

public enum RelativePlanarDirectionState implements StringRepresentable {
    LEFT("left"),
    UP("up"),
    RIGHT("right"),
    DOWN("down");

    private final String name;
    private RelativePlanarDirectionState(String type) {this.name = type;}
    public String toString() {return this.name;}
    public String getSerializedName() {return this.name;}
}
