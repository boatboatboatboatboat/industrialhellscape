package net.boat.industrialhellscape.block.modded_block_classes.TextureToggleBlocks;

import net.boat.industrialhellscape.ModCommonConfig;
import net.boat.industrialhellscape.block.modded_interfaces.ToolUseInterface;
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

/*
INFO:
-----
 This block, when interacted with an eligible tool, will toggle its block-state.
 Used with ConnectedTexturesMod to separate texture connections for the same block.
 Allows tiling of the same block without them connecting into one big seamless texture.

 Keywords: Rough Rockrete, rough_rockrete, vesselplate
*/

public class SimpleTextureToggleBlock extends Block implements ToolUseInterface {
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
            if(player.isCrouching() && ModCommonConfig.crouchToChangeTexture()) {
                state = state.setValue(ALT_STATE,true);
            }
        }

        return state;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        return ToolUseInterface.simpleToolUse(stack, state, level, pos, player, ALT_STATE, 2);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ALT_STATE);
    }
    public boolean skipRendering(@NotNull BlockState pState, BlockState pAdjacentBlockState, @NotNull Direction pSide) {
        return pAdjacentBlockState.is(this) || super.skipRendering(pState, pAdjacentBlockState, pSide);
    }
}