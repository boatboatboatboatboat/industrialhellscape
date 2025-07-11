package net.boat.industrialhellscape.sound;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, IndustrialHellscape.MOD_ID);

    public static final RegistryObject<SoundEvent> VESSELPLATE_BREAK = registerSoundEvents("vesselplate_break");
    public static final RegistryObject<SoundEvent> VESSELPLATE_STEP = registerSoundEvents("vesselplate_step");
    public static final RegistryObject<SoundEvent> VESSELPLATE_FALL = registerSoundEvents("vesselplate_fall");
    public static final RegistryObject<SoundEvent> VESSELPLATE_PLACE = registerSoundEvents("vesselplate_place");
    public static final RegistryObject<SoundEvent> VESSELPLATE_HIT = registerSoundEvents("vesselplate_hit");

    public static final RegistryObject<SoundEvent> VULTA_SHATTERED = registerSoundEvents("vulta_shattered");
    public static final RegistryObject<SoundEvent> TOILET_FLUSH = registerSoundEvents("toilet_flush");

    public static final ForgeSoundType VESSELPLATE_BLOCK_SOUNDS = new ForgeSoundType(2f,1.5f,
            ModSounds.VESSELPLATE_BREAK,
            ModSounds.VESSELPLATE_STEP,
            ModSounds.VESSELPLATE_FALL,
            ModSounds.VESSELPLATE_PLACE,
            ModSounds.VESSELPLATE_HIT
            );

    private static RegistryObject<SoundEvent> registerSoundEvents(String soundFileName) {
        return SOUND_EVENTS.register(soundFileName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(IndustrialHellscape.MOD_ID, soundFileName)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
