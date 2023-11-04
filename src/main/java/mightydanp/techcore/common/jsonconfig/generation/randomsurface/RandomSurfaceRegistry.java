package mightydanp.techcore.common.jsonconfig.generation.randomsurface;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.handler.generation.PlantGenerationHandler;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techcore.common.world.gen.feature.OreVeinGenFeatureCodec;
import mightydanp.techcore.common.world.gen.feature.RandomSurfaceGenFeatureCodec;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class RandomSurfaceRegistry extends JsonConfigMultiFile<RandomSurfaceGenFeatureCodec> {

    @Override
    public void initiate() {

        setJsonFolderName(RandomSurfaceGenFeatureCodec.codecName);
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation() + "/generation/");

        if(TechAscension.mainJsonConfig.loadDefault()){

        }

        buildJson();
        loadExistingJsons();
        super.initiate();
    }

    @Override
    public void register(RandomSurfaceGenFeatureCodec codec) {
        if (registryMap.containsKey(codec.name())) {
            throw new IllegalArgumentException(RandomSurfaceGenFeatureCodec.codecName + " with name(" + codec.name() + "), already exists.");
        } else {
            registryMap.put(codec.name(), codec);
            PlantGenerationHandler.addRegistryRandomSurfaceGenerate(codec);
        }
    }

    public void buildJson() {
        for (RandomSurfaceGenFeatureCodec codec : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(codec.name());
            if (jsonObject.size() == 0) {
                this.saveJsonObject(codec.name(), toJsonObject(codec));
            }
        }
    }

    public void buildAndRegister(RandomSurfaceGenFeatureCodec codec){
        if (!registryMap.containsKey(codec.name())) {
            this.register(codec);
            this.saveJsonObject(codec.name(), toJsonObject(codec));
        }
    }

    public void loadExistingJsons() {
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if (path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        RandomSurfaceGenFeatureCodec codec = fromJsonObject(jsonObject);

                        registryMap.put(codec.name(), codec);
                        PlantGenerationHandler.addRegistryRandomSurfaceGenerate(codec);
                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to " + RandomSurfaceGenFeatureCodec.codecName + " list because a " + RandomSurfaceGenFeatureCodec.codecName + " already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport(RandomSurfaceGenFeatureCodec.codecName + " json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public RandomSurfaceGenFeatureCodec fromJsonObject(JsonObject jsonObjectIn) {
        return RandomSurfaceGenFeatureCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + RandomSurfaceGenFeatureCodec.codecName + ", please fix this"))).getFirst();
    }

    public JsonObject toJsonObject(RandomSurfaceGenFeatureCodec codec) {
        return RandomSurfaceGenFeatureCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with your " + RandomSurfaceGenFeatureCodec.codecName + " with name [" + codec.name() + "], please fix this"))).getAsJsonObject();

    }
}