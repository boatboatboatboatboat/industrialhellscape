package net.boat.industrialhellscape.block.block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.block_classes.PlacedFacingBlocks.SimpleFacingBlock;
import net.boat.industrialhellscape.block.block_interfaces.MultiBlockPlacementInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BaseIntegerMultiBlock extends SimpleFacingBlock implements MultiBlockPlacementInterface, IBlockExtension {
    public int[][] multiBlockPlacementMatrix; //not static, the placement matrix is unique to many kinds of blocks
    public final IntegerProperty partProperty; //not static, extended classes define a max ceiling on IntegerProperty

    public BaseIntegerMultiBlock(Properties pProperties, int[][] multiBlockPlacementMatrix, IntegerProperty partProperty) {
        super(pProperties);
        //maximumBlockStates = registerMaxBlockStates-1;
        this.multiBlockPlacementMatrix = multiBlockPlacementMatrix;
        this.partProperty = partProperty; //Will eventually be initialized by passing in a defined IntegerProperty as the last argument

        this.registerDefaultState(this.stateDefinition.any()
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

        //Forgot if I tried using interface method in onRemove() but with if( newState.getBlock() != state.getBlock() )
        //Probably because onRemove doesn't have a Player argument to check for creative mode to change level block-removal method

        MultiBlockPlacementInterface.destroyRemainingMultiBlock(pLevel, pPlayer, this, pPos, partProperty, pState, multiBlockPlacementMatrix);
        return super.onDestroyedByPlayer(pState, pLevel, pPos, pPlayer, willHarvest, fluid);
    }

    public void setPlacedBy(@Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull BlockState pState, @Nullable LivingEntity pPlacer, @Nonnull ItemStack pStack) {
        MultiBlockPlacementInterface.constructMultiBlock(pLevel, pPos, multiBlockPlacementMatrix, partProperty);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos originPos = pContext.getClickedPos();
        BlockState pState = this.defaultBlockState();
        Direction facing = pContext.getHorizontalDirection().getOpposite(); //Standard orientation for Minecraft blocks with a "front" side (faces opposite, towards player)

        return MultiBlockPlacementInterface.placeOriginBlock(level, originPos, pState, FACING, facing, multiBlockPlacementMatrix);
    }

    public PushReaction getPistonPushReaction(@NotNull BlockState pState) {
        return PushReaction.NORMAL;
    }
}
