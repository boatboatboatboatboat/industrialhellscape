package net.boat.industrialhellscape.block.modded_interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
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

    static void destroyRemainingMultiBlock(LevelAccessor pLevel, Player pPlayer, Block thisBlock, BlockPos pos, IntegerProperty partProperty, BlockState pState, int[][] multiBlockMatrix) {

        /*
        Experimentally determined:
        neighborChanged() appears to be called right before a block disappears. Meaning if a multiblock part is mined,
        adjacent blocks will receive neighborChanged() updates with neighborState being the state of the multiblock part
        prior to its removal.

        Unfortunately, create contraptions will trigger both neighborChanged() and onRemove() for each block attached.
        */

        /*
        used as a helper method for onDestroyedByPlayer().

        This logic isn't used for block updates, so
        it's probably fine to run heavier logic for the specific case
        where a player is actually mining the block.

        Create Mod machinery calls level.destroyBlock somewhere in their code (removing the block and dropping loot)
        This is why all multiBLock loot tables are restricted to dropping only for the first blockState, part:0, to prevent duping.
         */

        //BlockPos of the first block of the multiblock, part "0"
        BlockPos originPos = vectorToOriginBlockPos(pos, multiBlockMatrix,pState, partProperty);

        for (int i = 0; i < multiBlockMatrix.length; i++) {
            BlockPos blockPosToCheck = vectorToBlockPos(originPos, multiBlockMatrix, i, pState);

            boolean multiBLockPartIsHere = pLevel.getBlockState(blockPosToCheck).is(thisBlock);

            if (multiBLockPartIsHere) {
                /*
                If Player is in creative mode, the for-loop will remove all multiBlock parts without dropping loot from
                their loot table.
                If Player is in survival, the for-loop will remove all multiblock parts, and when possible, drop loot from
                their loot table.

                That is the distinction between level.removeBlock() and level.destroyBlock()
                 */
                if(pPlayer.isCreative()) {
                    pLevel.removeBlock(vectorToBlockPos(originPos, multiBlockMatrix, i, pState), true);
                } else {
                    pLevel.destroyBlock(vectorToBlockPos(originPos, multiBlockMatrix, i, pState), true);
                }
            }
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
