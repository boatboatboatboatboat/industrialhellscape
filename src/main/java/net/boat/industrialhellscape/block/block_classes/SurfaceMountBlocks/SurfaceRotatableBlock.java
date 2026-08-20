package net.boat.industrialhellscape.block.block_classes.SurfaceMountBlocks;

import net.boat.industrialhellscape.block.block_state_enums.RelativePlanarDirectionState;
import net.boat.industrialhellscape.block.block_interfaces.HitboxRotationInterface;
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

//INFO:
//-----
//Block can be placed on walls, ceilings, and floors (FACING). In addition to this, it can be rotated in four directions on that surface (PLANE_DIRECTION)
//I hate this, but maybe it can be useful for something else

public class SurfaceRotatableBlock extends Block implements SimpleWaterloggedBlock, ToolUseInterface {

    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final EnumProperty<RelativePlanarDirectionState> PLANE_DIRECTION = EnumProperty.create("plane_direction", RelativePlanarDirectionState.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape SHAPE_FLOOR = Block.box(0, 0, 0, 16, 6, 16);
    public static final VoxelShape SHAPE_CEILING = Block.box(0, 10, 0, 16, 16, 16);

    public static final VoxelShape SHAPE_NORTH = Block.box(0, 0, 0, 16, 16, 6);
    public static final VoxelShape SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, SHAPE_NORTH);
    public static final VoxelShape SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, SHAPE_NORTH);
    public static final VoxelShape SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, SHAPE_NORTH);

    public SurfaceRotatableBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.NORTH)
                .setValue(PLANE_DIRECTION, RelativePlanarDirectionState.UP)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public @Nonnull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            //6 Cases for collision box shape
            case UP -> SHAPE_CEILING;
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_FLOOR;
        };
    }
    @Override
    public @Nonnull RenderShape getRenderShape(@Nonnull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction facing = pContext.getHorizontalDirection();
        BlockState state = this.defaultBlockState();
        Direction directionClicked = pContext.getClickedFace().getOpposite(); //Are you clicking the floor, ceiling, north wall, south wall, east wall, west wall?
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());

        //This section determines surface alignment based on where you click to place.
        state = state.setValue(FACING, directionClicked);

        //This section determines waterlogging.
        state =  state.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        if(directionClicked == Direction.UP || directionClicked == Direction.DOWN) {
            switch (facing) {
                case NORTH -> state = state.setValue(PLANE_DIRECTION, RelativePlanarDirectionState.UP);
                case SOUTH -> state = state.setValue(PLANE_DIRECTION, RelativePlanarDirectionState.DOWN);
                case WEST -> state = state.setValue(PLANE_DIRECTION, RelativePlanarDirectionState.LEFT);
                case EAST -> state = state.setValue(PLANE_DIRECTION, RelativePlanarDirectionState.RIGHT);
            }
            return state;
        }
        //default case
        return state.setValue(PLANE_DIRECTION, RelativePlanarDirectionState.UP);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        return ToolUseInterface.crouchToolUse(stack, state, level, pos, player, FACING, 3, PLANE_DIRECTION, 3);
    }

    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public @NotNull BlockState rotate(BlockState pState, @NotNull Rotation pRot) {
        if (pState.getValue(FACING).getAxis() == Direction.Axis.Y) { //Block is placed up or down
            RelativePlanarDirectionState originallyFacing = pState.getValue(PLANE_DIRECTION);
            RelativePlanarDirectionState clockwise90Facing;
            RelativePlanarDirectionState clockwise180Facing;
            RelativePlanarDirectionState counterClockwise90Facing;
            switch(originallyFacing) {
                case LEFT:
                    clockwise90Facing = RelativePlanarDirectionState.UP;
                    clockwise180Facing = RelativePlanarDirectionState.RIGHT;
                    counterClockwise90Facing = RelativePlanarDirectionState.DOWN;
                    break;
                case DOWN:
                    clockwise90Facing = RelativePlanarDirectionState.LEFT;
                    clockwise180Facing = RelativePlanarDirectionState.UP;
                    counterClockwise90Facing = RelativePlanarDirectionState.RIGHT;
                    break;
                case RIGHT:
                    clockwise90Facing = RelativePlanarDirectionState.DOWN;
                    clockwise180Facing = RelativePlanarDirectionState.LEFT;
                    counterClockwise90Facing = RelativePlanarDirectionState.UP;
                    break;
                default:
                    clockwise90Facing = RelativePlanarDirectionState.RIGHT;
                    clockwise180Facing = RelativePlanarDirectionState.DOWN;
                    counterClockwise90Facing = RelativePlanarDirectionState.LEFT;
                    break;
            }
            switch (pRot){
                case CLOCKWISE_90 -> pState = pState.setValue(PLANE_DIRECTION, clockwise90Facing);
                case CLOCKWISE_180 -> pState = pState.setValue(PLANE_DIRECTION, clockwise180Facing);
                case COUNTERCLOCKWISE_90 -> pState = pState.setValue(PLANE_DIRECTION, counterClockwise90Facing);
                default -> {} //Assumed to be case "NONE", therefore block is unchanged
            }
            return pState;

        } else { //block is not placed up or down (it is in the cardinal directions)
            return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
        }
    }

    @Override
    public @NotNull BlockState mirror(BlockState pState, @NotNull Mirror mirror) {
        RelativePlanarDirectionState originallyFacing = pState.getValue(PLANE_DIRECTION);
        RelativePlanarDirectionState leftRightMirror;
        RelativePlanarDirectionState frontBackMirror;

        if (pState.getValue(FACING) == Direction.DOWN) { //Block is placed on floor or ceiling
            switch (originallyFacing) {
                case LEFT:
                    leftRightMirror = RelativePlanarDirectionState.UP;
                    frontBackMirror = RelativePlanarDirectionState.DOWN;
                    break;
                case DOWN:
                    leftRightMirror = RelativePlanarDirectionState.RIGHT;
                    frontBackMirror = RelativePlanarDirectionState.LEFT;
                    break;
                case RIGHT:
                    leftRightMirror = RelativePlanarDirectionState.DOWN;
                    frontBackMirror = RelativePlanarDirectionState.UP;
                    break;
                default:
                    leftRightMirror = RelativePlanarDirectionState.LEFT;
                    frontBackMirror = RelativePlanarDirectionState.RIGHT;
                    break;
            }
            switch (mirror) {
                case LEFT_RIGHT -> pState = pState.setValue(PLANE_DIRECTION, leftRightMirror);
                case FRONT_BACK -> pState = pState.setValue(PLANE_DIRECTION, frontBackMirror);
                default -> {
                    //Assumed to be case "NONE", therefore block is unchanged
                }
            }
            return pState.rotate(mirror.getRotation(pState.getValue(FACING)));

        } else if (pState.getValue(FACING) == Direction.UP) {
            switch (originallyFacing) {
                case LEFT:
                    leftRightMirror = RelativePlanarDirectionState.DOWN;
                    frontBackMirror = RelativePlanarDirectionState.UP;
                    break;
                case DOWN:
                    leftRightMirror = RelativePlanarDirectionState.LEFT;
                    frontBackMirror = RelativePlanarDirectionState.RIGHT;
                    break;
                case RIGHT:
                    leftRightMirror = RelativePlanarDirectionState.UP;
                    frontBackMirror = RelativePlanarDirectionState.DOWN;
                    break;
                default:
                    leftRightMirror = RelativePlanarDirectionState.RIGHT;
                    frontBackMirror = RelativePlanarDirectionState.LEFT;
                    break;
            }
            switch (mirror) {
                case LEFT_RIGHT -> pState = pState.setValue(PLANE_DIRECTION, leftRightMirror);
                case FRONT_BACK -> pState = pState.setValue(PLANE_DIRECTION, frontBackMirror);
                default -> {
                    //Assumed to be case "NONE", therefore block is unchanged
                }
            }
            return pState.rotate(mirror.getRotation(pState.getValue(FACING)));


        } else { //Block is placed on walls
            switch (originallyFacing) {
                case LEFT:
                    leftRightMirror = RelativePlanarDirectionState.DOWN;
                    frontBackMirror = RelativePlanarDirectionState.DOWN;
                    break;
                case DOWN:
                    leftRightMirror = RelativePlanarDirectionState.LEFT;
                    frontBackMirror = RelativePlanarDirectionState.LEFT;
                    break;
                case RIGHT:
                    leftRightMirror = RelativePlanarDirectionState.UP;
                    frontBackMirror = RelativePlanarDirectionState.UP;
                    break;
                default:
                    leftRightMirror = RelativePlanarDirectionState.RIGHT;
                    frontBackMirror = RelativePlanarDirectionState.RIGHT;
                    break;
            }
            switch (mirror) {
                case LEFT_RIGHT -> pState = pState.setValue(PLANE_DIRECTION, leftRightMirror);
                case FRONT_BACK -> pState = pState.setValue(PLANE_DIRECTION, frontBackMirror);
                default -> {
                    //Assumed to be case "NONE", therefore block is unchanged
                }
            }
            return pState.rotate(mirror.getRotation(pState.getValue(FACING)));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PLANE_DIRECTION, WATERLOGGED);
    }
}