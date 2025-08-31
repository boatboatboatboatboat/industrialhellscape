package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.block.special_blocks_properties.InnerCornerConnectionState;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class InnerCornerBlock extends Block{

    public static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING; //"FACING" is used to store DirectionProperty value of "north, south, east, west" //KJ
    public static final EnumProperty<InnerCornerConnectionState> TYPE_CORNER = EnumProperty.create("type", InnerCornerConnectionState.class); //"UP", "SIDE", or "DOWN" enum values

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

    public InnerCornerBlock(Properties pProperties, VoxelShape upShape, VoxelShape downShape, VoxelShape sideShape) {
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
        ); //Default state if placed with no player present
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

        //This section defines the facing direction property of the block
        BlockState state = this.defaultBlockState().setValue(FACING, directionFacing);

        //This section defines the orientation "type" of the block
        switch(directionClicked) {
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, TYPE_CORNER);
    }
}
