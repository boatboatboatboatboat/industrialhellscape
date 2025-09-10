package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

//INFO:
//-----
// This block has gravity enabled. It will fall like sand if there are no blocks below it.
// Cardinal directional placement is supported.
// Custom interaction: Upon landing, a fixed custom sound will be played.

public class FacingFallableBlock extends FallingBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public FacingFallableBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)); //Default state if placed with no player present
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction directionClicked = pContext.getHorizontalDirection(); //Gets the cardinal direction when player places new block
        BlockState state = this.defaultBlockState().setValue(FACING, directionClicked); //First, defines facing direction of the block
        return state;
    }

    @Override
    public void onLand(Level pLevel, BlockPos pPos, BlockState pState, BlockState pReplaceableState, FallingBlockEntity pFallingBlock) {
        pLevel.playSound(null, pPos, ModSounds.METALPIPEFALLINGSOUNDEFFECT.get(), SoundSource.BLOCKS,
                1f, 1f);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING); //Block's blockstates; its NSEW orientation, its connection type defined
    }


}
