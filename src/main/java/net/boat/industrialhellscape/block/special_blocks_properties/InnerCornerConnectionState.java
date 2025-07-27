package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.util.StringRepresentable;

//INFO:
//-----
//This enum dictates the possible block state for an orientable inner corner or bracket - style block
//-----

public enum InnerCornerConnectionState implements StringRepresentable {
    UP("up"),
    SIDE("side"),
    DOWN("down");

    //The following methods lets you refer to these enums with their lower case names above.

    private final String name;

    private InnerCornerConnectionState(String type) {
        this.name = type;
    }

    public String toString() {  //Tells the code to use the lowercase string in TOP("top") in anything external to Minecraft (such as loggers)
        return this.name;
    }

    public String getSerializedName() { //Tells the code to use the lowercase string in TOP("top") inside Minecraft
        return this.name;               //getSerializedName is from Minecraft, its not a default Java method. Required to be present by StringRepresentable.
    }
}
