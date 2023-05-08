package mightydanp.techcore.common.jsonconfig.generation.blocksinwater;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.handler.generation.PlantGenerationHandler;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techcore.common.world.gen.feature.BlocksInWaterGenFeatureCodec;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class BlocksInWaterRegistry extends JsonConfigMultiFile<BlocksInWaterGenFeatureCodec> {

    @Override
    public void initiate() {
        setJsonFolderName("blocks_in_water");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation() + "/generation/");
        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public List<BlocksInWaterGenFeatureCodec> getAllValues() {
        return new ArrayList<>(registryMap.values());
    }

    @Override
    public void register(BlocksInWaterGenFeatureCodec feature) {
        if (registryMap.containsKey(feature.name())) {
            throw new IllegalArgumentException("blocks in water with name(" + feature.name() + "), already exists.");
        } else {
            registryMap.put(feature.name(), feature);
        }
    }

    public void buildJson() {
        for (BlocksInWaterGenFeatureCodec blocksInWater : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(blocksInWater.name());
            if (jsonObject.size() == 0) {
                this.saveJsonObject(blocksInWater.name(), toJsonObject(blocksInWater));
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
                        BlocksInWaterGenFeatureCodec blocksInWater = fromJsonObject(jsonObject);

                        registryMap.put(blocksInWater.name(), blocksInWater);
                        PlantGenerationHandler.addRegistryBlockInWaterGenerate(blocksInWater);
                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to blocks in water list because a blocks in water already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("blocks in water json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public BlocksInWaterGenFeatureCodec fromJsonObject(JsonObject jsonObjectIn) {
        return BlocksInWaterGenFeatureCodec.CODEC.decode(JsonOps.INSTANCE, jsonObjectIn).getOrThrow(false,(a) -> TechAscension.LOGGER.throwing(new Error("There is something wrong with one of your blocks in water, please fix this"))).getFirst();
    }

    public JsonObject toJsonObject(BlocksInWaterGenFeatureCodec config) {
        return BlocksInWaterGenFeatureCodec.CODEC.encodeStart(JsonOps.INSTANCE, config).get().left().orElseThrow(() -> TechAscension.LOGGER.throwing(new Error("There is something wrong with your blocks in water with name [" + config.name() + "], please fix this"))).getAsJsonObject();
    }
}