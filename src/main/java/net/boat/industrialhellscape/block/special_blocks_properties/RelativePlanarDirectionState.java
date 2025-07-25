package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.util.StringRepresentable;

//INFO:
//-----

//-----

public enum RelativePlanarDirectionState implements StringRepresentable {
    LEFT("left"), //Only these four states are allowed to be called from the enum FurnitureConnectionState
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
