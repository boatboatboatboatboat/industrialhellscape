package net.boat.industrialhellscape.datagen;

import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.TwoBlockMultiBlock;
import net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks.RailingBlock;
import net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks.StairRailingBlock;
import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;

//THIS JAVA CLASS HANDLES BLOCK's ITEM DROP BEHAVIOR WHEN MINED, DATA-GENERATING LOOT DROP TABLES

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        //DEBUG BLOCKS
        this.dropSelf(ModBlocks.PROTOTYPE_MACHINE.get());
        this.dropSelf(ModBlocks.POSTER_1.get());
        this.dropSelf(ModBlocks.OFFICE_CHAIR.get());
        this.dropSelf(ModBlocks.BLACK_OFFICE_CHAIR.get());
        this.dropSelf(ModBlocks.FOLDING_CHAIR.get());
        this.dropSelf(ModBlocks.OPERATING_TABLE.get());
        this.dropSelf(ModBlocks.MEDICAL_BED.get());
        this.dropSelf(ModBlocks.IV_DRIPSTAND.get());
        this.dropSelf(ModBlocks.VITALS_MONITOR.get());
        this.add(ModBlocks.VESSELPLATE_DOOR.get(),
                block -> createDoorTable(ModBlocks.VESSELPLATE_DOOR.get()));
        this.add(ModBlocks.GRAY_VESSELPLATE_DOOR.get(),
                block -> createDoorTable(ModBlocks.GRAY_VESSELPLATE_DOOR.get()));
        this.dropSelf(ModBlocks.VESSELPLATE_TRAPDOOR.get());
        this.dropSelf(ModBlocks.GRAY_VESSELPLATE_TRAPDOOR.get());


        //JOKE BLOCKS
        this.dropSelf(ModBlocks.BODY_PILLOW.get());

        //IRONLIKE SIMPLE BLOCKS
        this.dropSelf(ModBlocks.RIVETED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.HORIZONTAL_VESSELPLATE.get());
        this.dropSelf(ModBlocks.VERTICAL_VESSELPLATE.get());

        this.dropSelf(ModBlocks.HORIZONTAL_GRATE.get());
        this.dropSelf(ModBlocks.HORIZONTAL_CUTOUT_GRATE.get());
        this.dropSelf(ModBlocks.RUSTY_GRATE.get());
        this.dropSelf(ModBlocks.RUSTY_SEETHROUGH_GRATE.get());

        this.dropSelf(ModBlocks.HORIZONTAL_VESSELPLATE.get());
        this.dropSelf(ModBlocks.SMOOTH_VESSELPLATE.get());
        this.dropSelf(ModBlocks.VESSELPLATE_PILLAR.get());
        this.dropSelf(ModBlocks.HORIZONTAL_REINFORCED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.VERTICAL_REINFORCED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.RIVETED_VESSELPLATE_STAIRS.get());
        this.add(ModBlocks.RIVETED_VESSELPLATE_SLAB.get(), block -> createSlabItemTable(ModBlocks.RIVETED_VESSELPLATE_SLAB.get()));
        this.dropSelf(ModBlocks.SMOOTH_VESSELPLATE_STAIRS.get());
        this.add(ModBlocks.SMOOTH_VESSELPLATE_SLAB.get(), block -> createSlabItemTable(ModBlocks.SMOOTH_VESSELPLATE_SLAB.get()));

        this.dropSelf(ModBlocks.GRAY_RIVETED_VESSELPLATE.get());

        this.dropSelf(ModBlocks.GRAY_HORIZONTAL_GRATE.get());
        this.dropSelf(ModBlocks.GRAY_HORIZONTAL_CUTOUT_GRATE.get());
        this.dropSelf(ModBlocks.GRAY_VERTICAL_GRATE.get());
        this.dropSelf(ModBlocks.GRAY_VERTICAL_CUTOUT_GRATE.get());

        this.dropSelf(ModBlocks.GRAY_VERTICAL_REINFORCED_VESSELPLATE.get());

        this.dropSelf(ModBlocks.GRAY_HORIZONTAL_REINFORCED_VESSELPLATE.get());
        this.dropSelf(ModBlocks.SMOOTH_GRAY_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_VESSELPLATE_PILLAR.get());
        this.dropSelf(ModBlocks.GRAY_HORIZONTAL_VESSELPLATE.get());
        this.dropSelf(ModBlocks.GRAY_VERTICAL_VESSELPLATE.get());
//        this.dropSelf(ModBlocks.GRAY_VESSELPLATE_PANEL_STAIRS.get());
//        this.add(ModBlocks.GRAY_VESSELPLATE_PANEL_SLAB.get(), block -> createSlabItemTable(ModBlocks.GRAY_VESSELPLATE_PANEL_SLAB.get()));
        this.dropSelf(ModBlocks.GRAY_RIVETED_VESSELPLATE_STAIRS.get());
        this.add(ModBlocks.GRAY_RIVETED_VESSELPLATE_SLAB.get(), block -> createSlabItemTable(ModBlocks.GRAY_RIVETED_VESSELPLATE_SLAB.get()));
        this.dropSelf(ModBlocks.SMOOTH_GRAY_VESSELPLATE_STAIRS.get());
        this.add(ModBlocks.SMOOTH_GRAY_VESSELPLATE_SLAB.get(), block -> createSlabItemTable(ModBlocks.SMOOTH_GRAY_VESSELPLATE_SLAB.get()));

        this.dropSelf(ModBlocks.RUSTY_VESSELPLATE_SHEETING.get());
        this.dropSelf(ModBlocks.RUSTY_VESSELPLATE_SHEETING_STAIRS.get());
        this.add(ModBlocks.RUSTY_VESSELPLATE_SHEETING_SLAB.get(), block -> createSlabItemTable(ModBlocks.RUSTY_VESSELPLATE_SHEETING_SLAB.get()));

        this.dropSelf(ModBlocks.RUSTY_RIVETED_VESSELPLATE_PANEL.get());
        this.dropSelf(ModBlocks.RUSTY_RIVETED_VESSELPLATE_STAIRS.get());
        this.add(ModBlocks.RUSTY_RIVETED_VESSELPLATE_SLAB.get(), block -> createSlabItemTable(ModBlocks.RUSTY_RIVETED_VESSELPLATE_SLAB.get()));
        this.dropSelf(ModBlocks.RUSTY_VESSELPLATE_PILLAR.get());
        this.dropSelf(ModBlocks.RUSTY_DIRECTIONAL_RIVETED_VESSELPLATE.get());

        this.dropSelf(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_TILE.get());
        this.dropSelf(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_STAIRS.get());
        this.dropSelf(ModBlocks.SMOOTH_RUSTY_VESSELPLATE_SLAB.get());
        //VESSELGLASS
        this.dropSelf(ModBlocks.REINFORCED_VESSELGLASS.get());
        this.dropSelf(ModBlocks.VESSELGLASS.get());
        this.dropSelf(ModBlocks.GRAY_REINFORCED_VESSELGLASS.get());
        this.dropSelf(ModBlocks.GRAY_VESSELGLASS.get());

        //STRUTS
        this.dropSelf(ModBlocks.TRUSS.get());
        this.dropSelf(ModBlocks.CATWALK_TRUSS.get());
        this.dropSelf(ModBlocks.CATWALK_TRUSS_STAIRS.get());
        this.add(ModBlocks.CATWALK_TRUSS_SLAB.get(), block -> createSlabItemTable(ModBlocks.CATWALK_TRUSS_SLAB.get()));
        this.add(ModBlocks.TRUSS_SLAB.get(), block -> createSlabItemTable(ModBlocks.TRUSS_SLAB.get()));
        this.dropSelf(ModBlocks.TRUSS_STAIRS.get());

        this.dropSelf(ModBlocks.GRAY_TRUSS.get());
        this.dropSelf(ModBlocks.GRAY_CATWALK_TRUSS.get());
        this.dropSelf(ModBlocks.GRAY_CATWALK_TRUSS_STAIRS.get());
        this.add(ModBlocks.GRAY_CATWALK_TRUSS_SLAB.get(), block -> createSlabItemTable(ModBlocks.GRAY_CATWALK_TRUSS_SLAB.get()));
        this.add(ModBlocks.GRAY_TRUSS_SLAB.get(), block -> createSlabItemTable(ModBlocks.GRAY_TRUSS_SLAB.get()));
        this.dropSelf(ModBlocks.GRAY_TRUSS_STAIRS.get());

        this.dropSelf(ModBlocks.RUSTY_TRUSS.get());
        this.dropSelf(ModBlocks.RUSTY_TRUSS_STAIRS.get());
        this.add(ModBlocks.RUSTY_TRUSS_SLAB.get(), block -> createSlabItemTable(ModBlocks.RUSTY_TRUSS_SLAB.get()));
        this.dropSelf(ModBlocks.RUSTY_CATWALK_TRUSS.get());
        this.dropSelf(ModBlocks.RUSTY_CATWALK_TRUSS_STAIRS.get());
        this.add(ModBlocks.RUSTY_CATWALK_TRUSS_SLAB.get(), block -> createSlabItemTable(ModBlocks.RUSTY_CATWALK_TRUSS_SLAB.get()));

        this.dropSelf(ModBlocks.ENCASED_CABLES.get());

        //STONELIKE BLOCKS
        this.dropSelf(ModBlocks.GRAY_ROCKRETE.get());
        this.dropSelf(ModBlocks.ROUGH_GRAY_ROCKRETE.get());
        this.dropSelf(ModBlocks.GRAY_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.GRAY_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.GRAY_ROCKRETE_SLAB.get(), block -> createSlabItemTable(ModBlocks.GRAY_ROCKRETE_SLAB.get()));

        this.dropSelf(ModBlocks.GREEN_ROCKRETE.get());
        this.dropSelf(ModBlocks.ROUGH_GREEN_ROCKRETE.get());
        this.dropSelf(ModBlocks.GREEN_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.GREEN_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.GREEN_ROCKRETE_SLAB.get(), block -> createSlabItemTable(ModBlocks.GREEN_ROCKRETE_SLAB.get()));

        this.dropSelf(ModBlocks.YELLOW_ROCKRETE.get());
        this.dropSelf(ModBlocks.ROUGH_YELLOW_ROCKRETE.get());
        this.dropSelf(ModBlocks.YELLOW_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.YELLOW_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.YELLOW_ROCKRETE_SLAB.get(), block -> createSlabItemTable(ModBlocks.YELLOW_ROCKRETE_SLAB.get()));

        this.dropSelf(ModBlocks.BLUE_ROCKRETE.get());
        this.dropSelf(ModBlocks.ROUGH_BLUE_ROCKRETE.get());
        this.dropSelf(ModBlocks.BLUE_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.BLUE_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.BLUE_ROCKRETE_SLAB.get(), block -> createSlabItemTable(ModBlocks.BLUE_ROCKRETE_SLAB.get()));

        this.dropSelf(ModBlocks.RED_ROCKRETE.get());
        this.dropSelf(ModBlocks.ROUGH_RED_ROCKRETE.get());
        this.dropSelf(ModBlocks.RED_ROCKRETE_STAIRS.get());
        this.dropSelf(ModBlocks.RED_ROCKRETE_PILLAR.get());
        this.add(ModBlocks.RED_ROCKRETE_SLAB.get(), block -> createSlabItemTable(ModBlocks.RED_ROCKRETE_SLAB.get()));

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
        this.dropSelf(ModBlocks.DUCT.get());
        this.dropSelf(ModBlocks.RUSTY_DUCT.get());
        this.add(ModBlocks.YELLOW_STAIR_RAILING.get(),
                block -> createStairRailingDrops(ModBlocks.YELLOW_STAIR_RAILING.get()));
        this.add(ModBlocks.YELLOW_RAILING.get(),
                block -> createRailingDrops(ModBlocks.YELLOW_RAILING.get()));
        this.add(ModBlocks.GRAY_STAIR_RAILING.get(),
                block -> createStairRailingDrops(ModBlocks.GRAY_STAIR_RAILING.get()));
        this.add(ModBlocks.GRAY_RAILING.get(),
                block -> createRailingDrops(ModBlocks.GRAY_RAILING.get()));
        this.add(ModBlocks.BLACK_STAIR_RAILING.get(),
                block -> createStairRailingDrops(ModBlocks.BLACK_STAIR_RAILING.get()));
        this.add(ModBlocks.BLACK_RAILING.get(),
                block -> createRailingDrops(ModBlocks.BLACK_RAILING.get()));
        this.add(ModBlocks.RUSTY_STAIR_RAILING.get(),
                block -> createStairRailingDrops(ModBlocks.RUSTY_STAIR_RAILING.get()));
        this.add(ModBlocks.RUSTY_RAILING.get(),
                block -> createRailingDrops(ModBlocks.RUSTY_RAILING.get()));
        this.dropSelf(ModBlocks.GRAY_BOLTED_BRACKET.get());
        this.dropSelf(ModBlocks.SMALL_GRAY_BOLTED_BRACKET.get());
        this.dropSelf(ModBlocks.BLACK_BOLTED_BRACKET.get());
        this.dropSelf(ModBlocks.SMALL_BLACK_BOLTED_BRACKET.get());
        this.dropSelf(ModBlocks.RUSTY_BOLTED_BRACKET.get());
        this.dropSelf(ModBlocks.SMALL_RUSTY_BOLTED_BRACKET.get());

        //FURNITURE BLOCKS
        this.dropSelf(ModBlocks.RED_WALL_MEDKIT.get());
        this.dropSelf(ModBlocks.WHITE_WALL_MEDKIT.get());
        this.dropSelf(ModBlocks.FIRE_EXTINGUISHER.get());
        this.dropSelf(ModBlocks.SMOKE_ALARM.get());

        this.dropSelf(ModBlocks.TOILET.get());
        this.dropSelf(ModBlocks.SINK.get());
        this.dropSelf(ModBlocks.URINAL.get());

        this.dropSelf(ModBlocks.LOCKER_BOX.get());
//        this.dropSelf(ModBlocks.LARGE_LOCKER.get());
        this.add(ModBlocks.LARGE_LOCKER.get(),
                block -> createMultiBlockDrops(ModBlocks.LARGE_LOCKER.get()));
        this.dropSelf(ModBlocks.WORK_LIGHT_STAND.get());
        this.dropSelf(ModBlocks.FLOOR_WORK_LIGHT.get());
        this.dropSelf(ModBlocks.FUEL_DRUM.get());
        this.dropSelf(ModBlocks.CCTV_CAMERA.get());
        this.dropSelf(ModBlocks.WALL_SPEAKER.get());

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

        //DOORS AND TRAPDOORS
        this.dropSelf(ModBlocks.DUCT_VENT.get());
        this.dropSelf(ModBlocks.RUSTY_DUCT_VENT.get());

        //FURNITURE CATEGORIES
        this.dropSelf(ModBlocks.AMENITY_FURNISHINGS.get());
        this.dropSelf(ModBlocks.INDUSTRIAL_FURNISHINGS.get());
        this.dropSelf(ModBlocks.TECHNOLOGY_FURNISHINGS.get());
        this.dropSelf(ModBlocks.HYGIENE_FURNISHINGS.get());
        this.dropSelf(ModBlocks.SAFETY_FURNISHINGS.get());
        this.dropSelf(ModBlocks.IHEA_FURNITURE_KIT.get());

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


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
