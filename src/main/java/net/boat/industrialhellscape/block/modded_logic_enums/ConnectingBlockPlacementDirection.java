package net.boat.industrialhellscape.block.modded_logic_enums;

/*
INFO:
-----
 This enum is NOT used for block state properties. It is used as a parameter during block registration to
 decide how the two connectable blocks check for connections.

 Currently used by ConnectedFurnitureBlock and ConnectedStorageBlock

*/
import net.minecraft.util.StringRepresentable;

public enum ConnectingBlockPlacementDirection implements StringRepresentable {
    VERTICAL("vertical"),
    HORIZONTAL("horizontal"),
    FORWARD("forward");

    private final String name;
    private ConnectingBlockPlacementDirection(String type) {this.name = type;}
    public String toString() {return this.name;}
    public String getSerializedName() {return this.name;}
}
