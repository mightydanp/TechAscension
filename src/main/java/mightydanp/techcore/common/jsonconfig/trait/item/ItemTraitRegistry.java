package mightydanp.techcore.common.jsonconfig.trait.item;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFileDirectory;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;
import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ItemTraitRegistry extends JsonConfigMultiFileDirectory<ItemTraitCodec> {

    @Override
    public void initiate() {
        setJsonFolderName("trait/item");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        for (DefaultItemTrait codec : DefaultItemTrait.values()) {
            register(codec.getCodec());
        }

        buildJson();
        loadExistingJsons();
        super.initiate();
    }

    @Override
    public void register(ItemTraitCodec codec) {
        if (registryMap.containsKey(codec.registry()))
            throw new IllegalArgumentException(ItemTraitCodec.codecName + " for registry item(" + codec.registry() + "), already exists.");
        registryMap.put(codec.registry(), codec);
    }

    public ItemTraitCodec getItemTraitByName(String name) {
        return registryMap.get(name);
    }

    public Set<ItemTraitCodec> getAllItemTrait() {
        return new HashSet<>(registryMap.values());
    }

    public void buildJson(){
        for(ItemTraitCodec codec : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(codec.registry());

            if (jsonObject.size() == 0) {
                this.saveJsonObject(codec.registry(), toJsonObject(codec));
            }
        }
    }

    public void loadExistingJsons(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        List<File> folders = Arrays.stream(Objects.requireNonNull(path.toFile().listFiles())).filter(file -> !file.getName().contains(".")).toList();

        if(path.toFile().listFiles() != null && folders.size() > 0) {
            for(File folder : folders){
                if(folder.listFiles() != null) {
                    for (final File file : Objects.requireNonNull(folder.listFiles())) {
                        if (file.getName().contains(".json")) {
                            JsonObject jsonObject = getJsonObject(new ResourceLocation(folder.getName(), file.getName().replace(".json", "")).toString());

                            if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                                ItemTraitCodec codec = fromJsonObject(jsonObject);
                                String registry = codec.registry();

                                registryMap.put(registry, codec);
                            } else {
                                TechAscension.LOGGER.fatal("[{}] could not be added to " + ItemTraitCodec.codecName + " because a " + ItemTraitCodec.codecName + " already exist!!", file.getAbsolutePath());
                            }
                        }
                    }
                } else {
                    TechAscension.LOGGER.warn(new CrashReport(ItemTraitCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
                }
            }
        }  else {
            TechAscension.LOGGER.warn(new CrashReport(ItemTraitCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public ItemTraitCodec fromJsonObject(JsonObject jsonObjectIn){
        return ItemTraitCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + ItemTraitCodec.codecName + ", please fix this"))).getFirst();
    }

    public JsonObject toJsonObject(ItemTraitCodec codec) {
        return ItemTraitCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + ItemTraitCodec.codecName + ", please fix this"))).getAsJsonObject();
    }
}