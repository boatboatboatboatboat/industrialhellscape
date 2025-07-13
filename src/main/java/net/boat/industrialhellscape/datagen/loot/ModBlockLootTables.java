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
        //IRONLIKE BLOCKS
        this.dropSelf(ModBlocks.VESSELPLATE.get());
        this.dropSelf(ModBlocks.VESSELPLATE_GRATE_BLOCK.get());

        //STONELIKE BLOCKS
        this.dropSelf(ModBlocks.GRAY_ROCKRETE.get());
        this.dropSelf(ModBlocks.LIGHT_GRAY_ROCKRETE.get());
        this.dropSelf(ModBlocks.LIGHT_GRAY_ROCKRETE_REBAR.get());
        this.dropSelf(ModBlocks.LIGHT_GRAY_ROCKRETE_STAIRS.get());
        this.add(ModBlocks.LIGHT_GRAY_ROCKRETE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.LIGHT_GRAY_ROCKRETE_SLAB.get())
                );

        this.dropSelf(ModBlocks.WHITE_ROCKRETE.get());
        this.dropSelf(ModBlocks.BLACK_ROCKRETE.get());
        this.dropSelf(ModBlocks.HAZARD_STRIPE_YELLOW.get());
        this.dropSelf(ModBlocks.HAZARD_STRIPE_RED.get());

        //SPECIAL BLOCKS
        this.dropSelf(ModBlocks.BUNKER_WALL.get());
        this.dropSelf(ModBlocks.VESSELPLATE_PILLAR.get());
        this.dropSelf(ModBlocks.SOUND_BLOCK.get());
        this.dropSelf(ModBlocks.VESSELPLATE_GRATE.get());
        this.dropSelf(ModBlocks.RUSTY_VESSELPLATE_GRATE.get());
        this.dropSelf(ModBlocks.VERTICAL_RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.HORIZONTAL_RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.REINFORCED_VESSELGLASS.get());
        this.dropSelf(ModBlocks.VESSELGLASS.get());
        this.dropSelf(ModBlocks.SMOOTH_VESSELPLATE.get());
        this.dropSelf(ModBlocks.SMOOTH_VESSELPLATE_TILE.get());
        this.dropSelf(ModBlocks.DESK.get());
        this.dropSelf(ModBlocks.DESK_DRAWER.get());
        this.dropSelf(ModBlocks.METAL_DESK.get());
        this.dropSelf(ModBlocks.STRUT.get());
        this.dropSelf(ModBlocks.CATWALK_STRUT.get());
        this.dropSelf(ModBlocks.CATWALK_STRUT_STAIRS.get());
        this.dropSelf(ModBlocks.CATWALK_STRUT_SLAB.get());
        this.dropSelf(ModBlocks.STRUT_SLAB.get());
        this.dropSelf(ModBlocks.STRUT_STAIRS.get());
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
