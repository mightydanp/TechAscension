package mightydanp.techcore.common.jsonconfig.generation.smallore;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import mightydanp.techcore.common.handler.generation.OreGenerationHandler;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techcore.common.world.gen.feature.RandomSurfaceGenFeatureCodec;
import mightydanp.techcore.common.world.gen.feature.SmallOreVeinGenFeatureCodec;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SmallOreVeinRegistry extends JsonConfigMultiFile<SmallOreVeinGenFeatureCodec> {

    @Override
    public void initiate() {
        setJsonFolderName(SmallOreVeinGenFeatureCodec.codecName);
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation() + "/generation/");
        buildJson();
        loadExistJson();
        super.initiate();
    }

    public void register(SmallOreVeinGenFeatureCodec codec) {
        if (registryMap.containsKey(codec.name())) {
            throw new IllegalArgumentException(SmallOreVeinGenFeatureCodec.codecName + " vein with name(" + codec.name() + "), already exists.");
        } else {
            registryMap.put(codec.name(), codec);
        }
    }

    public void buildJson() {
        for (SmallOreVeinGenFeatureCodec codec : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(codec.name());
            if (jsonObject.size() == 0) {
                this.saveJsonObject(codec.name(), toJsonObject(codec));
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
                        SmallOreVeinGenFeatureCodec codec = fromJsonObject(jsonObject);

                        registryMap.put(codec.name(), codec);
                        OreGenerationHandler.addRegistrySmallOreVeinGeneration(codec);
                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to " + SmallOreVeinGenFeatureCodec.codecName + " vein list because a small ore vein already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport(SmallOreVeinGenFeatureCodec.codecName + " vein json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public SmallOreVeinGenFeatureCodec fromJsonObject(JsonObject jsonObjectIn) {
        return SmallOreVeinGenFeatureCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false, (a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your " + SmallOreVeinGenFeatureCodec.codecName + ", please fix this"))).getFirst();

    }

    public JsonObject toJsonObject(SmallOreVeinGenFeatureCodec codec) {
        return SmallOreVeinGenFeatureCodec.CODEC.encodeStart(JsonOps.INSTANCE, codec).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with your " + SmallOreVeinGenFeatureCodec.codecName + " with name [" + codec.name() + "], please fix this"))).getAsJsonObject();

    }
}