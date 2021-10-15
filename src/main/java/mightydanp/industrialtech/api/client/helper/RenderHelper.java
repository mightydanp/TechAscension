package mightydanp.industrialtech.api.client.helper;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.BlockFaceUV;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector4f;

/**
 * Created by MightyDanp on 5/9/2021.
 */
public class RenderHelper {

    public static void renderUVCube(MatrixStack matrixStack, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha, float texWidth, float texHeight, AxisAlignedBB cube, BlockFaceUV north, BlockFaceUV east, BlockFaceUV south, BlockFaceUV west, BlockFaceUV up, BlockFaceUV down){
        matrixStack.pushPose();
        MatrixStack.Entry matrixStackLast = matrixStack.last();
        Matrix4f matrix4f = matrixStackLast.pose();

        Vector4f vertex = new Vector4f((float)cube.maxX / 16 - 0.5F, (float)cube.minY / 16, (float)cube.minZ / 16 - 0.5F,1.0F);
        Vector4f vertex1 = new Vector4f((float)cube.maxX / 16 - 0.5F, (float)cube.maxY / 16, (float)cube.minZ / 16 - 0.5F,1.0F);
        Vector4f vertex2 = new Vector4f((float)cube.minX / 16 - 0.5F, (float)cube.maxY / 16, (float)cube.minZ / 16 - 0.5F,1.0F);
        Vector4f vertex3 = new Vector4f((float)cube.minX / 16 - 0.5F, (float)cube.minY / 16, (float)cube.maxZ / 16 - 0.5F,1.0F);
        Vector4f vertex4 = new Vector4f((float)cube.maxX / 16 - 0.5F, (float)cube.minY / 16, (float)cube.maxZ / 16 - 0.5F,1.0F);
        Vector4f vertex5 = new Vector4f((float)cube.maxX / 16 - 0.5F, (float)cube.maxY / 16, (float)cube.maxZ / 16 - 0.5F,1.0F);
        Vector4f vertex6 = new Vector4f((float)cube.minX / 16 - 0.5F, (float)cube.maxY / 16, (float)cube.maxZ / 16 - 0.5F,1.0F);
        Vector4f vertex7 = new Vector4f((float)cube.minX / 16 - 0.5F, (float)cube.minY / 16, (float)cube.minZ / 16 - 0.5F,1.0F);


        vertex.transform(matrix4f);
        vertex1.transform(matrix4f);
        vertex2.transform(matrix4f);
        vertex3.transform(matrix4f);
        vertex4.transform(matrix4f);
        vertex5.transform(matrix4f);
        vertex6.transform(matrix4f);
        vertex7.transform(matrix4f);


        float minU = up.uvs[up.getReverseIndex(0)];
        float minV = up.uvs[up.getReverseIndex(1)];
        float maxU = up.uvs[up.getReverseIndex(2)];
        float maxV = up.uvs[up.getReverseIndex(3)];
        int one = up.getReverseIndex(0);
        int two = up.getReverseIndex(1);
        int three = up.getReverseIndex(2);
        int four = up.getReverseIndex(3);

        // Top side
        bufferIn.vertex(vertex1.x(), vertex1.y(), vertex1.z(), red, green, blue, alpha, up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 3 : 0)] / texWidth, up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 0 : 3)] / texHeight, packedOverlayIn, packedLightIn, 0, 1, 0);
        bufferIn.vertex(vertex2.x(), vertex2.y(), vertex2.z(), red, green, blue, alpha, up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 3 : 2)] / texWidth, up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 2 : 3)] / texHeight, packedOverlayIn, packedLightIn, 0, 1, 0);
        bufferIn.vertex(vertex6.x(), vertex6.y(), vertex6.z(), red, green, blue, alpha, up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 1 : 2)] / texWidth, up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 2 : 1)] / texHeight, packedOverlayIn, packedLightIn, 0, 1, 0);
        bufferIn.vertex(vertex5.x(), vertex5.y(), vertex5.z(), red, green, blue, alpha, up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 1 : 0)] / texWidth, up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 0 : 1)] / texHeight, packedOverlayIn, packedLightIn, 0, 1, 0);

        // Bottom side
        bufferIn.vertex(vertex4.x(), vertex4.y(), vertex4.z(), red, green, blue, alpha, down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 3 : 0)] / texWidth, down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 0 : 3)] / texHeight, packedOverlayIn, packedLightIn, 0, -1, 0);
        bufferIn.vertex(vertex3.x(), vertex3.y(), vertex3.z(), red, green, blue, alpha, down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 3 : 2)] / texWidth, down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 2 : 3)] / texHeight, packedOverlayIn, packedLightIn, 0, -1, 0);
        bufferIn.vertex(vertex7.x(), vertex7.y(), vertex7.z(), red, green, blue, alpha, down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 1 : 2)] / texWidth, down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 2 : 1)] / texHeight, packedOverlayIn, packedLightIn, 0, -1, 0);
        bufferIn.vertex(vertex.x(), vertex.y(), vertex.z(), red, green, blue, alpha,    down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 1 : 0)] / texWidth, down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 0 : 1)] / texHeight, packedOverlayIn, packedLightIn, 0, -1, 0);

        // West side
        bufferIn.vertex(vertex7.x(), vertex7.y(), vertex7.z(), red, green, blue, alpha, west.uvs[west.getReverseIndex(0)] / texWidth, west.uvs[west.getReverseIndex(3)] / texHeight, packedOverlayIn, packedLightIn, -1, 0, 0);
        bufferIn.vertex(vertex3.x(), vertex3.y(), vertex3.z(), red, green, blue, alpha, west.uvs[west.getReverseIndex(2)] / texWidth, west.uvs[west.getReverseIndex(3)] / texHeight, packedOverlayIn, packedLightIn, -1, 0, 0);
        bufferIn.vertex(vertex6.x(), vertex6.y(), vertex6.z(), red, green, blue, alpha, west.uvs[west.getReverseIndex(2)] / texWidth, west.uvs[west.getReverseIndex(1)] / texHeight, packedOverlayIn, packedLightIn, -1, 0, 0);
        bufferIn.vertex(vertex2.x(), vertex2.y(), vertex2.z(), red, green, blue, alpha, west.uvs[west.getReverseIndex(0)] / texWidth, west.uvs[west.getReverseIndex(1)] / texHeight, packedOverlayIn, packedLightIn, -1, 0, 0);

        // East side
        bufferIn.vertex(vertex4.x(), vertex4.y(), vertex4.z(), red, green, blue, alpha, east.uvs[east.getReverseIndex(0)] / texWidth, east.uvs[east.getReverseIndex(3)] / texHeight, packedOverlayIn, packedLightIn, 1, 0, 0);
        bufferIn.vertex(vertex.x(), vertex.y(), vertex.z(), red, green, blue, alpha,    east.uvs[east.getReverseIndex(2)] / texWidth, east.uvs[east.getReverseIndex(3)]/ texHeight, packedOverlayIn, packedLightIn, 1, 0, 0);
        bufferIn.vertex(vertex1.x(), vertex1.y(), vertex1.z(), red, green, blue, alpha, east.uvs[east.getReverseIndex(2)] / texWidth, east.uvs[east.getReverseIndex(1)] / texHeight,  packedOverlayIn, packedLightIn, 1, 0, 0);
        bufferIn.vertex(vertex5.x(), vertex5.y(), vertex5.z(), red, green, blue, alpha, east.uvs[east.getReverseIndex(0)] / texWidth, east.uvs[east.getReverseIndex(1)] / texHeight,  packedOverlayIn, packedLightIn, 1, 0, 0);

        // North side
        bufferIn.vertex(vertex3.x(), vertex3.y(), vertex3.z(), red, green, blue, alpha, north.uvs[north.getReverseIndex(0)] / texWidth, north.uvs[north.getReverseIndex(3)] / texHeight, packedOverlayIn, packedLightIn, 0, 0, 1);
        bufferIn.vertex(vertex4.x(), vertex4.y(), vertex4.z(), red, green, blue, alpha, north.uvs[north.getReverseIndex(2)] / texWidth, north.uvs[north.getReverseIndex(3)] / texHeight, packedOverlayIn, packedLightIn, 0, 0, 1);
        bufferIn.vertex(vertex5.x(), vertex5.y(), vertex5.z(), red, green, blue, alpha, north.uvs[north.getReverseIndex(2)] / texWidth, north.uvs[north.getReverseIndex(1)] / texHeight, packedOverlayIn, packedLightIn, 0, 0, 1);
        bufferIn.vertex(vertex6.x(), vertex6.y(), vertex6.z(), red, green, blue, alpha, north.uvs[north.getReverseIndex(0)]/ texWidth, north.uvs[north.getReverseIndex(1)] / texHeight, packedOverlayIn, packedLightIn, 0, 0, 1);


        // South side
        bufferIn.vertex(vertex.x(), vertex.y(), vertex.z(), red, green, blue, alpha,    south.uvs[south.getReverseIndex(0)] / texWidth, south.uvs[south.getReverseIndex(3)] / texHeight, packedOverlayIn, packedLightIn, 0, 0, -1);
        bufferIn.vertex(vertex7.x(), vertex7.y(), vertex7.z(), red, green, blue, alpha, south.uvs[south.getReverseIndex(2)] / texWidth, south.uvs[south.getReverseIndex(3)] / texHeight, packedOverlayIn, packedLightIn, 0, 0, -1);
        bufferIn.vertex(vertex2.x(), vertex2.y(), vertex2.z(), red, green, blue, alpha, south.uvs[south.getReverseIndex(2)] / texWidth, south.uvs[south.getReverseIndex(1)] / texHeight, packedOverlayIn, packedLightIn, 0, 0, -1);
        bufferIn.vertex(vertex1.x(), vertex1.y(), vertex1.z(), red, green, blue, alpha, south.uvs[south.getReverseIndex(0)] / texWidth, south.uvs[south.getReverseIndex(1)] / texHeight, packedOverlayIn, packedLightIn, 0, 0, -1);


        matrixStack.popPose();
    }
}
