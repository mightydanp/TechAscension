package mightydanp.industrialtech.api.client.models.tools;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelBuilder;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.ModelTransformComposition;
import net.minecraftforge.client.model.geometry.IModelGeometryPart;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

/**
 * Created by MightyDanp on 4/10/2021.
 */
public class Submodel implements IModelGeometryPart {

    private String name;
    private IUnbakedModel model;
    private IModelTransform modelTransform;

    public Submodel(String nameIn, IUnbakedModel unbakedModelIn, IModelTransform modelTransformIn) {
        name = nameIn;
        model = unbakedModelIn;
        modelTransform = modelTransformIn;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void addQuads(IModelConfiguration owner, IModelBuilder<?> modelBuilder, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ResourceLocation modelLocation) {
        throw new UnsupportedOperationException("Attempted to call adQuads on a Submodel instance. Please don't.");
    }

    public IBakedModel bakeModel(ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ResourceLocation modelLocation) {
        IUnbakedModel unbakedModel = model;
        boolean uvLock = this.modelTransform.isUvLocked() || modelTransform.isUvLocked();
        IModelTransform transform = new ModelTransformComposition(this.modelTransform, modelTransform, uvLock);
        if (model instanceof BlockModel) {
            BlockModel blockmodel = (BlockModel)model;
            if (blockmodel.getRootModel() == ModelBakery.GENERATION_MARKER) {
                unbakedModel = new ItemModelGenerator().generateBlockModel(spriteGetter, blockmodel);
            }
        }
        return unbakedModel.bake(bakery, spriteGetter, transform, modelLocation);
    }

    @Override
    public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        return model.getMaterials(modelGetter, missingTextureErrors);
    }
}