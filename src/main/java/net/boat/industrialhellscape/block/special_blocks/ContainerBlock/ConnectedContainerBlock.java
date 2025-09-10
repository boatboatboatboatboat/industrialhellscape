package net.boat.industrialhellscape.block.special_blocks.ContainerBlock;

import net.boat.industrialhellscape.block.special_blocks_properties.ConnectedModelCapability;
import net.boat.industrialhellscape.block.special_blocks_properties.FurnitureConnectionState;
import net.boat.industrialhellscape.block.special_blocks_properties.RotationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ConnectedContainerBlock extends FacingContainerBlock implements EntityBlock, SimpleWaterloggedBlock, ConnectedModelCapability {


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

    public ConnectedContainerBlock(Properties properties, int slotAmount, TagKey<Block> inputCompatibleBlockSet, VoxelShape soloShape, VoxelShape leftShape, VoxelShape middleShape, VoxelShape rightShape, SoundEvent openSound, SoundEvent closeSound) {
        super(properties, slotAmount, openSound, closeSound);

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
        state =  state.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
        return state;
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, TYPE); //Block's blockstates; its NSEW orientation, its connection type defined
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
}