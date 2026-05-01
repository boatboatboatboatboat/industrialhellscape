package net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks;

import net.boat.industrialhellscape.block.modded_interfaces.ToolUseCapability;
import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class SmokeDetectorBlock extends ModelledSurfaceMountBlock{

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public SmokeDetectorBlock(Properties pProperties, VoxelShape floorHitBox, boolean ceilingSurfaceMountOnly) {
        super(pProperties, floorHitBox, ceilingSurfaceMountOnly);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(SURFACE_MOUNT, AttachFace.WALL)
                .setValue(POWERED, true) //Does not beep by default
                .setValue(WATERLOGGED, false));
    }

    public @Nonnull InteractionResult use(@Nonnull BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {
        //Interact successful with empty hand, toggling the boolean POWERED state
        //See modded interface ToolUseCapability for the list of methods used to handle common player/block interactions in this mod
        return ToolUseCapability.SimpleHandInteract(POWERED, pPlayer, pState, pLevel, pPos);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return !pState.getValue(POWERED); //If NOT powered, randomly tick
    }

    public void randomTick(@NotNull BlockState pState, ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        //If chunk is loaded, random tick the SmokeDetectorBlock if it is NOT powered
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (!pState.getValue(POWERED)) { //If NOT powered
            playSound(pLevel, pPos);
        }

    }

    private static void playSound(Level pLevel, BlockPos pPos) {
        //Plays stonecutter sound when interaction successful and this method is called
        pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                ModSounds.SMOKE_ALARM.get(), SoundSource.BLOCKS, 1f, 1f, 0);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, POWERED, SURFACE_MOUNT);
    }
}
