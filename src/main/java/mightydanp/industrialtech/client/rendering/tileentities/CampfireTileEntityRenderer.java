package mightydanp.industrialtech.client.rendering.tileentities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mightydanp.industrialtech.client.rendering.models.CampfireModel;
import mightydanp.industrialtech.common.blocks.state.CampfireStateController;
import mightydanp.industrialtech.common.tileentities.CampfireTileEntityOverride;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

/**
 * Created by MightyDanp on 5/6/2021.
 */
public class CampfireTileEntityRenderer extends TileEntityRenderer<CampfireTileEntityOverride> {
    private static final ResourceLocation campfireOffTexture = new ResourceLocation("textures/block/campfire_log.png");
    private static final ResourceLocation campfireOnTexture = new ResourceLocation("textures/block/campfire/campfire_log_lit.png");

    public CampfireTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcherIn) {
        super(tileEntityRendererDispatcherIn);
    }

    @Override
    public void render(CampfireTileEntityOverride campfireTileEntityOverride, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int combinedLight, int combinedOverlay) {
        matrixStack.pushPose();
        CampfireStateController campfireStateController = campfireTileEntityOverride.getCampfireNBT();
        Model model = new CampfireModel(campfireTileEntityOverride);

        matrixStack.translate(0.5, 0, 0.5D);

        switch (campfireStateController.getDirection()) {
            case "east":
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(90F));
                break;
            case "south":
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(180F));
                break;
            case "west":
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(270F));
                break;
        }

        IVertexBuilder renderBuffer = iRenderTypeBuffer.getBuffer(model.renderType(getCampfireOffTextureLocation()));
        model.renderToBuffer(matrixStack, renderBuffer, combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F); // white, fully opaque
        matrixStack.popPose();

        /*
        matrixStack.pushPose();
        matrixStack.scale(0.65F, 0.7F, 0.65F);
        matrixStack.translate(0.26225, (double) 0.05F, 0.2625);
        Minecraft.getInstance().getBlockRenderer().renderBlock(Blocks.FIRE.defaultBlockState(), matrixStack, iRenderTypeBuffer, combinedLight, combinedOverlay, EmptyModelData.INSTANCE);
        matrixStack.popPose();

         */
    }


    public ResourceLocation getCampfireOffTextureLocation() {
        return campfireOffTexture;
    }

    public ResourceLocation getCampfireOnTextureLocation() {
        return campfireOnTexture;
    }

}