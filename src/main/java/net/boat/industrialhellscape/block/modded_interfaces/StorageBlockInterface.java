package net.boat.industrialhellscape.block.modded_interfaces;

import net.boat.industrialhellscape.block.modded_block_entities.StorageBE.StorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public interface StorageBlockInterface {

    //--------- NECESSARY FIELDS FOR BLOCK ENTITY ----------
    //Blocks that have a Storage Block Entity and implements this interface MUST possess these methods and supply a value.
    //The block entity anticipates blocks passed to it possess this interface, and will use these methods to read off values.
    //From the block that were registered with the block instance
    int getSlotCount();
    SoundEvent getOpenSound();
    SoundEvent getCloseSound();
    //---------- END OF NECESSARY FIELDS FOR BLOCK ENTITY ----------

    static void dropContainerInventory(BlockState state, Level level, BlockPos pos) {
        //Stupid roundabout way to drop inventory to keep maintaining non-dupe compat with Aeronautics
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof Container container) {
            Containers.dropContents(level, pos, container);
            level.updateNeighbourForOutputSignal(pos, state.getBlock());
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
