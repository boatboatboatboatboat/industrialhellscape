package net.boat.industrialhellscape.block.modded_interfaces;

import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public interface ToolUseCapability {
    //Allows player holding an applicable tool to cycle either of two Block-State properties based on whether they are crouching.
    //For example: being able to cycle Direction "FACING" or AttachFace "ATTACH FACE" for certain blocks

    static InteractionResult StandCrouchToolInteract(Property<?> standUseProperty, Property<?> crouchUseProperty, Player pPlayer, BlockState pState, Level pLevel, BlockPos pPos) {

        boolean playerHasTool = pPlayer.getMainHandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS) || pPlayer.getOffhandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS);
        boolean playerIsCrouching = pPlayer.isCrouching();

        if(playerHasTool && playerIsCrouching) {
            pState = pState.cycle(crouchUseProperty); //Property that is changed when CROUCHED
            pLevel.setBlock(pPos, pState, 2); //2
            ToolUseCapability.playInteractSound(pLevel, pPos);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);

        } else if (playerHasTool) {
            pState = pState.cycle(standUseProperty); //Property that is changed when STANDING
            pLevel.setBlock(pPos, pState, 2); //2
            ToolUseCapability.playInteractSound(pLevel, pPos);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    static InteractionResult SimpleToolInteract(Property<?> useProperty, Player pPlayer, BlockState pState, Level pLevel, BlockPos pPos) {
        boolean playerHasTool = pPlayer.getMainHandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS) || pPlayer.getOffhandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS);

        if (playerHasTool) {
            pState = pState.cycle(useProperty);
            pLevel.setBlock(pPos, pState, 2); //2, update the block, but not neighboring blocks
            ToolUseCapability.playInteractSound(pLevel, pPos);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    static InteractionResult SimpleHandInteract(Property<?> useProperty, Player pPlayer, BlockState pState, Level pLevel, BlockPos pPos) {
        boolean playerHandsEmpty = pPlayer.getMainHandItem().isEmpty() || pPlayer.getOffhandItem().isEmpty();

        if (playerHandsEmpty) {
            pState = pState.cycle(useProperty);
            pLevel.setBlock(pPos, pState, 2); //2, update the block, but not neighboring blocks
            ToolUseCapability.playInteractSound(pLevel, pPos);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    static InteractionResult ModdedToolInteract(Property<?> useProperty, Player pPlayer, BlockState pState, Level pLevel, BlockPos pPos) {
        boolean playerHasTool = pPlayer.getMainHandItem().is(ModTags.Items.IH_COMPATIBLE_MODDED_TOOLS) || pPlayer.getOffhandItem().is(ModTags.Items.IH_COMPATIBLE_MODDED_TOOLS);

        if (playerHasTool) {
            pState = pState.cycle(useProperty);
            pLevel.setBlock(pPos, pState, 2); //2, update the block, but not neighboring blocks
            ToolUseCapability.playInteractSound(pLevel, pPos);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    static void playInteractSound(Level pLevel, BlockPos pPos) {
        //Plays stonecutter sound when interaction successful and this method is called
        pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.1f, 1f, 0);
    }
}
