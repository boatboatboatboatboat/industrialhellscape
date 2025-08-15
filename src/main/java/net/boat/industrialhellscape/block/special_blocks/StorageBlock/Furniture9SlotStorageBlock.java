package net.boat.industrialhellscape.block.special_blocks.StorageBlock;

import net.boat.industrialhellscape.block.special_blocks_properties.HitboxGeometryCollection;
import net.boat.industrialhellscape.block.special_blocks_properties.ModBlockEntities;
import net.boat.industrialhellscape.block.special_blocks_properties.RotationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import net.minecraft.world.level.block.RenderShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Furniture9SlotStorageBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock, HitboxGeometryCollection {

    private VoxelShape SHAPE_NORTH;
    private VoxelShape SHAPE_SOUTH;
    private VoxelShape SHAPE_EAST;
    private VoxelShape SHAPE_WEST;


    private static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public Furniture9SlotStorageBlock(Properties pProperties, VoxelShape pHitbox) {
        super(pProperties);
        SHAPE_NORTH = pHitbox;
        SHAPE_SOUTH = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, SHAPE_NORTH);
        SHAPE_EAST = RotationHelper.rotateVoxelHorizontal(Direction.EAST, SHAPE_NORTH);
        SHAPE_WEST = RotationHelper.rotateVoxelHorizontal(Direction.WEST, SHAPE_NORTH);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return ModBlockEntities.STORAGE_9_BLOCK_ENTITY.get().create(pos, state);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = this.defaultBlockState();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction direction = pContext.getHorizontalDirection();
        state = state.setValue(FACING, direction.getOpposite());
        return state.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof Storage9SlotBlockEntity blockEntity))
            return InteractionResult.PASS;

        if (level.isClientSide())
            return InteractionResult.SUCCESS;

        // open screen
        if (player instanceof ServerPlayer sPlayer) {
            NetworkHooks.openScreen(sPlayer, blockEntity, pos);
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(FACING)) {
            case EAST: return SHAPE_EAST;
            case SOUTH: return SHAPE_SOUTH;
            case WEST: return SHAPE_WEST;
            default: return SHAPE_NORTH;
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            if (pState.getValue(WATERLOGGED)) {
                pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
            }
        }
    }

    @Override
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED); //Block's blockstates; its NSEW orientation, its connection type defined
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
}