package net.boat.industrialhellscape.block.modded_interfaces;

import net.boat.industrialhellscape.ModCommonConfig;
import net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks.ParapetBlock;
import net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks.RailingBlock;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

import static net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks.ParapetBlock.*;

public interface ToolUseInterface {

    static boolean checkForIHCompatTools(ItemStack stack) {
    /*
    Helper method to check if tool is appropriate for triggering block interaction
    If config enabled for pickaxe tools, do a boolean check to see the player's hand has an item stack that is of that tag.
    If config disabled, only check for the mod's primary tool item.
     */
        return ModCommonConfig.taggedPickaxeCompatEnabled() ? stack.is(ModTags.Items.IH_COMPATIBLE_TOOLS) : stack.is(ModItems.INHELL_HAVEN_DEVICE.get()) ;
    }

    static ItemInteractionResult simpleToolUse(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, Property<?> cycleProperty, int flag) {
            //IF TAGGED PICKAXE COMPAT ENABLED, ALLOW ALL COMPATIBLE TOOLS TO INTERACT WITH BLOCKS
            if(checkForIHCompatTools(stack)) {
                state = state.cycle(cycleProperty);
                level.setBlock(pos, state, flag);
                level.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.25f, 1f);
                return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    static ItemInteractionResult crouchToolUse(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, Property<?> cycleProperty1, int flag1, Property<?> cycleProperty2, int flag2) {
        boolean playerIsCrouching = player.isCrouching();

        //IF TAGGED PICKAXE COMPAT ENABLED, ALLOW ALL COMPATIBLE TOOLS TO INTERACT WITH BLOCKS
        if(checkForIHCompatTools(stack)) {
            level.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.25f, 1f);

            if(playerIsCrouching) {
                //Cycles Property 1 if crouched
                state = state.cycle(cycleProperty1);
                level.setBlock(pos, state, flag1);

            } else {
                //Cycles Property 2 by default
                state = state.cycle(cycleProperty2);
                level.setBlock(pos, state, flag2);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    static ItemInteractionResult RailingRotationToolUse(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, int flag) {
        if(state.getBlock() instanceof RailingBlock) {
            if(checkForIHCompatTools(stack)) {
                boolean north = state.getValue(NORTH_FENCE);
                boolean south = state.getValue(SOUTH_FENCE);
                boolean east = state.getValue(EAST_FENCE);
                boolean west = state.getValue(WEST_FENCE);

                state = state.setValue(NORTH_FENCE, west);
                state = state.setValue(WEST_FENCE, south);
                state = state.setValue(SOUTH_FENCE, east);
                state = state.setValue(EAST_FENCE, north);

                level.setBlock(pos, state, flag);

                if(state.getBlock() instanceof ParapetBlock) {
                    //parapet outer corners rotate with the rest of the block to prevent corner model overlap with main block model
                    boolean nw = state.getValue(NORTH_WEST_FENCE);
                    boolean sw = state.getValue(SOUTH_WEST_FENCE);
                    boolean se = state.getValue(SOUTH_EAST_FENCE);
                    boolean ne = state.getValue(NORTH_EAST_FENCE);

                    state = state.setValue(NORTH_WEST_FENCE, sw);
                    state = state.setValue(SOUTH_WEST_FENCE, se);
                    state = state.setValue(SOUTH_EAST_FENCE, ne);
                    state = state.setValue(NORTH_EAST_FENCE, nw);

                    level.setBlock(pos, state, flag);
                }

                level.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.25f, 1f);

                return ItemInteractionResult.sidedSuccess(level.isClientSide);
            }
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

}
