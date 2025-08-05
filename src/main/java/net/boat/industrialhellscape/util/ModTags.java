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
        //MAIN BLOCK CATEGORIES
        public static final TagKey<Block> VESSELPLATE_BLOCKS = tag("vesselplate_blocks");
        public static final TagKey<Block> VESSELGLASS_BLOCKS = tag("vesselglass_blocks");
        public static final TagKey<Block> ROCKRETE_BLOCKS = tag("rockrete_blocks");
        public static final TagKey<Block> PIPEWORKS_BLOCKS = tag("pipeworks_blocks");

        //FURNITURE CATEGORIES
        public static final TagKey<Block> SAFETY_FURNITURE_CATEGORY = tag("safety_furniture_category");
        public static final TagKey<Block> HYGIENE_FURNITURE_CATEGORY= tag("hygiene_furniture_category");
        public static final TagKey<Block> INDUSTRIAL_FURNITURE_CATEGORY = tag("industrial_furniture_category");
        public static final TagKey<Block> TECHNOLOGY_FURNITURE_CATEGORY= tag("technology_furniture_category");
        public static final TagKey<Block> AMENITY_FURNITURE_CATEGORY = tag("amenity_furniture_category");
        public static final TagKey<Block> ALL_FURNITURE_BLOCKS = tag("all_furniture_blocks");

        //Connected Blockset Families
        public static final TagKey<Block> CLASSIC_DESK = tag("classic_desk");
        public static final TagKey<Block> METAL_DESK = tag("metal_desk");
        public static final TagKey<Block> COPPER_PIPE_CONDUIT = tag("copper_pipe_conduit");
        public static final TagKey<Block> BRASS_PIPE_CONDUIT = tag("brass_pipe_conduit");
        public static final TagKey<Block> GRAY_PIPE_CONDUIT = tag("gray_pipe_conduit");
        public static final TagKey<Block> WHITE_PIPE_CONDUIT = tag("white_pipe_conduit");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(IndustrialHellscape.MOD_ID, name));
        }
    }

    public static class Items {

        //For ungenerated recipes dictating recycling back into vanilla materials
        public static final TagKey<Item> VESSELPLATE_SMELTABLE_ITEM = tag("vesselplate_smeltable_item");
        public static final TagKey<Item> VESSELGLASS_SMELTABLE_ITEM = tag("vesselglass_smeltable_item");
        public static final TagKey<Item> ROCKRETE_SMELTABLE_ITEM = tag("rockrete_smeltable_item");
        public static final TagKey<Item> PIPEWORKS_SMELTABLE_ITEM = tag("pipeworks_smeltable_item");

        //For stonecutter recipe generation
        public static final TagKey<Item> VESSELPLATE_STONECUT_OUTPUTS = tag("vesselplate_blocks");
        public static final TagKey<Item> VESSELGLASS_STONECUT_OUTPUTS = tag("vesselglass_blocks");
        public static final TagKey<Item> ROCKRETE_STONECUT_OUTPUTS = tag("rockrete_blocks");
        public static final TagKey<Item> PIPEWORKS_STONECUT_OUTPUTS = tag("pipeworks_blocks");

        //Furniture Categories and Subcategories
        public static final TagKey<Item> FURNITURE_CATEGORIES = tag("furniture_categories");
        public static final TagKey<Item> SAFETY_FURNITURE_CATEGORY = tag("safety_furniture_category");
        public static final TagKey<Item> HYGIENE_FURNITURE_CATEGORY= tag("hygiene_furniture_category");
        public static final TagKey<Item> INDUSTRIAL_FURNITURE_CATEGORY = tag("industrial_furniture_category");
        public static final TagKey<Item> TECHNOLOGY_FURNITURE_CATEGORY= tag("technology_furniture_category");
        public static final TagKey<Item> AMENITY_FURNITURE_CATEGORY = tag("amenity_furniture_category");
        public static final TagKey<Item> ALL_FURNITURE_ITEMS = tag("all_furniture_items");

        //For crafting convenience
        public static final TagKey<Item> IH_RECIPE_STONELIKES = tag("ih_recipe_stonelikes");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(IndustrialHellscape.MOD_ID, name));
        }
    }
}
