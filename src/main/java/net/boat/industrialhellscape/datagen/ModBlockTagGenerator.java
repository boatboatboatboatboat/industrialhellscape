package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
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
                        ModBlocks.RUSTY_VESSELPLATE_GRATE.get(),
                        ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get(),
                        ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get(),
                        ModBlocks.SMOOTH_VESSELPLATE.get(),
                        ModBlocks.SMOOTH_VESSELPLATE.get(),
                        ModBlocks.STRUT.get(),
                        ModBlocks.STRUT_STAIRS.get(),
                        ModBlocks.STRUT_SLAB.get(),
                        ModBlocks.CATWALK_STRUT.get(),
                        ModBlocks.CATWALK_STRUT_SLAB.get(),
                        ModBlocks.CATWALK_STRUT_STAIRS.get(),
                        ModBlocks.VESSELPLATE_PILLAR.get(),
                        ModBlocks.RUSTY_VESSELPLATE_GRATE.get()
                );

        this.tag(ModTags.Blocks.VESSELGLASS_BLOCKS)
                .add(
                        ModBlocks.REINFORCED_VESSELGLASS.get(),
                        ModBlocks.VESSELGLASS.get()
                );

        this.tag(ModTags.Blocks.ROCKRETE_BLOCKS)
                .add(
                        ModBlocks.BUNKER_WALL.get(),
                        ModBlocks.LIGHT_GRAY_ROCKRETE.get(),
                        ModBlocks.LIGHT_GRAY_ROCKRETE_SLAB.get(),
                        ModBlocks.LIGHT_GRAY_ROCKRETE_STAIRS.get(),
                        ModBlocks.LIGHT_GRAY_ROCKRETE_REBAR.get(),

                        ModBlocks.HAZARD_STRIPE_YELLOW.get(),
                        ModBlocks.HAZARD_STRIPE_RED.get()
                );
        this.tag(ModTags.Blocks.CLASSIC_DESK)
                .add(
                        ModBlocks.DESK.get(),
                        ModBlocks.BUNKER_WALL.get(),
                        ModBlocks.DESK_DRAWER.get()
                );


        //VANILLA TAGS BELOW
        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .addTags(ModTags.Blocks.ROCKRETE_BLOCKS);

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .addTag(ModTags.Blocks.VESSELGLASS_BLOCKS)
                .addTag(ModTags.Blocks.VESSELPLATE_BLOCKS);

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTags(ModTags.Blocks.ROCKRETE_BLOCKS)
                .addTags(ModTags.Blocks.VESSELPLATE_BLOCKS)
                .addTags(ModTags.Blocks.VESSELGLASS_BLOCKS);
    }
}
