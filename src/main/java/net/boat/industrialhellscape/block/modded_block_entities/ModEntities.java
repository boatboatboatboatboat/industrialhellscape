package net.boat.industrialhellscape.block.modded_block_entities;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.modded_block_entities.SittableEntity.SittableEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class  ModEntities {

    private static final float chairHeight = 0.50f; // 1.00 is a full block height

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, IndustrialHellscape.MOD_ID);

    public static final Supplier<EntityType<SittableEntity>> CHAIR =
            ENTITY_TYPES.register("chair", () -> EntityType.Builder.of(SittableEntity::new, MobCategory.MISC)
                    .sized(0.5f, chairHeight).build("chair"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
