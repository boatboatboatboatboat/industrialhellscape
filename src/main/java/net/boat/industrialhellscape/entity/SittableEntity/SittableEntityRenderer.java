package net.boat.industrialhellscape.entity.SittableEntity;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;


public class SittableEntityRenderer extends EntityRenderer<SittableEntity> {
    public SittableEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(SittableEntity chairEntity) {
        return null;
    }

    @Override
    public boolean shouldRender(SittableEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }
}


