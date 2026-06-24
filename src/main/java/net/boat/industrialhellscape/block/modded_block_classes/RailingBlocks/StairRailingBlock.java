package net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks;

import net.boat.industrialhellscape.block.modded_interfaces.HitboxRotationInterface;
import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

//INFO:
//-----
// Modified from the mod Create Deco. Based on Catwalk Railing block class code (CC0 license)
// 2 boolean block state properties corresponding to relative left or right direction based on direction (FACING) player is facing when placing down the first block.
// The second-nearest cardinal direction to where the player is facing determines whether or not the left or right stair-rail will be placed.
// For example, if player is facing NORTH, if they are facing slightly to the WEST (left), a left stair-rail will be placed.
// If player is facing NORTH, and are facing slightly to the EAST (right), a right stair-rail will be placed.

// The block-state .json file is in a multipart format, like sea pickles.
// Multiple placements inside that block to occupy vacant directions is supported. datagen/ModBlockLootTableProvider has a method to datagen the appropriate loot table.

public class StairRailingBlock extends Block implements SimpleWaterloggedBlock {

    //Properties the block possesses
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty LEFT_FENCE = BooleanProperty.create("left");
    public static final BooleanProperty RIGHT_FENCE = BooleanProperty.create("right");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    // Hitbox shapes (8 total, 2x4) for left and right variants of the stair rail per cardinal direction (used both for collision and interaction)
    // Modded interface HitboxRotationInterface helps rotate voxels along cardinal directions. See interface for more details
    private static final VoxelShape SHAPE_NORTH_LEFT = Block.box(0d, 0d, 0d, 2d, 14, 16d);
    private static final VoxelShape SHAPE_NORTH_RIGHT = Block.box(14d, 0d, 0d, 16d, 14, 16d);

    private static final VoxelShape SHAPE_SOUTH_LEFT = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, SHAPE_NORTH_LEFT);
    private static final VoxelShape SHAPE_SOUTH_RIGHT = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, SHAPE_NORTH_RIGHT);

    private static final VoxelShape SHAPE_EAST_LEFT = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, SHAPE_NORTH_LEFT);
    private static final VoxelShape SHAPE_EAST_RIGHT = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, SHAPE_NORTH_RIGHT);

    private static final VoxelShape SHAPE_WEST_LEFT = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, SHAPE_NORTH_LEFT);
    private static final VoxelShape SHAPE_WEST_RIGHT = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, SHAPE_NORTH_RIGHT);

    public StairRailingBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(LEFT_FENCE, false)
                .setValue(RIGHT_FENCE, false)
                .setValue(BlockStateProperties.WATERLOGGED, false)
        );
    }

    @Override
    public @Nonnull VoxelShape getShape(@Nonnull BlockState pState, @Nonnull BlockGetter reader, @Nonnull BlockPos pos, @Nonnull CollisionContext ctx) {
        return getInteractionShape(pState, reader, pos);
    }

    @Override
    public @Nonnull VoxelShape getInteractionShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos) {
        VoxelShape shape = Shapes.empty();
        Direction facingState = pState.getValue(FACING);

        //Assign one of the eight hitboxes for each placement block state

        if(pState.getValue(LEFT_FENCE) && facingState == Direction.NORTH) shape = Shapes.join(shape, SHAPE_NORTH_LEFT, BooleanOp.OR);
        if(pState.getValue(RIGHT_FENCE) && facingState == Direction.NORTH) shape = Shapes.join(shape, SHAPE_NORTH_RIGHT, BooleanOp.OR);

        if(pState.getValue(LEFT_FENCE) && facingState == Direction.SOUTH) shape = Shapes.join(shape, SHAPE_SOUTH_LEFT, BooleanOp.OR);
        if(pState.getValue(RIGHT_FENCE) && facingState == Direction.SOUTH) shape = Shapes.join(shape, SHAPE_SOUTH_RIGHT, BooleanOp.OR);

        if(pState.getValue(LEFT_FENCE) && facingState == Direction.EAST) shape = Shapes.join(shape, SHAPE_EAST_LEFT, BooleanOp.OR);
        if(pState.getValue(RIGHT_FENCE) && facingState == Direction.EAST) shape = Shapes.join(shape, SHAPE_EAST_RIGHT, BooleanOp.OR);

        if(pState.getValue(LEFT_FENCE) && facingState == Direction.WEST) shape = Shapes.join(shape, SHAPE_WEST_LEFT, BooleanOp.OR);
        if(pState.getValue(RIGHT_FENCE) && facingState == Direction.WEST) shape = Shapes.join(shape, SHAPE_WEST_RIGHT, BooleanOp.OR);

        return shape;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        boolean playerHasTool = player.getMainHandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS);

        //Rotates the current railings counterclockwise.
        if(playerHasTool) {
            state = state.cycle(FACING);

            level.setBlock(pos, state, 3);

            level.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.25f, 1f);
            return InteractionResult.SUCCESS;
        } else {
            //No interaction if no eligible tool is equipped.
            return InteractionResult.PASS;
        }
    }

    public boolean canBeReplaced(BlockState pState, @Nonnull BlockPlaceContext pUseContext) {
        //Is this block a RailingBlock? Allow additional block placement into the occupied space only if you have another block like this in your hand.
        //Like for sea-pickles or candles.
        return pState.getBlock() instanceof StairRailingBlock ? pUseContext.getItemInHand().is(this.asItem()) : super.canBeReplaced(pState, pUseContext);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement (BlockPlaceContext pContext) {
        //The following code allows placement aligning to the players' facing direction, ONLY IF the block is placed on the floor surface (or ceiling surface).

        Level level = pContext.getLevel();
        BlockPos position = pContext.getClickedPos();
        FluidState fluid = level.getFluidState(position);
        BlockState state = pContext.getLevel().getBlockState(position);

        Direction nearestHorizontalDirection = pContext.getHorizontalDirection();

        //1st direction of this array is most likely UP or DOWN. This is to be ignored.
        //2nd direction of this array is most likely the nearest horizontal direction. This is to be ignored. getHorizontalDirection() ("FACING") is more reliable than using this direction indice.
        //3rd direction of this array is most likely the second-nearest horizontal direction. This is used to place either the left or the right railing block automatically along the FACING direction.
        Direction[] allNearestLookingDirections = pContext.getNearestLookingDirections();

        //Pick the THIRD indice from the last array. This is MOST LIKELY the player's second-nearest HORIZONTAL facing direction.
        // The first is most likely UP or DOWN, the second is most likely the same as nearestHorizontalDirection
        Direction secondNearestHorizontalDirection = allNearestLookingDirections[2];

        //Ideally these should be false and the nearest looked-direction should be N/S/E/W, ideally
        boolean secondNearestHorizontalDirectionIsUp = secondNearestHorizontalDirection == Direction.UP;
        boolean secondNearestHorizontalDirectionIsDown = secondNearestHorizontalDirection == Direction.DOWN;

        Block clickedBlock = level.getBlockState(position).getBlock();
        boolean isRailingBlock = clickedBlock instanceof StairRailingBlock;

        if(isRailingBlock) { //If there is a RailingBlock at the location of placement

            //state is the railing block
            //Set the opposite fence to TRUE, placing another fence down in the same block so now there is two (or there is already two, so nothing happens).
            if(state.getValue(LEFT_FENCE)) state = state.setValue(RIGHT_FENCE, true);
            if(state.getValue(RIGHT_FENCE)) state = state.setValue(LEFT_FENCE, true);

            //Update waterlogging status
            state = state.setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
            return state;

        } else { //Fallback. If there is NOT a detected RailingBlock at this location.
            if(pContext.getPlayer() == null) {
                return null; //Disallow placement by non-players to prevent nullPointException errors for locating a player to send Client Message to

            } else if(secondNearestHorizontalDirectionIsUp || secondNearestHorizontalDirectionIsDown) {
                pContext.getPlayer().displayClientMessage(Component.translatable("hud.industrialhellscape.invalid_placement_surface"), true);
                return null; //Disallow placement if the surface to place down is a vertical wall.

            } else { //No stair railing exists at this position

                //state is initially redefined as the default blockstate for the stairs railing block
                state = this.defaultBlockState();
                //Which cardinal direction are the stairs leading to?
                state = state.setValue(FACING, nearestHorizontalDirection); //getHorizontalDirection returns the nearest N/S/E/W direction

                //If the second-nearest direction tends to the relative left (Counterclockwise to nearest-horizontal facing direction), put a left railing down, else, put a right railing down
                if (secondNearestHorizontalDirection == state.getValue(FACING).getCounterClockWise()) {
                    state = state.setValue(LEFT_FENCE, true);
                } else {
                    state = state.setValue(RIGHT_FENCE, true);
                }

                //Update waterlogging status
                return state.setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
            }
        }
    }


    @Override
    public void neighborChanged (@Nonnull BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull Block neighborBlock, @Nonnull BlockPos neighborPos, boolean movedByPiston) {
        // Used to ensure the block doesn't leave a ghost behind if all 2 sides are gone
        //If there is NO railings present at the blockstate of this location (All FENCE properties were set to false)
        //Set the block at that location to be an air block, erasing the block.
        if (!railingExists(pState)) pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 0);
        super.neighborChanged(pState, pLevel, pPos, neighborBlock, neighborPos, movedByPiston);
    }

    //Checks if ANY railings exist at that block position
    public static boolean railingExists(BlockState pState) {
        return pState.getValue(LEFT_FENCE) || pState.getValue(RIGHT_FENCE);
    }

    @Override
    public @Nonnull FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    public @NotNull BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    protected boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition (StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, LEFT_FENCE, RIGHT_FENCE, WATERLOGGED);
    }
}
