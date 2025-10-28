package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

public class LightMultiBlock extends Modelled2BMBlock{
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public LightMultiBlock(Properties pProperties, MultiBlockPlacementDirection multiBlockPlacementDirection, VoxelShape hitboxPositiveShape, VoxelShape hitboxNegativeShape) {
        super(pProperties, multiBlockPlacementDirection, hitboxPositiveShape, hitboxNegativeShape);
    }

    @Override
    public @Nonnull InteractionResult use(@Nonnull BlockState pState, Level pLevel, @Nonnull BlockPos pPos, @Nonnull Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {
        if(pState.getValue(HALF_PART).equals(TwoBlockMultiBlockState.NEGATIVE)) {
            pState = pState.cycle(POWERED);
            pLevel.setBlock(pPos, pState, 2);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HALF_PART, POWERED, WATERLOGGED);
    }
}
