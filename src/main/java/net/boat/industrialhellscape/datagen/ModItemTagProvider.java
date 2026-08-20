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

    public static final TagKey<Item> TOOLS = TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("c", "tools"));
    public static final TagKey<Item> WRENCH = TagKey.create(BuiltInRegistries.ITEM.key(), ResourceLocation.fromNamespaceAndPath("c", "wrench"));

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //---------- EXTERNAL TAG REGISTRATION ----------
        tag(TOOLS); //Common
        tag(WRENCH); //Common

        this.tag(WRENCH)
                .add(ModItems.INHELL_HAVEN_DEVICE.get()
                );
        this.tag(TOOLS)
                .add(ModItems.INHELL_HAVEN_DEVICE.get()
                );
        //---------- END OF MODLOADER TAG REGISTRATION ----------

        //---------- RECIPE DATAGEN INPUT TAGS ----------
        this.tag(ModTags.Items.HVAC_SMELTABLE_ITEM)
                .add(
                        ModBlocks.DUCT.asItem(),
                        ModBlocks.RUSTY_DUCT.asItem(),

                        ModBlocks.HORIZONTAL_VENT.asItem(),
                        ModBlocks.VERTICAL_VENT.asItem(),
                        ModBlocks.HORIZONTAL_CUTOUT_VENT.asItem(),
                        ModBlocks.VERTICAL_CUTOUT_VENT.asItem(),

                        ModBlocks.RUSTY_HORIZONTAL_VENT.asItem(),
                        ModBlocks.RUSTY_VERTICAL_VENT.asItem(),
                        ModBlocks.RUSTY_HORIZONTAL_CUTOUT_VENT.asItem(),
                        ModBlocks.RUSTY_VERTICAL_CUTOUT_VENT.asItem()
                );
        this.tag(ModTags.Items.METALWORKS_ITEMS)
                .add(
                        ModBlocks.METALWORKS.asItem(),

                        ModBlocks.YELLOW_STAIR_RAILING.asItem(),
                        ModBlocks.YELLOW_RAILING.asItem(),
                        ModBlocks.GRAY_STAIR_RAILING.asItem(),
                        ModBlocks.GRAY_RAILING.asItem(),
                        ModBlocks.BLACK_STAIR_RAILING.asItem(),
                        ModBlocks.BLACK_RAILING.asItem(),
                        ModBlocks.RUSTY_STAIR_RAILING.asItem(),
                        ModBlocks.RUSTY_RAILING.asItem(),

                        ModBlocks.RUSTY_BOLTED_BRACKET.asItem(),
                        ModBlocks.BLACK_BOLTED_BRACKET.asItem(),
                        ModBlocks.GRAY_BOLTED_BRACKET.asItem(),
                        ModBlocks.SMALL_GRAY_BOLTED_BRACKET.asItem(),
                        ModBlocks.SMALL_BLACK_BOLTED_BRACKET.asItem(),
                        ModBlocks.SMALL_RUSTY_BOLTED_BRACKET.asItem()
                );
        this.tag(ModTags.Items.VESSELPLATE_SMELTABLE_ITEM)
                .add(
                        ModBlocks.HORIZONTAL_VESSELPLATE.asItem(),
                        ModBlocks.VERTICAL_VESSELPLATE.asItem(),
                        ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.asItem(),
                        ModBlocks.VERTICAL_REINFORCED_VESSELPLATE.asItem(),
                        ModBlocks.SMOOTH_VESSELPLATE.asItem(),
                        ModBlocks.RIVETED_VESSELPLATE.asItem(),
                        ModBlocks.VESSELPLATE_PILLAR.asItem(),

                        ModBlocks.GRAY_HORIZONTAL_VESSELPLATE.asItem(),
                        ModBlocks.GRAY_VERTICAL_VESSELPLATE.asItem(),
                        ModBlocks.GRAY_HORIZONTAL_REINFORCED_VESSELPLATE.asItem(),
                        ModBlocks.GRAY_VERTICAL_REINFORCED_VESSELPLATE.asItem(),
                        ModBlocks.SMOOTH_GRAY_VESSELPLATE.asItem(),
                        ModBlocks.GRAY_RIVETED_VESSELPLATE.asItem(),
                        ModBlocks.GRAY_VESSELPLATE_PILLAR.asItem(),

                        ModBlocks.RUSTY_HORIZONTAL_VESSELPLATE.asItem(),
                        ModBlocks.RUSTY_VERTICAL_VESSELPLATE.asItem(),
                        ModBlocks.RUSTY_HORIZONTAL_REINFORCED_VESSELPLATE.asItem(),
                        ModBlocks.RUSTY_VERTICAL_REINFORCED_VESSELPLATE.asItem(),
                        ModBlocks.RUSTY_VESSELPLATE_PILLAR.asItem(),

                        ModBlocks.SMOOTH_RUSTY_VESSELPLATE.asItem(),
                        ModBlocks.SMOOTH_RUSTY_VESSELPLATE_SLAB.asItem(),
                        ModBlocks.SMOOTH_RUSTY_VESSELPLATE_STAIRS.asItem(),
                        ModBlocks.RUSTY_RIVETED_VESSELPLATE.asItem(),
                        ModBlocks.RUSTY_RIVETED_VESSELPLATE_SLAB.asItem(),
                        ModBlocks.RUSTY_RIVETED_VESSELPLATE_STAIRS.asItem()
                );
        this.tag(ModTags.Items.TRUSS_SMELTABLE_ITEM)
                .add(
                        ModBlocks.TRUSS_SUPPORT.asItem(),
                        ModBlocks.TRUSS.asItem(),
                        ModBlocks.TRUSS_STAIRS.asItem(),
                        ModBlocks.TRUSS_SLAB.asItem(),

                        ModBlocks.CATWALK_TRUSS.asItem(),
                        ModBlocks.CATWALK_TRUSS_STAIRS.asItem(),
                        ModBlocks.CATWALK_TRUSS_SLAB.asItem(),
                        ModBlocks.GRAY_TRUSS.asItem(),
                        ModBlocks.GRAY_CATWALK_TRUSS.asItem(),
                        ModBlocks.GRAY_CATWALK_TRUSS_STAIRS.asItem(),
                        ModBlocks.GRAY_CATWALK_TRUSS_SLAB.asItem(),
                        ModBlocks.GRAY_TRUSS_STAIRS.asItem(),
                        ModBlocks.GRAY_TRUSS_SLAB.asItem(),

                        ModBlocks.RUSTY_TRUSS.asItem(),
                        ModBlocks.RUSTY_TRUSS_STAIRS.asItem(),
                        ModBlocks.RUSTY_TRUSS_SLAB.asItem()
                );
        this.tag(ModTags.Items.VESSELGLASS_SMELTABLE_ITEM)
                .add(
                        ModBlocks.VESSELGLASS.asItem(),
                        ModBlocks.REINFORCED_VESSELGLASS.asItem(),
                        ModBlocks.GRAY_VESSELGLASS.asItem(),
                        ModBlocks.GRAY_REINFORCED_VESSELGLASS.asItem(),
                        ModBlocks.RUSTY_VESSELGLASS.asItem(),
                        ModBlocks.RUSTY_REINFORCED_VESSELGLASS.asItem()
                );
        this.tag(ModTags.Items.ROCKRETE_SMELTABLE_ITEM)
                .add(
                        ModBlocks.ROUGH_GRAY_ROCKRETE.asItem(),
                        ModBlocks.ROUGH_RED_ROCKRETE.asItem(),
                        ModBlocks.ROUGH_BLUE_ROCKRETE.asItem(),
                        ModBlocks.ROUGH_GREEN_ROCKRETE.asItem(),
                        ModBlocks.ROUGH_YELLOW_ROCKRETE.asItem(),

                        ModBlocks.GRAY_ROCKRETE.asItem(),
                        ModBlocks.GRAY_ROCKRETE_PILLAR.asItem(),

                        ModBlocks.RED_ROCKRETE.asItem(),
                        ModBlocks.RED_ROCKRETE_PILLAR.asItem(),

                        ModBlocks.BLUE_ROCKRETE.asItem(),
                        ModBlocks.BLUE_ROCKRETE_PILLAR.asItem(),

                        ModBlocks.GREEN_ROCKRETE.asItem(),
                        ModBlocks.GREEN_ROCKRETE_PILLAR.asItem(),

                        ModBlocks.YELLOW_ROCKRETE.asItem(),
                        ModBlocks.YELLOW_ROCKRETE_PILLAR.asItem()
                );
        this.tag(ModTags.Items.DOOR_ITEMS)
                .add(
                        ModBlocks.ARMORED_DOOR.asItem(),
                        ModBlocks.STAMPED_METAL_DOOR.asItem(),
                        ModBlocks.BULKHEAD_DOOR.asItem()
                );
        this.tag(ModTags.Items.TRAPDOOR_ITEMS)
                .add(
                        ModBlocks.VENT_TRAPDOOR.asItem(),
                        ModBlocks.RUSTY_VENT_TRAPDOOR.asItem()
                );
        this.tag(ModTags.Items.PIPEWORKS_ITEMS)
                .add(
                        ModBlocks.PIPEWORKS.asItem(),

                        ModBlocks.COPPER_PIPE_CONDUIT.asItem(),
                        ModBlocks.COPPER_PIPE_CONDUIT_PLANAR_CORNER.asItem(),
                        ModBlocks.COPPER_PIPE_CONDUIT_INNER_CORNER.asItem(),
                        ModBlocks.COPPER_PIPE_CONDUIT_OUTER_CORNER.asItem(),

                        ModBlocks.BRASS_PIPE_CONDUIT.asItem(),
                        ModBlocks.BRASS_PIPE_CONDUIT_PLANAR_CORNER.asItem(),
                        ModBlocks.BRASS_PIPE_CONDUIT_INNER_CORNER.asItem(),
                        ModBlocks.BRASS_PIPE_CONDUIT_OUTER_CORNER.asItem(),

                        ModBlocks.GRAY_PIPE_CONDUIT.asItem(),
                        ModBlocks.GRAY_PIPE_CONDUIT_PLANAR_CORNER.asItem(),
                        ModBlocks.GRAY_PIPE_CONDUIT_INNER_CORNER.asItem(),
                        ModBlocks.GRAY_PIPE_CONDUIT_OUTER_CORNER.asItem()
                );

        //FURNITURE CATEGORIES BELOW
        this.tag(ModTags.Items.FURNITURE_CATEGORIES)
                .add(
                        ModBlocks.SAFETY_FURNISHINGS.asItem(),
                        ModBlocks.HYGIENE_FURNISHINGS.asItem(),
                        ModBlocks.INDUSTRIAL_FURNISHINGS.asItem(),
                        ModBlocks.TECHNOLOGY_FURNISHINGS.asItem(),
                        ModBlocks.AMENITY_FURNISHINGS.asItem().asItem()
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
                        ModBlocks.RED_WALL_MEDKIT.asItem(),
                        ModBlocks.WHITE_WALL_MEDKIT.asItem(),
                        ModBlocks.FIRE_EXTINGUISHER.asItem(),
                        ModBlocks.SMOKE_ALARM.asItem(),
                        ModBlocks.OPERATING_TABLE.asItem(),
                        ModBlocks.MEDICAL_BED.asItem(),
                        ModBlocks.IV_DRIPSTAND.asItem(),
                        ModBlocks.VITALS_MONITOR.asItem()
                );
        this.tag(ModTags.Items.HYGIENE_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.TOILET.asItem(),
                        ModBlocks.SINK.asItem(),
                        ModBlocks.URINAL.asItem()
                );
        this.tag(ModTags.Items.INDUSTRIAL_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.LOCKER_BOX.asItem(),
                        ModBlocks.LARGE_LOCKER.asItem(),
                        ModBlocks.WORK_LIGHT_STAND.asItem(),
                        ModBlocks.FLOOR_WORK_LIGHT.asItem(),
                        ModBlocks.CCTV_CAMERA.asItem(),
//                        ModBlocks.INDUSTRIAL_LAMP.asItem(),
                        ModBlocks.OBLONG_CAGE_LAMP.asItem()
                );
        this.tag(ModTags.Items.TECHNOLOGY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.RETRO_COMPUTER.asItem(),
                        ModBlocks.RETRO_COMPUTER_2.asItem(),
                        ModBlocks.MONITOR_AND_KEYBOARD.asItem(),
                        ModBlocks.DESKTOP_TOWER.asItem(),
                        ModBlocks.CASSETTE_PLAYER.asItem()
                );

        this.tag(ModTags.Items.AMENITY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.DESK.asItem(),
                        ModBlocks.DESK_DRAWER.asItem(),

                        ModBlocks.METAL_DESK.asItem(),
                        ModBlocks.METAL_DESK_DRAWER.asItem(),
                        ModBlocks.METAL_DESK_DRAWER_2.asItem(),

                        ModBlocks.OFFICE_DESK_DRAWER.asItem(),
                        ModBlocks.OFFICE_DESK.asItem(),

                        ModBlocks.OFFICE_CHAIR.asItem(),
                        ModBlocks.BLACK_OFFICE_CHAIR.asItem(),
                        ModBlocks.FOLDING_CHAIR.asItem()
                );
        //---------- END OF RECIPE DATAGEN INPUT TAGS ----------

        //---------- MISC TAGS ----------
        this.tag(ModTags.Items.IH_RECIPE_STONES)
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
                        Items.IRON_INGOT
        );

        this.tag(ModTags.Items.BODY_PILLOW_ITEM)
                .add(
                        ModBlocks.BODY_PILLOW_OZY.asItem(),
                        ModBlocks.BODY_PILLOW_FANG.asItem(),
                        ModBlocks.BODY_PILLOW_PROV.asItem()
                );

        this.tag(ModTags.Items.IH_COMPATIBLE_TOOLS)
                .add(
                        ModItems.INHELL_HAVEN_DEVICE.get()
                )
                .addTags(
                        PICKAXES
        );
    }

    //---------- END OF MISC TAGS ----------
}
