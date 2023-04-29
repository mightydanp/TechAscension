package mightydanp.techcore.common.jsonconfig.generation.randomsurface;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.handler.generation.PlantGenerationHandler;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techcore.common.world.gen.feature.OreVeinGenFeatureConfig;
import mightydanp.techcore.common.world.gen.feature.RandomSurfaceGenFeatureConfig;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class RandomSurfaceRegistry extends JsonConfigMultiFile<RandomSurfaceGenFeatureConfig> {

    @Override
    public void initiate() {

        setJsonFolderName("random_surface");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation() + "/generation/");
        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(RandomSurfaceGenFeatureConfig feature) {
        if (registryMap.containsKey(feature.name())) {
            throw new IllegalArgumentException("random surface with name(" + feature.name() + "), already exists.");
        } else {
            registryMap.put(feature.name(), feature);
        }
    }

    public void buildJson() {
        for (RandomSurfaceGenFeatureConfig randomSurface : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(randomSurface.name());
            if (jsonObject.size() == 0) {
                this.saveJsonObject(randomSurface.name(), toJsonObject(randomSurface));
            }
        }
    }

    public void loadExistJson() {
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if (path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(fromJsonObject(jsonObject))) {
                        RandomSurfaceGenFeatureConfig randomSurface = fromJsonObject(jsonObject);

                        registryMap.put(randomSurface.name(), randomSurface);
                        PlantGenerationHandler.addRegistryRandomSurfaceGenerate(randomSurface);
                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to random surface list because a random surface already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("random surface json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public RandomSurfaceGenFeatureConfig fromJsonObject(JsonObject jsonObjectIn) {
        return RandomSurfaceGenFeatureConfig.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your random surface, please fix this"))).getFirst();
    }

    public JsonObject toJsonObject(RandomSurfaceGenFeatureConfig config) {
        return RandomSurfaceGenFeatureConfig.CODEC.encodeStart(JsonOps.INSTANCE, config).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with your random surface with name [" + config.name() + "], please fix this"))).getAsJsonObject();

    }
}