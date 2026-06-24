package net.boat.industrialhellscape.entity;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.entity.SittableEntity.SittableEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class  ModEntities {

    private static final float chairHeight = 0.50f; // 1.00 is a full block height

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, IndustrialHellscape.MOD_ID);

    public static final Supplier<EntityType<SittableEntity>> CHAIR =
            ENTITY_TYPES.register("chair", () -> EntityType.Builder.of(SittableEntity::new, MobCategory.MISC)
                    .sized(0.5f, chairHeight).build("chair"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
