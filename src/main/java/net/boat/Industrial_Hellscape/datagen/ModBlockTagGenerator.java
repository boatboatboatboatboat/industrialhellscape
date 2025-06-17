package net.boat.Industrial_Hellscape.datagen;

import net.boat.Industrial_Hellscape.IndustrialHellscape;
import net.boat.Industrial_Hellscape.block.ModBlocks;
import net.boat.Industrial_Hellscape.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, IndustrialHellscape.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Blocks.VESSELPLATE_VARIANTS)
                .add(ModBlocks.VESSELPLATE_GRATE_BLOCK.get()); //.addTags(Tags.Blocks.etc);

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.VESSELPLATE_GRATE_BLOCK.get()); //.addTags(Tags.Blocks.etc)

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.VESSELPLATE_GRATE_BLOCK.get(),
                    ModBlocks.HAZARD_STRIPE_YELLOW.get()
                    //OTHER BLOCKS GO HERE

                );
    }
}
