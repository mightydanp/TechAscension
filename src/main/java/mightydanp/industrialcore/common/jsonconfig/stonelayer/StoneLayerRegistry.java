package mightydanp.industrialcore.common.jsonconfig.stonelayer;

import com.google.gson.JsonObject;
import mightydanp.industrialapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.CrashReport;
import net.minecraft.resources.ResourceLocation;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by MightyDanp on 1/26/2022.
 */
public class StoneLayerRegistry extends JsonConfigMultiFile<IStoneLayer> {

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

    public List<IStoneLayer> getAllStoneLayersByModID(String modIDIn) {
        return registryMap.values().stream().filter(o -> o.getBlock().equals(modIDIn)).collect(Collectors.toList());
    }

    @Override
    public void register(IStoneLayer stoneLayerIn) {
        ResourceLocation resourceLocation = convertToResourceLocation(stoneLayerIn);
        if (registryMap.containsKey(resourceLocation))
            throw new IllegalArgumentException("stone layer with block(" + resourceLocation + "), already exists.");
        registryMap.put(String.valueOf(resourceLocation), stoneLayerIn);
    }
    
    public static ResourceLocation convertToResourceLocation(IStoneLayer stoneLayerIn){
        return new ResourceLocation(stoneLayerIn.getBlock().split(":")[0], stoneLayerIn.getBlock().split(":")[1]);
    }

    public void buildStoneLayerJson(){
        for(IStoneLayer stoneLayer : registryMap.values()) {
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

                    if (!registryMap.containsValue(getFromJsonObject(jsonObject))) {
                        IStoneLayer stoneLayer = getFromJsonObject(jsonObject);

                        registryMap.put(String.valueOf(convertToResourceLocation(stoneLayer)), stoneLayer);

                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to stone Layer list because a stone layer already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("stone layer json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    @Override
    public IStoneLayer getFromJsonObject(JsonObject jsonObjectIn){
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