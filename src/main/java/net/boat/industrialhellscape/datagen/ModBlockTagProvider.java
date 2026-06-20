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
                        ModBlocks.RUSTY_DUCT.get(),
                        ModBlocks.DUCT_VENT.get(),
                        ModBlocks.RUSTY_DUCT_VENT.get(),

                        ModBlocks.HORIZONTAL_GRATE.get(),
                        ModBlocks.VERTICAL_GRATE.get(),
                        ModBlocks.HORIZONTAL_CUTOUT_GRATE.get(),
                        ModBlocks.VERTICAL_CUTOUT_GRATE.get()
                );
        this.tag(ModTags.Blocks.METALWORKS_BLOCKS)
                .add(
                        ModBlocks.METALWORKS.get(),

                        ModBlocks.YELLOW_STAIR_RAILING.get(),
                        ModBlocks.YELLOW_RAILING.get(),
                        ModBlocks.GRAY_STAIR_RAILING.get(),
                        ModBlocks.GRAY_RAILING.get(),
                        ModBlocks.BLACK_STAIR_RAILING.get(),
                        ModBlocks.BLACK_RAILING.get(),
                        ModBlocks.RUSTY_STAIR_RAILING.get(),
                        ModBlocks.RUSTY_RAILING.get(),

                        ModBlocks.GRAY_BOLTED_BRACKET.get(),
                        ModBlocks.SMALL_GRAY_BOLTED_BRACKET.get(),
                        ModBlocks.BLACK_BOLTED_BRACKET.get(),
                        ModBlocks.SMALL_BLACK_BOLTED_BRACKET.get(),
                        ModBlocks.RUSTY_BOLTED_BRACKET.get(),
                        ModBlocks.SMALL_RUSTY_BOLTED_BRACKET.get()
                );

        this.tag(ModTags.Blocks.VESSELPLATE_BLOCKS)
                .add(
                        ModBlocks.HORIZONTAL_VESSELPLATE.get(),
                        ModBlocks.VERTICAL_VESSELPLATE.get(),
                        ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.get(),
                        ModBlocks.VERTICAL_REINFORCED_VESSELPLATE.get(),
                        ModBlocks.SMOOTH_VESSELPLATE.get(),
                        ModBlocks.RIVETED_VESSELPLATE.get(),
                        ModBlocks.VESSELPLATE_PILLAR.get(),
                        ModBlocks.RIVETED_VESSELPLATE_SLAB.get(),
                        ModBlocks.RIVETED_VESSELPLATE_STAIRS.get(),
                        ModBlocks.SMOOTH_VESSELPLATE_SLAB.get(),
                        ModBlocks.SMOOTH_VESSELPLATE_STAIRS.get(),

                        ModBlocks.GRAY_HORIZONTAL_VESSELPLATE.get(),
                        ModBlocks.GRAY_VERTICAL_VESSELPLATE.get(),
                        ModBlocks.GRAY_HORIZONTAL_REINFORCED_VESSELPLATE.get(),
                        ModBlocks.GRAY_VERTICAL_REINFORCED_VESSELPLATE.get(),
                        ModBlocks.SMOOTH_GRAY_VESSELPLATE.get(),
                        ModBlocks.GRAY_RIVETED_VESSELPLATE.get(),
                        ModBlocks.GRAY_VESSELPLATE_PILLAR.get(),
                        ModBlocks.GRAY_RIVETED_VESSELPLATE_SLAB.get(),
                        ModBlocks.GRAY_RIVETED_VESSELPLATE_STAIRS.get(),
                        ModBlocks.SMOOTH_GRAY_VESSELPLATE_SLAB.get(),
                        ModBlocks.SMOOTH_GRAY_VESSELPLATE_STAIRS.get()
                );
        this.tag(ModTags.Blocks.TRUSS_BLOCKS)
                .add(
                        ModBlocks.TRUSS.get(),
                        ModBlocks.CATWALK_TRUSS.get(),
                        ModBlocks.CATWALK_TRUSS_STAIRS.get(),
                        ModBlocks.CATWALK_TRUSS_SLAB.get(),
                        ModBlocks.TRUSS_STAIRS.get(),
                        ModBlocks.TRUSS_SLAB.get(),
                        ModBlocks.GRAY_TRUSS.get(),
                        ModBlocks.GRAY_CATWALK_TRUSS.get(),
                        ModBlocks.GRAY_CATWALK_TRUSS_STAIRS.get(),
                        ModBlocks.GRAY_CATWALK_TRUSS_SLAB.get(),
                        ModBlocks.GRAY_TRUSS_STAIRS.get(),
                        ModBlocks.GRAY_TRUSS_SLAB.get()
                );
        this.tag(ModTags.Blocks.VESSELGLASS_BLOCKS)
                .add(
                        ModBlocks.VESSELGLASS.get(),
                        ModBlocks.REINFORCED_VESSELGLASS.get(),
                        ModBlocks.GRAY_VESSELGLASS.get(),
                        ModBlocks.GRAY_REINFORCED_VESSELGLASS.get()
                );
        this.tag(ModTags.Blocks.ROCKRETE_BLOCKS)
                .add(
                        ModBlocks.GRAY_ROCKRETE.get(),
                        ModBlocks.ROUGH_GRAY_ROCKRETE.get(),
                        ModBlocks.GRAY_ROCKRETE_PILLAR.get(),
                        ModBlocks.GRAY_ROCKRETE_SLAB.get(),
                        ModBlocks.GRAY_ROCKRETE_PILLAR.get(),
                        ModBlocks.GRAY_ROCKRETE_SLAB.get(),
                        ModBlocks.GRAY_ROCKRETE_STAIRS.get(),
                        ModBlocks.ROUGH_GRAY_ROCKRETE_SLAB.get(),
                        ModBlocks.ROUGH_GRAY_ROCKRETE_STAIRS.get(),

                        ModBlocks.RED_ROCKRETE.get(),
                        ModBlocks.ROUGH_RED_ROCKRETE.get(),
                        ModBlocks.RED_ROCKRETE_PILLAR.get(),
                        ModBlocks.RED_ROCKRETE_SLAB.get(),
                        ModBlocks.RED_ROCKRETE_STAIRS.get(),
                        ModBlocks.ROUGH_RED_ROCKRETE_SLAB.get(),
                        ModBlocks.ROUGH_RED_ROCKRETE_STAIRS.get(),

                        ModBlocks.BLUE_ROCKRETE.get(),
                        ModBlocks.ROUGH_BLUE_ROCKRETE.get(),
                        ModBlocks.BLUE_ROCKRETE_PILLAR.get(),
                        ModBlocks.BLUE_ROCKRETE_SLAB.get(),
                        ModBlocks.BLUE_ROCKRETE_STAIRS.get(),
                        ModBlocks.ROUGH_BLUE_ROCKRETE_SLAB.get(),
                        ModBlocks.ROUGH_BLUE_ROCKRETE_STAIRS.get(),

                        ModBlocks.GREEN_ROCKRETE.get(),
                        ModBlocks.ROUGH_GREEN_ROCKRETE.get(),
                        ModBlocks.GREEN_ROCKRETE_PILLAR.get(),
                        ModBlocks.GREEN_ROCKRETE_SLAB.get(),
                        ModBlocks.GREEN_ROCKRETE_STAIRS.get(),
                        ModBlocks.ROUGH_GREEN_ROCKRETE_SLAB.get(),
                        ModBlocks.ROUGH_GREEN_ROCKRETE_STAIRS.get(),

                        ModBlocks.YELLOW_ROCKRETE.get(),
                        ModBlocks.ROUGH_YELLOW_ROCKRETE.get(),
                        ModBlocks.YELLOW_ROCKRETE_PILLAR.get(),
                        ModBlocks.YELLOW_ROCKRETE_SLAB.get(),
                        ModBlocks.YELLOW_ROCKRETE_STAIRS.get(),
                        ModBlocks.ROUGH_YELLOW_ROCKRETE_SLAB.get(),
                        ModBlocks.ROUGH_YELLOW_ROCKRETE_STAIRS.get(),

                        ModBlocks.GRIMY_RESTROOM_TILE.get()
                );
        this.tag(ModTags.Blocks.DOOR_BLOCKS) //FOR CORRECT MINING TOOLS AND TOOL TIER
                .add(
                        ModBlocks.ARMORED_DOOR.get(),
                        ModBlocks.STAMPED_METAL_DOOR.get(),
                        ModBlocks.BULKHEAD_DOOR.get()
                );
        this.tag(ModTags.Blocks.TRAPDOOR_BLOCKS) //FOR CORRECT MINING TOOLS AND TOOL TIER
                .add(
                        //ModBlocks.VESSELPLATE_TRAPDOOR.get(),
                        ModBlocks.VENT_TRAPDOOR.get()
                );
        this.tag(ModTags.Blocks.ALL_FURNITURE_BLOCKS)
                .addTags(
                        ModTags.Blocks.AMENITY_FURNITURE_CATEGORY,
                        ModTags.Blocks.HYGIENE_FURNITURE_CATEGORY,
                        ModTags.Blocks.INDUSTRIAL_FURNITURE_CATEGORY,
                        ModTags.Blocks.TECHNOLOGY_FURNITURE_CATEGORY,
                        ModTags.Blocks.SAFETY_FURNITURE_CATEGORY
                )
                .add(
                        ModBlocks.IHEA_FURNITURE_KIT.get()
                );
        this.tag(ModTags.Blocks.SAFETY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.SAFETY_FURNISHINGS.get(),
                        ModBlocks.RED_WALL_MEDKIT.get(),
                        ModBlocks.WHITE_WALL_MEDKIT.get(),
                        ModBlocks.FIRE_EXTINGUISHER.get(),
                        ModBlocks.SMOKE_ALARM.get(),
                        ModBlocks.OPERATING_TABLE.get(),
                        ModBlocks.MEDICAL_BED.get(),
                        ModBlocks.IV_DRIPSTAND.get(),
                        ModBlocks.VITALS_MONITOR.get()
                );
        this.tag(ModTags.Blocks.HYGIENE_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.HYGIENE_FURNISHINGS.get(),
                        ModBlocks.TOILET.get(),
                        ModBlocks.SINK.get(),
                        ModBlocks.URINAL.get()
                );

        this.tag(ModTags.Blocks.INDUSTRIAL_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.INDUSTRIAL_FURNISHINGS.get(),
                        ModBlocks.LOCKER_BOX.get(),
                        ModBlocks.LARGE_LOCKER.get(),
                        ModBlocks.WORK_LIGHT_STAND.get(),
                        ModBlocks.FLOOR_WORK_LIGHT.get(),
                        ModBlocks.FUEL_DRUM.get(),
                        ModBlocks.CCTV_CAMERA.get()
                        //ModBlocks.CAGE_LAMP.get()
                );

        this.tag(ModTags.Blocks.TECHNOLOGY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.TECHNOLOGY_FURNISHINGS.get(),
                        ModBlocks.RETRO_COMPUTER.get(),
                        ModBlocks.RETRO_COMPUTER_2.get(),
                        ModBlocks.MONITOR_AND_KEYBOARD.get(),
                        ModBlocks.DESKTOP_TOWER.get(),
                        ModBlocks.CASSETTE_PLAYER.get()
                );
        this.tag(ModTags.Blocks.AMENITY_FURNITURE_CATEGORY)
                .add(
                        ModBlocks.AMENITY_FURNISHINGS.get(),

                        ModBlocks.DESK.get(),
                        ModBlocks.DESK_DRAWER.get(),

                        ModBlocks.METAL_DESK.get(),
                        ModBlocks.METAL_DESK_DRAWER.get(),
                        ModBlocks.METAL_DESK_DRAWER_2.get(),

                        ModBlocks.OFFICE_DESK.get(),
                        ModBlocks.OFFICE_DESK_DRAWER.get(),

                        ModBlocks.OFFICE_CHAIR.get(),
                        ModBlocks.BLACK_OFFICE_CHAIR.get(),
                        ModBlocks.FOLDING_CHAIR.get()
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
        this.tag(ModTags.Blocks.OFFICE_DESK)
                .add(
                        ModBlocks.OFFICE_DESK.get(),
                        ModBlocks.OFFICE_DESK_DRAWER.get()
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
        this.tag(ModTags.Blocks.PIPEWORKS_BLOCKS) //FOR CORRECT MINING TOOLS AND TOOL TIER
                .add(ModBlocks.PIPEWORKS.get())
                .addTags(
                        ModTags.Blocks.COPPER_PIPE_CONDUIT,
                        ModTags.Blocks.BRASS_PIPE_CONDUIT,
                        ModTags.Blocks.GRAY_PIPE_CONDUIT
                );

        this.tag(ModTags.Blocks.STORAGE_BLOCKS)
                .add(
                        ModBlocks.OFFICE_DESK_DRAWER.get(),
                        ModBlocks.DESK_DRAWER.get(),
                        ModBlocks.METAL_DESK_DRAWER.get(),
                        ModBlocks.METAL_DESK_DRAWER_2.get(),
                        ModBlocks.LOCKER_BOX.get(),
                        ModBlocks.LARGE_LOCKER.get(),
                        ModBlocks.FUEL_DRUM.get()

                );

        this.tag(BlockTags.NEEDS_STONE_TOOL) //When specific tool tiers are required
                .addTags(
                        ModTags.Blocks.VESSELPLATE_BLOCKS,
                        ModTags.Blocks.ROCKRETE_BLOCKS,

                        ModTags.Blocks.TRUSS_BLOCKS,
                        ModTags.Blocks.HVAC_BLOCKS,

                        ModTags.Blocks.PIPEWORKS_BLOCKS,
                        ModTags.Blocks.METALWORKS_BLOCKS,

                        ModTags.Blocks.DOOR_BLOCKS,
                        ModTags.Blocks.TRAPDOOR_BLOCKS,

                        ModTags.Blocks.ALL_FURNITURE_BLOCKS
                );
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .addTags(
                        ModTags.Blocks.ROCKRETE_BLOCKS,
                        ModTags.Blocks.VESSELPLATE_BLOCKS,
                        ModTags.Blocks.VESSELGLASS_BLOCKS,
                        ModTags.Blocks.TRUSS_BLOCKS,
                        ModTags.Blocks.HVAC_BLOCKS,

                        ModTags.Blocks.PIPEWORKS_BLOCKS,
                        ModTags.Blocks.METALWORKS_BLOCKS,

                        ModTags.Blocks.DOOR_BLOCKS,
                        ModTags.Blocks.TRAPDOOR_BLOCKS,

                        ModTags.Blocks.ALL_FURNITURE_BLOCKS //All furniture can be mined with axe or pickaxe.
                );
        this.tag(BlockTags.MINEABLE_WITH_AXE)
                .addTags(
                        ModTags.Blocks.ALL_FURNITURE_BLOCKS //All furniture can be mined with axe or pickaxe.
                );

        //INTER-MOD COMPAT
        this.tag(ModTags.Blocks.MOD_CREATE_FAN_TRANSPARENT)
                .addTags(
                        ModTags.Blocks.TRUSS_BLOCKS
                );

        this.tag(ModTags.Blocks.MOD_CREATE_SIMPLE_MOUNTED_STORAGE)
                .addTags(
                        ModTags.Blocks.STORAGE_BLOCKS
                );

        this.tag(ModTags.Blocks.MOD_SABLE_MASS_SUPER_LIGHT)
                .add(ModBlocks.BODY_PILLOW.get()
                );
        this.tag(ModTags.Blocks.MOD_SABLE_MASS_LIGHT)
                .addTags(
                        ModTags.Blocks.METALWORKS_BLOCKS,
                        ModTags.Blocks.PIPEWORKS_BLOCKS,

                        ModTags.Blocks.HVAC_BLOCKS,
                        ModTags.Blocks.TRUSS_BLOCKS,

                        ModTags.Blocks.ALL_FURNITURE_BLOCKS
                );
        this.tag(ModTags.Blocks.MOD_SABLE_MASS_NORMAL)
                .addTags(
                        ModTags.Blocks.VESSELPLATE_BLOCKS,
                        ModTags.Blocks.VESSELGLASS_BLOCKS
                );
        this.tag(ModTags.Blocks.MOD_SABLE_MASS_HEAVY)
                .addTags(
                        ModTags.Blocks.ROCKRETE_BLOCKS
                        );

        this.tag(ModTags.Blocks.MOD_SABLE_VOLUME_HALF)
                .addTags(
                        ModTags.Blocks.ALL_FURNITURE_BLOCKS
                );
        this.tag(ModTags.Blocks.MOD_SABLE_VOLUME_QUARTER)
                .addTags(
                        ModTags.Blocks.METALWORKS_BLOCKS,
                        ModTags.Blocks.PIPEWORKS_BLOCKS,
                        ModTags.Blocks.DOOR_BLOCKS,
                        ModTags.Blocks.TRAPDOOR_BLOCKS,
                        ModTags.Blocks.TRUSS_BLOCKS
                );
    }
}
