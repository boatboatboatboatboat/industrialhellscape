package net.boat.industrialhellscape.block.special_blocks.StorageBlock;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.boat.industrialhellscape.block.special_blocks_properties.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Storage9SlotBlockEntity extends BlockEntity implements MenuProvider {
    private int rows = 3;
    private int columns = 3;

    private static final Component TITLE =
            Component.translatable("gui." + IndustrialHellscape.MOD_ID + ".inventory_menu");

    private final ItemStackHandler inventory = new ItemStackHandler(rows*columns) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            Storage9SlotBlockEntity.this.setChanged();
        }
    };

    private final LazyOptional<ItemStackHandler> optional = LazyOptional.of(() -> this.inventory);

    public Storage9SlotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.STORAGE_9_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        CompoundTag tutorialmodData = nbt.getCompound(IndustrialHellscape.MOD_ID);
        this.inventory.deserializeNBT(tutorialmodData.getCompound("Inventory"));
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag nbt) {
        super.saveAdditional(nbt);
        var tutorialmodData = new CompoundTag();
        tutorialmodData.put("Inventory", this.inventory.serializeNBT());
        nbt.put(IndustrialHellscape.MOD_ID, tutorialmodData);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.optional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.optional.invalidate();
    }

    public LazyOptional<ItemStackHandler> getOptional() {
        return this.optional;
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, Player pPlayer) {
        return new Storage9SlotMenu(pContainerId, pPlayerInventory, this);
    }
}