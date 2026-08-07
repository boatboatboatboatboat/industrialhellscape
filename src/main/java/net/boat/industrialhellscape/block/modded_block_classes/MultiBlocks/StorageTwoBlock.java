package net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks;

import net.boat.industrialhellscape.block.modded_block_entities.StorageBE.StorageBE;
import net.boat.industrialhellscape.block.modded_interfaces.MultiBlockPlacementInterface;
import net.boat.industrialhellscape.block.modded_interfaces.StorageBlockInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class StorageTwoBlock extends IntegerMultiBlock implements EntityBlock, StorageBlockInterface{
    public final int SLOTS; //Amount of inventory slots. Should be a multiple of 9.
    public final SoundEvent OPEN_SOUND;
    public final SoundEvent CLOSE_SOUND;

    public StorageTwoBlock(Properties pProperties, int registerMaxBlockStates, int[][] multiBlockPlacementMatrix, int slotsAmount, SoundEvent openSound, SoundEvent closeSound) {
        super(pProperties, registerMaxBlockStates, multiBlockPlacementMatrix);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, 0)
                .setValue(FACING, Direction.NORTH)
        );

        this.SLOTS = slotsAmount;
        this.OPEN_SOUND = openSound;
        this.CLOSE_SOUND = closeSound;
    }

    @Override
    public int getSlotCount() {
        return SLOTS;
    }

    @Override
    public SoundEvent getCloseSound() {
        return CLOSE_SOUND;
    }

    @Override
    public SoundEvent getOpenSound() {
        return OPEN_SOUND;
    }

    // PLACEMENT BEHAVIOR ALREADY HANDLED BY SUPERCLASS

    //---------- Block Entity Handling Methods below ----------

    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        //See modded interface MultiBlockPlacementInterface for more details
        //return MultiBlockPlacementInterface.newBlockEntityInNegativeBlock(pos, state);
        if(state.getBlock() instanceof StorageTwoBlock) {
            if(state.getValue(PART) == 0) { //If the block is the POSITIVE block
                return new StorageBE(pos, state);
            }
        }
        return null;
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        BlockPos originPos = MultiBlockPlacementInterface.vectorToOriginBlockPos(pos, multiBlockPlacementMatrix,state, PART);

        return StorageBlockInterface.OpenContainerInventory(level, originPos, player);
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        BlockPos originPos = MultiBlockPlacementInterface.vectorToOriginBlockPos(pos, multiBlockPlacementMatrix,state, PART);
        StorageBlockInterface.dropContainerInventory(this, state, level, originPos, newState);
        MultiBlockPlacementInterface.destroyRemainingMultiBlock(level, this, pos, PART, state, multiBlockPlacementMatrix);

        super.onRemove(state, level, pos, newState, isMoving);
    }

    //---------- End of Block Entity Handling Methods ----------
}
