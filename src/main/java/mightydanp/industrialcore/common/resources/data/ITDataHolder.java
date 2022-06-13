package mightydanp.industrialcore.common.resources.data;

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

public class ITDataHolder implements PackResources {

    public Map<ResourceLocation, JsonObject> DATA_HOLDER = new HashMap<>();

    private static final int PACK_VERSION = 8;

    public void addToResources(ResourceLocation location, JsonObject jsonObject){
        if (!DATA_HOLDER.containsKey(location)) {
            DATA_HOLDER.put(location, jsonObject);
        }else{
            IndustrialTech.LOGGER.warn("[" + location + "] already exists in data.");
        }
    }

    public void removeToResources(ResourceLocation location){
        if (DATA_HOLDER.containsKey(location)) {
            DATA_HOLDER.remove(location);
        }else{
            IndustrialTech.LOGGER.warn("[" + location + "] does not already exists in data.");
        }
    }

    @Nullable
    @Override
    public InputStream getRootResource(String location) throws IOException {
        if(!location.contains("/") && !location.contains("\\")) {
            JsonObject supplier = DATA_HOLDER.get(location);

            return new ByteArrayInputStream(supplier.toString().getBytes());
        } else {
            throw new IllegalArgumentException("File name can't be a path");
        }
    }

    @Override
    public InputStream getResource(PackType packType, ResourceLocation location) throws IOException {
        if (packType == PackType.SERVER_DATA) {
            if (DATA_HOLDER.containsKey(location)) {
                JsonObject stream = DATA_HOLDER.get(location);
                if (stream != null && DATA_HOLDER.get(location).size() > 0) {
                    return new ByteArrayInputStream(stream.toString().getBytes());
                } else {
                    throw new IOException("Data is null or empty: " + location);
                }
            }
        }
        throw new IOException("Could not find resource in generated data: " + location);
    }


    @Override
    public Collection<ResourceLocation> getResources(PackType packType, String namespace, String directory, int depth, Predicate<String> predicate) {
        ArrayList<ResourceLocation> locations = new ArrayList<>();
        if (packType == PackType.SERVER_DATA) {
            for (ResourceLocation location : DATA_HOLDER.keySet()) {
                if (location.getPath().startsWith(directory) && location.getNamespace().equals(namespace) && predicate.test(location.getPath()) && DATA_HOLDER.get(location) != null && DATA_HOLDER.get(location).size() > 0) {
                    locations.add(location);
                }
            }
        }
        return locations;
    }

    @Override
    public boolean hasResource(@NotNull PackType packType, @NotNull ResourceLocation location) {
        if (packType == PackType.SERVER_DATA) {
            if (DATA_HOLDER.containsKey(location)) {
                return DATA_HOLDER.get(location) != null && DATA_HOLDER.get(location).size() > 0;
            }
        }
        return false;
    }

    @Override
    public @NotNull Set<String> getNamespaces(@NotNull PackType packType) {
        Set<String> namespaces = new HashSet<>();
        if (packType == PackType.SERVER_DATA) {
            for (ResourceLocation resource : DATA_HOLDER.keySet()) {
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
            object.addProperty("description", "dynamically generated data");
            return serializer.fromJson(object);
        }

        return serializer.fromJson(new JsonObject());
    }

    @Override
    public @NotNull String getName() {
        return Ref.mod_id + ":data";
    }

    @Override
    public void close() {

    }
}