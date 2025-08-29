package net.boat.industrialhellscape.item.special_items;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

//INFO:
//-----
//This object class is a RecordItem that allows a custom tooltip to be defined per item during mod item registration, via an extra argument
//-----

public class RecordItemWithTooltip extends RecordItem {

    //Tooltip text is based on the "itemName" and will be different per item accordingly if its entry in the lang file is present.
    protected String itemName;
    public RecordItemWithTooltip(String itemName, int comparatorValue, Supplier<SoundEvent> soundSupplier, Properties builder, int lengthInTicks) {
        super(comparatorValue, soundSupplier, builder, lengthInTicks);
        this.itemName = itemName;
    }
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.industrialhellscape."+itemName+".flavortext"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
