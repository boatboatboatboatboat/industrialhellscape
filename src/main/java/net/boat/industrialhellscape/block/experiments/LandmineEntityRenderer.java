package net.boat.industrialhellscape.block.experiments;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.boat.industrialhellscape.IndustrialHellscape;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class LandmineEntityRenderer extends EntityRenderer<LandMineEntity> {

    public LandmineEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(LandMineEntity landMineEntity) {
        return ResourceLocation.fromNamespaceAndPath(IndustrialHellscape.MOD_ID,"mine/landmine.png");
    }

    public void render(LandMineEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0F, 0.5F, 0.0F);

        poseStack.mulPose(Axis.YP.rotationDegrees(-90.0F));
        poseStack.translate(-0.5F, -0.5F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

}
