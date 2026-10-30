package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.block_classes.RailingBlocks.ParapetBlock;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, IndustrialHellscape.MOD_ID, existingFileHelper);
    }

    private void includeListInItemTagButRemoveNonFullBlocks(List<Block> stoneCutRecipeOutputList, TagKey<Block> blockTag, TagKey<Item> itemTag) {
        List<Item> filterRemoveList = stoneCutRecipeOutputList
                .stream()
                .filter(a-> (a instanceof SlabBlock) || (a instanceof StairBlock) || (a instanceof ParapetBlock))
                .map(Block::asItem)
                .toList();

        //Copy blockTag to itemTag (already 1:1 to stonecutRecipeOutputList
        copy(blockTag, itemTag);

        /*
            After copying, filter from remove-list if it is not empty
            (Remove can optionally have varargs, but only after passing in a single argument first)
        */

        if(filterRemoveList.isEmpty()) { // No entries in the filter
            return;
        } else if(filterRemoveList.size() == 1) { // One entry in the filter
            this.tag(itemTag).remove(filterRemoveList.getFirst());
        } else { // Multiple entries in the filter
            this.tag(itemTag).remove(filterRemoveList.getFirst(), filterRemoveList.stream().skip(1).toArray(Item[]::new));
        }
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        //---------- EXTERNAL TAG REGISTRATION ----------
        this.tag(ModTags.Items.COMMON_WRENCH_TAG)
                .add(ModItems.INHELL_HAVEN_DEVICE.get()
                );
        //---------- END OF EXTERNAL TAG REGISTRATION ----------

        //----- RECIPE DATAGEN INPUT ITEM TAGS -----

        //--- Main
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.VESSELPLATE_STONECUT_OUTPUT,ModTags.Blocks.VESSELPLATE_BLOCKS, ModTags.Items.VESSELPLATE_SMELTABLE_ITEM);
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.VESSELGLASS_STONECUT_OUTPUT,ModTags.Blocks.VESSELGLASS_BLOCKS, ModTags.Items.VESSELGLASS_SMELTABLE_ITEM);
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.ROCKRETE_STONECUT_OUTPUT,ModTags.Blocks.ROCKRETE_BLOCKS, ModTags.Items.ROCKRETE_SMELTABLE_ITEM);
        //--- Structural
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.HVAC_STONECUT_OUTPUT, ModTags.Blocks.HVAC_BLOCKS, ModTags.Items.HVAC_SMELTABLE_ITEM);
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.TRUSS_STONECUT_OUTPUT,ModTags.Blocks.TRUSS_BLOCKS, ModTags.Items.TRUSS_SMELTABLE_ITEM);
        //--- Doors
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.DOORS_STONECUT_OUTPUT,ModTags.Blocks.DOOR_BLOCKS, ModTags.Items.DOOR_ITEMS);
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.TRAPDOORS_STONECUT_OUTPUT,ModTags.Blocks.TRAPDOOR_BLOCKS, ModTags.Items.TRAPDOOR_ITEMS);
        //--- Works
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.PIPEWORKS_STONECUT_OUTPUT,ModTags.Blocks.PIPEWORKS_BLOCKS, ModTags.Items.PIPEWORKS_ITEMS);
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.METALWORKS_STONECUT_OUTPUT,ModTags.Blocks.METALWORKS_BLOCKS, ModTags.Items.METALWORKS_ITEMS);
        //--- Furniture
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.FURNITURE_CATEGORY_STONECUT_OUTPUT,ModTags.Blocks.FURNITURE_CATEGORY_BLOCKS, ModTags.Items.FURNITURE_CATEGORIES);
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.SAFETY_FURNITURE_STONECUT_OUTPUT,ModTags.Blocks.SAFETY_FURNITURE_CATEGORY, ModTags.Items.SAFETY_FURNITURE_CATEGORY);
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.HYGIENE_FURNITURE_STONECUT_OUTPUT,ModTags.Blocks.HYGIENE_FURNITURE_CATEGORY, ModTags.Items.HYGIENE_FURNITURE_CATEGORY);
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.INDUSTRIAL_FURNITURE_STONECUT_OUTPUT,ModTags.Blocks.INDUSTRIAL_FURNITURE_CATEGORY, ModTags.Items.INDUSTRIAL_FURNITURE_CATEGORY);
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.TECHNOLOGY_FURNITURE_STONECUT_OUTPUT,ModTags.Blocks.TECHNOLOGY_FURNITURE_CATEGORY, ModTags.Items.TECHNOLOGY_FURNITURE_CATEGORY);
        includeListInItemTagButRemoveNonFullBlocks(ModRecipeProvider.AMENITY_FURNITURE_STONECUT_OUTPUT,ModTags.Blocks.AMENITY_FURNITURE_CATEGORY, ModTags.Items.AMENITY_FURNITURE_CATEGORY);

        //----- No stone-cutting output recipe lists available for below tags -----
        copy(ModTags.Blocks.BODY_PILLOW_BLOCK, ModTags.Items.BODY_PILLOW_ITEM);

        this.tag(ModTags.Items.ALL_FURNITURE_ITEMS)
                .addTags(
                        ModTags.Items.AMENITY_FURNITURE_CATEGORY,
                        ModTags.Items.HYGIENE_FURNITURE_CATEGORY,
                        ModTags.Items.INDUSTRIAL_FURNITURE_CATEGORY,
                        ModTags.Items.TECHNOLOGY_FURNITURE_CATEGORY,
                        ModTags.Items.SAFETY_FURNITURE_CATEGORY
                );
        //----- No stone-cutting output recipe lists available for above tags -----

        //----- VANILLA ITEM TAGS -----
        copy(BlockTags.FENCES, ItemTags.FENCES);
        copy(BlockTags.WALLS, ItemTags.WALLS);
        //----- END OF VANILLA ITEM TAGS -----

        //---------- CRAFTING TAGS ----------

        /*
            Used for crafting grid ingredients this mod's blocks
           - Rockrete smelts back into Stone by default, regardless
             of the stone used to craft it. This is considered acceptable.
        */
        this.tag(ModTags.Items.IH_RECIPE_STONES)
                .add(
                        Items.STONE,
                        Items.COBBLESTONE,
                        Items.COBBLED_DEEPSLATE,
                        Items.DEEPSLATE,
                        Items.DIORITE,
                        Items.GRANITE,
                        Items.ANDESITE,
                        Items.BLACKSTONE,
                        Items.END_STONE
                );

        /*
            Used for crafting grid ingredients this mod's blocks
           - If you change or add to this tag, you should change/disable
             smelting recipes to avoid transmutation. Metallic blocks smelt
             to Iron Ingots by default.
        */
        this.tag(ModTags.Items.IH_RECIPE_INGOTS)
                .add(
                        Items.IRON_INGOT
                );
    }

    //---------- END OF CRAFTING TAGS ----------
}
