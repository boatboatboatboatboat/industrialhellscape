package net.boat.industrialhellscape.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

//THIS JAVA CLASS HANDLES FOOD ITEM PROPERTIES

public class ModFoods {
    public static final FoodProperties ASPIC = new FoodProperties.Builder()
            .nutrition(6)
            .saturationMod(0.6f)
            .meat()
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 200),0.33f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200),0.33f)
            .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200),0.33f)
            .build();
}
