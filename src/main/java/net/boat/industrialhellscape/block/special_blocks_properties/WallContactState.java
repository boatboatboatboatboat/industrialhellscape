package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.util.StringRepresentable;

//INFO:
//-----
//This enum dictates whether a non-full-block is placed touching the ground (touching the top of a block) or touching the ceiling (touching the bottom of a block)
//-----

public enum WallContactState implements StringRepresentable {
    FLOOR("left"), //Only these four states are allowed to be called from the enum FurnitureConnectionState
    CEILING("middle");

    private final String name; //new variable declared but not defined. It is a string and uses the strings corresponding with the enums above, NOT the uppercase enums

    private WallContactState(String type) { //Constructor
        this.name = type; //The user Enum input (all uppercase), is referred to as "type", defines the string ("top") to be used. That string goes into the "name" variable
    }

    public String toString() {  //Tells the code to use the lowercase string in TOP("top") in anything external to Minecraft (such as loggers)
        return this.name;
    }

    public String getSerializedName() { //Tells the code to use the lowercase string in TOP("top") inside Minecraft
        return this.name;               //getSerializedName is from Minecraft, its not a default Java method. Required to be present by StringRepresentable.
    }
}
