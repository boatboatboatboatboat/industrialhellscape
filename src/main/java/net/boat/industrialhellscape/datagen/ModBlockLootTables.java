package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.*;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

//THIS JAVA CLASS HANDLES BLOCK's ITEM DROP BEHAVIOR WHEN MINED, DATA-GENERATING LOOT DROP TABLES

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        //DEBUG BLOCKS
        this.dropSelf(ModBlocks.HANDRAIL.get());

        //JOKE BLOCKS
        this.dropSelf(ModBlocks.BODY_PILLOW.get());

        //IRONLIKE SIMPLE BLOCKS
        this.dropSelf(ModBlocks.VESSELPLATE.get());
        this.dropSelf(ModBlocks.RIVETED_VESSELPLATE_PANEL.get());
        this.dropSelf(ModBlocks.GRATE.get());
        this.dropSelf(ModBlocks.SEETHROUGH_GRATE.get());
        this.dropSelf(ModBlocks.RUSTY_GRATE.get());
        this.dropSelf(ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.SMOOTH_VESSELPLATE_TILE.get());
        this.dropSelf(ModBlocks.VESSELPLATE_PILLAR.get());
        this.dropSelf(ModBlocks.VESSELPLATE_STAIRS.get());
        this.dropSelf(ModBlocks.VESSELPLATE_SLAB.get());

        this.dropSelf(ModBlocks.REINFORCED_VESSELGLASS.get());
        this.dropSelf(ModBlocks.VESSELGLASS.get());
        this.dropSelf(ModBlocks.GRAY_REINFORCED_VESSELGLASS.get());
        this.dropSelf(ModBlocks.GRAY_VESSELGLASS.get());

        this.dropSelf(ModBlocks.GRAY_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_RIVETED_VESSELPLATE_PANEL.get());
        this.dropSelf(ModBlocks.GRAY_GRATE.get());
        this.dropSelf(ModBlocks.GRAY_SEETHROUGH_GRATE.get());
        this.dropSelf(ModBlocks.GRAY_SEETHROUGH_GRATE_PANE.get());
        this.dropSelf(ModBlocks.GRAY_VERTICAL_RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_HORIZONTAL_RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.SMOOTH_GRAY_VESSELPLATE_TILE.get());
        this.dropSelf(ModBlocks.GRAY_VESSELPLATE_PILLAR.get());
        this.dropSelf(ModBlocks.GRAY_VESSELPLATE_STAIRS.get());
        this.dropSelf(ModBlocks.GRAY_VESSELPLATE_SLAB.get());

        this.dropSelf(ModBlocks.STRUT.get());
        this.dropSelf(ModBlocks.CATWALK_STRUT.get());
        this.dropSelf(ModBlocks.CATWALK_STRUT_STAIRS.get());
        this.dropSelf(ModBlocks.CATWALK_STRUT_SLAB.get());
        this.dropSelf(ModBlocks.STRUT_SLAB.get());
        this.dropSelf(ModBlocks.STRUT_STAIRS.get());

        this.dropSelf(ModBlocks.GRAY_STRUT.get());
        this.dropSelf(ModBlocks.GRAY_CATWALK_STRUT.get());
        this.dropSelf(ModBlocks.GRAY_CATWALK_STRUT_STAIRS.get());
        this.dropSelf(ModBlocks.GRAY_CATWALK_STRUT_SLAB.get());
        this.dropSelf(ModBlocks.GRAY_STRUT_SLAB.get());
        this.dropSelf(ModBlocks.GRAY_STRUT_STAIRS.get());

        this.dropSelf(ModBlocks.ENCASED_CABLES.get());

        //STONELIKE BLOCKS
        this.dropSelf(ModBlocks.GRAY_ROCKRETE.get());
        this.dropSelf(ModBlocks.GRAY_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.GRAY_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.GRAY_ROCKRETE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.GRAY_ROCKRETE_SLAB.get())
                );

        this.dropSelf(ModBlocks.GREEN_ROCKRETE.get());
        this.dropSelf(ModBlocks.GREEN_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.GREEN_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.GREEN_ROCKRETE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.GREEN_ROCKRETE_SLAB.get())
        );

        this.dropSelf(ModBlocks.YELLOW_ROCKRETE.get());
        this.dropSelf(ModBlocks.YELLOW_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.YELLOW_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.YELLOW_ROCKRETE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.YELLOW_ROCKRETE_SLAB.get())
        );

        this.dropSelf(ModBlocks.BLUE_ROCKRETE.get());
        this.dropSelf(ModBlocks.BLUE_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.BLUE_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.BLUE_ROCKRETE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.BLUE_ROCKRETE_SLAB.get())
        );

        this.dropSelf(ModBlocks.HAZARD_STRIPE_YELLOW.get());
        this.dropSelf(ModBlocks.HAZARD_STRIPE_RED.get());
        this.dropSelf(ModBlocks.GRIMY_RESTROOM_TILE.get());

        //PIPEWORKS BLOCKS
        this.dropSelf(ModBlocks.PIPEWORKS.get());

        this.dropSelf(ModBlocks.COPPER_PIPE_CONDUIT.get());
        this.dropSelf(ModBlocks.COPPER_PIPE_CONDUIT_PLANAR_CORNER.get());
        this.dropSelf(ModBlocks.COPPER_PIPE_CONDUIT_INNER_CORNER.get());
        this.dropSelf(ModBlocks.COPPER_PIPE_CONDUIT_OUTER_CORNER.get());

        this.dropSelf(ModBlocks.BRASS_PIPE_CONDUIT.get());
        this.dropSelf(ModBlocks.BRASS_PIPE_CONDUIT_PLANAR_CORNER.get());
        this.dropSelf(ModBlocks.BRASS_PIPE_CONDUIT_INNER_CORNER.get());
        this.dropSelf(ModBlocks.BRASS_PIPE_CONDUIT_OUTER_CORNER.get());

        this.dropSelf(ModBlocks.GRAY_PIPE_CONDUIT.get());
        this.dropSelf(ModBlocks.GRAY_PIPE_CONDUIT_PLANAR_CORNER.get());
        this.dropSelf(ModBlocks.GRAY_PIPE_CONDUIT_INNER_CORNER.get());
        this.dropSelf(ModBlocks.GRAY_PIPE_CONDUIT_OUTER_CORNER.get());

        //METALWORKS BLOCKS
        this.dropSelf(ModBlocks.METALWORKS.get());
        this.dropSelf(ModBlocks.YELLOW_STAIR_RAILING.get());
        this.dropSelf(ModBlocks.YELLOW_RAILING.get());
        this.dropSelf(ModBlocks.GRAY_BOLTED_BRACKET.get());
        this.dropSelf(ModBlocks.BLACK_BOLTED_BRACKET.get());

        //FURNITURE BLOCKS
        this.dropSelf(ModBlocks.RED_WALL_MEDKIT.get());
        this.dropSelf(ModBlocks.WHITE_WALL_MEDKIT.get());
        this.dropSelf(ModBlocks.FIRE_EXTINGUISHER.get());
        this.dropSelf(ModBlocks.SMOKE_ALARM.get());

        this.dropSelf(ModBlocks.TOILET.get());
        this.dropSelf(ModBlocks.SINK.get());
        this.dropSelf(ModBlocks.URINAL.get());

        this.dropSelf(ModBlocks.LOCKER_BOX.get());
        this.dropSelf(ModBlocks.LARGE_LOCKER.get());
        this.dropSelf(ModBlocks.YELLOW_TRIPOD.get());
        this.dropSelf(ModBlocks.WORK_LIGHT_MOUNT.get());
        this.dropSelf(ModBlocks.FLOOR_WORK_LIGHT.get());
        this.dropSelf(ModBlocks.FUEL_DRUM.get());

        this.dropSelf(ModBlocks.CASSETTE_PLAYER.get());
        this.dropSelf(ModBlocks.RETRO_COMPUTER.get());
        this.dropSelf(ModBlocks.RETRO_COMPUTER_2.get());

        this.dropSelf(ModBlocks.DESK.get());
        this.dropSelf(ModBlocks.DESK_DRAWER.get());
        this.dropSelf(ModBlocks.METAL_DESK.get());
        this.dropSelf(ModBlocks.METAL_DESK_DRAWER.get());
        this.dropSelf(ModBlocks.METAL_DESK_DRAWER_2.get());

        this.dropSelf(ModBlocks.OFFICE_DESK_DRAWER.get());

        //FURNITURE CATEGORIES
        this.dropSelf(ModBlocks.AMENITY_FURNISHINGS.get());
        this.dropSelf(ModBlocks.INDUSTRIAL_FURNISHINGS.get());
        this.dropSelf(ModBlocks.TECHNOLOGY_FURNISHINGS.get());
        this.dropSelf(ModBlocks.HYGIENE_FURNISHINGS.get());
        this.dropSelf(ModBlocks.SAFETY_FURNISHINGS.get());
        this.dropSelf(ModBlocks.IHEA_FURNITURE_KIT.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
