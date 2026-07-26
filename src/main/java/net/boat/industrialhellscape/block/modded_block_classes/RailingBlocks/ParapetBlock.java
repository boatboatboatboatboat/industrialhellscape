package net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks;

import net.boat.industrialhellscape.block.modded_interfaces.HitboxRotationInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
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
// 4 boolean block state properties corresponding to cardinal directions and whether a model will be placed at that location inside the block boundaries.
// 4 additional boolean block state properties corresponding to diagonals (NW, NE, SW, SE) for outer corners for situations where two parapets are placed meeting towards a corner in a different block.
// That block will automatically be occupied with an instance of the Parapet Block as one of these diagonal states to seamlessly connect. That specific corner will disappear if all adjacent connections are removed.
// Multiple Outer Corner Parapets can exist in one block depending on how adjacent parapets are connected. That block will automatically disappear if no appropriate adjacent parapet connections are present.
// Those four diagonal block states are not handled in the block's loot tables (A block will only drop per each main cardinal direction). The diagonal states are safe to mine without duping.

// The block-state .json file is in a multipart format, like sea pickles.
// Multiple placements inside that block to occupy vacant directions is supported. datagen/ModBlockLootTableProvider has a method to datagen the appropriate loot table.

public class ParapetBlock extends RailingBlock implements SimpleWaterloggedBlock{

    private static final double RAILING_HEIGHT = 16; //16 units is a full block height
    private static final double RAILING_COLLISION_HEIGHT = 16; //Vanilla wall value

    private double RAILING_THICKNESS;
    private double RAILING_COLLISION_SHAPE_BASE; //For normal Railing classes, this is 15 to allow projectiles to be shot through their hollow structure.

    // INTERACTION SHAPE, black outline in-game is based on this shape.
    private final VoxelShape SHAPE_NORTH;//
    private final VoxelShape SHAPE_SOUTH;// = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, SHAPE_NORTH);
    private final VoxelShape SHAPE_EAST;// = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, SHAPE_NORTH);
    private final VoxelShape SHAPE_WEST;// = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, SHAPE_NORTH);

    private final VoxelShape SHAPE_NW;//
    private final VoxelShape SHAPE_NE;// = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, SHAPE_NORTH);
    private final VoxelShape SHAPE_SW;// = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, SHAPE_NORTH);
    private final VoxelShape SHAPE_SE;// = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, SHAPE_NORTH);

    // COLLISION SHAPE (FOR PLAYER)
    private final VoxelShape COLLISON_SHAPE_NORTH;
    private final VoxelShape COLLISION_SHAPE_SOUTH;
    private final VoxelShape COLLISION_SHAPE_EAST;
    private final VoxelShape COLLISION_SHAPE_WEST;

    private final VoxelShape COLLISION_SHAPE_NW;
    private final VoxelShape COLLISION_SHAPE_NE;
    private final VoxelShape COLLISION_SHAPE_SW;
    private final VoxelShape COLLISION_SHAPE_SE;

    // Boolean properties to check whether there is an additional fence at that direction (multiple can be placed down in each of the four cardinal directions in one block space)
    // These "superposition" block properties are handled via a block-state .json file handling "multi-block" states.
//    public static final BooleanProperty NORTH_FENCE = BlockStateProperties.NORTH;
//    public static final BooleanProperty SOUTH_FENCE = BlockStateProperties.SOUTH;
//    public static final BooleanProperty EAST_FENCE  = BlockStateProperties.EAST;
//    public static final BooleanProperty WEST_FENCE  = BlockStateProperties.WEST;
//    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty NORTH_WEST_FENCE = BooleanProperty.create("north_west");
    public static final BooleanProperty NORTH_EAST_FENCE = BooleanProperty.create("north_east");
    public static final BooleanProperty SOUTH_WEST_FENCE = BooleanProperty.create("south_west");
    public static final BooleanProperty SOUTH_EAST_FENCE = BooleanProperty.create("south_east");

    public ParapetBlock(Properties pProperties, double railingThickness, double railingCollisionShapeBase) {
        super(pProperties, railingThickness, railingCollisionShapeBase);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(NORTH_FENCE, false)
                .setValue(SOUTH_FENCE, false)
                .setValue(EAST_FENCE,  false)
                .setValue(WEST_FENCE,  false)
                .setValue(NORTH_WEST_FENCE, false)
                .setValue(NORTH_EAST_FENCE,false)
                .setValue(SOUTH_WEST_FENCE, false)
                .setValue(SOUTH_EAST_FENCE, false)
                .setValue(BlockStateProperties.WATERLOGGED, false)
        );
        this.RAILING_THICKNESS = railingThickness;
        this.RAILING_COLLISION_SHAPE_BASE = railingCollisionShapeBase;

        this.SHAPE_NORTH = Block.box(0d, 0d, 0, 16d, RAILING_HEIGHT, RAILING_THICKNESS);
        this.SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, SHAPE_NORTH);
        this.SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, SHAPE_NORTH);
        this.SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, SHAPE_NORTH);

        this.SHAPE_NW = Block.box(0,0,0, RAILING_THICKNESS, 16, RAILING_THICKNESS);
        this.SHAPE_SW = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, SHAPE_NW);
        this.SHAPE_SE = HitboxRotationInterface.rotateVoxelYAxisIntTimes(2, SHAPE_NW);
        this.SHAPE_NE = HitboxRotationInterface.rotateVoxelYAxisIntTimes(3, SHAPE_NW);

        this.COLLISON_SHAPE_NORTH = Block.box(0d, RAILING_COLLISION_SHAPE_BASE, 0, 16d, RAILING_COLLISION_HEIGHT, RAILING_THICKNESS);
        this.COLLISION_SHAPE_SOUTH = HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, COLLISON_SHAPE_NORTH);
        this.COLLISION_SHAPE_EAST = HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, COLLISON_SHAPE_NORTH);
        this.COLLISION_SHAPE_WEST = HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, COLLISON_SHAPE_NORTH);

        this.COLLISION_SHAPE_NW = Block.box(0,0,0, RAILING_THICKNESS, RAILING_COLLISION_HEIGHT, RAILING_THICKNESS);
        this.COLLISION_SHAPE_SW = HitboxRotationInterface.rotateVoxelYAxisIntTimes(1, COLLISION_SHAPE_NW);
        this.COLLISION_SHAPE_SE = HitboxRotationInterface.rotateVoxelYAxisIntTimes(2, COLLISION_SHAPE_NW);
        this.COLLISION_SHAPE_NE = HitboxRotationInterface.rotateVoxelYAxisIntTimes(3, COLLISION_SHAPE_NW);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement (BlockPlaceContext pContext) {

        Level level = pContext.getLevel();
        BlockPos position = pContext.getClickedPos();
        Direction facing = pContext.getHorizontalDirection(); //.getOpposite(); //Makes more sense to not get direction opposite of player facing for these types of blocks.
        FluidState fluid = level.getFluidState(position);
        Block clickedBlock = level.getBlockState(position).getBlock();
        BlockState state = level.getBlockState(position); //Get the current block-state of the RailingBlock (which should already be there)
        boolean isRailingBlock = clickedBlock instanceof ParapetBlock;

        if(isRailingBlock) { //If there is a RailingBlock at the location of placement
            switch(facing) { //Assign true to the property corresponding with the direction player is facing to place a new railing in that direction next to existing ones
                case NORTH -> state = state.setValue(NORTH_FENCE, true).setValue(NORTH_WEST_FENCE, false).setValue(NORTH_EAST_FENCE, false);
                case SOUTH -> state = state.setValue(SOUTH_FENCE, true).setValue(SOUTH_WEST_FENCE, false).setValue(SOUTH_EAST_FENCE, false);
                case EAST -> state = state.setValue(EAST_FENCE, true).setValue(NORTH_EAST_FENCE, false).setValue(SOUTH_EAST_FENCE, false);
                case WEST -> state = state.setValue(WEST_FENCE, true).setValue(NORTH_WEST_FENCE, false).setValue(SOUTH_WEST_FENCE, false);
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

    public void setPlacedBy(@Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull BlockState pState, @javax.annotation.Nullable LivingEntity pPlacer, @Nonnull ItemStack pStack) {
        if(pPlacer != null) {
            Direction facing = pPlacer.getDirection();

            BlockPos leftPosFromBlock = pPos.relative(facing.getCounterClockWise());
            BlockState leftBlock = pLevel.getBlockState(leftPosFromBlock);
            BlockPos leftDiagonalPosFromBlock = leftPosFromBlock.relative(facing);
            BlockState leftDiagonalState = pLevel.getBlockState(leftDiagonalPosFromBlock);
            boolean leftDiagonalBlockisThisBlock = leftDiagonalState.is(this);
            boolean leftBlockIsAirOrThisBlock = leftBlock.isAir() || leftBlock.is(this);

            BlockPos rightPosFromBlock = pPos.relative(facing.getClockWise());
            BlockState rightBlock = pLevel.getBlockState(rightPosFromBlock);
            BlockPos rightDiagonalPosFromBlock = rightPosFromBlock.relative(facing);
            BlockState rightDiagonalState = pLevel.getBlockState(rightDiagonalPosFromBlock);
            boolean rightDiagonalBlockisThisBlock = rightDiagonalState.is(this);
            boolean rightBlockIsAirOrThisBlock = rightBlock.isAir() || rightBlock.is(this);


            if(outerCornerNeedsBlock(facing, pState, leftDiagonalState, leftDiagonalBlockisThisBlock, "left") && leftBlockIsAirOrThisBlock) {
                placeOuterCornerBlock(pLevel, facing, pState, leftPosFromBlock, leftBlock, rightPosFromBlock, rightBlock, "left");
            }
            if(outerCornerNeedsBlock(facing, pState, rightDiagonalState, rightDiagonalBlockisThisBlock,"right") && rightBlockIsAirOrThisBlock) {
                placeOuterCornerBlock(pLevel, facing, pState, leftPosFromBlock, leftBlock, rightPosFromBlock, rightBlock, "right");
            }
        }

    }

    public static boolean outerCornerNeedsBlock(Direction playerFacing, BlockState currentBlockState, BlockState blockStateAtDiagonalPos, boolean diagonalIsThisBlock, String leftOrRight) {
        if(diagonalIsThisBlock) {
            switch (playerFacing) {
                case NORTH -> {
                    if (currentBlockState.getValue(NORTH_FENCE) && leftOrRight.equals("left")) {
                        return blockStateAtDiagonalPos.getValue(EAST_FENCE); //IF CHECKING LEFT DIAGONAL SIDE
                    } else {
                        return blockStateAtDiagonalPos.getValue(WEST_FENCE); //IF CHECKING RIGHT DIAGONAL SIDE
                    }
                }
                case SOUTH -> {
                    if (currentBlockState.getValue(SOUTH_FENCE) && leftOrRight.equals("left")) {
                        return blockStateAtDiagonalPos.getValue(WEST_FENCE); //IF CHECKING LEFT DIAGONAL SIDE
                    } else {
                        return blockStateAtDiagonalPos.getValue(EAST_FENCE); //IF CHECKING RIGHT DIAGONAL SIDE
                    }
                }
                case WEST -> {
                    if (currentBlockState.getValue(WEST_FENCE) && leftOrRight.equals("left")) {
                        return blockStateAtDiagonalPos.getValue(NORTH_FENCE); //IF CHECKING LEFT DIAGONAL SIDE
                    } else {
                        return blockStateAtDiagonalPos.getValue(SOUTH_FENCE); //IF CHECKING RIGHT DIAGONAL SIDE
                    }
                }
                case EAST -> {
                    if (currentBlockState.getValue(EAST_FENCE) && leftOrRight.equals("left")) {
                        return blockStateAtDiagonalPos.getValue(SOUTH_FENCE); //IF CHECKING LEFT DIAGONAL SIDE
                    } else {
                        return blockStateAtDiagonalPos.getValue(NORTH_FENCE); //IF CHECKING RIGHT DIAGONAL SIDE
                    }
                }
            }
            return false;
        }
        return false;
    }

    public BlockState howToUpdateBlockstate(BlockState adjacentBlockState, BlockState currentBlockState, BooleanProperty booleanProperty) {
        //If adjacent block state is instance of this block (a parapet block already exists here), set its desired booleanProperty true.
        //Else, take the current block (known to be an instance of this block), reset to default block state (all booleanProperties false by default), set its desired booleanProperty true.
        //The reset BlockState aforementioned is to be placed adjacent to the current block.
        return adjacentBlockState.is(this) ? adjacentBlockState.setValue(booleanProperty, true) : currentBlockState.getBlock().defaultBlockState().setValue(booleanProperty, true);
    }

    //OUTER CORNER STATE SETTING LOGIC
    public void placeOuterCornerBlock(Level level, Direction facing, BlockState state, BlockPos leftPos, BlockState leftBlock, BlockPos rightPos, BlockState rightBlock, String leftOrRight) {
        switch(facing) {
            case NORTH -> {
                if (state.getValue(NORTH_FENCE) && leftOrRight.equals("left")) {
                    level.setBlock(leftPos, howToUpdateBlockstate(leftBlock, state, NORTH_EAST_FENCE), 2);
                } else {
                    level.setBlock(rightPos, howToUpdateBlockstate(rightBlock, state, NORTH_WEST_FENCE), 2);
                }
            }
            case SOUTH -> {
                if (state.getValue(SOUTH_FENCE) && leftOrRight.equals("left")) {
                    level.setBlock(leftPos, howToUpdateBlockstate(leftBlock, state, SOUTH_WEST_FENCE), 2);
                } else {
                    level.setBlock(rightPos, howToUpdateBlockstate(rightBlock, state, SOUTH_EAST_FENCE), 2);
                }
            }
            case WEST -> {
                if (state.getValue(WEST_FENCE) && leftOrRight.equals("left")) {
                    level.setBlock(leftPos,  howToUpdateBlockstate(leftBlock, state, NORTH_WEST_FENCE), 2);
                } else {
                    level.setBlock(rightPos, howToUpdateBlockstate(rightBlock, state, SOUTH_WEST_FENCE), 2);
                }
            }
            case EAST -> {
                if (state.getValue(EAST_FENCE) && leftOrRight.equals("left")) {
                    level.setBlock(leftPos, howToUpdateBlockstate(leftBlock, state, SOUTH_EAST_FENCE), 2);
                } else {
                    level.setBlock(rightPos, howToUpdateBlockstate(rightBlock, state, NORTH_EAST_FENCE), 2);
                }
            }
        }
    }
    @Override
    public @Nonnull VoxelShape getInteractionShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos) {
        VoxelShape shape = Shapes.empty();
        if (pState.getValue(NORTH_FENCE)) shape = Shapes.join(shape, SHAPE_NORTH, BooleanOp.OR);
        if (pState.getValue(SOUTH_FENCE)) shape = Shapes.join(shape, SHAPE_SOUTH, BooleanOp.OR);
        if (pState.getValue(EAST_FENCE))  shape = Shapes.join(shape, SHAPE_EAST,  BooleanOp.OR);
        if (pState.getValue(WEST_FENCE))  shape = Shapes.join(shape, SHAPE_WEST,  BooleanOp.OR);

        if (pState.getValue(NORTH_WEST_FENCE)) shape = Shapes.join(shape, SHAPE_NW, BooleanOp.OR);
        if (pState.getValue(NORTH_EAST_FENCE)) shape = Shapes.join(shape, SHAPE_NE, BooleanOp.OR);
        if (pState.getValue(SOUTH_WEST_FENCE))  shape = Shapes.join(shape, SHAPE_SW,  BooleanOp.OR);
        if (pState.getValue(SOUTH_EAST_FENCE))  shape = Shapes.join(shape, SHAPE_SE,  BooleanOp.OR);


        return shape;
    }

    @Override
    public @Nonnull VoxelShape getCollisionShape(BlockState pState, @Nonnull BlockGetter pLevel, @Nonnull BlockPos pPos, @Nonnull CollisionContext pContext) {
        VoxelShape shape = Shapes.empty();
        if (pState.getValue(NORTH_FENCE)) shape = Shapes.join(shape, COLLISON_SHAPE_NORTH, BooleanOp.OR);
        if (pState.getValue(SOUTH_FENCE)) shape = Shapes.join(shape, COLLISION_SHAPE_SOUTH, BooleanOp.OR);
        if (pState.getValue(EAST_FENCE))  shape = Shapes.join(shape, COLLISION_SHAPE_EAST,  BooleanOp.OR);
        if (pState.getValue(WEST_FENCE))  shape = Shapes.join(shape, COLLISION_SHAPE_WEST,  BooleanOp.OR);

        if (pState.getValue(NORTH_WEST_FENCE)) shape = Shapes.join(shape, COLLISION_SHAPE_NW, BooleanOp.OR);
        if (pState.getValue(NORTH_EAST_FENCE)) shape = Shapes.join(shape, COLLISION_SHAPE_NE, BooleanOp.OR);
        if (pState.getValue(SOUTH_WEST_FENCE))  shape = Shapes.join(shape, COLLISION_SHAPE_SW,  BooleanOp.OR);
        if (pState.getValue(SOUTH_EAST_FENCE))  shape = Shapes.join(shape, COLLISION_SHAPE_SE,  BooleanOp.OR);
        return shape;
    }

    @Override
    public void neighborChanged (@Nonnull BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, @Nonnull Block neighborBlock, @Nonnull BlockPos neighborPos, boolean movedByPiston) {
        BlockPos northBlockPos = pPos.relative(Direction.NORTH);
        BlockPos southBlockPos = pPos.relative(Direction.SOUTH);
        BlockPos eastBlockPos = pPos.relative(Direction.EAST);
        BlockPos westBlockPos = pPos.relative(Direction.WEST);

        BlockState northBlockState = pLevel.getBlockState(northBlockPos);
        BlockState southBlockState = pLevel.getBlockState(southBlockPos);
        BlockState eastBlockState = pLevel.getBlockState(eastBlockPos);
        BlockState westBlockState = pLevel.getBlockState(westBlockPos);

        //If a diagonal parapet element exists, upon neighborChanged() update, check if it should continue existing based on adjacent block conncetions.
        if(pState.getValue(NORTH_WEST_FENCE)) pState = pState.setValue(NORTH_WEST_FENCE, (parapetBlockFenceExists(northBlockState, WEST_FENCE) || parapetBlockFenceExists(westBlockState, NORTH_FENCE)));
        if(pState.getValue(NORTH_EAST_FENCE)) pState = pState.setValue(NORTH_EAST_FENCE, (parapetBlockFenceExists(northBlockState, EAST_FENCE) || parapetBlockFenceExists(eastBlockState, NORTH_FENCE)));
        if(pState.getValue(SOUTH_WEST_FENCE)) pState = pState.setValue(SOUTH_WEST_FENCE, (parapetBlockFenceExists(southBlockState, WEST_FENCE) || parapetBlockFenceExists(westBlockState, SOUTH_FENCE)));
        if(pState.getValue(SOUTH_EAST_FENCE)) pState = pState.setValue(SOUTH_EAST_FENCE, (parapetBlockFenceExists(southBlockState, EAST_FENCE) || parapetBlockFenceExists(eastBlockState, SOUTH_FENCE)));

        pLevel.setBlock(pPos, pState, 2);

        // Used to ensure the block doesn't leave a ghost behind if all 4 sides are gone
        //If there is NO railings present at the blockstate of this location (All FENCE properties were set to false)
        //Set the block at that location to be an air block, erasing the block.
        //This must be located at the end of the method.
        if (!railingExists(pState)) pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 0);
    }

    public boolean parapetBlockFenceExists(BlockState blockState, BooleanProperty fenceToCheck) {
        return blockState.is(this) && blockState.getValue(fenceToCheck);
    }

    public static BooleanProperty fromDiagonalDirection (Direction face) {
        return switch (face) {
            case SOUTH -> SOUTH_WEST_FENCE;
            case EAST  -> SOUTH_EAST_FENCE;
            case WEST  -> NORTH_WEST_FENCE;
            default -> NORTH_EAST_FENCE;
        };
    }

    //OUTER CORNER LOGIC
    //Checks if ANY railings exist at that block position
    public static boolean railingExists(BlockState pState) {
        boolean occupied = false;
        // For all possible cardinal values
        // "occupied" becomes true ONLY IF any of the block state boolean properties are TRUE
        // Meaning that a RailingBlock is present here.
        for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            occupied |= pState.getValue(fromDirection(dir)); //returns true for any true value, even if the rest are false
        }
        for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            occupied |= pState.getValue(fromDiagonalDirection(dir)); //returns true for any true value, even if the rest are false
        }
        return occupied;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        boolean north = state.getValue(NORTH_FENCE);
        boolean south = state.getValue(SOUTH_FENCE);
        boolean east = state.getValue(EAST_FENCE);
        boolean west = state.getValue(WEST_FENCE);

        boolean nw = state.getValue(NORTH_WEST_FENCE);
        boolean ne = state.getValue(NORTH_EAST_FENCE);
        boolean sw = state.getValue(SOUTH_WEST_FENCE);
        boolean se = state.getValue(SOUTH_EAST_FENCE);

        switch (rotation){
            case CLOCKWISE_90 -> state = state.setValue(NORTH_FENCE, west)
                    .setValue(WEST_FENCE, south)
                    .setValue(SOUTH_FENCE, east)
                    .setValue(EAST_FENCE, north)
                    .setValue(NORTH_WEST_FENCE, sw)
                    .setValue(SOUTH_WEST_FENCE, se)
                    .setValue(SOUTH_EAST_FENCE, ne)
                    .setValue(NORTH_EAST_FENCE, nw);
            case CLOCKWISE_180 -> state = state.setValue(NORTH_FENCE, south)
                    .setValue(WEST_FENCE, east)
                    .setValue(SOUTH_FENCE, north)
                    .setValue(EAST_FENCE, west)
                    .setValue(NORTH_WEST_FENCE, se)
                    .setValue(SOUTH_WEST_FENCE, ne)
                    .setValue(SOUTH_EAST_FENCE, nw)
                    .setValue(NORTH_EAST_FENCE, sw);

            case COUNTERCLOCKWISE_90 -> state = state.setValue(NORTH_FENCE, east)
                    .setValue(EAST_FENCE, south)
                    .setValue(SOUTH_FENCE, west)
                    .setValue(WEST_FENCE, north)
                    .setValue(NORTH_WEST_FENCE, ne)
                    .setValue(SOUTH_WEST_FENCE, nw)
                    .setValue(SOUTH_EAST_FENCE, sw)
                    .setValue(NORTH_EAST_FENCE, se);
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

        boolean nw = state.getValue(NORTH_WEST_FENCE);
        boolean ne = state.getValue(NORTH_EAST_FENCE);
        boolean sw = state.getValue(SOUTH_WEST_FENCE);
        boolean se = state.getValue(SOUTH_EAST_FENCE);

        switch (mirror) {
            case LEFT_RIGHT -> state = state
                    .setValue(NORTH_FENCE, south)
                    .setValue(SOUTH_FENCE, north)
                    .setValue(NORTH_WEST_FENCE, sw)
                    .setValue(NORTH_EAST_FENCE, se)
                    .setValue(SOUTH_WEST_FENCE, nw)
                    .setValue(SOUTH_EAST_FENCE, ne);
            case FRONT_BACK -> state = state
                    .setValue(EAST_FENCE, west)
                    .setValue(WEST_FENCE, east)
                    .setValue(NORTH_WEST_FENCE, ne)
                    .setValue(NORTH_EAST_FENCE, nw)
                    .setValue(SOUTH_WEST_FENCE, se)
                    .setValue(SOUTH_EAST_FENCE, sw);
            default -> {
                //Assumed to be case "NONE", therefore block is unchanged
            }
        }
        return state;
    }

    @Override
    protected void createBlockStateDefinition (StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH_WEST_FENCE, NORTH_EAST_FENCE, SOUTH_WEST_FENCE, SOUTH_EAST_FENCE);
    }
}