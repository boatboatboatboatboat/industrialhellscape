package net.boat.industrialhellscape.block.modded_block_classes.Experimental;

import com.mojang.serialization.MapCodec;
import net.boat.industrialhellscape.block.modded_block_entities.DebugBE.DebugBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DebugBEBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(1, 1, 1, 15, 15, 15);
    public static final MapCodec<DebugBEBlock> CODEC = simpleCodec(DebugBEBlock::new);


    public DebugBEBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    /* Block Entiy */

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new DebugBE(blockPos, blockState);
    }

    @Override
    protected void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(state.getBlock() != newState.getBlock()) {
            if(level.getBlockEntity(pos) instanceof DebugBE storageBE)
                storageBE.drops();
            level.updateNeighbourForOutputSignal(pos, this);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof DebugBE debugBE) {
            //If inventory slot 0 is empty and item stack in hand is not empty,
            //Copy hand item stack into slot 0
            //Remove (-1) item stack from player's hand.
            //Play a sound
            if(player.isCrouching() && !level.isClientSide()) {
                player.openMenu(new SimpleMenuProvider(debugBE, Component.literal("Prototype")), pos);
                return ItemInteractionResult.SUCCESS;
            }

            if (debugBE.inventory.getStackInSlot(0).isEmpty() && !stack.isEmpty()) {
                debugBE.inventory.insertItem(0, stack.copy(), false);
                stack.shrink(1);
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 2f);
            //If item stack in hand is empty
            //Move stack from slot 0 in block to player Hand
            //Remove contents from entirety of Block
            //Play a sound
            } else if (stack.isEmpty()) {
                ItemStack stackOnBlock = debugBE.inventory.extractItem(0, 1, false);
                player.setItemInHand(InteractionHand.MAIN_HAND, stackOnBlock);
                debugBE.clearContents();
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            }
        }
        return ItemInteractionResult.SUCCESS;
    }
}