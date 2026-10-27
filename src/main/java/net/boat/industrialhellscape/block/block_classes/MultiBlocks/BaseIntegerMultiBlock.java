package net.boat.industrialhellscape.block.block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.block_classes.HorizontalDirectionBlocks.SimpleHorizontalBlock;
import net.boat.industrialhellscape.block.block_interfaces.MultiBlockPlacementInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BaseIntegerMultiBlock extends SimpleHorizontalBlock implements MultiBlockPlacementInterface, IBlockExtension {
    public int[][] multiBlockPlacementMatrix; //not static, the placement matrix is unique to many kinds of blocks
    public final IntegerProperty partProperty; //not static, extended classes define a max ceiling on IntegerProperty

    public BaseIntegerMultiBlock(Properties pProperties, int[][] multiBlockPlacementMatrix, IntegerProperty partProperty) {
        super(pProperties);
        this.multiBlockPlacementMatrix = multiBlockPlacementMatrix;
        this.partProperty = partProperty; //Will eventually be initialized by passing in a defined IntegerProperty as the last argument

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
        );
    }
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState pState = this.defaultBlockState();
        Direction facing = pContext.getHorizontalDirection().getOpposite(); //Standard orientation for Minecraft blocks with a "front" side (faces opposite, towards player)
        return pState.setValue(FACING,facing);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return MultiBlockPlacementInterface.isFreeFromObstruction(state,level,pos,multiBlockPlacementMatrix);
    }

    public void setPlacedBy(@Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull BlockState pState, @Nullable LivingEntity pPlacer, @Nonnull ItemStack pStack) {
        MultiBlockPlacementInterface.constructMultiBlock(pLevel, this,  pPos, multiBlockPlacementMatrix, partProperty, FACING);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, boolean willHarvest, FluidState fluid) {
        /*
            https://docs.neoforged.net/docs/1.21.1/blocks/

            Experimentally determined:
            playerDestroy() will not work in Creative Mode at all. It can be called manually by another method related to block destruction,
            such as playerWillDestroy() or onDestroyedByPlayer (this method)
         */

        /*
            onDestroyedByPlayer()
            -Has a Player parameter to allow checking for creative mode for proper item-dropping behavior
            -Won't make multi-block act weird with Create mod, unlike neighborChanged()
            -Presumably only ever called by real players, intentionally triggering the destruction for-loop
         */

        MultiBlockPlacementInterface.destroyRemainingMultiBlock(pLevel, pPlayer, this, pPos, partProperty, pState, multiBlockPlacementMatrix);
        return super.onDestroyedByPlayer(pState, pLevel, pPos, pPlayer, willHarvest, fluid);
    }

    public PushReaction getPistonPushReaction(@NotNull BlockState pState) {
        return PushReaction.NORMAL;
    }
}
