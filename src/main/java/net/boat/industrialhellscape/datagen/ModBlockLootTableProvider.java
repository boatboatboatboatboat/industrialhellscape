package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.block_classes.MultiBlocks.BaseIntegerMultiBlock;
import net.boat.industrialhellscape.block.block_classes.MultiBlocks.Integer11Blocks.Integer11Block;
import net.boat.industrialhellscape.block.block_classes.MultiBlocks.Integer2Blocks.Integer2Block;
import net.boat.industrialhellscape.block.block_classes.RailingBlocks.RailingBlock;
import net.boat.industrialhellscape.block.block_classes.RailingBlocks.StairRailingBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    protected void generateLootTableFromListOfBlocks(List<Block> listOfBlocks) {
        for(Block blockInIndice: listOfBlocks) {
            switch (blockInIndice) {
                case SlabBlock ignored -> {
                    this.add(blockInIndice, createSlabItemTable(blockInIndice));
                }
                case RailingBlock ignored -> {
                    this.add(blockInIndice, createRailingDrops(blockInIndice));
                }
                case StairRailingBlock ignored -> {
                    this.add(blockInIndice, createStairRailingDrops(blockInIndice));
                }
                case BaseIntegerMultiBlock registeredMultiBlockInstance -> {
                    this.add(blockInIndice, createIntegerMultiBlockDrops(blockInIndice, registeredMultiBlockInstance.partProperty));
                }
                case DoorBlock ignored -> {
                    this.add(blockInIndice, createDoorTable(blockInIndice));
                }
                 default -> {
                    this.dropSelf(blockInIndice);
                }
            }
        }
    }

    @Override
    protected void generate() {

        //DEBUG BLOCK
        this.add(ModBlocks.DEBUG_BLOCK.get(),
                createIntegerMultiBlockDrops(ModBlocks.DEBUG_BLOCK.get(), Integer11Block.PART));

        //IN-DEVELOPMENT BLOCK
//        this.dropWhenSilkTouch(ModBlocks.VERY_SUSPICIOUS_SAND.get());
//        this.dropSelf(ModBlocks.LAND_MINE.get());

        //JOKE BLOCK
        this.add(ModBlocks.BODY_PILLOW_OZY.get(),
                createIntegerMultiBlockDrops(ModBlocks.BODY_PILLOW_OZY.get(), Integer2Block.PART));
        this.add(ModBlocks.BODY_PILLOW_FANG.get(),
                createIntegerMultiBlockDrops(ModBlocks.BODY_PILLOW_FANG.get(),Integer2Block.PART));
        this.add(ModBlocks.BODY_PILLOW_PROV.get(),
                createIntegerMultiBlockDrops(ModBlocks.BODY_PILLOW_PROV.get(),Integer2Block.PART));
        this.add(ModBlocks.PEANUT_STATUE.get(),
                createIntegerMultiBlockDrops(ModBlocks.PEANUT_STATUE.get(), Integer2Block.PART));

        // Main Blockset
        generateLootTableFromListOfBlocks(ModRecipeProvider.VESSELPLATE_STONECUT_OUTPUT);
        generateLootTableFromListOfBlocks(ModRecipeProvider.ROCKRETE_STONECUT_OUTPUT);
        generateLootTableFromListOfBlocks(ModRecipeProvider.VESSELGLASS_STONECUT_OUTPUT);

        // Structural
        generateLootTableFromListOfBlocks(ModRecipeProvider.TRUSS_STONECUT_OUTPUT);
        generateLootTableFromListOfBlocks(ModRecipeProvider.HVAC_STONECUT_OUTPUT);

        // The Works
        generateLootTableFromListOfBlocks(ModRecipeProvider.METALWORKS_STONECUT_OUTPUT);
        generateLootTableFromListOfBlocks(ModRecipeProvider.PIPEWORKS_STONECUT_OUTPUT);

        // Doors
        generateLootTableFromListOfBlocks(ModRecipeProvider.DOORS_STONECUT_OUTPUT);
        generateLootTableFromListOfBlocks(ModRecipeProvider.TRAPDOORS_STONECUT_OUTPUT);

        // Furniture
        this.dropSelf(ModBlocks.IHEA_FURNITURE_KIT.get());
        generateLootTableFromListOfBlocks(ModRecipeProvider.FURNITURE_CATEGORY_STONECUT_OUTPUT);
        generateLootTableFromListOfBlocks(ModRecipeProvider.SAFETY_FURNITURE_STONECUT_OUTPUT);
        generateLootTableFromListOfBlocks(ModRecipeProvider.HYGIENE_FURNITURE_STONECUT_OUTPUT);
        generateLootTableFromListOfBlocks(ModRecipeProvider.INDUSTRIAL_FURNITURE_STONECUT_OUTPUT);
        generateLootTableFromListOfBlocks(ModRecipeProvider.TECHNOLOGY_FURNITURE_STONECUT_OUTPUT);
        generateLootTableFromListOfBlocks(ModRecipeProvider.AMENITY_FURNITURE_STONECUT_OUTPUT);

        // Vertical Vesselplate Variants not in stone-cutting recipe output lists
        this.dropSelf(ModBlocks.VERTICAL_VESSELPLATE.get());
        this.dropSelf(ModBlocks.VERTICAL_REINFORCED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_VERTICAL_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_VERTICAL_REINFORCED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.RUSTY_VERTICAL_VESSELPLATE.get());
        this.dropSelf(ModBlocks.RUSTY_VERTICAL_REINFORCED_VESSELPLATE.get());

        // Vertical Vent Variants not in stone-cutting recipe output lists
        this.dropSelf(ModBlocks.VERTICAL_VENT.get());
        this.dropSelf(ModBlocks.VERTICAL_CUTOUT_VENT.get());
        this.dropSelf(ModBlocks.RUSTY_VERTICAL_VENT.get());
        this.dropSelf(ModBlocks.RUSTY_VERTICAL_CUTOUT_VENT.get());
    }

    protected LootTable.Builder createRailingDrops(Block block) {
        return dropOnBooleanPropertyTrue(block, RailingBlock.NORTH_FENCE, RailingBlock.SOUTH_FENCE, RailingBlock.EAST_FENCE, RailingBlock.WEST_FENCE);
    }

    protected LootTable.Builder createPanelBlockDrops(Block block) {
        return dropOnBooleanPropertyTrue(block, BlockStateProperties.NORTH, BlockStateProperties.SOUTH, BlockStateProperties.EAST, BlockStateProperties.WEST);
    }

    protected LootTable.Builder createStairRailingDrops(Block block) {
        return dropOnBooleanPropertyTrue(block, StairRailingBlock.LEFT_FENCE, StairRailingBlock.RIGHT_FENCE);
    }

    protected LootTable.Builder createIntegerMultiBlockDrops(Block block, IntegerProperty integerProperty) {
        //Only the first block of the multiblock (PART #0) has a loot table to drop the block itself
        return LootTable.lootTable().withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(integerProperty, 0))) //PART #0
                        .add(this.applyExplosionDecay(block, LootItem.lootTableItem(block)
                                        .apply(List.of(1), (p_249985_) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) p_249985_))))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                ;
    }

    protected LootTable.Builder dropOnBooleanPropertyTrue(Block block, BooleanProperty... booleanProperty) {
        LootTable.Builder builder = LootTable.lootTable();

        for(BooleanProperty currentProperty:booleanProperty) {
            builder = builder.withPool(LootPool.lootPool()
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(currentProperty, true))) //when true
                    .add(this.applyExplosionDecay(block, LootItem.lootTableItem(block)
                                    .apply(List.of(1), (p_249985_) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) p_249985_))))
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))));
        }

        return builder;
    };

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
