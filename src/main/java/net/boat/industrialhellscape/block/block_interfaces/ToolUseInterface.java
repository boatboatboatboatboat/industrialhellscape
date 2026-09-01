package net.boat.industrialhellscape.block.block_interfaces;

import net.boat.industrialhellscape.ModCommonConfig;
import net.boat.industrialhellscape.block.block_classes.RailingBlocks.ParapetBlock;
import net.boat.industrialhellscape.block.block_classes.RailingBlocks.RailingBlock;
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

import static net.boat.industrialhellscape.block.block_classes.RailingBlocks.ParapetBlock.*;

public interface ToolUseInterface {
    static boolean checkForWrench(ItemStack stack) {
        return (ModCommonConfig.taggedWrenchCompatEnabled() && stack.is(ModTags.Items.COMMON_WRENCH_TAG)) || stack.is(ModItems.INHELL_HAVEN_DEVICE.get());
    }

    static boolean checkForPickaxe(ItemStack stack) {
        return (ModCommonConfig.taggedPickaxeCompatEnabled() && stack.is(ModTags.Items.VANILLA_PICKAXE_TAG)) || stack.is(ModItems.INHELL_HAVEN_DEVICE.get());
    }

    static boolean checkForEnabledTool(ItemStack stack) {
        return checkForWrench(stack) || checkForPickaxe(stack);
    }

    static ItemInteractionResult simpleToolUse(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, Property<?> cycleProperty, int flag) {
        if(checkForEnabledTool(stack)) {
            state = state.cycle(cycleProperty);
            level.setBlock(pos, state, flag);
            level.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.25f, 1f);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    static ItemInteractionResult crouchToolUse(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, Property<?> propertyChangedWhenCrouched, int flag1, Property<?> propertyChangedWhenStanding, int flag2) {
        boolean playerIsCrouching = player.isCrouching();

        if(checkForEnabledTool(stack)) {
            level.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.25f, 1f);

            if(playerIsCrouching) {
                state = state.cycle(propertyChangedWhenCrouched);
                level.setBlock(pos, state, flag1);

            } else {
                state = state.cycle(propertyChangedWhenStanding);
                level.setBlock(pos, state, flag2);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    static ItemInteractionResult RailingRotationToolUse(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, int flag) {
        if(state.getBlock() instanceof RailingBlock) {
            if(checkForEnabledTool(stack)) {
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
