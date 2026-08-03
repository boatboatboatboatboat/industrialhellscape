package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.modded_block_classes.PlacedFacingBlocks.SimpleFacingBlock;
import net.boat.industrialhellscape.block.modded_interfaces.MultiBlockPlacementInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IntegerMultiBlock extends SimpleFacingBlock implements MultiBlockPlacementInterface{
    public int[][] multiBlockPlacementMatrix; //not static, the placement matrix is unique to many kinds of blocks
    public static int maximumBlockStates;
    public static final IntegerProperty PART = IntegerProperty.create("part",0, 1);

    public IntegerMultiBlock(BlockBehaviour.Properties pProperties, int registerMaxBlockStates, int[][] multiBlockPlacementMatrix) {
        super(pProperties);
        //maximumBlockStates = registerMaxBlockStates-1;
        this.multiBlockPlacementMatrix = multiBlockPlacementMatrix;
        maximumBlockStates = registerMaxBlockStates;


        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, 0)
                .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState newState, boolean movedByPiston) {
        MultiBlockPlacementInterface.destroyRemainingMultiBlock(pLevel, this, pPos, PART, pState, multiBlockPlacementMatrix);
        super.onRemove(pState, pLevel, pPos, newState, movedByPiston);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos originPos = pContext.getClickedPos();
        BlockState pState = this.defaultBlockState();
        Direction facing = pContext.getHorizontalDirection().getOpposite(); //Standard orientation for Minecraft blocks with a "front" side (faces opposite, towards player)

        return MultiBlockPlacementInterface.placeOriginBlock(level, originPos, pState, FACING, facing, multiBlockPlacementMatrix);
    }

    public void setPlacedBy(@Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull BlockState pState, @Nullable LivingEntity pPlacer, @Nonnull ItemStack pStack) {
        MultiBlockPlacementInterface.constructMultiBlock(pLevel, pPos, multiBlockPlacementMatrix, PART);
    }

    public PushReaction getPistonPushReaction(@NotNull BlockState pState) {
        return PushReaction.NORMAL;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(PART, FACING);
    }
}
