package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks.StorageBlock.NineSlotMenuBlockEntity;
import net.boat.industrialhellscape.block.special_blocks_properties.TwoStageMultiBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class TwoHalvesMultiBlock extends SimpleFacingBlock{

    public static final EnumProperty<TwoStageMultiBlock> HALF = EnumProperty.create("half", TwoStageMultiBlock.class);

    public TwoHalvesMultiBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, TwoStageMultiBlock.NEGATIVE).setValue(FACING,Direction.NORTH));
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        BlockEntity be = pLevel.getBlockEntity(pPos);

        if (pLevel.isClientSide()) {
            return InteractionResult.SUCCESS;
        } else {
            if (pState.getValue(HALF) != TwoStageMultiBlock.POSITIVE) {
                pPos = pPos.relative(pState.getValue(FACING));
                pState = pLevel.getBlockState(pPos);
                if (!pState.is(this)) {
                    return InteractionResult.CONSUME;
                }
            }
        }

        if (!(be instanceof NineSlotMenuBlockEntity blockEntity))
            return InteractionResult.PASS;

        // open screen
        if (pPlayer instanceof ServerPlayer sPlayer) {
            NetworkHooks.openScreen(sPlayer, blockEntity, pPos);
        }

        return InteractionResult.CONSUME;
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        TwoStageMultiBlock doubleblockhalf = pState.getValue(HALF);
        if (pFacing.getAxis() == Direction.Axis.Y && doubleblockhalf == TwoStageMultiBlock.NEGATIVE == (pFacing == Direction.UP)) {
            return pFacingState.is(this) && pFacingState.getValue(HALF) != doubleblockhalf ? pState.setValue(FACING, pFacingState.getValue(FACING)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == TwoStageMultiBlock.NEGATIVE && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(pContext)) {
            return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection()).setValue(HALF, TwoStageMultiBlock.NEGATIVE);
        } else {
            return null;
        }
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(HALF, TwoStageMultiBlock.POSITIVE), 3);
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return pState.getValue(HALF) == TwoStageMultiBlock.NEGATIVE ? blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP) : blockstate.is(this);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF, FACING);
    }
}
