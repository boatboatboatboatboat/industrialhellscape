package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.IntegerMultiBlock;
import net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks.RailingBlock;
import net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks.StairRailingBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.List;
import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {

        //DEBUG BLOCK
        //this.dropSelf(ModBlocks.REMOVE_THIS_ITEM.get());
        this.add(ModBlocks.DEBUG_BLOCK.get(),
                createIntegerMultiBlockDrops(ModBlocks.DEBUG_BLOCK.get()));

        //JOKE BLOCK
        this.add(ModBlocks.BODY_PILLOW_OZY.get(),
                createIntegerMultiBlockDrops(ModBlocks.BODY_PILLOW_OZY.get()));
        this.add(ModBlocks.BODY_PILLOW_FANG.get(),
                createIntegerMultiBlockDrops(ModBlocks.BODY_PILLOW_FANG.get()));

        //BASE BUILDING BLOCKS
        this.dropSelf(ModBlocks.METALWORKS.get());
        this.dropSelf(ModBlocks.PIPEWORKS.get());
        this.dropSelf(ModBlocks.IHEA_FURNITURE_KIT.get());
        this.dropSelf(ModBlocks.AMENITY_FURNISHINGS.get());
        this.dropSelf(ModBlocks.INDUSTRIAL_FURNISHINGS.get());
        this.dropSelf(ModBlocks.TECHNOLOGY_FURNISHINGS.get());
        this.dropSelf(ModBlocks.HYGIENE_FURNISHINGS.get());
        this.dropSelf(ModBlocks.SAFETY_FURNISHINGS.get());
        this.dropSelf(ModBlocks.IHEA_FURNITURE_KIT.get());

        //RAILING BLOCKS
        this.add(ModBlocks.YELLOW_STAIR_RAILING.get(),
                createStairRailingDrops(ModBlocks.YELLOW_STAIR_RAILING.get()));
        this.add(ModBlocks.YELLOW_RAILING.get(),
                createRailingDrops(ModBlocks.YELLOW_RAILING.get()));
        this.add(ModBlocks.GRAY_STAIR_RAILING.get(),
                createStairRailingDrops(ModBlocks.GRAY_STAIR_RAILING.get()));
        this.add(ModBlocks.GRAY_RAILING.get(),
                createRailingDrops(ModBlocks.GRAY_RAILING.get()));
        this.add(ModBlocks.BLACK_STAIR_RAILING.get(),
                createStairRailingDrops(ModBlocks.BLACK_STAIR_RAILING.get()));
        this.add(ModBlocks.BLACK_RAILING.get(),
                createRailingDrops(ModBlocks.BLACK_RAILING.get()));
        this.add(ModBlocks.RUSTY_STAIR_RAILING.get(),
                createStairRailingDrops(ModBlocks.RUSTY_STAIR_RAILING.get()));
        this.add(ModBlocks.RUSTY_RAILING.get(),
                createRailingDrops(ModBlocks.RUSTY_RAILING.get()));
        this.add(ModBlocks.GRAY_ROCKRETE_PARAPET.get(),
                createRailingDrops(ModBlocks.GRAY_ROCKRETE_PARAPET.get()));
        this.add(ModBlocks.RED_ROCKRETE_PARAPET.get(),
                createRailingDrops(ModBlocks.RED_ROCKRETE_PARAPET.get()));
        this.add(ModBlocks.YELLOW_ROCKRETE_PARAPET.get(),
                createRailingDrops(ModBlocks.YELLOW_ROCKRETE_PARAPET.get()));
        this.add(ModBlocks.BLUE_ROCKRETE_PARAPET.get(),
                createRailingDrops(ModBlocks.BLUE_ROCKRETE_PARAPET.get()));
        this.add(ModBlocks.GREEN_ROCKRETE_PARAPET.get(),
                createRailingDrops(ModBlocks.GREEN_ROCKRETE_PARAPET.get()));

        //WALL BLOCKS
        this.dropSelf(ModBlocks.GRAY_ROCKRETE_WALL.get());
        this.dropSelf(ModBlocks.RED_ROCKRETE_WALL.get());
        this.dropSelf(ModBlocks.YELLOW_ROCKRETE_WALL.get());
        this.dropSelf(ModBlocks.BLUE_ROCKRETE_WALL.get());
        this.dropSelf(ModBlocks.GREEN_ROCKRETE_WALL.get());

        //DUCT BLOCKS
        this.dropSelf(ModBlocks.DUCT.get());
        this.dropSelf(ModBlocks.RUSTY_DUCT.get());
        
        //VENT BLOCKS
        this.dropSelf(ModBlocks.HORIZONTAL_VENT.get());
        this.dropSelf(ModBlocks.HORIZONTAL_CUTOUT_VENT.get());
        this.dropSelf(ModBlocks.VERTICAL_VENT.get());
        this.dropSelf(ModBlocks.VERTICAL_CUTOUT_VENT.get());

        this.dropSelf(ModBlocks.RUSTY_HORIZONTAL_VENT.get());
        this.dropSelf(ModBlocks.RUSTY_HORIZONTAL_CUTOUT_VENT.get());
        this.dropSelf(ModBlocks.RUSTY_VERTICAL_VENT.get());
        this.dropSelf(ModBlocks.RUSTY_VERTICAL_CUTOUT_VENT.get());

        //VESSELPLATE BLOCKS
        this.dropSelf(ModBlocks.HORIZONTAL_VESSELPLATE.get());
        this.dropSelf(ModBlocks.VERTICAL_VESSELPLATE.get());
        this.dropSelf(ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.VERTICAL_REINFORCED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.SMOOTH_VESSELPLATE.get());
        this.dropSelf(ModBlocks.VESSELPLATE_PILLAR.get());
        this.dropSelf(ModBlocks.SMOOTH_VESSELPLATE_STAIRS.get());
        this.add(ModBlocks.SMOOTH_VESSELPLATE_SLAB.get(),
                createSlabItemTable(ModBlocks.SMOOTH_VESSELPLATE_SLAB.get()));
        this.dropSelf(ModBlocks.RIVETED_VESSELPLATE_STAIRS.get());
        this.add(ModBlocks.RIVETED_VESSELPLATE_SLAB.get(),
                createSlabItemTable(ModBlocks.RIVETED_VESSELPLATE_SLAB.get()));

        this.dropSelf(ModBlocks.GRAY_HORIZONTAL_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_VERTICAL_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_HORIZONTAL_REINFORCED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_VERTICAL_REINFORCED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.SMOOTH_GRAY_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_VESSELPLATE_PILLAR.get());
        this.dropSelf(ModBlocks.SMOOTH_GRAY_VESSELPLATE_STAIRS.get());
        this.add(ModBlocks.SMOOTH_GRAY_VESSELPLATE_SLAB.get(),
                createSlabItemTable(ModBlocks.SMOOTH_GRAY_VESSELPLATE_SLAB.get()));
        this.dropSelf(ModBlocks.GRAY_RIVETED_VESSELPLATE_STAIRS.get());
        this.add(ModBlocks.GRAY_RIVETED_VESSELPLATE_SLAB.get(),
                createSlabItemTable(ModBlocks.GRAY_RIVETED_VESSELPLATE_SLAB.get()));

        this.dropSelf(ModBlocks.SMOOTH_RUSTY_VESSELPLATE.get());
        this.dropSelf(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_STAIRS.get());
        this.add(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_SLAB.get(),
                createSlabItemTable(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_SLAB.get()));

        this.dropSelf(ModBlocks.RUSTY_RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.RUSTY_RIVETED_VESSELPLATE_STAIRS.get());
        this.add(ModBlocks.RUSTY_RIVETED_VESSELPLATE_SLAB.get(),
                createSlabItemTable(ModBlocks.RUSTY_RIVETED_VESSELPLATE_SLAB.get()));

        this.dropSelf(ModBlocks.RUSTY_HORIZONTAL_VESSELPLATE.get());
        this.dropSelf(ModBlocks.RUSTY_VERTICAL_VESSELPLATE.get());
        this.dropSelf(ModBlocks.RUSTY_HORIZONTAL_REINFORCED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.RUSTY_VERTICAL_REINFORCED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.RUSTY_VESSELPLATE_PILLAR.get());

        //TRUSS BLOCKS
        this.dropSelf(ModBlocks.TRUSS.get());
        this.add(ModBlocks.TRUSS_SLAB.get(), createSlabItemTable(ModBlocks.TRUSS_SLAB.get()));
        this.dropSelf(ModBlocks.TRUSS_STAIRS.get());
        this.dropSelf(ModBlocks.CATWALK_TRUSS.get());
        this.dropSelf(ModBlocks.CATWALK_TRUSS_STAIRS.get());
        this.add(ModBlocks.CATWALK_TRUSS_SLAB.get(), createSlabItemTable(ModBlocks.CATWALK_TRUSS_SLAB.get()));

        this.dropSelf(ModBlocks.GRAY_TRUSS.get());
        this.dropSelf(ModBlocks.GRAY_CATWALK_TRUSS.get());
        this.dropSelf(ModBlocks.GRAY_CATWALK_TRUSS_STAIRS.get());
        this.add(ModBlocks.GRAY_CATWALK_TRUSS_SLAB.get(), createSlabItemTable(ModBlocks.GRAY_CATWALK_TRUSS_SLAB.get()));
        this.add(ModBlocks.GRAY_TRUSS_SLAB.get(), createSlabItemTable(ModBlocks.GRAY_TRUSS_SLAB.get()));
        this.dropSelf(ModBlocks.GRAY_TRUSS_STAIRS.get());

        this.dropSelf(ModBlocks.RUSTY_TRUSS.get());
        this.add(ModBlocks.RUSTY_TRUSS_SLAB.get(), createSlabItemTable(ModBlocks.RUSTY_TRUSS_SLAB.get()));
        this.dropSelf(ModBlocks.RUSTY_TRUSS_STAIRS.get());

        this.dropSelf(ModBlocks.RUSTY_CATWALK_TRUSS.get());
        this.add(ModBlocks.RUSTY_CATWALK_TRUSS_SLAB.get(), createSlabItemTable(ModBlocks.RUSTY_CATWALK_TRUSS_SLAB.get()));
        this.dropSelf(ModBlocks.RUSTY_CATWALK_TRUSS_STAIRS.get());
        
        //VESSELGLASS BLOCKS
        this.dropSelf(ModBlocks.REINFORCED_VESSELGLASS.get());
        this.dropSelf(ModBlocks.VESSELGLASS.get());
        this.dropSelf(ModBlocks.GRAY_REINFORCED_VESSELGLASS.get());
        this.dropSelf(ModBlocks.GRAY_VESSELGLASS.get());
        this.dropSelf(ModBlocks.RUSTY_REINFORCED_VESSELGLASS.get());
        this.dropSelf(ModBlocks.RUSTY_VESSELGLASS.get());

        //ROCKRETE BLOCKS
        this.dropSelf(ModBlocks.ROUGH_GRAY_ROCKRETE.get());
        this.add(ModBlocks.ROUGH_GRAY_ROCKRETE_SLAB.get(), createSlabItemTable(ModBlocks.ROUGH_GRAY_ROCKRETE_SLAB.get()));
        this.dropSelf(ModBlocks.ROUGH_GRAY_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.ROUGH_GREEN_ROCKRETE.get());
        this.add(ModBlocks.ROUGH_GREEN_ROCKRETE_SLAB.get(), createSlabItemTable(ModBlocks.ROUGH_GREEN_ROCKRETE_SLAB.get()));
        this.dropSelf(ModBlocks.ROUGH_GREEN_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.ROUGH_YELLOW_ROCKRETE.get());
        this.add(ModBlocks.ROUGH_YELLOW_ROCKRETE_SLAB.get(), createSlabItemTable(ModBlocks.ROUGH_YELLOW_ROCKRETE_SLAB.get()));
        this.dropSelf(ModBlocks.ROUGH_YELLOW_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.ROUGH_BLUE_ROCKRETE.get());
        this.add(ModBlocks.ROUGH_BLUE_ROCKRETE_SLAB.get(), createSlabItemTable(ModBlocks.ROUGH_BLUE_ROCKRETE_SLAB.get()));
        this.dropSelf(ModBlocks.ROUGH_BLUE_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.ROUGH_RED_ROCKRETE.get());
        this.add(ModBlocks.ROUGH_RED_ROCKRETE_SLAB.get(), createSlabItemTable(ModBlocks.ROUGH_RED_ROCKRETE_SLAB.get()));
        this.dropSelf(ModBlocks.ROUGH_RED_ROCKRETE_STAIRS.get());

        this.dropSelf(ModBlocks.GRAY_ROCKRETE.get());
        this.dropSelf(ModBlocks.GRAY_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.GRAY_ROCKRETE_SLAB.get(), createSlabItemTable(ModBlocks.GRAY_ROCKRETE_SLAB.get()));
        this.dropSelf(ModBlocks.GRAY_ROCKRETE_STAIRS.get());

        this.dropSelf(ModBlocks.GREEN_ROCKRETE.get());
        this.dropSelf(ModBlocks.GREEN_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.GREEN_ROCKRETE_SLAB.get(), createSlabItemTable(ModBlocks.GREEN_ROCKRETE_SLAB.get()));
        this.dropSelf(ModBlocks.GREEN_ROCKRETE_STAIRS.get());

        this.dropSelf(ModBlocks.YELLOW_ROCKRETE.get());
        this.dropSelf(ModBlocks.YELLOW_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.YELLOW_ROCKRETE_SLAB.get(), createSlabItemTable(ModBlocks.YELLOW_ROCKRETE_SLAB.get()));
        this.dropSelf(ModBlocks.YELLOW_ROCKRETE_STAIRS.get());


        this.dropSelf(ModBlocks.BLUE_ROCKRETE.get());
        this.dropSelf(ModBlocks.BLUE_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.BLUE_ROCKRETE_SLAB.get(), createSlabItemTable(ModBlocks.BLUE_ROCKRETE_SLAB.get()));
        this.dropSelf(ModBlocks.BLUE_ROCKRETE_STAIRS.get());

        this.dropSelf(ModBlocks.RED_ROCKRETE.get());
        this.dropSelf(ModBlocks.RED_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.RED_ROCKRETE_SLAB.get(), createSlabItemTable(ModBlocks.RED_ROCKRETE_SLAB.get()));
        this.dropSelf(ModBlocks.RED_ROCKRETE_STAIRS.get());

        this.dropSelf(ModBlocks.GRIMY_RESTROOM_TILE.get());

        //DOORS, TRAPDOORS
        this.add(ModBlocks.ARMORED_DOOR.get(),
                createDoorTable(ModBlocks.ARMORED_DOOR.get()));
        this.add(ModBlocks.STAMPED_METAL_DOOR.get(),
                createDoorTable(ModBlocks.STAMPED_METAL_DOOR.get()));
        this.add(ModBlocks.BULKHEAD_DOOR.get(),
                createDoorTable(ModBlocks.BULKHEAD_DOOR.get()));
        this.dropSelf(ModBlocks.VENT_TRAPDOOR.get());
        this.dropSelf(ModBlocks.RUSTY_VENT_TRAPDOOR.get());

        //BRACKETS
        this.dropSelf(ModBlocks.GRAY_BOLTED_BRACKET.get());
        this.dropSelf(ModBlocks.SMALL_GRAY_BOLTED_BRACKET.get());
        this.dropSelf(ModBlocks.BLACK_BOLTED_BRACKET.get());
        this.dropSelf(ModBlocks.SMALL_BLACK_BOLTED_BRACKET.get());
        this.dropSelf(ModBlocks.RUSTY_BOLTED_BRACKET.get());
        this.dropSelf(ModBlocks.SMALL_RUSTY_BOLTED_BRACKET.get());

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

        //FURNITURE
        this.dropSelf(ModBlocks.RED_WALL_MEDKIT.get());
        this.dropSelf(ModBlocks.WHITE_WALL_MEDKIT.get());
        this.dropSelf(ModBlocks.FIRE_EXTINGUISHER.get());
        this.dropSelf(ModBlocks.SMOKE_ALARM.get());
        this.add(ModBlocks.OPERATING_TABLE.get(),
                createIntegerMultiBlockDrops(ModBlocks.OPERATING_TABLE.get()));
        this.add(ModBlocks.MEDICAL_BED.get(),
                createIntegerMultiBlockDrops(ModBlocks.MEDICAL_BED.get()));
        this.add(ModBlocks.IV_DRIPSTAND.get(),
                createIntegerMultiBlockDrops(ModBlocks.IV_DRIPSTAND.get()));
        this.add(ModBlocks.VITALS_MONITOR.get(),
                createIntegerMultiBlockDrops(ModBlocks.VITALS_MONITOR.get()));

        this.dropSelf(ModBlocks.TOILET.get());
        this.dropSelf(ModBlocks.SINK.get());
        this.dropSelf(ModBlocks.URINAL.get());

        this.dropSelf(ModBlocks.LOCKER_BOX.get());
        this.add(ModBlocks.LARGE_LOCKER.get(),
                createIntegerMultiBlockDrops(ModBlocks.LARGE_LOCKER.get()));
        this.add(ModBlocks.WORK_LIGHT_STAND.get(),
                createIntegerMultiBlockDrops(ModBlocks.WORK_LIGHT_STAND.get()));
        this.dropSelf(ModBlocks.FLOOR_WORK_LIGHT.get());
        this.dropSelf(ModBlocks.FUEL_DRUM.get());
        this.dropSelf(ModBlocks.CCTV_CAMERA.get());
//        this.dropSelf(ModBlocks.INDUSTRIAL_LAMP.get());
        this.dropSelf(ModBlocks.OBLONG_CAGE_LAMP.get());

        this.dropSelf(ModBlocks.CASSETTE_PLAYER.get());
        this.dropSelf(ModBlocks.RETRO_COMPUTER.get());
        this.dropSelf(ModBlocks.RETRO_COMPUTER_2.get());
        this.dropSelf(ModBlocks.MONITOR_AND_KEYBOARD.get());
        this.dropSelf(ModBlocks.DESKTOP_TOWER.get());

        this.dropSelf(ModBlocks.DESK.get());
        this.dropSelf(ModBlocks.DESK_DRAWER.get());
        this.dropSelf(ModBlocks.METAL_DESK.get());
        this.dropSelf(ModBlocks.METAL_DESK_DRAWER.get());
        this.dropSelf(ModBlocks.METAL_DESK_DRAWER_2.get());
        this.dropSelf(ModBlocks.OFFICE_DESK_DRAWER.get());
        this.dropSelf(ModBlocks.OFFICE_DESK.get());
        this.dropSelf(ModBlocks.OFFICE_CHAIR.get());
        this.dropSelf(ModBlocks.BLACK_OFFICE_CHAIR.get());
        this.dropSelf(ModBlocks.FOLDING_CHAIR.get());
    }

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

    protected LootTable.Builder createIntegerMultiBlockDrops(Block pBlock) {
        //Only the first block of the multiblock (PART #0) has a loot table to drop the block itself
        return LootTable.lootTable().withPool(LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBlock)
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(IntegerMultiBlock.PART, 0))) //PART #0
                        .add(this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pBlock)
                                        .apply(List.of(1), (p_249985_) -> SetItemCountFunction.setCount(ConstantValue.exactly((float) p_249985_))))
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))))
                ;
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
