package net.boat.industrialhellscape.block.block_state_enums;

//INFO:
//-----
//For pipe blocks attached to a block surface. They can be rotated 90 degrees.
//Since they are coaxially symmetrical, the block states properties can be simplified to this enum of two values,
//Instead of having to use four enum values of HORIZONTAL_FACING.
//-----

import net.minecraft.util.StringRepresentable;

public enum SurfacePipeMountState implements StringRepresentable {
    STRAIGHT("straight"),
    SIDEWAYS("sideways");

    private final String name;
    private SurfacePipeMountState(String type) {this.name = type;}
    public String toString() {return this.name;}
    public String getSerializedName() {return this.name;}
}
