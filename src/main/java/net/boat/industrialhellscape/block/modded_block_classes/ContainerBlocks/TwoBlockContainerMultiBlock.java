package net.boat.industrialhellscape.block.modded_block_classes.ContainerBlocks;

import net.boat.industrialhellscape.block.modded_block_classes.MultiBlocks.TwoBlockMultiBlock;
import net.boat.industrialhellscape.block.modded_interfaces.ContainerBlockCapability;
import net.boat.industrialhellscape.block.modded_interfaces.MultiBlockPlacementCapability;
import net.boat.industrialhellscape.block.modded_logic_enums.MultiBlockPlacementDirection;
import net.boat.industrialhellscape.block.modded_block_state_properties.TwoBlockMultiBlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class TwoBlockContainerMultiBlock extends TwoBlockMultiBlock implements EntityBlock {

    public final int SLOTS; //Amount of inventory slots. Should be a multiple of 9.
    public final SoundEvent OPEN_SOUND;
    public final SoundEvent CLOSE_SOUND;
    public MultiBlockPlacementDirection multiBlockPlacementDirection;
    public static final EnumProperty<TwoBlockMultiBlockState> HALF_PART = EnumProperty.create("half", TwoBlockMultiBlockState.class);
    //Values of "POSITIVE" and "NEGATIVE". In this context, it refers either to TOP vs BOTTOM block, or LEFT vs RIGHT block.

    public TwoBlockContainerMultiBlock(Properties pProperties, MultiBlockPlacementDirection multiBlockPlacementDirection, int SLOTS, SoundEvent OPEN_SOUND, SoundEvent CLOSE_SOUND) {
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
        this.SLOTS = SLOTS;
        this.OPEN_SOUND = OPEN_SOUND;
        this.CLOSE_SOUND = CLOSE_SOUND;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF_PART, FACING);
    }

    // PLACEMENT BEHAVIOR ALREADY HANDLED BY SUPERCLASS, "TwoBlockMultiBlock"

    //---------- Block Entity Handling Methods below ----------

    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        //See modded interface MultiBlockPlacementCapability for more details
        return MultiBlockPlacementCapability.newBlockEntityInNegativeBlock(pos, state);
    }

    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        //Interaction for multiblocks will always occur at the NEGATIVE (bottom, right, or back) block's position

        BlockPos negativeHalfPos = pos;
        BlockState negativeHalfBlockState = state;

        // If top block, shift down to get to the block entity
        if (state.getValue(HALF_PART) == TwoBlockMultiBlockState.POSITIVE) { //If the block state at this position is a "Positive" block, redefine position and state to be of the "Negative Block"
            //Apply a correction to the variables to represent the correct block to apply the interaction.
            //See modded interface MultiBlockPlacementCapability for more details
            negativeHalfPos = MultiBlockPlacementCapability.posToPlaceOtherHalf(pos, TwoBlockMultiBlockState.POSITIVE, state.getValue(FACING), multiBlockPlacementDirection); //This should be the position of the NEGATIVE block half of the multiblock
            negativeHalfBlockState = level.getBlockState(negativeHalfPos); //This should return the NEGATIVE block state
        }

        if (negativeHalfBlockState.is(this)) { //Since the defined block may be different, check if its still the same block
            //If the other half is the same block, open the inventory
            //See modded interface ContainerBlockCapability for details
            return ContainerBlockCapability.OpenContainerInventory(level, negativeHalfPos, player);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        //See modded interface ContainerBlockCapability for details
        ContainerBlockCapability.dropSavedContainerInventory(this, state, level, pos, newState);
        super.onRemove(state, level, pos, newState, isMoving);
    }

    //---------- End of Block Entity Handling Methods ----------
}
