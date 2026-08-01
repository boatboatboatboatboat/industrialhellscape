package net.boat.industrialhellscape.block.modded_block_classes.RailingBlocks;

import net.boat.industrialhellscape.block.modded_block_state_properties.DynamicConnectionState;
import net.boat.industrialhellscape.block.modded_interfaces.ConnectedModelInterface;
import net.boat.industrialhellscape.block.modded_interfaces.ToolUseInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//INFO:
//-----
// Modified from the mod Create Deco. Based on Catwalk Railing block class code (CC0 license)
// 4 boolean block state properties corresponding to cardinal directions and whether or not a model will be placed at that location inside
// the block boundaries.
// The block-state .json file is in a multipart format, like sea pickles.
// Multiple placements inside that block to occupy vacant directions is supported. datagen/ModBlockLootTableProvider has a method to datagen the appropriate loot table.
//This subclass adds a blockstate property that allows this block to stack on top of itself, changing the model if its the bottom, middle, or top segment

public class ConnectedStackingEdgeBlock extends RailingBlock implements SimpleWaterloggedBlock, ToolUseInterface, ConnectedModelInterface {
    private double RAILING_THICKNESS;
    private double RAILING_COLLISION_SHAPE_BASE;

    public static final EnumProperty<DynamicConnectionState> TYPE = EnumProperty.create("type", DynamicConnectionState.class); //Custom enum. "TYPE" is used to store enum value of "solo, pos, neg, middle"
    //N,S,E,W boolean blockstates already defined in superclass

    public ConnectedStackingEdgeBlock(Properties pProperties, double railingThickness, double railingCollisionShapeBase) {
        super(pProperties, railingThickness, railingCollisionShapeBase);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(NORTH_FENCE, false)
                .setValue(SOUTH_FENCE, false)
                .setValue(EAST_FENCE,  false)
                .setValue(WEST_FENCE,  false)
                .setValue(BlockStateProperties.WATERLOGGED, false)
                .setValue(TYPE, DynamicConnectionState.SOLO)
        );

    }

    @Nullable
    @Override
    public BlockState getStateForPlacement (BlockPlaceContext pContext) {

        Level level = pContext.getLevel();
        BlockPos position = pContext.getClickedPos();
        Direction facing = pContext.getHorizontalDirection(); //.getOpposite(); //Makes more sense to not get direction opposite of player facing for these types of blocks.
        FluidState fluid = level.getFluidState(position);
        Block clickedBlock = level.getBlockState(position).getBlock();
        BlockState state = level.getBlockState(position); //Get the current block-state of the RailingBlock (which should already be there)
        boolean isRailingBlock = clickedBlock instanceof ConnectedStackingEdgeBlock;
        BlockState getPositiveState = ConnectedModelInterface.getStateRelativeTop(level, position);
        BlockState getNegativeState = ConnectedModelInterface.getStateRelativeBottom(level, position);

        if(isRailingBlock) { //If there is a RailingBlock at the location of placement - THIS OVERRIDES BLOCK PLACEMENT ON TOP OF FORMER BLOCK PLS PLS PLS FIX
            switch(facing) { //Assign true to the property corresponding with the direction player is facing to place a new railing in that direction next to existing ones
                case NORTH -> state = state.setValue(NORTH_FENCE, true).setValue(TYPE, getStackingRailType(state, getPositiveState, getNegativeState, NORTH_FENCE));
                case SOUTH -> state = state.setValue(SOUTH_FENCE, true).setValue(TYPE, getStackingRailType(state, getPositiveState, getNegativeState, SOUTH_FENCE));
                case EAST -> state = state.setValue(EAST_FENCE, true).setValue(TYPE, getStackingRailType(state, getPositiveState, getNegativeState, EAST_FENCE));
                case WEST -> state = state.setValue(WEST_FENCE, true).setValue(TYPE, getStackingRailType(state, getPositiveState, getNegativeState, WEST_FENCE));
            }

            state.setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
            return state;

        } else { //Fallback. If there is NOT a detected RailingBlock at this location.
            return defaultBlockState() //get a new "blank slate" (the default block slate with no fences defined earlier)
                    .setValue(NORTH_FENCE, (facing == Direction.NORTH)) //Set value based on where player is currently looking
                    .setValue(SOUTH_FENCE, (facing == Direction.SOUTH))
                    .setValue(EAST_FENCE, (facing == Direction.EAST))
                    .setValue(WEST_FENCE, (facing == Direction.WEST))
                    .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER); //Set waterlogged status also
        }
    }

    @Override
    protected void createBlockStateDefinition (StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TYPE);
    }
}