package net.boat.industrialhellscape.block.block_classes.FaceAttachedHorizontalDirectionBlocks;

import net.boat.industrialhellscape.block.block_interfaces.SoundPlayInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

public class WaterloggableLeverBlock extends ModelledFAHDBlock implements SoundPlayInterface {

    // Additional BlockState Properties
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    // Additional Per-Instance Constructor Fields
    protected final SoundEvent OPEN_SOUND;
    protected final SoundEvent CLOSE_SOUND;

    public WaterloggableLeverBlock(Properties pProperties, VoxelShape floorHitBox, SoundEvent openSound, SoundEvent closedSound) {
        super(pProperties, floorHitBox);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(FACE, AttachFace.WALL)
                .setValue(WATERLOGGED, false)
                .setValue(POWERED, false)
        );
        this.OPEN_SOUND = openSound;
        this.CLOSE_SOUND = closedSound;
    }

    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            this.pull(state, level, pos, null);
            return InteractionResult.CONSUME;
        }
    }

    public void pull(BlockState state, Level level, BlockPos pos, @Nullable Player player) {
        state = state.cycle(POWERED);
        level.setBlock(pos, state, 3);
        this.updateNeighbours(state, level, pos);
        SoundPlayInterface.onOffSound(state,level,pos,POWERED, OPEN_SOUND, CLOSE_SOUND, 0.3f, 0.2f,0.6f);
        level.gameEvent(player, state.getValue(POWERED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
    }

    @Override
    protected int getSignal(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected int getDirectSignal(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return state.getValue(POWERED) && getConnectedDirection(state) == direction ? 15 : 0;
    }

    protected boolean isSignalSource(@NotNull BlockState state) {
        return true;
    }

    private void updateNeighbours(BlockState state, Level level, BlockPos pos) {
        level.updateNeighborsAt(pos, this);
        level.updateNeighborsAt(pos.relative(getConnectedDirection(state).getOpposite()), this);
    }

    protected void onExplosionHit(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Explosion explosion, @NotNull BiConsumer<ItemStack, BlockPos> dropConsumer) {
        if (explosion.canTriggerBlocks()) {
            this.pull(state, level, pos, null);
        }

        super.onExplosionHit(state, level, pos, explosion, dropConsumer);
    }

    protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        if (!isMoving && !state.is(newState.getBlock())) {
            if (state.getValue(POWERED)) {
                this.updateNeighbours(state, level, pos);
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }

    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, FACE, POWERED); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}
