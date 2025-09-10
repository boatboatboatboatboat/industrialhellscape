package net.boat.industrialhellscape.block.special_blocks.ContainerBlock;

import net.boat.industrialhellscape.block.special_blocks_properties.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

//INFO:
//-----
//This block entity takes the SLOTS parameter passed from FacingContainerBlock, and loads an inventory with appropriate GUI based on that item slot capacity.
//Modified from Valhesia Furniture mod's Desk Drawer block entity code to allow a configurable inventory slot capacity determined during block registration. This avoids multiple block entity classes or registrations.
//-----

public class GenericContainerBE extends RandomizableContainerBlockEntity {

    private int SLOTS;
    public final SoundEvent OPEN_SOUND;
    public final SoundEvent CLOSE_SOUND;
    private NonNullList<ItemStack> items;

    public GenericContainerBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CONTAINER_BLOCK_ENTITY.get(), pos, state);
        Block block = state.getBlock();
        if (block instanceof FacingContainerBlock containerBlock) {
            this.SLOTS = containerBlock.SLOTS;
            this.OPEN_SOUND = containerBlock.OPEN_SOUND;
            this.CLOSE_SOUND = containerBlock.CLOSE_SOUND;
        } else {
            this.SLOTS = 1; //Placeholder
            this.OPEN_SOUND = SoundEvents.BARREL_OPEN;
            this.CLOSE_SOUND = SoundEvents.BARREL_CLOSE;
        }
        items = NonNullList.withSize(SLOTS, ItemStack.EMPTY);
    }

    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
            GenericContainerBE.this.playSound(state, OPEN_SOUND);
        }

        @Override
        protected void onClose(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
            GenericContainerBE.this.playSound(state, CLOSE_SOUND);
        }

        @Override
        protected void openerCountChanged(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, int count, int openCount) {
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof ChestMenu menu) {
                Container container = menu.getContainer();
                return container == GenericContainerBE.this;
            } else {
                return false;
            }
        }
    };

    @NotNull
    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> itemStacks) {
        this.items = itemStacks;
    }

    @NotNull
    @Override
    protected Component getDefaultName() {
        return Component.translatable("gui.industrialhellscape.inventory_menu");
    }

    @NotNull
    @Override
    protected AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory) {
        //Conditional GUI appearance based on inventory slot capacity chosen by registered block
        switch(SLOTS) {
            default: return new ChestMenu(MenuType.GENERIC_9x1, id, inventory, this, 1);
            case(18): return new ChestMenu(MenuType.GENERIC_9x2, id, inventory, this, 2);
            case(27): return new ChestMenu(MenuType.GENERIC_9x3, id, inventory, this, 3);
            case(54): return new ChestMenu(MenuType.GENERIC_9x3, id, inventory, this, 6);
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items);
        }
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);

        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items);
        }
    }

    @Override
    public int getContainerSize() {
        return SLOTS;
    }

    @Override
    public void startOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    @Override
    public void stopOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    private void playSound(BlockState state, SoundEvent soundEvent) {
        double d0 = this.worldPosition.getX() + 0.5D;
        double d1 = this.worldPosition.getY() + 0.5D;
        double d2 = this.worldPosition.getZ() + 0.5D;

        this.level.playSound(null, d0, d1, d2, soundEvent, SoundSource.BLOCKS, 0.5F, this.level.getRandom().nextFloat() * 0.1F + 0.9F);
    }
}