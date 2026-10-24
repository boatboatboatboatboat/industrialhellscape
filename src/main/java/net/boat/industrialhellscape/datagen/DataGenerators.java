package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.experiments.LandMineBlocks.BrushingLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = IndustrialHellscape.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(
                event.includeServer(),
                new LootTableProvider(packOutput,
                    Collections.emptySet(),
                    List.of(
                            //new loot table variants are added to this list
                            new LootTableProvider.SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK),
                            new LootTableProvider.SubProviderEntry(BrushingLootTableProvider::new, LootContextParamSets.ARCHAEOLOGY)
                    ),
                    lookupProvider
                )
        );

        generator.addProvider(
                event.includeServer(),
                new ModRecipeProvider(
                        packOutput,
                        lookupProvider
                )
        );

        //blockTagsProvider; Used for both block tag and item tag generators for some reason
        BlockTagsProvider blockTagsProvider = new ModBlockTagProvider(packOutput, lookupProvider, existingFileHelper);

        generator.addProvider(
                event.includeServer(),
                blockTagsProvider
        );

        generator.addProvider(
                event.includeServer(),
                new ModItemTagProvider(
                        packOutput,
                        lookupProvider,
                        blockTagsProvider.contentsGetter(),
                        existingFileHelper
                )
        );

        //DataMap
        //generator.addProvider(event.includeServer(), new ModDataMapProvider(packOutput, lookupProvider));

        generator.addProvider(
                event.includeClient(),
                new ModItemModelProvider(
                        packOutput,
                        existingFileHelper
                )
        );

        generator.addProvider(
                event.includeClient(),
                new ModBlockStateProvider(
                        packOutput, existingFileHelper
                )
        );
    }
}
