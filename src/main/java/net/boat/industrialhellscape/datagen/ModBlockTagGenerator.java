package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
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
        /*this.tag(Tags.Blocks.BARRELS) //Forge Tags
                .add(
                        ModBlocks.VESSELPLATE.get()
                );*/
        this.tag(ModTags.Blocks.VESSELPLATE_BLOCKS)
                .add(
                        ModBlocks.VESSELPLATE.get(),
                        ModBlocks.VESSELPLATE_GRATE_BLOCK.get(),
                        ModBlocks.VESSELPLATE_GRATE.get(),
                        ModBlocks.RUSTY_VESSELPLATE_GRATE.get()
                );
        //VANILLA TAGS BELOW

        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(

                        ModBlocks.GRAY_ROCKRETE.get(),
                        ModBlocks.BLACK_ROCKRETE.get(),
                        ModBlocks.LIGHT_GRAY_ROCKRETE.get(),
                        ModBlocks.WHITE_ROCKRETE.get(),

                        ModBlocks.HAZARD_STRIPE_YELLOW.get(),
                        ModBlocks.HAZARD_STRIPE_RED.get(),
                        ModBlocks.BUNKER_WALL.get()
        );
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(

                        ModBlocks.VESSELPLATE.get(),
                        ModBlocks.VESSELPLATE_GRATE_BLOCK.get(),
                        ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(

                        ModBlocks.VESSELPLATE.get(),
                        ModBlocks.VESSELPLATE_GRATE_BLOCK.get(),

                        ModBlocks.GRAY_ROCKRETE.get(),
                        ModBlocks.HAZARD_STRIPE_YELLOW.get(),
                        ModBlocks.HAZARD_STRIPE_RED.get()
                );
    }
}
