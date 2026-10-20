package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public class BrushingLootTableProvider implements LootTableSubProvider {
    public BrushingLootTableProvider(HolderLookup.Provider lookupProvider) {
        super(/*lookupProvider*/);
    }
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        consumer.accept(
                ResourceKey.create(
                        Registries.LOOT_TABLE,
                        ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, "archaeology/very_suspicious_sand")
                ),
                LootTable.lootTable()
                        // Add a loot table-level loot function. This example uses a number provider (see below).
                        //.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
                        // Add a loot pool.
                        .withPool(LootPool.lootPool()
                                // Add a loot pool-level function, similar to above.
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
        // Add a loot pool-level condition. This example only rolls the pool if it is raining.
                        //.when(WeatherCheck.weather().setRaining(true))
                // Set the amount of rolls and bonus rolls, respectively.
                // Both of these methods utilize a number provider.
                .setRolls(ConstantValue.exactly(1))
                //.setBonusRolls(ConstantValue.exactly(1))
                // Add a loot entry. This example returns an item loot entry. See below for more loot entries.
                .add(LootItem.lootTableItem(ModItems.GAS_STATION_PILL.get()))
                )
        );
    }
}
