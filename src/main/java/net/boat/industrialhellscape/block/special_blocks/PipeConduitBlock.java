package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.PillarConnectionState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PipeConduitBlock extends Block {

    public static final EnumProperty<Direction.Axis> XORZAXIS = BlockStateProperties.HORIZONTAL_AXIS; //"AXIS" is used to store the block state direction. X or Z
    public static final EnumProperty<Direction> UPORDOWN = BlockStateProperties.VERTICAL_DIRECTION; //Used to store whether block attaches to wall or ceiling. UP or Down
    public static final EnumProperty<PillarConnectionState> TYPE = EnumProperty.create("type", PillarConnectionState.class); //"TYPE" is used to store enum value of "solo, positive, right, middle" for block connected variants

    public static final VoxelShape SHAPE_DOWN = Block.box(0, 0, 0, 16, 6, 16);
    public static final VoxelShape SHAPE_UP = Block.box(0, 10, 0, 16, 16, 16);

    public PipeConduitBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(TYPE, PillarConnectionState.SOLO)
                .setValue(XORZAXIS, Direction.Axis.Z)
                .setValue(UPORDOWN, Direction.DOWN)
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.getValue(UPORDOWN) == Direction.UP) {
            return SHAPE_UP;
        } else {
            return SHAPE_DOWN;
        }
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }


    //Placement Faces the player
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockState state = this.defaultBlockState();
        BlockPos pos = pContext.getClickedPos();
        Direction.Axis axisHorizontalFacing = pContext.getHorizontalDirection().getAxis();
        Direction directionVerticalFacing = pContext.getNearestLookingVerticalDirection();

        pContext.getPlayer().sendSystemMessage(Component.literal("axisHorizontalFacing: "+axisHorizontalFacing.toString()));
        pContext.getPlayer().sendSystemMessage(Component.literal("directionVerticalFacing: "+directionVerticalFacing.toString()));

        //This section determines if block aligns with X axis or Z axis
        /*Direction.Axis placementAxis = pContext.getHorizontalDirection().getAxis(); //Gets the cardinal direction when player places new block
        if (placementAxis == Direction.Axis.Y) {
            state.setValue(HAXIS, Direction.Axis.Z); //fallback if placementAxis somehow outputs Axis.Y, default to pointing NS
        } else {
            state.setValue(HAXIS, placementAxis); //if placementAxis is X or Z, that property is established for block
        }*/

        //This section determines X or Z axis alignment
        state = state.setValue(XORZAXIS, axisHorizontalFacing); //axisHorizontalFacing outputs X or Z

        //This section determines if block aligns with X axis or Z axis
        state = state.setValue(UPORDOWN, directionVerticalFacing); //directionVerticalFacing outputs UP or DOWN

        //This section determines block connection state to connect with neighbors
        state = state.setValue(TYPE, getPipeType(
                state,
                getRelativeForward(level, pos, axisHorizontalFacing),
                getRelativeBackward(level, pos, axisHorizontalFacing)
        ));

        return state;
    }

    @Override //THIS TELLS THE NEIGHBORS TO UPDATE
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide) return;

        Direction.Axis xzaxis = state.getValue(XORZAXIS);
        PillarConnectionState type = getPipeType(state, getRelativeForward(level, pos, xzaxis), getRelativeBackward(level, pos, xzaxis));
        if (state.getValue(TYPE) == type) return;

        state = state.setValue(TYPE, type);
        level.setBlock(pos, state, 3);
    }

    //Method to find blockstate of the block in the positive axial direction of selected direction
    //Outputs the block state in the positive axial direction of the selection's block state String. This is to identify the neighbor block.
    public BlockState getRelativeForward(Level level, BlockPos pos, Direction.Axis horizontalAxis) {
        return level.getBlockState(pos.relative(Direction.fromAxisAndDirection(horizontalAxis, Direction.AxisDirection.POSITIVE)));
    }

    //Method to find blockstate of the block in the negative axial direction of selected direction
    //Outputs the block state in the negative axial direction of the selection's block state String. This is to identify the neighbor block.
    public BlockState getRelativeBackward(Level level, BlockPos pos, Direction.Axis axis) {
        return level.getBlockState(pos.relative(Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE)));
    }

    public PillarConnectionState getPipeType(BlockState state, BlockState forward, BlockState backward) {

        boolean blockstate_forward_is_same = forward.is(state.getBlock()) && state.getValue(XORZAXIS) == forward.getValue(XORZAXIS) && state.getValue(UPORDOWN) == forward.getValue(UPORDOWN);

        boolean blockstate_backward_is_same = backward.is(state.getBlock()) && state.getValue(XORZAXIS) == backward.getValue(XORZAXIS) && state.getValue(UPORDOWN) == backward.getValue(UPORDOWN);

        //Where "forward" and "backward" refer to in the positive and negative axial direction respectively, like Y direction (height).
        if (blockstate_forward_is_same && !blockstate_backward_is_same) return PillarConnectionState.NEGATIVE;
        else if (!blockstate_forward_is_same && blockstate_backward_is_same) return PillarConnectionState.POSITIVE;
        else if (blockstate_forward_is_same) return PillarConnectionState.MIDDLE;
        return PillarConnectionState.SOLO;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, XORZAXIS, UPORDOWN); //Type defines connection state, HAXIS defines horizontal orientation. UPORDOWN defines whether block attaches to floor or ceiling
    }
}