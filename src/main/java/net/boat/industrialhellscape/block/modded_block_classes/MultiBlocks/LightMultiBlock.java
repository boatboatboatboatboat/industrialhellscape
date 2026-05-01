package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class LightMultiBlock extends Modelled2BMBlock{
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public LightMultiBlock(Properties pProperties, MultiBlockPlacementDirection multiBlockPlacementDirection, VoxelShape hitboxPositiveShape, VoxelShape hitboxNegativeShape) {
        super(pProperties, multiBlockPlacementDirection, hitboxPositiveShape, hitboxNegativeShape);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POWERED, false)
                .setValue(HALF_PART, TwoBlockMultiBlockState.NEGATIVE)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED,false));
    }

    @Override
    public @Nonnull InteractionResult use(@Nonnull BlockState pState, @NotNull Level pLevel, @Nonnull BlockPos pPos, @Nonnull Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {

        if(pState.getValue(HALF_PART).equals(TwoBlockMultiBlockState.NEGATIVE)) {
            //If the interacted block is the bottom block. Light it up. Then light the block above it.
            BlockPos abovePos = pPos.above();
            boolean otherBlockIsThisBlock = pLevel.getBlockState(abovePos).is(this);

            //For this block
            pState = pState.cycle(POWERED);
            pLevel.setBlock(pPos, pState, 2); //Light the negative Block (bottom block)

            //For above block
            if(otherBlockIsThisBlock) pLevel.setBlock(abovePos, pLevel.getBlockState(abovePos).cycle(POWERED), 2); //Light the positive Block (top block)

            return InteractionResult.SUCCESS;

        } else if(pState.getValue(HALF_PART).equals(TwoBlockMultiBlockState.POSITIVE)) {
            //If the interacted block is the top block. Light it up. Then light the block below it.
            BlockPos belowPos = pPos.below();
            boolean otherBlockIsThisBlock = pLevel.getBlockState(belowPos).is(this);

            //For this block
            pState = pState.cycle(POWERED);
            pLevel.setBlock(pPos, pState, 2); //Light the positive Block (top block)

            //for below block
            if(otherBlockIsThisBlock) pLevel.setBlock(belowPos, pLevel.getBlockState(belowPos).cycle(POWERED), 2); //Light the negative Block (bottom block)

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HALF_PART, POWERED, WATERLOGGED);
    }
}
