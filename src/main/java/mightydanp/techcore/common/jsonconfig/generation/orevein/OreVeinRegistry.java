package mightydanp.techcore.common.jsonconfig.generation.orevein;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
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
        setJsonFolderName("ore_vein");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation() + "/generation/");
        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(OreVeinGenFeatureCodec config) {
        if (registryMap.containsKey(config.name())) {
            throw new IllegalArgumentException("ore vein with name(" + config.name() + "), already exists.");
        } else {
            registryMap.put(config.name(), config);
        }
    }

    public void buildJson() {
        for (OreVeinGenFeatureCodec oreVein : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(oreVein.name());
            if (jsonObject.size() == 0) {
                this.saveJsonObject(oreVein.name(), toJsonObject(oreVein));
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
                        OreVeinGenFeatureCodec OreVein = fromJsonObject(jsonObject);

                        registryMap.put(OreVein.name(), OreVein);
                        OreGenerationHandler.addRegistryOreGeneration(OreVein);
                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to ore vein list because a ore vein already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("ore vein json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public OreVeinGenFeatureCodec fromJsonObject(JsonObject jsonObjectIn) {
        return OreVeinGenFeatureCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your ore vein, please fix this"))).getFirst();

    }

    public JsonObject toJsonObject(OreVeinGenFeatureCodec config) {
        return OreVeinGenFeatureCodec.CODEC.encodeStart(JsonOps.INSTANCE, config).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with your ore vein with name [" + config.name() + "], please fix this"))).getAsJsonObject();
    }
}