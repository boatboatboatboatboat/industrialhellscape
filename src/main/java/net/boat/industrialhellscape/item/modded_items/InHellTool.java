package net.boat.industrialhellscape.item.modded_items;


import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (level.isClientSide) {
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }
        player.openMenu(getMenuProvider(level,player.blockPosition(), ItemStack.EMPTY));
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    public static StonecutterMenu getStonecutterMenu(int i, Inventory inventory, Level level, BlockPos pos, ItemStack itemStack){
        return new StonecutterMenu(i, inventory, ContainerLevelAccess.create(level, pos)){
            {
                this.getSlot(0).set(itemStack);
            }

            @Override
            public boolean stillValid(Player _player) {
                return true;
            }

        };
    }

    public MenuProvider getMenuProvider(Level level, BlockPos pos, ItemStack itemStack){
        return new SimpleMenuProvider((i, inventory, _player) -> getStonecutterMenu(i,inventory,level,pos,itemStack) , getDescription());
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