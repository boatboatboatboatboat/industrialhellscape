package net.boat.industrialhellscape.block.modded_interfaces;

import net.boat.industrialhellscape.block.modded_block_entities.StorageBE.StorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public interface StorageBlockInterface {

    //--------- NECESSARY FIELDS FOR BLOCK ENTITY ----------
    //Blocks that have a Storage Block Entity MUST possess these methods and supply a value.
    //The block entity assumes blocks passed to it possess this interface, and will use these methods to read off values.
    int getSlotCount();
    Supplier<SoundEvent> getOpenSound();
    Supplier<SoundEvent> getCloseSound();
    //---------- END OF NECESSARY FIELDS FOR BLOCK ENTITY ----------

    static void dropContainerInventory(Block block, BlockState state, Level level, BlockPos pos, BlockState newState) {
        //Occurs when block is mined. Items should drop.
        if(newState.getBlock() != state.getBlock() ) {
            Containers.dropContentsOnDestroy(state, newState, level, pos);
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
