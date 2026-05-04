package net.boat.industrialhellscape.block.modded_block_classes.ConnectedBlocks;

import net.boat.industrialhellscape.block.modded_block_state_properties.DynamicConnectionState;
import net.boat.industrialhellscape.block.modded_interfaces.ConnectedModelCapability;
import net.boat.industrialhellscape.block.modded_interfaces.ToolUseCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

//INFO:
//-----
// This block, when placed, aligns with the axis of placement (x, y, z). Subsequent blocks placed adjacent in the same alignment will cause block state updates to allow directional connected textures without CTM.
// This is necessary because CTM does not support directional texture connection.
// The operating methods for block state detection and updating are present in this mod's ConnectedModelCapability interface.

// Block-state notation:
//     Solo - Unconnected block-state. When placed for the first time by itself with no eligible adjacent connections.
//     Pos - an "end" connection facing the positive axis (East, Up, South).
//     Middle - an interior connection that may repeat based on the length of the pillar.
//     Neg - "an end" connection facing the negative axis direction (West, Down, North).

// Block class is adapted from Hearth and Home mod's Stone Pillar block class code.

public class AxialPillarBlock extends RotatedPillarBlock implements ConnectedModelCapability {
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS; //"AXIS" is used to store the block state direction
    public static final EnumProperty<DynamicConnectionState> TYPE = EnumProperty.create("type", DynamicConnectionState.class); //Custom enum. "TYPE" is used to store enum value of "solo, pos, neg, middle"

    public AxialPillarBlock(Properties pProperties) { //Establishes the Default State - Vertical, unconnected (solo)
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(TYPE, DynamicConnectionState.SOLO)
                .setValue(AXIS, Direction.Axis.Z));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction.Axis axis = context.getClickedFace().getAxis(); //Turns the direction clicked into the axis the direction is aligned to (East/West = X axis, etc.)

        BlockState state = this.defaultBlockState().setValue(AXIS, axis); //Sets X/Y/Z direction block shall align to when placed
        state = state.setValue(TYPE, ConnectedModelCapability.getPillarType(state, ConnectedModelCapability.getStateAtAxisPositive(level, pos, axis), ConnectedModelCapability.getStateAtAxisNegative(level, pos, axis)));
            //Determines and sets block type based on neighbor connection (top, middle, bottom, solo unconnected)
            //See the interface ConnectedModelCapability for details on how neighboring blocks are read using interface methods
            //getStateAxisPositive() and getStateAxisNegative()
        return state;
    }

    @Override //THIS TELLS THE NEIGHBORS TO UPDATE
    public void neighborChanged(@Nonnull BlockState state, Level level, @Nonnull BlockPos pos, @Nonnull Block block, @Nonnull BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide) return;

        Direction.Axis axis = state.getValue(AXIS);
        DynamicConnectionState type = ConnectedModelCapability.getPillarType(state, ConnectedModelCapability.getStateAtAxisPositive(level, pos, axis), ConnectedModelCapability.getStateAtAxisNegative(level, pos, axis));
            //See the interface ConnectedModelCapability for details on how neighboring blocks are read using
            //getStateAxisPositive() and getStateAxisNegative()
        if (state.getValue(TYPE) == type) return;

        state = state.setValue(TYPE, type);
        level.setBlock(pos, state, 3);
    }

    @NotNull
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        return ToolUseCapability.SimpleToolInteract(TYPE,player, state, level, pos);
    }

//    public @NotNull BlockState rotate(BlockState pState, @NotNull Rotation pRot) {
//        if (pState.getValue(AXIS) != Direction.Axis.Y) { //Block is NOT placed up or down
//            Direction.Axis originallyFacing = pState.getValue(AXIS);
//            Direction.Axis clockwise90Facing;
//            //Direction.Axis clockwise180Facing;
//            Direction.Axis counterClockwise90Facing;
//            switch(originallyFacing) {
//                case X:
//                    clockwise90Facing = Direction.Axis.Z;
//                    counterClockwise90Facing = Direction.Axis.Z;
//                    break;
//                default:
//                    clockwise90Facing = Direction.Axis.X;
//                    counterClockwise90Facing = Direction.Axis.X;
//                    break;
//            }
//
//
//            switch (pRot){
//                case CLOCKWISE_90 -> pState = pState.setValue(AXIS, clockwise90Facing);
//                case CLOCKWISE_180 -> pState = pState.setValue(AXIS, originallyFacing);
//                case COUNTERCLOCKWISE_90 -> pState = pState.setValue(AXIS, counterClockwise90Facing);
//                default -> {} //Assumed to be case "NONE", therefore block is unchanged
//            }
//            return pState;
//
//        } else { //block is not placed up or down (it is in the cardinal directions)
//            return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
//        }
//    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, AXIS);
    }
}