package mightydanp.techcore.common.jsonconfig.material.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techascension.common.TechAscension;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.fluidstate.FluidStateRegistry;
import mightydanp.techcore.common.jsonconfig.icons.TextureIconRegistry;
import mightydanp.techcore.common.jsonconfig.material.ore.OreTypeRegistry;
import mightydanp.techcore.common.material.TCMaterial;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 12/4/2021.
 */
public class MaterialRegistry extends JsonConfigMultiFile<TCMaterial>{
    //todo StoneLayers in ITMaterial need to be registered before any other materials.
    @Override
    public void register(TCMaterial materialIn){
        if(!registryMap.containsKey(materialIn.name)){
            registryMap.put(materialIn.name, materialIn);
        }else{
            TechAscension.LOGGER.warn("material (" + materialIn.name + ") was not added because it has already been added!");
        }
    }

    @Override
    public void initiate() {
        setJsonFolderName("material");
        setJsonFolderLocation(TechAscension.mainJsonConfig.getFolderLocation());

        buildJson();
        loadExistJson();
        registryMap.forEach((modID, material) -> {
            material.save();
        });
    }

    public void initiateClient() {
        registryMap.forEach((modID, material) -> {
            material.clientRenderLayerInit();
            material.registerColorForItem();
            material.registerColorForBlock();
        });
    }

    public Map<String, TCMaterial> getRegistryMapFromList(List<TCMaterial> materials) {
        Map<String, TCMaterial> materialList = new LinkedHashMap<>();
        materials.forEach(itMaterial -> materialList.put(itMaterial.name, itMaterial));

        return materialList;
    }

    public void buildJson(){
        for(TCMaterial material : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(material.name);

            if (jsonObject.size() == 0) {
                JsonObject materialJson = toJsonObject(material);

                this.saveJsonObject(material.name, materialJson);
            }

        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!registryMap.containsKey(fromJsonObject(jsonObject).name)) {
                        TCMaterial material = fromJsonObject(jsonObject);

                        registryMap.put(material.name, material);
                    } else {
                        TechAscension.LOGGER.fatal("[{}] could not be added to material list because a material already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            TechAscension.LOGGER.warn(new CrashReport("material json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    public JsonObject toJsonObject(TCMaterial materialIn) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", materialIn.name);
        jsonObject.addProperty("color", materialIn.color);
        jsonObject.addProperty("texture_icon", materialIn.textureIcon.getFirst() + ":" + materialIn.textureIcon.getSecond().getName());

        JsonObject elementLocalization = new JsonObject();
        {
            if (materialIn.symbol != null) {
                elementLocalization.addProperty("symbol", materialIn.symbol);
            }

            if (elementLocalization.size() > 0) {
                jsonObject.add("element_localization", elementLocalization);
            }
        }

        JsonObject temperatureProperties = new JsonObject();
        {
            if (materialIn.meltingPoint != null) {
                temperatureProperties.addProperty("melting_point", materialIn.meltingPoint);
            }

            if (materialIn.boilingPoint != null) {
                temperatureProperties.addProperty("boiling_point", materialIn.boilingPoint);
            }

            if (temperatureProperties.size() > 0) {
                jsonObject.add("temperature_properties", temperatureProperties);
            }
        }

        JsonObject stoneLayerProperties = new JsonObject();
        {
            if (materialIn.isStoneLayer != null) {
                stoneLayerProperties.addProperty("is_stone_layer", materialIn.isStoneLayer);
            }

            if (materialIn.stoneLayerBlock != null) {
                stoneLayerProperties.addProperty("stone_layer_block", materialIn.stoneLayerBlock);
            }

            if (materialIn.stoneLayerTextureLocation != null) {
                stoneLayerProperties.addProperty("stone_layer_texture_location", materialIn.stoneLayerTextureLocation);
            }

            if (stoneLayerProperties.size() > 0) {
                jsonObject.add("stone_layer_properties", stoneLayerProperties);
            }

        }

        JsonObject blockProperties = new JsonObject();
        {
            if (materialIn.harvestLevel != null) {
                blockProperties.addProperty("mining_level", materialIn.harvestLevel);
            }

            if (blockProperties.size() > 0) {
                jsonObject.add("block_properties", blockProperties);
            }
        }

        JsonObject oreProperties = new JsonObject();
        {
            if (materialIn.oreType != null) {
                oreProperties.addProperty("ore_type", materialIn.oreType.getName());
            }

            if (materialIn.denseOreDensity != null) {
                oreProperties.addProperty("dense_ore_density", materialIn.denseOreDensity);
            }

            if (oreProperties.size() > 0) {
                jsonObject.add("ore_properties", oreProperties);
            }
        }

        JsonObject fluidProperties = new JsonObject();
        {
            if (materialIn.fluidState != null) {
                fluidProperties.addProperty("fluid_state", materialIn.fluidState.name());
            }

            if (materialIn.fluidAcceleration != null) {
                fluidProperties.addProperty("fluid_acceleration", materialIn.fluidAcceleration);
            }

            if (materialIn.fluidDensity != null) {
                fluidProperties.addProperty("fluid_density", materialIn.fluidDensity);
            }

            if (materialIn.fluidLuminosity != null) {
                fluidProperties.addProperty("fluid_luminosity", materialIn.fluidLuminosity);
            }

            if (materialIn.fluidViscosity != null) {
                fluidProperties.addProperty("fluid_viscosity", materialIn.fluidViscosity);
            }

            if (fluidProperties.size() > 0) {
                jsonObject.add("fluid_properties", fluidProperties);
            }
        }

        JsonArray toolProperties = new JsonArray();
        {
            JsonObject properties = new JsonObject();
            {
                if (materialIn.attackSpeed != null) {
                    properties.addProperty("attack_speed", materialIn.attackSpeed);
                }

                if (materialIn.durability != null) {
                    properties.addProperty("durability", materialIn.durability);
                }

                if (materialIn.attackDamage != null) {
                    properties.addProperty("attack_damage", materialIn.attackDamage);
                }

                if (materialIn.weight != null) {
                    properties.addProperty("weight", materialIn.weight);
                }

                if (materialIn.efficiency != null) {
                    properties.addProperty("efficiency", materialIn.efficiency);
                }

                if (materialIn.toolLevel != null) {
                    properties.addProperty("tool_level", materialIn.toolLevel);
                }

                JsonArray toolPartWhiteList = new JsonArray();
                if(materialIn.toolPartWhiteList != null){
                    for (Pair<String, String> toolPart : materialIn.toolPartWhiteList) {
                        JsonObject toolPartProperties = new JsonObject();
                        toolPartProperties.addProperty("tool_part_prefix", toolPart.getFirst());
                        toolPartProperties.addProperty("tool_part_suffix", toolPart.getSecond());

                        if (toolPartProperties.size() > 0) {
                            toolPartWhiteList.add(toolPartProperties);
                        }
                    }

                    if (toolPartWhiteList.size() > 0) {
                        jsonObject.add("tool_part_white_list", toolPartWhiteList);
                    }
                }

                JsonArray toolPartBlackList = new JsonArray();
                if(materialIn.toolPartBlackList != null){
                    for (Pair<String, String> toolPart : materialIn.toolPartBlackList) {
                        JsonObject toolPartProperties = new JsonObject();
                        toolPartProperties.addProperty("tool_part_prefix", toolPart.getFirst());
                        toolPartProperties.addProperty("tool_part_suffix", toolPart.getSecond());

                        if (toolPartProperties.size() > 0) {
                            toolPartBlackList.add(toolPartProperties);
                        }
                    }

                    if (toolPartBlackList.size() > 0) {
                        jsonObject.add("tool_part_black_list", toolPartBlackList);
                    }
                }

                if (properties.size() > 0) {
                    toolProperties.add(properties);
                }

                if (toolProperties.size() > 0) {
                    jsonObject.add("tool_properties", toolProperties);
                }
            }
        }

        return jsonObject;
    }

    @Override
    public TCMaterial fromJsonObject(JsonObject jsonObject) {
        String textureIconJson = jsonObject.get("texture_icon").getAsString();
        TCMaterial material = new TCMaterial(
                jsonObject.get("name").getAsString(),
                jsonObject.get("color").getAsInt(),
                new Pair<>(textureIconJson.split(":")[0], ((TextureIconRegistry) TCJsonConfigs.textureIcon.getFirst()).getTextureIconByName(textureIconJson.split(":")[1])));

        if(jsonObject.has("element_localization")) {
            JsonObject elementLocalization = jsonObject.get("element_localization").getAsJsonObject();{
                if (elementLocalization.has("symbol")) {
                    material.setElementalLocalization(elementLocalization.get("symbol").getAsString());
                }
            }
        }

        if(jsonObject.has("temperature_properties")) {
            JsonObject temperatureProperties = jsonObject.get("temperature_properties").getAsJsonObject();{
                if (temperatureProperties.has("melting_point") && temperatureProperties.has("boiling_point")) {
                    material.setTemperatureProperties(
                            temperatureProperties.get("melting_point").getAsInt(),
                            temperatureProperties.get("boiling_point").getAsInt());
                }
            }
        }

        if(jsonObject.has("stone_layer_properties")) {
            JsonObject stoneLayerProperties = jsonObject.get("stone_layer_properties").getAsJsonObject();{
                if (stoneLayerProperties.has("is_stone_layer") && stoneLayerProperties.has("stone_layer_block") && stoneLayerProperties.has("mining_level")) {
                    material.setStoneLayerProperties(
                            stoneLayerProperties.get("is_stone_layer").getAsBoolean(),
                            stoneLayerProperties.get("stone_layer_block").getAsString(),
                            stoneLayerProperties.get("stone_layer_texture_location").getAsString());
                }
            }
        }

        if(jsonObject.has("block_properties")) {
            JsonObject blockProperties = jsonObject.get("block_properties").getAsJsonObject();{
                if (blockProperties.has("mining_level")) {
                    material.setBlockProperties(blockProperties.get("mining_level").getAsInt());
                }
            }
        }

        if(jsonObject.has("ore_properties")) {
            JsonObject oreProperties = jsonObject.get("ore_properties").getAsJsonObject();{
                if (oreProperties.has("ore_type") && oreProperties.has("dense_ore_density")) {
                    material.setOreType( ((OreTypeRegistry) TCJsonConfigs.oreType.getFirst()).getByName(oreProperties.get("ore_type").getAsString()));
                    material.setDenseOreDensity(oreProperties.get("dense_ore_density").getAsInt());
                }
            }
        }

        if(jsonObject.has("fluid_properties")) {
            JsonObject fluidProperties = jsonObject.get("fluid_properties").getAsJsonObject();{
                if (fluidProperties.has("fluid_state") && fluidProperties.has("fluid_acceleration") && fluidProperties.has("fluid_density") && fluidProperties.has("fluid_luminosity") && fluidProperties.has("fluid_viscosity")) {
                    material.setFluidProperties(
                            ((FluidStateRegistry)TCJsonConfigs.fluidState.getFirst()).getFluidStateByName(fluidProperties.get("fluid_state").getAsString()),
                            fluidProperties.get("fluid_acceleration").getAsFloat(),
                            fluidProperties.get("fluid_density").getAsInt(),
                            fluidProperties.get("fluid_luminosity").getAsInt(),
                            fluidProperties.get("fluid_viscosity").getAsInt());
                }
            }
        }

        if(jsonObject.has("tool_properties") && jsonObject.has("tool_parts") && jsonObject.has("tool_parts")) {
            JsonArray toolProperties = jsonObject.get("tool_properties").getAsJsonArray();

            if(toolProperties.size()> 0) {
                JsonObject properties = toolProperties.get(0).getAsJsonObject();

                {
                    if (properties.has("attack_speed") && properties.has("durability") && properties.has("attack_damage") && properties.has("weight")  && jsonObject.has("efficiency")  && jsonObject.has("tool_level") && jsonObject.has("tool_parts")) {
                        JsonArray toolPartWhiteListArray = jsonObject.get("tool_part_white_list").getAsJsonArray();
                        List<Pair<String, String>> toolPartWhiteList = new ArrayList<>();

                        for (int i = 0; i < toolPartWhiteListArray.size(); i++) {
                            JsonObject toolPartProperties = toolPartWhiteListArray.get(i).getAsJsonObject();
                            if (toolPartProperties.has("tool_part_prefix") && toolPartProperties.has("tool_part_suffix")) {
                                String toolPartPrefix = toolPartProperties.get("tool_part_prefix").getAsString();
                                String toolPartSuffix = toolPartProperties.get("tool_part_suffix").getAsString();

                                Pair<String, String> toolPartJson = new Pair<>(toolPartPrefix, toolPartSuffix);

                                toolPartWhiteList.add(toolPartJson);
                            }
                        }

                        JsonArray toolPartBlackListArray = jsonObject.get("tool_part_black_list").getAsJsonArray();
                        List<Pair<String, String>> toolPartBlackList = new ArrayList<>();

                        for (int i = 0; i < toolPartBlackListArray.size(); i++) {
                            JsonObject toolPartProperties = toolPartBlackListArray.get(i).getAsJsonObject();
                            if (toolPartProperties.has("tool_part_prefix") && toolPartProperties.has("tool_part_suffix")) {
                                String toolPartPrefix = toolPartProperties.get("tool_part_prefix").getAsString();
                                String toolPartSuffix = toolPartProperties.get("tool_part_suffix").getAsString();

                                Pair<String, String> toolPartJson = new Pair<>(toolPartPrefix, toolPartSuffix);

                                toolPartBlackList.add(toolPartJson);
                            }
                        }

                        material.setToolProperties(
                                properties.get("attack_speed").getAsInt(),
                                properties.get("durability").getAsInt(),
                                properties.get("attack_damage").getAsFloat(),
                                properties.get("weight").getAsFloat(),
                                properties.get("efficiency").getAsFloat(),
                                properties.get("tool_level").getAsInt(),
                                toolPartWhiteList, toolPartBlackList);
                    }
                }
            }
        }

        return material;
    }


}
