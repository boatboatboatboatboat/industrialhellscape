package net.boat.Industrial_Hellscape.util;

import net.boat.Industrial_Hellscape.IndustrialHellscape;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> VESSELPLATE_VARIANTS = tag("vesselplate_variants");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(IndustrialHellscape.MOD_ID, name));
        }
    }

    public static class Items {
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(IndustrialHellscape.MOD_ID, name));
        }
    }
}
