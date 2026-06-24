package net.boat.industrialhellscape.item.modded_items;


import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

//Stonecutter implementation on a handheld item - Sourced from Minecraft Mod "Yuushya Townscape" (MIT licensed)

public class InHellToolItem extends Item {
    public InHellToolItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @Nonnull InteractionResultHolder<ItemStack> use(Level level, @Nonnull Player player, @Nonnull InteractionHand usedHand) {
        if (level.isClientSide) {
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }
        //Opens menu, the input slot of the stonecutter will initially be empty
        player.openMenu( getMenuProvider(level, player.blockPosition(), ItemStack.EMPTY) );
        return InteractionResultHolder.consume(player.getItemInHand(usedHand));
    }

    public MenuProvider getMenuProvider(Level level, BlockPos pos, ItemStack itemStack){
        return new SimpleMenuProvider((i, inventory, pPlayer) -> getStonecutterMenu(i,inventory,level,pos,itemStack) , getDescription());
    }

    public static StonecutterMenu getStonecutterMenu(int i, Inventory inventory, Level level, BlockPos pos, ItemStack itemStack){
        return new StonecutterMenu(i, inventory, ContainerLevelAccess.create(level, pos)){
            {
                this.getSlot(0).set(itemStack);
            }

            @Override
            //GUI menu will always be on until player hits escape key.
            public boolean stillValid(@Nonnull Player pPlayer) {
                return true;
            }

        };
    }

    @Override
    //Allows item to have a sneak-interact functionality on blocks that allow that
    //Default sneak-behavior bypasses any interaction. Using a tool with doesSneakBypassUse circumvents this.
    public boolean doesSneakBypassUse(@NotNull ItemStack stack, @NotNull LevelReader level, @NotNull BlockPos pos, @NotNull Player player) {
        return true;
    }

    //Item has tooltip text capability.
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        if (Screen.hasShiftDown()) {
            //Expand tooltip if shift-key is down while hovering over item in a GUI.
            tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.haventool"));
        } else {
            //Minimize tooltip by default.
            tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.shift_down"));
        }
        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }

    //Item remains in crafting grid when used to craft something, from the two methods below.
    public @NotNull ItemStack getCraftingRemainingItem(@Nonnull ItemStack stack) {
        return new ItemStack(this);
    }

    public boolean hasCraftingRemainingItem(@Nonnull ItemStack stack) {
        return true;
    }
}