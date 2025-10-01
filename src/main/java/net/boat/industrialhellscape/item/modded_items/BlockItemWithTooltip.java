package net.boat.industrialhellscape.item.modded_items;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockItemWithTooltip extends BlockItem {
        protected String itemName;
    public BlockItemWithTooltip(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
        this.itemName = BuiltInRegistries.BLOCK.getKey(pBlock).getPath();
    }

    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.industrialhellscape."+itemName+".flavortext"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
