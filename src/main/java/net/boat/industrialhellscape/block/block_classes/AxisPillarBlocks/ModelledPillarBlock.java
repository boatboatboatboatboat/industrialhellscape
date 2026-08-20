package net.boat.industrialhellscape.block.block_classes.AxisPillarBlocks;

import net.boat.industrialhellscape.block.block_interfaces.HitboxRotationInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class ModelledPillarBlock extends RotatedPillarBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final VoxelShape X_HITBOX;
    private final VoxelShape Y_HITBOX;
    private final VoxelShape Z_HITBOX;
    public ModelledPillarBlock(Properties properties, VoxelShape verticalHitbox) {
        super(properties);

        this.Y_HITBOX = verticalHitbox;
        Z_HITBOX = HitboxRotationInterface.rotateVoxelXAxisIntTimes(1, Y_HITBOX);
        X_HITBOX = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, Z_HITBOX);
    }

    @Override
    public @NotNull BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(AXIS, context.getClickedFace().getAxis()).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
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
        pBuilder.add(AXIS, WATERLOGGED);
    }
}
