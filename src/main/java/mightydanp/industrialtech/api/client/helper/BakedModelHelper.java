package mightydanp.industrialtech.api.client.helper;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

/**
 * Created by MightyDanp on 11/1/2021.
 */
public class BakedModelHelper {
    
    private static void putVertex(BakedQuadBuilder builder, double x, double y, double z, float u, float v, float r, float g, float b, float a, double normalX, double normalY, double normalZ) {
        builder.setApplyDiffuseLighting(true);
        builder.setContractUVs(true);

        Vector3d normal = new Vector3d(normalX, normalY ,normalZ);
        builder.setQuadOrientation(Direction.getNearest(normal.x, normal.y, normal.z));

        ImmutableList<VertexFormatElement> elements = builder.getVertexFormat().getElements().asList();
        for (int j = 0 ; j < elements.size() ; j++) {
            VertexFormatElement e = elements.get(j);
            switch (e.getUsage()) {
                case POSITION:
                    builder.put(j, (float) x, (float) y, (float) z, 1.0f);
                    break;
                case COLOR:
                    builder.put(j, r, g, b, a);
                    break;
                case UV:
                    switch (e.getIndex()) {
                        case 0:
                            builder.put(j, u, v);
                            break;
                        case 2:
                            builder.put(j, (short) 0, (short) 0);
                            break;
                        default:
                            builder.put(j);
                            break;
                    }
                    break;
                case NORMAL:
                    builder.put(j, (float) normal.x, (float) normal.y, (float) normal.z, 0);
                    break;
                default:
                    builder.put(j);
                    break;
            }
        }
    }

    public static BakedQuad createQuad(int red, int green, int blue, int alpha, TextureAtlasSprite sprite, Direction.Axis axis, float rotation, AxisAlignedBB cube, BlockFaceUV north, BlockFaceUV east, BlockFaceUV south, BlockFaceUV west, BlockFaceUV up, BlockFaceUV down) {
        int texWidth = sprite.getWidth();
        int texHeight = sprite.getHeight();

        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);

        Vector4f vertex = new Vector4f(new BlockPartRotation(new Vector3f((float)cube.maxX / 16, (float)cube.minY / 16, (float)cube.minZ / 16), axis == null ? Direction.Axis.X : axis, rotation, false).origin);
        Vector4f vertex1 = new Vector4f(new BlockPartRotation(new Vector3f((float)cube.maxX / 16, (float)cube.maxY / 16, (float)cube.minZ / 16), axis == null ? Direction.Axis.X : axis, rotation, false).origin);
        Vector4f vertex2 = new Vector4f(new BlockPartRotation(new Vector3f((float)cube.minX / 16, (float)cube.maxY / 16, (float)cube.minZ / 16), axis == null ? Direction.Axis.X : axis, rotation, false).origin);
        Vector4f vertex3 = new Vector4f(new BlockPartRotation(new Vector3f((float)cube.minX / 16, (float)cube.minY / 16, (float)cube.maxZ / 16), axis == null ? Direction.Axis.X : axis, rotation, false).origin);
        Vector4f vertex4 = new Vector4f(new BlockPartRotation(new Vector3f((float)cube.maxX / 16, (float)cube.minY / 16, (float)cube.maxZ / 16), axis == null ? Direction.Axis.X : axis, rotation, false).origin);
        Vector4f vertex5 = new Vector4f(new BlockPartRotation(new Vector3f((float)cube.maxX / 16, (float)cube.maxY / 16, (float)cube.maxZ / 16), axis == null ? Direction.Axis.X : axis, rotation, false).origin);
        Vector4f vertex6 = new Vector4f(new BlockPartRotation(new Vector3f((float)cube.minX / 16, (float)cube.maxY / 16, (float)cube.maxZ / 16), axis == null ? Direction.Axis.X : axis, rotation, false).origin);
        Vector4f vertex7 = new Vector4f(new BlockPartRotation(new Vector3f((float)cube.minX / 16, (float)cube.minY / 16, (float)cube.minZ / 16), axis == null ? Direction.Axis.X : axis, rotation, false).origin);

        // Top side
        putVertex(builder,vertex1.x(), vertex1.y(), vertex1.z(), up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 3 : 0)] / texWidth, up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 0 : 3)] / texHeight, red, green, blue, alpha, 0.5, 1, 0.5);
        putVertex(builder,vertex2.x(), vertex2.y(), vertex2.z(), up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 3 : 2)] / texWidth, up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 2 : 3)] / texHeight, red, green, blue, alpha, 0.5, 1, 0.5);
        putVertex(builder,vertex6.x(), vertex6.y(), vertex6.z(), up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 1 : 2)] / texWidth, up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 2 : 1)] / texHeight, red, green, blue, alpha, 0.5, 1, 0.5);
        putVertex(builder,vertex5.x(), vertex5.y(), vertex5.z(), up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 1 : 0)] / texWidth, up.uvs[up.getReverseIndex(up.rotation == 90 || up.rotation == 270 ? 0 : 1)] / texHeight, red, green, blue, alpha, 0.5, 1, 0.5);

        // Bottom side
        putVertex(builder, vertex4.x(), vertex4.y(), vertex4.z(), down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 3 : 0)] / texWidth, down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 0 : 3)] / texHeight, red, green, blue, alpha, 0.5, -1, 0.5);
        putVertex(builder, vertex3.x(), vertex3.y(), vertex3.z(), down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 3 : 2)] / texWidth, down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 2 : 3)] / texHeight, red, green, blue, alpha, 0.5, -1, 0.5);
        putVertex(builder, vertex7.x(), vertex7.y(), vertex7.z(), down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 1 : 2)] / texWidth, down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 2 : 1)] / texHeight, red, green, blue, alpha, 0.5, -1, 0.5);
        putVertex(builder, vertex.x(), vertex.y(), vertex.z(),    down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 1 : 0)] / texWidth, down.uvs[down.getReverseIndex(down.rotation == 90 || down.rotation == 270 ? 0 : 1)] / texHeight, red, green, blue, alpha, 0.5, -1, 0.5);

        // West side
        putVertex(builder, vertex7.x(), vertex7.y(), vertex7.z(), west.uvs[west.getReverseIndex(0)] / texWidth, west.uvs[west.getReverseIndex(3)] / texHeight, red, green, blue, alpha, -0.5, 0, 0.5);
        putVertex(builder, vertex3.x(), vertex3.y(), vertex3.z(), west.uvs[west.getReverseIndex(2)] / texWidth, west.uvs[west.getReverseIndex(3)] / texHeight, red, green, blue, alpha, -0.5, 0, 0.5);
        putVertex(builder, vertex6.x(), vertex6.y(), vertex6.z(), west.uvs[west.getReverseIndex(2)] / texWidth, west.uvs[west.getReverseIndex(1)] / texHeight, red, green, blue, alpha, -0.5, 0, 0.5);
        putVertex(builder, vertex2.x(), vertex2.y(), vertex2.z(), west.uvs[west.getReverseIndex(0)] / texWidth, west.uvs[west.getReverseIndex(1)] / texHeight, red, green, blue, alpha, -0.5, 0, 0.5);

        // East side
        putVertex(builder, vertex4.x(), vertex4.y(), vertex4.z(), east.uvs[east.getReverseIndex(0)] / texWidth, east.uvs[east.getReverseIndex(3)] / texHeight, red, green, blue, alpha, 1.5, 0, 0.5);
        putVertex(builder, vertex.x(), vertex.y(), vertex.z(),    east.uvs[east.getReverseIndex(2)] / texWidth, east.uvs[east.getReverseIndex(3)]/ texHeight, red, green, blue, alpha, 1.5, 0, 0.5);
        putVertex(builder, vertex1.x(), vertex1.y(), vertex1.z(), east.uvs[east.getReverseIndex(2)] / texWidth, east.uvs[east.getReverseIndex(1)] / texHeight, red, green, blue, alpha, 1.5, 0, 0.5);
        putVertex(builder, vertex5.x(), vertex5.y(), vertex5.z(), east.uvs[east.getReverseIndex(0)] / texWidth, east.uvs[east.getReverseIndex(1)] / texHeight, red, green, blue, alpha, 1.5, 0, 0.5);

        // North side
        putVertex(builder, vertex3.x(), vertex3.y(), vertex3.z(), north.uvs[north.getReverseIndex(0)] / texWidth, north.uvs[north.getReverseIndex(3)] / texHeight, red, green, blue, alpha, 0.5, 0, 1.5);
        putVertex(builder, vertex4.x(), vertex4.y(), vertex4.z(), north.uvs[north.getReverseIndex(2)] / texWidth, north.uvs[north.getReverseIndex(3)] / texHeight, red, green, blue, alpha, 0.5, 0, 1.5);
        putVertex(builder, vertex5.x(), vertex5.y(), vertex5.z(), north.uvs[north.getReverseIndex(2)] / texWidth, north.uvs[north.getReverseIndex(1)] / texHeight, red, green, blue, alpha, 0.5, 0, 1.5);
        putVertex(builder, vertex6.x(), vertex6.y(), vertex6.z(), north.uvs[north.getReverseIndex(0)]/ texWidth, north.uvs[north.getReverseIndex(1)] / texHeight, red, green, blue, alpha, 0.5, 0, 1.5);


        // South side
        putVertex(builder, vertex.x(), vertex.y(), vertex.z(),    south.uvs[south.getReverseIndex(0)] / texWidth, south.uvs[south.getReverseIndex(3)] / texHeight, red, green, blue, alpha, 0, 0, -1);
        putVertex(builder, vertex7.x(), vertex7.y(), vertex7.z(), south.uvs[south.getReverseIndex(2)] / texWidth, south.uvs[south.getReverseIndex(3)] / texHeight, red, green, blue, alpha, 0, 0, -1);
        putVertex(builder, vertex2.x(), vertex2.y(), vertex2.z(), south.uvs[south.getReverseIndex(2)] / texWidth, south.uvs[south.getReverseIndex(1)] / texHeight, red, green, blue, alpha, 0, 0, -1);
        putVertex(builder, vertex1.x(), vertex1.y(), vertex1.z(), south.uvs[south.getReverseIndex(0)] / texWidth, south.uvs[south.getReverseIndex(1)] / texHeight, red, green, blue, alpha, 0, 0, -1);

        return builder.build();
    }

}
