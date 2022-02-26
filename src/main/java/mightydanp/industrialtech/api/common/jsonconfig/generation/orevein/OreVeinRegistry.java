package mightydanp.industrialtech.api.common.jsonconfig.generation.orevein;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeatureConfig;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class OreVeinRegistry extends JsonConfigMultiFile {
    private static final Map<String , OreGenFeatureConfig> OreVeinList = new HashMap<>();

    @Override
    public void initiate() {
        setJsonFolderName("ore_vein");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation() + "/generation/");
        buildOreVeinJson();
        loadExistJson();
        super.initiate();
    }

    public static List<OreGenFeatureConfig> getAllOreVeins() {
        return new ArrayList<>(OreVeinList.values());
    }

    public static void register(OreGenFeatureConfig feature) {
        if (OreVeinList.containsKey(feature.veinName)){
            throw new IllegalArgumentException("ore vein with name(" + feature.veinName + "), already exists.");
        }else {
            OreVeinList.put(feature.veinName, feature);
        }
    }

    public void buildOreVeinJson(){
        for(OreGenFeatureConfig oreVein : OreVeinList.values()) {
            JsonObject jsonObject = getJsonObject(oreVein.veinName);

            if (jsonObject.size() == 0) {
                JsonObject oreVeinJson = new JsonObject();
                {
                    oreVeinJson.addProperty("name", oreVein.veinName);
                    oreVeinJson.addProperty("rarity", oreVein.rarity);
                    oreVeinJson.addProperty("min_height", oreVein.minHeight);
                    oreVeinJson.addProperty("max_height", oreVein.maxHeight);
                    oreVeinJson.addProperty("min_radius", oreVein.minRadius);
                    oreVeinJson.addProperty("min_number_of_small_ore_layers", oreVein.minNumberOfSmallOreLayers);

                    JsonArray veinBlocks = new JsonArray();{
                    JsonObject veinBlocksArray = new JsonObject();{
                            for (int i = 0; i < oreVein.veinBlocksAndChances.size(); i++){
                                veinBlocksArray.addProperty("vein_block", oreVein.veinBlocksAndChances.get(i).getFirst());
                                veinBlocksArray.addProperty("vein_block_chance", oreVein.veinBlocksAndChances.get(i).getSecond());
                            }
                        veinBlocks.add(veinBlocksArray);
                        }
                    }
                    oreVeinJson.add("vein_blocks_and_chances", veinBlocks);
                }
                this.saveJsonObject(oreVein.veinName, jsonObject);
            }
        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!OreVeinList.containsValue(getOreVein(jsonObject))) {
                        OreGenFeatureConfig OreVein = getOreVein(jsonObject);

                        OreVeinList.put(OreVein.veinName, OreVein);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to ore vein list because a ore vein already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("ore vein json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    public OreGenFeatureConfig getOreVein(JsonObject jsonObjectIn){
        String name = jsonObjectIn.get("name").getAsString();
        int rarity = jsonObjectIn.get("rarity").getAsInt();
        int minHeight = jsonObjectIn.get("min_height").getAsInt();
        int maxHeight = jsonObjectIn.get("max_height").getAsInt();
        int minRadius = jsonObjectIn.get("min_radius").getAsInt();
        int minNumberOfSmallOreLayers = jsonObjectIn.get("min_number_of_small_ore_layers").getAsInt();

        JsonArray veinBlocksJson = jsonObjectIn.getAsJsonArray("vein_blocks_and_chances");
        List<Pair<String, Integer>> veinBlockChances = new ArrayList<>();

        veinBlocksJson.forEach((jsonElement) -> {
            JsonObject object = jsonElement.getAsJsonObject();
            veinBlockChances.add(new Pair<>(object.get("vein_block").getAsString(), object.get("vein_block_chance").getAsInt()));
        });

        return new OreGenFeatureConfig(name, rarity, minHeight, maxHeight, minRadius, minNumberOfSmallOreLayers, veinBlockChances);
    }

    public JsonObject toJsonObject(OreGenFeatureConfig oreVein) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("name", oreVein.veinName);
        jsonObject.addProperty("rarity", oreVein.rarity);
        jsonObject.addProperty("min_height", oreVein.minHeight);
        jsonObject.addProperty("max_height", oreVein.maxHeight);
        jsonObject.addProperty("min_radius", oreVein.minRadius);
        jsonObject.addProperty("min_number_of_small_ore_layers", oreVein.minNumberOfSmallOreLayers);

        JsonArray veinBlocks = new JsonArray();{
            JsonObject veinBlocksArray = new JsonObject();{
                for (int i = 0; i < oreVein.veinBlocksAndChances.size(); i++){
                    veinBlocksArray.addProperty("vein_block", oreVein.veinBlocksAndChances.get(i).getFirst());
                    veinBlocksArray.addProperty("vein_block_chance", oreVein.veinBlocksAndChances.get(i).getSecond());
                }
                veinBlocks.add(veinBlocksArray);
            }
        }
        jsonObject.add("vein_blocks_and_chances", veinBlocks);

        return jsonObject;
    }
}