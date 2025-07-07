package net.boat.industrialhellscape.util;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> VESSELPLATE_BLOCKS = tag("vesselplate_blocks");
        public static final TagKey<Block> VESSELGLASS_BLOCKS = tag("vesselglass_blocks");
        public static final TagKey<Block> ROCKRETE_BLOCKS = tag("rockrete_blocks");

        public static final TagKey<Block> CLASSIC_DESK = tag("classic_desk");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(IndustrialHellscape.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> VESSELPLATE_SMELTABLE_ITEM = tag("vesselplate_smeltable_item");
        public static final TagKey<Item> VESSELGLASS_SMELTABLE_ITEM = tag("vesselglass_smeltable_item");
        public static final TagKey<Item> ROCKRETE_SMELTABLE_ITEM = tag("rockrete_smeltable_item");
        public static final TagKey<Item> VESSELPLATE_STONECUT_OUTPUTS = tag("vesselplate_blocks");
        public static final TagKey<Item> VESSELGLASS_STONECUT_OUTPUTS = tag("vesselglass");
        public static final TagKey<Item> ROCKRETE_STONECUT_OUTPUTS = tag("rockrete_blocks");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(IndustrialHellscape.MOD_ID, name));
        }
    }
}
