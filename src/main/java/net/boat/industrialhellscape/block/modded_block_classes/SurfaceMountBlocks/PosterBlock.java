package net.boat.industrialhellscape.block.modded_block_classes.SurfaceMountBlocks;

import net.boat.industrialhellscape.screen.PosterScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PosterBlock extends ModelledSurfaceMountBlock implements SimpleWaterloggedBlock {
    public final String TEXTURE_NAME; //String name to locate GUI texture (Poster)
    public final int imageWidth;
    public final int imageHeight;

    public PosterBlock(Properties pProperties, VoxelShape floorHitBox, boolean ceilingSurfaceMountOnly, String textureName, int imageWidth, int imageHeight) { //The last parameter is passed during block registration to specify the GuI texture to use
        super(pProperties, floorHitBox, ceilingSurfaceMountOnly);
        this.TEXTURE_NAME = textureName;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if(level.isClientSide) {
            Minecraft.getInstance().setScreen(new PosterScreen(Component.literal("Poster"), TEXTURE_NAME, imageWidth, imageHeight));
        }
        return InteractionResult.SUCCESS;
    }
}


