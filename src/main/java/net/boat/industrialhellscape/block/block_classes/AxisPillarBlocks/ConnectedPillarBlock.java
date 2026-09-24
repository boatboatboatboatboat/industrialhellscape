package net.boat.industrialhellscape.block.block_classes.AxisPillarBlocks;

import net.boat.industrialhellscape.block.block_interfaces.ConnectedModelInterface;
import net.boat.industrialhellscape.block.block_state_enums.DynamicConnectionState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
INFO:
-----
 This block, when placed, aligns with the axis of placement (x, y, z). Subsequent blocks placed adjacent in the same alignment will cause block state updates to allow directional connected textures without CTM.
 This is necessary because CTM does not support directional texture connection.
 The operating methods for block state detection and updating are present in this mod's ConnectedModelInterface interface.

 Block-state notation:
     SOLO - Unconnected block-state. When placed for the first time by itself with no eligible adjacent connections.
     POSITIVE - an "end" connection facing the positive axis (East, Up, South).
     MIDDLE - an interior connection that may repeat based on the length of the pillar.
     NEGATIVE - "an end" connection facing the negative axis direction (West, Down, North).

 Block class is adapted from Hearth and Home mod's Stone Pillar block class code. (has since been heavily adapted. Thank you, Starfish Studios)
 */

public class ConnectedPillarBlock extends RotatedPillarBlock implements ConnectedModelInterface {
    public static final EnumProperty<DynamicConnectionState> TYPE = EnumProperty.create("type", DynamicConnectionState.class); //Custom enum. "TYPE" is used to store enum value of "solo, pos, neg, middle"

    public ConnectedPillarBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(TYPE, DynamicConnectionState.SOLO)
                .setValue(AXIS, Direction.Axis.Z));
    }

    @Override
    public @NotNull BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        /*
         Certain mods call getStateForPlacement() early without an actual block placement action from the player
         If a helper method used in getStateForPlacement() uses level.setBlock(), these mods may unintentionally
         update blocks in the world without user input. This is to be avoided by moving setBlock() actions into
         a separate helper method used elsewhere, such as in useItemOn or setPlacedBy
         */
        return ConnectedModelInterface.AxialPillarStateForPlacement(context,this,AXIS,TYPE);
    }

//    @Override
//    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
//        /*
//        Getting the face direction of a block at a player's crosshair before placing a block is usually done in getStateForPlacement
//        By calling context.getClickedFace(), which internally calls the getDirection() method in BlockHitResult.
//
//        Because this direction is necessary for intuitive pillar placement, level update logic is executed in a helper
//        method located here, in order to access BlockHitResult
//         */
//
//        //WILL NOT EXECUTE WHEN USING MODDED BUILDING-ASSISTANCE TOOLS (CONSTRUCTION WANDS, etc)
//
//        if(stack.is(this.asItem())) { //ensures other block items dont trigger a state update
//            ConnectedModelInterface.AxialPillarUpdateNeighbors(level,hitResult,state,pos,this,AXIS,TYPE);
//        }
//        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
//    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {

        //called regardless of if player uses item or not

        //When setPlacedBy is called, the block's axis and type is already known and placed into the world.
        //Use this to determine the location of the neighbor to update state

        Direction.Axis placedStateAxis = state.getValue(AXIS);
        DynamicConnectionState placedStateType = state.getValue(TYPE);

        switch(placedStateType) {
            //If the block placed down is POSITIVE or NEGATIVE type, determined by getStateForPlacement(),  identify neighbor location to check block type.
            //If it is instance of this block, set neighbor type to MIDDLE to match connection scheme.
            case POSITIVE, NEGATIVE -> {
                DynamicConnectionState toggleType = placedStateType.equals(DynamicConnectionState.POSITIVE) ? DynamicConnectionState.NEGATIVE : DynamicConnectionState.POSITIVE;
                Direction.AxisDirection axisDirectionToCheck = placedStateType.equals(DynamicConnectionState.POSITIVE) ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE;

                BlockPos neighborPos = pos.relative(Direction.fromAxisAndDirection(placedStateAxis, axisDirectionToCheck));
                BlockState neighborBlockState = level.getBlockState(neighborPos);

                BlockPos farNeighborPos = pos.relative(Direction.fromAxisAndDirection(placedStateAxis, axisDirectionToCheck), 2);
                BlockState farNeighborBlockState = level.getBlockState(farNeighborPos);

                boolean neighborIsThis = neighborBlockState.is(this);
                boolean farNeighborIsThis = farNeighborBlockState.is(this);

                boolean neighborAlignedSame = false;
                boolean farNeighborAlignedSame = false;
                boolean twoNeighborsAlignedSame = false;

                if(neighborIsThis) {
                    neighborAlignedSame = neighborBlockState.getValue(AXIS) == state.getValue(AXIS);
                }
                if(farNeighborIsThis) {
                    farNeighborAlignedSame = farNeighborBlockState.getValue(AXIS) == state.getValue(AXIS);
                }
                if(neighborAlignedSame && farNeighborAlignedSame) {
                    twoNeighborsAlignedSame = true;
                }

                if(twoNeighborsAlignedSame) {
                    level.setBlock(neighborPos,neighborBlockState.setValue(TYPE, DynamicConnectionState.MIDDLE),3);
                } else if (neighborAlignedSame) {
                    level.setBlock(neighborPos,neighborBlockState.setValue(TYPE, toggleType),3);
                }

            }
            //if Block placed down is SOLO, determined by getStateForPlacement(),
            //don't do anything.
            //If Block placed down is MIDDLE, determined by getStateForPlacement(),
            //Not possible to place down a middle segment by itself
        }

        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
        ConnectedModelInterface.fixPillarNeighborUponRemove(this,state,level,pos,newState,AXIS ,TYPE);
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    public @NotNull BlockState rotate(@NotNull BlockState state, Rotation rotation) {
        switch (rotation) { //start super switch case
            case CLOCKWISE_90 -> {
                if(state.getValue(AXIS).isHorizontal()) {
                    if(state.getValue(AXIS) == Direction.Axis.X) {
                        state = state.setValue(AXIS, Direction.Axis.Z); //toggle horizontal axis (X axis - CW safe)
                    }
                    else if(state.getValue(AXIS) == Direction.Axis.Z) {
                        state = state.setValue(AXIS, Direction.Axis.X); //toggle horizontal axis (Z axis - CW unsafe)
                        switch(state.getValue(TYPE)) { //toggle TYPE
                            case NEGATIVE -> {state = state.setValue(TYPE, DynamicConnectionState.POSITIVE);}
                            case POSITIVE -> {state = state.setValue(TYPE, DynamicConnectionState.NEGATIVE);}
                        }
                    }
                } //end of operation if axis is horizontal

                // -- no change if axis is vertical (Y axis) --

            }

            case COUNTERCLOCKWISE_90 -> {
                if(state.getValue(AXIS).isHorizontal()) {
                    if(state.getValue(AXIS) == Direction.Axis.X) {
                        state = state.setValue(AXIS, Direction.Axis.Z); //toggle horizontal axis (X axis - CCW unsafe)
                        switch(state.getValue(TYPE)) { //toggle TYPE
                            case NEGATIVE -> {state = state.setValue(TYPE, DynamicConnectionState.POSITIVE);}
                            case POSITIVE -> {state = state.setValue(TYPE, DynamicConnectionState.NEGATIVE);}
                        }
                    }
                    else if(state.getValue(AXIS) == Direction.Axis.Z) {
                        state = state.setValue(AXIS, Direction.Axis.X); //toggle horizontal axis (Z axis - CCW safe)
                    }
                } //end of operation if axis is horizontal

                // -- no change if axis is vertical (Y axis) --

            }

            case CLOCKWISE_180 -> {
                if(state.getValue(AXIS).isHorizontal()) {
                    switch(state.getValue(TYPE)) {
                        case NEGATIVE -> {state = state.setValue(TYPE, DynamicConnectionState.POSITIVE);}
                        case POSITIVE -> {state = state.setValue(TYPE, DynamicConnectionState.NEGATIVE);}
                    }
                }
            }

        } //end super switch case

        return state;
    }

    @Override
    public @NotNull BlockState mirror(BlockState pState, Mirror pMirror) {
        switch(pMirror) {
            case FRONT_BACK, LEFT_RIGHT -> {
                switch(pState.getValue(TYPE)) {
                    case NEGATIVE -> {pState = pState.setValue(TYPE, DynamicConnectionState.POSITIVE);}
                    case POSITIVE -> {pState = pState.setValue(TYPE, DynamicConnectionState.NEGATIVE);}
                }
            }
            case NONE -> {return pState;}
        }

        return pState;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, AXIS);
    }
}