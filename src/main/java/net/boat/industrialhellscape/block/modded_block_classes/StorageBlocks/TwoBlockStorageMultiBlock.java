package net.boat.industrialhellscape.block.modded_block_classes.StorageBlocks;

import net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.TwoBlockMultiBlock;
import net.boat.industrialhellscape.block.modded_block_entities.StorageBE.StorageBE;
import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.boat.industrialhellscape.block.modded_interfaces.StorageBlockInterface;
import net.boat.industrialhellscape.block.modded_interfaces.MultiBlockPlacementCapability;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
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

import java.util.function.Supplier;

public class TwoBlockStorageMultiBlock extends TwoBlockMultiBlock implements EntityBlock, StorageBlockInterface {
    public final int SLOTS; //Amount of inventory slots. Should be a multiple of 9.
    public final Supplier<SoundEvent> OPEN_SOUND;
    public final Supplier<SoundEvent> CLOSE_SOUND;

    public MultiBlockPlacementDirection multiBlockPlacementDirection;
    //public static final EnumProperty<TwoBlockMultiBlockState> HALF_PART = EnumProperty.create("half", TwoBlockMultiBlockState.class);
    //Values of "POSITIVE" and "NEGATIVE". In this context, it refers either to TOP vs BOTTOM block, or LEFT vs RIGHT block.

    public TwoBlockStorageMultiBlock(Properties pProperties, MultiBlockPlacementDirection multiBlockPlacementDirection, int slotsAmount, Supplier<SoundEvent> openSound, Supplier<SoundEvent> closeSound) {
        // When registering this block, pass in:
        // Properties,
        // Which direction the block will place its other half (horizontally, vertically, longitudinally)
        // Amount of inventory slots for block entity inventory (multiple of 9)
        // Sound to play when player opens inventory
        // Sound to play when player closes inventory

        super(pProperties, multiBlockPlacementDirection);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HALF_PART, TwoBlockMultiBlockState.NEGATIVE)
                .setValue(FACING,Direction.NORTH)
        );
        this.multiBlockPlacementDirection = multiBlockPlacementDirection;
        this.SLOTS = slotsAmount;
        this.OPEN_SOUND = openSound;
        this.CLOSE_SOUND = closeSound;
    }

    @Override
    public int getSlotCount() {
        return SLOTS;
    }

    @Override
    public Supplier<SoundEvent> getCloseSound() {
        return CLOSE_SOUND;
    }

    @Override
    public Supplier<SoundEvent> getOpenSound() {
        return OPEN_SOUND;
    }

    // PLACEMENT BEHAVIOR ALREADY HANDLED BY SUPERCLASS, "TwoBlockMultiBlock"

    //---------- Block Entity Handling Methods below ----------

    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        //See modded interface MultiBlockPlacementCapability for more details
        //return MultiBlockPlacementCapability.newBlockEntityInNegativeBlock(pos, state);
        if(state.getBlock() instanceof TwoBlockStorageMultiBlock) {
            if(state.getValue(HALF_PART) == TwoBlockMultiBlockState.POSITIVE) { //If the block is the POSITIVE block
                return null; //no new block entities will be generated
            }
            return new StorageBE(pos, state); //new StorageBE(pos, state); //One block entity will be present in this multiblock: in the NEGATIVE block
        } else {
            return null;
        }
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        BlockPos negativeHalfPos = pos;
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        if(state.getValue(HALF_PART) == TwoBlockMultiBlockState.POSITIVE) {
            negativeHalfPos = MultiBlockPlacementCapability.posToPlaceOtherHalf(negativeHalfPos, TwoBlockMultiBlockState.POSITIVE, state.getValue(FACING), multiBlockPlacementDirection);
        }
        return StorageBlockInterface.OpenContainerInventory(level, negativeHalfPos, player);
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        BlockPos negativeHalfPos = pos;
        if(state.getValue(HALF_PART) == TwoBlockMultiBlockState.POSITIVE) {
            negativeHalfPos = MultiBlockPlacementCapability.posToPlaceOtherHalf(negativeHalfPos, TwoBlockMultiBlockState.POSITIVE, state.getValue(FACING), multiBlockPlacementDirection);
        }
        StorageBlockInterface.dropContainerInventory(this, state, level, negativeHalfPos, newState);
        super.onRemove(state, level, pos, newState, isMoving);
    }

    //---------- End of Block Entity Handling Methods ----------
}
