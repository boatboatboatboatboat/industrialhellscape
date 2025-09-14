package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.RotationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class RailingBlock extends Block implements SimpleWaterloggedBlock {

    private static final double RAILING_HEIGHT = 17; //16 units is a full block height

    private static final VoxelShape VOXEL_NORTH = Block.box(
            0d, 0d, 14d,
            16d, RAILING_HEIGHT, 16d
    );
    private static final VoxelShape VOXEL_SOUTH = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, VOXEL_NORTH);
    private static final VoxelShape VOXEL_EAST = RotationHelper.rotateVoxelHorizontal(Direction.EAST, VOXEL_NORTH);
    private static final VoxelShape VOXEL_WEST = RotationHelper.rotateVoxelHorizontal(Direction.WEST, VOXEL_NORTH);

    public static final BooleanProperty NORTH_FENCE = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH_FENCE = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST_FENCE  = BlockStateProperties.EAST;
    public static final BooleanProperty WEST_FENCE  = BlockStateProperties.WEST;

    public RailingBlock (Properties props) {
        super(props);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(NORTH_FENCE, false)
                .setValue(SOUTH_FENCE, false)
                .setValue(EAST_FENCE,  false)
                .setValue(WEST_FENCE,  false)
                .setValue(BlockStateProperties.WATERLOGGED, false)
        );
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {

        if(pState.getBlock() instanceof RailingBlock ) {
            if (pState.getValue(NORTH_FENCE)) {
                return pUseContext.getItemInHand().is(this.asItem()) && pState.getValue(NORTH_FENCE) || super.canBeReplaced(pState, pUseContext);
            } else if (pState.getValue(SOUTH_FENCE)) {
                return pUseContext.getItemInHand().is(this.asItem()) && pState.getValue(SOUTH_FENCE) || super.canBeReplaced(pState, pUseContext);
            } else if (pState.getValue(EAST_FENCE)) {
                return pUseContext.getItemInHand().is(this.asItem()) && pState.getValue(EAST_FENCE) || super.canBeReplaced(pState, pUseContext);
            } else if (pState.getValue(WEST_FENCE)) {
                return pUseContext.getItemInHand().is(this.asItem()) && pState.getValue(WEST_FENCE) || super.canBeReplaced(pState, pUseContext);
            }
        }
        return super.canBeReplaced(pState, pUseContext);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement (BlockPlaceContext pContext) {
        BlockPos position = pContext.getClickedPos();
        Direction facing = pContext.getHorizontalDirection().getOpposite();
        FluidState fluid = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Block clickedTarget = pContext.getLevel().getBlockState(position).getBlock();
        boolean isRailingBlock = clickedTarget instanceof RailingBlock;

        if(!isRailingBlock) {
            BlockState state = defaultBlockState()
                    .setValue(NORTH_FENCE, (facing == Direction.NORTH))
                    .setValue(SOUTH_FENCE, (facing == Direction.SOUTH))
                    .setValue(EAST_FENCE, (facing == Direction.EAST))
                    .setValue(WEST_FENCE, (facing == Direction.WEST))
                    .setValue(BlockStateProperties.WATERLOGGED, fluid.getType() == Fluids.WATER);
            return state;
        } else {
            BlockState state = pContext.getLevel().getBlockState(position);
                   switch(facing) {
                       case NORTH -> state = state.setValue(NORTH_FENCE, true);
                       case SOUTH -> state = state.setValue(SOUTH_FENCE, true);
                       case EAST -> state = state.setValue(EAST_FENCE, true);
                       case WEST -> state = state.setValue(WEST_FENCE, true);
                   }
                    state.setValue(BlockStateProperties.WATERLOGGED, fluid.getType() == Fluids.WATER);
            return state;
        }
    }

    @Override
    public @Nonnull List<ItemStack> getDrops(BlockState pState, @Nonnull LootParams.Builder pParams) {
        int howManyToDrop = 0;
        if(pState.getValue(NORTH_FENCE)) howManyToDrop++; //increments for each railing placed
        if(pState.getValue(SOUTH_FENCE)) howManyToDrop++;
        if(pState.getValue(EAST_FENCE)) howManyToDrop++;
        if(pState.getValue(WEST_FENCE)) howManyToDrop++;

        return List.of(
                new ItemStack(this.asItem(), howManyToDrop)
        );
    }

    @Override
    protected void createBlockStateDefinition (StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH_FENCE);
        builder.add(SOUTH_FENCE);
        builder.add(EAST_FENCE);
        builder.add(WEST_FENCE);
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter reader, BlockPos pos, CollisionContext ctx) {
        return getCollisionShape(pState, reader, pos, ctx);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape shape = Shapes.empty();
        if (pState.getValue(NORTH_FENCE)) shape = Shapes.join(shape, VOXEL_NORTH, BooleanOp.OR);
        if (pState.getValue(SOUTH_FENCE)) shape = Shapes.join(shape, VOXEL_SOUTH, BooleanOp.OR);
        if (pState.getValue(EAST_FENCE))  shape = Shapes.join(shape, VOXEL_EAST,  BooleanOp.OR);
        if (pState.getValue(WEST_FENCE))  shape = Shapes.join(shape, VOXEL_WEST,  BooleanOp.OR);

        return shape;
    }

    @Override
    public boolean canPlaceLiquid (BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
        return !state.getValue(BlockStateProperties.WATERLOGGED) && fluid == Fluids.WATER;
    }

    // used to ensure the block doesn't leave a ghost behind if all 4 sides are gone
    @Override
    public void neighborChanged (
            BlockState state, Level level, BlockPos pos,
            Block neighborBlock, BlockPos neighborPos, boolean movedByPiston
    ) {

        if (isEmpty(state)) level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0);
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    public static BooleanProperty fromDirection (Direction face) {
        return switch (face) {
            case SOUTH -> SOUTH_FENCE;
            case EAST  -> EAST_FENCE;
            case WEST  -> WEST_FENCE;
            default -> NORTH_FENCE;
        };
    }

    public static boolean isEmpty (BlockState state) {
        boolean safe = false;
        for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            safe |= state.getValue(fromDirection(dir));
        }
        return !safe;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        boolean north = state.getValue(NORTH_FENCE);
        boolean south = state.getValue(SOUTH_FENCE);
        boolean east = state.getValue(EAST_FENCE);
        boolean west = state.getValue(WEST_FENCE);
        switch (rotation){
            case CLOCKWISE_90 -> {
                north = state.getValue(WEST_FENCE);
                south = state.getValue(EAST_FENCE);
                east = state.getValue(NORTH_FENCE);
                west = state.getValue(SOUTH_FENCE);
            }
            case CLOCKWISE_180 -> {
                north = state.getValue(SOUTH_FENCE);
                south = state.getValue(NORTH_FENCE);
                east = state.getValue(WEST_FENCE);
                west = state.getValue(EAST_FENCE);
            }
            case COUNTERCLOCKWISE_90 -> {
                north = state.getValue(EAST_FENCE);
                south = state.getValue(WEST_FENCE);
                east = state.getValue(SOUTH_FENCE);
                west = state.getValue(NORTH_FENCE);
            }
            case NONE -> {
                north = state.getValue(NORTH_FENCE);
                south = state.getValue(SOUTH_FENCE);
                east = state.getValue(EAST_FENCE);
                west = state.getValue(WEST_FENCE);
            }
        }
        BlockState newState = defaultBlockState().setValue(NORTH_FENCE, north).setValue(SOUTH_FENCE, south).setValue(EAST_FENCE, east).setValue(WEST_FENCE, west);
        return newState;
    }
}