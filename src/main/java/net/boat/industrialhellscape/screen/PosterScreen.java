package net.boat.industrialhellscape.screen;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class PosterScreen extends Screen {
    public ResourceLocation TEXTURE;

    private final int imageWidth, imageHeight;

    private int leftPos, topPos;

    public PosterScreen(Component pTitle, String textureName, int imageWidth, int imageHeight) {
        super(pTitle);

        TEXTURE = new ResourceLocation(IndustrialHellscape.MOD_ID, "textures/gui/" + textureName);
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
    }

    protected void init() {
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(graphics);
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        super.render(graphics, mouseX, mouseY, partialTicks);
    }
}
