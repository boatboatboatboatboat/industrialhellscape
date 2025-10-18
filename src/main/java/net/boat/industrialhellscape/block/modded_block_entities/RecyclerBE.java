package net.boat.industrialhellscape.block.modded_block_entities;

import net.boat.industrialhellscape.screen.RecyclerMenu;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RecyclerBE extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4); //Two GUI item slots that may or may not contain items

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT1 = 1;
    private static final int OUTPUT_SLOT2 = 2;
    private static final int OUTPUT_SLOT3 = 3;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 25;

    public RecyclerBE(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.RECYCLER_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> RecyclerBE.this.progress;
                    case 1 -> RecyclerBE.this.maxProgress;
                    case 2 -> RecyclerBE.this.maxProgress;
                    case 3 -> RecyclerBE.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> RecyclerBE.this.progress = pValue;
                    case 1 -> RecyclerBE.this.maxProgress = pValue;
                    case 2 -> RecyclerBE.this.maxProgress = pValue;
                    case 3 -> RecyclerBE.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 4; //One input slot, one output slot
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Recycler Machine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new RecyclerMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT()); //when world is saved, save the item contents of block under "inventory" NBT tag
        pTag.putInt("recycler.progress", progress); //when world is saved, save the progress value under "recycler.progress" NBT tag

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory")); //Load the "inventory" tag defined above on world load
        progress = pTag.getInt("recycler.progress"); //Load the "recycler.progress" tag defined above on world load
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(hasInputItem()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()) {
                recycleInputItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void recycleInputItem() {
        ItemStack resultIronIngot = new ItemStack(Items.IRON_INGOT, 1); //Result is +one OUTPUT ITEM when craft is complete
        ItemStack resultStone = new ItemStack(Items.STONE, 1); //Result is +one OUTPUT ITEM when craft is complete
        ItemStack resultGlass = new ItemStack(Items.GLASS, 1); //Result is +one OUTPUT ITEM when craft is complete

        boolean hasVesselplate = this.itemHandler.getStackInSlot(INPUT_SLOT).is(ModTags.Items.VESSELPLATE_SMELTABLE_ITEM);
        boolean hasStrut = this.itemHandler.getStackInSlot(INPUT_SLOT).is(ModTags.Items.STRUT_SMELTABLE_ITEM);
        boolean hasHvac = this.itemHandler.getStackInSlot(INPUT_SLOT).is(ModTags.Items.HVAC_SMELTABLE_ITEM);
        boolean hasVesselglass = this.itemHandler.getStackInSlot(INPUT_SLOT).is(ModTags.Items.VESSELGLASS_SMELTABLE_ITEM);

        boolean hasRockrete = this.itemHandler.getStackInSlot(INPUT_SLOT).is(ModTags.Items.ROCKRETE_SMELTABLE_ITEM);

        //Each time an item is crafted, get the existing amount of output from the OUTPUT_SLOT, add the result to it
        if(hasVesselplate || hasStrut || hasHvac) {
            this.itemHandler.extractItem(INPUT_SLOT, 1, false);
            this.itemHandler.setStackInSlot(OUTPUT_SLOT1, new ItemStack(resultIronIngot.getItem(),
                    this.itemHandler.getStackInSlot(OUTPUT_SLOT1).getCount() + resultIronIngot.getCount())); }

        else if(hasRockrete) {
            this.itemHandler.extractItem(INPUT_SLOT, 1, false);
            this.itemHandler.setStackInSlot(OUTPUT_SLOT2, new ItemStack(resultStone.getItem(),
                    this.itemHandler.getStackInSlot(OUTPUT_SLOT2).getCount() + resultStone.getCount())); }

        else if(hasVesselglass) {
            this.itemHandler.extractItem(INPUT_SLOT, 4, false); //HasInputItem needs to check if there is >4 vesselglass
            this.itemHandler.setStackInSlot(OUTPUT_SLOT3, new ItemStack(resultGlass.getItem(),
                    this.itemHandler.getStackInSlot(OUTPUT_SLOT3).getCount() + resultGlass.getCount()));
            this.itemHandler.setStackInSlot(OUTPUT_SLOT1, new ItemStack(resultIronIngot.getItem(),
                    this.itemHandler.getStackInSlot(OUTPUT_SLOT1).getCount() + resultIronIngot.getCount())); }
    }

    private boolean hasInputItem() {
        //Is there INPUT ITEMS in the input slot?
        boolean hasVesselplate = this.itemHandler.getStackInSlot(INPUT_SLOT).is(ModTags.Items.VESSELPLATE_SMELTABLE_ITEM);
        boolean hasStrut = this.itemHandler.getStackInSlot(INPUT_SLOT).is(ModTags.Items.STRUT_SMELTABLE_ITEM);
        boolean hasHvac = this.itemHandler.getStackInSlot(INPUT_SLOT).is(ModTags.Items.HVAC_SMELTABLE_ITEM);
        boolean hasVesselglass = this.itemHandler.getStackInSlot(INPUT_SLOT).is(ModTags.Items.VESSELGLASS_SMELTABLE_ITEM) && ( this.itemHandler.getStackInSlot(INPUT_SLOT).getCount() >= 4 );

        boolean hasRockrete = this.itemHandler.getStackInSlot(INPUT_SLOT).is(ModTags.Items.ROCKRETE_SMELTABLE_ITEM);

        //One item stack containing one OUTPUT item
        ItemStack resultIronIngot = new ItemStack(Items.IRON_INGOT);
        ItemStack resultStone = new ItemStack(Items.STONE);
        ItemStack resultGlass = new ItemStack(Items.GLASS);

        //Outputs iron ingot in 1st output slot
        if(hasVesselplate || hasStrut || hasHvac) {return canInsertResultIntoOutput(resultIronIngot, OUTPUT_SLOT1);}
        //Outputs stone in 2nd output slot
        else if(hasRockrete) {return canInsertResultIntoOutput(resultStone, OUTPUT_SLOT2);}
        else if(hasVesselglass) {return canInsertResultIntoOutput(resultIronIngot, OUTPUT_SLOT1) && canInsertResultIntoOutput(resultGlass, OUTPUT_SLOT3);}
        else {return false;}

        //There is an eligible recipe (true) to operate only if the following three conditions are true:
        // - There is INPUT ITEMs in the input
        // - There is less OUTPUT ITEMs than the max stack size (64 usually)
        // - The output slot is either empty, or has OUTPUT ITEMS inside it already
    }

    private boolean canInsertResultIntoOutput(ItemStack result, int outputSlot) {
        return canInsertItemIntoOutputSlot(result.getItem(), outputSlot) && canInsertAmountIntoOutputSlot(result.getCount(), outputSlot);
    }

    private boolean canInsertItemIntoOutputSlot(Item item, int outputSlot) {
        //You can insert an item into the output slot
        return this.itemHandler.getStackInSlot(outputSlot).isEmpty() || this.itemHandler.getStackInSlot(outputSlot).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count, int outputSlot) {
        //You can insert any amount of items into the output slot up to the max stack size of the item
        return this.itemHandler.getStackInSlot(outputSlot).getCount() + count <= this.itemHandler.getStackInSlot(outputSlot).getMaxStackSize();
    }

    //PROGRESS INCREMENTERS AND RESETTERS
    private void resetProgress() {
        progress = 0;
    }
    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }
    private void increaseCraftingProgress() {
        progress++;
    }
}
