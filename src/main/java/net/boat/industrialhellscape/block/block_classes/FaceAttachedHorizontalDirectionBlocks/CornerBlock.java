package net.boat.industrialhellscape.block.block_classes.FaceAttachedHorizontalDirectionBlocks;

import net.boat.industrialhellscape.block.block_interfaces.HitboxRotationInterface;
import net.boat.industrialhellscape.block.block_interfaces.ToolUseInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/*
INFO:
-----
 This block, when placed, will align top/bottom. It is meant for "corner" or "right-angle" shaped blocks whose models will be touching two perpendicular full-block surfaces of adjacent blocks.
 Using an appropriate tool and interacting with the block, it will rotate, cycling through the cardinal directions it is facing.
 Using an appropriate tool while crouching, it will cycle through the FACE variants (touching floor, ceiling, or side of a wall)

Corner blocks have unique geometric issues that make them distinct from other blocks that can be placed on floors, ceilings, or walls.

BlockBench models for normal blocks are saved in the default orientation of "on the floor, facing North"
Most block classes accept a Voxelshape hitbox assuming this default orientation too.
This includes any other subclass of ModelledFAHDBlock.

On contrary, BlockBench models for corner blocks (this class) are saved in the default orientation of "on the floor, facing West"
VoxelShapes for corner blocks therefore default to this too, "INNER_CORNER_DOWN_W = downShape;" see below.
    - If player is facing north and places a CornerBlock on the wall, the wall variant is rotated 90 degrees about Z-direction
    - This is in contrast to other FAHD blocks like levers, which will simply rotate 90 degrees about X axis if player facing north places a lever down
    - Minecraft cannot rotate models in the Z axis when generating block states. This makes my life harder.
    - Having the 3D model of the block aligned west by default, this way allows combinations of X and Y axis rotations to achieve all necessary block states orientations.

Keywords: bracket, Pipe Conduit Inner Corner, Pipe Conduit Outer Corner, pipe_conduit_inner_corner, pipe_conduit_outer_corner
*/

public class CornerBlock extends ModelledFAHDBlock implements SimpleWaterloggedBlock, ToolUseInterface {

    public static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING; //"FACING" is used to store DirectionProperty value of "north, south, east, west" //KJ
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CornerBlock(Properties pProperties, VoxelShape floorHitBox) {
        super(pProperties, floorHitBox);

        //Redefine operations on existing VoxelShape fields of superclass ModelledFAHDBlock

        //Wall Hitbox
        SHAPE_WEST = HitboxRotationInterface.rotateVoxelXAxisIntTimes(1, floorHitBox);
        SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, SHAPE_WEST);
        SHAPE_EAST = HitboxRotationInterface.rotateVoxelYAxisIntTimes(2, SHAPE_WEST);
        SHAPE_NORTH = HitboxRotationInterface.rotateVoxelYAxisIntTimes(3, SHAPE_WEST);

        //Floor Hitbox
        SHAPE_FLOOR_WEST = floorHitBox; //g
        SHAPE_FLOOR_SOUTH = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, SHAPE_FLOOR_WEST);
        SHAPE_FLOOR_EAST = HitboxRotationInterface.rotateVoxelYAxisIntTimes(2, SHAPE_FLOOR_WEST);
        SHAPE_FLOOR_NORTH = HitboxRotationInterface.rotateVoxelYAxisIntTimes(3, SHAPE_FLOOR_WEST);

        //Ceiling Hitbox
        SHAPE_CEILING_WEST = HitboxRotationInterface.rotateVoxelXAxisIntTimes(2, floorHitBox);
        SHAPE_CEILING_SOUTH = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, SHAPE_CEILING_WEST);
        SHAPE_CEILING_EAST = HitboxRotationInterface.rotateVoxelYAxisIntTimes(2, SHAPE_CEILING_WEST);
        SHAPE_CEILING_NORTH = HitboxRotationInterface.rotateVoxelYAxisIntTimes(3, SHAPE_CEILING_WEST);



        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(FACE, AttachFace.CEILING)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {

        Direction facingForShape = pState.getValue(FACING);

        switch(pState.getValue(FACE)){
            case FLOOR:
                switch(facingForShape) {
                    case NORTH: return SHAPE_FLOOR_NORTH;
                    case SOUTH: return SHAPE_FLOOR_SOUTH;
                    case EAST: return SHAPE_FLOOR_EAST;
                    case WEST: return SHAPE_FLOOR_WEST;
                }
            case CEILING:
                switch(facingForShape) {
                    case NORTH: return SHAPE_CEILING_NORTH;
                    case SOUTH: return SHAPE_CEILING_SOUTH;
                    case EAST: return SHAPE_CEILING_EAST;
                    case WEST: return SHAPE_CEILING_WEST;
                }
            case WALL:
                switch(facingForShape) {
                    case NORTH: return SHAPE_NORTH;
                    case SOUTH: return SHAPE_SOUTH;
                    case EAST: return SHAPE_EAST;
                    case WEST: return SHAPE_WEST;
                }
            default:
                return SHAPE_CEILING_NORTH;
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Player player = pContext.getPlayer();
        boolean playerExists = player != null;
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction facing = pContext.getHorizontalDirection().getOpposite();
        Direction clickedFaceDirection = pContext.getClickedFace();

        BlockState state; //Start with default blockstate

        if (clickedFaceDirection.getAxis() == Direction.Axis.Y) { //On Floor or Ceiling, assign respectively floor/ceiling AttachFace enum value
            state = this.defaultBlockState()
                    .setValue(FACE, clickedFaceDirection == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING).setValue(FACING, facing);
        } else if (playerExists && player.isCrouching()) { //On wall, but player is crouching, assign wall Attachface enum value
            state = this.defaultBlockState()
                    .setValue(FACE, AttachFace.WALL).setValue(FACING, facing);
        } else { //On wall, player not crouching, assign attachFace ceiling (intended default state) - i thought i fixed this many times
            state = this.defaultBlockState()
                    .setValue(FACE, AttachFace.CEILING).setValue(FACING, facing);
        }

        state = state.setValue(WATERLOGGED,fluidstate.getType() == Fluids.WATER);

        return state;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        return ToolUseInterface.crouchToolUse(stack, state, level, pos, player, FACE, 3, FACING, 3);
    }

    @Override
    public @NotNull BlockState mirror(BlockState pState, @NotNull Mirror pMirror) {
        if (pState.getValue(FACE) == AttachFace.WALL) { //sideways placement is different from ceiling/floor placement
            Direction originallyFacing = pState.getValue(FACING);
            Direction leftRightMirror;
            Direction frontBackMirror;
            switch(originallyFacing) {
                case SOUTH:
                    leftRightMirror = Direction.WEST; //originally east (4/4/26)
                    frontBackMirror = Direction.EAST; //originally west (4/4/26)
                    break;
                case EAST:
                    leftRightMirror = Direction.NORTH;
                    frontBackMirror = Direction.SOUTH;
                    break;
                case WEST:
                    leftRightMirror = Direction.SOUTH;
                    frontBackMirror = Direction.NORTH;
                    break;
                default: //north
                    leftRightMirror = Direction.EAST; //originally west (4/4/26)
                    frontBackMirror = Direction.WEST; //originally east (4/4/26)
                    break;
            }
            switch (pMirror) {
                case LEFT_RIGHT -> pState = pState.setValue(FACING, leftRightMirror);
                case FRONT_BACK -> pState = pState.setValue(FACING, frontBackMirror);
                default -> {
                    //Assumed to be case "NONE", therefore block is unchanged
                }
            }
            return pState;
        }

        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, FACE, WATERLOGGED);
    }
}
