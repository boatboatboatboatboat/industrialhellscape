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

import java.util.function.Supplier;

//INFO:
//-----
//Handles block entity behavior for all inheriting block classes.

//getSlotCount() used by StorageBlockInterface to detect desired Block Entity item slot amount to create.
//getOpenSound() and getClosedSound() used by StorageBlockInterface to detect desired sounds for opening and closing Block Entity menu.
//If I define these block states in the interfaces, it may crash in 1.21. This is why these methods are in place.

public class BaseStorageBlock extends BaseEntityBlock implements StorageBlockInterface {
    //public static final MapCodec<BaseStorageBlock> CODEC = simpleCodec(BaseStorageBlock::new);
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
        return SLOTS;
    }

    @Override
    public SoundEvent getOpenSound() {
        return OPEN_SOUND;
    }

    @Override
    public SoundEvent getCloseSound() {
        return CLOSE_SOUND;
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        return StorageBlockInterface.OpenContainerInventory(level, pos, player);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new StorageBE(blockPos, blockState);
    }

    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        StorageBlockInterface.dropContainerInventory(this, state, level,pos, newState);
        super.onRemove(state, level, pos, newState, isMoving);
    }

    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    public int getAnalogOutputSignal(@NotNull BlockState blockState, Level level, @NotNull BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(OPEN);
    }
}
