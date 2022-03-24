package mightydanp.industrialtech.api.client.rendering.models;

import com.google.common.collect.ImmutableList;
import mightydanp.industrialtech.api.common.blocks.HoleBlock;
import mightydanp.industrialtech.api.common.tileentities.HoleTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by MightyDanp on 11/1/2021.
 */
public class HoleBakedModel implements IDynamicBakedModel {

    private final IBakedModel baseModel;
    private IModelData modelData;
    private BlockState blockState;

    public HoleBakedModel(IBakedModel baseModelIn)
    {
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

        if (desiredBlockState != null && !(desiredBlockState.getBlock() instanceof HoleBlock)) {
            if (layer == null || RenderTypeLookup.canRenderInLayer(desiredBlockState, layer)) {
                IBakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(desiredBlockState);
                try {
                    quads.addAll(model.getQuads(desiredBlockState, side, rand, EmptyModelData.INSTANCE));
                    return quads;
                } catch (Exception e) {
                    return Collections.emptyList();
                }
            }
            return Collections.emptyList();
        }

        if (side != null || (layer != null && !layer.equals(RenderType.solid()))) {
            return Collections.emptyList();
        }

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
