package mightydanp.techcore.common.jsonconfig.material.ore;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 1/20/2022.
 */
public class OreTypeRegistry extends JsonConfigMultiFile<OreTypeCodec> {

    @Override
    public void initiate() {
        setJsonFolderName(OreTypeCodec.codecName);
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        if(TechAscension.mainJsonConfig.loadDefault()){
            for (DefaultOreType codecs : DefaultOreType.values()) {
                register(codecs.getCodec());
            }
        }

        buildJson();
        loadExistingJsons();
        super.initiate();
    }

    @Override
    public void register(OreTypeCodec codec) {
        String name = codec.name();
        if (registryMap.containsKey(codec.name()))
            throw new IllegalArgumentException(OreTypeCodec.codecName + " with name(" + name + "), already exists.");
        registryMap.put(name, codec);
    }

    public OreTypeCodec getByName(String name) {
        return registryMap.get(name);
    }

    public void buildJson(){
        for(OreTypeCodec oreType : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(oreType.name());

            if (jsonObject.size() == 0 || TechAscension.mainJsonConfig.loadDefault()) {
                this.saveJsonObject(oreType.name(), toJsonObject(oreType));
            }
        }
    }

    public void loadExistingJsons(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        OreTypeCodec codec = fromJsonObject(jsonObject);

                        registryMap.put(codec.name(), codec);

                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to " + OreTypeCodec.codecName + " list because a " + OreTypeCodec.codecName + " already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport(OreTypeCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public OreTypeCodec fromJsonObject(JsonObject jsonObjectIn){
        return OreTypeCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + OreTypeCodec.codecName + ", please fix this"))).getFirst();
    }

    public JsonObject toJsonObject(OreTypeCodec codec) {
        return OreTypeCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with your " + OreTypeCodec.codecName + " with name [" + codec.name() + "], please fix this"))).getAsJsonObject();
    }
}