package net.boat.industrialhellscape.block.modded_interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface MultiBlockPlacementInterface {
    static BlockPos vectorToBlockPos(BlockPos originPos, int[][] multiBlockMatrix, int rowIndice, BlockState pState) {
        int[] currentVector = multiBlockMatrix[rowIndice];
        Direction placementDirection = pState.getValue(BlockStateProperties.HORIZONTAL_FACING);

        return switch(placementDirection) {
            case WEST -> originPos.relative(Direction.NORTH, currentVector[0]).relative(Direction.UP, currentVector[1]).relative(Direction.EAST, currentVector[2]);
            case SOUTH -> originPos.relative(Direction.WEST, currentVector[0]).relative(Direction.UP, currentVector[1]).relative(Direction.NORTH, currentVector[2]);
            case EAST -> originPos.relative(Direction.SOUTH, currentVector[0]).relative(Direction.UP, currentVector[1]).relative(Direction.WEST, currentVector[2]);
            default -> originPos.relative(Direction.EAST, currentVector[0]).relative(Direction.UP, currentVector[1]).relative(Direction.SOUTH, currentVector[2]);
        };
    }

    static BlockPos vectorToOriginBlockPos(BlockPos pos, int[][] multiBlockMatrix, BlockState pState, IntegerProperty partProperty) {
        Direction placementDirection = pState.getValue(BlockStateProperties.HORIZONTAL_FACING);
        int intBlockState = pState.getValue(partProperty);
        int[] currentVector = multiBlockMatrix[intBlockState];

        return switch(placementDirection) {
            //vectorToOriginBlockPos is the same as vectorToBlockPos, except it returns negative values.
            //These are kept for clarity and debugging instead of merely changing to the opposite Directions.
            case WEST -> pos.relative(Direction.NORTH, -currentVector[0]).relative(Direction.UP, -currentVector[1]).relative(Direction.EAST, -currentVector[2]);
            case SOUTH -> pos.relative(Direction.WEST, -currentVector[0]).relative(Direction.UP, -currentVector[1]).relative(Direction.NORTH, -currentVector[2]);
            case EAST -> pos.relative(Direction.SOUTH, -currentVector[0]).relative(Direction.UP, -currentVector[1]).relative(Direction.WEST, -currentVector[2]);
            default -> pos.relative(Direction.EAST, -currentVector[0]).relative(Direction.UP, -currentVector[1]).relative(Direction.SOUTH, -currentVector[2]);
        };
    }

    static void destroyRemainingMultiBlock(LevelAccessor pLevel, Block thisBlock, BlockPos pos, IntegerProperty partProperty, BlockState pState, int[][] multiBlockMatrix) {
        //Interface method intended to be called when onRemove() is called in the block class.

        BlockPos originPos = vectorToOriginBlockPos(pos, multiBlockMatrix,pState, partProperty);

        //Max IntegerProperty blockState, counting from 0. For a two block multiblock, this value should return 1
        int intFinalBlockState = multiBlockMatrix.length - 1;
        //identify the current block of the multiblock via its partProperty, counting from 0;
        int intCurrentBlockState = pState.getValue(partProperty);
        BlockState currentBlockState = pLevel.getBlockState(pos);
        boolean currentBlockIsThisBlock = currentBlockState.is(thisBlock);

        //Identify the next block state in sequence. If it is the last block, loop back to zero. Else, increment.
        int intNextBlockState = (intCurrentBlockState == intFinalBlockState) ? 0 : intCurrentBlockState + 1 ;
        BlockPos nextBlockPos = vectorToBlockPos(originPos,multiBlockMatrix, intNextBlockState, pState);
        BlockState nextBlockState = pLevel.getBlockState(nextBlockPos);
        boolean nextBlockIsThisBlock = nextBlockState.is(thisBlock);

        /*Executes destruction of subsequent block only if subsequent block is this block and current block is gone (e.g. is Air, or somehow replaced entirely)
          This ensures that state updates set via .setBlock() which trigger onRemove() for some reason, don't lead to unintended block destruction even if all blocks are intact.

          Known side effect: If a non-origin block (one that is not blockState Part: 0) is mined by a Creative Mode player, this method triggers, and .destroyBlock() produce the
           loot pool for block Part: 0 and dropping the item despite player being in Creative Mode.
         */
        if(nextBlockIsThisBlock && !currentBlockIsThisBlock) {
            pLevel.destroyBlock(vectorToBlockPos(originPos, multiBlockMatrix, intNextBlockState, pState), true);
        }
    }

    static void updateRemainingMultiBlock(LevelAccessor pLevel, Block thisBlock, BlockPos pos, IntegerProperty partProperty, BooleanProperty interactiveProperty, BlockState pState, int[][] multiBlockMatrix) {
        /*
        Used by useWithoutItem() override
        Checks each position of the multiblock. If a part still exists at that location,
        toggle the passed property to change states.
         */
        BlockPos originPos = vectorToOriginBlockPos(pos, multiBlockMatrix,pState, partProperty);

        for (int i = 0; i < multiBlockMatrix.length; i++) {
            BlockPos nextPos = vectorToBlockPos(originPos, multiBlockMatrix, i, pState);
            BlockState nextState = pLevel.getBlockState(nextPos);

            if(pLevel.getBlockState(nextPos).is(thisBlock)) {
                pLevel.setBlock(nextPos, nextState.cycle(interactiveProperty), 3);
            }
        }
    }

    static BlockState placeOriginBlock(Level pLevel, BlockPos pPos, BlockState pState, DirectionProperty directionProperty, Direction facing, int[][] multiBlockMatrix) {
        pState = pState.setValue(directionProperty, facing);

        //For-loop checks each BlockPos starting from the origin block position, governed by multiBlockMatrix
        for (int i = 0; i < multiBlockMatrix.length; i++) {
            BlockPos blockPosToCheck = vectorToBlockPos(pPos, multiBlockMatrix, i, pState);

            //True if potential block pos is air and within world boundaries
            boolean blockCanBePlacedHere = pLevel.getBlockState(blockPosToCheck).isAir() && pLevel.getWorldBorder().isWithinBounds(blockPosToCheck);

            //if a position for a future multiblock part placement is invalid, stop & exit for-loop and return null for this method.
            if (!blockCanBePlacedHere) {
                return null;
            }
        }

        //Places down the first block state of the multiBlock if for-loop checks for position pass.
        //The block class's setPlacedBy method (which should contain .constructMultiBlock() ) will handle construction behavior.
        return pState.setValue(directionProperty, facing);
    }

    static void constructMultiBlock(Level pLevel, BlockPos originPos, int[][] multiBlockMatrix, IntegerProperty partProperty) {
        /*
        Used for setPlacedBy() override

        For each state up to the max state predicted by multiBlockMatrix
        - obtain a vector of coordinates from multiBlockMatrix, relative to the originPos (the first block placed down already)
        - set block and assign its PART property the appropriate value in sequence
         */
        BlockState state = pLevel.getBlockState(originPos);
        for (int i = 0; i < multiBlockMatrix.length; i++) {
            BlockPos nextPosition = vectorToBlockPos(originPos, multiBlockMatrix, i, state);
            pLevel.setBlock(nextPosition, state.setValue(partProperty, i), 3);
        }
    }

    static VoxelShape setMultiBlockHitBox(BlockState pState, DirectionProperty facingProperty, IntegerProperty partProperty, VoxelShape[] hitboxArray) {
        /*

       Used for getShape() override

        A multiblock is registered with a VoxelShape[] array in ModBlocks for block registration.
        Each index of "hitboxArray" corresponds to a different hitbox for a block in the multiblock.
        Array position corresponds to blockState PART number.
        Depending on the assigned direction state of the block, the switch case will rotate this hitbox
        appropriately.
         */
        Direction facing = pState.getValue(facingProperty);
        int intCurrentState = pState.getValue(partProperty);

        return switch (facing) {
            case EAST -> HitboxRotationInterface.rotateVoxelCardinal(Direction.EAST, hitboxArray[intCurrentState]);
            case SOUTH -> HitboxRotationInterface.rotateVoxelCardinal(Direction.SOUTH, hitboxArray[intCurrentState]);
            case WEST -> HitboxRotationInterface.rotateVoxelCardinal(Direction.WEST, hitboxArray[intCurrentState]);
            default -> HitboxRotationInterface.rotateVoxelCardinal(Direction.NORTH, hitboxArray[intCurrentState]);
        };
    }
}
