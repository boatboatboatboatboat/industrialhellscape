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
    //MAIN CLASS BLOCKS
        //VESSELPLATE
        //VESSELGLASS
        //ROCKRETE
        //PIPEWORKS
        this.tag(ModTags.Blocks.VESSELPLATE_BLOCKS)
                .add(
                        ModBlocks.VESSELPLATE.get(),
                        ModBlocks.RIVETED_VESSELPLATE.get(),
                        ModBlocks.VESSELPLATE_GRATE_BLOCK.get(),
                        ModBlocks.VESSELPLATE_GRATE.get(),
                        ModBlocks.SEETHROUGH_GRATE.get(),
                        ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get(),
                        ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get(),
                        ModBlocks.SMOOTH_VESSELPLATE.get(),
                        ModBlocks.SMOOTH_VESSELPLATE_TILE.get(),
                        ModBlocks.VESSELPLATE_PILLAR.get(),

                        ModBlocks.GRAY_VESSELPLATE.get(),
                        ModBlocks.GRAY_RIVETED_VESSELPLATE.get(),
                        ModBlocks.GRAY_VESSELPLATE_GRATE.get(),
                        ModBlocks.GRAY_SEETHROUGH_GRATE.get(),
                        ModBlocks.GRAY_HORIZONTAL_RIVETED_VESSELPLATE.get(),
                        ModBlocks.GRAY_VERTICAL_RIVETED_VESSELPLATE.get(),
                        ModBlocks.SMOOTH_GRAY_VESSELPLATE.get(),
                        ModBlocks.SMOOTH_GRAY_VESSELPLATE_TILE.get(),
                        ModBlocks.GRAY_VESSELPLATE_PILLAR.get(),

                        ModBlocks.STRUT.get(),
                        ModBlocks.STRUT_STAIRS.get(),
                        ModBlocks.STRUT_SLAB.get(),

                        ModBlocks.CATWALK_STRUT.get(),
                        ModBlocks.CATWALK_STRUT_SLAB.get(),
                        ModBlocks.CATWALK_STRUT_STAIRS.get(),

                        ModBlocks.GRAY_STRUT.get(),
                        ModBlocks.GRAY_STRUT_STAIRS.get(),
                        ModBlocks.GRAY_STRUT_SLAB.get(),

                        ModBlocks.GRAY_CATWALK_STRUT.get(),
                        ModBlocks.GRAY_CATWALK_STRUT_SLAB.get(),
                        ModBlocks.GRAY_CATWALK_STRUT_STAIRS.get(),


                        ModBlocks.HORIZONTAL_ENCASED_CABLES.get(),
                        ModBlocks.VERTICAL_ENCASED_CABLES.get(),
                        ModBlocks.RUSTY_VESSELPLATE_GRATE.get()
                );

        this.tag(ModTags.Blocks.VESSELGLASS_BLOCKS)
                .add(
                        ModBlocks.REINFORCED_VESSELGLASS.get(),
                        ModBlocks.VESSELGLASS.get(),
                        ModBlocks.GRAY_REINFORCED_VESSELGLASS.get(),
                        ModBlocks.GRAY_VESSELGLASS.get()

                );

        this.tag(ModTags.Blocks.ROCKRETE_BLOCKS)
                .add(
                        ModBlocks.GRAY_ROCKRETE.get(),
                        ModBlocks.GRAY_ROCKRETE_SLAB.get(),
                        ModBlocks.GRAY_ROCKRETE_STAIRS.get(),
                        ModBlocks.GRAY_ROCKRETE_PILLAR.get(),
                        ModBlocks.GRAY_ROCKRETE_REBAR.get(),

                        ModBlocks.GREEN_ROCKRETE.get(),
                        ModBlocks.GREEN_ROCKRETE_SLAB.get(),
                        ModBlocks.GREEN_ROCKRETE_STAIRS.get(),
                        ModBlocks.GREEN_ROCKRETE_PILLAR.get(),
                        ModBlocks.GREEN_ROCKRETE_REBAR.get(),

                        ModBlocks.YELLOW_ROCKRETE.get(),
                        ModBlocks.YELLOW_ROCKRETE_SLAB.get(),
                        ModBlocks.YELLOW_ROCKRETE_STAIRS.get(),
                        ModBlocks.YELLOW_ROCKRETE_PILLAR.get(),
                        ModBlocks.YELLOW_ROCKRETE_REBAR.get(),

                        ModBlocks.BLUE_ROCKRETE.get(),
                        ModBlocks.BLUE_ROCKRETE_SLAB.get(),
                        ModBlocks.BLUE_ROCKRETE_STAIRS.get(),
                        ModBlocks.BLUE_ROCKRETE_PILLAR.get(),
                        ModBlocks.BLUE_ROCKRETE_REBAR.get(),

                        ModBlocks.HAZARD_STRIPE_YELLOW.get(),
                        ModBlocks.HAZARD_STRIPE_RED.get(),
                        ModBlocks.GRIMY_RESTROOM_TILE.get()
                );

        this.tag(ModTags.Blocks.PIPEWORKS_BLOCKS) //FOR CORRECT MINING TOOLS AND TOOL TIER
                .addTags(
                        ModTags.Blocks.COPPER_PIPE_CONDUIT,
                        ModTags.Blocks.BRASS_PIPE_CONDUIT,
                        ModTags.Blocks.GRAY_PIPE_CONDUIT
                );

        //BLOCKSET FAMILIES
        this.tag(ModTags.Blocks.CLASSIC_DESK)
                .add(
                        ModBlocks.DESK.get(),
                        ModBlocks.DESK_DRAWER.get()
                );
        this.tag(ModTags.Blocks.METAL_DESK)
                .add(
                        ModBlocks.METAL_DESK.get(),
                        ModBlocks.METAL_DESK_DRAWER.get(),
                        ModBlocks.METAL_DESK_DRAWER_2.get()
                );
        this.tag(ModTags.Blocks.METAL_DESK)
                .add(
                        ModBlocks.METAL_DESK.get(),
                        ModBlocks.METAL_DESK_DRAWER.get(),
                        ModBlocks.METAL_DESK_DRAWER_2.get()
                );
        this.tag(ModTags.Blocks.COPPER_PIPE_CONDUIT)
                .add(
                        ModBlocks.PIPEWORKS.get(),
                        ModBlocks.COPPER_PIPE_CONDUIT.get(),
                        ModBlocks.COPPER_PIPE_CONDUIT_PLANAR_CORNER.get(),
                        ModBlocks.COPPER_PIPE_CONDUIT_INNER_CORNER.get(),
                        ModBlocks.COPPER_PIPE_CONDUIT_OUTER_CORNER.get()
                );
        this.tag(ModTags.Blocks.BRASS_PIPE_CONDUIT)
                .add(
                        ModBlocks.PIPEWORKS.get(),
                        ModBlocks.BRASS_PIPE_CONDUIT.get(),
                        ModBlocks.BRASS_PIPE_CONDUIT_PLANAR_CORNER.get(),
                        ModBlocks.BRASS_PIPE_CONDUIT_INNER_CORNER.get(),
                        ModBlocks.BRASS_PIPE_CONDUIT_OUTER_CORNER.get()
                );
        this.tag(ModTags.Blocks.GRAY_PIPE_CONDUIT)
                .add(
                        ModBlocks.PIPEWORKS.get(),
                        ModBlocks.GRAY_PIPE_CONDUIT.get(),
                        ModBlocks.GRAY_PIPE_CONDUIT_PLANAR_CORNER.get(),
                        ModBlocks.GRAY_PIPE_CONDUIT_INNER_CORNER.get(),
                        ModBlocks.GRAY_PIPE_CONDUIT_OUTER_CORNER.get()
                );

        //FURNITURE CATEGORY TAGS
        this.tag(ModTags.Blocks.ALL_FURNITURE_BLOCKS)
                .addTags(
                        ModTags.Blocks.AMENITY_FURNITURE_CATEGORY,
                        ModTags.Blocks.HYGIENE_FURNITURE_CATEGORY,
                        ModTags.Blocks.INDUSTRIAL_FURNITURE_CATEGORY,
                        ModTags.Blocks.TECHNOLOGY_FURNITURE_CATEGORY,
                        ModTags.Blocks.SAFETY_FURNITURE_CATEGORY
                );

        this.tag(ModTags.Blocks.SAFETY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.RED_WALL_MEDKIT.get(),
                        ModBlocks.WHITE_WALL_MEDKIT.get()
                );
        this.tag(ModTags.Blocks.HYGIENE_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.TOILET.get(),
                        ModBlocks.SINK.get()
                );

        this.tag(ModTags.Blocks.INDUSTRIAL_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.GRAY_BOLTED_BRACKET.get(),
                        ModBlocks.BLACK_BOLTED_BRACKET.get(),
                        ModBlocks.YELLOW_TRIPOD.get(),
                        ModBlocks.WORK_LIGHT_MOUNT.get(),
                        ModBlocks.FLOOR_WORK_LIGHT.get()
                );

        this.tag(ModTags.Blocks.TECHNOLOGY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.RETRO_COMPUTER.get(),
                        ModBlocks.CASSETTE_PLAYER.get()
                );
        this.tag(ModTags.Blocks.AMENITY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.DESK.get(),
                        ModBlocks.DESK_DRAWER.get(),
                        ModBlocks.METAL_DESK.get(),
                        ModBlocks.METAL_DESK_DRAWER.get(),
                        ModBlocks.METAL_DESK_DRAWER_2.get()
                );

        //MINING TAGS

        /*this.tag(BlockTags.NEEDS_STONE_TOOL) //When specific tool tiers are required
                .add(

                );*/
        //TAG inputs
        this.tag(BlockTags.NEEDS_STONE_TOOL) //When specific tool tiers are required
                .addTags(
                        ModTags.Blocks.VESSELPLATE_BLOCKS,
                        ModTags.Blocks.VESSELGLASS_BLOCKS,
                        ModTags.Blocks.ROCKRETE_BLOCKS
                );

        this.tag(BlockTags.NEEDS_IRON_TOOL) //When specific tool tiers are required
                .addTags(
                        ModTags.Blocks.PIPEWORKS_BLOCKS
                );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTags(
                        ModTags.Blocks.ROCKRETE_BLOCKS,
                        ModTags.Blocks.VESSELPLATE_BLOCKS,
                        ModTags.Blocks.VESSELGLASS_BLOCKS,
                        ModTags.Blocks.PIPEWORKS_BLOCKS,

                        ModTags.Blocks.ALL_FURNITURE_BLOCKS //All furniture can be mined with axe or pickaxe.
                );
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .addTags(
                        ModTags.Blocks.ALL_FURNITURE_BLOCKS //All furniture can be mined with axe or pickaxe.
                );
    }
}
