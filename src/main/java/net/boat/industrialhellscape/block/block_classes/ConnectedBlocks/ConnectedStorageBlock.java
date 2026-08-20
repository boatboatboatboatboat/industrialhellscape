package net.boat.industrialhellscape.block.block_classes.ConnectedBlocks;

import net.boat.industrialhellscape.block.block_entities.StorageBE.StorageBE;
import net.boat.industrialhellscape.block.block_state_enums.DynamicConnectionState;
import net.boat.industrialhellscape.block.block_interfaces.ConnectedModelInterface;
import net.boat.industrialhellscape.block.block_interfaces.StorageBlockInterface;
import net.boat.industrialhellscape.block.block_interfaces.ToolUseInterface;
import net.boat.industrialhellscape.block.logic_enums.ConnectingBlockPlacementOrientation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/*
INFO:
-----
Block changes block state model depending on adjacent blocks (directions to check determined by "placementDirection parameter") to simulate a connected structure (e.g. a long table).
Block has a block entity within.
"inputCompatibleBlockSet" parameter lets it detect which different blocks are compatible to update model for.

getSlotCount() used by StorageBlockInterface to detect desired Block Entity item slot amount to create.
getOpenSound() and getClosedSound() used by StorageBlockInterface to detect desired sounds for opening and closing Block Entity menu.

Keywords: desk, drawer
*/

public class ConnectedStorageBlock extends ConnectedFurnitureBlock implements EntityBlock, ConnectedModelInterface, ToolUseInterface, StorageBlockInterface, SimpleWaterloggedBlock {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public final int SLOTS;
    public final SoundEvent OPEN_SOUND;
    public final SoundEvent CLOSE_SOUND;

    public ConnectedStorageBlock(Properties properties, int slotAmount, TagKey<Block> inputCompatibleBlockSet, VoxelShape soloShape, VoxelShape leftShape, VoxelShape middleShape, VoxelShape rightShape, ConnectingBlockPlacementOrientation placementDirection, SoundEvent openSound, SoundEvent closeSound) {
        // When registering this block, pass in
        // Properties,
        // Integer slot amount (should be multiple of 9) that its block entity inventory shall possess
        // Block Tag for related blocks that this block can physically connect to
        // Unconnected state hitbox VoxelShape
        // Left connection state hitbox VoxelShape
        // Middle connection state hitbox VoxelShape
        // Right connection state hitbox VoxelShape
        // Enum Value: Whether blocks should be placed horizontally, vertically, or longitudinally to check for connections
        // Sound to play when player opens block
        // Sound to play when player closes block

        super(properties, inputCompatibleBlockSet,  soloShape,  leftShape,  middleShape,  rightShape,  placementDirection);
        //Slots
        this.SLOTS = slotAmount;
        //Sounds
        this.OPEN_SOUND = openSound;
        this.CLOSE_SOUND = closeSound;

//        //To determine other blocks aside from its own can this block connect to
//        this.blockSetFamily = inputCompatibleBlockSet;

//        //To determine which direction placed blocks will connect to.
//        this.placementDirection = placementDirection;

        //Default state is Solo/unconnected, facing North, no waterlogging
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TYPE, DynamicConnectionState.SOLO)
                .setValue(FACING, Direction.NORTH)
                .setValue(OPEN, false)
                .setValue(WATERLOGGED, false)
        );
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

    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        return StorageBlockInterface.OpenContainerInventory(level, pos, player);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new StorageBE(blockPos, blockState);
    }

    protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        StorageBlockInterface.dropContainerInventory(state, level, pos);
        super.onRemove(state, level, pos, newState, isMoving);
    }

    protected boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    protected int getAnalogOutputSignal(@NotNull BlockState blockState, Level level, @NotNull BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    //---------- HITBOXES, PLACEMENT, AND BLOCK UPDATES HANDLED BY INTERFACE ---------

    //---------- END OF METHODS HANDLED BY INTERFACE ----------

    @Override
    public @Nonnull @NotNull RenderShape getRenderShape(@Nonnull BlockState pState) {
        return RenderShape.MODEL;
    }

    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, TYPE, OPEN); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}