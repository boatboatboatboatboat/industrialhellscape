package net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks;

import net.boat.industrialhellscape.block.modded_interfaces.RotationHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SurfaceMountBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<AttachFace> SURFACE_MOUNT = BlockStateProperties.ATTACH_FACE;

    public SurfaceMountBlock(Properties pProperties) {
        super(pProperties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(SURFACE_MOUNT, AttachFace.WALL)
                .setValue(WATERLOGGED, false)
        );
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction facing = pContext.getHorizontalDirection().getOpposite();

        for(Direction direction : pContext.getNearestLookingDirections()) {
            BlockState state;
            if (direction.getAxis() == Direction.Axis.Y) { //On Floor or Ceiling
                state = this.defaultBlockState()
                        .setValue(SURFACE_MOUNT, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, facing)
                        .setValue(WATERLOGGED,fluidstate.getType() == Fluids.WATER);
            } else { //On walls
                state = this.defaultBlockState()
                        .setValue(SURFACE_MOUNT, AttachFace.WALL).setValue(FACING, facing)
                        .setValue(WATERLOGGED,fluidstate.getType() == Fluids.WATER);
            }
            return state;
        }
        return null;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, SURFACE_MOUNT); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}


