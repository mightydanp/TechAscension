package mightydanp.industrialtech.api.client.rendering.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mightydanp.industrialtech.api.client.helper.RenderHelper;
import mightydanp.industrialtech.api.common.crafting.recipe.HoleRecipe;
import mightydanp.industrialtech.api.common.crafting.recipe.Recipes;
import mightydanp.industrialtech.api.common.tileentities.HoleTileEntity;
import mightydanp.industrialtech.common.crafting.recipe.ModRecipes;
import mightydanp.industrialtech.common.tileentities.CampfireTileEntityOverride;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BlockFaceUV;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.AxisAlignedBB;

import java.awt.*;
import java.util.Optional;

/**
 * Created by MightyDanp on 10/11/2021.
 */
public class HoleModel extends Model {

    private final HoleTileEntity holeTileEntity;

    public HoleModel(HoleTileEntity holeTileEntityIn) {
        super(RenderType::entitySolid);
        holeTileEntity = holeTileEntityIn;
        texWidth = 16;
        texHeight = 16;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        if(holeTileEntity.getLevel() != null) {
            Color holeColor = Color.decode(String.valueOf(holeTileEntity.holeColor));

            //hole_0
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, holeColor.getRed(), holeColor.getGreen(), holeColor.getBlue(), alpha, texHeight, texWidth,
                    new AxisAlignedBB(6, 11, 16, 10, 12, 0.25),
                    new BlockFaceUV(new float[]{10, 4, 6, 5}, 0),
                    new BlockFaceUV(new float[]{9, 4, 10, 5}, 0),
                    new BlockFaceUV(new float[]{6, 4, 10, 5}, 0),
                    new BlockFaceUV(new float[]{6, 4, 7, 5}, 0),
                    new BlockFaceUV(new float[]{6, 4, 10, 5}, 0),
                    new BlockFaceUV(new float[]{6, 5, 10, 4}, 0));

            //hole_1
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, holeColor.getRed(), holeColor.getGreen(), holeColor.getBlue(), alpha, texHeight, texWidth,
                    new AxisAlignedBB(5, 6, 16, 6, 11, 0.25),
                    new BlockFaceUV(new float[]{6, 5, 5, 9}, 0),
                    new BlockFaceUV(new float[]{5, 5, 6, 9}, 0),
                    new BlockFaceUV(new float[]{5, 5, 6, 9}, 0),
                    new BlockFaceUV(new float[]{5, 5, 6, 9}, 0),
                    new BlockFaceUV(new float[]{5, 5, 6, 9}, 0),
                    new BlockFaceUV(new float[]{5, 9, 6, 8}, 0));

            //hole_2
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, holeColor.getRed(), holeColor.getGreen(), holeColor.getBlue(), alpha, texHeight, texWidth,
                    new AxisAlignedBB(10, 6, 16, 11, 11, 0.25),
                    new BlockFaceUV(new float[]{11, 5, 10, 9}, 0),
                    new BlockFaceUV(new float[]{10, 5, 11, 9}, 0),
                    new BlockFaceUV(new float[]{10, 5, 11, 9}, 0),
                    new BlockFaceUV(new float[]{10, 5, 11, 9}, 0),
                    new BlockFaceUV(new float[]{10, 5, 11, 6}, 0),
                    new BlockFaceUV(new float[]{10, 9, 11, 8}, 0));

            //hole_3
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, holeColor.getRed(), holeColor.getGreen(), holeColor.getBlue(), alpha, texHeight, texWidth,
                    new AxisAlignedBB(6, 4, 16, 10, 5, 0.25),
                    new BlockFaceUV(new float[]{10, 10, 6, 11}, 0),
                    new BlockFaceUV(new float[]{9, 10, 10, 11}, 0),
                    new BlockFaceUV(new float[]{6, 10, 10, 11}, 0),
                    new BlockFaceUV(new float[]{6, 10, 7, 11}, 0),
                    new BlockFaceUV(new float[]{6, 10, 10, 11}, 0),
                    new BlockFaceUV(new float[]{6, 11, 10, 10}, 0));

            //hole_4
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, holeColor.getRed(), holeColor.getGreen(), holeColor.getBlue(), alpha, texHeight, texWidth,
                    new AxisAlignedBB(6, 4, 16, 10, 5, 0.25),
                    new BlockFaceUV(new float[]{7, 9, 6, 10}, 0),
                    new BlockFaceUV(new float[]{6, 9, 7, 10}, 0),
                    new BlockFaceUV(new float[]{6, 9, 7, 10}, 0),
                    new BlockFaceUV(new float[]{6, 9, 7, 10}, 0),
                    new BlockFaceUV(new float[]{6, 9, 7, 10}, 0),
                    new BlockFaceUV(new float[]{6, 10, 7, 9}, 0));

            //hole_5
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, holeColor.getRed(), holeColor.getGreen(), holeColor.getBlue(), alpha, texHeight, texWidth,
                    new AxisAlignedBB(9.01, 5, 16, 10, 6, 0.25),
                    new BlockFaceUV(new float[]{10, 9, 9, 10}, 0),
                    new BlockFaceUV(new float[]{9, 9, 10, 10}, 0),
                    new BlockFaceUV(new float[]{9, 9, 10, 10}, 0),
                    new BlockFaceUV(new float[]{9, 9, 10, 10}, 0),
                    new BlockFaceUV(new float[]{9, 9, 10, 10}, 0),
                    new BlockFaceUV(new float[]{9, 10, 10, 9}, 0));

            //hole_6
            RenderHelper.renderUVCube(matrixStack, iVertexBuilder, packedLight, packedOverlay, holeColor.getRed(), holeColor.getGreen(), holeColor.getBlue(), alpha, texHeight, texWidth,
                    new AxisAlignedBB(6, 5, 15.99, 10, 11, 0.08),
                    new BlockFaceUV(new float[]{10, 5, 6, 10}, 0),
                    new BlockFaceUV(new float[]{6, 5, 10, 10}, 0),
                    new BlockFaceUV(new float[]{6, 5, 10, 10}, 0),
                    new BlockFaceUV(new float[]{6, 5, 10, 10}, 0),
                    new BlockFaceUV(new float[]{6, 5, 10, 6}, 0),
                    new BlockFaceUV(new float[]{6, 10, 10, 9}, 0));
        }
    }
}
