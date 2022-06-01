package mightydanp.industrialtech.api.common.resources.data.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

import java.util.*;

public class TagData<A> {
    private final ResourceLocation tagName;
    private TagKey<A> tagKey;
    private String parentFolder;
    private Boolean replace;
    private Set<ResourceLocation> values = new HashSet<>();

    public TagData(ResourceLocation tagName, ResourceKey<? extends Registry<A>> ResourceKey){
        this.tagName = tagName;
        tagKey = TagKey.create(ResourceKey, tagName);
    }

    public ResourceLocation getTagName() {
        return tagName;
    }

    public TagKey<A> getTagKey() {
        return tagKey;
    }

    public TagData<A> setParentFolder(String parentFolder) {
        this.parentFolder = parentFolder;
        return this;
    }

    public String getParentFolder() {
        return parentFolder;
    }

    public TagData<A> setReplace(Boolean replace) {
        this.replace = replace;
        return this;
    }

    public TagData<A> addValue(ResourceLocation value) {
        values.add(value);
        return this;
    }

    public TagData<A> addAllValues(Set<ResourceLocation> value) {
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