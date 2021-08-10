package mightydanp.industrialtech.client.rendering.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mightydanp.industrialtech.api.client.helper.RenderHelper;
import mightydanp.industrialtech.common.tileentities.CampfireTileEntityOverride;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BlockFaceUV;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3f;

/**
 * Created by MightyDanp on 7/2/2021.
 */
public class CookingSlabModel extends Model {

    private final CampfireTileEntityOverride campfireTileEntityOverride;

    public CookingSlabModel(CampfireTileEntityOverride campfireTileEntityOverrideIn) {
        super(RenderType::entitySolid);
        campfireTileEntityOverride = campfireTileEntityOverrideIn;
        texWidth = 16;
        texHeight = 16;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {


        if (campfireTileEntityOverride != null) {
            matrixStack.pushPose();
            ///22.5
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(0, 0, 17, 4, 7, 21),
                    new BlockFaceUV(new float[]{0, 0, 4, 7}, 0),
                    new BlockFaceUV(new float[]{0, 0, 4, 7}, 0),
                    new BlockFaceUV(new float[]{0, 0, 4, 7}, 0),
                    new BlockFaceUV(new float[]{0, 0, 4, 7}, 0),
                    new BlockFaceUV(new float[]{0, 0, 4, 4}, 0),
                    new BlockFaceUV(new float[]{0, 0, 4, 4}, 0));
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(-22.5F));
            matrixStack.popPose();

            matrixStack.pushPose();
            //22.5
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(5, 0, 0, 9, 7, 4),
                    new BlockFaceUV(new float[]{0, 0, 4, 7}, 0),
                    new BlockFaceUV(new float[]{0, 0, 4, 7}, 0),
                    new BlockFaceUV(new float[]{0, 0, 4, 7}, 0),
                    new BlockFaceUV(new float[]{0, 0, 4, 7}, 0),
                    new BlockFaceUV(new float[]{0, 0, 4, 4}, 0),
                    new BlockFaceUV(new float[]{0, 0, 4, 4}, 0));
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(-22.5F));
            matrixStack.popPose();

            matrixStack.pushPose();
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(10, 7, 15, 12, 8, 16),
                    new BlockFaceUV(new float[]{12, 15, 10, 16}, 0),
                    new BlockFaceUV(new float[]{11, 15, 12, 16}, 90),
                    new BlockFaceUV(new float[]{10, 15, 12, 16}, 0),
                    new BlockFaceUV(new float[]{10, 15, 11, 16}, 270),
                    new BlockFaceUV(new float[]{10, 15, 12, 16}, 0),
                    new BlockFaceUV(new float[]{10, 16, 12, 15}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(1, 7, 7, 2, 8, 12),
                    new BlockFaceUV(new float[]{2, 7, 1, 8}, 0),
                    new BlockFaceUV(new float[]{1, 7, 2, 12}, 90),
                    new BlockFaceUV(new float[]{1, 11, 2, 12}, 0),
                    new BlockFaceUV(new float[]{1, 7, 2, 12}, 270),
                    new BlockFaceUV(new float[]{1, 7, 2, 12}, 0),
                    new BlockFaceUV(new float[]{1, 12, 2, 7}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(14, 7, 11, 15, 8, 14),
                    new BlockFaceUV(new float[]{15, 11, 14, 12}, 0),
                    new BlockFaceUV(new float[]{14, 11, 15, 14}, 90),
                    new BlockFaceUV(new float[]{14, 13, 15, 14}, 0),
                    new BlockFaceUV(new float[]{14, 11, 15, 14}, 270),
                    new BlockFaceUV(new float[]{14, 11, 15, 14}, 0),
                    new BlockFaceUV(new float[]{14, 14, 15, 11}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(14, 7, 4, 15, 8, 9),
                    new BlockFaceUV(new float[]{15, 4, 14, 5}, 0),
                    new BlockFaceUV(new float[]{14, 4, 15, 9}, 90),
                    new BlockFaceUV(new float[]{14, 8, 15, 9}, 0),
                    new BlockFaceUV(new float[]{14, 4, 15, 9}, 270),
                    new BlockFaceUV(new float[]{14, 4, 15, 9}, 0),
                    new BlockFaceUV(new float[]{14, 9, 15, 4}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(1, 7, 4, 2, 8, 6),
                    new BlockFaceUV(new float[]{2, 4, 1, 5}, 0),
                    new BlockFaceUV(new float[]{1, 4, 2, 6}, 90),
                    new BlockFaceUV(new float[]{1, 5, 2, 6}, 0),
                    new BlockFaceUV(new float[]{1, 4, 2, 6}, 270),
                    new BlockFaceUV(new float[]{1, 4, 2, 6}, 0),
                    new BlockFaceUV(new float[]{1, 6, 2, 4}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(2, 7, 3, 3, 8, 13),
                    new BlockFaceUV(new float[]{3, 3, 2, 4}, 0),
                    new BlockFaceUV(new float[]{2, 3, 3, 13}, 90),
                    new BlockFaceUV(new float[]{2, 12, 3, 13}, 0),
                    new BlockFaceUV(new float[]{2, 3, 3, 13}, 270),
                    new BlockFaceUV(new float[]{2, 3, 3, 13}, 0),
                    new BlockFaceUV(new float[]{2, 13, 3, 3}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(13, 7, 2, 14, 8, 15),
                    new BlockFaceUV(new float[]{14, 2, 13, 3}, 0),
                    new BlockFaceUV(new float[]{13, 2, 14, 15}, 90),
                    new BlockFaceUV(new float[]{13, 14, 14, 15}, 0),
                    new BlockFaceUV(new float[]{13, 2, 14, 15}, 270),
                    new BlockFaceUV(new float[]{13, 2, 14, 15}, 0),
                    new BlockFaceUV(new float[]{13, 15, 14, 2}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(7, 7, 2, 8, 8, 16),
                    new BlockFaceUV(new float[]{8, 2, 7, 3}, 0),
                    new BlockFaceUV(new float[]{7, 2, 8, 16}, 90),
                    new BlockFaceUV(new float[]{7, 15, 8, 16}, 0),
                    new BlockFaceUV(new float[]{7, 2, 8, 16}, 270),
                    new BlockFaceUV(new float[]{7, 2, 8, 16}, 0),
                    new BlockFaceUV(new float[]{7, 16, 8, 2}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(3, 7, 2, 4, 8, 16),
                    new BlockFaceUV(new float[]{4, 2, 3, 3}, 0),
                    new BlockFaceUV(new float[]{3, 2, 4, 16}, 90),
                    new BlockFaceUV(new float[]{3, 15, 4, 16}, 0),
                    new BlockFaceUV(new float[]{3, 2, 4, 16}, 270),
                    new BlockFaceUV(new float[]{3, 2, 4, 16}, 0),
                    new BlockFaceUV(new float[]{3, 16, 4, 2}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(12, 7, 1, 13, 8, 16),
                    new BlockFaceUV(new float[]{13, 1, 12, 2}, 0),
                    new BlockFaceUV(new float[]{12, 1, 13, 16}, 90),
                    new BlockFaceUV(new float[]{12, 15, 13, 16}, 0),
                    new BlockFaceUV(new float[]{12, 1, 13, 16}, 270),
                    new BlockFaceUV(new float[]{12, 1, 13, 16}, 0),
                    new BlockFaceUV(new float[]{12, 16, 13, 1}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(8, 7, 1, 9, 8, 16),
                    new BlockFaceUV(new float[]{9, 1, 8, 2}, 0),
                    new BlockFaceUV(new float[]{8, 1, 9, 16}, 90),
                    new BlockFaceUV(new float[]{8, 15, 9, 16}, 0),
                    new BlockFaceUV(new float[]{8, 1, 9, 16}, 270),
                    new BlockFaceUV(new float[]{8, 1, 9, 16}, 0),
                    new BlockFaceUV(new float[]{8, 16, 9, 1}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(4, 7, 1, 5, 8, 16),
                    new BlockFaceUV(new float[]{5, 1, 4, 2}, 0),
                    new BlockFaceUV(new float[]{4, 1, 5, 16}, 90),
                    new BlockFaceUV(new float[]{4, 15, 5, 16}, 0),
                    new BlockFaceUV(new float[]{4, 1, 5, 16}, 270),
                    new BlockFaceUV(new float[]{4, 1, 5, 16}, 0),
                    new BlockFaceUV(new float[]{4, 16, 5, 1}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(6, 7, 1, 7, 8, 14),
                    new BlockFaceUV(new float[]{7, 1, 6, 2}, 0),
                    new BlockFaceUV(new float[]{6, 1, 7, 14}, 90),
                    new BlockFaceUV(new float[]{6, 13, 7, 14}, 0),
                    new BlockFaceUV(new float[]{6, 1, 7, 14}, 270),
                    new BlockFaceUV(new float[]{6, 1, 7, 14}, 0),
                    new BlockFaceUV(new float[]{6, 14, 7, 1}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(9, 7, 0, 12, 8, 15),
                    new BlockFaceUV(new float[]{12, 0, 9, 1}, 0),
                    new BlockFaceUV(new float[]{11, 0, 12, 15}, 90),
                    new BlockFaceUV(new float[]{9, 14, 12, 15}, 0),
                    new BlockFaceUV(new float[]{9, 0, 10, 15}, 270),
                    new BlockFaceUV(new float[]{9, 0, 12, 15}, 0),
                    new BlockFaceUV(new float[]{9, 15, 12, 0}, 0));

            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth, new AxisAlignedBB(5, 7, 0, 6, 8, 15),
                    new BlockFaceUV(new float[]{6, 0, 5, 1}, 0),
                    new BlockFaceUV(new float[]{5, 0, 6, 15}, 90),
                    new BlockFaceUV(new float[]{5, 14, 6, 15}, 0),
                    new BlockFaceUV(new float[]{5, 0, 6, 15}, 270),
                    new BlockFaceUV(new float[]{5, 0, 6, 15}, 0),
                    new BlockFaceUV(new float[]{5, 15, 6, 0}, 0));
            matrixStack.popPose();
        }
    }
}
