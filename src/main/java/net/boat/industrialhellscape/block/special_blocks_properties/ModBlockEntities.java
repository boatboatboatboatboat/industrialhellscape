package net.boat.industrialhellscape.block.special_blocks_properties;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.special_blocks.ContainerBlock.GenericContainerBE;
import net.boat.industrialhellscape.block.special_blocks.DeprecatedStorageBlocks.Storage18SlotBlockEntity;
import net.boat.industrialhellscape.block.special_blocks.DeprecatedStorageBlocks.Storage9SlotBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

//DO NOT REMOVE DEPRECIATED INVENTORY BLOCK ENTITIES

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, IndustrialHellscape.MOD_ID);

    public static final RegistryObject<BlockEntityType<Storage9SlotBlockEntity>> STORAGE_9_BLOCK_ENTITY = //DEPRECATED
            BLOCK_ENTITIES.register("storage_9slot_block_entity", () ->
                    BlockEntityType.Builder.of(Storage9SlotBlockEntity::new,
                            ModBlocks.RED_WALL_MEDKIT.get(),
                            ModBlocks.WHITE_WALL_MEDKIT.get(),
                            ModBlocks.DESK_DRAWER.get(),
                            ModBlocks.METAL_DESK_DRAWER.get(),
                            ModBlocks.METAL_DESK_DRAWER_2.get()
                    ).build(null));
    public static final RegistryObject<BlockEntityType<Storage18SlotBlockEntity>> STORAGE_18_BLOCK_ENTITY = //DEPRECATED
            BLOCK_ENTITIES.register("storage_18slot_block_entity", () ->
                    BlockEntityType.Builder.of(Storage18SlotBlockEntity::new,
                            ModBlocks.DESK_DRAWER.get(),
                            ModBlocks.METAL_DESK_DRAWER.get(),
                            ModBlocks.METAL_DESK_DRAWER_2.get()
                    ).build(null));

    public static final RegistryObject<BlockEntityType<GenericContainerBE>> CONTAINER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("container_block_entity", () ->
                    BlockEntityType.Builder.of(GenericContainerBE::new,

                            ModBlocks.LOCKER_BOX.get(),

                            ModBlocks.RED_WALL_MEDKIT.get(),
                            ModBlocks.WHITE_WALL_MEDKIT.get(),

                            ModBlocks.DESK_DRAWER.get(),
                            ModBlocks.METAL_DESK_DRAWER.get(),
                            ModBlocks.METAL_DESK_DRAWER_2.get()

                    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}