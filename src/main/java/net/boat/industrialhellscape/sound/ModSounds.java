package net.boat.industrialhellscape.sound;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, IndustrialHellscape.MOD_ID);

    public static final Supplier<SoundEvent> METAL_CLANK = registerSoundEvents("metal_clank");
    public static final Supplier<SoundEvent> SMOKE_ALARM = registerSoundEvents("smoke_alarm");
    public static final Supplier<SoundEvent> TOILET_FLUSH = registerSoundEvents("toilet_flush");
    public static final Supplier<SoundEvent> METALPIPEFALLINGSOUNDEFFECT = registerSoundEvents("metalpipefallingsoundeffect");
    public static final Supplier<SoundEvent> SNORE = registerSoundEvents("snore");

    public static final Supplier<SoundEvent> METAL_BOX_OPEN = registerSoundEvents("metal_box_opening");
    public static final Supplier<SoundEvent> METAL_BOX_CLOSE = registerSoundEvents("metal_box_closing");

    public static final DeferredSoundType HOLLOW_METAL_BLOCK_SOUNDS = new DeferredSoundType(2f,1.5f,
            ModSounds.METAL_CLANK, //Break
            ModSounds.METAL_CLANK, //Step
            ModSounds.METAL_CLANK, //Fall
            ModSounds.METAL_CLANK, //Place
            ModSounds.METAL_CLANK  //Hit
            );

    public static final Supplier<SoundEvent> SONG_1 = registerSoundEvents("song_1");
    public static final ResourceKey<JukeboxSong> SONG_1_KEY = createSong("song_1");

    private static ResourceKey<JukeboxSong> createSong(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, name));
    }

    private static Supplier<SoundEvent> registerSoundEvents(String soundFileName) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, soundFileName);
        return SOUND_EVENTS.register(soundFileName, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
