package net.boat.industrialhellscape.block.special_blocks.DeprecatedStorageBlocks;

import net.boat.industrialhellscape.block.special_blocks.SimpleFacingBlock;
import net.boat.industrialhellscape.block.special_blocks_properties.TwoBlockMultiBlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Vertical2BlockStorageMultiBlock extends SimpleFacingBlock implements EntityBlock {

    public static final EnumProperty<TwoBlockMultiBlockState> HALF = EnumProperty.create("half", TwoBlockMultiBlockState.class); //Values of "POSITIVE" and "NEGATIVE". In this context, it refers to "top block" and "bottom block" respectively

    public Vertical2BlockStorageMultiBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HALF, TwoBlockMultiBlockState.NEGATIVE).setValue(FACING,Direction.NORTH));
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (pLevel.isClientSide()) { //On client
            return InteractionResult.SUCCESS;
        }
        else { //On server
            if (pState.getValue(HALF) != TwoBlockMultiBlockState.NEGATIVE) { //If the interacted block half is NOT the bottom block
                pPos = pPos.below();    //Move the interacted block position one block below (the player will always interact with bottom block where the block entity is, regardless of top/bottom block clicked)
                pState = pLevel.getBlockState(pPos);
                if (!pState.is(this)) {
                    return InteractionResult.CONSUME; //Do the interaction at the position of the bottom block
                }
            }
        }

        BlockEntity be = pLevel.getBlockEntity(pPos);
        if (!(be instanceof Storage9SlotBlockEntity blockEntity))
            return InteractionResult.PASS;

        //Open Screen
        if (pPlayer instanceof ServerPlayer sPlayer) {
            NetworkHooks.openScreen(sPlayer, blockEntity, pPos);
        }
        return InteractionResult.CONSUME;
    }

    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        BlockEntity storageEntity = new Storage9SlotBlockEntity(pos, state);

        if(state.getValue(HALF) == TwoBlockMultiBlockState.POSITIVE) { //If the block is the top block
            return null; //no new block entities will be generated
        }
        return storageEntity; //One block entity will be present: in the bottom block
    }

    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        TwoBlockMultiBlockState doubleblockhalf = pState.getValue(HALF);
        if (pFacing.getAxis() == Direction.Axis.Y && doubleblockhalf == TwoBlockMultiBlockState.NEGATIVE == (pFacing == Direction.UP)) {
            return pFacingState.is(this) && pFacingState.getValue(HALF) != doubleblockhalf ? pState.setValue(FACING, pFacingState.getValue(FACING)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == TwoBlockMultiBlockState.NEGATIVE && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(pContext)) {
            return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection()).setValue(HALF, TwoBlockMultiBlockState.NEGATIVE);
        } else {
            return null;
        }
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(HALF, TwoBlockMultiBlockState.POSITIVE), 3);
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return pState.getValue(HALF) == TwoBlockMultiBlockState.NEGATIVE ? blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP) : blockstate.is(this);
    }

    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof Storage9SlotBlockEntity blockEntity) {
                ItemStackHandler inventory = blockEntity.getInventory();
                for (int index = 0; index < inventory.getSlots(); index++) {
                    ItemStack stack = inventory.getStackInSlot(index);
                    var entity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
                    level.addFreshEntity(entity);
                }
            }
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF, FACING);
    }
}
