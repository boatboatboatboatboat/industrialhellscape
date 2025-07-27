package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.util.StringRepresentable;

//INFO:
//-----
//This enum is used to determine the block orientation along any plane surface relative to player perspective.
//For example, an arrow-shaped block that can face left, up, right, or down on a wall, and also on a floor relative to the player viewing it
//-----

public enum RelativePlanarDirectionState implements StringRepresentable {
    LEFT("left"),
    UP("up"),
    RIGHT("right"),
    DOWN("down");

    private final String name;

    private RelativePlanarDirectionState(String type) {
        this.name = type;
    }

    public String toString() {  //Tells the code to use the lowercase string in TOP("top") in anything external to Minecraft (such as loggers)
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
