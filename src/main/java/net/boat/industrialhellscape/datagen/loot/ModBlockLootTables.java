package net.boat.industrialhellscape.datagen.loot;

import net.boat.industrialhellscape.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        //DEBUG BLOCKS
        this.dropSelf(ModBlocks.LARGE_LOCKER.get());
        this.dropSelf(ModBlocks.HANDRAIL.get());

        //IRONLIKE SIMPLE BLOCKS
        this.dropSelf(ModBlocks.VESSELPLATE.get());
        //this.dropSelf(ModBlocks.VESSELPLATE_GRATE_BLOCK.get());
        this.dropSelf(ModBlocks.RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.SMOOTH_VESSELPLATE_TILE.get());
        this.dropSelf(ModBlocks.VESSELPLATE_PILLAR.get());
        this.dropSelf(ModBlocks.GRATE.get());
        this.dropSelf(ModBlocks.SEETHROUGH_GRATE.get());
        //this.dropSelf(ModBlocks.SEETHROUGH_GRATE_PANE.get());
        this.dropSelf(ModBlocks.RUSTY_GRATE.get());
        this.dropSelf(ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.SMOOTH_VESSELPLATE.get());

        this.dropSelf(ModBlocks.REINFORCED_VESSELGLASS.get());
        this.dropSelf(ModBlocks.VESSELGLASS.get());
        this.dropSelf(ModBlocks.GRAY_REINFORCED_VESSELGLASS.get());
        this.dropSelf(ModBlocks.GRAY_VESSELGLASS.get());

        this.dropSelf(ModBlocks.GRAY_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_GRATE.get());
        this.dropSelf(ModBlocks.GRAY_SEETHROUGH_GRATE.get());
        this.dropSelf(ModBlocks.GRAY_SEETHROUGH_GRATE_PANE.get());
        this.dropSelf(ModBlocks.GRAY_VERTICAL_RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_HORIZONTAL_RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.SMOOTH_GRAY_VESSELPLATE.get());
        this.dropSelf(ModBlocks.SMOOTH_GRAY_VESSELPLATE_TILE.get());
        this.dropSelf(ModBlocks.GRAY_VESSELPLATE_PILLAR.get());

        //STONELIKE BLOCKS
        this.dropSelf(ModBlocks.GRAY_ROCKRETE.get());
        this.dropSelf(ModBlocks.GRAY_ROCKRETE_REBAR.get());
        this.dropSelf(ModBlocks.GRAY_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.GRAY_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.GRAY_ROCKRETE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.GRAY_ROCKRETE_SLAB.get())
                );

        this.dropSelf(ModBlocks.GREEN_ROCKRETE.get());
        this.dropSelf(ModBlocks.GREEN_ROCKRETE_REBAR.get());
        this.dropSelf(ModBlocks.GREEN_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.GREEN_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.GREEN_ROCKRETE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.GREEN_ROCKRETE_SLAB.get())
        );

        this.dropSelf(ModBlocks.YELLOW_ROCKRETE.get());
        this.dropSelf(ModBlocks.YELLOW_ROCKRETE_REBAR.get());
        this.dropSelf(ModBlocks.YELLOW_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.YELLOW_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.YELLOW_ROCKRETE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.YELLOW_ROCKRETE_SLAB.get())
        );

        this.dropSelf(ModBlocks.BLUE_ROCKRETE.get());
        this.dropSelf(ModBlocks.BLUE_ROCKRETE_REBAR.get());
        this.dropSelf(ModBlocks.BLUE_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.BLUE_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.BLUE_ROCKRETE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.BLUE_ROCKRETE_SLAB.get())
        );

        this.dropSelf(ModBlocks.HAZARD_STRIPE_YELLOW.get());
        this.dropSelf(ModBlocks.HAZARD_STRIPE_RED.get());
        this.dropSelf(ModBlocks.GRIMY_RESTROOM_TILE.get());

        //SPECIAL BLOCKS
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

        this.dropSelf(ModBlocks.DESK.get());
        this.dropSelf(ModBlocks.DESK_DRAWER.get());
        this.dropSelf(ModBlocks.METAL_DESK.get());
        this.dropSelf(ModBlocks.METAL_DESK_DRAWER.get());
        this.dropSelf(ModBlocks.METAL_DESK_DRAWER_2.get());
        this.dropSelf(ModBlocks.RETRO_COMPUTER.get());
        this.dropSelf(ModBlocks.CASSETTE_PLAYER.get());
        this.dropSelf(ModBlocks.YELLOW_TRIPOD.get());
        this.dropSelf(ModBlocks.WORK_LIGHT_MOUNT.get());
        this.dropSelf(ModBlocks.FLOOR_WORK_LIGHT.get());
        this.dropSelf(ModBlocks.LOCKER_BOX.get());
        this.dropSelf(ModBlocks.LARGE_LOCKER.get());

        this.dropSelf(ModBlocks.PIPEWORKS.get());

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

        this.dropSelf(ModBlocks.GRAY_BOLTED_BRACKET.get());
        this.dropSelf(ModBlocks.BLACK_BOLTED_BRACKET.get());


        this.dropSelf(ModBlocks.VERTICAL_ENCASED_CABLES.get());
        this.dropSelf(ModBlocks.HORIZONTAL_ENCASED_CABLES.get());
        this.dropSelf(ModBlocks.TOILET.get());
        this.dropSelf(ModBlocks.SINK.get());
        this.dropSelf(ModBlocks.RED_WALL_MEDKIT.get());
        this.dropSelf(ModBlocks.WHITE_WALL_MEDKIT.get());

        this.dropSelf(ModBlocks.AMENITY_FURNISHINGS.get());
        this.dropSelf(ModBlocks.INDUSTRIAL_FURNISHINGS.get());
        this.dropSelf(ModBlocks.TECHNOLOGY_FURNISHINGS.get());
        this.dropSelf(ModBlocks.HYGIENE_FURNISHINGS.get());
        this.dropSelf(ModBlocks.SAFETY_FURNISHINGS.get());
        this.dropSelf(ModBlocks.IHEA_FURNITURE_KIT.get());

        //Any Ores are added here too
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
