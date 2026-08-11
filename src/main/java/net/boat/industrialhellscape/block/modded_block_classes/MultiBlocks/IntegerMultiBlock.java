package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.modded_block_classes.PlacedFacingBlocks.SimpleFacingBlock;
import net.boat.industrialhellscape.block.modded_interfaces.MultiBlockPlacementInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IntegerMultiBlock extends SimpleFacingBlock implements MultiBlockPlacementInterface, IBlockExtension {
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
    public boolean onDestroyedByPlayer(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, boolean willHarvest, FluidState fluid) {
        /*
        https://docs.neoforged.net/docs/1.21.1/blocks/

        Experimentally determined:
        playerDestroy() will not work in Creative Mode at all. It can be called manually by another method related to block destruction,
        such as playerWillDestroy() or onDestroyedByPlayer
         */

        MultiBlockPlacementInterface.destroyRemainingMultiBlock(pLevel, pPlayer, this, pPos, PART, pState, multiBlockPlacementMatrix);
        return super.onDestroyedByPlayer(pState, pLevel, pPos, pPlayer, willHarvest, fluid);
    }

//    @Override
//    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
//        if(player.isCreative()) {
//            playerDestroy(level, player, pos, state, null, ItemStack.EMPTY);
//        }
//        return super.playerWillDestroy(level, pos, state, player);
//    }

//    @Override
//    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @org.jetbrains.annotations.Nullable BlockEntity blockEntity, ItemStack tool) {
//        //playerDestroy will not run for player in CreativeMode
//        MultiBlockPlacementInterface.destroyRemainingMultiBlock(pLevel, this, pPos, PART, pState, multiBlockPlacementMatrix);
//        super.playerDestroy(pLevel, pPlayer, pPos, pState, blockEntity, tool);
//    }

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
