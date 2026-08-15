package net.boat.industrialhellscape.item;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> HORSEPILL_DAMAGE =
            ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID, "horsepill_damage"));
}
