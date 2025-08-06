package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.util.StringRepresentable;

//INFO:
//-----
//This enum the halves of a two-block multiblock
//The convention: Positive refers to left/top/front, negative refers to right/bottom/back
//-----

public enum TwoStageMultiBlock implements StringRepresentable {
    POSITIVE("positive"),
    NEGATIVE("negative");

    private final String name;

    private TwoStageMultiBlock(String type) {
        this.name = type;
    }

    public String toString() {  //Tells the code to use the lowercase string in TOP("top") in anything external to Minecraft (such as loggers)
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
