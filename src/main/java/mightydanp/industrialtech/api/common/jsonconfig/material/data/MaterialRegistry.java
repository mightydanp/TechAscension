package mightydanp.industrialtech.api.common.jsonconfig.material.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.IFluidState;
import mightydanp.industrialtech.api.common.jsonconfig.icons.ITextureIcon;
import mightydanp.industrialtech.api.common.jsonconfig.ore.IOreType;
import mightydanp.industrialtech.api.common.jsonconfig.ore.OreTypeRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.IToolPart;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.ToolPartRegistry;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by MightyDanp on 12/4/2021.
 */
public class MaterialRegistry extends JsonConfigMultiFile{
    private static final Map<String, ITMaterial> materialList = new LinkedHashMap<>();

    public static Map<String, ITMaterial> materials() {
        return materialList;
    }

    //todo StoneLayers in ITMaterial need to be registered before any other materials.
    public static void registerMaterial(ITMaterial materialIn){
        if(!materials().containsKey(materialIn.name)){
            materialList.put(materialIn.name, materialIn);
        }else{
            IndustrialTech.LOGGER.warn("material (" + materialIn.name + ") was not added because it has already been added!");
        }
    }

    @Override
    public void initiate() {
        setJsonFolderName("material");
        setJsonFolderLocation(IndustrialTech.mainJsonConfig.getFolderLocation());

        buildMaterialJson();
        loadExistJson();
        materialList.forEach((modID, material) -> {
            material.save();
        });
    }

    public void initiateClient() {
        materialList.forEach((modID, material) -> {
            material.clientRenderLayerInit();
            material.registerColorForItem();
            material.registerColorForBlock();
        });
    }

    public void buildMaterialJson(){
        for(ITMaterial material : materialList.values()) {
            JsonObject jsonObject = getJsonObject(material.name);

            if (jsonObject.size() == 0) {
                JsonObject materialJson = getJsonObject(material);

                this.saveJsonObject(material.name, materialJson);
            }

            RegistryHandler.MATERIAL.register(material.setRegistryName(new ResourceLocation(Ref.mod_id, material.name)));

        }
    }

    public void loadExistJson(){
        Path path = Paths.get(this.getJsonFolderLocation() + "/" + this.getJsonFolderName());

        if(path.toFile().listFiles() != null) {
            for (final File file : Objects.requireNonNull(path.toFile().listFiles())) {
                if (file.getName().contains(".json")) {
                    JsonObject jsonObject = getJsonObject(file.getName());

                    if (!materialList.containsKey(getMaterial(jsonObject).name)) {
                        ITMaterial material = getMaterial(jsonObject);

                        materialList.put(material.name, material);
                        RegistryHandler.MATERIAL.register(material.setRegistryName(new ResourceLocation(Ref.mod_id, material.name)));
                    } else {
                        IndustrialTech.LOGGER.fatal("[{}] could not be added to material list because a material already exist!!", file.getAbsolutePath());
                    }
                }
            }
        } else {
            IndustrialTech.LOGGER.warn(new CrashReport("material json configs are empty [" + getJsonFolderLocation() + "/" + getJsonFolderName() + "]", new Throwable()));
        }
    }

    public static ITMaterial grabMaterialFromRegistry(String materialNameIn){
        return RegistryHandler.MATERIAL.getValue(new ResourceLocation(Ref.mod_id, materialNameIn));
    }

    public static List<ITMaterial> getMaterials() {
        return new ArrayList<>(RegistryHandler.MATERIAL.getValues());
    }

    public static Map<String, ITMaterial> getMaterialsMap() {
        Map<String, ITMaterial> materialList = new LinkedHashMap<>();
        getMaterials().forEach(itMaterial -> materialList.put(itMaterial.name, itMaterial));

        return materialList;
    }

    public static JsonObject getJsonObject(ITMaterial materialIn) {
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
            if (materialIn.miningLevel != null) {
                blockProperties.addProperty("mining_level", materialIn.miningLevel);
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

                if (properties.size() > 0) {
                    toolProperties.add(properties);
                }

                if (toolProperties.size() > 0) {
                    jsonObject.add("tool_properties", toolProperties);
                }
            }
        }

        JsonArray toolTypesArray = new JsonArray();
        if(materialIn.toolTypes != null){
            for (Pair<ToolType, Integer> toolType : materialIn.toolTypes) {
                JsonObject toolTypeProperties = new JsonObject();

                toolTypeProperties.addProperty("tool_type", toolType.getFirst().getName());
                toolTypeProperties.addProperty("tool_level", toolType.getSecond());

                if (toolTypeProperties.size() > 0) {
                    toolTypesArray.add(toolTypeProperties);
                }
            }
            if (toolTypesArray.size() > 0) {
                jsonObject.add("tool_types", toolTypesArray);

            }
        }

        JsonArray toolParts = new JsonArray();
        if(materialIn.toolParts != null){
            for (IToolPart toolPart : materialIn.toolParts) {
                JsonObject toolPartProperties = new JsonObject();
                toolPartProperties.addProperty("tool_part_prefix", toolPart.getFixes().getFirst());
                toolPartProperties.addProperty("tool_part_suffix", toolPart.getFixes().getSecond());

                if (toolPartProperties.size() > 0) {
                    toolParts.add(toolPartProperties);
                }
            }

            if (toolParts.size() > 0) {
                jsonObject.add("tool_parts", toolParts);
            }
        }
        return jsonObject;
    }

    public static ITMaterial getMaterial(JsonObject jsonObject) {
        String nameJson = jsonObject.get("name").getAsString();
        int colorJson = jsonObject.get("color").getAsInt();
        String textureIconJson = jsonObject.get("texture_icon").getAsString();
        Pair<String, ITextureIcon> textureIcon = new Pair<>(textureIconJson.split(":")[0], IndustrialTech.textureIconRegistry.getTextureIconByName(textureIconJson.split(":")[1]));
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
                    IOreType oreTypeJson = OreTypeRegistry.getOreTypeByName(oreProperties.get("ore_type").getAsString());
                    int denseOreDensityJson = oreProperties.get("dense_ore_density").getAsInt();
                    material.setOreType(oreTypeJson);
                    material.setDenseOreDensity(denseOreDensityJson);
                }
            }
        }

        if(jsonObject.has("fluid_properties")) {
            JsonObject fluidProperties = jsonObject.get("fluid_properties").getAsJsonObject();{
                if (fluidProperties.has("fluid_state") && fluidProperties.has("fluid_acceleration") && fluidProperties.has("fluid_density") && fluidProperties.has("fluid_luminosity") && fluidProperties.has("fluid_viscosity")) {
                    IFluidState fluidStateJson = IndustrialTech.fluidStateRegistry.getFluidStateByName(fluidProperties.get("fluid_state").getAsString());
                    float fluidAccelerationJson = fluidProperties.get("fluid_acceleration").getAsFloat();
                    int fluidDensityJson = fluidProperties.get("fluid_density").getAsInt();
                    int fluidLuminosityJson = fluidProperties.get("fluid_luminosity").getAsInt();
                    int fluidViscosityJson = fluidProperties.get("fluid_viscosity").getAsInt();

                    material.setFluidProperties(fluidStateJson, fluidAccelerationJson, fluidDensityJson, fluidLuminosityJson, fluidViscosityJson);
                }
            }
        }

        if(jsonObject.has("tool_properties") && jsonObject.has("tool_types") && jsonObject.has("tool_parts")) {
            JsonArray toolProperties = jsonObject.get("tool_properties").getAsJsonArray();

            if(toolProperties.size()> 0) {
                JsonObject Properties = toolProperties.get(0).getAsJsonObject();
                JsonArray toolTypesArray = jsonObject.get("tool_types").getAsJsonArray();
                JsonArray toolParts = jsonObject.get("tool_parts").getAsJsonArray();

                {
                    if (Properties.has("attack_speed") && Properties.has("durability") && Properties.has("attack_damage") && Properties.has("weight") && jsonObject.has("tool_types") && jsonObject.has("tool_parts")) {
                        int attackSpeedJson = Properties.get("attack_speed").getAsInt();
                        int durabilityJson = Properties.get("durability").getAsInt();
                        float attackDamageJson = Properties.get("attack_damage").getAsFloat();
                        float weightJson = Properties.get("weight").getAsFloat();
                        List<Pair<ToolType, Integer>> toolTypesJsonList = new ArrayList<>();
                        List<IToolPart> toolPartJsonList = new ArrayList<>();

                        for (int i = 0; i < toolTypesArray.size(); i++) {
                            JsonObject toolTypeProperties = toolTypesArray.get(i).getAsJsonObject();
                            if (toolTypeProperties.has("tool_type") && toolTypeProperties.has("tool_level")) {
                                ToolType toolTypeJson = ToolType.get(toolTypeProperties.get("tool_type").getAsString());
                                int toolLevelJson = toolTypeProperties.get("tool_level").getAsInt();

                                toolTypesJsonList.add(new Pair<>(toolTypeJson, toolLevelJson));
                            }
                        }

                        for (int i = 0; i < toolParts.size(); i++) {
                            JsonObject toolPartProperties = toolParts.get(i).getAsJsonObject();
                            if (toolPartProperties.has("tool_part_prefix") && toolPartProperties.has("tool_part_suffix")) {
                                String toolPartPrefix = toolPartProperties.get("tool_part_prefix").getAsString();
                                String toolPartSuffix = toolPartProperties.get("tool_part_suffix").getAsString();

                                IToolPart toolPartJson = ToolPartRegistry.getToolPartByName(fixesToName(new Pair<>(toolPartPrefix, toolPartSuffix)));

                                toolPartJsonList.add(toolPartJson);
                            }
                        }

                        material.setToolProperties(attackSpeedJson, durabilityJson, attackDamageJson, weightJson, toolTypesJsonList, toolPartJsonList);
                    }
                }
            }
        }

        return material;
    }

    public static Map<String, ITMaterial> getMaterialsMap(List<ITMaterial> materials) {
        Map<String, ITMaterial> materialList = new LinkedHashMap<>();
        materials.forEach(itMaterial -> materialList.put(itMaterial.name, itMaterial));

        return materialList;
    }


}
