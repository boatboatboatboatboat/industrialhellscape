package net.boat.industrialhellscape.block.special_blocks.StorageBlock;

import net.boat.industrialhellscape.block.special_blocks_properties.ConnectedModelCapability;
import net.boat.industrialhellscape.block.special_blocks_properties.FurnitureConnectionState;
import net.boat.industrialhellscape.block.special_blocks_properties.ModBlockEntities;
import net.boat.industrialhellscape.block.special_blocks_properties.RotationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
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
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConnectedFurniture9SlotStorageBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock, ConnectedModelCapability {


    private static final EnumProperty<FurnitureConnectionState> TYPE = EnumProperty.create("type", FurnitureConnectionState.class);
    private static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public TagKey<Block> BlockSetFamily;

    private VoxelShape SOLO_SHAPE_NORTH;
    private VoxelShape SOLO_SHAPE_SOUTH;
    private VoxelShape SOLO_SHAPE_EAST;
    private VoxelShape SOLO_SHAPE_WEST;

    private VoxelShape LEFT_SHAPE_NORTH;
    private VoxelShape LEFT_SHAPE_SOUTH;
    private VoxelShape LEFT_SHAPE_EAST;
    private VoxelShape LEFT_SHAPE_WEST;

    private VoxelShape MIDDLE_SHAPE_NORTH;
    private VoxelShape MIDDLE_SHAPE_SOUTH;
    private VoxelShape MIDDLE_SHAPE_EAST;
    private VoxelShape MIDDLE_SHAPE_WEST;

    private VoxelShape RIGHT_SHAPE_NORTH;
    private VoxelShape RIGHT_SHAPE_SOUTH;
    private VoxelShape RIGHT_SHAPE_EAST;
    private VoxelShape RIGHT_SHAPE_WEST;

    public ConnectedFurniture9SlotStorageBlock(Properties properties, TagKey<Block> inputCompatibleBlockSet, VoxelShape soloShape, VoxelShape leftShape, VoxelShape middleShape, VoxelShape rightShape) {
        super(properties);

        SOLO_SHAPE_NORTH = soloShape;
        SOLO_SHAPE_SOUTH = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, soloShape);
        SOLO_SHAPE_EAST = RotationHelper.rotateVoxelHorizontal(Direction.EAST, soloShape);
        SOLO_SHAPE_WEST = RotationHelper.rotateVoxelHorizontal(Direction.WEST, soloShape);

        LEFT_SHAPE_NORTH = leftShape;
        LEFT_SHAPE_SOUTH = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, leftShape);
        LEFT_SHAPE_EAST = RotationHelper.rotateVoxelHorizontal(Direction.EAST, leftShape);
        LEFT_SHAPE_WEST = RotationHelper.rotateVoxelHorizontal(Direction.WEST, leftShape);

        MIDDLE_SHAPE_NORTH = middleShape;
        MIDDLE_SHAPE_SOUTH = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, middleShape);
        MIDDLE_SHAPE_EAST = RotationHelper.rotateVoxelHorizontal(Direction.EAST, middleShape);
        MIDDLE_SHAPE_WEST = RotationHelper.rotateVoxelHorizontal(Direction.WEST, middleShape);

        RIGHT_SHAPE_NORTH = rightShape;
        RIGHT_SHAPE_SOUTH = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, rightShape);
        RIGHT_SHAPE_EAST = RotationHelper.rotateVoxelHorizontal(Direction.EAST, rightShape);
        RIGHT_SHAPE_WEST = RotationHelper.rotateVoxelHorizontal(Direction.WEST, rightShape);

        this.BlockSetFamily = inputCompatibleBlockSet;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TYPE, FurnitureConnectionState.SOLO)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
        );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return ModBlockEntities.NINE_SLOT_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockState state = this.defaultBlockState();
        BlockPos positionClicked = pContext.getClickedPos(); //Get the position when player places new block
        Direction directionClicked = pContext.getHorizontalDirection(); //Gets the cardinal direction when player places new block
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction direction = pContext.getHorizontalDirection();
        state = state.setValue(FACING, direction);
        state = state.setValue(TYPE, getTypeAndFamily(state, getStateRelativeLeft(level, positionClicked, directionClicked), getStateRelativeRight(level, positionClicked, directionClicked), BlockSetFamily)); //Second, defines connection type of the block
        return state.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof NineSlotMenuBlockEntity blockEntity))
            return InteractionResult.PASS;

        if (level.isClientSide())
            return InteractionResult.SUCCESS;

        // open screen
        if (player instanceof ServerPlayer sPlayer) {
            level.playSound(player, pos, SoundEvents.NOTE_BLOCK_DIDGERIDOO.get(), SoundSource.BLOCKS,
                    1f, 1f);
            NetworkHooks.openScreen(sPlayer, blockEntity, pos);
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(TYPE)) {

            case SOLO: switch (pState.getValue(FACING)) {
                case SOUTH: return SOLO_SHAPE_SOUTH;
                case EAST: return SOLO_SHAPE_EAST;
                case WEST: return SOLO_SHAPE_WEST;
                default: return SOLO_SHAPE_NORTH;
            }

            case LEFT: switch (pState.getValue(FACING)) {
                case SOUTH: return LEFT_SHAPE_SOUTH;
                case EAST: return LEFT_SHAPE_EAST;
                case WEST: return LEFT_SHAPE_WEST;
                default: return LEFT_SHAPE_NORTH;
            }

            case MIDDLE: switch (pState.getValue(FACING)) {
                case SOUTH: return MIDDLE_SHAPE_SOUTH;
                case EAST: return MIDDLE_SHAPE_EAST;
                case WEST: return MIDDLE_SHAPE_WEST;
                default: return MIDDLE_SHAPE_NORTH;
            }

            case RIGHT: switch (pState.getValue(FACING)) {
                case SOUTH: return RIGHT_SHAPE_SOUTH;
                case EAST: return RIGHT_SHAPE_EAST;
                case WEST: return RIGHT_SHAPE_WEST;
                default: return RIGHT_SHAPE_NORTH;
            }

            default: return SOLO_SHAPE_NORTH;
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
//BlockState state, Level level, BlockPos positionClicked, Block block, BlockPos fromPos, boolean isMoving)
    public void neighborChanged(BlockState state, Level level, BlockPos positionClicked, Block block, BlockPos fromPos, boolean pIsMoving) {
        if (!level.isClientSide) {
            if (state.getValue(WATERLOGGED)) {
                level.scheduleTick(fromPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
        }
        if (level.isClientSide) return;

        Direction directionClicked = state.getValue(FACING);
        FurnitureConnectionState type = getTypeAndFamily(state, getStateRelativeLeft(level, positionClicked, directionClicked), getStateRelativeRight(level, positionClicked, directionClicked), BlockSetFamily);
        if (state.getValue(TYPE) == type) return;

        state = state.setValue(TYPE, type);


        level.setBlock(positionClicked, state, 3); //3
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) { //Determines item drop behavior when block broken
        if (!state.is(newState.getBlock())) { //If the block in the new updated state is different
            if (state.getBlock() != newState.getBlock()) { //If block after the update is different from the old block
                BlockEntity be = level.getBlockEntity(pos); //Save the selected block entity as "be"
                if (be instanceof NineSlotMenuBlockEntity blockEntity) { //If the block entity is a storage block entity
                    ItemStackHandler inventory = blockEntity.getInventory(); //read and store the block entity's inventory
                    for (int i = 0; i < inventory.getSlots(); i++) {
                        ItemStack stack = inventory.getStackInSlot(i); //One at a time, read and store each itemStack in each slot
                        if (!stack.isEmpty()) { //If that stack is NOT EMPTY
                            var entity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack); //spawn the current itemstack as an Item Entity inworld at this positon
                            level.addFreshEntity(entity); //Add a new entity
                            level.setBlockEntity(be); //Place block entity into current location
                        } //If an empty stack is reached, exit logic, remove block entity. There are no more items to salvage. If inventory is full, for loop will salvage all items inside, then then exit logic to allow block entity removal
                    }
                }
            }

            super.onRemove(state, level, pos, newState, isMoving); //Remove the block entity
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, TYPE); //Block's blockstates; its NSEW orientation, its connection type defined
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
}