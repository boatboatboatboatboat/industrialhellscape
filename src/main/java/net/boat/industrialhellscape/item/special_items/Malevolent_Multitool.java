
package net.boat.industrialhellscape.item.special_items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class Malevolent_Multitool extends Item {
    public Malevolent_Multitool(Properties pProperties) {
        super(pProperties);
    }

    @Nonnull
    public ItemStack getCraftingRemainingItem(@Nonnull ItemStack stack) {
        return new ItemStack(this);
    }

    public boolean hasCraftingRemainingItem(@Nonnull ItemStack stack) {
        return true;
    }
}
