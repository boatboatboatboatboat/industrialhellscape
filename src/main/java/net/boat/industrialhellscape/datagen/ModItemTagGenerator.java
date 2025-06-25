package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, IndustrialHellscape.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(ModTags.Items.DEBUG_ITEM_TAG)
                .add(
                        ModItems.INHELL_DIAGNOSTIC_TOOL.get(),
                        ModItems.MALEVOLENT_MULTITOOL.get()
                );
        this.tag(ModTags.Items.VESSELPLATE_SMELTABLE_ITEM)
                .add(
                        ModBlocks.VESSELPLATE.get().asItem(),
                        ModBlocks.VESSELPLATE_GRATE_BLOCK.get().asItem(),
                        ModBlocks.VESSELPLATE_GRATE.get().asItem(),
                        ModBlocks.RUSTY_VESSELPLATE_GRATE.get().asItem()
                );
        this.tag(ModTags.Items.ROCKRETE_SMELTABLE_ITEM)
                .add(
                        ModBlocks.BUNKER_WALL.get().asItem(),
                        ModBlocks.HAZARD_STRIPE_RED.get().asItem(),
                        ModBlocks.HAZARD_STRIPE_YELLOW.get().asItem(),
                        ModBlocks.BLACK_ROCKRETE.get().asItem(),
                        ModBlocks.GRAY_ROCKRETE.get().asItem(),
                        ModBlocks.LIGHT_GRAY_ROCKRETE.get().asItem(),
                        ModBlocks.WHITE_ROCKRETE.get().asItem()
                );

    }
}
