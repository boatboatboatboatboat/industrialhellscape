package net.boat.industrialhellscape.block.block_entities.StorageBE;

import net.boat.industrialhellscape.block.block_entities.ModBlockEntities;
import net.boat.industrialhellscape.block.block_interfaces.SoundPlayInterface;
import net.boat.industrialhellscape.block.block_interfaces.StorageBlockInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

/*
    NOTES:
    - Load BlockPos NBT data: inside loadAdditional(): NbtUtils.readBlockPos(tag, "be_pos").ifPresent(pos -> this.originBlockPos = pos);
    - Save BlockPos NBT data: inside saveAdditional(): tag.put("be_pos", NbtUtils.writeBlockPos(this.originBlockPos));

    //Codec.INT.encodeStart(NbtOps.INSTANCE, this.originBlockPos.);
    //Source: https://forums.minecraftforge.net/topic/99484-solved-116-storing-arraylist-to-nbt/  (5 years ago)

 */

public class StorageBE extends RandomizableContainerBlockEntity implements StorageBlockInterface, SoundPlayInterface {
    private final int SLOTS;
    public final SoundEvent OPEN_SOUND;
    public final SoundEvent CLOSE_SOUND;

    private NonNullList<ItemStack> items;
    private final ContainerOpenersCounter openersCounter;

    public StorageBE(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.STORAGE_BE.get(), pos, blockState);

        //Populate Block Entity data with arguments from registered Block
        Block block = blockState.getBlock();
        if (block instanceof StorageBlockInterface storageBlock) { //If block uses StorageBlockInterface. assign its slots and sound effects with its interface methods.
            this.SLOTS = storageBlock.getSlotCount();
            this.OPEN_SOUND = storageBlock.getOpenSound();
            this.CLOSE_SOUND = storageBlock.getCloseSound();
        } else { // Else, fall-back to a non-null slot amount, and default barrel sound effects.
            this.SLOTS = 9; //Placeholder
            this.OPEN_SOUND = SoundEvents.BARREL_OPEN;
            this.CLOSE_SOUND = SoundEvents.BARREL_CLOSE;
        }

        this.items = NonNullList.withSize(SLOTS, ItemStack.EMPTY);
        this.openersCounter = new ContainerOpenersCounter() {
            protected void onOpen(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
                SoundPlayInterface.playSoundBE(OPEN_SOUND,StorageBE.this);
                //StorageBE.this.updateBlockState(state, true);
                // In the future, use the interface to update a block's state for special effects like lids opening
            }
            protected void onClose(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
                SoundPlayInterface.playSoundBE(CLOSE_SOUND,StorageBE.this);
                //StorageBE.this.updateBlockState(state, false);
                // In the future, use the interface to update a block's state for special effects like lids opening
            }

            protected void openerCountChanged(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, int p_155069_, int p_155070_) {
            }

            protected boolean isOwnContainer(@NotNull Player player) {
                if (player.containerMenu instanceof ChestMenu) {
                    Container container = ((ChestMenu)player.containerMenu).getContainer();
                    return container == StorageBE.this;
                } else {
                    return false;
                }
            }
        };
    }

    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.trySaveLootTable(tag)) {
            ContainerHelper.saveAllItems(tag, this.items, registries);
        }
    }

    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);


        if (!this.tryLoadLootTable(tag)) {
            ContainerHelper.loadAllItems(tag, this.items, registries);
        }
    }

    @Override
    public int getContainerSize() { return SLOTS; }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() { return this.items; }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> items) { this.items = items; }

    @Override
    protected @NotNull Component getDefaultName() { return Component.translatable("gui.industrialhellscape.inventory_menu"); }

    @Override
    public @NotNull AbstractContainerMenu createMenu(int id, @NotNull Inventory inventory) {
        //GUI appearance (vanilla menu/screens) based on inventory slot capacity chosen during block registration
        return switch (SLOTS) {
            case (18) -> new ChestMenu(MenuType.GENERIC_9x2, id, inventory, this, 2);
            case (27) -> new ChestMenu(MenuType.GENERIC_9x3, id, inventory, this, 3);
            case (54) -> new ChestMenu(MenuType.GENERIC_9x6, id, inventory, this, 6);
            default -> new ChestMenu(MenuType.GENERIC_9x1, id, inventory, this, 1); //Defaults to assuming block has 9 slot inventory
        };
    }

    // ----- CompoundContainer Class Methods -----
    public void startOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void stopOpen(@NotNull Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }
    // ----- End of CompoundContainer Class Methods -----

    // ----- Capabilities -----
    public IItemHandler getItemCapability() {return new InvWrapper(this);}
    // ----- End of Capabilities -----

    // ----- StorageBlockInterface Methods -----

    @Override
    public int getSlotCount() {return SLOTS;}

    @Override
    public SoundEvent getOpenSound() {return OPEN_SOUND;}

    @Override
    public SoundEvent getCloseSound() {return CLOSE_SOUND;}

    // ----- End of StorageBlockInterface Methods -----
}
