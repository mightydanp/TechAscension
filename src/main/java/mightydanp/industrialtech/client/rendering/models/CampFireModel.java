package mightydanp.industrialtech.client.rendering.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mightydanp.industrialtech.client.helper.RenderHelper;
import mightydanp.industrialtech.common.blocks.state.CampfireStateController;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BlockFaceUV;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector4f;

import java.util.function.Function;

/**
 * Created by MightyDanp on 5/9/2021.
 */
public class CampFireModel extends Model {

    private ModelRenderer log1;
    private ModelRenderer log2;
    private ModelRenderer log3;

    private ModelRenderer log4;
    private ModelRenderer ash;

    private CampfireStateController campfireStateController;

    public CampFireModel(CampfireStateController campfireStateControllerIn) {
        super(RenderType::entitySolid);
        campfireStateController = campfireStateControllerIn;
        texWidth = 16;
        texHeight = 16; }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        //log1.render(matrixStack, iVertexBuilder, packedLight, packedOverlay);
                                                                   //minX, minY, minZ, maxX, maxY, maxZ
        //log 1
        RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(1, 0, 0, 5, 4, 16),
                new BlockFaceUV(new float[]{0, 4, 4, 8}, 0),
                new BlockFaceUV(new float[]{0, 0, 16, 4}, 0),
                new BlockFaceUV(new float[]{0, 4, 4, 8}, 0),
                new BlockFaceUV(new float[]{16, 0, 0, 4}, 0),
                new BlockFaceUV(new float[]{0, 0, 16, 4}, 0),
                new BlockFaceUV(new float[]{0, 0, 16, 4}, 0));
        //log 2
        RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(11, 0, 0, 15, 4, 16),
                new BlockFaceUV(new float[]{0, 4, 4, 8}, 0),
                new BlockFaceUV(new float[]{0, 0, 16, 4}, 0),
                new BlockFaceUV(new float[]{0, 4, 4, 8}, 0),
                new BlockFaceUV(new float[]{16, 0, 0, 4}, 0),
                new BlockFaceUV(new float[]{0, 0, 16, 4}, 0),
                new BlockFaceUV(new float[]{0, 0, 16, 4}, 0));

        //log 3
        RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(0, 3, 11, 16, 7, 15),
                new BlockFaceUV(new float[]{16, 0, 0, 4}, 0),
                new BlockFaceUV(new float[]{0, 4, 4, 8}, 0),
                new BlockFaceUV(new float[]{0, 0, 16, 4}, 0),
                new BlockFaceUV(new float[]{0, 4, 4, 8}, 0),
                new BlockFaceUV(new float[]{0, 0, 16, 4}, 180),
                new BlockFaceUV(new float[]{0, 0, 16, 4}, 0));

        //log 4
        RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(0, 3, 1, 16, 7, 5),
                new BlockFaceUV(new float[]{0, 0, 16, 4}, 0),
                new BlockFaceUV(new float[]{0, 4, 4, 8}, 0),
                new BlockFaceUV(new float[]{16, 0, 0, 4}, 0),
                new BlockFaceUV(new float[]{0, 4, 4, 8}, 0),
                new BlockFaceUV(new float[]{0, 0, 16, 4}, 180),
                new BlockFaceUV(new float[]{0, 0, 16, 4}, 0));

        //ash
        RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(5, 0, 0, 11, 1, 16),
                new BlockFaceUV(new float[]{0, 15, 6, 16}, 0),
                new BlockFaceUV(new float[]{0, 0, 0, 0}, 0),
                new BlockFaceUV(new float[]{10, 15, 16, 16}, 0),
                new BlockFaceUV(new float[]{0, 0, 0, 0}, 0),
                new BlockFaceUV(new float[]{0, 8, 16, 14}, 0),
                new BlockFaceUV(new float[]{0, 8, 16, 14}, 0));
    }


/*
    public void drawTextureQuad(AxisAlignedBB axisAlignedBB, Vector4f north, Vector4f east, Vector4f south, Vector4f west, Vector4f top, Vector4f bottom){
        ModelRenderer.PositionTextureVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ, minU, minV);
        ModelRenderer.PositionTextureVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ, minU, minV);
        ModelRenderer.PositionTextureVertex(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ, 0.0F, 0.0F);
        ModelRenderer.PositionTextureVertex(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ, 0.0F, 0.0F);
    }
    */
        //uv is x, y                                                                                                                       u,   v
        //ModelRenderer.PositionTextureVertex modelrenderer$positiontexturevertex7 = new ModelRenderer.PositionTextureVertex(x, y, z, 0.0F, 0.0F);
}
