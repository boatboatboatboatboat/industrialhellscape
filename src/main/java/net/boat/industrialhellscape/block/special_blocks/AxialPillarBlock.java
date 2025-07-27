package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.ConnectedModelCapability;
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
import org.jetbrains.annotations.Nullable;

public class AxialPillarBlock extends Block implements ConnectedModelCapability {
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

        BlockState state = this.defaultBlockState().setValue(AXIS, axis); //Determines and sets X/Y/Z direction block shall align to when placed
        state = state.setValue(TYPE, getType(state, getStateAxisPositive(level, pos, axis), getStateAxisNegative(level, pos, axis)));
            //Determines and sets block type based on neighbor connection (top, middle, buttom, solo unconnected)
            //See the interface ConnectedModelCapability for details on how neighboring blocks are read using
            //getStateAxisPositive() and getStateAxisNegative()
        return state;
    }

    @Override //THIS TELLS THE NEIGHBORS TO UPDATE
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide) return;

        Direction.Axis axis = state.getValue(AXIS);
        PillarConnectionState type = getType(state, getStateAxisPositive(level, pos, axis), getStateAxisNegative(level, pos, axis));
            //See the interface ConnectedModelCapability for details on how neighboring blocks are read using
            //getStateAxisPositive() and getStateAxisNegative()
        if (state.getValue(TYPE) == type) return;

        state = state.setValue(TYPE, type);
        level.setBlock(pos, state, 3);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, AXIS);
    }
}