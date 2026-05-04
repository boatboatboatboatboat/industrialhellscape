package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, IndustrialHellscape.MOD_ID, existingFileHelper);
    }
    public static final TagKey<Item> PICKAXES = TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("minecraft", "pickaxes"));
    public static final TagKey<Item> WRENCHES = TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("forge", "wrenches"));
    public static final TagKey<Item> WRENCH = TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("forge", "tools/wrench"));
    public static final TagKey<Item> TOOLS = TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("forge", "tools"));

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //---------- EXTERNAL TAG REGISTRATION ----------
        tag(WRENCHES); //Forge
        tag(WRENCH); //Forge
        tag(TOOLS); //Common modloader tag

        this.tag(WRENCH)
                .add(ModItems.INHELL_HAVEN_DEVICE.get()
                );
        this.tag(WRENCHES)
                .add(ModItems.INHELL_HAVEN_DEVICE.get()
                );
        this.tag(TOOLS)
                .add(ModItems.INHELL_HAVEN_DEVICE.get()
                );
        //---------- END OF MODLOADER TAG REGISTRATION ----------

        //---------- RECIPE DATAGEN INPUT TAGS ----------
        this.tag(ModTags.Items.HVAC_SMELTABLE_ITEM)
                .add(
                        ModBlocks.DUCT.get().asItem(),
                        ModBlocks.RUSTY_DUCT.get().asItem()
                );
        //---------- END OF RECIPE DATAGEN INPUT TAGS ----------

        //---------- FURNITURE TAGS ----------

        //FURNITURE CATEGORIES BELOW

        //---------- END OF FURNITURE TAGS ----------

        //---------- MISC TAGS ----------

        this.tag(ModTags.Items.IH_RECIPE_STONELIKES)
                .add(
                        Items.COBBLESTONE,
                        Items.COBBLED_DEEPSLATE,
                        Items.STONE,
                        Items.DEEPSLATE,
                        Items.DIORITE,
                        Items.GRANITE,
                        Items.ANDESITE,
                        Items.BLACKSTONE,
                        Items.END_STONE
                );

        this.tag(ModTags.Items.IH_RECIPE_INGOTS)
                .add(
                        Items.COPPER_INGOT,
                        Items.IRON_INGOT
                );

        this.tag(ModTags.Items.IH_COMPATIBLE_TOOLS)
                .add(
                        ModItems.INHELL_HAVEN_DEVICE.get()
                )
                .addTags(
                        PICKAXES,
                        WRENCHES
                )
                ;

        this.tag(ModTags.Items.IH_COMPATIBLE_MODDED_TOOLS)
                .add(
                        ModItems.INHELL_HAVEN_DEVICE.get()
                )
                .addTags(
                        WRENCHES
                )
        ;
    }

    //---------- END OF MISC TAGS ----------
}
