package net.boat.industrialhellscape.block.modded_block_classes.StorageBlocks;

import net.boat.industrialhellscape.block.modded_block_entities.StorageBE.StorageBE;
import net.boat.industrialhellscape.block.modded_block_state_properties.DynamicConnectionState;
import net.boat.industrialhellscape.block.modded_interfaces.ConnectedModelInterface;
import net.boat.industrialhellscape.block.modded_interfaces.HitboxRotationInterface;
import net.boat.industrialhellscape.block.modded_interfaces.StorageBlockInterface;
import net.boat.industrialhellscape.block.modded_logic_enums.ConnectingBlockPlacementDirection;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
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

public class ConnectedStorageBlock extends FacingStorageBlock implements EntityBlock, ConnectedModelInterface, StorageBlockInterface, SimpleWaterloggedBlock {
    private static final EnumProperty<DynamicConnectionState> TYPE = EnumProperty.create("type", DynamicConnectionState.class);
    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public TagKey<Block> BlockSetFamily; //To determine other blocks aside from its own can this block connect to
    private final ConnectingBlockPlacementDirection placementDirection;

    public final SoundEvent OPEN_SOUND;
    public final SoundEvent CLOSE_SOUND;

    private final VoxelShape SOLO_SHAPE_NORTH;
    private final VoxelShape SOLO_SHAPE_SOUTH;
    private final VoxelShape SOLO_SHAPE_EAST;
    private final VoxelShape SOLO_SHAPE_WEST;

    private final VoxelShape LEFT_SHAPE_NORTH;
    private final VoxelShape LEFT_SHAPE_SOUTH;
    private final VoxelShape LEFT_SHAPE_EAST;
    private final VoxelShape LEFT_SHAPE_WEST;

    private final VoxelShape MIDDLE_SHAPE_NORTH;
    private final VoxelShape MIDDLE_SHAPE_SOUTH;
    private final VoxelShape MIDDLE_SHAPE_EAST;
    private final VoxelShape MIDDLE_SHAPE_WEST;

    private final VoxelShape RIGHT_SHAPE_NORTH;
    private final VoxelShape RIGHT_SHAPE_SOUTH;
    private final VoxelShape RIGHT_SHAPE_EAST;
    private final VoxelShape RIGHT_SHAPE_WEST;

    public ConnectedStorageBlock(Properties properties, int slotAmount, TagKey<Block> inputCompatibleBlockSet, VoxelShape soloShape, VoxelShape leftShape, VoxelShape middleShape, VoxelShape rightShape, ConnectingBlockPlacementDirection placementDirection, SoundEvent openSound, SoundEvent closeSound) {
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

        super(properties, slotAmount, openSound, closeSound);


        //Define the Voxelshape hitboxes for each state
        SOLO_SHAPE_NORTH = soloShape;
        SOLO_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, soloShape);
        SOLO_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, soloShape);
        SOLO_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, soloShape);

        LEFT_SHAPE_NORTH = leftShape;
        LEFT_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, leftShape);
        LEFT_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, leftShape);
        LEFT_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, leftShape);

        MIDDLE_SHAPE_NORTH = middleShape;
        MIDDLE_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, middleShape);
        MIDDLE_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, middleShape);
        MIDDLE_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, middleShape);

        RIGHT_SHAPE_NORTH = rightShape;
        RIGHT_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, rightShape);
        RIGHT_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, rightShape);
        RIGHT_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, rightShape);

        //Sounds
        this.OPEN_SOUND = openSound;
        this.CLOSE_SOUND = closeSound;

        //To determine other blocks aside from its own can this block connect to
        this.BlockSetFamily = inputCompatibleBlockSet;

        //To determine which direction placed blocks will connect to.
        this.placementDirection = placementDirection;

        //Default state is Solo/unconnected, facing North, no waterlogging
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TYPE, DynamicConnectionState.SOLO)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public SoundEvent getCloseSound() {
        return CLOSE_SOUND;
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
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        boolean playerHasTool = player.getMainHandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS) || player.getOffhandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS);

        if (playerHasTool) {
            //Cycles the connection state of the block WITHOUT UPDATING NEIGHBORS (This is what flag #2 does)
            state = state.cycle(TYPE);
            level.setBlock(pos, state, 2); //2
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            //If player does not have an eligible tool, just open the inventory
            return StorageBlockInterface.OpenContainerInventory(level, pos, player);
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new StorageBE(blockPos, blockState);
    }

    //---------- HITBOXES, PLACEMENT, AND BLOCK UPDATES HANDLED BY INTERFACE ---------
    @Override
    public @Nonnull VoxelShape getShape(@Nonnull BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        //See this mod's ConnectedModelInterface interface to view the following method.
        return makeConnectedHitboxes(
                pState,
                LEFT_SHAPE_NORTH,LEFT_SHAPE_SOUTH,LEFT_SHAPE_EAST,LEFT_SHAPE_WEST,
                MIDDLE_SHAPE_NORTH,MIDDLE_SHAPE_SOUTH,MIDDLE_SHAPE_EAST,MIDDLE_SHAPE_WEST,
                RIGHT_SHAPE_NORTH,RIGHT_SHAPE_SOUTH,RIGHT_SHAPE_EAST,RIGHT_SHAPE_WEST,
                SOLO_SHAPE_NORTH,SOLO_SHAPE_SOUTH,SOLO_SHAPE_EAST,SOLO_SHAPE_WEST,
                FACING, TYPE);
    }
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        //See this mod's ConnectedModelInterface interface to view the following method.
        return placeConnectableBlock(this, pContext, BlockSetFamily, placementDirection, FACING, TYPE, WATERLOGGED);
    }
    public void neighborChanged(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos positionClicked, @Nonnull Block block, @Nonnull BlockPos fromPos, boolean pIsMoving) {
        //See this mod's ConnectedModelInterface interface to view the following method.
        whenConnectedNeighborUpdated(this, state,level,positionClicked,fromPos,BlockSetFamily, placementDirection, FACING, TYPE, WATERLOGGED);
    }
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