package net.boat.industrialhellscape.block.modded_block_classes.TextureToggleBlocks;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

//INFO:
//-----
// SimpleTextureToggleBlock, but with waterlogging capability.

// skipRendering is for transparent blocks such as grates so they don't show their block sides internally.

public class TrussBlock extends SimpleTextureToggleBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public final boolean seeInteriorSides;

    public TrussBlock(Properties pProperties, boolean seeInteriorSides) {
        super(pProperties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(ALT_STATE, Boolean.FALSE)
                .setValue(WATERLOGGED, Boolean.FALSE));
        this.seeInteriorSides = seeInteriorSides;
    }

    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        Player player = pContext.getPlayer();
        BlockState state = this.defaultBlockState();

        if(player != null) {
            if(player.isCrouching()) {
                state = state.setValue(ALT_STATE,true);
            }
        }
        state = state.setValue(WATERLOGGED,fluidstate.getType() == Fluids.WATER); //check for waterlogging status
        return state;
    }

    public @Nonnull FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ALT_STATE, WATERLOGGED);
    }

    public boolean skipRendering(@NotNull BlockState pState, BlockState pAdjacentBlockState, @NotNull Direction pSide) {
        //Needed to see interior sides of truss structures.
        if(seeInteriorSides) {
            return !pAdjacentBlockState.is(this) && super.skipRendering(pState, pAdjacentBlockState, pSide);
        } else {
            return pAdjacentBlockState.is(this) && super.skipRendering(pState, pAdjacentBlockState, pSide);
        }
    }
}
