package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
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

        this.tag(ModTags.Items.VESSELPLATE_SMELTABLE_ITEM) //NO HALF BLOCKS HERE
                .add(
                        //Full BLocks
                        ModBlocks.VESSELPLATE.get().asItem(),
                        ModBlocks.VESSELPLATE_GRATE_BLOCK.get().asItem(),
                        ModBlocks.VESSELPLATE_GRATE.get().asItem(),
                        ModBlocks.RUSTY_VESSELPLATE_GRATE.get().asItem(),
                        ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get().asItem(),
                        ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get().asItem(),
                        ModBlocks.SMOOTH_VESSELPLATE.get().asItem(),
                        ModBlocks.SMOOTH_VESSELPLATE_TILE.get().asItem(),
                        ModBlocks.RUSTY_VESSELPLATE_GRATE.get().asItem(),

                        ModBlocks.GRAY_STRUT.get().asItem(),
                        ModBlocks.GRAY_STRUT_STAIRS.get().asItem(),

                        ModBlocks.GRAY_CATWALK_STRUT.get().asItem(),
                        ModBlocks.GRAY_CATWALK_STRUT_STAIRS.get().asItem(),

                        ModBlocks.HORIZONTAL_ENCASED_CABLES.get().asItem(),
                        ModBlocks.VERTICAL_ENCASED_CABLES.get().asItem()
                );
        this.tag(ModTags.Items.VESSELGLASS_SMELTABLE_ITEM)
                .add(
                        ModBlocks.VESSELGLASS.get().asItem(),
                        ModBlocks.REINFORCED_VESSELGLASS.get().asItem()
                );
        this.tag(ModTags.Items.ROCKRETE_SMELTABLE_ITEM)
                .add(
                        //Full Blocks
                        ModBlocks.HAZARD_STRIPE_RED.get().asItem(),
                        ModBlocks.HAZARD_STRIPE_YELLOW.get().asItem(),

                        ModBlocks.GRAY_ROCKRETE_PILLAR.get().asItem(),
                        ModBlocks.GRAY_ROCKRETE_REBAR.get().asItem(),
                        ModBlocks.GRAY_ROCKRETE.get().asItem()
                );

        //----------
        //BELOW:    FOR RECIPE DATA GENERATION PURPOSES. ADD SLABS OR STAIRS

        this.tag(ModTags.Items.VESSELPLATE_STONECUT_OUTPUTS)
                .add(
                        ModBlocks.VESSELPLATE.get().asItem(),
                        ModBlocks.RIVETED_VESSELPLATE.get().asItem(),
                        ModBlocks.VESSELPLATE_GRATE_BLOCK.get().asItem(),
                        ModBlocks.VESSELPLATE_GRATE.get().asItem(),
                        ModBlocks.RUSTY_VESSELPLATE_GRATE.get().asItem(),
                        ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get().asItem(),
                        ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get().asItem(),
                        ModBlocks.SMOOTH_VESSELPLATE.get().asItem(),
                        ModBlocks.SMOOTH_VESSELPLATE_TILE.get().asItem(),

                        ModBlocks.STRUT.get().asItem(),
                        ModBlocks.CATWALK_STRUT.get().asItem(),
                        ModBlocks.CATWALK_STRUT_SLAB.get().asItem(),
                        ModBlocks.CATWALK_STRUT_STAIRS.get().asItem(),
                        ModBlocks.STRUT_STAIRS.get().asItem(),
                        ModBlocks.STRUT_SLAB.get().asItem(),

                        ModBlocks.GRAY_STRUT.get().asItem(),
                        ModBlocks.GRAY_CATWALK_STRUT.get().asItem(),
                        ModBlocks.GRAY_CATWALK_STRUT_SLAB.get().asItem(),
                        ModBlocks.GRAY_CATWALK_STRUT_STAIRS.get().asItem(),
                        ModBlocks.GRAY_STRUT_STAIRS.get().asItem(),
                        ModBlocks.GRAY_STRUT_SLAB.get().asItem(),

                        ModBlocks.VESSELPLATE_PILLAR.get().asItem(),
                        ModBlocks.VERTICAL_ENCASED_CABLES.get().asItem(),
                        ModBlocks.HORIZONTAL_ENCASED_CABLES.get().asItem(),
                        ModBlocks.RUSTY_VESSELPLATE_GRATE.get().asItem()
                );
        this.tag(ModTags.Items.VESSELGLASS_STONECUT_OUTPUTS)
                .add(
                        ModBlocks.REINFORCED_VESSELGLASS.get().asItem(),
                        ModBlocks.VESSELGLASS.get().asItem()
                );
        this.tag(ModTags.Items.ROCKRETE_STONECUT_OUTPUTS)
                .add(

                        ModBlocks.GRAY_ROCKRETE.get().asItem(),
                        ModBlocks.GRAY_ROCKRETE_REBAR.get().asItem(),
                        ModBlocks.GRAY_ROCKRETE_PILLAR.get().asItem(),
                        ModBlocks.GRAY_ROCKRETE_STAIRS.get().asItem(),
                        ModBlocks.GRAY_ROCKRETE_SLAB.get().asItem(),

                        ModBlocks.HAZARD_STRIPE_YELLOW.get().asItem(),
                        ModBlocks.HAZARD_STRIPE_RED.get().asItem(),
                        ModBlocks.GRIMY_RESTROOM_TILE.get().asItem()
                );

        this.tag(ModTags.Items.FURNITURE_CATEGORIES)
                .add(
                        ModBlocks.SAFETY_FURNISHINGS.get().asItem(),
                        ModBlocks.HYGIENE_FURNISHINGS.get().asItem(),
                        ModBlocks.INDUSTRIAL_FURNISHINGS.get().asItem(),
                        ModBlocks.TECHNOLOGY_FURNISHINGS.get().asItem(),
                        ModBlocks.AMENITY_FURNISHINGS.get().asItem().asItem()
                );

        this.tag(ModTags.Items.ALL_FURNITURE)
                .addTags(
                        ModTags.Items.SAFETY_FURNITURE_CATEGORY,
                        ModTags.Items.HYGIENE_FURNITURE_CATEGORY,
                        ModTags.Items.INDUSTRIAL_FURNITURE_CATEGORY,
                        ModTags.Items.TECHNOLOGY_FURNITURE_CATEGORY,
                        ModTags.Items.AMENITY_FURNITURE_CATEGORY
                );
        this.tag(ModTags.Items.SAFETY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.RED_WALL_MEDKIT.get().asItem(),
                        ModBlocks.WHITE_WALL_MEDKIT.get().asItem()
                );
        this.tag(ModTags.Items.HYGIENE_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.TOILET.get().asItem()
                );

        this.tag(ModTags.Items.INDUSTRIAL_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.COPPER_PIPE_CONDUIT.get().asItem(),
                        ModBlocks.COPPER_PIPE_CONDUIT_OUTER_CORNER.get().asItem(),
                        ModBlocks.COPPER_PIPE_CONDUIT_INNER_CORNER.get().asItem(),
                        ModBlocks.COPPER_PIPE_CONDUIT_PLANAR_CORNER.get().asItem()
                );


        this.tag(ModTags.Items.TECHNOLOGY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.RETRO_COMPUTER.get().asItem()
                );

        this.tag(ModTags.Items.AMENITY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.DESK.get().asItem(),
                        ModBlocks.DESK_DRAWER.get().asItem(),
                        ModBlocks.METAL_DESK.get().asItem(),
                        ModBlocks.METAL_DESK_DRAWER_2.get().asItem()
                );

        this.tag(ItemTags.MUSIC_DISCS)
                .add(ModItems.VAPORWAVE_CASSETTE.get()
                );


        this.tag(ModTags.Items.IH_RECIPE_STONELIKES)
                .add(
                        Items.STONE,
                        Items.DEEPSLATE,
                        Items.DIORITE,
                        Items.GRANITE,
                        Items.ANDESITE,
                        Items.BLACKSTONE,
                        Items.END_STONE
                );
    }
}
