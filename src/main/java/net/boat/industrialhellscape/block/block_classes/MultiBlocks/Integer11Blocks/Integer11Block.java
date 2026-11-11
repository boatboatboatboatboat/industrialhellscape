package net.boat.industrialhellscape.block.block_classes.MultiBlocks.Integer11Blocks;

import net.boat.industrialhellscape.block.block_classes.MultiBlocks.BaseIntegerMultiBlock;
import net.boat.industrialhellscape.block.block_interfaces.MultiBlockPlacementInterface;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.common.extensions.IBlockExtension;

public class Integer11Block extends BaseIntegerMultiBlock implements MultiBlockPlacementInterface, IBlockExtension {

//    // Additional Per-Instance Constructor Fields
//    public final int[][] multiBlockPlacementMatrix; //not static, the placement matrix is unique to many kinds of blocks. Final once initialized

    // Additional BlockState Properties
    public static final IntegerProperty PART = IntegerProperty.create("part",0, 10);

    public Integer11Block(Properties pProperties, int[][] multiBlockPlacementMatrix) {
        super(pProperties, multiBlockPlacementMatrix, PART);
        this.multiBlockPlacementMatrix = multiBlockPlacementMatrix;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, 0)
                .setValue(FACING, Direction.NORTH)
        );
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(PART, FACING);
    }
}
