package net.boat.industrialhellscape.block.modded_block_classes.ConnectedBlocks;

import net.boat.industrialhellscape.block.modded_block_state_properties.DynamicConnectionState;
import net.boat.industrialhellscape.block.modded_interfaces.ConnectedModelInterface;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
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

public class AxialPillarBlock extends RotatedPillarBlock implements ConnectedModelInterface {
    public static final EnumProperty<DynamicConnectionState> TYPE = EnumProperty.create("type", DynamicConnectionState.class); //Custom enum. "TYPE" is used to store enum value of "solo, pos, neg, middle"

    public AxialPillarBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(TYPE, DynamicConnectionState.SOLO)
                .setValue(AXIS, Direction.Axis.Z));
    }

    @Override
    public @NotNull BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        /*
        To improve performance, state-changing update logic only executes when a block is placed down next to an adjacent one.
         */
        return ConnectedModelInterface.AxialPillarStateForPlacement(context, this, AXIS, TYPE);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, AXIS);
    }
}