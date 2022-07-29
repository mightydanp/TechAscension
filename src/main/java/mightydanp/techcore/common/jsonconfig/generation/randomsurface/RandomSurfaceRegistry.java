package mightydanp.techcore.common.jsonconfig.generation.randomsurface;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.handler.generation.PlantGenerationHandler;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
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
        if (registryMap.containsKey(feature.name)) {
            throw new IllegalArgumentException("random surface with name(" + feature.name + "), already exists.");
        } else {
            registryMap.put(feature.name, feature);
        }
    }

    public void buildJson() {
        for (RandomSurfaceGenFeatureConfig randomSurface : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(randomSurface.name);
            if (jsonObject.size() == 0) {
                this.saveJsonObject(randomSurface.name, toJsonObject(randomSurface));
            }
        }
    }

    public void loadExistJson() {
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if (path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsValue(getFromJsonObject(jsonObject))) {
                        RandomSurfaceGenFeatureConfig randomSurface = getFromJsonObject(jsonObject);

                        registryMap.put(randomSurface.name, randomSurface);
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
    public RandomSurfaceGenFeatureConfig getFromJsonObject(JsonObject jsonObjectIn) {
        String name = jsonObjectIn.get("name").getAsString();
        int rarity = jsonObjectIn.get("rarity").getAsInt();
        int minNumberOfSmallOreLayers = jsonObjectIn.get("min_number_of_small_ore_layers").getAsInt();
        JsonArray biomesJson = jsonObjectIn.getAsJsonArray("biomes");
        List<String> biomesList = new ArrayList<>();

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

        validBlocksJson.forEach((jsonElement) -> {
            validBlocks.add(jsonElement.getAsString());
        });

        JsonArray blocksJson = jsonObjectIn.getAsJsonArray("blocks");
        List<String> blocks = new ArrayList<>();

        blocksJson.forEach((jsonElement) -> {
            blocks.add(jsonElement.getAsString());
        });

        return new RandomSurfaceGenFeatureConfig(name, rarity, dimensionsList, validBiomesList, invalidBiomesList, validBlocks, blocks);
    }

    public JsonObject toJsonObject(RandomSurfaceGenFeatureConfig config) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", config.name);
        jsonObject.addProperty("rarity", config.rarity);

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

        JsonArray blocks = new JsonArray();
        {
            config.blocks.forEach(blocks::add);
        }

        return jsonObject;
    }
}