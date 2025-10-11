package net.boat.industrialhellscape.block.modded_block_classes;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//INFO:
//-----
// This block, when placed next to the horizontal (wall) surface of another block, will remain unchanged from its default state.
// If placed vertically (on top of another block), it will change to its alternative texture block-state.

// Extends this mod's SimpleTextureToggleBlock, that which extends the vanilla AbstractGlassBlock, see SimpleTextureToggleBlock
// For details

public class DirectionalTextureToggleBlock extends SimpleTextureToggleBlock{
    public DirectionalTextureToggleBlock(Properties pProperties) {
        super(pProperties);
    }

    @Nullable
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext pContext) {
        BlockState state = this.defaultBlockState();

        Direction.Axis axis = pContext.getClickedFace().getAxis();
        boolean faceIsHorizontal = ( axis == Direction.Axis.X ) || ( axis == Direction.Axis.Z );

        if(faceIsHorizontal) {
            state = state.setValue(ALT_STATE, false); //HORIZONTAL
        } else {
            state = state.setValue(ALT_STATE, true); //VERTICAL (NOT HORIZONTAL)
        }

        return state;
    }
}
