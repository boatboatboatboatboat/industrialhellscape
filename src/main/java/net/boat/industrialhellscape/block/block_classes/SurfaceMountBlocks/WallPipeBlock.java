package net.boat.industrialhellscape.block.block_classes.SurfaceMountBlocks;

import net.boat.industrialhellscape.block.block_interfaces.ConnectedModelInterface;
import net.boat.industrialhellscape.block.block_interfaces.HitboxRotationInterface;
import net.boat.industrialhellscape.block.block_interfaces.ToolUseInterface;
import net.boat.industrialhellscape.block.block_state_enums.DynamicConnectionState;
import net.boat.industrialhellscape.block.block_state_enums.SurfacePipeMountState;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
import java.util.List;

//INFO:
//-----
//Can be placed on any surface (FACING). Additionally, can be rotated orthogonally on that surface (ORIENTATION).

public class WallPipeBlock extends Block implements SimpleWaterloggedBlock, ToolUseInterface, ConnectedModelInterface {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public static final EnumProperty<SurfacePipeMountState> ORIENTATION = EnumProperty.create("axis", SurfacePipeMountState.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape SHAPE_FLOOR = Block.box(0, 0.1, 0, 16, 6, 16);
    public static final VoxelShape SHAPE_CEILING = Block.box(0, 10, 0, 16, 15.9, 16);

    public static final VoxelShape SHAPE_NORTH = Block.box(0, 0, 0.1, 16, 16, 6);
    public static final VoxelShape SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, SHAPE_NORTH);
    public static final VoxelShape SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, SHAPE_NORTH);
    public static final VoxelShape SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, SHAPE_NORTH);

    public WallPipeBlock(Properties pProperties) {
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
        return ConnectedModelInterface.wallPipeStateForPlacement(this,pContext,FACING, ORIENTATION);
    }

    @Override
    protected void onPlace(BlockState newState, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if(newState.getBlock() != this) {
            ConnectedModelInterface.updateWallPipeNeighbors(this,newState,level,pos,newState,FACING, ORIENTATION);
        }
        super.onPlace(newState, level, pos, oldState, movedByPiston);
    }

    @Override
    protected void onRemove(BlockState priorState, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(newState.getBlock() != this) {
            ConnectedModelInterface.updateWallPipeNeighbors(this,priorState,level,pos,newState,FACING, ORIENTATION);
        }
        super.onRemove(priorState, level, pos, newState, movedByPiston);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState newState, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        return ToolUseInterface.crouchToolUse(stack, newState, level, pos, player, TYPE, 2, ORIENTATION, 3);
    }

    @Override
    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
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

    @Override
    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.pipe_conduit"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.shift_down"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ORIENTATION, TYPE, WATERLOGGED); //Type defines connection state, Surface Direction defines which surface (up, down, cardinal) the block is placed on. Planar Axis is used to define pipe direction lengthwise.
    }
}