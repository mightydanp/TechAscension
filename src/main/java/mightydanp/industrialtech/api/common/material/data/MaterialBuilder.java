package mightydanp.industrialtech.api.common.material.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.material.IFinishedMaterial;
import mightydanp.industrialtech.api.common.material.flag.IMaterialFlag;
import mightydanp.industrialtech.api.common.material.fluidstate.DefaultFluidState;
import mightydanp.industrialtech.api.common.material.fluidstate.IFluidState;
import mightydanp.industrialtech.api.common.material.ore.DefaultOreType;
import mightydanp.industrialtech.api.common.material.ore.IOreType;
import mightydanp.industrialtech.api.common.material.tool.part.flag.DefaultToolPart;
import mightydanp.industrialtech.api.common.material.tool.part.flag.IToolPart;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static mightydanp.industrialtech.api.common.material.flag.DefaultMaterialFlag.*;

/**
 * Created by MightyDanp on 11/23/2021.
 */
public class MaterialBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    public final String name;
    public final Integer color;
    public final String textureIcon;
    public final List<IMaterialFlag> materialFlags = new ArrayList<>();
    private String symbol = null;
    private Integer meltingPoint = null;
    private Integer boilingPoint = null;
    private IOreType oreType = null;
    private Integer denseOreDensity = null;
    private IFluidState fluidState = null;
    private Float fluidAcceleration = null;
    private Integer fluidDensity = null;
    private Integer fluidLuminosity = null;
    private Integer fluidViscosity = null;
    private Integer attackSpeed = null;
    private Integer durability = null;
    private Float attackDamage = null;
    private Float weight = null;
    private List<Pair<ToolType, Integer>> toolTypes = new ArrayList<>();
    private final List<IToolPart> toolPartFlags = new ArrayList<>();

    public MaterialBuilder(String nameIn, int colorIn, ResourceLocation textureIconIn) {
        name = nameIn;
        color = colorIn;
        textureIcon = textureIconIn.toString();
    }

    public static MaterialBuilder material(String nameIn, int colorIn, ResourceLocation textureIconIn) {
        return new MaterialBuilder(nameIn, colorIn, textureIconIn);
    }

    public MaterialBuilder init() {
        return this;
    }

    public MaterialBuilder addElementalLocalization(String symbolIn) {
        symbol = symbolIn;
        return this;
    }

    public MaterialBuilder addTemperatureProperties(int meltingPointIn, int boilingPointIn) {
        meltingPoint = meltingPointIn;
        boilingPoint = boilingPointIn;
        return this;
    }

    public MaterialBuilder setOreType(IOreType oreTypeIn) {
        oreType = oreTypeIn;

        if (oreTypeIn == DefaultOreType.ORE) {
            materialFlags.add(ORE);
        }

        if (oreTypeIn == DefaultOreType.GEM) {
            materialFlags.add(GEM);
        }

        if (oreTypeIn == DefaultOreType.CRYSTAL) {

        }

        return this;
    }

    public MaterialBuilder setDenseOreDensity(int denseOreDensityIn) {
        denseOreDensity = denseOreDensityIn;
        return this;
    }

    public MaterialBuilder addFluidProperties(IFluidState stateIn, float accelerationIn, Integer densityIn, Integer luminosityIn, Integer viscosityIn) {
        fluidState = stateIn;
        fluidAcceleration = accelerationIn;
        if (densityIn != null) fluidDensity = densityIn;
        if (luminosityIn != null) fluidLuminosity = luminosityIn;
        if (viscosityIn != null) fluidViscosity = viscosityIn;

        if (stateIn == DefaultFluidState.FLUID) {
            materialFlags.add(FLUID);
        }

        if (stateIn == DefaultFluidState.GAS) {
            materialFlags.add(GAS);
        }

        return this;
    }

    public MaterialBuilder addToolProperties(int attackSpeedIn, int durabilityIn, float attackDamageIn, float weightIn, List<Pair<ToolType, Integer>> toolTypesIn, DefaultToolPart... toolPartFlagsIn) {
        attackSpeed = attackSpeedIn;
        durability = durabilityIn;
        attackDamage = attackDamageIn;
        weight = weightIn;
        toolTypes = toolTypesIn;
        toolPartFlags.addAll(Arrays.asList(toolPartFlagsIn));
        return this;
    }

    public void save(Consumer<IFinishedMaterial> consumerIn, String modIDIn) {
        this.save(consumerIn, new ResourceLocation(modIDIn, name));
    }

    public void save(Consumer<IFinishedMaterial> consumer, ResourceLocation resourceLocation) {
        consumer.accept(new MaterialBuilder.ResultMaterial(
                resourceLocation, name, color, textureIcon, symbol, meltingPoint, boilingPoint, oreType, denseOreDensity, fluidState, fluidAcceleration, fluidDensity, fluidLuminosity, fluidViscosity, attackSpeed, durability, attackDamage, weight, toolTypes, toolPartFlags));
    }

    public static class ResultMaterial implements IFinishedMaterial {
        private final ResourceLocation id;
        private final String name;
        private final Integer color;
        private final String textureIcon;
        private final String symbol;
        private final Integer meltingPoint;
        private final Integer boilingPoint;
        private final IOreType oreType;
        private final Integer denseOreDensity;
        private final IFluidState fluidState;
        private final Float fluidAcceleration;
        private final Integer fluidDensity;
        private final Integer fluidLuminosity;
        private final Integer fluidViscosity;
        private final Integer attackSpeed;
        private final Integer durability;
        private final Float attackDamage;
        private final Float weight;
        private final List<Pair<ToolType, Integer>> toolTypes;
        private final List<IToolPart> toolPartFlags;

        public ResultMaterial(ResourceLocation resourceLocationIn, String nameIn, Integer colorIn, String textureIconIn, String symbolIn, Integer meltingPointIn, Integer boilingPointIn, IOreType oreTypeIn, Integer denseOreDensityIn, IFluidState fluidStateIn, Float fluidAccelerationIn, Integer fluidDensityIn, Integer fluidLuminosityIn, Integer fluidViscosityIn, Integer attackSpeedIn, Integer durabilityIn, Float attackDamageIn, Float weightIn, List<Pair<ToolType, Integer>> toolTypesIn, List<IToolPart> toolPartFlagsIn) {
            id = resourceLocationIn;
            name = nameIn;
            color = colorIn;
            textureIcon = textureIconIn;

            symbol = symbolIn;

            meltingPoint = meltingPointIn;
            boilingPoint = boilingPointIn;

            oreType = oreTypeIn;
            denseOreDensity = denseOreDensityIn;

            fluidState = fluidStateIn;
            fluidAcceleration = fluidAccelerationIn;
            fluidDensity = fluidDensityIn;
            fluidLuminosity = fluidLuminosityIn;
            fluidViscosity = fluidViscosityIn;

            attackSpeed = attackSpeedIn;
            durability = durabilityIn;
            attackDamage = attackDamageIn;
            weight = weightIn;
            toolTypes = toolTypesIn;
            toolPartFlags = toolPartFlagsIn;
        }

        public void serializeMaterialData(JsonObject jsonObject) {
            jsonObject.addProperty("name", name);
            jsonObject.addProperty("color", color);
            jsonObject.addProperty("texture_icon", textureIcon);

            JsonObject elementLocalization = new JsonObject();
            {
                if (symbol != null) {
                    elementLocalization.addProperty("symbol", symbol);
                }

                if (elementLocalization.size() > 0) {
                    jsonObject.add("element_localization", elementLocalization);
                }
            }

            JsonObject temperatureProperties = new JsonObject();
            {
                if (meltingPoint != null) {
                    temperatureProperties.addProperty("melting_point", meltingPoint);
                }

                if (boilingPoint != null) {
                    temperatureProperties.addProperty("boiling_point", boilingPoint);
                }

                if (temperatureProperties.size() > 0) {
                    jsonObject.add("temperature_properties", temperatureProperties);
                }
            }

            JsonObject oreProperties = new JsonObject();
            {
                if (oreType != null) {
                    oreProperties.addProperty("ore_type", oreType.getName());
                }

                if (denseOreDensity != null) {
                    oreProperties.addProperty("dense_ore_density", denseOreDensity);
                }

                if (oreProperties.size() > 0) {
                    jsonObject.add("ore_properties", oreProperties);
                }
            }

            JsonObject fluidProperties = new JsonObject();
            {
                if (fluidState != null) {
                    fluidProperties.addProperty("fluid_state", fluidState.getName());
                }

                if (fluidAcceleration != null) {
                    fluidProperties.addProperty("fluid_acceleration", fluidAcceleration);
                }

                if (fluidDensity != null) {
                    fluidProperties.addProperty("fluid_density", fluidDensity);
                }

                if (fluidLuminosity != null) {
                    fluidProperties.addProperty("fluid_luminosity", fluidLuminosity);
                }

                if (fluidViscosity != null) {
                    fluidProperties.addProperty("fluid_viscosity", fluidViscosity);
                }

                if (fluidProperties.size() > 0) {
                    jsonObject.add("fluid_properties", fluidProperties);
                }
            }

            JsonArray toolProperties = new JsonArray();
            {
                JsonObject properties = new JsonObject();
                {
                    if (attackSpeed != null) {
                        properties.addProperty("attack_speed", attackSpeed);
                    }

                    if (durability != null) {
                        properties.addProperty("durability", durability);
                    }

                    if (attackDamage != null) {
                        properties.addProperty("attack_damage", attackDamage);
                    }

                    if (weight != null) {
                        properties.addProperty("weight", weight);
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
            {
                for (Pair<ToolType, Integer> toolType : this.toolTypes) {
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
            {
                for (IToolPart toolPart : this.toolPartFlags) {
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
        }

        public ResourceLocation getId() {
            return this.id;
        }
    }
}