package mightydanp.industrialtech.client.rendering.tileentities;

import com.mojang.blaze3d.matrix.MatrixStack;
import mightydanp.industrialtech.common.tileentities.CampfireTileEntityOverride;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;

/**
 * Created by MightyDanp on 5/6/2021.
 */
public class CampfireTileEntityRenderer extends TileEntityRenderer<CampfireTileEntityOverride> {
    public static final RenderMaterial SHELL_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/base"));
    public static final RenderMaterial ACTIVE_SHELL_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/cage"));
    public static final RenderMaterial WIND_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/wind"));
    public static final RenderMaterial VERTICAL_WIND_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/wind_vertical"));
    public static final RenderMaterial OPEN_EYE_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/open_eye"));
    public static final RenderMaterial CLOSED_EYE_TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/closed_eye"));

    private final ModelRenderer log1 = new ModelRenderer(32, 32, 0, 0);


    public CampfireTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcherIn) {
        super(tileEntityRendererDispatcherIn);
        log1.addBox(0F, 0F, 0F, 6F, 6F, 6F);
    }

    @Override
    public void render(CampfireTileEntityOverride campfireTileEntityOverride, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int x, int y) {
        //this.eye.addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.01F);
        this.log1.render(matrixStack, CLOSED_EYE_TEXTURE.buffer(iRenderTypeBuffer, RenderType::entityCutoutNoCull), x, y);
        matrixStack.popPose();

    }

}