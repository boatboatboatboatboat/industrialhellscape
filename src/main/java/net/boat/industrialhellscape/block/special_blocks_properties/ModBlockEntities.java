package net.boat.industrialhellscape.block.special_blocks_properties;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.special_blocks.StorageBlock.NineSlotMenuBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, IndustrialHellscape.MOD_ID);

    public static final RegistryObject<BlockEntityType<NineSlotMenuBlockEntity>> NINE_SLOT_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("storage_9slot_block_entity", () ->
                    BlockEntityType.Builder.of(NineSlotMenuBlockEntity::new,
                            ModBlocks.RED_WALL_MEDKIT.get(),
                            ModBlocks.WHITE_WALL_MEDKIT.get(),
                            ModBlocks.DESK_DRAWER.get(),
                            ModBlocks.DESK_DRAWER.get(),
                            ModBlocks.DESK_DRAWER.get(),
                            ModBlocks.METAL_DESK_DRAWER_2.get()
                    ).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}