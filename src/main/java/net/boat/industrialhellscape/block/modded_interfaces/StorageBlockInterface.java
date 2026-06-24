package net.boat.industrialhellscape.block.modded_interfaces;

import net.boat.industrialhellscape.block.modded_block_entities.StorageBE.StorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public interface StorageBlockInterface {

    //--------- NECESSARY FIELDS FOR BLOCK ENTITY ----------
    //Blocks that have a Storage Block Entity and implements this interface MUST possess these methods and supply a value.
    //The block entity assumes blocks passed to it possess this interface, and will use these methods to read off values.
    int getSlotCount();
    SoundEvent getOpenSound();
    SoundEvent getCloseSound();
    //---------- END OF NECESSARY FIELDS FOR BLOCK ENTITY ----------

    static void dropContainerInventory(Block block, BlockState state, Level level, BlockPos pos, BlockState newState) {
//        //Occurs when block is mined. Items should drop.
//        if(newState.getBlock() != state.getBlock() ) {
//            Containers.dropContents(level, pos, container);
//        }
        BlockEntity be = level.getBlockEntity(pos);

        if (!state.is(newState.getBlock())) {
            if (be instanceof Container container) { //For container-type block entities (RandomizableContainerBlockEntity)
                Containers.dropContents(level, pos, container);
                level.updateNeighbourForOutputSignal(pos, block);
            } else if(be !=null) { //For more basic block entities

                try {
                    Method possibleGetInventory = be.getClass().getMethod("getInventory"); //Search for a getInventory method in this block entity

                    //-----From TurtyWurty 1.20.1 Github-----

                    ItemStackHandler inventory = (ItemStackHandler) possibleGetInventory.invoke(be); //Attempt to read and store the block entity's inventory

                    for (int i = 0; i < inventory.getSlots(); i++) {
                        ItemStack stack = inventory.getStackInSlot(i); //One at a time, read and store each itemStack in each slot
                        if (!stack.isEmpty()) { //If that stack is NOT EMPTY
                            var entity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack); //spawn the current itemstack as an Item Entity inworld at this positon
                            level.addFreshEntity(entity); //Add a new entity
                        }
                    }
                    //----- End of Obtained Code -----

                } catch(Exception e) { //Should trip if somehow a getInventory method does not exist for the old block entities
                    System.out.println("This block entity is not an inventory Block Entity");
                }

            }
        }
    }
    static InteractionResult OpenContainerInventory(Level level, @NotNull BlockPos pos, @NotNull Player player) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof StorageBE) {
                player.openMenu((StorageBE)blockentity);
            }

            return InteractionResult.CONSUME;
        }
    }
}
