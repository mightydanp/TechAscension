package mightydanp.industrialtech.api.client.models.tools;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometryPart;
import net.minecraftforge.client.model.geometry.IMultipartModelGeometry;

import java.util.*;
import java.util.function.Function;

/**
 * Created by MightyDanp on 4/10/2021.
 */
public class ToolModelGeometry implements IMultipartModelGeometry<ToolModelGeometry> {
    private ImmutableMap<String,Submodel> parts;

    public ToolModelGeometry(ImmutableMap<String, Submodel> partsIn) {
        parts = partsIn;
    }


    @Override
    public Collection<? extends IModelGeometryPart> getParts() {
        return parts.values();
    }

    @Override
    public Optional<? extends IModelGeometryPart> getPart(String name) {
        return Optional.ofNullable(parts.get(name));
    }

    @Override
    public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
        ImmutableMap.Builder<String, IBakedModel> bakedParts = ImmutableMap.builder();
        for (Map.Entry<String, Submodel> part : parts.entrySet()) {
            Submodel submodel = part.getValue();
            if (!owner.getPartVisibility(submodel)) continue;
            bakedParts.put(part.getKey(), submodel.bakeModel(bakery, spriteGetter, modelTransform, modelLocation));
        }

        // place names in an array so we can maintain order
        String[] partNames = this.parts.keySet().toArray(new String[0]);
        IModelTransform transforms = owner.getCombinedTransform();
        return new ToolModel(owner.isShadedInGui(), owner.isSideLit(), owner.useSmoothLighting(), bakedParts.build(), transforms, partNames);
    }

    @Override
    public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
        Set<RenderMaterial> textures = new HashSet<>();
        for (Submodel part : parts.values()) {
            textures.addAll(part.getTextures(owner, modelGetter, missingTextureErrors));
        }
        return textures;
    }
}
