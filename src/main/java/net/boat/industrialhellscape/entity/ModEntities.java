package net.boat.industrialhellscape.entity;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.entity.SittableEntity.SittableEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {

    public static void removeAndReplaceItems() { //Replaces/renames Entities with new id value
        //currently there is no need for remapping of entities
        //specificEntityReplacement("chair","folding_chair");
    }

    protected static ResourceLocation entityID(String entityID) {
        return ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, entityID);
    }

    protected static void specificEntityReplacement(String oldID, String newID) {
        ENTITY_TYPES.addAlias(
                entityID(oldID),
                entityID(newID)
        );
    }

    private static final float foldingChairHeight = 0.625f; // 1.00 is a full block height
    //private static final float officeChairHeight = 0.625f; // 1.00 is a full block height

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, IndustrialHellscape.MOD_ID);

    public static final Supplier<EntityType<SittableEntity>> CHAIR =
            ENTITY_TYPES.register("chair", () -> EntityType.Builder.of(SittableEntity::new, MobCategory.MISC)
                    .sized(0.5f, foldingChairHeight)
                    .build("chair"));

    //fine to keep
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
