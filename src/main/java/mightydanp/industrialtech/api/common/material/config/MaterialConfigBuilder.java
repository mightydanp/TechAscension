package mightydanp.industrialtech.api.common.material.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.material.flag.IMaterialFlag;
import mightydanp.industrialtech.api.common.material.fluidstate.DefaultFluidState;
import mightydanp.industrialtech.api.common.material.fluidstate.IFluidState;
import mightydanp.industrialtech.api.common.material.ore.DefaultOreType;
import mightydanp.industrialtech.api.common.material.ore.IOreType;
import mightydanp.industrialtech.api.common.material.tool.part.flag.DefaultToolPart;
import mightydanp.industrialtech.api.common.material.tool.part.flag.IToolPart;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ToolType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import static mightydanp.industrialtech.api.common.material.flag.DefaultMaterialFlag.*;

/**
 * Created by MightyDanp on 11/23/2021.
 */
public class MaterialConfigBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    public final String name;
    public final Integer color;
    public final String textureIconsLocation;
    public final List<IMaterialFlag> materialFlags = new ArrayList<>();
    private final List<IToolPart> toolPartFlags = new ArrayList<>();
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

    public MaterialConfigBuilder(String nameIn, int colorIn, ResourceLocation textureIconsLocationIn) {
        name = nameIn;
        color = colorIn;
        textureIconsLocation = textureIconsLocationIn.toString();
    }

    public static MaterialConfigBuilder material(String nameIn, int colorIn, ResourceLocation textureIconsLocationIn) {
        return new MaterialConfigBuilder(nameIn, colorIn, textureIconsLocationIn);
    }

    public MaterialConfigBuilder init() {
        return this;
    }

    public MaterialConfigBuilder addElementalLocalization(String symbolIn) {
        symbol = symbolIn;
        return this;
    }

    public MaterialConfigBuilder addTemperatureProperties(int meltingPointIn, int boilingPointIn) {
        meltingPoint = meltingPointIn;
        boilingPoint = boilingPointIn;
        return this;
    }

    public MaterialConfigBuilder setOreType(IOreType oreTypeIn) {
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

    public MaterialConfigBuilder setDenseOreDensity(int denseOreDensityIn) {
        denseOreDensity = denseOreDensityIn;
        return this;
    }

    public MaterialConfigBuilder addFluidProperties(IFluidState stateIn, float accelerationIn, Integer densityIn, Integer luminosityIn, Integer viscosityIn) {
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

    public MaterialConfigBuilder addToolProperties(int attackSpeedIn, int durabilityIn, float attackDamageIn, float weightIn, List<Pair<ToolType, Integer>> toolTypesIn, DefaultToolPart... toolPartFlagsIn) {
        attackSpeed = attackSpeedIn;
        durability = durabilityIn;
        attackDamage = attackDamageIn;
        weight = weightIn;
        toolTypes = toolTypesIn;
        toolPartFlags.addAll(Arrays.asList(toolPartFlagsIn));
        return this;
    }

    public void save(Consumer<IConfigMaterial> consumerIn, String modIDIn) {
        //ResourceLocation resourcelocation = Registry.ITEM.getKey(name);
        //if ((new ResourceLocation(modIDIn, name)).equals(resourcelocation)) {
        //     throw new IllegalStateException("Material " + name + " should remove its 'save' argument");
        //} else {
        this.save(consumerIn, new ResourceLocation(modIDIn, name));
        //}
    }

    public void save(Consumer<IConfigMaterial> consumer, ResourceLocation resourceLocation) {
        consumer.accept(new MaterialConfigBuilder.ResultMaterial(
                resourceLocation, name, color, textureIconsLocation, symbol, meltingPoint, boilingPoint, oreType, denseOreDensity, fluidState, fluidAcceleration, fluidDensity, fluidLuminosity, fluidViscosity, attackSpeed, durability, attackDamage, weight, toolTypes, toolPartFlags));
    }

    public static class ResultMaterial implements IConfigMaterial {
        private final ResourceLocation id;
        private final String name;
        private final Integer color;
        private final String textureIconsLocation;
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
        private final List<IToolPart> toolParts;

        public ResultMaterial(ResourceLocation resourceLocationIn, String nameIn, Integer colorIn, String textureIconsLocationIn, String symbolIn, Integer meltingPointIn, Integer boilingPointIn, IOreType oreTypeIn, Integer denseOreDensityIn, IFluidState fluidStateIn, Float fluidAccelerationIn, Integer fluidDensityIn, Integer fluidLuminosityIn, Integer fluidViscosityIn, Integer attackSpeedIn, Integer durabilityIn, Float attackDamageIn, Float weightIn, List<Pair<ToolType, Integer>> toolTypesIn, List<IToolPart> toolPartFlagsIn) {
            id = resourceLocationIn;
            name = nameIn;
            color = colorIn;
            textureIconsLocation = textureIconsLocationIn;

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
            toolParts = toolPartFlagsIn;
        }

        public void serializeMaterialData(ForgeConfigSpec.Builder builder) {
            builder.push("general_properties");
            ForgeConfigSpec.ConfigValue<String> nameCfg = builder.comment("Sets material name. Default for this material is: " + name).translation(Ref.mod_id + ":material.comment.general_properties.name").define("name", name);
            ForgeConfigSpec.ConfigValue<Integer> colorCfg = builder.comment("Sets material color. Default for this material is: " + color).translation(Ref.mod_id + ":material.comment.general_properties.color").define("color", color);
            ForgeConfigSpec.ConfigValue<String> textureIconsLocationCfg = builder.comment("Sets material icons location point. Default for this material is: " + textureIconsLocation).translation(Ref.mod_id + ":material.comment.general_properties.textureIconLocation").define("texture_icons_location", textureIconsLocation);

            if(symbol != null) {
                builder.push("element_localization");
                ForgeConfigSpec.ConfigValue<String> symbolCfg = builder.comment("Set material symbol point. Default for this material is: " + symbol).translation(Ref.mod_id + ":material.comment.element_localization.symbol").define("symbol", symbol);
                builder.pop();
            }

            if(meltingPoint != null || boilingPoint != null) {
                builder.push("temperature_properties");
                if(meltingPoint != null) {
                    ForgeConfigSpec.ConfigValue<Integer> meltingPointCfg = builder.comment("Set material melting point. Default for this material is: " + meltingPoint).translation(Ref.mod_id + ":material.comment.temperature_properties.melting_point").define("melting_point", meltingPoint);
                }
                if(boilingPoint != null) {
                    ForgeConfigSpec.ConfigValue<Integer> boilingPointCfg = builder.comment("Set material boiling point. Default for this material is: " + boilingPoint).translation(Ref.mod_id + ":material.comment.temperature_properties.boiling_point").define("boiling_point", boilingPoint);
                }
                builder.pop();
            }

            if(oreType != null || denseOreDensity != null) {
                builder.push("ore_properties");
                if(oreType != null) {
                    ForgeConfigSpec.ConfigValue<String> oreTypeCfg = builder.comment("Set material ore type. Default for this material is: " + oreType).translation(Ref.mod_id + ":material.comment.ore_properties.ore_type").define("ore_type", oreType.getName());
                }
                if(denseOreDensity != null) {
                    ForgeConfigSpec.ConfigValue<Integer> denseOreDensityCfg = builder.comment("Set material dense ore density. Default for this material is: " + denseOreDensity).translation(Ref.mod_id + ":material.comment.ore_properties.dense_ore_density").define("dense_ore_density", denseOreDensity);
                }
                builder.pop();
            }

            if(fluidState != null || fluidAcceleration != null || fluidDensity != null || fluidLuminosity != null || fluidViscosity != null) {
                builder.push("fluid_properties");
                if (fluidState != null) {
                    ForgeConfigSpec.ConfigValue<String> fluidStateCfg  = builder.comment("Set material fluid state. Default for this material is: " + fluidState).translation(Ref.mod_id + ":material.comment.fluid_properties.oreType").define("fluid_state", fluidState.getName());
                }

                if (fluidAcceleration != null) {
                    ForgeConfigSpec.ConfigValue<Float> fluidAccelerationCfg  = builder.comment("Set material fluid fluid acceleration. Default for this material is: " + fluidAcceleration).translation(Ref.mod_id + ":material.comment.fluid_properties.fluid_acceleration").define("fluid_acceleration", fluidAcceleration);
                }

                if (fluidDensity != null) {
                    ForgeConfigSpec.ConfigValue<Integer> fluidDensityCfg  = builder.comment("Set material fluid density. Default for this material is: " + fluidDensity).translation(Ref.mod_id + ":material.comment.fluid_properties.fluid_density").define("fluid_density", fluidDensity);
                }

                if (fluidLuminosity != null) {
                    ForgeConfigSpec.ConfigValue<Integer> fluidLuminosityCfg  = builder.comment("Set material fluid luminosity. Default for this material is: " + fluidLuminosity).translation(Ref.mod_id + ":material.comment.fluid_properties.fluid_luminosity").define("fluid_luminosity", fluidLuminosity);
                }

                if (fluidViscosity != null) {
                    ForgeConfigSpec.ConfigValue<Integer> fluidViscosityCfg  = builder.comment("Set material fluid viscosity. Default for this material is: " + fluidViscosity).translation(Ref.mod_id + ":material.comment.fluid_properties.fluid_viscosity").define("fluid_viscosity", fluidViscosity);
                }

                builder.pop();
            }

            if(attackSpeed != null || durability != null || attackDamage != null|| weight != null || toolTypes.size() > 0) {
                builder.push("tool_properties");
                if (attackSpeed != null) {
                    ForgeConfigSpec.ConfigValue<Integer> attackSpeedCfg = builder.comment("Set material attack speed. Default for this material is: " + attackSpeed).translation(Ref.mod_id + ":material.comment.tool_properties.attackSpeed").define("attack_speed", attackSpeed);
                }

                if (durability != null) {
                    ForgeConfigSpec.ConfigValue<Integer> durabilityCfg = builder.comment("Set material durability. Default for this material is: " + durability).translation(Ref.mod_id + ":material.comment.tool_properties.durability").define("durability", durability);
                }

                if (attackDamage != null) {
                    ForgeConfigSpec.ConfigValue<Float> attackDamageCfg = builder.comment("Set material attack damage. Default for this material is: " + attackDamage).translation(Ref.mod_id + ":material.comment.tool_properties.attack_damage").define("attack_damage", attackDamage);
                }

                if (weight != null) {
                    ForgeConfigSpec.ConfigValue<Float> weightCfg = builder.comment("Set material weight. Default for this material is: " + weight).translation(Ref.mod_id + ":material.comment.tool_properties.weight").define("weight", weight);
                }

                if (toolTypes.size() > 0) {
                    builder.push("tool_types");
                    List<String> toolTypesList = new ArrayList<>();
                    //List<Integer> toolTypesLevelList = new ArrayList<>();
                    for(Pair<ToolType, Integer> toolTypePair : toolTypes){
                        toolTypesList.add(toolTypePair.getFirst().getName() + "|" + toolTypePair.getSecond());
                    }

                    ForgeConfigSpec.ConfigValue<List<? extends String>> toolTypesCfg = builder.comment("Set material tool types. Default for this material is: " + toolTypesList).translation(Ref.mod_id + ":material.comment.tool_properties.tool_types").defineList("tool_types", toolTypesList, s -> s instanceof String);
                    //ForgeConfigSpec.ConfigValue<List<? extends Integer>> toolTypesLevelCfg = builder.comment("Set material tool types Level. Default for this material is: " + toolTypesLevelList).translation(Ref.mod_id + ":material.comment.tool_properties.tool_types_level").defineList("tool_types_level", toolTypesLevelList, s -> s instanceof Integer);
                    builder.pop();
                }

                if (toolParts.size() > 0) {
                    builder.push("tool_parts");
                    List<String> toolPartsList = new ArrayList<>();
                    //List<String> toolPartsPrefixesList = new ArrayList<>();
                    //List<String> toolPartsSuffixesList = new ArrayList<>();

                    for(IToolPart toolPart : toolParts){
                        toolPartsList.add(toolPart.getFixes().getFirst() + "|" + toolPart.getFixes().getSecond());
                        //toolPartsPrefixesList.add(toolPart.getFixes().getFirst());
                        //toolPartsSuffixesList.add(toolPart.getFixes().getSecond());
                    }

                    ForgeConfigSpec.ConfigValue<List<? extends String>> toolPartsCfg = builder.comment("Set material tool parts. Default for this material is: " + toolPartsList).translation(Ref.mod_id + ":material.comment.tool_properties.tool_parts").defineList("tool_parts", toolPartsList, s -> s instanceof String);
                    //ForgeConfigSpec.ConfigValue<List<? extends String>> toolPartsPrefixesCfg = builder.comment("Set material tool part prefixes. Default for this material is: " + toolPartsPrefixesList).translation(Ref.mod_id + ":material.comment.tool_properties.tool_parts_prefixes").defineList("tool_parts_prefixes", toolPartsPrefixesList, s -> s instanceof String);
                    //ForgeConfigSpec.ConfigValue<List<? extends String>> toolPartsSuffixesCfg = builder.comment("Set material tool part suffixes. Default for this material is: " + toolPartsSuffixesList).translation(Ref.mod_id + ":material.comment.tool_properties.tool_parts_suffixes").defineList("tool_parts_suffixes", toolPartsSuffixesList, s -> s instanceof String);
                    builder.pop();
                }

                builder.pop();
            }
            builder.pop();
        }

        public ResourceLocation getId() {
            return this.id;
        }
    }
}