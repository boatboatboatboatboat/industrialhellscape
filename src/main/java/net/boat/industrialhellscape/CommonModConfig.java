package net.boat.industrialhellscape;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonModConfig {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    static { //Common config. In case a tool is used on a block without a player present, rely on server config
        TAGGED_PICKAXE_COMPAT = BUILDER
                .comment("Allow all items tagged 'minecraft:pickaxe' to rotate or interact with this mod's blocks (Modded or otherwise).")
                .define("tagged_pickaxe_compat", true);
    }
    static { //Common config. In case block is placed without a player present, rely on server config
        CROUCH_TO_CHANGE_BLOCKSTATES = BUILDER
                .comment("When placing blocks with CTM texture-toggling, allow crouching to switch to the alt-block states")
                .define("crouch_to_change_states", true);
    }
    public static final ForgeConfigSpec SPEC = BUILDER.build();

    private static final ForgeConfigSpec.BooleanValue TAGGED_PICKAXE_COMPAT, CROUCH_TO_CHANGE_BLOCKSTATES;
    public static boolean taggedPickaxeCompatEnabled() { return TAGGED_PICKAXE_COMPAT.get(); }
    public static boolean crouchToChangeBlockStatesEnabled() { return CROUCH_TO_CHANGE_BLOCKSTATES.get(); }
}