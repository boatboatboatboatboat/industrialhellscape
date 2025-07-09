package net.boat.industrialhellscape.entity;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.entity.custom.SittableEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class  ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, IndustrialHellscape.MOD_ID);

    public static final RegistryObject<EntityType<SittableEntity>> CHAIR =
            ENTITY_TYPES.register("chair", () -> EntityType.Builder.of(SittableEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("chair"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
