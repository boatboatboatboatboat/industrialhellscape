package net.boat.industrialhellscape.block.modded_block_classes.StorageBlocks;

import com.mojang.serialization.MapCodec;
import net.boat.industrialhellscape.block.modded_block_entities.StorageBE.StorageBE;
import net.boat.industrialhellscape.block.modded_interfaces.StorageBlockInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
INFO:
-----
Handles block entity behavior for all inheriting block classes.

getSlotCount() used by StorageBE to acquire desired Block Entity item slot amount, registered per Block instance.
getOpenSound() & getCloseSound() used by StorageBE to acquire desired sound effects registered per Block instance.
*/

public class BaseStorageBlock extends BaseEntityBlock implements StorageBlockInterface {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public final int SLOTS;
    public final SoundEvent OPEN_SOUND;
    public final SoundEvent CLOSE_SOUND;

    public @NotNull MapCodec<BaseStorageBlock> codec() {
        return null;
    }

    public BaseStorageBlock(Properties properties, int slotsAmount, SoundEvent openSound, SoundEvent closeSound) {
        super(properties);
        this.SLOTS = slotsAmount;
        this.OPEN_SOUND = openSound;
        this.CLOSE_SOUND = closeSound;
        this.registerDefaultState(this.stateDefinition.any().setValue(OPEN, false));
    }

    @Override
    public int getSlotCount() {
        //StorageBlockInterface
        return SLOTS;
    }

    @Override
    public SoundEvent getOpenSound() {
        //StorageBlockInterface
        return OPEN_SOUND;
    }

    @Override
    public SoundEvent getCloseSound() {
        //StorageBlockInterface
        return CLOSE_SOUND;
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        return StorageBlockInterface.OpenContainerInventory(level, pos, player);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new StorageBE(blockPos, blockState);
    }

    protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        Containers.dropContentsOnDestroy(state, newState, level, pos);
        super.onRemove(state, level, pos, newState, isMoving);
    }

    protected boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    protected int getAnalogOutputSignal(@NotNull BlockState blockState, Level level, @NotNull BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
    }
}
