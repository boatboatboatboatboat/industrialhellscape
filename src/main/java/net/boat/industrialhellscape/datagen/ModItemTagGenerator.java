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
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, IndustrialHellscape.MOD_ID, existingFileHelper);
    }
    public static final TagKey<Item> PICKAXES = TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("minecraft", "pickaxes"));
    public static final TagKey<Item> WRENCHES = TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("forge", "wrenches"));
    public static final TagKey<Item> WRENCH = TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("forge", "tools/wrench"));
    public static final TagKey<Item> TOOLS = TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("forge", "tools"));

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
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
            //Only a Smeltable-tagged item can be stone-cut into a non-Smeltable output (e.g. slabs)

        this.tag(ModTags.Items.VESSELPLATE_SMELTABLE_ITEM) //NO HALF BLOCKS HERE
                .add(
                        //Full BLocks
                        ModBlocks.VESSELPLATE.get().asItem(),
                        ModBlocks.GRATE.get().asItem(),
                        ModBlocks.SEETHROUGH_GRATE.get().asItem(),
                        ModBlocks.RUSTY_GRATE.get().asItem(),
                        ModBlocks.RIVETED_VESSELPLATE_PANEL.get().asItem(),
                        ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get().asItem(),
                        ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get().asItem(),
                        ModBlocks.SMOOTH_VESSELPLATE_TILE.get().asItem(),
                        ModBlocks.VESSELPLATE_PILLAR.get().asItem(),

                        ModBlocks.RUSTY_GRATE.get().asItem(),

                        ModBlocks.GRAY_VESSELPLATE.get().asItem(),
                        ModBlocks.GRAY_RIVETED_VESSELPLATE_PANEL.get().asItem(),
                        ModBlocks.GRAY_GRATE.get().asItem(),
                        ModBlocks.GRAY_SEETHROUGH_GRATE.get().asItem(),
                        ModBlocks.RIVETED_VESSELPLATE_PANEL.get().asItem(),
                        ModBlocks.GRAY_HORIZONTAL_RIVETED_VESSELPLATE.get().asItem(),
                        ModBlocks.GRAY_VERTICAL_RIVETED_VESSELPLATE.get().asItem(),
                        ModBlocks.SMOOTH_GRAY_VESSELPLATE_TILE.get().asItem(),
                        ModBlocks.GRAY_VESSELPLATE_PILLAR.get().asItem(),

                        ModBlocks.STRUT.get().asItem(),
                        ModBlocks.STRUT_STAIRS.get().asItem(),
                        ModBlocks.CATWALK_STRUT.get().asItem(),
                        ModBlocks.CATWALK_STRUT_STAIRS.get().asItem(),

                        ModBlocks.GRAY_STRUT.get().asItem(),
                        ModBlocks.GRAY_STRUT_STAIRS.get().asItem(),
                        ModBlocks.GRAY_CATWALK_STRUT.get().asItem(),
                        ModBlocks.GRAY_CATWALK_STRUT_STAIRS.get().asItem(),

                        ModBlocks.ENCASED_CABLES.get().asItem()
                );
        this.tag(ModTags.Items.VESSELGLASS_SMELTABLE_ITEM)
                .add(
                        ModBlocks.VESSELGLASS.get().asItem(),
                        ModBlocks.REINFORCED_VESSELGLASS.get().asItem(),
                        ModBlocks.GRAY_VESSELGLASS.get().asItem(),
                        ModBlocks.GRAY_REINFORCED_VESSELGLASS.get().asItem()
                );
        this.tag(ModTags.Items.ROCKRETE_SMELTABLE_ITEM)
                .add(
                        //Full Blocks
                        ModBlocks.HAZARD_STRIPE_RED.get().asItem(),
                        ModBlocks.HAZARD_STRIPE_YELLOW.get().asItem(),
                        ModBlocks.GRIMY_RESTROOM_TILE.get().asItem(),

                        ModBlocks.GRAY_ROCKRETE_PILLAR.get().asItem(),
                        ModBlocks.GRAY_ROCKRETE_STAIRS.get().asItem(),
                        ModBlocks.GRAY_ROCKRETE.get().asItem(),

                        ModBlocks.GREEN_ROCKRETE_PILLAR.get().asItem(),
                        ModBlocks.GREEN_ROCKRETE_STAIRS.get().asItem(),
                        ModBlocks.GREEN_ROCKRETE.get().asItem(),

                        ModBlocks.YELLOW_ROCKRETE_PILLAR.get().asItem(),
                        ModBlocks.YELLOW_ROCKRETE_STAIRS.get().asItem(),
                        ModBlocks.YELLOW_ROCKRETE.get().asItem(),

                        ModBlocks.BLUE_ROCKRETE_PILLAR.get().asItem(),
                        ModBlocks.BLUE_ROCKRETE_STAIRS.get().asItem(),
                        ModBlocks.BLUE_ROCKRETE.get().asItem()
                );

        this.tag(ModTags.Items.PIPEWORKS_SMELTABLE_ITEM)
                .add(
                        ModBlocks.PIPEWORKS.get().asItem(),

                        ModBlocks.COPPER_PIPE_CONDUIT.get().asItem(),
                        ModBlocks.COPPER_PIPE_CONDUIT_PLANAR_CORNER.get().asItem(),
                        ModBlocks.COPPER_PIPE_CONDUIT_INNER_CORNER.get().asItem(),
                        ModBlocks.COPPER_PIPE_CONDUIT_OUTER_CORNER.get().asItem(),

                        ModBlocks.BRASS_PIPE_CONDUIT.get().asItem(),
                        ModBlocks.BRASS_PIPE_CONDUIT_PLANAR_CORNER.get().asItem(),
                        ModBlocks.BRASS_PIPE_CONDUIT_INNER_CORNER.get().asItem(),
                        ModBlocks.BRASS_PIPE_CONDUIT_OUTER_CORNER.get().asItem(),

                        ModBlocks.GRAY_PIPE_CONDUIT.get().asItem(),
                        ModBlocks.GRAY_PIPE_CONDUIT_PLANAR_CORNER.get().asItem(),
                        ModBlocks.GRAY_PIPE_CONDUIT_INNER_CORNER.get().asItem(),
                        ModBlocks.GRAY_PIPE_CONDUIT_OUTER_CORNER.get().asItem()
                );
        //---------- END OF RECIPE DATAGEN INPUT TAGS ----------

        //---------- FURNITUREA TAGS ----------

        //FURNITURE CATEGORIES BELOW

        this.tag(ModTags.Items.FURNITURE_CATEGORIES)
                .add(
                        ModBlocks.SAFETY_FURNISHINGS.get().asItem(),
                        ModBlocks.HYGIENE_FURNISHINGS.get().asItem(),
                        ModBlocks.INDUSTRIAL_FURNISHINGS.get().asItem(),
                        ModBlocks.TECHNOLOGY_FURNISHINGS.get().asItem(),
                        ModBlocks.AMENITY_FURNISHINGS.get().asItem().asItem()
                );

        this.tag(ModTags.Items.ALL_FURNITURE_ITEMS)
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
                        ModBlocks.TOILET.get().asItem(),
                        ModBlocks.SINK.get().asItem()
                );
        this.tag(ModTags.Items.INDUSTRIAL_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.LOCKER_BOX.get().asItem(),
                        ModBlocks.LARGE_LOCKER.get().asItem(),
                        ModBlocks.GRAY_BOLTED_BRACKET.get().asItem(),
                        ModBlocks.BLACK_BOLTED_BRACKET.get().asItem(),
                        ModBlocks.YELLOW_TRIPOD.get().asItem(),
                        ModBlocks.WORK_LIGHT_MOUNT.get().asItem(),
                        ModBlocks.FLOOR_WORK_LIGHT.get().asItem()
                );
        this.tag(ModTags.Items.TECHNOLOGY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.RETRO_COMPUTER.get().asItem(),
                        ModBlocks.CASSETTE_PLAYER.get().asItem()
                );

        this.tag(ModTags.Items.AMENITY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.DESK.get().asItem(),
                        ModBlocks.DESK_DRAWER.get().asItem(),

                        ModBlocks.METAL_DESK.get().asItem(),
                        ModBlocks.METAL_DESK_DRAWER.get().asItem(),
                        ModBlocks.METAL_DESK_DRAWER_2.get().asItem()
                );

        //---------- END OF FURNITURE TAGS ----------

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

        this.tag(ModTags.Items.IH_COMPATIBLE_TOOLS)
                .add(
                        ModItems.INHELL_HAVEN_DEVICE.get()
                )
                .addTags(
                        PICKAXES,
                        WRENCHES
                );
    }
}
