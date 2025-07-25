package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.PillarConnectionState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable; //This is what Hearth and Home uses instead of the default

public class AxialPillarBlock extends Block {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS; //"AXIS" is used to store the block state direction
    public static final EnumProperty<PillarConnectionState> TYPE = EnumProperty.create("type", PillarConnectionState.class); //"TYPE" is used to store enum value of "solo, pos, neg, middle"

    public AxialPillarBlock(Properties pProperties) { //Establishes the Default State - Vertical, unconnected (solo)
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(TYPE, PillarConnectionState.SOLO)
                .setValue(AXIS, Direction.Axis.Z));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction.Axis axis = context.getClickedFace().getAxis();

        BlockState state = this.defaultBlockState().setValue(AXIS, axis); //Determines and sets X/Y/Z direction block shall face when placed
        state = state.setValue(TYPE, getType(state, getRelativeTop(level, pos, axis), getRelativeBottom(level, pos, axis))); //Determines and sets block type based on neighbor connection (top, middle, buttom, solo unconnected)
        return state;
    }

    @Override //THIS TELLS THE NEIGHBORS TO UPDATE
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide) return;

        Direction.Axis axis = state.getValue(AXIS);
        PillarConnectionState type = getType(state, getRelativeTop(level, pos, axis), getRelativeBottom(level, pos, axis));
        if (state.getValue(TYPE) == type) return;

        state = state.setValue(TYPE, type);
        level.setBlock(pos, state, 3);
    }
    //My convention: "TOP" refers to any positive axis direction (XYZ), like positive Y, or up. Vice Versa for "BOTTOM"

    //Method to find blockstate of the block in the positive axial direction of selected direction
    //Outputs the block state in the positive axial direction of the selection's block state String. This is to identify the neighbor block.
    public BlockState getRelativeTop(Level level, BlockPos pos, Direction.Axis axis) {
        return level.getBlockState(pos.relative(Direction.fromAxisAndDirection(axis, Direction.AxisDirection.POSITIVE)));
    }

    //Method to find blockstate of the block in the negative axial direction of selected direction
    //Outputs the block state in the negative axial direction of the selection's block state String. This is to identify the neighbor block.
    public BlockState getRelativeBottom(Level level, BlockPos pos, Direction.Axis axis) {
        return level.getBlockState(pos.relative(Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE)));
    }

    //Method to determine placed block's blockstate in relation to adjacent blocks identified by getRelativeTop() and getRelativeBottom()
    //Outputs a value of the PillarConnectionState enum.
    public PillarConnectionState getType(BlockState state, BlockState above, BlockState below) {
        boolean blockstate_above_is_same = above.is(state.getBlock()) && state.getValue(AXIS) == above.getValue(AXIS);
        boolean blockstate_below_is_same = below.is(state.getBlock()) && state.getValue(AXIS) == below.getValue(AXIS);

        //Where "above" and "below" refer to in the positive and negative axial direction respectively, like Y direction (height).
        if (blockstate_above_is_same && !blockstate_below_is_same) return PillarConnectionState.NEGATIVE;
        else if (!blockstate_above_is_same && blockstate_below_is_same) return PillarConnectionState.POSITIVE;
        else if (blockstate_above_is_same) return PillarConnectionState.MIDDLE;
        return PillarConnectionState.SOLO;
    }
    @Override //IT WILL NOT COMPILE IF THESE LINES ARE REMOVED
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, AXIS);
    }
}