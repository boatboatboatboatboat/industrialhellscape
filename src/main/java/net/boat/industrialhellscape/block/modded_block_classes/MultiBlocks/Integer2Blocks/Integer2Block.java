package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.Integer2Blocks;

import net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.BaseIntegerMultiBlock;
import net.boat.industrialhellscape.block.modded_interfaces.MultiBlockPlacementInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Integer2Block extends BaseIntegerMultiBlock implements MultiBlockPlacementInterface, IBlockExtension {
    //Specific to this class and subclasses
    public static final IntegerProperty PART = IntegerProperty.create("part",0, 1);

    public Integer2Block(BlockBehaviour.Properties pProperties, int[][] multiBlockPlacementMatrix) {
        super(pProperties, multiBlockPlacementMatrix, PART);
        //maximumBlockStates = registerMaxBlockStates-1;
        this.multiBlockPlacementMatrix = multiBlockPlacementMatrix;

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

    public void setPlacedBy(@Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull BlockState pState, @Nullable LivingEntity pPlacer, @Nonnull ItemStack pStack) {
        MultiBlockPlacementInterface.constructMultiBlock(pLevel, pPos, multiBlockPlacementMatrix, PART);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(PART, FACING);
    }
}
