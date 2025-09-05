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

    public static final RegistryObject<SoundEvent> VESSELPLATE_CLANK = registerSoundEvents("vesselplate_clank");
    public static final RegistryObject<SoundEvent> VULTA_SHATTERED = registerSoundEvents("vulta_shattered");
    public static final RegistryObject<SoundEvent> TOILET_FLUSH = registerSoundEvents("toilet_flush");
    public static final RegistryObject<SoundEvent> METALPIPEFALLINGSOUNDEFFECT = registerSoundEvents("metalpipefallingsoundeffect");

    public static final RegistryObject<SoundEvent> METAL_BOX_OPEN = registerSoundEvents("metal_box_opening");
    public static final RegistryObject<SoundEvent> METAL_BOX_CLOSE = registerSoundEvents("metal_box_closing");

    public static final ForgeSoundType VESSELPLATE_BLOCK_SOUNDS = new ForgeSoundType(2f,1.5f,
            ModSounds.VESSELPLATE_CLANK, //Break
            ModSounds.VESSELPLATE_CLANK, //Step
            ModSounds.VESSELPLATE_CLANK, //Fall
            ModSounds.VESSELPLATE_CLANK, //Place
            ModSounds.VESSELPLATE_CLANK //Hit
            );

    private static RegistryObject<SoundEvent> registerSoundEvents(String soundFileName) {
        return SOUND_EVENTS.register(soundFileName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(IndustrialHellscape.MOD_ID, soundFileName)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
