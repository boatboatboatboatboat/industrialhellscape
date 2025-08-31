package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.RelativePlanarDirectionState;
import net.boat.industrialhellscape.block.special_blocks_properties.RotationHelper;
import net.boat.industrialhellscape.item.ModItems;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PipePlanarCornerBlock extends Block {

    public static final EnumProperty<Direction> SURFACE_ATTACHED = BlockStateProperties.FACING;
    public static final EnumProperty<RelativePlanarDirectionState> PLANE_DIRECTION = EnumProperty.create("plane_direction", RelativePlanarDirectionState.class);

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
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch(pState.getValue(SURFACE_ATTACHED)) {
            //6 Cases for collision box shape
            case UP: return SHAPE_CEILING;
            case NORTH: return SHAPE_NORTH;
            case SOUTH: return SHAPE_SOUTH;
            case EAST: return SHAPE_EAST;
            case WEST: return SHAPE_WEST;
            default: return SHAPE_FLOOR;
        }
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = this.defaultBlockState();
        Direction directionClicked = pContext.getClickedFace(); //Are you clicking the floor, ceiling, north wall, south wall, east wall, west wall?

        //This section determines surface alignment based on where you click to place.
        state = state.setValue(SURFACE_ATTACHED, directionClicked.getOpposite());

        //This section determines block rotation on the surface
        //Currently defaults to facing down/south on the plane
        return state.setValue(PLANE_DIRECTION, RelativePlanarDirectionState.DOWN);
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        boolean playerHasTool = pPlayer.getMainHandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS) || pPlayer.getOffhandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS);

        if( playerHasTool ) { //If player has tool, change the block state property
            pState = pState.cycle(PLANE_DIRECTION);
            pLevel.setBlock(pPos, pState, 2);

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PLANE_DIRECTION, SURFACE_ATTACHED); //Type defines connection state, HAXIS defines horizontal orientation. UPORDOWN defines whether block attaches to floor or ceiling
    }
}