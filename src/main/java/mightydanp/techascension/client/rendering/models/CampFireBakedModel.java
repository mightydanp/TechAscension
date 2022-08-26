package mightydanp.techascension.client.rendering.models;

import mightydanp.techapi.client.helper.BakedModelHelper;
import mightydanp.techcore.common.tileentities.HoleTileEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;

public class CampFireBakedModel implements IDynamicBakedModel {

    private final BakedModel baseModel;
    private IModelData modelData;
    private BlockState blockState;

    public CampFireBakedModel(BakedModel baseModelIn) {
        this.baseModel = baseModelIn;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        RenderType layer = MinecraftForgeClient.getRenderType();
        modelData = extraData;
        blockState = state;
        BlockState desiredBlockState = extraData.getData(HoleTileEntity.desiredBlock);

        List<BakedQuad> quads = new ArrayList<>(baseModel.getQuads(state, side, rand));

        TextureAtlas blocksStitchedTextures = ForgeModelBakery.instance().getSpriteMap().getAtlas(TextureAtlas.LOCATION_BLOCKS);
        TextureAtlasSprite campfire = blocksStitchedTextures.getSprite((new ResourceLocation("block/campfire_log")));

        //rock 1
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(7, 0, 0, 9, 2, 2),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 2
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(5, 0, 1, 7, 2, 3),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 3
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(3, 0, 3, 5, 2, 5),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 4
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(1, 0, 5, 3, 2, 7),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 5
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(0, 0, 7, 2, 2, 9),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 6
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(1, 0, 9, 3, 2, 11),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 7
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(3, 0, 11, 5, 2, 13),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 8
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(5, 0, 13, 7, 2, 15),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 9
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(7, 0, 14, 9, 2, 16),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 10
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(9, 0, 13, 11, 2, 15),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 11
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(11, 0, 11, 13, 2, 13),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 12
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(13, 0, 9, 15, 2, 11),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 13
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(14, 0, 7, 16, 2, 9),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 14
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(13, 0, 5, 15, 2, 7),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 15
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(11, 0, 3, 13, 2, 5),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0),
                new BlockFaceUV(new float[]{0, 0, 2, 2}, 0)));

        //rock 16
        quads.addAll(BakedModelHelper.createCube(1, 1, 1, 1.0f, -1, campfire, null, 0,
                new AABB(9, 0, 1, 11, 2, 3),
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
        return baseModel.useAmbientOcclusion(blockState);
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
        return baseModel.getParticleIcon(modelData);
    }

    @Override
    public ItemOverrides getOverrides() {
        return baseModel.getOverrides();
    }

    @Override
    public ItemTransforms getTransforms() {
        return ItemTransforms.NO_TRANSFORMS;
    }
}