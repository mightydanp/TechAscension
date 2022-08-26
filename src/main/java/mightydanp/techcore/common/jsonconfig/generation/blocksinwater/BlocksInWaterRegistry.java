package mightydanp.techcore.common.jsonconfig.generation.blocksinwater;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.handler.generation.PlantGenerationHandler;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techcore.common.world.gen.feature.BlocksInWaterGenFeatureConfig;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class BlocksInWaterRegistry extends JsonConfigMultiFile<BlocksInWaterGenFeatureConfig> {

    @Override
    public void initiate() {
        setJsonFolderName("blocks_in_water");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation() + "/generation/");
        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public List<BlocksInWaterGenFeatureConfig> getAllValues() {
        return new ArrayList<>(registryMap.values());
    }

    @Override
    public void register(BlocksInWaterGenFeatureConfig feature) {
        if (registryMap.containsKey(feature.name)) {
            throw new IllegalArgumentException("blocks in water with name(" + feature.name + "), already exists.");
        } else {
            registryMap.put(feature.name, feature);
        }
    }

    public void buildJson() {
        for (BlocksInWaterGenFeatureConfig blocksInWater : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(blocksInWater.name);
            if (jsonObject.size() == 0) {
                this.saveJsonObject(blocksInWater.name, toJsonObject(blocksInWater));
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
                        BlocksInWaterGenFeatureConfig blocksInWater = fromJsonObject(jsonObject);

                        registryMap.put(blocksInWater.name, blocksInWater);
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
    public BlocksInWaterGenFeatureConfig fromJsonObject(JsonObject jsonObjectIn) {

        JsonArray dimensionsJson = jsonObjectIn.getAsJsonArray("dimensions");
        List<String> dimensionsList = new ArrayList<>();
        dimensionsJson.forEach(jsonElement -> dimensionsList.add(jsonElement.getAsString()));

        JsonArray validBiomesJson = jsonObjectIn.getAsJsonArray("valid_biomes");
        List<String> validBiomesList = new ArrayList<>();
        validBiomesJson.forEach(jsonElement -> validBiomesList.add(jsonElement.getAsString()));

        JsonArray invalidBiomesJson = jsonObjectIn.getAsJsonArray("invalid_biomes");
        List<String> invalidBiomesList = new ArrayList<>();
        invalidBiomesJson.forEach(jsonElement -> invalidBiomesList.add(jsonElement.getAsString()));


        JsonArray validBlocksJson = jsonObjectIn.getAsJsonArray("valid_blocks");
        List<String> validBlocks = new ArrayList<>();

        validBlocksJson.forEach(jsonElement -> validBlocks.add(jsonElement.getAsString()));

        return new BlocksInWaterGenFeatureConfig(
                jsonObjectIn.get("name").getAsString(),
                jsonObjectIn.get("rarity").getAsInt(),
                jsonObjectIn.get("height").getAsInt(),
                jsonObjectIn.get("shallow_water").getAsBoolean(), jsonObjectIn.get("go_above_water").getAsBoolean(),
                dimensionsList, validBiomesList, invalidBiomesList, validBlocks,
                jsonObjectIn.get("top_state").getAsString(), jsonObjectIn.get("bellow_state").getAsString());
    }

    public JsonObject toJsonObject(BlocksInWaterGenFeatureConfig config) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", config.name);
        jsonObject.addProperty("rarity", config.rarity);
        jsonObject.addProperty("height", config.height);
        jsonObject.addProperty("shallow_water", config.shallowWater);
        jsonObject.addProperty("go_above_water", config.goAboveWater);

        JsonArray dimensions = new JsonArray();
        {
            config.dimensions.forEach(dimensions::add);
        }
        jsonObject.add("dimensions", dimensions);

        JsonArray validBiomes = new JsonArray();
        {
            config.validBiomes.forEach(validBiomes::add);
        }
        jsonObject.add("valid_biomes", validBiomes);

        JsonArray invalid_biomes = new JsonArray();
        {
            config.invalidBiomes.forEach(invalid_biomes::add);
        }
        jsonObject.add("invalid_biomes", invalid_biomes);

        JsonArray validBlocks = new JsonArray();
        {
            config.validBlocks.forEach(validBlocks::add);
        }
        jsonObject.add("valid_blocks", validBlocks);

        jsonObject.addProperty("top_state", config.topState);

        jsonObject.addProperty("bellow_state", config.bellowState);

        return jsonObject;
    }
}