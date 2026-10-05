package net.boat.industrialhellscape.datagen;

import com.mojang.serialization.Codec;
import net.boat.industrialhellscape.IndustrialHellscape;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(
                    Registries.DATA_COMPONENT_TYPE,
                    IndustrialHellscape.MOD_ID
            );

    //String for unique ID/frequencies for block entity
    public static final DeferredHolder<
                DataComponentType<?>,
                DataComponentType<String>
                > FREQUENCY_STRING = DATA_COMPONENTS.registerComponentType(
            "frequency_string",
            builder -> builder
                    .persistent(Codec.STRING)
                    .networkSynchronized(ByteBufCodecs.STRING_UTF8)
    );

    //String for block entity boolean property
    public static final DeferredHolder<
            DataComponentType<?>,
            DataComponentType<Boolean>
            > IS_POWERED = DATA_COMPONENTS.registerComponentType(
            "is_powered",
            builder -> builder
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
    );

    public static final DeferredHolder<
            DataComponentType<?>,
            DataComponentType<Boolean>
            > IS_RECEIVER = DATA_COMPONENTS.registerComponentType(
            "is_receiver",
            builder -> builder
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL)
    );

    private ModDataComponents() {
    }

    public static void register(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }
}