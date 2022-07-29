package mightydanp.industrialcore.common.jsonconfig.generation.smallore;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.handler.generation.OreGenerationHandler;
import mightydanp.industrialapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialcore.common.world.gen.feature.SmallOreVeinGenFeatureConfig;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SmallOreVeinRegistry extends JsonConfigMultiFile<SmallOreVeinGenFeatureConfig> {

    @Override
    public void initiate() {
        setJsonFolderName("small_ore");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation() + "/generation/");
        buildJson();
        loadExistJson();
        super.initiate();
    }

    public void register(SmallOreVeinGenFeatureConfig feature) {
        if (registryMap.containsKey(feature.name)) {
            throw new IllegalArgumentException("small ore vein with name(" + feature.name + "), already exists.");
        } else {
            registryMap.put(feature.name, feature);
        }
    }

    public void buildJson() {
        for (SmallOreVeinGenFeatureConfig smallOreVein : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(smallOreVein.name);
            if (jsonObject.size() == 0) {
                this.saveJsonObject(smallOreVein.name, toJsonObject(smallOreVein));
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
                        SmallOreVeinGenFeatureConfig smallOreVein = getFromJsonObject(jsonObject);

                        registryMap.put(smallOreVein.name, smallOreVein);
                        OreGenerationHandler.addRegistrySmallOreVeinGeneration(smallOreVein);
                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to small ore vein list because a small ore vein already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("small ore vein json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public SmallOreVeinGenFeatureConfig getFromJsonObject(JsonObject jsonObjectIn) {
        String name = jsonObjectIn.get("name").getAsString();
        int rarity = jsonObjectIn.get("rarity").getAsInt();
        int minHeight = jsonObjectIn.get("min_height").getAsInt();
        int maxHeight = jsonObjectIn.get("max_height").getAsInt();

        JsonArray dimensionsJson = jsonObjectIn.getAsJsonArray("dimensions");
        List<String> dimensionsList = new ArrayList<>();
        dimensionsJson.forEach(jsonElement -> dimensionsList.add(jsonElement.getAsString()));

        JsonArray validBiomesJson = jsonObjectIn.getAsJsonArray("valid_biomes");
        List<String> validBiomesList = new ArrayList<>();
        validBiomesJson.forEach(jsonElement -> validBiomesList.add(jsonElement.getAsString()));

        JsonArray invalidBiomesJson = jsonObjectIn.getAsJsonArray("invalid_biomes");
        List<String> invalidBiomesList = new ArrayList<>();
        invalidBiomesJson.forEach(jsonElement -> invalidBiomesList.add(jsonElement.getAsString()));


        JsonArray veinBlocksJson = jsonObjectIn.getAsJsonArray("vein_blocks_and_chances");
        List<Pair<String, Integer>> veinBlockChances = new ArrayList<>();

        veinBlocksJson.forEach(jsonElement -> veinBlockChances.add(new Pair<>(jsonElement.getAsJsonObject().get("vein_block").getAsString(), jsonElement.getAsJsonObject().get("vein_block_chance").getAsInt())));

        return new SmallOreVeinGenFeatureConfig(name, rarity, minHeight, maxHeight, dimensionsList, validBiomesList, invalidBiomesList, veinBlockChances);
    }

    public JsonObject toJsonObject(SmallOreVeinGenFeatureConfig config) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", config.name);
        jsonObject.addProperty("rarity", config.rarity);
        jsonObject.addProperty("min_height", config.minHeight);
        jsonObject.addProperty("max_height", config.maxHeight);

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

        JsonArray veinBlocks = new JsonArray();
        {
            JsonObject veinBlocksArray = new JsonObject();
            {
                for (int i = 0; i < config.blocksAndChances.size(); i++) {
                    veinBlocksArray.addProperty("vein_block", config.blocksAndChances.get(i).getFirst());
                    veinBlocksArray.addProperty("vein_block_chance", config.blocksAndChances.get(i).getSecond());
                }
                veinBlocks.add(veinBlocksArray);
            }
        }
        jsonObject.add("vein_blocks_and_chances", veinBlocks);

        return jsonObject;
    }
}