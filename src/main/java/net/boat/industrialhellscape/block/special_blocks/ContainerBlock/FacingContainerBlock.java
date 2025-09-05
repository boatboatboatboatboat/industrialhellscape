package net.boat.industrialhellscape.block.special_blocks.ContainerBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public class FacingContainerBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public final int SLOTS;

    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;


    public FacingContainerBlock(Properties properties, int SlotAmount) {
        super(properties);
        this.SLOTS = SlotAmount; //The inventory capacity of the block is determined during registration


        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = defaultBlockState();
        Direction directionClicked = pContext.getHorizontalDirection(); //Gets the cardinal direction when player places new block

        state = state.setValue(FACING, directionClicked); //Defines facing direction of the block
        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new GenericContainerBE(pos, state);
    }

    @NotNull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (level.getBlockEntity(pos) instanceof GenericContainerBE blockEntity) {
            player.openMenu(blockEntity);
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        //When the block is placed
        if (level.getBlockEntity(pos) instanceof GenericContainerBE blockEntity) {
            blockEntity.setSlotAmount(this.SLOTS); //The block entity will set the inventory slot amount that the registered block should have.
            //setSlotAmount is a function in the GenericContainerBE
        }
    }

    @Override
    public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
        BlockEntity be = level.getBlockEntity(pos);

        if (!state.is(newState.getBlock())) {
            if (be instanceof Container container) { //For container-type block entities (RandomizableContainerBlockEntity)
                Containers.dropContents(level, pos, container);
                level.updateNeighbourForOutputSignal(pos, this);
            } else if(be !=null) { //For more basic block entities

                try {
                    Method possibleGetInventory = be.getClass().getMethod("getInventory"); //Searcb for a getInventory method in this block entity
                    ItemStackHandler inventory = (ItemStackHandler) possibleGetInventory.invoke(be); //Attempt to read and store the block entity's inventory

                    for (int i = 0; i < inventory.getSlots(); i++) {
                        ItemStack stack = inventory.getStackInSlot(i); //One at a time, read and store each itemStack in each slot
                        if (!stack.isEmpty()) { //If that stack is NOT EMPTY
                            var entity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack); //spawn the current itemstack as an Item Entity inworld at this positon
                            level.addFreshEntity(entity); //Add a new entity
                        }
                    }
                } catch(Exception e) { //Should trip if somehow a getInventory method does not exist
                    System.out.println("This block entity is not an inventory Block Entity");
                }

            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
}