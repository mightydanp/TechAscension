package mightydanp.industrialcore.common.jsonconfig.material.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialcore.common.jsonconfig.fluidstate.FluidStateRegistry;
import mightydanp.industrialcore.common.jsonconfig.fluidstate.IFluidState;
import mightydanp.industrialcore.common.jsonconfig.icons.ITextureIcon;
import mightydanp.industrialcore.common.jsonconfig.icons.TextureIconRegistry;
import mightydanp.industrialcore.common.jsonconfig.material.ore.IOreType;
import mightydanp.industrialcore.common.jsonconfig.material.ore.OreTypeRegistry;
import mightydanp.industrialcore.common.jsonconfig.tool.part.IToolPart;
import mightydanp.industrialcore.common.jsonconfig.tool.part.ToolPartRegistry;
import mightydanp.industrialcore.common.jsonconfig.tool.type.IToolType;
import mightydanp.industrialcore.common.material.ITMaterial;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.CrashReport;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 12/4/2021.
 */
public class MaterialRegistry extends JsonConfigMultiFile<ITMaterial>{
    //todo StoneLayers in ITMaterial need to be registered before any other materials.
    @Override
    public void register(ITMaterial materialIn){
        if(!registryMap.containsKey(materialIn.name)){
            registryMap.put(materialIn.name, materialIn);
        }else{
            IndustrialTech.LOGGER.warn("material (" + materialIn.name + ") was not added because it has already been added!");
        }
    }

    @Override
    public void initiate() {
        setJsonFolderName("material");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

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

    public Map<String, ITMaterial> getRegistryMapFromList(List<ITMaterial> materials) {
        Map<String, ITMaterial> materialList = new LinkedHashMap<>();
        materials.forEach(itMaterial -> materialList.put(itMaterial.name, itMaterial));

        return materialList;
    }

    public void buildJson(){
        for(ITMaterial material : registryMap.values()) {
            JsonObject jsonObject = getJsonObject(material.name);

            if (jsonObject.size() == 0) {
                JsonObject materialJson = getJsonObject(material);

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

                    if (!registryMap.containsKey(getFromJsonObject(jsonObject).name)) {
                        ITMaterial material = getFromJsonObject(jsonObject);

                        registryMap.put(material.name, material);
                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to material list because a material already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("material json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    public JsonObject getJsonObject(ITMaterial materialIn) {
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
                fluidProperties.addProperty("fluid_state", materialIn.fluidState.getName());
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

                JsonArray toolParts = new JsonArray();
                if(materialIn.toolParts != null){
                    for (Pair<String, String> toolPart : materialIn.toolParts) {
                        JsonObject toolPartProperties = new JsonObject();
                        toolPartProperties.addProperty("tool_part_prefix", toolPart.getFirst());
                        toolPartProperties.addProperty("tool_part_suffix", toolPart.getSecond());

                        if (toolPartProperties.size() > 0) {
                            toolParts.add(toolPartProperties);
                        }
                    }

                    if (toolParts.size() > 0) {
                        jsonObject.add("tool_parts", toolParts);
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
    public ITMaterial getFromJsonObject(JsonObject jsonObject) {
        String nameJson = jsonObject.get("name").getAsString();
        int colorJson = jsonObject.get("color").getAsInt();
        String textureIconJson = jsonObject.get("texture_icon").getAsString();
        Pair<String, ITextureIcon> textureIcon = new Pair<>(textureIconJson.split(":")[0], ((TextureIconRegistry)IndustrialTech.configSync.textureIcon.getFirst()).getTextureIconByName(textureIconJson.split(":")[1]));
        ITMaterial material = new ITMaterial(nameJson, colorJson, textureIcon);

        if(jsonObject.has("element_localization")) {
            JsonObject elementLocalization = jsonObject.get("element_localization").getAsJsonObject();{
                if (elementLocalization.has("symbol")) {
                    String symbolJson = elementLocalization.get("symbol").getAsString();
                    material.setElementalLocalization(symbolJson);
                }
            }
        }

        if(jsonObject.has("temperature_properties")) {
            JsonObject temperatureProperties = jsonObject.get("temperature_properties").getAsJsonObject();{
                if (temperatureProperties.has("melting_point") && temperatureProperties.has("boiling_point")) {
                    int meltingPointJson = temperatureProperties.get("melting_point").getAsInt();
                    int boilingPointJson = temperatureProperties.get("boiling_point").getAsInt();
                    material.setTemperatureProperties(meltingPointJson, boilingPointJson);
                }
            }
        }

        if(jsonObject.has("stone_layer_properties")) {
            JsonObject stoneLayerProperties = jsonObject.get("stone_layer_properties").getAsJsonObject();{
                if (stoneLayerProperties.has("is_stone_layer") && stoneLayerProperties.has("stone_layer_block") && stoneLayerProperties.has("mining_level")) {
                    Boolean isStoneLayerJson = stoneLayerProperties.get("is_stone_layer").getAsBoolean();
                    String replacementBlockJson = stoneLayerProperties.get("stone_layer_block").getAsString();
                    String stoneLayerTextureLocationJson = stoneLayerProperties.get("stone_layer_texture_location").getAsString();
                    material.setStoneLayerProperties(isStoneLayerJson, replacementBlockJson, stoneLayerTextureLocationJson);
                }
            }
        }

        if(jsonObject.has("block_properties")) {
            JsonObject blockProperties = jsonObject.get("block_properties").getAsJsonObject();{
                if (blockProperties.has("mining_level")) {
                    int miningLevelJson = blockProperties.get("mining_level").getAsInt();
                    material.setBlockProperties(miningLevelJson);
                }
            }
        }

        if(jsonObject.has("ore_properties")) {
            JsonObject oreProperties = jsonObject.get("ore_properties").getAsJsonObject();{
                if (oreProperties.has("ore_type") && oreProperties.has("dense_ore_density")) {
                    IOreType oreTypeJson = ((OreTypeRegistry)IndustrialTech.configSync.oreType.getFirst()).getByName(oreProperties.get("ore_type").getAsString());
                    int denseOreDensityJson = oreProperties.get("dense_ore_density").getAsInt();
                    material.setOreType(oreTypeJson);
                    material.setDenseOreDensity(denseOreDensityJson);
                }
            }
        }

        if(jsonObject.has("fluid_properties")) {
            JsonObject fluidProperties = jsonObject.get("fluid_properties").getAsJsonObject();{
                if (fluidProperties.has("fluid_state") && fluidProperties.has("fluid_acceleration") && fluidProperties.has("fluid_density") && fluidProperties.has("fluid_luminosity") && fluidProperties.has("fluid_viscosity")) {
                    IFluidState fluidStateJson = ((FluidStateRegistry)IndustrialTech.configSync.fluidState.getFirst()).getFluidStateByName(fluidProperties.get("fluid_state").getAsString());
                    float fluidAccelerationJson = fluidProperties.get("fluid_acceleration").getAsFloat();
                    int fluidDensityJson = fluidProperties.get("fluid_density").getAsInt();
                    int fluidLuminosityJson = fluidProperties.get("fluid_luminosity").getAsInt();
                    int fluidViscosityJson = fluidProperties.get("fluid_viscosity").getAsInt();

                    material.setFluidProperties(fluidStateJson, fluidAccelerationJson, fluidDensityJson, fluidLuminosityJson, fluidViscosityJson);
                }
            }
        }

        if(jsonObject.has("tool_properties") && jsonObject.has("tool_parts") && jsonObject.has("tool_parts")) {
            JsonArray toolProperties = jsonObject.get("tool_properties").getAsJsonArray();

            if(toolProperties.size()> 0) {
                JsonObject Properties = toolProperties.get(0).getAsJsonObject();
                JsonArray toolPartsArray = jsonObject.get("tool_parts").getAsJsonArray();

                {
                    if (Properties.has("attack_speed") && Properties.has("durability") && Properties.has("attack_damage") && Properties.has("weight")  && jsonObject.has("efficiency")  && jsonObject.has("tool_level") && jsonObject.has("tool_parts")) {
                        int attackSpeedJson = Properties.get("attack_speed").getAsInt();
                        int durabilityJson = Properties.get("durability").getAsInt();
                        float attackDamageJson = Properties.get("attack_damage").getAsFloat();
                        float weightJson = Properties.get("weight").getAsFloat();
                        float efficiencyJson = Properties.get("efficiency").getAsFloat();
                        int toolLevelJson = Properties.get("tool_level").getAsInt();

                        List<Pair<String, String>> toolPartsJsonList = new ArrayList<>();

                        for (int i = 0; i < toolPartsArray.size(); i++) {
                            JsonObject toolPartProperties = toolPartsArray.get(i).getAsJsonObject();
                            if (toolPartProperties.has("tool_part_prefix") && toolPartProperties.has("tool_part_suffix")) {
                                String toolPartPrefix = toolPartProperties.get("tool_part_prefix").getAsString();
                                String toolPartSuffix = toolPartProperties.get("tool_part_suffix").getAsString();

                                Pair<String, String> toolPartJson = new Pair<>(toolPartPrefix, toolPartSuffix);

                                toolPartsJsonList.add(toolPartJson);
                            }
                        }

                        material.setToolProperties(attackSpeedJson, durabilityJson, attackDamageJson, weightJson, efficiencyJson, toolLevelJson, toolPartsJsonList);
                    }
                }
            }
        }

        return material;
    }


}
