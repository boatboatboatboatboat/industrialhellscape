package net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks;

import net.boat.industrialhellscape.block.modded_block_state_properties.DynamicConnectionState;
import net.boat.industrialhellscape.block.modded_block_state_properties.SurfacePipeMountState;
import net.boat.industrialhellscape.block.modded_interfaces.ConnectedModelInterface;
import net.boat.industrialhellscape.block.modded_interfaces.HitboxRotationInterface;
import net.boat.industrialhellscape.block.modded_interfaces.PipeInterface;
import net.boat.industrialhellscape.block.modded_interfaces.ToolUseInterface;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
//Can be placed on any surface (FACING). Additionally, can be rotated orthogonally on that surface (ORIENTATION).

public class WallPIpeBlock extends Block implements PipeInterface, SimpleWaterloggedBlock, ToolUseInterface {
    @Override
    public EnumProperty getOrientationProperty() {
        return ORIENTATION;
    }

    @Override
    public EnumProperty getSurfaceDirectionProperty() {
        return FACING;
    }

    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final EnumProperty<SurfacePipeMountState> ORIENTATION = EnumProperty.create("axis", SurfacePipeMountState.class);
    public static final EnumProperty<DynamicConnectionState> TYPE = EnumProperty.create("type", DynamicConnectionState.class); //"TYPE" is used to store enum value of "solo, pos, neg, middle"
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape SHAPE_FLOOR = Block.box(0, 0, 0, 16, 6, 16);
    public static final VoxelShape SHAPE_CEILING = Block.box(0, 10, 0, 16, 16, 16);

    public static final VoxelShape SHAPE_NORTH = Block.box(0, 0, 0, 16, 16, 6);
    public static final VoxelShape SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, SHAPE_NORTH);
    public static final VoxelShape SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, SHAPE_NORTH);
    public static final VoxelShape SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, SHAPE_NORTH);

    public WallPIpeBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(FACING, Direction.DOWN) //Default surface pipe is placed on
                .setValue(ORIENTATION, SurfacePipeMountState.STRAIGHT) //Default pipe orientation (straight or sideways)
                .setValue(TYPE, DynamicConnectionState.SOLO) //Default connection type is unconnected or "solo"
                .setValue(WATERLOGGED, false) //Default waterlogging status is false
        );
    }

    @Override
    public @Nonnull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            //6 Cases for collision box shape based on surface attached to
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
        BlockState state = this.defaultBlockState();
        Direction directionClicked = pContext.getClickedFace().getOpposite(); //Are you clicking the floor, ceiling, north wall, south wall, east wall, west wall? If clicking ceiling, result is UP
        Direction.Axis cardinalDirection = pContext.getHorizontalDirection().getAxis(); //What axis is the player facing when they place down the block?
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        Player player = pContext.getPlayer();

        //This section determines surface alignment based on where you click to place.
        state = state.setValue(FACING, directionClicked);

        //This section determines waterlogging.
        state =  state.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        //This section determines block rotation on the surface
        //      If you clicked to place on the floor or ceiling, the pipe axes will align with your nearest horizontal direction
        //      Therefore, clicking to place on walls defaults the pipe ORIENTATION to STRAIGHT. That default value is designated in the constructor matching super above.
        if(directionClicked == Direction.UP || directionClicked == Direction.DOWN) {
            if(cardinalDirection == Direction.Axis.X) { //Is player facing X axis? Align pipe STRAIGHT
                state = state.setValue(ORIENTATION, SurfacePipeMountState.STRAIGHT);
            } else { //Player must have been facing Z axis, align pipe SIDEWAYS
                state = state.setValue(ORIENTATION, SurfacePipeMountState.SIDEWAYS);
            }
        } else if(player != null) {
            if(player.isCrouching()) {
                state = state.setValue(ORIENTATION, SurfacePipeMountState.STRAIGHT);
            } else {
                state = state.setValue(ORIENTATION, SurfacePipeMountState.SIDEWAYS);
            }
        }

        // This section determines pipe connection type upon placement based on neighbors.
        // For surface-mounted blocks, this uses two custom methods with conditions to determine where the neighboring blocks are based on orientation and surface attachment.
        state = state.setValue(TYPE,
                getPipeType(
                        state,
                        getSurfacePositivePositionState(state, pos, level),
                        getSurfaceNegativePositionState(state, pos, level))
        );
        return state;

    }

    @Override //THIS TELLS THE NEIGHBORS TO UPDATE
    public void neighborChanged(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Block block, @Nonnull BlockPos fromPos, boolean isMoving) {
        if (!level.isClientSide) {
            if (state.getValue(WATERLOGGED)) {
                level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
            }
        }
        if (level.isClientSide) return;

        DynamicConnectionState type = getPipeType(
                state,
                getSurfacePositivePositionState(state, pos, level),
                getSurfaceNegativePositionState(state, pos, level)
        );

        state = state.setValue(TYPE, type);
        level.setBlock(pos, state, 3); //3
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        return crouchToolUse(stack, state, level, pos, player, TYPE, 2, ORIENTATION, 3);
    }

    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public @NotNull BlockState rotate(BlockState pState, @NotNull Rotation pRot) {

        if (pState.getValue(FACING).getAxis() == Direction.Axis.Y) { //Block is placed up or down
            SurfacePipeMountState originallyFacing = pState.getValue(ORIENTATION);
            SurfacePipeMountState clockwise90Facing;
            SurfacePipeMountState clockwise180Facing;
            SurfacePipeMountState counterClockwise90Facing;
            switch(originallyFacing) {
                case SIDEWAYS:
                    clockwise90Facing = SurfacePipeMountState.STRAIGHT;
                    clockwise180Facing = SurfacePipeMountState.SIDEWAYS;
                    counterClockwise90Facing = SurfacePipeMountState.STRAIGHT;
                    break;
                default: //STRAIGHT case
                    clockwise90Facing = SurfacePipeMountState.SIDEWAYS;
                    clockwise180Facing = SurfacePipeMountState.STRAIGHT;
                    counterClockwise90Facing = SurfacePipeMountState.SIDEWAYS;
                    break;
            }
            switch (pRot){
                case CLOCKWISE_90 -> pState = pState.setValue(ORIENTATION, clockwise90Facing);
                case CLOCKWISE_180 -> pState = pState.setValue(ORIENTATION, clockwise180Facing);
                case COUNTERCLOCKWISE_90 -> pState = pState.setValue(ORIENTATION, counterClockwise90Facing);
                default -> {} //Assumed to be case "NONE", therefore block is unchanged
            }
            return pState;

        } else { //block is not placed up or down (it is in the cardinal directions)
            return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
        }
    }

    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ORIENTATION, TYPE, WATERLOGGED); //Type defines connection state, Surface Direction defines which surface (up, down, cardinal) the block is placed on. Planar Axis is used to define pipe direction lengthwise.
    }

    // ---------- CUSTOM METHODS USED FOR THIS CLASS ----------
    //May be moved to an interface in the future if a similar block class is created with advanced getStateForPlacement or NeighborChanged functionality

    static BlockState getSurfacePositivePositionState(BlockState pState, BlockPos pos, Level level) {
        boolean facingEastWest = (pState.getValue(FACING) == Direction.EAST) || (pState.getValue(FACING) == Direction.WEST);

        //If block is attached to a wall (NORTH, SOUTH, EAST, WEST)
        if(pState.getValue(FACING) != Direction.UP && pState.getValue(FACING) != Direction.DOWN) {
            //And the pipe orientation is Straight (meaning pipes align horizontally on walls)
            if(pState.getValue(ORIENTATION) == SurfacePipeMountState.STRAIGHT)
            // - If it is on the east/west wall, check the positive Z-axis direction for an adjacent block (Z axis is horizontal to these walls)
            // - But if it is on the north/south wall, check the positive X-axis direction instead for an adjacent block (X axis is horizontal to these walls)
            {return facingEastWest ? ConnectedModelInterface.getStateAtAxisPositive(level, pos, Direction.Axis.Z) : ConnectedModelInterface.getStateAtAxisPositive(level, pos, Direction.Axis.X);}

            //But if the pipe orientation is Sideways, that means it is aligned vertical to the wall. Check the positive Y direction instead on all walls.
            else {return ConnectedModelInterface.getStateAtAxisPositive(level, pos, Direction.Axis.Y);}

            //ELSE If the block is attached to the floor or ceiling
        } else if (pState.getValue(FACING) == Direction.UP || pState.getValue(FACING) == Direction.DOWN) {
            //And the pipe orientation is Straight (meaning pipes align towards world X axis), Check the X axis for an adjacent block in positive direction.
            if(pState.getValue(ORIENTATION) == SurfacePipeMountState.STRAIGHT) {return ConnectedModelInterface.getStateAtAxisPositive(level, pos, Direction.Axis.X);}

            // But If the pipe is Sideways, check on the positive Z-axis direction instead.
            else {return ConnectedModelInterface.getStateAtAxisPositive(level, pos, Direction.Axis.Z);}
        }

        return pState; //Fallback. This should not be possible to reach.
    }

    static BlockState getSurfaceNegativePositionState(BlockState pState, BlockPos pos, Level level) {
        boolean facingEastWest = (pState.getValue(FACING) == Direction.EAST) || (pState.getValue(FACING) == Direction.WEST);

        //If block is attached to a wall (NORTH, SOUTH, EAST, WEST)
        if(pState.getValue(FACING) != Direction.UP && pState.getValue(FACING) != Direction.DOWN) {
            //And the pipe orientation is Straight (meaning pipes align horizontally on walls)
            if(pState.getValue(ORIENTATION) == SurfacePipeMountState.STRAIGHT)
            // - If it is on the east/west wall, check the negative Z-axis direction for an adjacent block (Z axis is horizontal to these walls)
            // - But if it is on the north/south wall, check the negative X-axis direction instead for an adjacent block (X axis is horizontal to these walls)
            {return facingEastWest ? ConnectedModelInterface.getStateAtAxisNegative(level, pos, Direction.Axis.Z) : ConnectedModelInterface.getStateAtAxisNegative(level, pos, Direction.Axis.X);}

            //But if the pipe orientation is Sideways, that means it is aligned vertical to the wall. Check the negative Y direction instead on all walls.
            else {return ConnectedModelInterface.getStateAtAxisNegative(level, pos, Direction.Axis.Y);}

            //ELSE If the block is attached to the floor or ceiling
        } else if (pState.getValue(FACING) == Direction.UP || pState.getValue(FACING) == Direction.DOWN) {
            //And the pipe orientation is Straight (meaning pipes align towards world X axis), Check the X axis for an adjacent block in negative direction.
            if(pState.getValue(ORIENTATION) == SurfacePipeMountState.STRAIGHT) {return ConnectedModelInterface.getStateAtAxisNegative(level, pos, Direction.Axis.X);}

            // But If the pipe is Sideways, check on the negative Z-axis direction instead.
            else {return ConnectedModelInterface.getStateAtAxisNegative(level, pos, Direction.Axis.Z);}
        }

        return pState; //Fallback. This should not be possible to reach.
    }

    // ---------- END OF CUSTOM METHODS ----------
}