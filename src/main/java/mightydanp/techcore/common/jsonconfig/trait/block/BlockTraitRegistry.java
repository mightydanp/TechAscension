package mightydanp.techcore.common.jsonconfig.trait.block;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFileDirectory;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.trait.item.DefaultItemTrait;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class BlockTraitRegistry extends JsonConfigMultiFileDirectory<BlockTraitCodec> {

    @Override
    public void initiate() {
        setJsonFolderName("trait/block");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        if(TechAscension.mainJsonConfig.loadDefault()){
            for (DefaultBlockTrait codec : DefaultBlockTrait.values()) {
                register(codec.getCodec());
            }
        }

        buildJson();
        loadExistingJsons();
        super.initiate();
    }

    @Override
    public void register(BlockTraitCodec codec) {
        String registry = codec.registry();
        if (registryMap.containsKey(registry))
            throw new IllegalArgumentException(BlockTraitCodec.codecName + " for registry block(" + registry + "), already exists.");
        registryMap.put(registry, codec);
    }

    public BlockTraitCodec getBlockTraitByName(String name) {
        return registryMap.get(name);
    }

    public Set<BlockTraitCodec> getAllBlockTrait() {
        return new HashSet<>(registryMap.values());
    }

    public void buildJson(){
        for(BlockTraitCodec codec : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(codec.registry());

            if (jsonObject.size() == 0 || TechAscension.mainJsonConfig.loadDefault()) {
                this.saveJsonObject(codec.registry(), toJsonObject(codec));
            }
        }
    }

    public void loadExistingJsons(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        List<File> folders = Arrays.stream(Objects.requireNonNull(path.toFile().listFiles())).filter(file -> !file.getName().contains(".")).toList();

        if(path.toFile().listFiles() != null) {
            for(File folder : folders) {
                if(folder.listFiles() != null) {
                    for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                        if (file.getName().contains(".json")) {
                            JsonObject jsonObject = getJsonObject(file.getName());

                            if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                                BlockTraitCodec codec = fromJsonObject(jsonObject);
                                String registry = codec.registry();

                                registryMap.put(registry, codec);
                            } else {
                                TechAscension.LOGGER.fatal("[{}] could not be added to " + BlockTraitCodec.codecName + " because a " + BlockTraitCodec.codecName + " already exist!!", file.getAbsolutePath());
                            }
                        }
                    }
                } else {
                    TechAscension.LOGGER.warn(new CrashReport(BlockTraitCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport(BlockTraitCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public BlockTraitCodec fromJsonObject(JsonObject jsonObjectIn){
        return BlockTraitCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + BlockTraitCodec.codecName + ", please fix this"))).getFirst();
    }

    public JsonObject toJsonObject(BlockTraitCodec codec) {
        return BlockTraitCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + BlockTraitCodec.codecName + ", please fix this"))).getAsJsonObject();
    }
}