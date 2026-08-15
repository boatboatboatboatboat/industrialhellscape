package net.boat.industrialhellscape;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModCommonConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    /*
        Common config. In case block is placed without a player present, rely on server config
        Common config avoids having to do sided logic for all block-placement methods
     */

    static {
        TAGGED_PICKAXE_COMPAT = BUILDER
                .comment("Allow all items tagged 'minecraft:pickaxe' to rotate or interact with this mod's blocks (Modded or otherwise).")
                .define("tagged_pickaxe_compat", true);
    }
    static {
        CROUCH_TO_CHANGE_TEXTURE = BUILDER
                .comment("When placing connective-texture blocks, allow crouching to switch to alternative state texture connection upon placement.")
                .define("crouch_to_change_texture", true);
    }
    static final ModConfigSpec SPEC = BUILDER.build();

    private static final ModConfigSpec.BooleanValue TAGGED_PICKAXE_COMPAT, CROUCH_TO_CHANGE_TEXTURE;
    public static boolean taggedPickaxeCompatEnabled() { return TAGGED_PICKAXE_COMPAT.get(); }
    public static boolean crouchToChangeTexture() { return CROUCH_TO_CHANGE_TEXTURE.get(); }
}
