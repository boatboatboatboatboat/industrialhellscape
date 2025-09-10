package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.InnerCornerConnectionState;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

//INFO:
//-----
// This block, when placed, will align top/bottom. It is meant for "corner" or "right-angle" shaped blocks whose models will be touching two perpendicular full-block surfaces of adjacent blocks.
// When placed, depending roughly on direction, it will align to the "wall and ceiling" or "wall and floor" surfaces.
// When placed while player is crouching, will default to "SIDE" alignment (wall to perpendicular wall).
// Using an appropriate tool and interacting with the block, it will rotate, cycling through the cardinal directions it is facing.
// Using an appropriate tool while crouching, it will cycle through the TYPE_CORNER variants
//      UP - ceiling/wall touching variant
//      SIDE - wall/wall touching variant
//      DOWN - floor/wall touching variant

// Tools that can rotate the block are ones tagged by this mod with the item tag IH_COMPATIBLE_TOOLS. Currently consisting of tagged pickaxes, "wrenches", and the mod's HAVEN Tool

public class CornerBlock extends Block implements SimpleWaterloggedBlock {

    public static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING; //"FACING" is used to store DirectionProperty value of "north, south, east, west" //KJ
    public static final EnumProperty<InnerCornerConnectionState> TYPE_CORNER = EnumProperty.create("type", InnerCornerConnectionState.class); //"UP", "SIDE", or "DOWN" enum values
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // 4 * 3 blockstates and therefore hitbox models based on above properties
    private VoxelShape INNER_CORNER_UP_N;
    private VoxelShape INNER_CORNER_UP_S;
    private VoxelShape INNER_CORNER_UP_E;
    private VoxelShape INNER_CORNER_UP_W;

    private VoxelShape INNER_CORNER_DOWN_N;
    private VoxelShape INNER_CORNER_DOWN_S;
    private VoxelShape INNER_CORNER_DOWN_E;
    private VoxelShape INNER_CORNER_DOWN_W;

    private VoxelShape INNER_CORNER_SIDE_N;
    private VoxelShape INNER_CORNER_SIDE_S;
    private VoxelShape INNER_CORNER_SIDE_E;
    private VoxelShape INNER_CORNER_SIDE_W;

    public CornerBlock(Properties pProperties, VoxelShape upShape, VoxelShape downShape, VoxelShape sideShape) {
        super(pProperties);

        INNER_CORNER_UP_N = upShape;
        INNER_CORNER_UP_S = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, upShape);
        INNER_CORNER_UP_E = RotationHelper.rotateVoxelHorizontal(Direction.EAST, upShape);
        INNER_CORNER_UP_W = RotationHelper.rotateVoxelHorizontal(Direction.WEST, upShape);

        INNER_CORNER_DOWN_N = downShape;
        INNER_CORNER_DOWN_S = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, downShape);
        INNER_CORNER_DOWN_E = RotationHelper.rotateVoxelHorizontal(Direction.EAST, downShape);
        INNER_CORNER_DOWN_W = RotationHelper.rotateVoxelHorizontal(Direction.WEST, downShape);

        INNER_CORNER_SIDE_N = sideShape;
        INNER_CORNER_SIDE_S = RotationHelper.rotateVoxelHorizontal(Direction.SOUTH, sideShape);
        INNER_CORNER_SIDE_E = RotationHelper.rotateVoxelHorizontal(Direction.EAST, sideShape);
        INNER_CORNER_SIDE_W = RotationHelper.rotateVoxelHorizontal(Direction.WEST, sideShape);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(TYPE_CORNER, InnerCornerConnectionState.UP)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {

        Direction facingForShape = pState.getValue(FACING);

        switch(pState.getValue(TYPE_CORNER)){
            case DOWN:
                switch(facingForShape) {
                    case NORTH: return INNER_CORNER_DOWN_N;
                    case SOUTH: return INNER_CORNER_DOWN_S;
                    case EAST: return INNER_CORNER_DOWN_E;
                    case WEST: return INNER_CORNER_DOWN_W;
                }
            case UP:
                switch(facingForShape) {
                    case NORTH: return INNER_CORNER_UP_N;
                    case SOUTH: return INNER_CORNER_UP_S;
                    case EAST: return INNER_CORNER_UP_E;
                    case WEST: return INNER_CORNER_UP_W;
                }
            case SIDE:
                switch(facingForShape) {
                    case NORTH: return INNER_CORNER_SIDE_N;
                    case SOUTH: return INNER_CORNER_SIDE_S;
                    case EAST: return INNER_CORNER_SIDE_E;
                    case WEST: return INNER_CORNER_SIDE_W;
                }
            default:
                VoxelShape defaultShape = INNER_CORNER_UP_N;
                return defaultShape;
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    //Placement Faces the player
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction directionFacing = pContext.getPlayer().getDirection(); //Gets the cardinal direction when player places new block
        Direction directionClicked = pContext.getClickedFace().getOpposite();
        Boolean PlayerisCrouching = pContext.getPlayer().isCrouching();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());

        //This section defines the facing direction property of the block
        BlockState state = this.defaultBlockState().setValue(FACING, directionFacing);

        //This section determines waterlogging
        state =  state.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));

        //This section defines the orientation "type" of the block (whether the block is on its side, or up or down).
        switch(directionClicked) { //Which block face in the world is the player clicking on to place this block?
            case UP: state = state.setValue(TYPE_CORNER, InnerCornerConnectionState.UP); break; //Bracket faces up if player clicks the ceiling
            case DOWN: state = state.setValue(TYPE_CORNER, InnerCornerConnectionState.DOWN); break; //Bracket faces down if player clicks the floor
            default: //If player is not clicking the floor or ceiling
                if(PlayerisCrouching) { //If the player is crouching
                    state = state.setValue(TYPE_CORNER, InnerCornerConnectionState.SIDE); break; //Set bracket to side
                } else {
                    state = state.setValue(TYPE_CORNER, InnerCornerConnectionState.UP); //If player is not crouching, default to UP orientation
                }
        }
        return state;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        boolean playerHasTool = pPlayer.getMainHandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS) || pPlayer.getOffhandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS);
        boolean PlayerisCrouching = pPlayer.isCrouching();

        if(playerHasTool && !PlayerisCrouching) {
            pState = pState.setValue(FACING, pState.getValue(FACING).getClockWise()); //Cycles horizontal orientation of the block
            pLevel.setBlock(pPos, pState, 2);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);


        } else if (playerHasTool && PlayerisCrouching) {
            pState = pState.cycle(TYPE_CORNER);
            pLevel.setBlock(pPos, pState, 2);

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }

        return InteractionResult.PASS;
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, TYPE_CORNER, WATERLOGGED);
    }
}
