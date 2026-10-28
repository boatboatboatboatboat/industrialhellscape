package net.boat.industrialhellscape.block.block_interfaces;

import net.boat.industrialhellscape.block.block_classes.MultiBlocks.BaseIntegerMultiBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface MultiBlockPlacementInterface {
    static BlockPos vectorToBlockPos(BlockPos originPos, int[][] multiBlockMatrix, int rowIndice, BlockState pState) {
        //From a multiBlock initial (origin) position and part# = 0 state, find the "i" = rowIndice position for the part# = "i" block;
        Direction placementDirection = pState.getValue(BlockStateProperties.HORIZONTAL_FACING);
        int[] currentVector = multiBlockMatrix[rowIndice];

        /*
        For default scenario (a block was placed and the block faces north, not the player):

        First column tells how many blocks West this block of the entire multiblock will be placed
        Second column tells how many blocks Up this block will be placed
        Third column tells how many blocks South this block will be placed

        From player pov, positive offset is to right, and away from player towards horizon.
        */

        return switch(placementDirection) {
            case WEST -> originPos.relative(Direction.SOUTH, currentVector[0]).relative(Direction.UP, currentVector[1]).relative(Direction.EAST, currentVector[2]);
            case SOUTH -> originPos.relative(Direction.EAST, currentVector[0]).relative(Direction.UP, currentVector[1]).relative(Direction.NORTH, currentVector[2]);
            case EAST -> originPos.relative(Direction.NORTH, currentVector[0]).relative(Direction.UP, currentVector[1]).relative(Direction.WEST, currentVector[2]);
            default -> originPos.relative(Direction.WEST, currentVector[0]).relative(Direction.UP, currentVector[1]).relative(Direction.SOUTH, currentVector[2]);
        };
    }

    static BlockPos vectorToOriginBlockPos(BlockPos pos, int[][] multiBlockMatrix, BlockState pState, IntegerProperty partProperty) {
        //From a multiBlock position and a multiBlock state, return to its origin block
        Direction placementDirection = pState.getValue(BlockStateProperties.HORIZONTAL_FACING);
        int intBlockState = pState.getValue(partProperty);
        int[] currentVector = multiBlockMatrix[intBlockState];

        return switch(placementDirection) {
            //vectorToOriginBlockPos is the same as vectorToBlockPos, except it returns negative values.
            //These are kept for clarity and debugging instead of merely changing to the opposite Directions.
            case WEST -> pos.relative(Direction.SOUTH, -currentVector[0]).relative(Direction.UP, -currentVector[1]).relative(Direction.EAST, -currentVector[2]);
            case SOUTH -> pos.relative(Direction.EAST, -currentVector[0]).relative(Direction.UP, -currentVector[1]).relative(Direction.NORTH, -currentVector[2]);
            case EAST -> pos.relative(Direction.NORTH, -currentVector[0]).relative(Direction.UP, -currentVector[1]).relative(Direction.WEST, -currentVector[2]);
            default -> pos.relative(Direction.WEST, -currentVector[0]).relative(Direction.UP, -currentVector[1]).relative(Direction.SOUTH, -currentVector[2]);
        };
    }

    static void destroyRemainingMultiBlock(LevelAccessor pLevel, Player pPlayer, Block thisBlock, BlockPos pos, IntegerProperty partProperty, BlockState pState, int[][] multiBlockMatrix) {

        /*
        Used in onDestroyByPlayer() override

        Experimentally determined:
        neighborChanged() appears to be called right before a block disappears. Meaning if a multiblock part is mined,
        adjacent blocks will receive neighborChanged() updates with neighborState being the state of the multiblock part
        prior to its removal.

        Unfortunately, create contraptions will trigger both neighborChanged() and onRemove() for each block attached.
        */

        /*
        This logic isn't used for block updates, so
        it's probably fine to run heavier logic for the specific case
        where a player is actually mining the block.

        Create Mod machinery calls level.destroyBlock somewhere in their code (removing the block and dropping loot)
        This is why all multiBLock loot tables are restricted to dropping only for the first blockState, part:0, to prevent duping.
         */

        //Locate BlockPos of the first block of the multiblock, part "0"
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

                if(pPlayer.isCreative()) { //no loot drops. also pPlayer seems to be nullable
                    pLevel.removeBlock(vectorToBlockPos(originPos, multiBlockMatrix, i, pState), true);
                } else { //has loot drops if in survival
                    pLevel.destroyBlock(vectorToBlockPos(originPos, multiBlockMatrix, i, pState), true);
                }
            } //do nothing if multiblock mid-section is somehow missing. continue for-loop until completion
        } //end for-loop
    }

    static void removeRemainingMultiBlock(LevelAccessor pLevel, Block thisBlock, BlockPos pos, IntegerProperty partProperty, BlockState pState, int[][] multiBlockMatrix) {
        //Removes block with no respect for loot tables. Player is not passed in this method.
        BlockPos originPos = vectorToOriginBlockPos(pos, multiBlockMatrix,pState, partProperty);

        for (int i = 0; i < multiBlockMatrix.length; i++) {
            BlockPos blockPosToCheck = vectorToBlockPos(originPos, multiBlockMatrix, i, pState);

            boolean multiBLockPartIsHere = pLevel.getBlockState(blockPosToCheck).is(thisBlock);

            if (multiBLockPartIsHere) {
                pLevel.removeBlock(vectorToBlockPos(originPos, multiBlockMatrix, i, pState), true);
            }
        }
    }

    static void updateRemainingMultiBlock(LevelAccessor pLevel, Block thisBlock, BlockPos pos, IntegerProperty partProperty, BooleanProperty interactiveProperty, BlockState pState, int[][] multiBlockMatrix) {
        /*
        Used by useWithoutItem() override
        Checks each position of the multiblock. If a part still exists at that location,
        toggle the passed property to change states.
        This affects ALL multi-block blocks.
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

    static BlockState placeModelledOriginBlock(BlockPlaceContext pContext, Block thisBlock, DirectionProperty facingProperty, BooleanProperty waterloggedProperty, boolean placedOppositeFacing) {
        /*
            Used in getStateForPlacement() override for waterloggable blocks
            - Assigns horizontal facing direction and waterlogged property
            - Allows control for whether block state is considered facing towards (opposite direction of player)
                or away from player (boolean placedOppositeFacing)
            - If boolean is changed, the multiBlockPlacementMatrix will need adjustment for correct multiBlock construction
         */

        Direction facing = placedOppositeFacing ? pContext.getHorizontalDirection().getOpposite() : pContext.getHorizontalDirection();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());

        //initialize default block state
        BlockState pState = thisBlock.defaultBlockState();
        //assign waterlogging state value
        pState = pState.setValue(waterloggedProperty,fluidstate.getType() == Fluids.WATER);
        //assign direction facing value
        pState = pState.setValue(facingProperty, facing);

        return pState;
    }

    static boolean isFreeFromObstruction(BlockState pState, LevelReader pLevel, BlockPos pPos, int[][] multiBlockMatrix) {
        /*
           Used in canSurvive() override
           - Obstruction checking logic to prevent placing down block in invalid locations.
           - For-loop checks each BlockPos starting from the origin block position, governed by multiBlockMatrix
           - Placement Contingency #1
           - Create SchematicCannon appears to bypass canSurvive()
        */
        for (int i = 0; i < multiBlockMatrix.length; i++) {
            BlockPos blockPosToCheck = vectorToBlockPos(pPos, multiBlockMatrix, i, pState);

            //True if potential block pos is air and within world boundaries
            boolean blockCanBePlacedHere = pLevel.getBlockState(blockPosToCheck).isAir() && pLevel.getWorldBorder().isWithinBounds(blockPosToCheck);

            /*
                If a position for a future multiblock part placement is invalid,
                end this method prematurely and return false.
                "Break" is not required for premature returns;
            */
            if (!blockCanBePlacedHere) {return false;}
        } //exited for-loop
        return true; //if for-loop succeeds with no obstructions detected. Permit placing down multiBlock.
    }

    static void constructMultiBlock(Level pLevel, BaseIntegerMultiBlock thisBlock, BlockPos originPos, int[][] multiBlockMatrix, IntegerProperty partProperty, DirectionProperty directionProperty) {
        /*
            Used for setPlacedBy() override
            For each state up to the max state predicted by multiBlockMatrix
            - obtain a vector of coordinates from multiBlockMatrix, relative to the originPos (the first block placed down already)
            - set block and assign its PART property the appropriate value in sequence
        */
        BlockState state = pLevel.getBlockState(originPos);
        for (int i = 0; i < multiBlockMatrix.length; i++) {
            BlockPos nextPosition = vectorToBlockPos(originPos, multiBlockMatrix, i, state);

            boolean placementPosIsAir = pLevel.getBlockState(nextPosition).isAir();

            /*
                - If Part# = "i"th blockState cannot locate its origin block (Part# = 0),
                - improper construction is detected. Cancel further MultiBlock construction
                - Placement Contingency #2
                - THIS STOPS THE SCHEMATICANNON BUG
            */
            BlockState originBlockState = pLevel.getBlockState(originPos);
            if(originBlockState.getBlock() instanceof BaseIntegerMultiBlock) {
                if(originBlockState.getValue(thisBlock.partProperty) != 0) {
                    return;
                }
            }

            /*
            Fix for Create SchematiCannon build ordering messing up horizontal multiblocks
            - Appears to act as if a player is placing down each individual block, recognizing vanilla states such as direction FACING
            - Schematicannons appear to respect modded blocks' getStateForPlacement logic but ignores null returns that protects against invalid placement,
                placing the origin block down anyways and allowing subsequent setPlacedBy behavior
            - aside from getStateForPlacement() null returns, also seems to ignore canSurvive() ? TODO: test further

            - A redundant check has to be done either in setPlacedBy or a helper method within it.
                This prevents the origin block placed down by default to set other blocks which will destroy existing blocks in a schematic-Cannon build
             */

            if(placementPosIsAir) {
                /*
                    IF ORIGIN BLOCK IS PLACED:
                    - Only place
                    - Placement Contingency #3
                    - Prevents SchematicCannon from overwriting other blocks
                 */
                pLevel.setBlock(nextPosition, state.setValue(partProperty, i), 3);
            }
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
