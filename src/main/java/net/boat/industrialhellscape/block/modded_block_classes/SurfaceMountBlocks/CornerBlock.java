package net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks;

import net.boat.industrialhellscape.block.modded_interfaces.HitboxRotationInterface;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

//INFO:
//-----
// This block, when placed, will align top/bottom. It is meant for "corner" or "right-angle" shaped blocks whose models will be touching two perpendicular full-block surfaces of adjacent blocks.
// Using an appropriate tool and interacting with the block, it will rotate, cycling through the cardinal directions it is facing.
// Using an appropriate tool while crouching, it will cycle through the ATTACH_FACE variants (touching floor, ceiling, or side of a wall)

//Corner blocks have unique geometric issues that make them distinct from other blocks that can be placed on floors, ceilings, or walls.

//Textured (Blockbench) models for normal blocks are saved in the default orientation of "on the floor, facing North"
//Most block classes accept a Voxelshape hitbox assuming this default orientation too.

//Textured (Blockbench) models for corner blocks are saved in the default orientation of "on the floor, facing West"
//VoxelShapes for corner blocks therefore default to this too, "INNER_CORNER_DOWN_W = downShape;" see below.

//This is because Minecraft cannot rotate models in the Z axis when generating block states. This makes my life harder.
//Having the 3D model of the block aligned this way allows combinations of X and Y axis rotations to achieve all possible block states orientations.

public class CornerBlock extends ModelledSurfaceMountBlock implements SimpleWaterloggedBlock {

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
        super(pProperties, downShape);

        INNER_CORNER_DOWN_W = downShape; //g
        INNER_CORNER_DOWN_S = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, INNER_CORNER_DOWN_W);
        INNER_CORNER_DOWN_E = HitboxRotationInterface.rotateVoxelYAxisIntTimes(2, INNER_CORNER_DOWN_W);
        INNER_CORNER_DOWN_N = HitboxRotationInterface.rotateVoxelYAxisIntTimes(3, INNER_CORNER_DOWN_W);

        INNER_CORNER_UP_W = HitboxRotationInterface.rotateVoxelXAxisIntTimes(2, downShape);
        INNER_CORNER_UP_S = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, INNER_CORNER_UP_W);
        INNER_CORNER_UP_E = HitboxRotationInterface.rotateVoxelYAxisIntTimes(2, INNER_CORNER_UP_W);
        INNER_CORNER_UP_N = HitboxRotationInterface.rotateVoxelYAxisIntTimes(3, INNER_CORNER_UP_W);

        INNER_CORNER_SIDE_W = HitboxRotationInterface.rotateVoxelXAxisIntTimes(1, downShape);
        INNER_CORNER_SIDE_S = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, INNER_CORNER_SIDE_W);
        INNER_CORNER_SIDE_E = HitboxRotationInterface.rotateVoxelYAxisIntTimes(2, INNER_CORNER_SIDE_W);
        INNER_CORNER_SIDE_N = HitboxRotationInterface.rotateVoxelYAxisIntTimes(3, INNER_CORNER_SIDE_W);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(ATTACH_FACE, AttachFace.CEILING)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {

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

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Player player = pContext.getPlayer();
        boolean playerExists = player != null;
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Direction facing = pContext.getHorizontalDirection().getOpposite();
        Direction clickedFaceDirection = pContext.getClickedFace();

        BlockState state; //Start with default blockstate

        if (clickedFaceDirection.getAxis() == Direction.Axis.Y) { //On Floor or Ceiling
            state = this.defaultBlockState()
                    .setValue(ATTACH_FACE, clickedFaceDirection == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING).setValue(FACING, facing);
        } else if (playerExists && player.isShiftKeyDown()) { //On wall, but player is crouching.
            state = this.defaultBlockState()
                    .setValue(ATTACH_FACE, AttachFace.CEILING).setValue(FACING, facing);
        } else { //On walls
            state = this.defaultBlockState()
                    .setValue(ATTACH_FACE, AttachFace.WALL).setValue(FACING, facing);
        }

        state = state.setValue(WATERLOGGED,fluidstate.getType() == Fluids.WATER);

        return state;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        boolean playerHasTool = stack.is(ModTags.Items.IH_COMPATIBLE_TOOLS);
        boolean playerIsCrouching = player.isCrouching();

        if(playerHasTool && playerIsCrouching) {
            //Cycles connection type. Only works with modded tools
            level.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.25f, 1f);
            state = state.cycle(FACING);
            level.setBlock(pos, state, 2); //2
            return ItemInteractionResult.sidedSuccess(level.isClientSide);



        } else if (playerHasTool) {
            //Cycles from vertical and horizontal pipes when interacting with pipes on walls. Works with modded tools AND pickaxes
            level.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.25f, 1f);
            state = state.cycle(ATTACH_FACE);
            level.setBlock(pos, state, 3); //3
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public @NotNull BlockState mirror(BlockState pState, @NotNull Mirror pMirror) {
        if (pState.getValue(ATTACH_FACE) == AttachFace.WALL) { //sideways placement is different from ceiling/floor placement
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
        pBuilder.add(FACING, ATTACH_FACE, WATERLOGGED);
    }
}
