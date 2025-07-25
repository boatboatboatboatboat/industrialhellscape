package net.boat.industrialhellscape.block.special_blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class InnerCornerBlock extends HorizontalDirectionalBlock{

    public static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING; //"FACING" is used to store DirectionProperty value of "north, south, east, west" //KJ

    public InnerCornerBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)); //Default state if placed with no player present
    }

    //Placement Faces the player
    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction directionClicked = pContext.getHorizontalDirection(); //Gets the cardinal direction when player places new block
        BlockState state = this.defaultBlockState().setValue(FACING, directionClicked); //First, defines facing direction of the block
        return state;
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING); //Block's blockstates; its NSEW orientation, its connection type defined
    }
}
