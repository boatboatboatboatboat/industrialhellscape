package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.DUCT.get());
        dropSelf(ModBlocks.RUSTY_DUCT.get());
    }
/*
    protected LootTable.Builder createRailingDrops(Block pBlock) {
        //It turns out, .withPool()s can be stacked together to form separate loot pools.
        //For example, the vanilla function createPotFlowerItemTable() in BlockLootSubProvider

        return LootTable.lootTable()

                //Separate Loot pool for NORTH_FENCE blockstate (0-1 item output)
                .withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(RailingBlock.NORTH_FENCE, true))) //when true
                        .add(this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pBlock)
                                        .apply(List.of(1), (p_249985_) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) p_249985_))))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))

                //Separate Loot pool for SOUTH_FENCE blockstate (0-1 item output)
                .withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(RailingBlock.SOUTH_FENCE, true))) //when true
                        .add(this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pBlock)
                                        .apply(List.of(1), (p_249985_) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) p_249985_))))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))

                //Separate Loot pool for EAST_FENCE blockstate (0-1 item output)
                .withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(RailingBlock.EAST_FENCE, true))) //when true
                        .add(this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pBlock)
                                        .apply(List.of(1), (p_249985_) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) p_249985_))))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))

                //Separate Loot pool for WEST_FENCE blockstate (0-1 item output)
                .withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(RailingBlock.WEST_FENCE, true))) //when true
                        .add(this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pBlock)
                                        .apply(List.of(1), (p_249985_) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) p_249985_))))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))

                ;
    }

    protected LootTable.Builder createStairRailingDrops(Block pBlock) {
        return LootTable.lootTable()

                //Separate Loot pool for RIGHT_FENCE blockstate (0-1 item output)
                .withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(StairRailingBlock.LEFT_FENCE, true))) //when true
                        .add(this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pBlock)
                                        .apply(List.of(1), (p_249985_) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) p_249985_))))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))

                //Separate Loot pool for LEFT_FENCE blockstate (0-1 item output)
                .withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(StairRailingBlock.RIGHT_FENCE, true))) //when true
                        .add(this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pBlock)
                                        .apply(List.of(1), (p_249985_) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) p_249985_))))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                ;
    }

    protected LootTable.Builder createMultiBlockDrops(Block pBlock) {
        //It turns out, .withPool()s can be stacked together to form separate loot pools.
        //For example, the vanilla function createPotFlowerItemTable() in BlockLootSubProvider

        return LootTable.lootTable()

                .withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(TwoBlockMultiBlock.HALF_PART, TwoBlockMultiBlockState.NEGATIVE))) //when true
                        .add(this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pBlock)
                                        .apply(List.of(1), (p_249985_) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) p_249985_))))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                ;
    }
    */
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
