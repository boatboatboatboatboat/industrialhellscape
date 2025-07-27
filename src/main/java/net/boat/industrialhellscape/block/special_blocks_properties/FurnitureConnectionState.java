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

    //The following methods lets you refer to these enums with their lower case names above.

    private final String name; //new variable declared but not defined. It is a string and uses the strings corresponding with the enums above, NOT the uppercase enums

    private FurnitureConnectionState(String type) {
        this.name = type;
    }

    public String toString() {  //Tells the code to use the lowercase string in TOP("top") in anything external to Minecraft (such as loggers)
        return this.name;
    }

    public String getSerializedName() { //Tells the code to use the lowercase string in TOP("top") inside Minecraft
        return this.name;               //getSerializedName is from Minecraft, its not a default Java method. Required to be present by StringRepresentable.
    }
}
