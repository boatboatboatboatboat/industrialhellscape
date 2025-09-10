package net.boat.industrialhellscape.block.special_blocks;

import net.boat.industrialhellscape.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

//INFO:
//-----
// This block, when interacted with an eligible tool, will toggle its block-state, which changes the block model.
// The block models have different texture variations or CTM connected texture modes that can be enabled which are not
// worth registering dedicated blocks for.
// Tools that can rotate the block are ones tagged by this mod with the item tag IH_COMPATIBLE_TOOLS. Currently consisting of tagged pickaxes, "wrenches", and the mod's HAVEN Tool

// AbstractGlassBlock enables internal face culling so transparent blocks of this block class can have internal face culling (E.G. Grates)
// Otherwise, this block class functions no different than if it uses the normal vanilla Block class

public class SimpleTextureToggleBlock extends AbstractGlassBlock {

    public static final BooleanProperty ALT_TEXTURE = BooleanProperty.create("alt_texture");

    public SimpleTextureToggleBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ALT_TEXTURE, false));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = this.defaultBlockState();
        return state;
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        boolean playerHasTool = pPlayer.getMainHandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS) || pPlayer.getOffhandItem().is(ModTags.Items.IH_COMPATIBLE_TOOLS);

        if(playerHasTool) { //If player has tool, change the block texture orientation to the other value
            pState = pState.cycle(ALT_TEXTURE);  //Cycles from vertical and horizontal block texture variants
            pLevel.setBlock(pPos, pState, 2);

            pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                    SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundSource.BLOCKS, 0.1f, 1f, 0);

            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        } else {
            return InteractionResult.PASS; //Disallow the interaction if the required tool to rotate the block texture orientation is not present.
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(ALT_TEXTURE);
    }
}