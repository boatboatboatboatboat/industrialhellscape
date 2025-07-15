package net.boat.industrialhellscape.block.special_blocks_properties;

import net.minecraft.util.StringRepresentable;

//INFO:
//-----
//This enum dictates the possible block state an orientable railing/handrail block can have
//For different models and/or textures per section
//-----

public enum RailingState implements StringRepresentable {

    //These blockstates address cardinal directionality without having duplicates or multiple sub-states

    NORTHSINGLE("northsingle"), //One railing, facing cardinal direction
    SOUTHSINGLE("northsingle"),
    EASTSINGLE("northsingle"),
    WESTSINGLE("northsingle"),

    NS_PARALLEL("ns_parallel"), //Two railings, in parallel. Like a bridge.
    EW_PARALLEL("ew_parallel"),

    N_THREEWAY("n_threeway"), //Three railings. Direction designates the open side.
    S_THREEWAY("s_threeway"),
    E_THREEWAY("e_threeway"),
    W_THREEWAY("w_threeway"),

    ALL_FOURWAY("all_fourway"); //Four railings, fully enclosing the top of the block they're placed on.

    private final String name; //new variable declared but not defined. It is a string and uses the strings corresponding with the enums above, NOT the uppercase enums

    private RailingState(String type) { //Constructor
        this.name = type; //The user Enum input (all uppercase), is referred to as "type", defines the string ("top") to be used. That string goes into the "name" variable
    }

    public String toString() {  //Tells the code to use the lowercase string in TOP("top") in anything external to Minecraft (such as loggers)
        return this.name;
    }

    public String getSerializedName() { //Tells the code to use the lowercase string in TOP("top") inside Minecraft
        return this.name;               //getSerializedName is from Minecraft, its not a default Java method. Required to be present by StringRepresentable.
    }
}
