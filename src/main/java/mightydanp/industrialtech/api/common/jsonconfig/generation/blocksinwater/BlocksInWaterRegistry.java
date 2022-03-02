package mightydanp.industrialtech.api.common.jsonconfig.generation.blocksinwater;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.handler.generation.OreGenerationHandler;
import mightydanp.industrialtech.api.common.handler.generation.PlantGenerationHandler;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.api.common.world.gen.feature.BlocksInWaterGenFeatureConfig;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.crash.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class BlocksInWaterRegistry extends JsonConfigMultiFile {
    private static final Map<String, BlocksInWaterGenFeatureConfig> BlocksInWaterList = new HashMap<>();

    @Override
    public void initiate() {
        setJsonFolderName("blocks_in_water");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation() + "/generation/");
        buildBlocksInWaterJson();
        loadExistJson();
        super.initiate();
    }

    public static List<BlocksInWaterGenFeatureConfig> getAllBlocksInWaters() {
        return new ArrayList<>(BlocksInWaterList.values());
    }

    public static void register(BlocksInWaterGenFeatureConfig feature) {
        if (BlocksInWaterList.containsKey(feature.name)) {
            throw new IllegalArgumentException("blocks in water with name(" + feature.name + "), already exists.");
        } else {
            BlocksInWaterList.put(feature.name, feature);
        }
    }

    public void buildBlocksInWaterJson() {
        for (BlocksInWaterGenFeatureConfig blocksInWater : BlocksInWaterList.values()) {
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

                    if (!BlocksInWaterList.containsValue(getBlocksInWater(jsonObject))) {
                        BlocksInWaterGenFeatureConfig blocksInWater = getBlocksInWater(jsonObject);

                        BlocksInWaterList.put(blocksInWater.name, blocksInWater);
                        PlantGenerationHandler.addRegistryBlockInWaterGenerate(blocksInWater);
                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to blocks in water list because a blocks in water already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("blocks in water json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    public BlocksInWaterGenFeatureConfig getBlocksInWater(JsonObject jsonObjectIn) {
        String name = jsonObjectIn.get("name").getAsString();
        int rarity = jsonObjectIn.get("rarity").getAsInt();
        int minHeight = jsonObjectIn.get("height").getAsInt();
        boolean shallowWater = jsonObjectIn.get("shallow_water").getAsBoolean();
        boolean goAboveWater = jsonObjectIn.get("go_above_water").getAsBoolean();
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

        String topState = jsonObjectIn.get("top_state").getAsString();

        String bellowState = jsonObjectIn.get("bellow_state").getAsString();

        return new BlocksInWaterGenFeatureConfig(name, rarity, minHeight, shallowWater, goAboveWater, biomesList, validBlocks, topState, bellowState);
    }

    public JsonObject toJsonObject(BlocksInWaterGenFeatureConfig blocksInWater) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", blocksInWater.name);
        jsonObject.addProperty("rarity", blocksInWater.rarity);
        jsonObject.addProperty("height", blocksInWater.height);
        jsonObject.addProperty("shallow_water", blocksInWater.shallowWater);
        jsonObject.addProperty("go_above_water", blocksInWater.goAboveWater);

        JsonArray biomes = new JsonArray();
        {
            for(String biome : blocksInWater.biomes){
                biomes.add(biome);
            }
        }
        jsonObject.add("biomes", biomes);


        JsonArray validBlocks = new JsonArray();
        {
            blocksInWater.validBlocks.forEach(validBlocks::add);
        }

        jsonObject.addProperty("top_state", blocksInWater.topState);

        jsonObject.addProperty("bellow_state", blocksInWater.bellowState);

        return jsonObject;
    }
}