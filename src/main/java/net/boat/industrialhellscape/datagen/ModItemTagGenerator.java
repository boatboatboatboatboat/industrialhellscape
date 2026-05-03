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

import javax.annotation.Nonnull;
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
    protected void addTags(@Nonnull HolderLookup.Provider pProvider) {
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
        this.tag(ModTags.Items.STRUT_SMELTABLE_ITEM)
                .add(
                        ModBlocks.TRUSS.get().asItem(),
                        ModBlocks.TRUSS_STAIRS.get().asItem(),
                        ModBlocks.CATWALK_TRUSS.get().asItem(),
                        ModBlocks.CATWALK_TRUSS_STAIRS.get().asItem(),

                        ModBlocks.GRAY_TRUSS.get().asItem(),
                        ModBlocks.GRAY_TRUSS_STAIRS.get().asItem(),
                        ModBlocks.GRAY_CATWALK_TRUSS.get().asItem(),
                        ModBlocks.GRAY_CATWALK_TRUSS_STAIRS.get().asItem()
                );
        this.tag(ModTags.Items.HVAC_SMELTABLE_ITEM)
                .add(
                        ModBlocks.DUCT.get().asItem(),
                        ModBlocks.RUSTY_DUCT.get().asItem(),
                        ModBlocks.DUCT_VENT.get().asItem(),
                        ModBlocks.RUSTY_DUCT_VENT.get().asItem(),

                        ModBlocks.HORIZONTAL_GRATE.get().asItem(),
                        ModBlocks.VERTICAL_GRATE.get().asItem(),
                        ModBlocks.HORIZONTAL_CUTOUT_GRATE.get().asItem(),
                        ModBlocks.VERTICAL_CUTOUT_GRATE.get().asItem(),

                        ModBlocks.GRAY_HORIZONTAL_GRATE.get().asItem(),
                        ModBlocks.GRAY_VERTICAL_GRATE.get().asItem(),
                        ModBlocks.GRAY_HORIZONTAL_CUTOUT_GRATE.get().asItem(),
                        ModBlocks.GRAY_VERTICAL_CUTOUT_GRATE.get().asItem(),

                        ModBlocks.ENCASED_CABLES.get().asItem()
                );


        this.tag(ModTags.Items.VESSELPLATE_SMELTABLE_ITEM) //NO HALF BLOCKS HERE
                .add(
                //NO HALF BLOCKS HERE
                        //Full BLocks
                        ModBlocks.RIVETED_VESSELPLATE.get().asItem(),
                        ModBlocks.HORIZONTAL_VESSELPLATE.get().asItem(),
                        ModBlocks.VERTICAL_VESSELPLATE.get().asItem(),

                        ModBlocks.VESSELPLATE_PILLAR.get().asItem(),


                        ModBlocks.RIVETED_VESSELPLATE_STAIRS.get().asItem(),

                        ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.get().asItem(),
                        ModBlocks.VERTICAL_REINFORCED_VESSELPLATE.get().asItem(),

                        ModBlocks.SMOOTH_VESSELPLATE.get().asItem(),
                        ModBlocks.SMOOTH_VESSELPLATE_STAIRS.get().asItem(),

                        ModBlocks.GRAY_RIVETED_VESSELPLATE.get().asItem(),

                        ModBlocks.GRAY_HORIZONTAL_REINFORCED_VESSELPLATE.get().asItem(),
                        ModBlocks.GRAY_VERTICAL_REINFORCED_VESSELPLATE.get().asItem(),

                        ModBlocks.SMOOTH_GRAY_VESSELPLATE.get().asItem(),
                        ModBlocks.GRAY_VESSELPLATE_PILLAR.get().asItem(),
                        ModBlocks.GRAY_HORIZONTAL_VESSELPLATE.get().asItem(),
                        ModBlocks.GRAY_VERTICAL_VESSELPLATE.get().asItem(),
                        //ModBlocks.GRAY_VESSELPLATE_PANEL_STAIRS.get().asItem(),

                        ModBlocks.GRAY_RIVETED_VESSELPLATE_STAIRS.get().asItem(),

                        ModBlocks.RUSTY_VESSELPLATE_PILLAR.get().asItem()

                //NO HALF BLOCKS HERE
                );
        this.tag(ModTags.Items.VESSELGLASS_SMELTABLE_ITEM)
                .add(
                //NO HALF BLOCKS HERE
                        ModBlocks.VESSELGLASS.get().asItem(),
                        ModBlocks.REINFORCED_VESSELGLASS.get().asItem(),
                        ModBlocks.GRAY_VESSELGLASS.get().asItem(),
                        ModBlocks.GRAY_REINFORCED_VESSELGLASS.get().asItem()
                //NO HALF BLOCKS HERE
                );
        this.tag(ModTags.Items.ROCKRETE_SMELTABLE_ITEM)
                .add(
                //NO HALF BLOCKS HERE
                        //Full Blocks
                        ModBlocks.HAZARD_STRIPE_RED.get().asItem(),
                        ModBlocks.HAZARD_STRIPE_YELLOW.get().asItem(),
                        ModBlocks.GRIMY_RESTROOM_TILE.get().asItem(),

                        ModBlocks.GRAY_ROCKRETE_PILLAR.get().asItem(),
                        ModBlocks.GRAY_ROCKRETE_STAIRS.get().asItem(),
                        ModBlocks.GRAY_ROCKRETE.get().asItem(),
                        ModBlocks.ROUGH_GRAY_ROCKRETE.get().asItem(),

                        ModBlocks.GREEN_ROCKRETE_PILLAR.get().asItem(),
                        ModBlocks.GREEN_ROCKRETE_STAIRS.get().asItem(),
                        ModBlocks.GREEN_ROCKRETE.get().asItem(),
                        ModBlocks.ROUGH_GREEN_ROCKRETE.get().asItem(),

                        ModBlocks.YELLOW_ROCKRETE_PILLAR.get().asItem(),
                        ModBlocks.YELLOW_ROCKRETE_STAIRS.get().asItem(),
                        ModBlocks.YELLOW_ROCKRETE.get().asItem(),
                        ModBlocks.ROUGH_YELLOW_ROCKRETE.get().asItem(),

                        ModBlocks.BLUE_ROCKRETE_PILLAR.get().asItem(),
                        ModBlocks.BLUE_ROCKRETE_STAIRS.get().asItem(),
                        ModBlocks.BLUE_ROCKRETE.get().asItem(),
                        ModBlocks.ROUGH_BLUE_ROCKRETE.get().asItem(),

                        ModBlocks.RED_ROCKRETE_PILLAR.get().asItem(),
                        ModBlocks.RED_ROCKRETE_STAIRS.get().asItem(),
                        ModBlocks.RED_ROCKRETE.get().asItem(),
                        ModBlocks.ROUGH_RED_ROCKRETE.get().asItem()
                //NO HALF BLOCKS HERE
                );

        this.tag(ModTags.Items.PIPEWORKS_ITEMS)
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
        this.tag(ModTags.Items.METALWORKS_ITEMS)
                .add(
                        ModBlocks.METALWORKS.get().asItem(),

                        ModBlocks.YELLOW_RAILING.get().asItem(),
                        ModBlocks.YELLOW_STAIR_RAILING.get().asItem(),
                        ModBlocks.GRAY_RAILING.get().asItem(),
                        ModBlocks.GRAY_STAIR_RAILING.get().asItem(),
                        ModBlocks.BLACK_RAILING.get().asItem(),
                        ModBlocks.BLACK_STAIR_RAILING.get().asItem(),
                        ModBlocks.RUSTY_RAILING.get().asItem(),
                        ModBlocks.RUSTY_STAIR_RAILING.get().asItem(),

                        ModBlocks.RUSTY_BOLTED_BRACKET.get().asItem(),
                        ModBlocks.BLACK_BOLTED_BRACKET.get().asItem(),
                        ModBlocks.GRAY_BOLTED_BRACKET.get().asItem(),
                        ModBlocks.SMALL_GRAY_BOLTED_BRACKET.get().asItem(),
                        ModBlocks.SMALL_BLACK_BOLTED_BRACKET.get().asItem(),
                        ModBlocks.SMALL_RUSTY_BOLTED_BRACKET.get().asItem()
                );

        this.tag(ModTags.Items.DOOR_ITEMS)
                .add(
                        ModBlocks.VESSELPLATE_DOOR.get().asItem(),
                        ModBlocks.GRAY_VESSELPLATE_DOOR.get().asItem()
                );
        this.tag(ModTags.Items.TRAPDOOR_ITEMS)
                .add(
                        ModBlocks.VESSELPLATE_TRAPDOOR.get().asItem(),
                        ModBlocks.GRAY_VESSELPLATE_TRAPDOOR.get().asItem()
                );
        //---------- END OF RECIPE DATAGEN INPUT TAGS ----------

        //---------- FURNITURE TAGS ----------

        //FURNITURE CATEGORIES BELOW

        this.tag(ModTags.Items.FURNITURE_CATEGORIES)
                .add(
                        ModBlocks.IHEA_FURNITURE_KIT.get().asItem(),
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
                        ModTags.Items.AMENITY_FURNITURE_CATEGORY,
                        ModTags.Items.FURNITURE_CATEGORIES
                );
        this.tag(ModTags.Items.SAFETY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.RED_WALL_MEDKIT.get().asItem(),
                        ModBlocks.WHITE_WALL_MEDKIT.get().asItem(),
                        ModBlocks.FIRE_EXTINGUISHER.get().asItem(),
                        ModBlocks.SMOKE_ALARM.get().asItem(),
                        ModBlocks.OPERATING_TABLE.get().asItem(),
                        ModBlocks.MEDICAL_BED.get().asItem(),
                        ModBlocks.IV_DRIPSTAND.get().asItem(),
                        ModBlocks.VITALS_MONITOR.get().asItem()
                );
        this.tag(ModTags.Items.HYGIENE_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.TOILET.get().asItem(),
                        ModBlocks.SINK.get().asItem(),
                        ModBlocks.URINAL.get().asItem()
                );
        this.tag(ModTags.Items.INDUSTRIAL_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.LOCKER_BOX.get().asItem(),
                        ModBlocks.LARGE_LOCKER.get().asItem(),
                        ModBlocks.WORK_LIGHT_STAND.get().asItem(),
                        ModBlocks.FLOOR_WORK_LIGHT.get().asItem(),
                        ModBlocks.CCTV_CAMERA.get().asItem(),
                        ModBlocks.WALL_SPEAKER.get().asItem()
                );
        this.tag(ModTags.Items.TECHNOLOGY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.RETRO_COMPUTER.get().asItem(),
                        ModBlocks.RETRO_COMPUTER_2.get().asItem(),
                        ModBlocks.MONITOR_AND_KEYBOARD.get().asItem(),
                        ModBlocks.DESKTOP_TOWER.get().asItem(),
                        ModBlocks.CASSETTE_PLAYER.get().asItem()
                );

        this.tag(ModTags.Items.AMENITY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.DESK.get().asItem(),
                        ModBlocks.DESK_DRAWER.get().asItem(),

                        ModBlocks.METAL_DESK.get().asItem(),
                        ModBlocks.METAL_DESK_DRAWER.get().asItem(),
                        ModBlocks.METAL_DESK_DRAWER_2.get().asItem(),

                        ModBlocks.OFFICE_DESK_DRAWER.get().asItem(),
                        ModBlocks.OFFICE_DESK.get().asItem(),

                        ModBlocks.OFFICE_CHAIR.get().asItem(),
                        ModBlocks.BLACK_OFFICE_CHAIR.get().asItem(),
                        ModBlocks.FOLDING_CHAIR.get().asItem()
                );

        //---------- END OF FURNITURE TAGS ----------

        this.tag(ItemTags.MUSIC_DISCS)
                .add(ModItems.VAPORWAVE_CASSETTE.get()
                );


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
                );

        this.tag(ModTags.Items.IH_COMPATIBLE_MODDED_TOOLS)
                .add(
                        ModItems.INHELL_HAVEN_DEVICE.get()
                )
                .addTags(
                        WRENCHES
                );
    }
}
