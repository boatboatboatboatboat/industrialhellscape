package net.boat.industrialhellscape.block.special_blocks.ContainerBlock;

import net.boat.industrialhellscape.block.special_blocks.TwoBlockMultiBlock;
import net.boat.industrialhellscape.block.special_blocks_properties.MultiBlockPlacementDirection;
import net.boat.industrialhellscape.block.special_blocks_properties.TwoBlockMultiBlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public class TwoBlockContainerMultiBlock extends TwoBlockMultiBlock implements EntityBlock {


    public final int SLOTS; //Amount of inventory slots. Should be a multiple of 9.
    public final SoundEvent OPEN_SOUND;
    public final SoundEvent CLOSE_SOUND;
    public MultiBlockPlacementDirection multiBlockPlacementDirection;
    public static final EnumProperty<TwoBlockMultiBlockState> HALF_PART = EnumProperty.create("half", TwoBlockMultiBlockState.class);
    //Values of "POSITIVE" and "NEGATIVE". In this context, it refers either to TOP vs BOTTOM block, or LEFT vs RIGHT block.

    public TwoBlockContainerMultiBlock(Properties pProperties, MultiBlockPlacementDirection multiBlockPlacementDirection, int SLOTS, SoundEvent OPEN_SOUND, SoundEvent CLOSE_SOUND) {
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

    //---------- Block Entity Handling Methods below ----------

    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        BlockEntity storageEntity = new GenericContainerBE(pos, state);

        if(state.getValue(HALF_PART) == TwoBlockMultiBlockState.POSITIVE) { //If the block is the top block
            return null; //no new block entities will be generated
        }
        return storageEntity; //One block entity will be present: in the bottom block
    }

    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (level.isClientSide()) { //Client side
            return InteractionResult.SUCCESS; //Allows interaction animation to occur client side
        }

        BlockPos negativeHalfPos = pos;
        BlockState negativeHalfBlockState = state;

        // If top block, shift down to get to the block entity
        if (state.getValue(HALF_PART) == TwoBlockMultiBlockState.POSITIVE) {
            //Apply a correction to the variables to represent the correct block to apply the interaction.
            negativeHalfPos = posToPlaceOtherHalf(pos, TwoBlockMultiBlockState.POSITIVE, state.getValue(FACING), multiBlockPlacementDirection); //This should be the position of the NEGATIVE block half of the multiblock
            negativeHalfBlockState = level.getBlockState(negativeHalfPos); //This should return  the NEGATIVE block state
        }

        if (negativeHalfBlockState.is(this)) { //Make sure the block is an instance of this block
            BlockEntity be = level.getBlockEntity(negativeHalfPos); //Get the block entity at the negative half
            if (be instanceof GenericContainerBE blockEntity) {
                player.openMenu(blockEntity);
                return InteractionResult.CONSUME;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        //General method for safe removal of current (Container) and deprecated block entities.
        //Handles both the new container entities and previous non-container block entities

        BlockEntity be = level.getBlockEntity(pos);

        if (!state.is(newState.getBlock())) {
            if (be instanceof Container container) { //For container-type block entities (RandomizableContainerBlockEntity)
                Containers.dropContents(level, pos, container);
                level.updateNeighbourForOutputSignal(pos, this);
            } else if(be !=null) { //For more basic block entities

                try {
                    Method possibleGetInventory = be.getClass().getMethod("getInventory"); //Searcb for a getInventory method in this block entity

                    //-----From TurtyWurty 1.20.1 Github-----

                    ItemStackHandler inventory = (ItemStackHandler) possibleGetInventory.invoke(be); //Attempt to read and store the block entity's inventory

                    for (int i = 0; i < inventory.getSlots(); i++) {
                        ItemStack stack = inventory.getStackInSlot(i); //One at a time, read and store each itemStack in each slot
                        if (!stack.isEmpty()) { //If that stack is NOT EMPTY
                            var entity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack); //spawn the current itemstack as an Item Entity inworld at this positon
                            level.addFreshEntity(entity); //Add a new entity
                        }
                    }
                    //----- End of Obtained Code -----

                } catch(Exception e) { //Should trip if somehow a getInventory method does not exist for the old block entities
                    System.out.println("This block entity is not an inventory Block Entity");
                }

            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    //---------- End of Block Entity Handling Methods ----------
}
