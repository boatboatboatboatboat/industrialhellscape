package net.boat.industrialhellscape.block.modded_block_classes.Experimental;

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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
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
// 4 boolean block state properties corresponding to cardinal directions and whether or not a model will be placed at that location inside
// the block boundaries.
// The block-state .json file is in a multipart format, like sea pickles.
// Multiple placements inside that block to occupy vacant directions is supported. datagen/ModBlockLootTableProvider has a method to datagen the appropriate loot table.

public class RailingBlock extends Block implements SimpleWaterloggedBlock{

    private static final double RAILING_HEIGHT = 16; //16 units is a full block height
    private static final double RAILING_COLLISION_HEIGHT = 24; //Vanilla wall value

    // INTERACTION SHAPE, black outline in-game is based on this shape.
    private static final VoxelShape SHAPE_NORTH = Block.box(0d, 0d, 14d, 16d, RAILING_HEIGHT, 16d);
    private static final VoxelShape SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, SHAPE_NORTH);
    private static final VoxelShape SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, SHAPE_NORTH);
    private static final VoxelShape SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, SHAPE_NORTH);

    // COLLISION SHAPE (FOR PLAYER), arrows will collide with hitbox portion that's within the 16x16x16 block boundary only.
    // Meaning despite the RAILING_COLLISION_HEIGHT being greater than 16 units, arrows can still fly through above the edge of the block.
    // However, player will still be obstructed from jumping over it, just like fences. For now this is intended behavior.
    // The modded interface HitboxRotationInterface is used to rotate the voxelshapes along appropriate cardinal directions.
    //There is a gap from y=0 to y=15. This allows you to shoot arrows through the railing blocks since they are pretty thin.
    private static final VoxelShape COLLISON_SHAPE_NORTH = Block.box(0d, 15d, 14d, 16d, RAILING_COLLISION_HEIGHT, 16d);
    private static final VoxelShape COLLISION_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, COLLISON_SHAPE_NORTH);
    private static final VoxelShape COLLISION_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, COLLISON_SHAPE_NORTH);
    private static final VoxelShape COLLISION_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, COLLISON_SHAPE_NORTH);

    // Boolean properties to check whether there is an additional fence at that direction (multiple can be placed down in each of the four cardinal directions in one block space)
    // These "superposition" block properties are handled via a block-state .json file handling "multi-block" states.
    public static final BooleanProperty NORTH_FENCE = BlockStateProperties.NORTH;
    public static final BooleanProperty SOUTH_FENCE = BlockStateProperties.SOUTH;
    public static final BooleanProperty EAST_FENCE  = BlockStateProperties.EAST;
    public static final BooleanProperty WEST_FENCE  = BlockStateProperties.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public RailingBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(NORTH_FENCE, false)
                .setValue(SOUTH_FENCE, false)
                .setValue(EAST_FENCE,  false)
                .setValue(WEST_FENCE,  false)
                .setValue(BlockStateProperties.WATERLOGGED, false)
        );
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        boolean playerHasTool = stack.is(ModTags.Items.IH_COMPATIBLE_TOOLS);

        //Rotates the current railings counterclockwise.
        if(playerHasTool) {
            boolean north = state.getValue(NORTH_FENCE);
            boolean south = state.getValue(SOUTH_FENCE);
            boolean east = state.getValue(EAST_FENCE);
            boolean west = state.getValue(WEST_FENCE);

            state = state.setValue(NORTH_FENCE, east);
            state = state.setValue(EAST_FENCE, south);
            state = state.setValue(SOUTH_FENCE, west);
            state = state.setValue(WEST_FENCE, north);

            level.setBlock(pos, state, 3);

            level.playSound(player, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.25f, 1f);
            return ItemInteractionResult.SUCCESS;
        } else {
            //No interaction if no eligible tool is equipped.
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
    }

    public boolean canBeReplaced(BlockState pState, @Nonnull BlockPlaceContext pUseContext) {
        //Is this block a RailingBlock? Allow additional block placement into the occupied space only if you have another block like this in your hand.
        //Like for sea-pickles or candles.
        return pState.getBlock() instanceof RailingBlock ? pUseContext.getItemInHand().is(this.asItem()) : super.canBeReplaced(pState, pUseContext);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement (BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos position = pContext.getClickedPos();
        Direction facing = pContext.getHorizontalDirection().getOpposite();
        FluidState fluid = level.getFluidState(position);
        Block clickedBlock = level.getBlockState(position).getBlock();
        boolean isRailingBlock = clickedBlock instanceof RailingBlock;

        if(isRailingBlock) { //If there is a RailingBlock at the location of placement
            BlockState state = level.getBlockState(position); //Get the current block-state of the RailingBlock (which should already be there)
            switch(facing) { //Assign true to the property corresponding with the direction player is facing to place a new railing in that direction next to existing ones
                case NORTH -> state = state.setValue(NORTH_FENCE, true);
                case SOUTH -> state = state.setValue(SOUTH_FENCE, true);
                case EAST -> state = state.setValue(EAST_FENCE, true);
                case WEST -> state = state.setValue(WEST_FENCE, true);
            }
            state.setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
            return state;

        } else { //Fallback. If there is NOT a detected RailingBlock at this location.
            return defaultBlockState() //get a new "blank slate" (the default block slate with no fences defined earlier)
                    .setValue(NORTH_FENCE, (facing == Direction.NORTH)) //Set value based on where player is currently looking
                    .setValue(SOUTH_FENCE, (facing == Direction.SOUTH))
                    .setValue(EAST_FENCE, (facing == Direction.EAST))
                    .setValue(WEST_FENCE, (facing == Direction.WEST))
                    .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER); //Set waterlogged status also

        }
    }

    @Override
    public @Nonnull VoxelShape getShape(@Nonnull BlockState pState, @Nonnull BlockGetter reader, @Nonnull BlockPos pos, @Nonnull CollisionContext ctx) {
        return getInteractionShape(pState, reader, pos);
    }

    @Override
    public @Nonnull VoxelShape getInteractionShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos) {
        VoxelShape shape = Shapes.empty();
        if (pState.getValue(NORTH_FENCE)) shape = Shapes.join(shape, SHAPE_NORTH, BooleanOp.OR);
        if (pState.getValue(SOUTH_FENCE)) shape = Shapes.join(shape, SHAPE_SOUTH, BooleanOp.OR);
        if (pState.getValue(EAST_FENCE))  shape = Shapes.join(shape, SHAPE_EAST,  BooleanOp.OR);
        if (pState.getValue(WEST_FENCE))  shape = Shapes.join(shape, SHAPE_WEST,  BooleanOp.OR);

        return shape;
    }

    @Override
    public @Nonnull VoxelShape getCollisionShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        VoxelShape shape = Shapes.empty();
        if (pState.getValue(NORTH_FENCE)) shape = Shapes.join(shape, COLLISON_SHAPE_NORTH, BooleanOp.OR);
        if (pState.getValue(SOUTH_FENCE)) shape = Shapes.join(shape, COLLISION_SHAPE_SOUTH, BooleanOp.OR);
        if (pState.getValue(EAST_FENCE))  shape = Shapes.join(shape, COLLISION_SHAPE_EAST,  BooleanOp.OR);
        if (pState.getValue(WEST_FENCE))  shape = Shapes.join(shape, COLLISION_SHAPE_WEST,  BooleanOp.OR);
        return shape;
    }

    @Override
    public void neighborChanged (@Nonnull BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull Block neighborBlock, @Nonnull BlockPos neighborPos, boolean movedByPiston) {
        // Used to ensure the block doesn't leave a ghost behind if all 4 sides are gone
        //If there is NO railings present at the blockstate of this location (All FENCE properties were set to false)
        //Set the block at that location to be an air block, erasing the block.
        if (!railingExists(pState)) pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 0);
        super.neighborChanged(pState, pLevel, pPos, neighborBlock, neighborPos, movedByPiston);
    }

    //Corresponds a Direction input to the block-state boolean property for railing blocks.
    public static BooleanProperty fromDirection (Direction face) {
        return switch (face) {
            case SOUTH -> SOUTH_FENCE;
            case EAST  -> EAST_FENCE;
            case WEST  -> WEST_FENCE;
            default -> NORTH_FENCE;
        };
    }

    //Checks if ANY railings exist at that block position
    public static boolean railingExists(BlockState pState) {
        boolean occupied = false;
        // For all possible cardinal values
        // "occupied" becomes true ONLY IF any of the block state boolean properties are TRUE
        // Meaning that a RailingBlock is present here.
        for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            occupied |= pState.getValue(fromDirection(dir));
        }
        return occupied;
    }

    @Override
    public @Nonnull FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        boolean north = state.getValue(NORTH_FENCE);
        boolean south = state.getValue(SOUTH_FENCE);
        boolean east = state.getValue(EAST_FENCE);
        boolean west = state.getValue(WEST_FENCE);

        switch (rotation){
            case CLOCKWISE_90 -> state = state.setValue(NORTH_FENCE, west)
                    .setValue(WEST_FENCE, south)
                    .setValue(SOUTH_FENCE, east)
                    .setValue(EAST_FENCE, north);
            case CLOCKWISE_180 -> state = state.setValue(NORTH_FENCE, south)
                    .setValue(WEST_FENCE, east)
                    .setValue(SOUTH_FENCE, north)
                    .setValue(EAST_FENCE, west);
            case COUNTERCLOCKWISE_90 -> state = state.setValue(NORTH_FENCE, east)
                    .setValue(EAST_FENCE, south)
                    .setValue(SOUTH_FENCE, west)
                    .setValue(WEST_FENCE, north);
            default -> {
                //Assumed to be case "NONE", therefore block is unchanged
            }
        }
        return state;
    }

    @Override
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        boolean north = state.getValue(NORTH_FENCE);
        boolean south = state.getValue(SOUTH_FENCE);
        boolean east = state.getValue(EAST_FENCE);
        boolean west = state.getValue(WEST_FENCE);

        switch (mirror) {
            case LEFT_RIGHT -> state = state.setValue(NORTH_FENCE, south)
                    .setValue(SOUTH_FENCE, north);
            case FRONT_BACK -> state = state.setValue(EAST_FENCE, west)
                    .setValue(WEST_FENCE, east);
            default -> {
                //Assumed to be case "NONE", therefore block is unchanged
            }
        }
        return state;
    }

    protected boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected void createBlockStateDefinition (StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH_FENCE, SOUTH_FENCE, EAST_FENCE, WEST_FENCE, WATERLOGGED);
    }
}