package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

//INFO:
//-----
//This block supports cardinal directional placement, and an inventory with GUI. The inventory size is determined upon block registration.
//Block places two blocks total within the world when placed down; First a "Negative" half, then a "Positive" half.
//The direction these are placed is set by parameter "multiBlockPlacementDirection". The second half of the structure can be placed vertically, horizontally, or forward to the first half.
//Supports a custom hitbox for custom model, passed during registration.

//useWithoutItem cycles POWERED blockstate for both blocks. Whether a blockstate gives off light is coded in ModBlocks.

public class InteractableMultiBlock extends Modelled2BMBlock{
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public InteractableMultiBlock(Properties pProperties, MultiBlockPlacementDirection multiBlockPlacementDirection, VoxelShape hitboxPositiveShape, VoxelShape hitboxNegativeShape) {
        super(pProperties, multiBlockPlacementDirection, hitboxPositiveShape, hitboxNegativeShape);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POWERED, false)
                .setValue(HALF_PART, TwoBlockMultiBlockState.NEGATIVE)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED,false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        boolean wasOn = state.getValue(POWERED);
        SoundEvent onOffSound = wasOn ? SoundEvents.STONE_BUTTON_CLICK_OFF : SoundEvents.STONE_BUTTON_CLICK_ON;

        level.playSound(player, pos, onOffSound, SoundSource.BLOCKS, 1f, 1f);

        if(state.getValue(HALF_PART).equals(TwoBlockMultiBlockState.NEGATIVE)) {
            //If the interacted block is the bottom block. Light it up. Then light the block above it.
            BlockPos abovePos = pos.above();
            boolean otherBlockIsThisBlock = level.getBlockState(abovePos).is(this);

            //For this block
            state = state.cycle(POWERED);
            level.setBlock(pos, state, 2); //Light the negative Block (bottom block)

            //For above block
            if(otherBlockIsThisBlock) level.setBlock(abovePos, level.getBlockState(abovePos).cycle(POWERED), 2); //Light the positive Block (top block)

            return InteractionResult.SUCCESS;

        } else if(state.getValue(HALF_PART).equals(TwoBlockMultiBlockState.POSITIVE)) {
            //If the interacted block is the top block. Light it up. Then light the block below it.
            BlockPos belowPos = pos.below();
            boolean otherBlockIsThisBlock = level.getBlockState(belowPos).is(this);

            //For this block
            state = state.cycle(POWERED);
            level.setBlock(pos, state, 2); //Light the positive Block (top block)

            //for below block
            if(otherBlockIsThisBlock) level.setBlock(belowPos, level.getBlockState(belowPos).cycle(POWERED), 2); //Light the negative Block (bottom block)

            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.PASS;
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HALF_PART, POWERED, WATERLOGGED);
    }
}
