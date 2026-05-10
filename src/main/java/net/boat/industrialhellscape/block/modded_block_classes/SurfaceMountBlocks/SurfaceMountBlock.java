package net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


//INFO:
//-----
//Block can be placed on any surface. If on floor or ceiling, it will face the direction it is placed down.
//If on wall, will face the wall it is touching.

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

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction facing = pContext.getHorizontalDirection().getOpposite();

        BlockPos blockPos = pContext.getClickedPos();
        BlockState state; //Start with default blockstate

        BlockPos neighborPos = blockPos.relative(pContext.getHorizontalDirection());
        BlockState neighborBlockState = pContext.getLevel().getBlockState(neighborPos);

        Direction direction = pContext.getClickedFace();

        if (direction.getAxis() == Direction.Axis.Y) { //On Floor or Ceiling
            state = this.defaultBlockState()
                    .setValue(SURFACE_MOUNT, direction == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING).setValue(FACING, facing)
                    .setValue(WATERLOGGED,fluidstate.getType() == Fluids.WATER);
        } else { //On walls
            state = this.defaultBlockState()
                    .setValue(SURFACE_MOUNT, AttachFace.WALL).setValue(FACING, facing)
                    .setValue(WATERLOGGED,fluidstate.getType() == Fluids.WATER);
        }

        //If block highlighted at cursor is an instance of this block, OVERRIDE everything and match its blockstate.
        if(neighborBlockState.is(this) ) {
            state = neighborBlockState;
        }

        return state;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED, SURFACE_MOUNT); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}


