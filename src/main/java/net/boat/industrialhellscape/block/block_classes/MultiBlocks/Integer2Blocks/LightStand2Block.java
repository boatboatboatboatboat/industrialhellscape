package net.boat.industrialhellscape.block.block_classes.MultiBlocks.Integer2Blocks;

import net.boat.industrialhellscape.block.block_interfaces.MultiBlockPlacementInterface;
import net.boat.industrialhellscape.block.block_interfaces.SoundPlayInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/*
    INFO:

    Modelled2Block superclass:
    - Multiblock consisting of part #0 and part #1 blockStates
    - WaterLoggable block

    This class:
    -Supports Right-click use interaction to cycle a chosen BooleanProperty across all multi-block parts
 */

public class LightStand2Block extends Modelled2Block implements SoundPlayInterface {

    public SoundEvent ON_SOUND;
    public SoundEvent OFF_SOUND;

    public LightStand2Block(Properties pProperties, int[][] multiBlockPlacementMatrix, VoxelShape[] hitboxShapeArray, SoundEvent onSound, SoundEvent offSound) {
        super(pProperties, multiBlockPlacementMatrix, hitboxShapeArray);

        this.ON_SOUND = onSound;
        this.OFF_SOUND = offSound;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.LIT, false)
                .setValue(PART, 0)
                .setValue(FACING, Direction.NORTH)
                .setValue(BlockStateProperties.WATERLOGGED,false));
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        /*
            if playing a sound here based on BlockState,
            - Off Sound will play if BlockState LIT = false before state updates, and vice versa.
            - This is due to state change happening in interface, and thus BlockState cannot be accessed for live reading;
            - Switch positions of OFF_SOUND and ON_SOUND in method parameters as a fix
            - Switch positions of on/off pitch float parameters too if needed
         */
        SoundPlayInterface.onOffSound(pState,pLevel,pPos,BlockStateProperties.LIT, OFF_SOUND, ON_SOUND, 0.5f, 0.5f, 0.6f);
        MultiBlockPlacementInterface.updateRemainingMultiBlock(pLevel, this, pPos, PART, BlockStateProperties.LIT, pState, multiBlockPlacementMatrix);
        return InteractionResult.SUCCESS;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, PART, BlockStateProperties.LIT, BlockStateProperties.WATERLOGGED);
    }
}
