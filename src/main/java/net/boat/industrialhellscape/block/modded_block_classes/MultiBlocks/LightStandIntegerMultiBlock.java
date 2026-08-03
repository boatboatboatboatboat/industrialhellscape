package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.modded_interfaces.MultiBlockPlacementInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

public class LightStandIntegerMultiBlock extends ModelledIntegerMultiBlock {

    public LightStandIntegerMultiBlock(Properties pProperties, int registerMaxBlockStates, int[][] multiBlockPlacementMatrix, VoxelShape[] hitboxShapeArray) {
        super(pProperties, registerMaxBlockStates, multiBlockPlacementMatrix, hitboxShapeArray);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(BlockStateProperties.LIT, false)
                .setValue(PART, 0)
                .setValue(FACING, Direction.NORTH)
                .setValue(BlockStateProperties.WATERLOGGED,false));
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState pState, Level pLevel, @NotNull BlockPos pPos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        boolean wasOn = pState.getValue(BlockStateProperties.LIT);
        SoundEvent onOffSound = wasOn ? SoundEvents.STONE_BUTTON_CLICK_OFF : SoundEvents.STONE_BUTTON_CLICK_ON;

        pLevel.playSound(player, pPos, onOffSound, SoundSource.BLOCKS, 1f, 1f);

        MultiBlockPlacementInterface.updateRemainingMultiBlock(pLevel, this, pPos, PART, BlockStateProperties.LIT, pState, multiBlockPlacementMatrix);
        return InteractionResult.SUCCESS;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, PART, BlockStateProperties.LIT, BlockStateProperties.WATERLOGGED);
    }
}
