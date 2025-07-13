package net.boat.industrialhellscape.item.special_items;


import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class InHellTool extends Item {
    public InHellTool(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(!pContext.getLevel().isClientSide()) {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();

            pContext.getLevel().playSeededSound(null, positionClicked.getX(), positionClicked.getY(), positionClicked.getZ(),
                    ModSounds.VESSELPLATE_PLACE.get(), SoundSource.BLOCKS, 1f, 1f, 0);
        }
        pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                player -> player.broadcastBreakEvent(player.getUsedItemHand()));

        return InteractionResult.SUCCESS;

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.industrialhellscape.haventool.flavortext"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Nonnull
    public ItemStack getCraftingRemainingItem(@Nonnull ItemStack stack) {
        return new ItemStack(this);
    }

    public boolean hasCraftingRemainingItem(@Nonnull ItemStack stack) {
        return true;
    }
}