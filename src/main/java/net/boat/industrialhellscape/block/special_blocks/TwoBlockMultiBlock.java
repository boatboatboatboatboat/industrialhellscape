package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.MultiBlockPlacementDirection;
import net.boat.industrialhellscape.block.special_blocks_properties.TwoBlockMultiBlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//otherBlockPos:
// Returns the position above the placed block if it is a vertical multiblock
// Returns the position counterclockwise to the direction of the faced block if it is a horizontal multiblock
// Returns the position behind the placed block (relative to facing player) if it is a forward horizontal multiblock

public class TwoBlockMultiBlock extends SimpleFacingBlock {

    public MultiBlockPlacementDirection multiBlockPlacementDirection;
    //Not a block-state property. Used for trinary logic during block registration to choose how the multiblock is built when placed down
    //VERTICAL, HORIZONTAL, or FORWARD

    public static final EnumProperty<TwoBlockMultiBlockState> HALF_PART = EnumProperty.create("half", TwoBlockMultiBlockState.class);
    //Values of "POSITIVE" and "NEGATIVE". In this context, it refers either to TOP vs BOTTOM block, or LEFT vs RIGHT block.

    public TwoBlockMultiBlock(Properties pProperties, MultiBlockPlacementDirection multiBlockPlacementDirection) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HALF_PART, TwoBlockMultiBlockState.NEGATIVE)
                .setValue(FACING,Direction.NORTH)
        );
        this.multiBlockPlacementDirection = multiBlockPlacementDirection;
    }

    public static BlockPos posToPlaceOtherHalf(BlockPos pPos, TwoBlockMultiBlockState pPart, Direction placementDirection, MultiBlockPlacementDirection multiBlockPlacementDirection) {
        //returns the direction where the other half is to be placed.

        return switch (multiBlockPlacementDirection) {
            case VERTICAL -> pPos.relative(pPart == TwoBlockMultiBlockState.POSITIVE ? Direction.DOWN : Direction.UP);
            case HORIZONTAL -> pPos.relative(pPart == TwoBlockMultiBlockState.POSITIVE ? placementDirection.getClockWise() : placementDirection.getCounterClockWise());
            default -> pPos.relative(pPart == TwoBlockMultiBlockState.POSITIVE ? placementDirection.getOpposite() : placementDirection);
        };
    }

    public @Nonnull BlockState updateShape(BlockState pState, @Nonnull Direction pDirection, @Nonnull BlockState pOtherState, LevelAccessor pLevel, @Nonnull BlockPos pPos, @Nonnull BlockPos pOtherPos) {
        //When there is a block update related to this block
        TwoBlockMultiBlockState half = pState.getValue(HALF_PART); //Which half is being updated?

        BlockPos otherBlockPos = posToPlaceOtherHalf(pPos, half, pState.getValue(FACING), multiBlockPlacementDirection); //Which location should the other half be placed based on the existing half?

        BlockState neighborState = pLevel.getBlockState(otherBlockPos); //What is the blockstate at the position where the other half should be?
        if (!neighborState.is(this)) { //If the block at that position is NOT an instance of this block (E.G, it was mined or missing)
            var air = Blocks.AIR.defaultBlockState();
            pLevel.setBlock(pPos, air, 35); //Replace the block at that position with an air block so that it drops nothing. You will not get double the blocks by mining this multiblock.
            pLevel.levelEvent(null, 2001, pPos, Block.getId(air));
            return air;
        }
        return super.updateShape(pState, pDirection, pOtherState, pLevel, pPos, pOtherPos);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getHorizontalDirection(); //Which direction is the block placed?

        //BlockPos otherBlockPos = isVerticalMultiBlock ? blockpos.relative(Direction.UP) : blockpos.relative(direction.getCounterClockWise()); //top half
        BlockPos otherBlockPos = posToPlaceOtherHalf(pContext.getClickedPos(), TwoBlockMultiBlockState.NEGATIVE, pContext.getHorizontalDirection(), multiBlockPlacementDirection);

        Level level = pContext.getLevel();
        if (level.getBlockState(otherBlockPos).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(otherBlockPos)) {
            return this.defaultBlockState().setValue(FACING, direction); //default block state is negative
        } else {
            return null;
        }
    }

    public void setPlacedBy(@Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull BlockState pState, @Nullable LivingEntity pPlacer, @Nonnull ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        if (!pLevel.isClientSide) {

            //BlockPos otherBlockPos = isVerticalMultiBlock ? pPos.relative(Direction.UP) : pPos.relative(pState.getValue(FACING).getCounterClockWise());
            BlockPos otherBlockPos = posToPlaceOtherHalf(pPos, pState.getValue(HALF_PART), pState.getValue(FACING), multiBlockPlacementDirection);

            pLevel.setBlock(otherBlockPos, pState.setValue(HALF_PART, TwoBlockMultiBlockState.POSITIVE), 3);
            pLevel.setBlock(pPos, pState.setValue(HALF_PART, TwoBlockMultiBlockState.NEGATIVE), 3);
            pLevel.blockUpdated(pPos, Blocks.AIR);
            pState.updateNeighbourShapes(pLevel, pPos, 3);
        }
    }

    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.BLOCK;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF_PART, FACING);
    }
}
