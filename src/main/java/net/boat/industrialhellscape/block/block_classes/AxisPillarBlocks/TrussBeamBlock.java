package net.boat.industrialhellscape.block.block_classes.AxisPillarBlocks;

import net.boat.industrialhellscape.block.block_interfaces.CustomModEnumsInterface;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

//Uses Axes for state determination

public class TrussBeamBlock extends RotatedPillarBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty ALT_STATE = CustomModEnumsInterface.ALT_STATE;

    private final VoxelShape X_HITBOX;
    private final VoxelShape Y_HITBOX;
    private final VoxelShape Z_HITBOX;
    public TrussBeamBlock(Properties properties, VoxelShape verticalHitbox) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(AXIS, Direction.Axis.Y)
                .setValue(ALT_STATE, false)
                .setValue(WATERLOGGED, false)
        );

        this.Y_HITBOX = verticalHitbox;
        Z_HITBOX = HitboxRotationInterface.rotateVoxelXAxisIntTimes(1, Y_HITBOX);
        X_HITBOX = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, Z_HITBOX);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {

        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos(); //is Always air.
        Direction directionClickedFace = context.getClickedFace();
        Direction directionTowardsNeighborPos = directionClickedFace.getOpposite();
        BlockPos neighborPos = clickedPos.relative(directionTowardsNeighborPos);
        BlockState neighborBlockState = level.getBlockState(neighborPos);

        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockState state = this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        if(neighborBlockState.is(this)) {
            if(neighborBlockState.getValue(AXIS) == state.getValue(AXIS)) {
                state = state.setValue(ALT_STATE, !neighborBlockState.getValue(ALT_STATE)); //boolean toggle
            }
        }

        return state;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return ToolUseInterface.simpleToolUse(stack,state,level,pos,player,ALT_STATE,3);
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return switch (state.getValue(AXIS)) {
            case X -> X_HITBOX;
            case Y -> Y_HITBOX;
            case Z -> Z_HITBOX;
        };
    }

    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AXIS, WATERLOGGED, ALT_STATE);
    }
}
