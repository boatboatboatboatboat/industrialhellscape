package net.boat.industrialhellscape.block.block_classes.MultiBlocks.Integer2Blocks;

import net.boat.industrialhellscape.block.block_interfaces.MultiBlockPlacementInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;


/*
    INFO:
    - Multiblock consisting of part #0 and part #1 blockStates
    - WaterLoggable block
 */

public class Modelled2Block extends Integer2Block implements SimpleWaterloggedBlock {

    public final VoxelShape[] hitboxShapeArray;

    public Modelled2Block(BlockBehaviour.Properties pProperties, int[][] multiBlockPlacementMatrix, VoxelShape[] hitboxShapeArray) {
        super(pProperties, multiBlockPlacementMatrix);

        this.hitboxShapeArray = hitboxShapeArray;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, 0)
                .setValue(FACING,Direction.NORTH)
                .setValue(BlockStateProperties.WATERLOGGED,false)
        );
    }

    @Override
    public @Nonnull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        return MultiBlockPlacementInterface.setMultiBlockHitBox(pState, FACING, PART, hitboxShapeArray);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return MultiBlockPlacementInterface.placeModelledOriginBlock(pContext, this,FACING,BlockStateProperties.WATERLOGGED,true);
    }

    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, PART, BlockStateProperties.WATERLOGGED); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}
