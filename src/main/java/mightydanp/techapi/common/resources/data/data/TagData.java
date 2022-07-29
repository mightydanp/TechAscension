package mightydanp.techapi.common.resources.data.data;

import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;

import java.util.*;

public class TagData<A> {
    private final ResourceLocation name;
    private Tag.Builder builder = new Tag.Builder();
    private final TagKey<A> tagKey;
    private Set<ResourceLocation> values = new HashSet<>();
    private final Registry<A> registry;

    public TagData(ResourceLocation name, ResourceKey<? extends Registry<A>> ResourceKey, Registry<A> registry){
        this.name = name;
        tagKey = TagKey.create(ResourceKey, name);
        this.registry = registry;
    }

    public ResourceLocation getName() {
        return name;
    }

    public TagData<A> replace(boolean replace){
        builder.replace(replace);
        return this;
    }

    public TagData<A> add(A object) {
        builder.addElement(registry.getKey(object), name.getNamespace());
        return this;
    }

    public TagData<A> addAll(A... objects) {
        Arrays.stream(objects).forEach(object -> builder.addElement(registry.getKey(object), name.getNamespace()));
        return this;
    }

    public TagData<A> remove(A object) {
        builder.removeElement(registry.getKey(object), name.getNamespace());
        return this;
    }

    public TagData<A> removeAll(A... objects) {
        Arrays.stream(objects).forEach(object -> builder.removeElement(registry.getKey(object), name.getNamespace()));
        return this;
    }

    public TagKey<A> getTagKey() {
        return tagKey;
    }

    public TagData<A> addExisting(){
        registry.getTag(tagKey).ifPresent(holders -> holders.forEach(aHolder -> add(aHolder.value())));
        return this;
    }

    public JsonObject createJson(){
        return builder.serializeToJson();
    }
}