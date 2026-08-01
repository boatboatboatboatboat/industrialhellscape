package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.modded_block_classes.PlacedFacingBlocks.SimpleFacingBlock;
import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.boat.industrialhellscape.block.modded_interfaces.MultiBlockPlacementInterface;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IntegerMultiBlock extends SimpleFacingBlock {
    public static int maximumBlockStates;
    public static final IntegerProperty PART = IntegerProperty.create("part",0, maximumBlockStates);

    public IntegerMultiBlock(BlockBehaviour.Properties pProperties, int registerMaxBlockStates) {
        super(pProperties);
        maximumBlockStates = registerMaxBlockStates;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, 0)
                .setValue(FACING, Direction.NORTH)
        );
    }

    public @Nonnull BlockState updateShape(BlockState pState, @Nonnull Direction pDirection, @Nonnull BlockState pOtherState, LevelAccessor pLevel, @Nonnull BlockPos pPos, @Nonnull BlockPos pOtherPos) {
        //When there is a block update related to this block (mainly, mining)
        //Ideal behavior: mining one block mines the whole multiblock.

        //otherBlockPos:
        // Returns the position above the placed block if it is a vertical multiblock
        // Returns the position counterclockwise to the direction of the faced block if it is a horizontal multiblock
        // Returns the position behind the placed block (relative to facing player) if it is a forward horizontal multiblock
        BlockPos otherBlockPos = MultiBlockPlacementInterface.posToPlaceOtherHalf(pPos, half, pState.getValue(FACING), multiBlockPlacementDirection); //Which location should the other half be placed based on the existing half?

        BlockState neighborState = pLevel.getBlockState(otherBlockPos); //What is the blockstate at the position where the other half should be?

        if (!neighborState.is(this)) { //If the block at that position is NOT an instance of this block (E.G, it was mined or missing)
            pLevel.destroyBlock(pPos, true);
        }


        return super.updateShape(pState, pDirection, pOtherState, pLevel, pPos, pOtherPos);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction facing = pContext.getHorizontalDirection().getOpposite(); //Which direction is the block placed?

        BlockPos otherBlockPos = MultiBlockPlacementInterface.posToPlaceOtherHalf(pContext.getClickedPos(), TwoBlockMultiBlockState.NEGATIVE, facing, multiBlockPlacementDirection);

        Level level = pContext.getLevel();
        if (level.getBlockState(otherBlockPos).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(otherBlockPos)) {
            return this.defaultBlockState().setValue(FACING, facing); //default block state is negative
        } else {
            return null;
        }
    }

    public void setPlacedBy(@Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull BlockState pState, @Nullable LivingEntity pPlacer, @Nonnull ItemStack pStack) {
        BlockPos otherBlockPos = MultiBlockPlacementInterface.posToPlaceOtherHalf(pPos, pState.getValue(HALF_PART), pState.getValue(FACING), multiBlockPlacementDirection);

        pLevel.setBlock(otherBlockPos, pState.setValue(HALF_PART, TwoBlockMultiBlockState.POSITIVE), 3);
        pLevel.setBlock(pPos, pState.setValue(HALF_PART, TwoBlockMultiBlockState.NEGATIVE), 3);
    }

    public PushReaction getPistonPushReaction(@NotNull BlockState pState) {
        return PushReaction.NORMAL;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(PART, FACING);
    }
}
