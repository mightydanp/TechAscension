package mightydanp.industrialtech.api.common.jsonconfig.generation.randomsurface;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.handler.generation.PlantGenerationHandler;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.api.common.world.gen.feature.RandomSurfaceGenFeatureConfig;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class RandomSurfaceRegistry extends JsonConfigMultiFile<RandomSurfaceGenFeatureConfig> {

    @Override
    public void initiate() {

        setJsonFolderName("random_surface");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation() + "/generation/");
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
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to random surface list because a random surface already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("random surface json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public RandomSurfaceGenFeatureConfig getFromJsonObject(JsonObject jsonObjectIn) {
        String name = jsonObjectIn.get("name").getAsString();
        int rarity = jsonObjectIn.get("rarity").getAsInt();
        int minNumberOfSmallOreLayers = jsonObjectIn.get("min_number_of_small_ore_layers").getAsInt();
        JsonArray biomesJson = jsonObjectIn.getAsJsonArray("biomes");
        List<String> biomesList = new ArrayList<>();

        biomesJson.forEach((jsonElement) -> {
            String biome = jsonElement.getAsString();
            biomesList.add(biome);
        });

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

        return new RandomSurfaceGenFeatureConfig(name, rarity, biomesList, validBlocks, blocks);
    }

    public JsonObject toJsonObject(RandomSurfaceGenFeatureConfig randomSurface) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", randomSurface.name);
        jsonObject.addProperty("rarity", randomSurface.rarity);

        JsonArray biomes = new JsonArray();
        {
            for(String biome : randomSurface.biomes){
                biomes.add(biome);
            }
        }
        jsonObject.add("biomes", biomes);

        JsonArray validBlocks = new JsonArray();
        {
            randomSurface.validBlocks.forEach(validBlocks::add);
        }

        JsonArray blocks = new JsonArray();
        {
            randomSurface.blocks.forEach(blocks::add);
        }

        return jsonObject;
    }
}