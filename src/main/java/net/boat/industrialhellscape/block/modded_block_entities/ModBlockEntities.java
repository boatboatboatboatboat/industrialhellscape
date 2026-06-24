package net.boat.industrialhellscape.block.modded_block_entities;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_entities.StorageBE.StorageBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, IndustrialHellscape.MOD_ID);

    public static final Supplier<BlockEntityType<StorageBE>> STORAGE_BE = BLOCK_ENTITIES.register("storage_be", () -> BlockEntityType.Builder.of(
            StorageBE::new,

            ModBlocks.RED_WALL_MEDKIT.get(),
            ModBlocks.WHITE_WALL_MEDKIT.get(),
            ModBlocks.OFFICE_DESK_DRAWER.get(),
            ModBlocks.DESK_DRAWER.get(),
            ModBlocks.METAL_DESK_DRAWER.get(),
            ModBlocks.METAL_DESK_DRAWER_2.get(),

            ModBlocks.LOCKER_BOX.get(),
            ModBlocks.LARGE_LOCKER.get(),
            ModBlocks.FUEL_DRUM.get()

    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}