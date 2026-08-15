package net.boat.industrialhellscape.block.modded_block_entities;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.boat.industrialhellscape.block.modded_block_entities.StorageBE.StorageBE;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IndustrialHellscape.MOD_ID);

//    public static final Supplier<BlockEntityType<StorageBE>> STORAGE_BE = BLOCK_ENTITIES.register("storage_be", () -> BlockEntityType.Builder.of(
//            WrapperStorageBE::new,
//            /*
//            It is REQUIRED to associate new inventory blocks to this block entity.
//            A crash will happen otherwise. This was not an issue in 1.20.1
//             */
//            ModBlocks.RED_WALL_MEDKIT.get(),
//            ModBlocks.WHITE_WALL_MEDKIT.get(),
//            ModBlocks.OFFICE_DESK_DRAWER.get(),
//            ModBlocks.DESK_DRAWER.get(),
//            ModBlocks.METAL_DESK_DRAWER.get(),
//            ModBlocks.METAL_DESK_DRAWER_2.get(),
//
//            ModBlocks.LOCKER_BOX.get(),
//            ModBlocks.LARGE_LOCKER.get(),
//            ModBlocks.FUEL_DRUM.get()
//
//    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StorageBE>> STORAGE_BE = BLOCK_ENTITIES.register("storage_be", () -> BlockEntityType.Builder.of(
                                    StorageBE::new,

                                    ModBlocks.DEBUG_BLOCK.get(),

                                    ModBlocks.RED_WALL_MEDKIT.get(),
                                    ModBlocks.WHITE_WALL_MEDKIT.get(),
                                    ModBlocks.OFFICE_DESK_DRAWER.get(),
                                    ModBlocks.DESK_DRAWER.get(),
                                    ModBlocks.METAL_DESK_DRAWER.get(),
                                    ModBlocks.METAL_DESK_DRAWER_2.get(),

                                    ModBlocks.LOCKER_BOX.get(),
                                    ModBlocks.LARGE_LOCKER.get(),
                                    ModBlocks.FUEL_DRUM.get()
                                    )
                                    .build(null));
}
