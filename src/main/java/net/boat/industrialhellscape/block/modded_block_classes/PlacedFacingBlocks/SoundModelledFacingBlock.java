package net.boat.industrialhellscape.block.modded_block_classes.PlacedFacingBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SoundModelledFacingBlock extends ModelledFacingBlock {

    public final Supplier<SoundEvent> INTERACT_SOUND;

    public SoundModelledFacingBlock(Properties pProperties, VoxelShape soloShape, Supplier<SoundEvent> interactSound) {
        super(pProperties, soloShape);
        this.INTERACT_SOUND = interactSound;
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        level.playSound(player, pos, INTERACT_SOUND.get(), SoundSource.BLOCKS, 1f, 1f);
        return InteractionResult.sidedSuccess(level.isClientSide);

    }

}
