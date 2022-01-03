package mightydanp.industrialtech.api.common.material.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.api.common.material.fluidstate.FluidStateManager;
import mightydanp.industrialtech.api.common.material.fluidstate.IFluidState;
import mightydanp.industrialtech.api.common.material.icons.ITextureIcon;
import mightydanp.industrialtech.api.common.material.icons.TextureIconManager;
import mightydanp.industrialtech.api.common.material.ore.IOreType;
import mightydanp.industrialtech.api.common.material.ore.OreTypeManager;
import mightydanp.industrialtech.api.common.material.serializable.MaterialSerializable;
import mightydanp.industrialtech.api.common.material.tool.part.flag.IToolPart;
import mightydanp.industrialtech.api.common.material.tool.part.flag.ToolPartManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ToolType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by MightyDanp on 12/4/2021.
 */
public class MaterialSerializer {
    static final Logger LOGGER = LogManager.getLogger();

    public static void toNetwork(PacketBuffer buffer, ITMaterial material) {//friendlybotbuff
        buffer.writeUtf(material.name);
        buffer.writeInt(material.color);
        String textureIconString = material.textureIcon.getFirst() + ":" + material.textureIcon.getSecond().getName();

        buffer.writeUtf(textureIconString);

        if(material.symbol != null){
            buffer.writeUtf(material.symbol);
        }else {
            buffer.writeUtf("");
        }

        if(material.meltingPoint != null){
            buffer.writeInt(material.meltingPoint);
        } else {
            buffer.writeInt(-1);
        }

        if(material.boilingPoint != null){
            buffer.writeInt(material.boilingPoint);
        } else {
            buffer.writeInt(-1);
        }

        if(material.oreType != null){
            buffer.writeUtf(material.oreType.getName());
        }else {
            buffer.writeUtf("");
        }

        if(material.denseOreDensity != null){
            buffer.writeInt(material.denseOreDensity);
        } else {
            buffer.writeInt(-1);
        }

        if(material.fluidState != null){
            buffer.writeUtf(material.fluidState.getName());
        }else {
            buffer.writeUtf("");
        }

        if(material.fluidAcceleration != null){
            buffer.writeFloat(material.fluidAcceleration);
        } else {
            buffer.writeFloat(-1);
        }

        if(material.fluidDensity != null){
            buffer.writeInt(material.fluidDensity);
        } else {
            buffer.writeInt(-1);
        }

        if(material.fluidLuminosity != null){
            buffer.writeInt(material.fluidLuminosity);
        } else {
            buffer.writeInt(-1);
        }

        if(material.fluidViscosity != null){
            buffer.writeInt(material.fluidViscosity);
        } else {
            buffer.writeInt(-1);
        }

        if(material.attackSpeed != null){
            buffer.writeInt(material.attackSpeed);
        } else {
            buffer.writeInt(-1);
        }

        if(material.durability != null){
            buffer.writeInt(material.durability);
        } else {
            buffer.writeInt(-1);
        }

        if(material.attackDamage != null){
            buffer.writeFloat(material.attackDamage);
        } else {
            buffer.writeFloat(-1);
        }

        if(material.weight != null){
            buffer.writeFloat(material.weight);
        } else {
            buffer.writeFloat(-1);
        }

        if(material.toolTypes.size() > 0){
            buffer.writeInt(material.toolTypes.size());
            for (Pair<ToolType, Integer> toolType : material.toolTypes) {
                buffer.writeUtf(toolType.getFirst().getName());
                buffer.writeInt(toolType.getSecond());
            }
        } else {
            buffer.writeInt(0);
        }

        if(material.toolParts.size() > 0){
            buffer.writeInt(material.toolParts.size());
            for (IToolPart toolPart : material.toolParts) {
                buffer.writeUtf(toolPart.getPrefix());
                buffer.writeUtf(toolPart.getSuffix());
            }
        } else {
            buffer.writeInt(0);
        }
    }

    public static ITMaterial fromNetwork(PacketBuffer buffer) {
        String name = buffer.readUtf();
        Integer color = buffer.readInt();
        String textureIconString = buffer.readUtf();
        Pair<String, ITextureIcon> textureIcon = new Pair<>(textureIconString.split(":")[0], TextureIconManager.getTextureIconByName(textureIconString.split(":")[1]));
        ITMaterial material = new ITMaterial(name, color, textureIcon);

        String symbol = buffer.readUtf();
        if(!symbol.equals("")){
            material.setElementalLocalization(symbol);
        }

        Integer meltingPoint = buffer.readInt();
        Integer boilingPoint = buffer.readInt();

        if(meltingPoint != -1 && boilingPoint != -1){
            material.setTemperatureProperties(meltingPoint, boilingPoint);
        }

        String oreTypeString = buffer.readUtf();

        if(oreTypeString.equals("")){
            IOreType oreType = OreTypeManager.getOreTypeByName(oreTypeString);
            material.setOreType(oreType);
        }

        Integer denseOreDensity = buffer.readInt();
        if(denseOreDensity != -1){
            material.setDenseOreDensity(denseOreDensity);
        }

        String fluidStateString = buffer.readUtf();
        Float fluidAcceleration = buffer.readFloat();
        Integer fluidDensity = buffer.readInt();
        Integer fluidLuminosity = buffer.readInt();
        Integer fluidViscosity = buffer.readInt();

        if(!fluidStateString.equals("") && fluidAcceleration != -1 && fluidDensity != -1 && fluidLuminosity != -1 && fluidViscosity != -1){
            IFluidState fluidState = FluidStateManager.getFluidStateByName(fluidStateString);
            material.setFluidProperties(fluidState, fluidAcceleration, fluidDensity, fluidLuminosity, fluidViscosity);
        }

        Integer attackSpeed = buffer.readInt();
        Integer durability = buffer.readInt();
        Float attackDamage  = buffer.readFloat();
        Float weight  = buffer.readFloat();

        List<Pair<ToolType, Integer>> toolTypes = new ArrayList<>();
        for(int i = 0; i < buffer.readInt(); i++){
            toolTypes.add(new Pair<>(ToolType.get(buffer.readUtf()), buffer.readInt()));
        }

        List<IToolPart> toolPartFlags = new ArrayList<>();
        for(int i = 0; i < buffer.readInt(); i++){
            toolPartFlags.add(ToolPartManager.getToolPartFlagByFixes(new Pair<>(buffer.readUtf(), buffer.readUtf())));
        }

        if(attackSpeed != -1 && durability != -1 && attackDamage != -1 && weight != -1 && toolTypes.size() != 0 && toolPartFlags.size() != 0){
            material.setToolProperties(attackSpeed, durability, attackDamage, weight, toolTypes, toolPartFlags);
        }

        return material;
    }

    public static JsonObject getJsonObject(Path jsonPathIn) {
        JsonObject jsonObject = new JsonObject();

        try {
            if (Files.exists(jsonPathIn)) {
                try (BufferedReader bufferedReader = Files.newBufferedReader(jsonPathIn)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                        //System.out.println(line);
                    }

                    jsonObject = new JsonParser().parse(stringBuilder.toString()).getAsJsonObject();

                    return jsonObject;
                }
            }
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't read json {}", jsonPathIn, ioexception);

        }
        return jsonObject;
    }

    public static ITMaterial getFromJson(JsonObject jsonObject) {
        String nameJson = jsonObject.get("name").getAsString();
        int colorJson = jsonObject.get("color").getAsInt();
        String textureIconJson = jsonObject.get("texture_icon").getAsString();
        Pair<String, ITextureIcon> textureIcon = new Pair<>(textureIconJson.split(":")[0], TextureIconManager.getTextureIconByName(textureIconJson.split(":")[1]));
        ITMaterial material =  new ITMaterial(nameJson, colorJson, textureIcon);

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

        if(jsonObject.has("ore_properties")) {
            JsonObject oreProperties = jsonObject.get("ore_properties").getAsJsonObject();{
                if (oreProperties.has("ore_type") && oreProperties.has("dense_ore_density")) {
                    IOreType oreTypeJson = OreTypeManager.getOreTypeByName(oreProperties.get("ore_type").getAsString());
                    int denseOreDensityJson = oreProperties.get("dense_ore_density").getAsInt();
                    material.setOreType(oreTypeJson);
                    material.setDenseOreDensity(denseOreDensityJson);
                }
            }
        }

        if(jsonObject.has("fluid_properties")) {
            JsonObject fluidProperties = jsonObject.get("fluid_properties").getAsJsonObject();{
                if (fluidProperties.has("fluid_state") && fluidProperties.has("fluid_acceleration") && fluidProperties.has("fluid_density") && fluidProperties.has("fluid_luminosity") && fluidProperties.has("fluid_viscosity")) {
                    IFluidState fluidStateJson = FluidStateManager.getFluidStateByName(fluidProperties.get("fluid_state").getAsString());
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

                                IToolPart toolPartJson = ToolPartManager.getToolPartFlagByFixes(new Pair<>(toolPartPrefix, toolPartSuffix));

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

    private static NonNullList<Pair<ToolType, Integer>> materialToolTypesJson(JsonArray jsonArrayIn) {
        NonNullList<Pair<ToolType, Integer>> nonnulllist = NonNullList.create();

        for (int i = 0; i < jsonArrayIn.size(); ++i) {
            JsonObject jsonObject = jsonArrayIn.get(i).getAsJsonObject();
            nonnulllist.add(new Pair<>(ToolType.get(jsonObject.get("tool_type").getAsString()), jsonObject.get("tool_level").getAsInt()));
        }

        return nonnulllist;
    }

    private static NonNullList<IToolPart> materialToolPartsJson(JsonArray jsonArrayIn) {
        NonNullList<IToolPart> nonnulllist = NonNullList.create();

        for (int i = 0; i < jsonArrayIn.size(); ++i) {
            JsonObject jsonObject = jsonArrayIn.get(i).getAsJsonObject();
            nonnulllist.add(ToolPartManager.getToolPartFlagByFixes(new Pair<>(jsonObject.get("tool_part_prefix").getAsString(), jsonObject.get("tool_part_suffix").getAsString())));
        }

        return nonnulllist;
    }
}
