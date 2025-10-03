package net.boat.industrialhellscape.item.modded_items;


import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class InHellTool extends Item {
    public InHellTool(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nonnull InteractionResult useOn(UseOnContext pContext) {
        if(!pContext.getLevel().isClientSide()) {
            BlockPos positionClicked = pContext.getClickedPos();
            Player player = pContext.getPlayer();

            //Text display
            if(player != null) player.displayClientMessage(Component.translatable("hud.industrialhellscape.block_not_interactable"), true);

            //Play Sound
            pContext.getLevel().playSeededSound(null, positionClicked.getX(), positionClicked.getY(), positionClicked.getZ(),
                    SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 1f, 10f, 0);
        }

        return InteractionResult.SUCCESS;

    }

    @Override
    //Allows item to have a sneak-interact functionality on blocks that allow that
    //Default sneak-behavior bypasses any interaction. Using a tool with doesSneakBypassUse circumvents this.
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return true;
    }

    //Item has tooltip text capability.
    @Override
    public void appendHoverText(@Nonnull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @Nonnull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.industrialhellscape.haventool"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    //Item remains in crafting grid when used to craft something, from the two methods below.
    @Nonnull
    public ItemStack getCraftingRemainingItem(@Nonnull ItemStack stack) {
        return new ItemStack(this);
    }

    public boolean hasCraftingRemainingItem(@Nonnull ItemStack stack) {
        return true;
    }
}