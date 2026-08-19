package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.Integer2Blocks;

import net.boat.industrialhellscape.block.modded_interfaces.MultiBlockPlacementInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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

//INFO:
//-----
//This block supports cardinal directional placement, and an inventory with GUI. The inventory size is determined upon block registration.
//Block places two blocks total within the world when placed down; First a "Negative" half, then a "Positive" half.
//The direction these are placed is set by parameter "multiBlockPlacementDirection". The second half of the structure can be placed vertically, horizontally, or forward to the first half.
//Supports a custom hitbox for custom model, passed during registration.

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
        Level level = pContext.getLevel();
        BlockPos originPos = pContext.getClickedPos();
        level.getBlockState(originPos);
        BlockState pState;
        Direction facing = pContext.getHorizontalDirection().getOpposite();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        pState = this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED,fluidstate.getType() == Fluids.WATER); //set default Block State for this block, THEN assign waterlogged state (so it doesnt try to do that for air)

        return MultiBlockPlacementInterface.placeOriginBlock(level, originPos, pState, FACING, facing, multiBlockPlacementMatrix);
    }

    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, PART, BlockStateProperties.WATERLOGGED); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}
