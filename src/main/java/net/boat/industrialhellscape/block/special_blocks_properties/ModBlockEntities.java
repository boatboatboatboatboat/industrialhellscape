package net.boat.industrialhellscape.block.special_blocks_properties;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.Storage18SlotBlockEntity;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.Storage27SlotBlockEntity;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.Storage54SlotBlockEntity;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.Storage9SlotBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//DO NOT REMOVE DEPRECIATED INVENTORY BLOCK ENTITIES

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, IndustrialHellscape.MOD_ID);

    public static final RegistryObject<BlockEntityType<Storage9SlotBlockEntity>> STORAGE_9_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("storage_9slot_block_entity", () ->
                    BlockEntityType.Builder.of(Storage9SlotBlockEntity::new,
                            ModBlocks.RED_WALL_MEDKIT.get(),
                            ModBlocks.WHITE_WALL_MEDKIT.get(),
                            ModBlocks.DESK_DRAWER.get(),
                            ModBlocks.METAL_DESK_DRAWER.get(),
                            ModBlocks.METAL_DESK_DRAWER_2.get()
                    ).build(null));
    public static final RegistryObject<BlockEntityType<Storage18SlotBlockEntity>> STORAGE_18_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("storage_18slot_block_entity", () ->
                    BlockEntityType.Builder.of(Storage18SlotBlockEntity::new,
                            ModBlocks.DESK_DRAWER.get(),
                            ModBlocks.METAL_DESK_DRAWER.get(),
                            ModBlocks.METAL_DESK_DRAWER_2.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<Storage27SlotBlockEntity>> STORAGE_27_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("storage_27slot_block_entity", () ->
                    BlockEntityType.Builder.of(Storage27SlotBlockEntity::new,
                            ModBlocks.LOCKER_BOX.get()
                    ).build(null));
    public static final RegistryObject<BlockEntityType<Storage54SlotBlockEntity>> STORAGE_54_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("storage_54slot_block_entity", () ->
                    BlockEntityType.Builder.of(Storage54SlotBlockEntity::new,
                            ModBlocks.LARGE_LOCKER.get()
                    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}