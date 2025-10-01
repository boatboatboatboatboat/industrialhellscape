package net.boat.industrialhellscape.block.modded_block_classes;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockState;

//Same as a normal LeverBlock but there is no particle effects upon activation.

public class ModdedLever extends LeverBlock {
    public ModdedLever(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        //No particle effect
    }
}
