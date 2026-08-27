package net.boat.industrialhellscape.block.block_classes.AxisPillarBlocks;

import net.boat.industrialhellscape.block.block_interfaces.ConnectedModelInterface;
import net.boat.industrialhellscape.block.block_state_enums.DynamicConnectionState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

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

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        /*
        Getting the face direction of a block at a player's crosshair before placing a block is usually done in getStateForPlacement
        By calling context.getClickedFace(), which internally calls the getDirection() method in BlockHitResult.

        Because this direction is necessary for intuitive pillar placement, level update logic is executed in a helper
        method located here, in order to access BlockHitResult
         */

        if(stack.is(this.asItem())) { //ensures other block items dont trigger a state update
            ConnectedModelInterface.AxialPillarUpdateNeighbors(level,hitResult,state,pos,this,AXIS,TYPE);
        }
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
        ConnectedModelInterface.fixPillarNeighborUponRemove(this,state,level,pos,newState,AXIS ,TYPE);
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, AXIS);
    }
}