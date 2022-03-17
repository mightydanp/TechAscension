package mightydanp.industrialtech.api.common.jsonconfig.generation.orevein;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.handler.generation.OreGenerationHandler;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.api.common.world.gen.feature.OreVeinGenFeatureConfig;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.crash.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class OreVeinRegistry extends JsonConfigMultiFile<OreVeinGenFeatureConfig> {

    @Override
    public void initiate() {
        setJsonFolderName("ore_vein");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation() + "/generation/");
        buildJson();
        loadExistJson();
        super.initiate();
    }

    @Override
    public void register(OreVeinGenFeatureConfig feature) {
        if (registryMap.containsKey(feature.name)) {
            throw new IllegalArgumentException("ore vein with name(" + feature.name + "), already exists.");
        } else {
            registryMap.put(feature.name, feature);
        }
    }

    public void buildJson() {
        for (OreVeinGenFeatureConfig oreVein : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(oreVein.name);
            if (jsonObject.size() == 0) {
                this.saveJsonObject(oreVein.name, toJsonObject(oreVein));
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
                        OreVeinGenFeatureConfig OreVein = getFromJsonObject(jsonObject);

                        registryMap.put(OreVein.name, OreVein);
                        OreGenerationHandler.addRegistryOreGeneration(OreVein);
                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to ore vein list because a ore vein already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("ore vein json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public OreVeinGenFeatureConfig getFromJsonObject(JsonObject jsonObjectIn) {
        String name = jsonObjectIn.get("name").getAsString();
        int rarity = jsonObjectIn.get("rarity").getAsInt();
        int minHeight = jsonObjectIn.get("min_height").getAsInt();
        int maxHeight = jsonObjectIn.get("max_height").getAsInt();
        int minRadius = jsonObjectIn.get("min_radius").getAsInt();
        int minNumberOfSmallOreLayers = jsonObjectIn.get("min_number_of_small_ore_layers").getAsInt();
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

        return new OreVeinGenFeatureConfig(name, rarity, minHeight, maxHeight, minRadius, minNumberOfSmallOreLayers, biomesList, veinBlockChances);
    }

    public JsonObject toJsonObject(OreVeinGenFeatureConfig oreVein) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", oreVein.name);
        jsonObject.addProperty("rarity", oreVein.rarity);
        jsonObject.addProperty("min_height", oreVein.minHeight);
        jsonObject.addProperty("max_height", oreVein.maxHeight);
        jsonObject.addProperty("min_radius", oreVein.minRadius);
        jsonObject.addProperty("min_number_of_small_ore_layers", oreVein.minNumberOfSmallOreLayers);

        JsonArray biomes = new JsonArray();
        {
            for(String biome : oreVein.biomes){
                biomes.add(biome);
            }
        }
        jsonObject.add("biomes", biomes);

        JsonArray veinBlocks = new JsonArray();
        {
            JsonObject veinBlocksArray = new JsonObject();
            {
                for (int i = 0; i < oreVein.blocksAndChances.size(); i++) {
                    veinBlocksArray.addProperty("vein_block", oreVein.blocksAndChances.get(i).getFirst());
                    veinBlocksArray.addProperty("vein_block_chance", oreVein.blocksAndChances.get(i).getSecond());
                }
                veinBlocks.add(veinBlocksArray);
            }
        }
        jsonObject.add("vein_blocks_and_chances", veinBlocks);

        return jsonObject;
    }
}