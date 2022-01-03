package mightydanp.industrialtech.api.client.rendering.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mightydanp.industrialtech.api.client.helper.RenderHelper;
import mightydanp.industrialtech.api.common.tileentities.HoleTileEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BlockFaceUV;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3f;

import java.awt.*;

/**
 * Created by MightyDanp on 10/12/2021.
 */
public class ResinHoleModel extends Model {

    private final HoleTileEntity holeTileEntity;

    public ResinHoleModel(HoleTileEntity holeTileEntityIn) {
        super(RenderType::entitySolid);
        holeTileEntity = holeTileEntityIn;
        texWidth = 16;
        texHeight = 16;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if (holeTileEntity.getLevel() != null) {
            matrixStack.pushPose();
            //resin_0
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth,
                    new AxisAlignedBB(7, 7, 16, 9, 10, 16.5),
                    new BlockFaceUV(new float[]{9, 6, 7, 9}, 0),
                    new BlockFaceUV(new float[]{8, 6, 9, 9}, 0),
                    new BlockFaceUV(new float[]{7, 6, 9, 9}, 0),
                    new BlockFaceUV(new float[]{7, 6, 9, 9}, 0),
                    new BlockFaceUV(new float[]{7, 6, 9, 7}, 0),
                    new BlockFaceUV(new float[]{7, 9, 9, 8}, 0));

            //resin_1
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth,
                    new AxisAlignedBB(6, 7, 16, 7, 9, 16.5),
                    new BlockFaceUV(new float[]{7, 7, 6, 9}, 0),
                    new BlockFaceUV(new float[]{6, 7, 7, 9}, 0),
                    new BlockFaceUV(new float[]{6, 7, 7, 9}, 0),
                    new BlockFaceUV(new float[]{6, 7, 7, 9}, 0),
                    new BlockFaceUV(new float[]{6, 7, 7, 8}, 0),
                    new BlockFaceUV(new float[]{6, 9, 7, 8}, 0));

            //resin_2
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth,
                    new AxisAlignedBB(9, 3.99, 16, 10.01, 9, 16.5),
                    new BlockFaceUV(new float[]{10, 7, 9, 12}, 0),
                    new BlockFaceUV(new float[]{9, 7, 10, 12}, 0),
                    new BlockFaceUV(new float[]{9, 7, 10, 12}, 0),
                    new BlockFaceUV(new float[]{9, 7, 10, 12}, 0),
                    new BlockFaceUV(new float[]{9, 7, 10, 8}, 0),
                    new BlockFaceUV(new float[]{9, 12, 10, 11}, 0));

            //resin_3
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth,
                    new AxisAlignedBB(8, 6, 16, 9, 7, 16.5),
                    new BlockFaceUV(new float[]{9, 9, 8, 10}, 0),
                    new BlockFaceUV(new float[]{8, 9, 9, 10}, 0),
                    new BlockFaceUV(new float[]{8, 9, 9, 10}, 0),
                    new BlockFaceUV(new float[]{8, 9, 9, 10}, 0),
                    new BlockFaceUV(new float[]{8, 9, 9, 10}, 0),
                    new BlockFaceUV(new float[]{8, 10, 9, 9}, 0));

            //resin_4
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth,
                    new AxisAlignedBB(8, 2, 16, 9, 4, 16.5),
                    new BlockFaceUV(new float[]{9, 12, 8, 14}, 0),
                    new BlockFaceUV(new float[]{8, 12, 9, 14}, 0),
                    new BlockFaceUV(new float[]{8, 12, 9, 14}, 0),
                    new BlockFaceUV(new float[]{8, 12, 9, 14}, 0),
                    new BlockFaceUV(new float[]{8, 12, 9, 13}, 0),
                    new BlockFaceUV(new float[]{8, 14, 9, 13}, 0));

            //resin_5
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth,
                    new AxisAlignedBB(10, 1, 16, 11, 4, 16.5),
                    new BlockFaceUV(new float[]{11, 12, 10, 15}, 0),
                    new BlockFaceUV(new float[]{10, 12, 11, 15}, 0),
                    new BlockFaceUV(new float[]{10, 12, 11, 15}, 0),
                    new BlockFaceUV(new float[]{10, 12, 11, 15}, 0),
                    new BlockFaceUV(new float[]{10, 12, 11, 13}, 0),
                    new BlockFaceUV(new float[]{10, 15, 11, 14}, 0));

            //resin_6
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, red, green, blue, alpha, texHeight, texWidth,
                    new AxisAlignedBB(7, 1, 16, 8, 2, 16.5),
                    new BlockFaceUV(new float[]{8, 14, 7, 15}, 0),
                    new BlockFaceUV(new float[]{7, 14, 8, 15}, 0),
                    new BlockFaceUV(new float[]{7, 14, 8, 15}, 0),
                    new BlockFaceUV(new float[]{7, 14, 8, 15}, 0),
                    new BlockFaceUV(new float[]{7, 14, 8, 15}, 0),
                    new BlockFaceUV(new float[]{7, 15, 8, 14}, 0));
            matrixStack.popPose();
        }
    }
}