package mightydanp.industrialcore.common.resources.asset.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class BlockStateData {
    private ResourceLocation Parent = null;
    private Map<String, ResourceLocation> blockStateModelLocation = new HashMap<>();
    private Map<String, ResourceLocation> blockTexturesLocation = new HashMap<>();

    public BlockStateData setParent(ResourceLocation parent) {
        Parent = parent;
        return this;
    }

    public BlockStateData setBlockStateModelLocation(String variantName, ResourceLocation resourceLocation) {
        blockStateModelLocation.put(variantName, resourceLocation);
        return this;
    }

    public BlockStateData setBlockTexturesLocation(String textureName, ResourceLocation textureLocation) {
        blockTexturesLocation.put(textureName, textureLocation);
        return this;
    }

    public JsonObject createJson(){
        JsonObject jsonObject = new JsonObject();
        if(Parent != null){
            jsonObject.addProperty("parent", Parent.toString());
        }

        if(blockStateModelLocation.size() > 0){
            JsonObject variants = new JsonObject();{

                blockStateModelLocation.forEach((s, r)-> {
                    JsonObject variant = new JsonObject();
                    variant.addProperty("model", r.toString());
                    variants.add(s, variant);
                });
            }
            jsonObject.add("variants", variants);
        }

        if(blockTexturesLocation.size() > 0){
            JsonArray textures = new JsonArray();{
                JsonObject variantObject = textures.getAsJsonObject();

                blockTexturesLocation.forEach((s, r)-> {
                    variantObject.addProperty(s, r.toString());
                });
            }
            jsonObject.add("textures", textures);

        }

        return jsonObject;
    }



}
