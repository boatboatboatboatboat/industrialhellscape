package net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


//INFO:
//-----
//Block can be placed on any surface. If on floor or ceiling, it will face the direction it is placed down.
//If on wall, will face the wall it is touching.

public class SurfaceMountBlock extends HorizontalDirectionalBlock {
    public static final EnumProperty<AttachFace> ATTACH_FACE = BlockStateProperties.ATTACH_FACE;

    public SurfaceMountBlock(Properties pProperties) {
        super(pProperties);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ATTACH_FACE, AttachFace.WALL)
        );
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return null;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction facing = pContext.getHorizontalDirection().getOpposite();
        Direction clickedFaceDirection = pContext.getClickedFace();

        BlockPos blockPos = pContext.getClickedPos();
        BlockState state; //Start with default blockstate

        if (clickedFaceDirection.getAxis() == Direction.Axis.Y) { //On Floor or Ceiling
            state = this.defaultBlockState()
                    .setValue(ATTACH_FACE, clickedFaceDirection == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING).setValue(FACING, facing);
        } else { //On walls
            state = this.defaultBlockState()
                    .setValue(ATTACH_FACE, AttachFace.WALL).setValue(FACING, clickedFaceDirection);
        }

        return state;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, ATTACH_FACE); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}


