package net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks;

import net.boat.industrialhellscape.block.modded_interfaces.RotationHelper;
import net.boat.industrialhellscape.block.modded_interfaces.ToolUseCapability;
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
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

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
    public static final EnumProperty<AttachFace> ATTACH_FACE = BlockStateProperties.ATTACH_FACE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // 4 * 3 blockstates and therefore hitbox models based on above properties
    private final VoxelShape INNER_CORNER_UP_E;
    private final VoxelShape INNER_CORNER_UP_N;
    private final VoxelShape INNER_CORNER_UP_W;
    private final VoxelShape INNER_CORNER_UP_S;

    private final VoxelShape INNER_CORNER_DOWN_E;
    private final VoxelShape INNER_CORNER_DOWN_N;
    private final VoxelShape INNER_CORNER_DOWN_W;
    private final VoxelShape INNER_CORNER_DOWN_S;

    private final VoxelShape INNER_CORNER_SIDE_E;
    private final VoxelShape INNER_CORNER_SIDE_N;
    private final VoxelShape INNER_CORNER_SIDE_W;
    private final VoxelShape INNER_CORNER_SIDE_S;

    public CornerBlock(Properties pProperties, VoxelShape downShape) {
        super(pProperties);

        INNER_CORNER_DOWN_W = downShape; //g
        INNER_CORNER_DOWN_S = RotationHelper.rotateVoxelYAxisIntTimes(1, INNER_CORNER_DOWN_W);
        INNER_CORNER_DOWN_E = RotationHelper.rotateVoxelYAxisIntTimes(2, INNER_CORNER_DOWN_W);
        INNER_CORNER_DOWN_N = RotationHelper.rotateVoxelYAxisIntTimes(3, INNER_CORNER_DOWN_W);

        INNER_CORNER_UP_W = RotationHelper.rotateVoxelXAxisIntTimes(2, downShape);
        INNER_CORNER_UP_S = RotationHelper.rotateVoxelYAxisIntTimes(1, INNER_CORNER_UP_W);
        INNER_CORNER_UP_E = RotationHelper.rotateVoxelYAxisIntTimes(2, INNER_CORNER_UP_W);
        INNER_CORNER_UP_N = RotationHelper.rotateVoxelYAxisIntTimes(3, INNER_CORNER_UP_W);

        INNER_CORNER_SIDE_W = RotationHelper.rotateVoxelXAxisIntTimes(1, downShape);
        INNER_CORNER_SIDE_S = RotationHelper.rotateVoxelYAxisIntTimes(1, INNER_CORNER_SIDE_W);
        INNER_CORNER_SIDE_E = RotationHelper.rotateVoxelYAxisIntTimes(2, INNER_CORNER_SIDE_W);
        INNER_CORNER_SIDE_N = RotationHelper.rotateVoxelYAxisIntTimes(3, INNER_CORNER_SIDE_W);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ATTACH_FACE, AttachFace.CEILING)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {

        Direction facingForShape = pState.getValue(FACING);

        switch(pState.getValue(ATTACH_FACE)){
            case FLOOR:
                switch(facingForShape) {
                    case NORTH: return INNER_CORNER_DOWN_N;
                    case SOUTH: return INNER_CORNER_DOWN_S;
                    case EAST: return INNER_CORNER_DOWN_E;
                    case WEST: return INNER_CORNER_DOWN_W;
                }
            case CEILING:
                switch(facingForShape) {
                    case NORTH: return INNER_CORNER_UP_N;
                    case SOUTH: return INNER_CORNER_UP_S;
                    case EAST: return INNER_CORNER_UP_E;
                    case WEST: return INNER_CORNER_UP_W;
                }
            case WALL:
                switch(facingForShape) {
                    case NORTH: return INNER_CORNER_SIDE_N;
                    case SOUTH: return INNER_CORNER_SIDE_S;
                    case EAST: return INNER_CORNER_SIDE_E;
                    case WEST: return INNER_CORNER_SIDE_W;
                }
            default:
                return INNER_CORNER_UP_N;
        }
    }

    @Override
    public @Nonnull RenderShape getRenderShape( @Nonnull BlockState pState) {
        return RenderShape.MODEL;
    }

    //Placement Faces the player
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction directionFacing = pContext.getHorizontalDirection().getOpposite(); //Gets the cardinal direction when player places new block
        Direction directionClicked = pContext.getClickedFace();
        boolean PlayerisCrouching = (pContext.getPlayer() != null) && pContext.getPlayer().isCrouching();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());

        //This section defines the facing direction property of the block
        BlockState state = this.defaultBlockState().setValue(FACING, directionFacing);

        //This section determines waterlogging
        state =  state.setValue(WATERLOGGED,fluidstate.getType() == Fluids.WATER);

        //This section defines the orientation "type" of the block (whether the block is on its side, or up or down).
        switch(directionClicked) { //Which block face in the world is the player clicking on to place this block?
            case UP: state = state.setValue(ATTACH_FACE, AttachFace.FLOOR); break; //Bracket faces down if player clicks the floor
            case DOWN: state = state.setValue(ATTACH_FACE, AttachFace.CEILING); break; //Bracket faces up if player clicks the ceiling
            default: state = state.setValue(ATTACH_FACE, AttachFace.WALL); break; //Bracket faces walls if player clicks the walls
        }
        return state;
    }

    @Override
    public @Nonnull InteractionResult use(@Nonnull BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {
        return ToolUseCapability.StandCrouchToolInteract(FACING, ATTACH_FACE,pPlayer,pState,pLevel,pPos);
    }

    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, ATTACH_FACE, WATERLOGGED);
    }
}
