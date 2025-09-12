package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.util.StringRepresentable;

//INFO:
//-----
//This enum dictates the possible block state an orientable connected pillar block can have ("top" portion of pillar, middle portion of pillar, bottom portion, and unconnected "solo")
//For different models and/or textures per section
//-----

public enum PillarConnectionState implements StringRepresentable {
    POSITIVE("positive"), //Only these four states are allowed to be called from the enum PillarConnectionState
    MIDDLE("middle"),
    NEGATIVE("negative"),
    SOLO("solo");

    private final String name;
    private PillarConnectionState(String type) {this.name = type;}
    public String toString() {return this.name;}
    public String getSerializedName() {return this.name;}
}
