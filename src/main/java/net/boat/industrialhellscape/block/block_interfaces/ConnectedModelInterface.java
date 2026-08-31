package net.boat.industrialhellscape.block.block_interfaces;

import net.boat.industrialhellscape.block.block_classes.AxisPillarBlocks.ConnectedPillarBlock;
import net.boat.industrialhellscape.block.block_classes.ConnectedBlocks.ConnectedFurnitureBlock;
import net.boat.industrialhellscape.block.block_classes.SurfaceMountBlocks.SurfaceRotatableBlock;
import net.boat.industrialhellscape.block.block_classes.SurfaceMountBlocks.WallPipeBlock;
import net.boat.industrialhellscape.block.block_state_enums.DynamicConnectionState;
import net.boat.industrialhellscape.block.block_state_enums.SurfacePipeMountState;
import net.boat.industrialhellscape.block.logic_enums.ConnectingBlockPlacementOrientation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

//INFO:
//-----
//This interface offloads similar code from other block classes that give the blocks connection ability when placed next to similar blocks
//This allows changing block-states to custom models that allow blocks to seamlessly "connect", like tables of variable width.
//-----

public interface ConnectedModelInterface {
    EnumProperty<DynamicConnectionState> TYPE = EnumProperty.create("type", DynamicConnectionState.class); //"TYPE" is used to store enum value of "solo, left, right, middle" for block connected variants

    static BlockState ConnectedFurnitureStateForPlacement(BlockPlaceContext context, Block thisBlock, DirectionProperty directionProperty, Property<DynamicConnectionState> typeProperty, ConnectingBlockPlacementOrientation placementDirectionToCheck, TagKey<Block> blockSetFamily) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos(); //is Always air.
        Direction directionToCheck = getClosestDirectionsFromLivingEntity(context.getPlayer(), placementDirectionToCheck);

        BlockPos neighborPos = clickedPos.relative(directionToCheck);
        BlockState neighborBlockState = level.getBlockState(neighborPos);

        //Assign Axis Property
        Direction playerFacing = context.getHorizontalDirection();
        Direction blockFacing = context.getHorizontalDirection().getOpposite();
        BlockState thisState = thisBlock.defaultBlockState().setValue(directionProperty, blockFacing);

        //Assign Waterlogged Property
        FluidState fluidstate = level.getFluidState(context.getClickedPos());
        thisState =  thisState.setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        //Assign Type Property based on clicked Block (thisState is reassigned if conditions are met. Else, has the default type property of type:solo)
        if(neighborBlockState.is(blockSetFamily)) {
            if(neighborBlockState.getValue(directionProperty) == blockFacing) { //<possible failure point if tagged block does not have matching properties as its members.
                if(placingBlockInNegativeRelativeDirection(directionToCheck, playerFacing, placementDirectionToCheck)) {
                    thisState = thisState.setValue(typeProperty, DynamicConnectionState.NEGATIVE);
                } else {
                    thisState = thisState.setValue(typeProperty, DynamicConnectionState.POSITIVE);
                }
            }
        }
        return thisState;
    }

    static void connectedFurnitureUpdateNeighbors(Level level, LivingEntity placer, BlockPos pos, BlockState state, DirectionProperty directionProperty, Property<DynamicConnectionState> typeProperty, ConnectingBlockPlacementOrientation placementDirectionToCheck, TagKey<Block> blockSetFamily) {
        Direction directionToCheck = getClosestDirectionsFromLivingEntity(placer, placementDirectionToCheck);
        BlockPos neighborPos = pos.relative(directionToCheck);
        BlockState neighborBlockState = level.getBlockState(neighborPos);
        Direction playerFacing = placer.getDirection();

        //Assign Type Property based on clicked Block (thisState is reassigned if conditions are met. Else, has the default type property of type:solo)
        if(neighborBlockState.is(blockSetFamily)) {
            if(neighborBlockState.getValue(directionProperty) == state.getValue(directionProperty)) { //<possible failure point if tagged block does not have matching properties as its members.
                boolean playerPlacedBlockInNegativeRelativeDirection = placingBlockInNegativeRelativeDirection(directionToCheck, playerFacing, placementDirectionToCheck);
                boolean playerPlacedBlockInPositiveRelativeDirection = !playerPlacedBlockInNegativeRelativeDirection;
                switch(neighborBlockState.getValue(typeProperty)) {
                    case POSITIVE -> {
                        if(playerPlacedBlockInPositiveRelativeDirection) {
                            level.setBlock(neighborPos, neighborBlockState.setValue(typeProperty, DynamicConnectionState.MIDDLE),3);
                        }
                    }
                    case NEGATIVE -> {
                        if(playerPlacedBlockInNegativeRelativeDirection) {
                            level.setBlock(neighborPos, neighborBlockState.setValue(typeProperty, DynamicConnectionState.MIDDLE),3);
                        }
                    }
                    case SOLO -> {
                        if(playerPlacedBlockInPositiveRelativeDirection) {
                            level.setBlock(neighborPos, neighborBlockState.setValue(typeProperty, DynamicConnectionState.NEGATIVE), 3);
                        } else {
                            level.setBlock(neighborPos, neighborBlockState.setValue(typeProperty, DynamicConnectionState.POSITIVE), 3);
                        }
                    }
                }
            }
        }
    }

    static void fixFurnitureNeighborUponRemove(ConnectedFurnitureBlock thisBlock, BlockState state, Level level, BlockPos pos, BlockState newState, DirectionProperty facingProperty, EnumProperty<DynamicConnectionState> typeProperty, TagKey<Block> blockSetFamily) {
        if(newState.getBlock() != thisBlock) {
            ConnectingBlockPlacementOrientation furnitureOrientation = thisBlock.placementDirection;

            //initialize values before defining them via block's intended placement direction
            BlockPos positiveNeighborBlockPos = pos;
            BlockPos negativeNeighborBlockPos = pos;

            switch (furnitureOrientation) {
                case HORIZONTAL -> {
                    positiveNeighborBlockPos = pos.relative(state.getValue(facingProperty).getClockWise(),1);
                    negativeNeighborBlockPos = pos.relative(state.getValue(facingProperty).getCounterClockWise(),1);
                }
                case VERTICAL -> {
                    positiveNeighborBlockPos = pos.relative(Direction.UP,1);
                    negativeNeighborBlockPos = pos.relative(Direction.DOWN,1);
                }
                case FORWARD -> {
                    positiveNeighborBlockPos = pos.relative(state.getValue(facingProperty).getOpposite(),1);
                    negativeNeighborBlockPos = pos.relative(state.getValue(facingProperty),1);
                }
            }

            BlockState positiveNeighborState = level.getBlockState(positiveNeighborBlockPos);
            BlockState negativeNeighborState = level.getBlockState(negativeNeighborBlockPos);

            if(positiveNeighborState.is(blockSetFamily) && positiveNeighborState.getBlock() instanceof ConnectedFurnitureBlock) { //If positive neighbor is both a part of this blockset, and to be sure, an instance of the class
                if(positiveNeighborState.getValue(facingProperty) == state.getValue(facingProperty)) { //If positive neighbor faces same direction as this block that was removed
                    switch(positiveNeighborState.getValue(typeProperty)) { //get typeProperty of the positive neighbor
                        case POSITIVE -> level.setBlock(positiveNeighborBlockPos, positiveNeighborState.setValue(typeProperty, DynamicConnectionState.SOLO), 3);
                        case MIDDLE -> level.setBlock(positiveNeighborBlockPos, positiveNeighborState.setValue(typeProperty, DynamicConnectionState.NEGATIVE), 3);
                        case NEGATIVE, SOLO -> {
                        }
                    }
                }
            }

            if(negativeNeighborState.is(blockSetFamily) && negativeNeighborState.getBlock() instanceof ConnectedFurnitureBlock) {
                if(negativeNeighborState.getValue(facingProperty) == state.getValue(facingProperty)) {
                    switch(negativeNeighborState.getValue(typeProperty)) {
                        case NEGATIVE -> level.setBlock(negativeNeighborBlockPos, negativeNeighborState.setValue(typeProperty, DynamicConnectionState.SOLO), 3);
                        case MIDDLE -> level.setBlock(negativeNeighborBlockPos, negativeNeighborState.setValue(typeProperty, DynamicConnectionState.POSITIVE), 3);
                        case POSITIVE, SOLO -> {
                        }
                    }
                }
            }

        }
    }

    static BlockState AxialPillarStateForPlacement(BlockPlaceContext context, Block thisBlock, EnumProperty<Direction.Axis> axisProperty, Property<DynamicConnectionState> typeProperty) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos(); //is Always air.
        Direction directionClickedFace = context.getClickedFace();
        Direction directionTowardsNeighborPos = directionClickedFace.getOpposite();
        BlockPos neighborPos = clickedPos.relative(directionTowardsNeighborPos);
        BlockState neighborBlockState = level.getBlockState(neighborPos);

        //Assign Axis Property
        Direction.Axis clickedAxis = context.getClickedFace().getAxis();
        BlockState thisState = thisBlock.defaultBlockState().setValue(axisProperty, clickedAxis);

        //Assign Type Property based on clicked Block (thisState is reassigned if conditions are met. Else, has the default type property of type:solo)
        if(neighborBlockState.is(thisBlock)) {
            if(neighborBlockState.getValue(axisProperty) == clickedAxis) {
                if(directionClickedFace == Direction.fromAxisAndDirection(clickedAxis, Direction.AxisDirection.POSITIVE)) {
                    thisState = thisState.setValue(typeProperty, DynamicConnectionState.POSITIVE);
                } else if(directionClickedFace == Direction.fromAxisAndDirection(clickedAxis, Direction.AxisDirection.NEGATIVE)) {
                    thisState = thisState.setValue(typeProperty, DynamicConnectionState.NEGATIVE);
                }
            }
        }
        return thisState;
    }


    static void AxialPillarUpdateNeighbors(Level level, BlockHitResult hitResult, BlockState neighborBlockState, BlockPos neighborPos, Block thisBlock, EnumProperty<Direction.Axis> axisProperty, Property<DynamicConnectionState> typeProperty) {
        Direction directionClickedFace = hitResult.getDirection();
        Direction.Axis clickedAxis = directionClickedFace.getAxis();

        if(neighborBlockState.is(thisBlock)) {
            if(neighborBlockState.getValue(axisProperty) == clickedAxis) {

                boolean clickedFaceFacesPositiveAxis = (directionClickedFace == Direction.fromAxisAndDirection(clickedAxis, Direction.AxisDirection.POSITIVE));
                boolean clickedFaceFacesNegativeAxis = (directionClickedFace == Direction.fromAxisAndDirection(clickedAxis, Direction.AxisDirection.NEGATIVE));

                switch(neighborBlockState.getValue(typeProperty)) {
                    case POSITIVE -> {
                        if(clickedFaceFacesPositiveAxis) {
                            level.setBlock(neighborPos, neighborBlockState.setValue(typeProperty, DynamicConnectionState.MIDDLE), 3);
                        }
                    }
                    case NEGATIVE -> {
                        if(clickedFaceFacesNegativeAxis) {
                            level.setBlock(neighborPos, neighborBlockState.setValue(typeProperty, DynamicConnectionState.MIDDLE), 3);
                        }
                    }
                    case SOLO -> {
                        if(clickedFaceFacesPositiveAxis) {
                            level.setBlock(neighborPos, neighborBlockState.setValue(typeProperty, DynamicConnectionState.NEGATIVE), 3);
                        } else if(clickedFaceFacesNegativeAxis) {
                            level.setBlock(neighborPos, neighborBlockState.setValue(typeProperty, DynamicConnectionState.POSITIVE), 3);
                        }
                    }
                }
            }
        }
    }

    static void fixPillarNeighborUponRemove(ConnectedPillarBlock thisBlock, BlockState state, Level level, BlockPos pos, BlockState newState, EnumProperty<Direction.Axis> axisProperty, EnumProperty<DynamicConnectionState> typeProperty) {
        if(newState.getBlock() != thisBlock) {
            BlockPos positiveNeighborBlockPos = pos.relative(state.getValue(axisProperty),1);
            BlockPos negativeNeighborBlockPos = pos.relative(state.getValue(axisProperty),-1);
            BlockState positiveNeighborState = level.getBlockState(positiveNeighborBlockPos);
            BlockState negativeNeighborState = level.getBlockState(negativeNeighborBlockPos);

            if(positiveNeighborState.getBlock() instanceof ConnectedPillarBlock) { //If positive neighbor is both a part of this blockset, and to be sure, an instance of the class
                if(positiveNeighborState.getValue(axisProperty).equals(state.getValue(axisProperty))) { //If positive neighbor faces same direction as this block that was removed
                    switch(positiveNeighborState.getValue(typeProperty)) { //get typeProperty of the positive neighbor
                        case POSITIVE -> level.setBlock(positiveNeighborBlockPos, positiveNeighborState.setValue(typeProperty, DynamicConnectionState.SOLO), 3);
                        case MIDDLE -> level.setBlock(positiveNeighborBlockPos, positiveNeighborState.setValue(typeProperty, DynamicConnectionState.NEGATIVE), 3);
                        case NEGATIVE, SOLO -> {
                        }
                    }
                }
            }

            if(negativeNeighborState.getBlock() instanceof ConnectedPillarBlock) {
                if(negativeNeighborState.getValue(axisProperty).equals(state.getValue(axisProperty))) {
                    switch(negativeNeighborState.getValue(typeProperty)) {
                        case NEGATIVE -> level.setBlock(negativeNeighborBlockPos, negativeNeighborState.setValue(typeProperty, DynamicConnectionState.SOLO), 3);
                        case MIDDLE -> level.setBlock(negativeNeighborBlockPos, negativeNeighborState.setValue(typeProperty, DynamicConnectionState.POSITIVE), 3);
                        case POSITIVE, SOLO -> {
                        }
                    }
                }
            }
        }
    }

    static BlockState wallPipeStateForPlacement(Block thisBlock, BlockPlaceContext context, EnumProperty<Direction> facingProperty, EnumProperty<SurfacePipeMountState> orientationProperty) {
        BlockState state = thisBlock.defaultBlockState();
        Direction directionClicked = context.getClickedFace().getOpposite(); //Are you clicking the floor, ceiling, north wall, south wall, east wall, west wall? If clicking ceiling, result is UP
        Direction.Axis cardinalDirection = context.getHorizontalDirection().getAxis(); //What axis is the player facing when they place down the block?
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockState clickedState = level.getBlockState(pos.relative(directionClicked));
        boolean clickedStateIsSurfaceRotateableBlock = clickedState.getBlock() instanceof SurfaceRotatableBlock;
        boolean clickedStateIsSRBonWall = clickedStateIsSurfaceRotateableBlock && clickedState.getValue(facingProperty).getAxis() != Direction.Axis.Y;
        boolean clickedStateIsThisBlock = clickedState.is(thisBlock);
        boolean clickedStateIsFlatWallPipe = clickedStateIsThisBlock && clickedState.getValue(facingProperty).getAxis() == Direction.Axis.Y;
        boolean clickedStateIsWallPipeOnWall = clickedStateIsThisBlock && clickedState.getValue(facingProperty).getAxis() != Direction.Axis.Y;

        //Assign WATERLOGGING
        state =  state.setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER);

        //Assign FACING
        if(clickedStateIsThisBlock || clickedStateIsSurfaceRotateableBlock) {
            if(directionClicked == clickedState.getValue(facingProperty)) { //avoid placing on top of existing pipes
                return null;
            }
            state = state.setValue(facingProperty, clickedState.getValue(facingProperty));
        } else {
            state = state.setValue(facingProperty, directionClicked);
        }

        //Assign ORIENTATION
        if( (directionClicked.getAxis() == Direction.Axis.Y && !clickedStateIsWallPipeOnWall) || clickedStateIsFlatWallPipe) { //placing on floor or clicked pipe on floor

            if(cardinalDirection == Direction.Axis.X) { //Is player facing X axis? Align pipe STRAIGHT
                state = state.setValue(orientationProperty, SurfacePipeMountState.STRAIGHT);
            } else { //Player must have been facing Z axis, align pipe SIDEWAYS
                state = state.setValue(orientationProperty, SurfacePipeMountState.SIDEWAYS);
            }

        } else if(clickedStateIsWallPipeOnWall) { //placing on wall or clicked pipe on wall
            state = state.setValue(orientationProperty, clickedState.getValue(orientationProperty));

        }else if(player != null) { //permits crouching toggling orientation when on walls
            if(player.isCrouching()) {
                state = state.setValue(orientationProperty, SurfacePipeMountState.STRAIGHT);
            } else {
                state = state.setValue(orientationProperty, SurfacePipeMountState.SIDEWAYS);
            }
        }

        BlockPos positivePipePos = ConnectedModelInterface.getPipeNeighborPosition(state,pos, Direction.AxisDirection.POSITIVE,facingProperty, orientationProperty);
        BlockPos negativePipePos = ConnectedModelInterface.getPipeNeighborPosition(state,pos, Direction.AxisDirection.NEGATIVE,facingProperty, orientationProperty);
        BlockState positiveNeighborState = level.getBlockState(positivePipePos);
        BlockState negativeNeighborState = level.getBlockState(negativePipePos);
        //Assign TYPE
        state = state.setValue(TYPE,
                ConnectedModelInterface.getPipeType(
                        thisBlock,
                        state,
                        positiveNeighborState,
                        negativeNeighborState,
                        orientationProperty,
                        facingProperty)
        );

        return state;
    }

    static void updateWallPipeNeighbors(Block thisBlock, BlockState priorState, Level level, BlockPos pos, BlockState newState, EnumProperty<Direction> facingProperty, EnumProperty<SurfacePipeMountState> orientationProperty) {
        BlockPos positiveNeighborPos = getPipeNeighborPosition(priorState,pos,Direction.AxisDirection.POSITIVE, facingProperty, orientationProperty);
        BlockPos negativeNeighborPos = getPipeNeighborPosition(priorState,pos,Direction.AxisDirection.NEGATIVE, facingProperty, orientationProperty);
        BlockState positiveNeighborState = level.getBlockState(positiveNeighborPos);
        BlockState negativeNeighborState = level.getBlockState(negativeNeighborPos);

        if(positiveNeighborState.getBlock() instanceof WallPipeBlock) {
            if((positiveNeighborState.getValue(orientationProperty) == priorState.getValue(orientationProperty)) && (positiveNeighborState.getValue(facingProperty) == priorState.getValue(facingProperty)) ) {
                //unsafe unless assured state argument is of a wallpipe (instanceof)
                BlockPos extraPositiveNeighborPos = getPipeNeighborPosition(positiveNeighborState,positiveNeighborPos,Direction.AxisDirection.POSITIVE, facingProperty, orientationProperty);
                BlockState extraPositiveNeighborState = level.getBlockState(extraPositiveNeighborPos);

                DynamicConnectionState typeForPositiveNeighbor = getPipeType(thisBlock, positiveNeighborState, extraPositiveNeighborState, newState, orientationProperty, facingProperty);
                level.setBlock(positiveNeighborPos, positiveNeighborState.setValue(TYPE, typeForPositiveNeighbor), 3); //3
            }
        }

        if((negativeNeighborState.getBlock() instanceof WallPipeBlock) && (negativeNeighborState.getValue(facingProperty) == priorState.getValue(facingProperty)) ) {
            if(negativeNeighborState.getValue(orientationProperty) == priorState.getValue(orientationProperty)) {
                //unsafe unless assured state argument is of a wallpipe (instanceof)
                BlockPos extraNegativeNeighborPos = getPipeNeighborPosition(negativeNeighborState,negativeNeighborPos,Direction.AxisDirection.NEGATIVE, facingProperty, orientationProperty);
                BlockState extraNegativeNeighborState = level.getBlockState(extraNegativeNeighborPos);

                //negativeNeighborState is the center block. state is the state positive, forward of it. extraNegative is the state further negative, rearwards of it
                DynamicConnectionState typeForNegativeNeighbor = getPipeType(thisBlock, negativeNeighborState, newState, extraNegativeNeighborState, orientationProperty, facingProperty);
                level.setBlock(negativeNeighborPos, negativeNeighborState.setValue(TYPE, typeForNegativeNeighbor), 3); //3
            }
        }
    }

    static BlockPos getPipeNeighborPosition(BlockState pState, BlockPos pos, Direction.AxisDirection signDirection, EnumProperty<Direction> facingProperty, EnumProperty<SurfacePipeMountState> orientationProperty) {
        boolean facingEastWest = (pState.getValue(facingProperty) == Direction.EAST) || (pState.getValue(facingProperty) == Direction.WEST);

        //If block is attached to a wall (NORTH, SOUTH, EAST, WEST)
        if(pState.getValue(facingProperty) != Direction.UP && pState.getValue(facingProperty) != Direction.DOWN) {
            //And the pipe orientation is Straight (meaning pipes align horizontally on walls)
            if(pState.getValue(orientationProperty) == SurfacePipeMountState.STRAIGHT)
            // - If it is on the east/west wall, check the positive Z-axis direction for an adjacent block (Z axis is horizontal to these walls)
            // - But if it is on the north/south wall, check the positive X-axis direction instead for an adjacent block (X axis is horizontal to these walls)
            {return facingEastWest ? pos.relative(Direction.fromAxisAndDirection(Direction.Axis.Z, signDirection)) : pos.relative(Direction.fromAxisAndDirection(Direction.Axis.X, signDirection));}

            //But if the pipe orientation is Sideways, that means it is aligned vertical to the wall. Check the positive Y direction instead on all walls.
            else {return pos.relative(Direction.fromAxisAndDirection(Direction.Axis.Y, signDirection));}

            //ELSE If the block is attached to the floor or ceiling
        } else if (pState.getValue(facingProperty) == Direction.UP || pState.getValue(facingProperty) == Direction.DOWN) {
            //And the pipe orientation is Straight (meaning pipes align towards world X axis), Check the X axis for an adjacent block in positive direction.
            if(pState.getValue(orientationProperty) == SurfacePipeMountState.STRAIGHT) {return pos.relative(Direction.fromAxisAndDirection(Direction.Axis.X, signDirection));}

            // But If the pipe is Sideways, check on the positive Z-axis direction instead.
            else {return pos.relative(Direction.fromAxisAndDirection(Direction.Axis.Z, signDirection));}
        }

        return pos; //Fallback. This should not be possible to reach.
    }

    default BlockState placeStackingRailing(Block thisBlock, BlockPlaceContext pContext, ConnectingBlockPlacementOrientation placementDirection, BooleanProperty booleanFacingProperty, EnumProperty<DynamicConnectionState> typeProperty) {
        //Seperate method because railings use boolean properties to determine direction placed instead of DirectionProperty
        Level level = pContext.getLevel();
        BlockState state = thisBlock.defaultBlockState();
        BlockPos positionClicked = pContext.getClickedPos(); //Get the position when player places new block
        FluidState fluidstate = level.getFluidState(pContext.getClickedPos());

        Direction facing = pContext.getHorizontalDirection().getOpposite(); //gets necessary BLOCK placement direction

        //assumed the MultiBlockPlacementDirection is either Horizontal or Vertical (fallback). Usage of "Forward" doesn't make sense here currently
        BlockState getPositiveState = getStateRelativeTop(level, positionClicked);
        BlockState getNegativeState = getStateRelativeBottom(level, positionClicked);

        state = state.setValue(typeProperty, getStackingRailType(thisBlock, state, getPositiveState, getNegativeState, booleanFacingProperty)); //Second, defines connection type of the block
        state =  state.setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER);
        return state;
    }

    default VoxelShape makeConnectedHitboxes(BlockState pState,
            VoxelShape LEFT_SHAPE_NORTH, VoxelShape LEFT_SHAPE_SOUTH, VoxelShape LEFT_SHAPE_EAST, VoxelShape LEFT_SHAPE_WEST,
            VoxelShape MIDDLE_SHAPE_NORTH, VoxelShape MIDDLE_SHAPE_SOUTH, VoxelShape MIDDLE_SHAPE_EAST, VoxelShape MIDDLE_SHAPE_WEST,
            VoxelShape RIGHT_SHAPE_NORTH, VoxelShape RIGHT_SHAPE_SOUTH, VoxelShape RIGHT_SHAPE_EAST, VoxelShape RIGHT_SHAPE_WEST,
            VoxelShape SOLO_SHAPE_NORTH, VoxelShape SOLO_SHAPE_SOUTH, VoxelShape SOLO_SHAPE_EAST, VoxelShape SOLO_SHAPE_WEST,
                                             DirectionProperty facingProperty,
                                             EnumProperty<DynamicConnectionState> typeProperty
               ) {

        return switch (pState.getValue(typeProperty)) {
            case POSITIVE -> switch (pState.getValue(facingProperty)) {
                case SOUTH -> LEFT_SHAPE_SOUTH;
                case EAST -> LEFT_SHAPE_EAST;
                case WEST -> LEFT_SHAPE_WEST;
                default -> LEFT_SHAPE_NORTH;
            };
            case MIDDLE -> switch (pState.getValue(facingProperty)) {
                case SOUTH -> MIDDLE_SHAPE_SOUTH;
                case EAST -> MIDDLE_SHAPE_EAST;
                case WEST -> MIDDLE_SHAPE_WEST;
                default -> MIDDLE_SHAPE_NORTH;
            };
            case NEGATIVE -> switch (pState.getValue(facingProperty)) {
                case SOUTH -> RIGHT_SHAPE_SOUTH;
                case EAST -> RIGHT_SHAPE_EAST;
                case WEST -> RIGHT_SHAPE_WEST;
                default -> RIGHT_SHAPE_NORTH;
            };
            default -> switch (pState.getValue(facingProperty)) { //Default case is assumed solo case
                case SOUTH -> SOLO_SHAPE_SOUTH;
                case EAST -> SOLO_SHAPE_EAST;
                case WEST -> SOLO_SHAPE_WEST;
                default -> SOLO_SHAPE_NORTH;
            };
        };
    }

    private static Direction getClosestDirectionsFromLivingEntity(@Nullable LivingEntity entity, ConnectingBlockPlacementOrientation placementDirectionToCheck) {
        if(entity != null) {
            Direction[] allNearestLookingDirections = Direction.orderedByNearest(entity);
            return switch (placementDirectionToCheck) {
                case VERTICAL -> allNearestLookingDirections[0];
                case FORWARD -> allNearestLookingDirections[1];
                case HORIZONTAL -> allNearestLookingDirections[2];
            };
        } else {
            return Direction.NORTH;
        }
    }

    private static boolean  placingBlockInNegativeRelativeDirection(Direction directionToCheck, Direction playerFacingDirection, ConnectingBlockPlacementOrientation placementOrientation) {
        return switch(placementOrientation) {
            case HORIZONTAL -> directionToCheck == playerFacingDirection.getCounterClockWise();
            case VERTICAL -> directionToCheck == Direction.UP;
            case FORWARD -> Direction.fromAxisAndDirection(directionToCheck.getAxis(), Direction.AxisDirection.POSITIVE) == playerFacingDirection;
        };
    }

    //---------- METHODS FOR CONNECTED FURNITURE BLOCKS ----------
        //The following methods find the block-state adjacent to the position clicked (this should correspond with a block being placed next to similar neighbors).
        //The block-state of the neighboring block is also used to identify what block that is too.

    //getStateRelativeForward and Backward are relative to the axes (X,Y,Z).
    static BlockState getStateAtAxisPositive(Level level, BlockPos pos, Direction.Axis horizontalAxis) {
        return level.getBlockState(pos.relative(Direction.fromAxisAndDirection(horizontalAxis, Direction.AxisDirection.POSITIVE)));
    }
    static BlockState getStateAtAxisNegative(Level level, BlockPos pos, Direction.Axis axis) {
        return level.getBlockState(pos.relative(Direction.fromAxisAndDirection(axis, Direction.AxisDirection.NEGATIVE)));
    }

    //getStateRelativeTop checks the neighbor above and belows' blockstate. Used for strictly vertical aligned blocks
    static BlockState getStateRelativeTop(Level level, BlockPos positionClicked) {
        BlockPos leftNeighborsPos = positionClicked.above();
        return level.getBlockState(leftNeighborsPos);
    }
    static BlockState getStateRelativeBottom(Level level, BlockPos positionClicked) {
        BlockPos rightNeighborsPos = positionClicked.below();
        return level.getBlockState(rightNeighborsPos);
    }

    /*
    The "getType" methods below output a DynamicConnectionState enum value. This is used by the block to determine
    which model to use to connect to neighboring blocks.

    Certain blocks can connect to completely different blocks (desks and drawers). Both blocks must be part of a
    block tag. There will be appropriate methods with that argument below.
     */
    default DynamicConnectionState getStackingRailType(Block thisBlock, BlockState state, BlockState aboveState, BlockState belowState, BooleanProperty booleanFacingProperty) {
        boolean neighborBelowIsSameBlock = (aboveState.is(thisBlock))
                && state.getValue(booleanFacingProperty) == aboveState.getValue(booleanFacingProperty); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?
        boolean neighborAboveIsSameBlock = (belowState.is(thisBlock))
                && state.getValue(booleanFacingProperty) == belowState.getValue(booleanFacingProperty); //Is the left blockstate the same as the current one, AND current orientation matches left blocks' orientation?

        if (neighborBelowIsSameBlock && !neighborAboveIsSameBlock) return DynamicConnectionState.NEGATIVE;
        else if (!neighborBelowIsSameBlock && neighborAboveIsSameBlock) return DynamicConnectionState.POSITIVE;
        else if (neighborBelowIsSameBlock) return DynamicConnectionState.MIDDLE;
        return DynamicConnectionState.SOLO;
    }

    static DynamicConnectionState getPipeType(Block thisBlock, BlockState state, BlockState forward, BlockState backward, EnumProperty<SurfacePipeMountState> orientationProperty, EnumProperty<Direction> surfaceDirectionProperty) {
        // Checks for axis compatibility like in getPillarType() above, but also has to check that neighbor blocks are attached to the same wall before
        // allowing connection

        if(!state.is(thisBlock)) {
            return DynamicConnectionState.SOLO; //preemptive catch in case wrong block argument
        }

        boolean blockstate_forward_is_same =
                forward.is(thisBlock)

                && state.getValue(orientationProperty) == forward.getValue(orientationProperty)
                && state.getValue(surfaceDirectionProperty) == forward.getValue(surfaceDirectionProperty);
        boolean blockstate_backward_is_same =
                backward.is(thisBlock)

                && state.getValue(orientationProperty) == backward.getValue(orientationProperty)
                && state.getValue(surfaceDirectionProperty) == backward.getValue(surfaceDirectionProperty);

        if (blockstate_forward_is_same && !blockstate_backward_is_same) return DynamicConnectionState.NEGATIVE;
        else if (!blockstate_forward_is_same && blockstate_backward_is_same) return DynamicConnectionState.POSITIVE;
        else if (blockstate_forward_is_same) return DynamicConnectionState.MIDDLE;
        return DynamicConnectionState.SOLO;
    }
}
