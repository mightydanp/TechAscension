package mightydanp.techcore.common.jsonconfig.generation.orevein;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techapi.common.resources.ClientResourcePackEventHandler;
import mightydanp.techapi.common.resources.ServerResourcePackEventHandler;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.handler.generation.OreGenerationHandler;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techcore.common.world.gen.feature.OreVeinGenFeatureCodec;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class OreVeinRegistry extends JsonConfigMultiFile<OreVeinGenFeatureCodec> {

    @Override
    public void initiate() {
        setJsonFolderName(OreVeinGenFeatureCodec.codecName);
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation() + "/generation/");

        if(TechAscension.mainJsonConfig.loadDefault()){

        }

        buildJson();
        super.initiate();
        ServerResourcePackEventHandler.postInitLoad.add(this);
    }

    @Override
    public void register(OreVeinGenFeatureCodec codec) {
        if (registryMap.containsKey(codec.name())) {
            throw new IllegalArgumentException(OreVeinGenFeatureCodec.codecName + " with name(" + codec.name() + "), already exists.");
        } else {
            registryMap.put(codec.name(), codec);
            OreGenerationHandler.addRegistryOreGeneration(codec);
        }
    }

    public void buildJson() {
        for (OreVeinGenFeatureCodec codec : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(codec.name());
            if (jsonObject.size() == 0 || TechAscension.mainJsonConfig.loadDefault()) {
                this.saveJsonObject(codec.name(), toJsonObject(codec));
            }
        }
    }

    public void buildAndRegister(OreVeinGenFeatureCodec codec){
        if (!registryMap.containsKey(codec.name())) {
            this.register(codec);
            this.saveJsonObject(codec.name(), toJsonObject(codec));
        }
    }

    @Override
    public void loadExistingJsons() {
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if (path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        OreVeinGenFeatureCodec codec = fromJsonObject(jsonObject);

                        registryMap.put(codec.name(), codec);
                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to " + OreVeinGenFeatureCodec.codecName + " list because it already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport(OreVeinGenFeatureCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public OreVeinGenFeatureCodec fromJsonObject(JsonObject jsonObjectIn) {
        return OreVeinGenFeatureCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + OreVeinGenFeatureCodec.codecName + ", please fix this"))).getFirst();

    }

    public JsonObject toJsonObject(OreVeinGenFeatureCodec codec) {
        return OreVeinGenFeatureCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with your " + OreVeinGenFeatureCodec.codecName + " with name [" + codec.name() + "], please fix this"))).getAsJsonObject();
    }
}