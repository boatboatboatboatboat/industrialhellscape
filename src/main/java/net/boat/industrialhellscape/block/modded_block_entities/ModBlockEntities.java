package net.boat.industrialhellscape.block.modded_block_entities;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

//DO NOT REMOVE DEPRECIATED INVENTORY BLOCK ENTITIES

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, IndustrialHellscape.MOD_ID);

    public static final Supplier<BlockEntityType<GenericContainerBE>> CONTAINER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("container_block_entity", () ->
                    BlockEntityType.Builder.of(GenericContainerBE::new,

//                            ModBlocks.LOCKER_BOX.get(),
//                            ModBlocks.LARGE_LOCKER.get(),
//
//                            ModBlocks.RED_WALL_MEDKIT.get(),
//                            ModBlocks.WHITE_WALL_MEDKIT.get(),
//
//                            ModBlocks.DESK_DRAWER.get(),
//                            ModBlocks.METAL_DESK_DRAWER.get(),
//                            ModBlocks.METAL_DESK_DRAWER_2.get(),
//                            ModBlocks.FUEL_DRUM.get(),
//
//                            ModBlocks.DESK_DRAWER.get(),
//                            ModBlocks.METAL_DESK_DRAWER.get(),
//                            ModBlocks.METAL_DESK_DRAWER_2.get(),
//                            ModBlocks.OFFICE_DESK_DRAWER.get()

                    ).build(null));


    //EXPERIMENTAL
//    public static final DeferredRegister<BlockEntityType<RecyclerBE>> RECYCLER_BLOCK_ENTITY =
//            BLOCK_ENTITIES.register("recycler_block_entity", () ->
//                    BlockEntityType.Builder.of(RecyclerBE::new,
//
//                            ModBlocks.PROTOTYPE_MACHINE.get()
//
//                    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}