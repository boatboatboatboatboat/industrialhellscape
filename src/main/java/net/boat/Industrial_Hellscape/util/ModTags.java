package net.boat.Industrial_Hellscape.util;

import net.boat.Industrial_Hellscape.IndustrialHellscape;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {

        //LIST OF TAGS USED IN THIS MOD
        public static final TagKey<Block> INHELL_VESSELPLATE_VARIANTS = tag("inhell_vesselplate_variants");
        public static final TagKey<Block> INHELL_ROCKRETE_DYE_VARIANTS = tag("inhell_rockrete_dye_variants");
        public static final TagKey<Block> INHELL_CTM_BLOCKS = tag("inhell_CTM_BLOCKS");
        public static final TagKey<Block> INHELL_ROCKRETE_VARIANTS = tag("inhell_rockrete_variants");



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
