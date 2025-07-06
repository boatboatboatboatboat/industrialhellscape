package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.util.StringRepresentable;

public enum MultiBlockPart implements StringRepresentable {

    TOP("top"), //Only these two states are allowed to be called from the enum PillarConnectionState
    BOTTOM("bottom");

    private final String name; //new variable declared but not defined. It is a string and uses the strings corresponding with the enums above, NOT the uppercase enums

    private MultiBlockPart(String type) { //Constructor
        this.name = type; //The user Enum input (all uppercase), is referred to as "type", defines the string ("top") to be used. That string goes into the "name" variable
    }

    public String toString() {  //Tells the code to use the lowercase string in TOP("top") in anything external to Minecraft (such as loggers)
        return this.name;
    }

    public String getSerializedName() { //Tells the code to use the lowercase string in TOP("top") inside Minecraft
        return this.name;               //getSerializedName is from Minecraft, it's not a default Java method. Required to be present by StringRepresentable.
    }
}
