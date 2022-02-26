package mightydanp.industrialtech.api.common.jsonconfig.stonelayer;

import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by MightyDanp on 1/26/2022.
 */
public class StoneLayerRegistry extends JsonConfigMultiFile {
    private static final Map<ResourceLocation, IStoneLayer> stoneLayerList = new HashMap<>();

    @Override
    public void initiate() {
        setJsonFolderName("stone_layer");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

        //
        for (DefaultStoneLayer stoneLayer : DefaultStoneLayer.values()) {
            register(stoneLayer);
        }
        //

        buildStoneLayerJson();
        loadExistJson();
        super.initiate();
    }

    public static List<IStoneLayer> getAllStoneLayers() {
        return new ArrayList<>(stoneLayerList.values());
    }

    public static List<IStoneLayer> getAllStoneLayersByModID(String modIDIn) {
        return stoneLayerList.values().stream().filter(o -> o.getBlock().equals(modIDIn)).collect(Collectors.toList());
    }

    public static void register(IStoneLayer stoneLayerIn) {
        ResourceLocation resourceLocation = convertToResourceLocation(stoneLayerIn);
        if (stoneLayerList.containsKey(resourceLocation))
            throw new IllegalArgumentException("stone layer with block(" + resourceLocation + "), already exists.");
        stoneLayerList.put(resourceLocation, stoneLayerIn);
    }
    
    public static ResourceLocation convertToResourceLocation(IStoneLayer stoneLayerIn){
        return new ResourceLocation(stoneLayerIn.getBlock().split(":")[0], stoneLayerIn.getBlock().split(":")[1]);
    }

    public Set<IStoneLayer> getAllStoneLayer() {
        return new HashSet<>(stoneLayerList.values());
    }

    public void buildStoneLayerJson(){
        for(IStoneLayer stoneLayer : stoneLayerList.values()) {
            JsonObject jsonObject = getJsonObject(stoneLayer.getBlock().split(":")[1]);

            if (jsonObject.size() == 0) {
                JsonObject materialFlagJson = new JsonObject();
                {
                    materialFlagJson.addProperty("block", stoneLayer.getBlock());

                    if (materialFlagJson.size() > 0) {
                        jsonObject.add("stone_layer", materialFlagJson);
                    }
                }
                this.saveJsonObject(stoneLayer.getBlock().split(":")[1], jsonObject);
            }
        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!stoneLayerList.containsValue(getStoneLayer(jsonObject))) {
                        IStoneLayer stoneLayer = getStoneLayer(jsonObject);

                        stoneLayerList.put(convertToResourceLocation(stoneLayer), stoneLayer);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to stone Layer list because a stone layer already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("stone layer json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    public IStoneLayer getStoneLayer(JsonObject jsonObjectIn){
        JsonObject stoneLayerJson = jsonObjectIn.getAsJsonObject("stone_layer");

        String block = stoneLayerJson.get("block").getAsString();

        return () -> block;
    }

    public JsonObject toJsonObject(IStoneLayer stoneLayer) {
        JsonObject jsonObject = new JsonObject();

        JsonObject json = new JsonObject();
        json.addProperty("block", stoneLayer.getBlock());

        if (json.size() > 0) {
            jsonObject.add("stone_layer", json);
        }

        return jsonObject;
    }
}