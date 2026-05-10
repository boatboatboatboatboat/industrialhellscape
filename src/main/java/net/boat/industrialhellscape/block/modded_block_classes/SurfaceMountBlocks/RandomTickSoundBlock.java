package net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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

import java.util.function.Supplier;

public class RandomTickSoundBlock extends ModelledSurfaceMountBlock{

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public final Supplier<SoundEvent> OUTPUT_SOUND;

    public RandomTickSoundBlock(Properties pProperties, VoxelShape floorHitBox, boolean ceilingSurfaceMountOnly, Supplier<SoundEvent> outputSound) {
        super(pProperties, floorHitBox, ceilingSurfaceMountOnly);

        this.OUTPUT_SOUND = outputSound;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(SURFACE_MOUNT, AttachFace.WALL)
                .setValue(POWERED, false) //Does not beep by default
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        boolean wasOn = state.getValue(POWERED);
        SoundEvent onOffSound = wasOn ? SoundEvents.STONE_BUTTON_CLICK_OFF : SoundEvents.STONE_BUTTON_CLICK_ON;
        level.playSound(player, pos, onOffSound, SoundSource.BLOCKS, 1f, 2f);

        state = state.cycle(POWERED);
        level.setBlock(pos, state, 2);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return pState.getValue(POWERED); //If powered (true), randomly tick
    }

    public void randomTick(@NotNull BlockState pState, ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        //If chunk is loaded, random tick the RandomTickSoundBlock if it is NOT powered
        if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pState.getValue(POWERED)) { //If powered (true)
            playSound(pLevel, pPos, OUTPUT_SOUND.get());
        }

    }

    private static void playSound(Level pLevel, BlockPos pPos, SoundEvent outputSound) {
        //Plays stonecutter sound when interaction successful and this method is called
        pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                outputSound, SoundSource.BLOCKS, 1f, 1f, 0);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, POWERED, SURFACE_MOUNT);
    }
}
