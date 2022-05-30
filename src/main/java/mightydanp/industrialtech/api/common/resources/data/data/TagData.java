package mightydanp.industrialtech.api.common.resources.data.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.resources.asset.data.BlockModelData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.*;

public class TagData<T> {
    private final String tagName;
    private String parentFolder;
    private Boolean isVanilla;
    private Boolean replace;
    private Set<ResourceLocation> values = new HashSet<>();

    public TagData(String tagName){
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public TagData<T> setParentFolder(String parentFolder) {
        this.parentFolder = parentFolder;
        return this;
    }

    public String getParentFolder() {
        return parentFolder;
    }



    public TagData<T> isVanilla(Boolean vanilla) {
        this.isVanilla = vanilla;
        return this;
    }

    public Boolean getVanilla() {
        return isVanilla;
    }

    public TagData<T> setReplace(Boolean replace) {
        this.replace = replace;
        return this;
    }

    public TagData<T> addValue(ResourceLocation value) {
        values.add(value);
        return this;
    }

    public TagData<T> addAllValues(Set<ResourceLocation> value) {
        values.addAll(value);
        return this;
    }

    public JsonObject createJson(){
        JsonObject jsonObject = new JsonObject();
        if(replace != null){
            jsonObject.addProperty("replace", replace);
        }

        if(values.size() > 0){
            JsonArray values = new JsonArray();{

                this.values.forEach(resourceLocation-> {
                    values.add(resourceLocation.toString());
                });
            }
            jsonObject.add("values", values);

        }

        return jsonObject;
    }



}