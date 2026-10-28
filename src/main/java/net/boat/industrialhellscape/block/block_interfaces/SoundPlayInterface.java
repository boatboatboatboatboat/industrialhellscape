package net.boat.industrialhellscape.block.block_interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface SoundPlayInterface {
    static void onOffSoundWithUpdateCheck(BlockState state, BlockState updatedState, Level level, BlockPos pos, BooleanProperty booleanProperty, SoundEvent onSound, SoundEvent offSound, float volume, float onPitch, float offPitch) {
        if(updatedState.getValue(booleanProperty) != state.getValue(booleanProperty)) {
            onOffSound(state,level,pos,booleanProperty,onSound,offSound,volume,onPitch,offPitch);
        }
    }

    static void onOffSound(BlockState state, Level level, BlockPos pos, BooleanProperty booleanProperty, SoundEvent onSound, SoundEvent offSound, float volume, float onPitch, float offPitch) {
        /*
            Call this helper method after state variable has been updated by player interaction, or else wrong sound will play.
            - If Light Block is off and this method reads the currently "off" state, it will play the "off" sound.
            If state updating logic is handled in helper methods or interface methods,
                reverse the order of on/off Sound and on/off pitch parameters.
        */
        boolean isLit = state.getValue(booleanProperty);
        float pitch = isLit ? onPitch : offPitch;
        SoundEvent soundToPlay = isLit ? onSound : offSound;
        level.playSound(null, pos, soundToPlay, SoundSource.BLOCKS, volume, pitch);
    }

    static void playSoundBE(SoundEvent soundEvent, BlockEntity blockEntity) {
        BlockPos bePos = blockEntity.getBlockPos();

        double xPos = bePos.getX() + 0.5D;
        double yPos = bePos.getY() + 0.5D;
        double zPos = bePos.getZ() + 0.5D;

        Level level = blockEntity.getLevel();
        if(level != null){
            level.playSound(null, xPos, yPos, zPos, soundEvent, SoundSource.BLOCKS, 0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F);
        }
    }
}
