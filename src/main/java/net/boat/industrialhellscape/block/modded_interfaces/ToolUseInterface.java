package net.boat.industrialhellscape.block.modded_interfaces;

import net.boat.industrialhellscape.CommonModConfig;
import net.boat.industrialhellscape.item.ModItems;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

public interface ToolUseInterface {
    default InteractionResult simpleToolUse(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, Property<?> cycleProperty, int flag) {
            boolean checkForIHCompatTools = CommonModConfig.taggedPickaxeCompatEnabled() ? stack.is(ModTags.Items.IH_COMPATIBLE_TOOLS) : stack.is(ModItems.INHELL_HAVEN_DEVICE.get()) ;

            //IF TAGGED PICKAXE COMPAT ENABLED, ALLOW ALL COMPATIBLE TOOLS TO INTERACT WITH BLOCKS
            if(checkForIHCompatTools) {
                state = state.cycle(cycleProperty);
                level.setBlock(pos, state, flag);
                level.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.25f, 1f);
                return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

    default InteractionResult crouchToolUse(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, Property<?> cycleProperty1, int flag1, Property<?> cycleProperty2, int flag2) {
        boolean checkForIHCompatTools = CommonModConfig.taggedPickaxeCompatEnabled() ? stack.is(ModTags.Items.IH_COMPATIBLE_TOOLS) : stack.is(ModItems.INHELL_HAVEN_DEVICE.get()) ;
        boolean playerIsCrouching = player.isCrouching();

        //IF TAGGED PICKAXE COMPAT ENABLED, ALLOW ALL COMPATIBLE TOOLS TO INTERACT WITH BLOCKS
        if(checkForIHCompatTools) {
            level.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.25f, 1f);

            if(playerIsCrouching) {
                //Cycles connection type. Only works with modded tools
                state = state.cycle(cycleProperty1);
                level.setBlock(pos, state, flag1);

            } else {
                //Cycles from vertical and horizontal pipes when interacting with pipes on walls. Works with modded tools AND pickaxes
                state = state.cycle(cycleProperty2);
                level.setBlock(pos, state, flag2);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }

}
