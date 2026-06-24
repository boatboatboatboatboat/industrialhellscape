package net.boat.industrialhellscape;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> HORSEPILL_DAMAGE =
            ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(IndustrialHellscape.MOD_ID, "horsepill_damage"));
}
