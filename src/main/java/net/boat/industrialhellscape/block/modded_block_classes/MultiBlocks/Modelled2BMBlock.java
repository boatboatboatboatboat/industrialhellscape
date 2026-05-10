package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.boat.industrialhellscape.block.modded_interfaces.HitboxRotationInterface;
import net.boat.industrialhellscape.block.modded_interfaces.MultiBlockPlacementInterface;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
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

public class Modelled2BMBlock extends TwoBlockMultiBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final VoxelShape POSITIVE_SHAPE_NORTH;
    private final VoxelShape POSITIVE_SHAPE_SOUTH;
    private final VoxelShape POSITIVE_SHAPE_EAST;
    private final VoxelShape POSITIVE_SHAPE_WEST;

    private final VoxelShape NEGATIVE_SHAPE_NORTH;
    private final VoxelShape NEGATIVE_SHAPE_SOUTH;
    private final VoxelShape NEGATIVE_SHAPE_EAST;
    private final VoxelShape NEGATIVE_SHAPE_WEST;

    private final MultiBlockPlacementDirection multiBlockPlacementDirection;

    public Modelled2BMBlock(Properties pProperties, MultiBlockPlacementDirection multiBlockPlacementDirection, VoxelShape hitboxPositiveShape, VoxelShape hitboxNegativeShape) {
        super(pProperties, multiBlockPlacementDirection);

        this.multiBlockPlacementDirection = multiBlockPlacementDirection;

        POSITIVE_SHAPE_NORTH = hitboxPositiveShape;
        POSITIVE_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, hitboxPositiveShape);
        POSITIVE_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, hitboxPositiveShape);
        POSITIVE_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, hitboxPositiveShape);

        NEGATIVE_SHAPE_NORTH = hitboxNegativeShape;
        NEGATIVE_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, hitboxNegativeShape);
        NEGATIVE_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, hitboxNegativeShape);
        NEGATIVE_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, hitboxNegativeShape);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HALF_PART, TwoBlockMultiBlockState.NEGATIVE)
                .setValue(FACING,Direction.NORTH)
                .setValue(WATERLOGGED,false)
        );
    }

    @Override
    public @Nonnull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        Direction facing = pState.getValue(FACING);

        if (pState.getValue(HALF_PART) == TwoBlockMultiBlockState.POSITIVE) {
            return switch (facing) {
                case SOUTH -> POSITIVE_SHAPE_SOUTH;
                case EAST -> POSITIVE_SHAPE_EAST;
                case WEST -> POSITIVE_SHAPE_WEST;
                default -> POSITIVE_SHAPE_NORTH;
            };
        } else {
            return switch (facing) {
                case SOUTH -> NEGATIVE_SHAPE_SOUTH;
                case EAST -> NEGATIVE_SHAPE_EAST;
                case WEST -> NEGATIVE_SHAPE_WEST;
                default -> NEGATIVE_SHAPE_NORTH;
            };
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction facing = pContext.getHorizontalDirection().getOpposite(); //Which direction is the block placed?

        if(multiBlockPlacementDirection == MultiBlockPlacementDirection.FORWARD) {
            facing = pContext.getHorizontalDirection(); //To match vanilla conventions for bed-like multiblock placement
        }

        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        BlockPos otherBlockPos = MultiBlockPlacementInterface.posToPlaceOtherHalf(pContext.getClickedPos(), TwoBlockMultiBlockState.NEGATIVE, facing, multiBlockPlacementDirection);

        Level level = pContext.getLevel();
        if (level.getBlockState(otherBlockPos).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(otherBlockPos)) {
            BlockState state = defaultBlockState().setValue(WATERLOGGED,fluidstate.getType() == Fluids.WATER); //Set waterlogged status
            return state.setValue(FACING, facing); //set placement direction value
        } else {
            return null;
        }
    }

    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HALF_PART, WATERLOGGED); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}
