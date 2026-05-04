package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, IndustrialHellscape.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(ModTags.Blocks.HVAC_BLOCKS)
                .add(
                        ModBlocks.DUCT.get(),
                        ModBlocks.RUSTY_DUCT.get()
                );

        this.tag(BlockTags.NEEDS_STONE_TOOL) //When specific tool tiers are required
                .addTags(
//                        ModTags.Blocks.VESSELPLATE_BLOCKS,
//                        ModTags.Blocks.VESSELPLATE_BLOCKS,
//                        ModTags.Blocks.VESSELGLASS_BLOCKS,
//                        ModTags.Blocks.ROCKRETE_BLOCKS,
//
//                        ModTags.Blocks.STRUT_BLOCKS,
                        ModTags.Blocks.HVAC_BLOCKS

//                        ModTags.Blocks.PIPEWORKS_BLOCKS,
//                        ModTags.Blocks.METALWORKS_BLOCKS,
//
//                        ModTags.Blocks.DOOR_BLOCKS,
//                        ModTags.Blocks.TRAPDOOR_BLOCKS,
//
//                        ModTags.Blocks.ALL_FURNITURE_BLOCKS
                );
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTags(
//                        ModTags.Blocks.ROCKRETE_BLOCKS,
//                        ModTags.Blocks.VESSELPLATE_BLOCKS,
//                        ModTags.Blocks.VESSELGLASS_BLOCKS,
//
//                        ModTags.Blocks.STRUT_BLOCKS,
                        ModTags.Blocks.HVAC_BLOCKS
//
//                        ModTags.Blocks.PIPEWORKS_BLOCKS,
//                        ModTags.Blocks.METALWORKS_BLOCKS,
//
//                        ModTags.Blocks.DOOR_BLOCKS,
//                        ModTags.Blocks.TRAPDOOR_BLOCKS,
//
//                        ModTags.Blocks.ALL_FURNITURE_BLOCKS //All furniture can be mined with axe or pickaxe.
                );
//        this.tag(BlockTags.MINEABLE_WITH_AXE)
//                .addTags(
//                        ModTags.Blocks.ALL_FURNITURE_BLOCKS //All furniture can be mined with axe or pickaxe.
//                );
//        this.tag(ModTags.Blocks.MOD_CREATE_FAN_TRANSPARENT)
//                .addTags(
//                        ModTags.Blocks.STRUT_BLOCKS
//                );
//
//        this.tag(ModTags.Blocks.MOD_CREATE_SIMPLE_MOUNTED_STORAGE)
//                .addTags(
//                        ModTags.Blocks.STORAGE_BLOCKS
//                );
    }
}
