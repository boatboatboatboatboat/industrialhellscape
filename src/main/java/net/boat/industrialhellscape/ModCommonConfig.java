package net.boat.industrialhellscape;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModCommonConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static { //Common config. In case a tool is used on a block without a player present, rely on server config
        TAGGED_PICKAXE_COMPAT = BUILDER
                .comment("Allow all items tagged 'minecraft:pickaxe' to rotate or interact with this mod's blocks (Modded or otherwise).")
                .define("tagged_pickaxe_compat", true);
    }
    static { //Common config. In case block is placed without a player present, rely on server config
        CROUCH_TO_CHANGE_BLOCKSTATES = BUILDER
                .comment("When placing blocks, allow crouching to switch to alternative block states upon placement")
                .define("crouch_to_change_states", true);
    }
    static final ModConfigSpec SPEC = BUILDER.build();

    private static final ModConfigSpec.BooleanValue TAGGED_PICKAXE_COMPAT, CROUCH_TO_CHANGE_BLOCKSTATES;
    public static boolean taggedPickaxeCompatEnabled() { return TAGGED_PICKAXE_COMPAT.get(); }
    public static boolean crouchToChangeBlockStatesEnabled() { return CROUCH_TO_CHANGE_BLOCKSTATES.get(); }
}
