package net.boat.industrialhellscape.block.experiments;

import com.mojang.serialization.Codec;
import net.boat.industrialhellscape.IndustrialHellscape;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModData {
    // Create the DeferredRegister for attachment types
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, IndustrialHellscape.MOD_ID);

    // Serialization via INBTSerializable
//    private static final Supplier<AttachmentType<ItemStackHandler>> HANDLER = ATTACHMENT_TYPES.register(
//            "handler", () -> AttachmentType.serializable(() -> new ItemStackHandler(1)).build()
//    );
    // Serialization via codec
    private static final Supplier<AttachmentType<Integer>> MANA = ATTACHMENT_TYPES.register(
            "owner", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    // No serialization
//    private static final Supplier<AttachmentType<SomeCache>> SOME_CACHE = ATTACHMENT_TYPES.register(
//            "some_cache", () -> AttachmentType.builder(() -> new SomeCache()).build()
//    );

    // In your mod constructor, don't forget to register the DeferredRegister to your mod bus:
    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
