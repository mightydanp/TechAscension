package mightydanp.industrialtech.api.common.resources.asset.data;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ItemModelData {
    private String parentFolder;

    private ResourceLocation Parent = null;

    private Map<String, ResourceLocation> texturesLocation = new HashMap<>();

    public ItemModelData setParentFolder(String parentFolder) {
        this.parentFolder = parentFolder;
        return this;
    }

    public ItemModelData setParent(ResourceLocation parent) {
        Parent = parent;
        return this;
    }

    public String getParentFolder() {
        return parentFolder;
    }

    public ItemModelData setTexturesLocation(String textureName, ResourceLocation textureLocation) {
        texturesLocation.put(textureName, textureLocation);
        return this;
    }

    public JsonObject createJson(){
        JsonObject jsonObject = new JsonObject();
        if(Parent != null){
            jsonObject.addProperty("parent", Parent.toString());
        }

        if(texturesLocation.size() > 0){
            JsonObject textures = new JsonObject();{
                texturesLocation.forEach((s, r)-> {
                    textures.addProperty(s, r.toString());
                });
            }
            jsonObject.add("textures", textures);

        }

        return jsonObject;
    }



}