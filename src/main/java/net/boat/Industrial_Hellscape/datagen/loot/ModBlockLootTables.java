package net.boat.Industrial_Hellscape.datagen.loot;

import net.boat.Industrial_Hellscape.block.ModBlocks;
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
        this.dropSelf(ModBlocks.HAZARD_STRIPE_YELLOW.get());
        this.dropSelf(ModBlocks.HAZARD_STRIPE_RED.get());

        //SPECIAL BLOCKS
        this.dropSelf(ModBlocks.SOUND_BLOCK.get());

        //Any Ores are added here too
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
