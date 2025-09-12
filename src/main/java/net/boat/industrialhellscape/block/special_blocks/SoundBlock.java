package net.boat.industrialhellscape.block.special_blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.client.model.IModelBuilder;

public class SoundBlock extends Block {


    public SoundBlock(Properties pProperties) {
        super(pProperties);
    }

    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks
        pLevel.playLocalSound(pPos,SoundEvents.ENDERMAN_DEATH, SoundSource.BLOCKS, 1,1,true);
    }

    @Override
    public VoxelShape getInteractionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return super.getInteractionShape(pState, pLevel, pPos);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        pLevel.playSound(pPlayer, pPos, SoundEvents.NOTE_BLOCK_DIDGERIDOO.get(), SoundSource.BLOCKS,
               1f, 1f);
        return InteractionResult.SUCCESS;
    }
}
