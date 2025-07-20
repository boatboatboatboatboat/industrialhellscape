package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


public class HandrailStandaloneBlock extends Block implements SimpleWaterloggedBlock {
    private static final VoxelShape SHAPE= Block.box(0.1, 0.1, 0.1, 15.9, 12, 15.9); //Hitbox
    //Return rotated shape for all directions.

    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty NORTH_FENCE = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH_FENCE = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST_FENCE  = BlockStateProperties.EAST;
    public static final BooleanProperty WEST_FENCE  = BlockStateProperties.WEST;

    public static boolean FENCE_AT_DIRECTION;

    public HandrailStandaloneBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH_FENCE, true)
                .setValue(SOUTH_FENCE, false)
                .setValue(EAST_FENCE, false)
                .setValue(WEST_FENCE, false));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }



    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        //The Tool will be used to change block state properties of the handrail block.


        boolean hasHAVENToolInHand = pPlayer.getMainHandItem().is(ModItems.INHELL_HAVEN_DEVICE.get()) || pPlayer.getMainHandItem().is(ModItems.MALEVOLENT_MULTITOOL.get());
            Direction direction = pPlayer.getDirection();

        if(hasHAVENToolInHand == true) {
            switch (direction) {
                case NORTH:
                    FENCE_AT_DIRECTION = pState.getValue(NORTH_FENCE);
                    pState = pState.setValue(NORTH_FENCE, !FENCE_AT_DIRECTION);
                case SOUTH:
                    FENCE_AT_DIRECTION = pState.getValue(SOUTH_FENCE);
                    pState = pState.setValue(SOUTH_FENCE, !FENCE_AT_DIRECTION);
                case EAST:
                    FENCE_AT_DIRECTION = pState.getValue(EAST_FENCE);
                    pState = pState.setValue(EAST_FENCE, !FENCE_AT_DIRECTION);
                case WEST:
                    FENCE_AT_DIRECTION = pState.getValue(WEST_FENCE);
                    pState = pState.setValue(WEST_FENCE, !FENCE_AT_DIRECTION);
            }
            pLevel.setBlock(pPos, pState, 2);

            return InteractionResult.sidedSuccess(pLevel.isClientSide); //If the player has an appropriate tool, invert the direction property of the blockstate at the facing direction
        }

        return InteractionResult.FAIL; //if player does not have an appropriate tool in hand, return failed interaction
    }

    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            if (pState.getValue(WATERLOGGED)) {
                pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
            }
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = this.defaultBlockState();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction direction = pContext.getHorizontalDirection();
        switch (direction) {
            case NORTH: blockstate = blockstate.setValue(NORTH_FENCE, true);
            case SOUTH: blockstate = blockstate.setValue(SOUTH_FENCE, true);
            case EAST: blockstate = blockstate.setValue(EAST_FENCE, true);
            case WEST: blockstate = blockstate.setValue(WEST_FENCE, true);
        }
        return blockstate.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH_FENCE, SOUTH_FENCE, EAST_FENCE, WEST_FENCE, WATERLOGGED);
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
}
