package mightydanp.industrialtech.api.client.helper;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.world.phys.Vec3;

/**
 * Created by MightyDanp on 11/1/2021.
 */
public class BakedModelHelper {
    private static FaceBakery faceBakery = new FaceBakery();

    private static void putVertex(BakedQuadBuilder builder,  Vec3 normal, double x, double y, double z, float u, float v, TextureAtlasSprite sprite, float r, float g, float b, float a) {
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
                            float iu = sprite.getU(u);
                            float iv = sprite.getV(v);
                            builder.put(j, iu, iv);
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

        builder.setApplyDiffuseLighting(true);
    }

    public static List<BakedQuad> createCube(float red, float green, float blue, float alpha, int tintIndex, TextureAtlasSprite sprite, Direction.Axis axis, float rotation, AABB cube, BlockFaceUV north, BlockFaceUV east, BlockFaceUV south, BlockFaceUV west, BlockFaceUV up, BlockFaceUV down) {
        int texWidth = sprite.getWidth();
        int texHeight = sprite.getHeight();
        
        List<BakedQuad> bakedQuadList = new ArrayList<>();
        Vec3 vertex0 = new Vec3((float)cube.maxX / 16, (float)cube.minY / 16, (float)cube.minZ / 16);
        Vec3 vertex1 = new Vec3((float)cube.maxX / 16, (float)cube.maxY / 16, (float)cube.minZ / 16);
        Vec3 vertex2 = new Vec3((float)cube.minX / 16, (float)cube.maxY / 16, (float)cube.minZ / 16);
        Vec3 vertex3 = new Vec3((float)cube.minX / 16, (float)cube.minY / 16, (float)cube.maxZ / 16);
        Vec3 vertex4 = new Vec3((float)cube.maxX / 16, (float)cube.minY / 16, (float)cube.maxZ / 16);
        Vec3 vertex5 = new Vec3((float)cube.maxX / 16, (float)cube.maxY / 16, (float)cube.maxZ / 16);
        Vec3 vertex6 = new Vec3((float)cube.minX / 16, (float)cube.maxY / 16, (float)cube.maxZ / 16);
        Vec3 vertex7 = new Vec3((float)cube.minX / 16, (float)cube.minY / 16, (float)cube.minZ / 16);

        if(axis != null) {
            switch (axis) {
                case X:
                if(rotation < 0.0) {
                    Vec3 translate = new Vec3(0,0.81,1.3644);

                    vertex0 = vertex0.xRot(-rotation).add(translate);
                    vertex1 = vertex1.xRot(-rotation).add(translate);
                    vertex2 = vertex2.xRot(-rotation).add(translate);
                    vertex3 = vertex3.xRot(-rotation).add(translate);
                    vertex4 = vertex4.xRot(-rotation).add(translate);
                    vertex5 = vertex5.xRot(-rotation).add(translate);
                    vertex6 = vertex6.xRot(-rotation).add(translate);
                    vertex7 = vertex7.xRot(-rotation).add(translate);
                }else{
                    Vec3 translate = new Vec3(0,0.695,0.63);
                    vertex0 = vertex0.xRot(-rotation).add(translate);
                    vertex1 = vertex1.xRot(-rotation).add(translate);
                    vertex2 = vertex2.xRot(-rotation).add(translate);
                    vertex3 = vertex3.xRot(-rotation).add(translate);
                    vertex4 = vertex4.xRot(-rotation).add(translate);
                    vertex5 = vertex5.xRot(-rotation).add(translate);
                    vertex6 = vertex6.xRot(-rotation).add(translate);
                    vertex7 = vertex7.xRot(-rotation).add(translate);
                }
                break;
                case Y:
                    if(rotation < 0.0) {
                        Vec3 translate = new Vec3(0,0,0);
                        vertex0 = vertex0.subtract(translate).yRot(-rotation);
                        vertex1 = vertex1.subtract(translate).yRot(-rotation);
                        vertex2 = vertex2.subtract(translate).yRot(-rotation);
                        vertex3 = vertex3.subtract(translate).yRot(-rotation);
                        vertex4 = vertex4.subtract(translate).yRot(-rotation);
                        vertex5 = vertex5.subtract(translate).yRot(-rotation);
                        vertex6 = vertex6.subtract(translate).yRot(-rotation);
                        vertex7 = vertex7.subtract(translate).yRot(-rotation);
                    }else{
                        Vec3 translate = new Vec3(0,0,0);
                        vertex0 = vertex0.subtract(translate).yRot(-rotation);
                        vertex1 = vertex1.subtract(translate).yRot(-rotation);
                        vertex2 = vertex2.subtract(translate).yRot(-rotation);
                        vertex3 = vertex3.subtract(translate).yRot(-rotation);
                        vertex4 = vertex4.subtract(translate).yRot(-rotation);
                        vertex5 = vertex5.subtract(translate).yRot(-rotation);
                        vertex6 = vertex6.subtract(translate).yRot(-rotation);
                        vertex7 = vertex7.subtract(translate).yRot(-rotation);
                    }
                    break;
                case Z:
                    if(rotation < 0.0) {
                        Vec3 translate = new Vec3(0.63,0.695,0);
                        vertex0 = vertex0.zRot(-rotation).add(translate);
                        vertex1 = vertex1.zRot(-rotation).add(translate);
                        vertex2 = vertex2.zRot(-rotation).add(translate);
                        vertex3 = vertex3.zRot(-rotation).add(translate);
                        vertex4 = vertex4.zRot(-rotation).add(translate);
                        vertex5 = vertex5.zRot(-rotation).add(translate);
                        vertex6 = vertex6.zRot(-rotation).add(translate);
                        vertex7 = vertex7.zRot(-rotation).add(translate);
                    }else{
                        Vec3 translate = new Vec3(1.3644,0.81,0);
                        vertex0 = vertex0.zRot(-rotation).add(translate);
                        vertex1 = vertex1.zRot(-rotation).add(translate);
                        vertex2 = vertex2.zRot(-rotation).add(translate);
                        vertex3 = vertex3.zRot(-rotation).add(translate);
                        vertex4 = vertex4.zRot(-rotation).add(translate);
                        vertex5 = vertex5.zRot(-rotation).add(translate);
                        vertex6 = vertex6.zRot(-rotation).add(translate);
                        vertex7 = vertex7.zRot(-rotation).add(translate);
                    }
                    break;
                default:
                    break;
            }
        }

        /*
        final Direction NO_FACE_CULLING = null;
        final int TINT_INDEX_NONE = -1;  // used for tintable blocks such as grass, which make a call to BlockColors to change their rendering colour.  -1 for not tintable.
        final String DUMMY_TEXTURE_NAME = "";  // texture name is only needed for loading from json files; not needed here
        BlockPartFace blockPartFace = new BlockPartFace(NO_FACE_CULLING, TINT_INDEX_NONE, DUMMY_TEXTURE_NAME,  blockFaceUV);

        IModelTransform NO_TRANSFORMATION = IDENTITY;
        ResourceLocation DUMMY_RL = new ResourceLocation("dummy_name");

        BakedQuad bakedQuad = faceBakery.bakeQuad(from, to, blockPartFace, sprite, axis, NO_TRANSFORMATION, rotation, true, DUMMY_RL);
        
         */

        // Up
        Vec3 normalUp = new Vec3(0.5, 1, 0.5);
        bakedQuadList.add(createQuad(vertex1, vertex2, vertex6, vertex5, normalUp, north.uvs[north.getReverseIndex(0)] / texWidth, north.uvs[north.getReverseIndex(2)] / texWidth, north.uvs[south.getReverseIndex(1)] / texHeight, north.uvs[north.getReverseIndex(3)] / texHeight, sprite, red, green, blue, alpha, tintIndex));

        // Down
        Vec3 normalDown = new Vec3(0.5, -1, 0.5);
        bakedQuadList.add(createQuad(vertex4, vertex3, vertex7, vertex0, normalDown, north.uvs[north.getReverseIndex(0)] / texWidth, north.uvs[north.getReverseIndex(2)] / texWidth, north.uvs[south.getReverseIndex(1)] / texHeight, north.uvs[north.getReverseIndex(3)] / texHeight, sprite, red, green, blue, alpha, tintIndex));

        // West side
        Vec3 normalWest = new Vec3(-0.5, 0, 0.5);
        bakedQuadList.add(createQuad(vertex7, vertex3, vertex6, vertex2, normalWest, north.uvs[north.getReverseIndex(0)] / texWidth, north.uvs[north.getReverseIndex(2)] / texWidth, north.uvs[south.getReverseIndex(1)] / texHeight, north.uvs[north.getReverseIndex(3)] / texHeight, sprite, red, green, blue, alpha, tintIndex));

        // East side
        Vec3 normalEast = new Vec3(1.5, 0, 0.5);
        bakedQuadList.add(createQuad(vertex4, vertex0, vertex1, vertex5, normalEast, north.uvs[north.getReverseIndex(0)] / texWidth, north.uvs[north.getReverseIndex(2)] / texWidth, north.uvs[south.getReverseIndex(1)] / texHeight, north.uvs[north.getReverseIndex(3)] / texHeight, sprite, red, green, blue, alpha, tintIndex));

        // North side
        Vec3 normalNorth = new Vec3(0.5, 0, 1.5);
        bakedQuadList.add(createQuad(vertex3, vertex4, vertex5, vertex6, normalNorth, north.uvs[north.getReverseIndex(0)] / texWidth, north.uvs[north.getReverseIndex(2)] / texWidth, north.uvs[south.getReverseIndex(1)] / texHeight, north.uvs[north.getReverseIndex(3)] / texHeight, sprite, red, green, blue, alpha, tintIndex));

        // South side
        Vec3 normalSouth = new Vec3(0, 0, -1);
        bakedQuadList.add(createQuad(vertex0, vertex7, vertex2, vertex1, normalSouth, south.uvs[south.getReverseIndex(0)] / texWidth, south.uvs[south.getReverseIndex(2)] / texWidth, south.uvs[south.getReverseIndex(1)] / texHeight, south.uvs[south.getReverseIndex(3)] / texHeight, sprite, red, green, blue, alpha, tintIndex));

        return bakedQuadList;
    }

    private static BakedQuad createQuad(Vec3 v1, Vec3 v2, Vec3 v3, Vec3 v4, Vec3 normal, float ulow, float uhigh, float vlow, float vhigh, TextureAtlasSprite sprite, float red, float green, float blue, float alpha, int tintIndex) {
        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
        builder.setQuadOrientation(Direction.getNearest(normal.x(), normal.y(), normal.z()));
        builder.setApplyDiffuseLighting(true);
        if (tintIndex>-1) {
            builder.setQuadTint(tintIndex);
        }
        putVertex(builder, normal, v1.x, v1.y, v1.z, ulow, vhigh, sprite, red, green, blue, alpha);
        putVertex(builder, normal, v2.x, v2.y, v2.z, uhigh, vhigh, sprite, red, green, blue, alpha);
        putVertex(builder, normal, v3.x, v3.y, v3.z, uhigh, vlow, sprite, red, green, blue, alpha);
        putVertex(builder, normal, v4.x, v4.y, v4.z, ulow, vlow, sprite, red, green, blue, alpha);

        return builder.build();
    }

}
