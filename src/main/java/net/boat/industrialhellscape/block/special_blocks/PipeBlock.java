package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.ConnectedModelCapability;
import net.boat.industrialhellscape.block.special_blocks_properties.PillarConnectionState;
import net.boat.industrialhellscape.block.special_blocks_properties.RotationHelper;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
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
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class PipeBlock extends Block implements ConnectedModelCapability, SimpleWaterloggedBlock {

    public static final EnumProperty<Direction> SURFACE_ATTACHED = BlockStateProperties.FACING;
    public static final EnumProperty<Direction.Axis> PLANAR_AXIS = BlockStateProperties.AXIS;
    public static final EnumProperty<PillarConnectionState> TYPE = EnumProperty.create("type", PillarConnectionState.class); //"TYPE" is used to store enum value of "solo, pos, neg, middle"
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape SHAPE_FLOOR = Block.box(0, 0, 0, 16, 6, 16);
    public static final VoxelShape SHAPE_CEILING = Block.box(0, 10, 0, 16, 16, 16);

    public static final VoxelShape SHAPE_NORTH = Block.box(0, 0, 0, 16, 16, 6);
    public static final VoxelShape SHAPE_SOUTH = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, SHAPE_NORTH);
    public static final VoxelShape SHAPE_EAST = RotationHelper.rotateVoxelHorizontal(Direction.EAST, SHAPE_NORTH);
    public static final VoxelShape SHAPE_WEST = RotationHelper.rotateVoxelHorizontal(Direction.WEST, SHAPE_NORTH);

    public PipeBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(SURFACE_ATTACHED, Direction.DOWN) //Default surface pipe is placed on
                .setValue(PLANAR_AXIS, Direction.Axis.Z) //Default North/South pipe direction
                .setValue(TYPE, PillarConnectionState.SOLO)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public @Nonnull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        return switch (pState.getValue(SURFACE_ATTACHED)) {
            //6 Cases for collision box shape based on surface attached to. Ignores pipe axial orientation
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
        Direction directionClicked = pContext.getClickedFace().getOpposite(); //Are you clicking the floor, ceiling, north wall, south wall, east wall, west wall?
        Direction.Axis cardinalDirection = pContext.getHorizontalDirection().getAxis();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());

        //This section determines surface alignment based on where you click to place.
        state = state.setValue(SURFACE_ATTACHED, directionClicked);

        //This section determines waterlogging.
        state =  state.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        //This section determines block rotation on the surface
        if(directionClicked == Direction.UP || directionClicked == Direction.DOWN) { //If you clicked to place on the floor or ceiling, the pipe axes will align with your nearest horizontal direction
            switch(cardinalDirection) {
                case X: return state.setValue(PLANAR_AXIS, Direction.Axis.X); //If you're facing positive X axis or negative X axis, the pipes placed will follow that alignment
                case Z: return state.setValue(PLANAR_AXIS, Direction.Axis.Z); //If you're facing positive Z axis or negative Z axis, the pipes placed will follow that alignment
            }

            //Below cases address if you wanted to place pipes on walls. By default the pipes will place horizontally. You will have to use a tool to align them vertically (Y axis) if you want
        } else if(directionClicked == Direction.EAST || directionClicked == Direction.WEST){ //If you clicked east or west, the pipes placed will align horizontal on the wall (z axis)
            return state.setValue(PLANAR_AXIS, Direction.Axis.Z);
        } else {
            return state.setValue(PLANAR_AXIS, Direction.Axis.X); //Else (you clicked a north or south wall), the pipes placed will align horizontal on the wall (x axis)
        }

        return state;

    }

    @Override //THIS TELLS THE NEIGHBORS TO UPDATE
    public void neighborChanged(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Block block, @Nonnull BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide) return;

        Direction.Axis xzaxis = state.getValue(PLANAR_AXIS);
        PillarConnectionState type = getPipeType(state, getStateAxisPositive(level, pos, xzaxis), getStateAxisNegative(level, pos, xzaxis));
        if (state.getValue(TYPE) == type) return;

        state = state.setValue(TYPE, type);
        level.setBlock(pos, state, 3);
    }

    public @Nonnull InteractionResult use(BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {

        boolean playerHasTool = pPlayer.getMainHandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS) || pPlayer.getOffhandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS);
        Direction surfaceAttached = pState.getValue(SURFACE_ATTACHED);

        //Cycles from vertical and horizontal pipes when interacting with pipes on walls
        if(playerHasTool && (surfaceAttached != Direction.UP && surfaceAttached != Direction.DOWN)) { //If player has tool, change the pipe orientation for walls
            switch(pState.getValue(PLANAR_AXIS)) {
                case X: pState = pState.setValue(PLANAR_AXIS, Direction.Axis.Y);break;
                case Z: pState = pState.setValue(PLANAR_AXIS, Direction.Axis.Y);break;
                case Y:
                    if(surfaceAttached == Direction.NORTH || surfaceAttached == Direction.SOUTH) {
                        pState = pState.setValue(PLANAR_AXIS, Direction.Axis.X); break;
                    }
                default: pState = pState.setValue(PLANAR_AXIS, Direction.Axis.Z); break;
            }
            pLevel.setBlock(pPos, pState, Block.UPDATE_ALL);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);

        } else if (playerHasTool){ //If player has tool, change the pipe orientation for walls and floors
            switch(pState.getValue(PLANAR_AXIS)) {
                case Z: pState = pState.setValue(PLANAR_AXIS, Direction.Axis.X); break;
                default: pState = pState.setValue(PLANAR_AXIS, Direction.Axis.Z); break;
            }
            pLevel.setBlock(pPos, pState, Block.UPDATE_ALL);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);

        } else {
            return  InteractionResult.PASS;
        }
    }

    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SURFACE_ATTACHED, PLANAR_AXIS, TYPE, WATERLOGGED); //Type defines connection state, Surface Direction defines which surface (up, down, cardinal) the block is placed on. Planar Axis is used to define pipe direction lengthwise.
    }
}