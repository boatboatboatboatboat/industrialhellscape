package net.boat.industrialhellscape.block.special_blocks;

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

import javax.annotation.Nonnull;
//INFO:
//-----
// This block supports rotation of custom models. It also supports directional placement based on player.
// It supports waterlogging.
// It supports interactions for model changes with the boolean property "POWERED"
// Will currently be used for blocks that emit light when "powered". The "LIT" property is omitted from usage for simplicity.
// It extends the modded block class, ModelledFacingBlock. Rotation of hitboxes for all cardinal directions is handled in that class.
//-----

public class InteractableModelledFacingBlock extends ModelledFacingBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED; //For binary states (on, off, lit, unlit)
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public InteractableModelledFacingBlock(Properties pProperties, VoxelShape soloShape) {
        super(pProperties, soloShape);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public @Nonnull InteractionResult use(@Nonnull BlockState pState, Level pLevel, @Nonnull BlockPos pPos, @Nonnull Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {
        pState = pState.cycle(POWERED);
        pLevel.setBlock(pPos, pState, 2);
        return InteractionResult.SUCCESS;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, POWERED); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}


