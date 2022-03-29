package mightydanp.industrialtech.client.rendering.models;

import mightydanp.industrialtech.api.client.helper.BakedModelHelper;
import mightydanp.industrialtech.api.common.blocks.HoleBlock;
import mightydanp.industrialtech.api.common.tileentities.HoleTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CampFireBakedModel implements IDynamicBakedModel {

    private final IBakedModel baseModel;
    private IModelData modelData;
    private BlockState blockState;

    public CampFireBakedModel(IBakedModel baseModelIn) {
        this.baseModel = baseModelIn;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        RenderType layer = MinecraftForgeClient.getRenderLayer();
        modelData = extraData;
        blockState = state;
        BlockState desiredBlockState = extraData.getData(HoleTileEntity.desiredBlock);

        List<BakedQuad> quads = new ArrayList<>(baseModel.getQuads(state, side, rand));

        AtlasTexture blocksStitchedTextures = ModelLoader.instance().getSpriteMap().getAtlas(AtlasTexture.LOCATION_BLOCKS);
        TextureAtlasSprite campfire = blocksStitchedTextures.getSprite((new ResourceLocation("block/campfire_log")));

        //rock 1
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(7, 0, 0, 9, 2, 2),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 2
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(5, 0, 1, 7, 2, 3),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 3
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(3, 0, 3, 5, 2, 5),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 4
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(1, 0, 5, 3, 2, 7),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 5
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(0, 0, 7, 2, 2, 9),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 6
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(1, 0, 9, 3, 2, 11),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 7
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(3, 0, 11, 5, 2, 13),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 8
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(5, 0, 13, 7, 2, 15),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 9
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(7, 0, 14, 9, 2, 16),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 10
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(9, 0, 13, 11, 2, 15),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 11
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(11, 0, 11, 13, 2, 13),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 12
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(13, 0, 9, 15, 2, 11),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 13
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(14, 0, 7, 16, 2, 9),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 14
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(13, 0, 5, 15, 2, 7),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 15
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(11, 0, 3, 13, 2, 5),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 16
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AxisAlignedBB(9, 0, 1, 11, 2, 3),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        return quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return baseModel.isAmbientOcclusion(blockState);
    }

    @Override
    public boolean isGui3d() {
        return baseModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return baseModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return baseModel.isCustomRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return baseModel.getParticleTexture(modelData);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return baseModel.getOverrides();
    }

    @Override
    public ItemCameraTransforms getTransforms() {
        return ItemCameraTransforms.NO_TRANSFORMS;
    }
}