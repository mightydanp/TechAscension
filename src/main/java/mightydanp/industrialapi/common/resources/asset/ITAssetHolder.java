package mightydanp.industrialapi.common.resources.asset;

import com.google.gson.JsonObject;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ITAssetHolder implements PackResources {

    public Map<ResourceLocation, Supplier<InputStream>> ASSET_HOLDER = new HashMap<>();

    private static final int PACK_VERSION = 8;

    public void addToResources(ResourceLocation location, JsonObject jsonObject){
        if (!ASSET_HOLDER.containsKey(location)) {
            InputStream inputStream = new ByteArrayInputStream(jsonObject.toString().getBytes());
            ASSET_HOLDER.put(location, ()-> inputStream);
        }else{
            IndustrialTech.LOGGER.warn("[" + location + "] already exists in assets.");
        }
    }

    public void removeToResources(ResourceLocation location){
        if (ASSET_HOLDER.containsKey(location)) {
            ASSET_HOLDER.remove(location);
        }else{
            IndustrialTech.LOGGER.warn("[" + location + "] does not already exists in assets.");
        }
    }

    @Nullable
    @Override
    public InputStream getRootResource(@NotNull String location) throws IOException {
        throw new IOException("Could not find resource in generated resources: " + location);
    }

    @Override
    public @NotNull InputStream getResource(@NotNull PackType packType, @NotNull ResourceLocation location) throws IOException {
        if (packType == PackType.CLIENT_RESOURCES) {
            if (ASSET_HOLDER.containsKey(location)) {
                InputStream resource = ASSET_HOLDER.get(location).get();
                if (resource != null) {
                    return resource;
                } else {
                    throw new IOException("Resource is null: " + location);
                }
            }
        }
        throw new IOException("Could not find resource in generated resources: " + location);
    }

    @Override
    public @NotNull Collection<ResourceLocation> getResources(@NotNull PackType packType, @NotNull String namespace, @NotNull String directory, int depth, @NotNull Predicate<String> predicate) {
        ArrayList<ResourceLocation> locations = new ArrayList<>();
        if (packType == PackType.CLIENT_RESOURCES) {
            for (ResourceLocation resource : ASSET_HOLDER.keySet()) {
                if (resource.toString().startsWith(directory) && resource.getNamespace().equals(namespace) && predicate.test(resource.getPath())) {
                    locations.add(resource);
                }
            }
        }
        return locations;
    }

    @Override
    public boolean hasResource(@NotNull PackType packType, @NotNull ResourceLocation location) {
        if (packType == PackType.CLIENT_RESOURCES) {
            if (ASSET_HOLDER.containsKey(location)) {
                return ASSET_HOLDER.get(location).get() != null;
            }
        }
        return false;
    }

    @Override
    public @NotNull Set<String> getNamespaces(@NotNull PackType packType) {
        Set<String> namespaces = new HashSet<>();
        if (packType == PackType.CLIENT_RESOURCES) {
            for (ResourceLocation resource : ASSET_HOLDER.keySet()) {
                namespaces.add(resource.getNamespace());
            }
        }
        return namespaces;
    }

    @Nullable
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> serializer) {
        if(serializer.getMetadataSectionName().equals("pack")) {
            JsonObject object = new JsonObject();
            object.addProperty("pack_format", PACK_VERSION);
            object.addProperty("description", "dynamically generated assets");
            return serializer.fromJson(object);
        }

        return serializer.fromJson(new JsonObject());
    }

    @Override
    public @NotNull String getName() {
        return Ref.mod_id + ":assets";
    }

    @Override
    public void close() {

    }
}