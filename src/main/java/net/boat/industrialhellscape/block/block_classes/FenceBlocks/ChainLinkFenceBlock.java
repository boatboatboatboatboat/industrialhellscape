package net.boat.industrialhellscape.block.block_classes.FenceBlocks;

import com.google.common.collect.ImmutableMap;
import net.boat.industrialhellscape.block.block_interfaces.ToolUseInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.Map;

public class ChainLinkFenceBlock extends WallBlock implements ToolUseInterface {

    private final Map<BlockState, VoxelShape> shapeByIndex;
    private final Map<BlockState, VoxelShape> collisionShapeByIndex;

    public ChainLinkFenceBlock(Properties properties) {
        super(properties);
        this.shapeByIndex = this.makeShapes(2.0F, 2.0F, 16.0F, 0.0F, 14.0F, 16.0F);
        this.collisionShapeByIndex = this.makeShapes(2.0F, 2.0F, 24.0F, 0.0F, 24.0F, 24.0F);
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return (VoxelShape)this.shapeByIndex.get(state);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return (VoxelShape)this.collisionShapeByIndex.get(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockPos abovePos = pos.above();
        BlockState aboveState = level.getBlockState(abovePos);

        if(aboveState.is(this)) {

        }

        return super.getStateForPlacement(context);
    }

    private Map<BlockState, VoxelShape> makeShapes(float width, float depth, float wallPostHeight, float wallMinY, float wallLowHeight, float wallTallHeight) {
        float f = 8.0F - width;
        float f1 = 8.0F + width;
        float f2 = 8.0F - depth;
        float f3 = 8.0F + depth;
        VoxelShape voxelshape = Block.box((double) f, (double) 0.0F, (double) f, (double) f1, (double) wallPostHeight, (double) f1);
        VoxelShape voxelshape1 = Block.box((double) f2, (double) wallMinY, (double) 0.0F, (double) f3, (double) wallLowHeight, (double) f3);
        VoxelShape voxelshape2 = Block.box((double) f2, (double) wallMinY, (double) f2, (double) f3, (double) wallLowHeight, (double) 16.0F);
        VoxelShape voxelshape3 = Block.box((double) 0.0F, (double) wallMinY, (double) f2, (double) f3, (double) wallLowHeight, (double) f3);
        VoxelShape voxelshape4 = Block.box((double) f2, (double) wallMinY, (double) f2, (double) 16.0F, (double) wallLowHeight, (double) f3);
        VoxelShape voxelshape5 = Block.box((double) f2, (double) wallMinY, (double) 0.0F, (double) f3, (double) wallTallHeight, (double) f3);
        VoxelShape voxelshape6 = Block.box((double) f2, (double) wallMinY, (double) f2, (double) f3, (double) wallTallHeight, (double) 16.0F);
        VoxelShape voxelshape7 = Block.box((double) 0.0F, (double) wallMinY, (double) f2, (double) f3, (double) wallTallHeight, (double) f3);
        VoxelShape voxelshape8 = Block.box((double) f2, (double) wallMinY, (double) f2, (double) 16.0F, (double) wallTallHeight, (double) f3);
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (Boolean obool : UP.getPossibleValues()) {
            for (WallSide wallside : EAST_WALL.getPossibleValues()) {
                for (WallSide wallside1 : NORTH_WALL.getPossibleValues()) {
                    for (WallSide wallside2 : WEST_WALL.getPossibleValues()) {
                        for (WallSide wallside3 : SOUTH_WALL.getPossibleValues()) {
                            VoxelShape voxelshape9 = Shapes.empty();
                            voxelshape9 = applyWallShape(voxelshape9, wallside, voxelshape4, voxelshape8);
                            voxelshape9 = applyWallShape(voxelshape9, wallside2, voxelshape3, voxelshape7);
                            voxelshape9 = applyWallShape(voxelshape9, wallside1, voxelshape1, voxelshape5);
                            voxelshape9 = applyWallShape(voxelshape9, wallside3, voxelshape2, voxelshape6);
                            if (obool) {
                                voxelshape9 = Shapes.or(voxelshape9, voxelshape);
                            }

                            BlockState blockstate = (BlockState) ((BlockState) ((BlockState) ((BlockState) ((BlockState) this.defaultBlockState().setValue(UP, obool)).setValue(EAST_WALL, wallside)).setValue(WEST_WALL, wallside2)).setValue(NORTH_WALL, wallside1)).setValue(SOUTH_WALL, wallside3);
                            builder.put((BlockState) blockstate.setValue(WATERLOGGED, false), voxelshape9);
                            builder.put((BlockState) blockstate.setValue(WATERLOGGED, true), voxelshape9);
                        }
                    }
                }
            }
        }
        return builder.build();
    }

    private static VoxelShape applyWallShape(VoxelShape baseShape, WallSide height, VoxelShape lowShape, VoxelShape tallShape) {
        if (height == WallSide.TALL) {
            return Shapes.or(baseShape, tallShape);
        } else {
            return height == WallSide.LOW ? Shapes.or(baseShape, lowShape) : baseShape;
        }
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return ToolUseInterface.simpleToolUse(stack,state,level,pos,player,UP, 3);
    }

//    @Override
//    public BlockState getStateForPlacement(BlockPlaceContext context) {
//        //Same as wallblock
//        LevelReader levelreader = context.getLevel();
//        BlockPos blockpos = context.getClickedPos();
//        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
//        BlockPos blockpos1 = blockpos.north();
//        BlockPos blockpos2 = blockpos.east();
//        BlockPos blockpos3 = blockpos.south();
//        BlockPos blockpos4 = blockpos.west();
//        BlockPos blockpos5 = blockpos.above();
//        BlockState blockstate = levelreader.getBlockState(blockpos1);
//        BlockState blockstate1 = levelreader.getBlockState(blockpos2);
//        BlockState blockstate2 = levelreader.getBlockState(blockpos3);
//        BlockState blockstate3 = levelreader.getBlockState(blockpos4);
//        BlockState blockstate4 = levelreader.getBlockState(blockpos5);
//        boolean flag = this.chainFenceConnectsTo(blockstate, blockstate.isFaceSturdy(levelreader, blockpos1, Direction.SOUTH), Direction.SOUTH);
//        boolean flag1 = this.chainFenceConnectsTo(blockstate1, blockstate1.isFaceSturdy(levelreader, blockpos2, Direction.WEST), Direction.WEST);
//        boolean flag2 = this.chainFenceConnectsTo(blockstate2, blockstate2.isFaceSturdy(levelreader, blockpos3, Direction.NORTH), Direction.NORTH);
//        boolean flag3 = this.chainFenceConnectsTo(blockstate3, blockstate3.isFaceSturdy(levelreader, blockpos4, Direction.EAST), Direction.EAST);
//        BlockState blockstate5 = (BlockState)this.defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
//        return this.updateShape(levelreader, blockstate5, blockpos5, blockstate4, flag, flag1, flag2, flag3);
//    }

    //copied methods over

//    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
//        if ((Boolean)state.getValue(WATERLOGGED)) {
//            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
//        }
//
//        if (facing == Direction.DOWN) {
//            return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
//        } else {
//            return facing == Direction.UP ? this.topUpdate(level, state, facingPos, facingState) : this.sideUpdate(level, currentPos, state, facingPos, facingState, facing);
//        }
//    }
//
//    private BlockState topUpdate(LevelReader level, BlockState state, BlockPos pos, BlockState secondState) {
//        boolean flag = isConnected(state, NORTH_WALL);
//        boolean flag1 = isConnected(state, EAST_WALL);
//        boolean flag2 = isConnected(state, SOUTH_WALL);
//        boolean flag3 = isConnected(state, WEST_WALL);
//        return this.updateShape(level, state, pos, secondState, flag, flag1, flag2, flag3);
//    }
//
//    private BlockState updateShape(LevelReader level, BlockState state, BlockPos pos, BlockState neighbour, boolean northConnection, boolean eastConnection, boolean southConnection, boolean westConnection) {
//        VoxelShape voxelshape = neighbour.getCollisionShape(level, pos).getFaceShape(Direction.DOWN);
//        BlockState blockstate = this.updateSides(state, northConnection, eastConnection, southConnection, westConnection, voxelshape);
//        return (BlockState)blockstate.setValue(UP, this.shouldRaisePost(blockstate, neighbour, voxelshape));
//    }
//
//    private boolean shouldRaisePost(BlockState state, BlockState neighbour, VoxelShape shape) {
//        boolean flag = neighbour.getBlock() instanceof WallBlock && (Boolean)neighbour.getValue(UP);
//        if (flag) {
//            return true;
//        } else {
//            WallSide wallside = (WallSide)state.getValue(NORTH_WALL);
//            WallSide wallside1 = (WallSide)state.getValue(SOUTH_WALL);
//            WallSide wallside2 = (WallSide)state.getValue(EAST_WALL);
//            WallSide wallside3 = (WallSide)state.getValue(WEST_WALL);
//            boolean flag1 = wallside1 == WallSide.NONE;
//            boolean flag2 = wallside3 == WallSide.NONE;
//            boolean flag3 = wallside2 == WallSide.NONE;
//            boolean flag4 = wallside == WallSide.NONE;
//            boolean flag5 = flag4 && flag1 && flag2 && flag3 || flag4 != flag1 || flag2 != flag3;
//            if (flag5) {
//                return true;
//            } else {
//                boolean flag6 = wallside == WallSide.TALL && wallside1 == WallSide.TALL || wallside2 == WallSide.TALL && wallside3 == WallSide.TALL;
//                return flag6 ? false : neighbour.is(BlockTags.WALL_POST_OVERRIDE) || isCovered(shape, POST_TEST);
//            }
//        }
//    }
//
//    private BlockState updateSides(BlockState state, boolean northConnection, boolean eastConnection, boolean southConnection, boolean westConnection, VoxelShape wallShape) {
//        return (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(NORTH_WALL, this.makeWallState(northConnection, wallShape, NORTH_TEST))).setValue(EAST_WALL, this.makeWallState(eastConnection, wallShape, EAST_TEST))).setValue(SOUTH_WALL, this.makeWallState(southConnection, wallShape, SOUTH_TEST))).setValue(WEST_WALL, this.makeWallState(westConnection, wallShape, WEST_TEST));
//    }
//
//    private WallSide makeWallState(boolean allowConnection, VoxelShape shape, VoxelShape neighbourShape) {
//        if (allowConnection) {
//            return isCovered(shape, neighbourShape) ? WallSide.TALL : WallSide.LOW;
//        } else {
//            return WallSide.NONE;
//        }
//    }
//
//    private static boolean isCovered(VoxelShape firstShape, VoxelShape secondShape) {
//        return !Shapes.joinIsNotEmpty(secondShape, firstShape, BooleanOp.ONLY_FIRST);
//    }
//
//    private static boolean isConnected(BlockState state, Property<WallSide> heightProperty) {
//        return state.getValue(heightProperty) != WallSide.NONE;
//    }
//
//    private BlockState sideUpdate(LevelReader level, BlockPos firstPos, BlockState firstState, BlockPos secondPos, BlockState secondState, Direction dir) {
//        Direction direction = dir.getOpposite();
//        boolean flag = dir == Direction.NORTH ? this.connectsTo(secondState, secondState.isFaceSturdy(level, secondPos, direction), direction) : isConnected(firstState, NORTH_WALL);
//        boolean flag1 = dir == Direction.EAST ? this.connectsTo(secondState, secondState.isFaceSturdy(level, secondPos, direction), direction) : isConnected(firstState, EAST_WALL);
//        boolean flag2 = dir == Direction.SOUTH ? this.connectsTo(secondState, secondState.isFaceSturdy(level, secondPos, direction), direction) : isConnected(firstState, SOUTH_WALL);
//        boolean flag3 = dir == Direction.WEST ? this.connectsTo(secondState, secondState.isFaceSturdy(level, secondPos, direction), direction) : isConnected(firstState, WEST_WALL);
//        BlockPos blockpos = firstPos.above();
//        BlockState blockstate = level.getBlockState(blockpos);
//        return this.updateShape(level, firstState, blockpos, blockstate, flag, flag1, flag2, flag3);
//    }
//
//    private boolean connectsTo(BlockState state, boolean sideSolid, Direction direction) {
//        Block block = state.getBlock();
//        boolean flag = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
//        return state.is(BlockTags.WALLS) || !isExceptionForConnection(state) && sideSolid || block instanceof IronBarsBlock || flag;
//    }

    //-----

    private boolean chainFenceConnectsTo(BlockState state, boolean sideSolid, Direction direction) {
        Block block = state.getBlock();
        boolean flag = block instanceof FenceGateBlock && FenceGateBlock.connectsToDirection(state, direction);
        return state.is(this) || !isExceptionForConnection(state) && sideSolid || block instanceof IronBarsBlock || flag;
    }

    @Override
    public @Nonnull RenderShape getRenderShape(@Nonnull BlockState state) {
        return RenderShape.MODEL;
    }
}
