package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.util.StringRepresentable;

//INFO:
//-----
//This enum the halves of a two-block multiblock
//Sign convention used within this mod:
//      Positive refers to left/top/front.
//      Negative refers to right/bottom/back.
//-----

public enum TwoBlockMultiBlockState implements StringRepresentable {
    POSITIVE("positive"),
    NEGATIVE("negative");

    private final String name;
    private TwoBlockMultiBlockState(String type) {this.name = type;}
    public String toString() {return this.name;}
    public String getSerializedName() {return this.name;}
}
