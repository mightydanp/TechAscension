package mightydanp.industrialtech.api.common.jsonconfig.generation.smallore;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.handler.generation.OreGenerationHandler;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.api.common.world.gen.feature.SmallOreVeinGenFeatureConfig;
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
        JsonArray biomesJson = jsonObjectIn.getAsJsonArray("biomes");
        List<String> biomesList = new ArrayList<>();

        biomesJson.forEach((jsonElement) -> {
            String biome = jsonElement.getAsString();
            biomesList.add(biome);
        });


        JsonArray veinBlocksJson = jsonObjectIn.getAsJsonArray("vein_blocks_and_chances");
        List<Pair<String, Integer>> veinBlockChances = new ArrayList<>();

        veinBlocksJson.forEach((jsonElement) -> {
            JsonObject object = jsonElement.getAsJsonObject();
            veinBlockChances.add(new Pair<>(object.get("vein_block").getAsString(), object.get("vein_block_chance").getAsInt()));
        });

        return new SmallOreVeinGenFeatureConfig(name, rarity, minHeight, maxHeight, biomesList, veinBlockChances);
    }

    public JsonObject toJsonObject(SmallOreVeinGenFeatureConfig smallOreVein) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", smallOreVein.name);
        jsonObject.addProperty("rarity", smallOreVein.rarity);
        jsonObject.addProperty("min_height", smallOreVein.minHeight);
        jsonObject.addProperty("max_height", smallOreVein.maxHeight);

        JsonArray biomes = new JsonArray();
        {
            for(String biome : smallOreVein.biomes){
                biomes.add(biome);
            }
        }
        jsonObject.add("biomes", biomes);

        JsonArray veinBlocks = new JsonArray();
        {
            JsonObject veinBlocksArray = new JsonObject();
            {
                for (int i = 0; i < smallOreVein.blocksAndChances.size(); i++) {
                    veinBlocksArray.addProperty("vein_block", smallOreVein.blocksAndChances.get(i).getFirst());
                    veinBlocksArray.addProperty("vein_block_chance", smallOreVein.blocksAndChances.get(i).getSecond());
                }
                veinBlocks.add(veinBlocksArray);
            }
        }
        jsonObject.add("vein_blocks_and_chances", veinBlocks);

        return jsonObject;
    }
}