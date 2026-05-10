package net.boat.industrialhellscape.block.modded_block_classes.TextureToggleBlocks;

import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

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

    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if(stack.is(ModTags.Items.IH_COMPATIBLE_TOOLS)) {
            state = state.cycle(ALT_STATE);
            level.setBlock(pos, state, 2);
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ALT_STATE);
    }

    public boolean skipRendering(@NotNull BlockState pState, BlockState pAdjacentBlockState, @NotNull Direction pSide) {
        return pAdjacentBlockState.is(this) || super.skipRendering(pState, pAdjacentBlockState, pSide);
    }
}