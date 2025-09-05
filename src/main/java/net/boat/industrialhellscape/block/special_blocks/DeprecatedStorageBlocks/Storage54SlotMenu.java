package net.boat.industrialhellscape.block.special_blocks.DeprecatedStorageBlocks;

import net.boat.industrialhellscape.block.special_blocks_properties.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class Storage54SlotMenu extends AbstractContainerMenu {
    protected final Storage54SlotBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;

    private int rows = 6;
    private int columns = 9;

    // Client Constructor
    public Storage54SlotMenu(int containerId, Inventory playerInv, FriendlyByteBuf additionalData) {
        this(containerId, playerInv, playerInv.player.level().getBlockEntity(additionalData.readBlockPos()));
    }

    // Server Constructor
    public Storage54SlotMenu(int containerId, Inventory playerInv, BlockEntity blockEntity) {
        super(ModMenuTypes.STORAGE_54SLOT_MENU.get(), containerId);
        if(blockEntity instanceof Storage54SlotBlockEntity be) {
            this.blockEntity = be;
        } else {
            throw new IllegalStateException("Incorrect block entity class (%s) passed into Storage54SlotMenu!"
                    .formatted(blockEntity.getClass().getCanonicalName()));
        }

        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        createPlayerHotbar(playerInv);
        createPlayerInventory(playerInv);
        createBlockEntityInventory(be);
    }

    private void createBlockEntityInventory(Storage54SlotBlockEntity be) {
        be.getOptional().ifPresent(inventory -> {
            for (int row = 0; row < rows; row++) {
                for (int column = 0; column < columns; column++) {
                    addSlot(new SlotItemHandler(inventory,
                            column + (row * columns),
                            8 + (column * 18),
                            18 + (row * 18)));
                }
            }
        });
    }

    //DO NOT TOUCH
    private void createPlayerInventory(Inventory playerInv) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInv,
                        9 + column + (row * 9),
                        8 + (column * 18),
                        140 + (row * 18)));
            }
        }
    }

    //DO NOT TOUCH
    private void createPlayerHotbar(Inventory playerInv) {
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInv,
                    column,
                    8 + (column * 18),
                    198));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        Slot fromSlot = getSlot(pIndex);
        ItemStack fromStack = fromSlot.getItem();

        if(fromStack.getCount() <= 0)
            fromSlot.set(ItemStack.EMPTY);

        if(!fromSlot.hasItem())
            return ItemStack.EMPTY;

        ItemStack copyFromStack = fromStack.copy();

        if(pIndex < 36) {
            // We are inside of the player's inventory
            if(!moveItemStackTo(fromStack, 36, 36+rows*columns, false))
                return ItemStack.EMPTY;
        } else if (pIndex < 36+rows*columns) {
            // We are inside of the block entity inventory
            if(!moveItemStackTo(fromStack, 0, 36, false))
                return ItemStack.EMPTY;
        } else {
            System.err.println("Invalid slot index: " + pIndex);
            return ItemStack.EMPTY;
        }

        fromSlot.setChanged();
        fromSlot.onTake(pPlayer, fromStack);

        return copyFromStack;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(this.levelAccess, pPlayer, this.blockEntity.getBlockState().getBlock());
    }

}