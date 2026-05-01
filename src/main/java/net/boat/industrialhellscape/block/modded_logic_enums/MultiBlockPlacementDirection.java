package net.boat.industrialhellscape.block.modded_logic_enums;

//INFO:
//-----
// This enum is NOT used for block state properties. It is used as a parameter during block registration to
// decide how the two blocks of a TwoBlockMultiBlock are constructed upon world placement.

// Key:
// VERTICAL - Bottom ("negative") and Top ("positive") halves are stacked on top of each other
// HORIZONTAL - Right ("negative") and Left ("positive") halves are placed next to each other
// FORWARD - Back ("negative") and Front ("positive") halves are placed away from the player, like a bed
//-----

import net.minecraft.util.StringRepresentable;

public enum MultiBlockPlacementDirection implements StringRepresentable {
    VERTICAL("vertical"),
    HORIZONTAL("horizontal"),
    FORWARD("forward");

    private final String name;
    private MultiBlockPlacementDirection(String type) {this.name = type;}
    public String toString() {return this.name;}
    public String getSerializedName() {return this.name;}
}
