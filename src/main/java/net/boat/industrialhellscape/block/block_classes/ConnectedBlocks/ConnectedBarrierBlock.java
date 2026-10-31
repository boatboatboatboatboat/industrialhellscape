package net.boat.industrialhellscape.block.block_classes.ConnectedBlocks;

import net.boat.industrialhellscape.block.block_interfaces.ConnectedModelInterface;
import net.boat.industrialhellscape.block.block_interfaces.HitboxRotationInterface;
import net.boat.industrialhellscape.block.block_state_enums.DynamicConnectionState;
import net.boat.industrialhellscape.block.logic_enums.ConnectingBlockPlacementOrientation;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class ConnectedBarrierBlock extends Block implements ConnectedModelInterface {
    public static final EnumProperty<Direction.Axis> HORIZONTAL_AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public final TagKey<Block> blockSetFamily; //To determine other blocks aside from its own can this block connect to

    public final VoxelShape SOLO_X;
    public final VoxelShape SOLO_Z;

    public final VoxelShape POSITIVE_X;
    public final VoxelShape POSITIVE_Z;

    public final VoxelShape MIDDLE_X;
    public final VoxelShape MIDDLE_Z;

    public final VoxelShape RIGHT_X;
    public final VoxelShape RIGHT_Z;
    
    public ConnectedBarrierBlock(Properties properties, @Nullable TagKey<Block> inputCompatibleBlockSet, VoxelShape soloShape, VoxelShape leftShape, VoxelShape middleShape, VoxelShape rightShape, ConnectingBlockPlacementOrientation placementDirection) {
        super(properties);

        //Define the Voxelshape hitboxes for each state
        SOLO_X = soloShape;
        POSITIVE_X = leftShape;
        MIDDLE_X = middleShape;
        RIGHT_X = rightShape;

        SOLO_Z = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, soloShape);
        POSITIVE_Z = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, leftShape);
        MIDDLE_Z = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, middleShape);
        RIGHT_Z = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, rightShape);

        //To determine other blocks aside from its own can this block connect to
        this.blockSetFamily = inputCompatibleBlockSet;

        //Default state is Solo/unconnected, facing X, no waterlogging
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TYPE, DynamicConnectionState.SOLO)
                .setValue(HORIZONTAL_AXIS, Direction.Axis.X)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public @Nonnull VoxelShape getShape(@NotNull BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        //See this mod's ConnectedModelInterface interface to view the following method.
        return makeHorizontalAxisHitBoxes(
                pState,
                SOLO_X, POSITIVE_X, MIDDLE_X, RIGHT_X,
                SOLO_Z, POSITIVE_Z, MIDDLE_Z, RIGHT_Z,
                HORIZONTAL_AXIS,TYPE);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        //Initialize Default State
        BlockState thisState = this.defaultBlockState();

        //Assign Facing Property
        Direction.Axis placedWhileFacing = context.getHorizontalDirection().getAxis() == Direction.Axis.X? Direction.Axis.Z : Direction.Axis.X;
        thisState = thisState.setValue(HORIZONTAL_AXIS, placedWhileFacing);

        //Useful information
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos(); //is Always air.
        Direction directionToCheck = ConnectedModelInterface.getClosestLookDirectionFromEntity(context.getPlayer(), ConnectingBlockPlacementOrientation.VERTICAL);
        Direction playerFacing = context.getHorizontalDirection();

        //Assign Waterlogged Property
        FluidState fluidstate = level.getFluidState(context.getClickedPos());
        thisState =  thisState.setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        //Find neighbor position and state
        BlockPos neighborPos = clickedPos.relative(directionToCheck);
        BlockState neighborBlockState = level.getBlockState(neighborPos);

        //Assign Type Property based on clicked Block (thisState is reassigned if conditions are met. Else, has the default type property of type:solo)
        if(ConnectedModelInterface.neighborStateCanConnectTo(this,blockSetFamily,neighborBlockState)) {
            if(neighborBlockState.getValue(HORIZONTAL_AXIS) == placedWhileFacing) { //<possible failure point if tagged block does not have matching properties as its members.
                if(ConnectedModelInterface.placingBlockInNegativeRelativeDirection(directionToCheck, playerFacing, ConnectingBlockPlacementOrientation.VERTICAL)) {
                    thisState = thisState.setValue(TYPE, DynamicConnectionState.NEGATIVE);
                } else {
                    thisState = thisState.setValue(TYPE, DynamicConnectionState.POSITIVE);
                }
            }
        }
        return thisState;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        Direction directionToCheck = ConnectedModelInterface.getClosestLookDirectionFromEntity(placer, ConnectingBlockPlacementOrientation.VERTICAL);
        BlockPos neighborPos = pos.relative(directionToCheck);
        BlockState neighborBlockState = level.getBlockState(neighborPos);
        Direction playerFacing = placer.getDirection();

        /*
            Assign Type Property based on clicked Block (thisState is reassigned if conditions are met. Else, has the default type property of type:solo)

            Possible failure point if incorrectly-tagged block does not have matching properties as its members.
            Logic assumes if neighboring block is of a blockset tag, that it has the same block state properties as this class
            Avoid by tagging furniture blocks correctly based on block state properties
         */

        //If neighbor block is from the same blockset as this block
        if(ConnectedModelInterface.neighborStateCanConnectTo(this,blockSetFamily,neighborBlockState)) {
            //And if neighbor is facing the same direction as this block
            if(neighborBlockState.getValue(HORIZONTAL_AXIS) == state.getValue(HORIZONTAL_AXIS)) {

                //Check if the placing entity is placing block left/right or up/down
                boolean playerPlacedBlockInNegativeRelativeDirection = ConnectedModelInterface.placingBlockInNegativeRelativeDirection(directionToCheck, playerFacing, ConnectingBlockPlacementOrientation.VERTICAL);
                boolean playerPlacedBlockInPositiveRelativeDirection = !playerPlacedBlockInNegativeRelativeDirection;

                switch(neighborBlockState.getValue(TYPE)) {
                    case POSITIVE -> {
                        if(playerPlacedBlockInPositiveRelativeDirection) {
                            level.setBlock(neighborPos, neighborBlockState.setValue(TYPE, DynamicConnectionState.MIDDLE),3);
                        }
                    }
                    case NEGATIVE -> {
                        if(playerPlacedBlockInNegativeRelativeDirection) {
                            level.setBlock(neighborPos, neighborBlockState.setValue(TYPE, DynamicConnectionState.MIDDLE),3);
                        }
                    }
                    case SOLO -> {
                        if(playerPlacedBlockInPositiveRelativeDirection) {
                            level.setBlock(neighborPos, neighborBlockState.setValue(TYPE, DynamicConnectionState.NEGATIVE), 3);
                        } else {
                            level.setBlock(neighborPos, neighborBlockState.setValue(TYPE, DynamicConnectionState.POSITIVE), 3);
                        }
                    }
                }
            }
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
        if(newState.getBlock() != this) {
            BlockPos positiveNeighborBlockPos = pos.relative(Direction.UP,1);
            BlockPos negativeNeighborBlockPos = pos.relative(Direction.DOWN,1);

            ConnectedModelInterface.optionalUpdateNeighbor(this,level,null,state,positiveNeighborBlockPos,HORIZONTAL_AXIS,TYPE, true);
            ConnectedModelInterface.optionalUpdateNeighbor(this,level,null,state,negativeNeighborBlockPos,HORIZONTAL_AXIS,TYPE, false);
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    //---------- END OF METHODS HANDLED BY INTERFACE ----------
    @Override
    public @Nonnull RenderShape getRenderShape(@Nonnull BlockState state) {
        return RenderShape.MODEL;
    }
    public @NotNull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    protected @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return switch(rot) {
            case CLOCKWISE_90, COUNTERCLOCKWISE_90 -> state.cycle(HORIZONTAL_AXIS);
            case CLOCKWISE_180, NONE -> state;
        };
    }

    protected @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_AXIS, WATERLOGGED, TYPE); //Block's blockstates; its NSEW orientation, its connection type defined
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.advise_connecting_furniture"));
        } else {
            tooltipComponents.add(Component.translatable("tooltip.industrialhellscape.shift_down"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
