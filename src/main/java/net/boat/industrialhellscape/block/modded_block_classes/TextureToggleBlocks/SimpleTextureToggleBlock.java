package net.boat.industrialhellscape.block.modded_block_classes.TextureToggleBlocks;

import net.boat.industrialhellscape.block.modded_interfaces.ToolUseCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//INFO:
//-----
// This block, when interacted with an eligible tool, will toggle its block-state, which changes the block model.
// The block models have different texture variations or CTM connected texture modes that can be enabled which are not
// worth registering dedicated blocks for.
// Tools that can rotate the block are ones tagged by this mod with the item tag IH_COMPATIBLE_TOOLS. Currently consisting of tagged pickaxes, "wrenches", and the mod's HAVEN Tool

// skipRendering is for transparent blocks such as grates so they don't show their block sides internally.

public class SimpleTextureToggleBlock extends Block {

    public static final BooleanProperty ALT_STATE = BooleanProperty.create("alt_state");

    public SimpleTextureToggleBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ALT_STATE, false));
    }

    @Nullable
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext pContext) {
        Player player = pContext.getPlayer();
        BlockState state = this.defaultBlockState();

        if(player != null) {
            if(player.isCrouching()) {
                state = state.setValue(ALT_STATE,true);
            }
        }

        return state;
    }

    public @Nonnull InteractionResult use(@Nonnull BlockState pState, @Nonnull Level pLevel, @Nonnull BlockPos pPos, Player pPlayer, @Nonnull InteractionHand pHand, @Nonnull BlockHitResult pHit) {
        //See modded interface ToolUseCapability for the list of methods used to handle common player/block interactions in this mod
        return ToolUseCapability.SimpleToolInteract(ALT_STATE, pPlayer, pState, pLevel, pPos);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ALT_STATE);
    }

    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
        return pAdjacentBlockState.is(this) ? true : super.skipRendering(pState, pAdjacentBlockState, pSide);
    }
}