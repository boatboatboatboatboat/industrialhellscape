package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.RelativePlanarDirectionState;
import net.boat.industrialhellscape.block.special_blocks_properties.RotationHelper;
import net.boat.industrialhellscape.util.ModTags;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class PipePlanarCornerBlock extends Block implements SimpleWaterloggedBlock {

    public static final EnumProperty<Direction> SURFACE_ATTACHED = BlockStateProperties.FACING;
    public static final EnumProperty<RelativePlanarDirectionState> PLANE_DIRECTION = EnumProperty.create("plane_direction", RelativePlanarDirectionState.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape SHAPE_FLOOR = Block.box(0, 0, 0, 16, 6, 16);
    public static final VoxelShape SHAPE_CEILING = Block.box(0, 10, 0, 16, 16, 16);

    public static final VoxelShape SHAPE_NORTH = Block.box(0, 0, 0, 16, 16, 6);
    public static final VoxelShape SHAPE_SOUTH = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, SHAPE_NORTH);
    public static final VoxelShape SHAPE_EAST = RotationHelper.rotateVoxelHorizontal(Direction.EAST, SHAPE_NORTH);
    public static final VoxelShape SHAPE_WEST = RotationHelper.rotateVoxelHorizontal(Direction.WEST, SHAPE_NORTH);

    public PipePlanarCornerBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(SURFACE_ATTACHED, Direction.NORTH)
                .setValue(PLANE_DIRECTION, RelativePlanarDirectionState.UP)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public @Nonnull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        return switch (pState.getValue(SURFACE_ATTACHED)) {
            //6 Cases for collision box shape
            case UP -> SHAPE_CEILING;
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_FLOOR;
        };
    }
    @Override
    public @Nonnull RenderShape getRenderShape(@Nonnull BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = this.defaultBlockState();
        Direction directionClicked = pContext.getClickedFace(); //Are you clicking the floor, ceiling, north wall, south wall, east wall, west wall?
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());

        //This section determines surface alignment based on where you click to place.
        state = state.setValue(SURFACE_ATTACHED, directionClicked.getOpposite());

        //This section determines waterlogging.
        state =  state.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        //This section determines block rotation on the surface
        //Currently defaults to facing down/south on the plane
        return state.setValue(PLANE_DIRECTION, RelativePlanarDirectionState.DOWN);
    }

    public @Nonnull InteractionResult use(@Nonnull BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {

        boolean playerHasTool = pPlayer.getMainHandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS) || pPlayer.getOffhandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS);

        if( playerHasTool ) { //If player has tool, change the block state property
            pState = pState.cycle(PLANE_DIRECTION);
            pLevel.setBlock(pPos, pState, 2);

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SURFACE_ATTACHED, PLANE_DIRECTION, WATERLOGGED); //Type defines connection state, HAXIS defines horizontal orientation. UPORDOWN defines whether block attaches to floor or ceiling
    }
}