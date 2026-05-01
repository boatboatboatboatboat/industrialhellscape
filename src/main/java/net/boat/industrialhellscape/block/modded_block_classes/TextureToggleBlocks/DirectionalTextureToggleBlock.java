package net.boat.industrialhellscape.block.modded_block_classes.TextureToggleBlocks;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//INFO:
//-----
// This block, when placed next to the horizontal (wall) surface of another block, will remain unchanged from its default state.
// If placed vertically (on top of another block), it will change to its alternative texture block-state.
// Currently used for Directional Riveted Vesselplate (three block variants: default, Rusty, Gray)

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

        if(faceIsHorizontal) { //If the block face is horizontal (facing NSEW), don't change the blockstate to the alt-texture state
            state = state.setValue(ALT_STATE, false); //HORIZONTAL
        } else { //If the block face is vertical (top or bottom), change the state
            state = state.setValue(ALT_STATE, true); //VERTICAL (NOT HORIZONTAL)
        }

        return state;
    }
}
