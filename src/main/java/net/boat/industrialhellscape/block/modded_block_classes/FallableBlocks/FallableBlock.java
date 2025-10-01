package net.boat.industrialhellscape.block.modded_block_classes.FallableBlocks;

import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

//INFO:
//-----
// This block has gravity enabled. It will fall like sand if there are no blocks below it.
// Cardinal directional placement is supported.
// Custom interaction: Upon landing, a fixed custom sound will be played.
// For full blocks

public class FallableBlock extends FallingBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public FallableBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void onLand(Level pLevel, @Nonnull BlockPos pPos, @Nonnull BlockState pState, @Nonnull BlockState pReplaceableState, @Nonnull FallingBlockEntity pFallingBlock) {
        pLevel.playSound(null, pPos, SoundEvents.ANVIL_LAND, SoundSource.BLOCKS,
                1f, 1f);
    }
}
