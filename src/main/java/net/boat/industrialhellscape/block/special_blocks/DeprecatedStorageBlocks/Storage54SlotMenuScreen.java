package net.boat.industrialhellscape.block.special_blocks.DeprecatedStorageBlocks;

import net.boat.industrialhellscape.IndustrialHellscape;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class Storage54SlotMenuScreen extends AbstractContainerScreen<Storage54SlotMenu> {
    private static final ResourceLocation TEXTURE =
            //GUI TEXTURE HERE
            new ResourceLocation(IndustrialHellscape.MOD_ID, "textures/gui/container9x6.png");

    public Storage54SlotMenuScreen(Storage54SlotMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        this.imageWidth = 176;
        this.imageHeight = 222;
        this.inventoryLabelY = 128; //54-slot GUIs are larger than <27 slot chest-like GUIs
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}