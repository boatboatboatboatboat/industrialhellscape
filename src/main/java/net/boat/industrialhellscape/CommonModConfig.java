package net.boat.industrialhellscape;

import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class CommonModConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static {
        TAGGED_PICKAXE_COMPAT = BUILDER
                .comment("Allow all items tagged 'minecraft:pickaxe' to rotate or interact with this mod's blocks (Modded or otherwise).")
                .define("tagged_pickaxe_compat", true);
    }
    static final ModConfigSpec SPEC = BUILDER.build();

    private static final ModConfigSpec.BooleanValue TAGGED_PICKAXE_COMPAT;
    public static boolean taggedPickaxeCompatEnabled() {
        return TAGGED_PICKAXE_COMPAT.get();
    }
}
