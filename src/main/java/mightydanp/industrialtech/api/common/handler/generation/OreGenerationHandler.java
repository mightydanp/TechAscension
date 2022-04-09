package mightydanp.industrialtech.api.common.handler.generation;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.generation.orevein.OreVeinRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.generation.smallore.SmallOreVeinRegistry;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.OreVeinGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.OreVeinGenFeatureConfig;
import mightydanp.industrialtech.api.common.world.gen.feature.SmallOreVeinGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.SmallOreVeinGenFeatureConfig;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 9/29/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreGenerationHandler {

    public static final RegistryObject<Feature<OreVeinGenFeatureConfig>> ore_vein = RegistryHandler.createFeature("ore_vein", () -> new OreVeinGenFeature(OreVeinGenFeatureConfig.CODEC));
    public static final RegistryObject<Feature<SmallOreVeinGenFeatureConfig>> small_ore = RegistryHandler.createFeature("small_ore", () -> new SmallOreVeinGenFeature(SmallOreVeinGenFeatureConfig.CODEC));

    private static final Map<String, MapWrapper> oreGenList = new HashMap<>();
    private static final Map<String, MapWrapper> smallOreGenList = new HashMap<>();

    public static void addRegistryOreGeneration(OreVeinGenFeatureConfig config) {
        Holder<ConfiguredFeature<OreVeinGenFeatureConfig, ?>> featureHolder = register(config.name, new ConfiguredFeature<>(ore_vein.get(), config));

        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        list.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(config.minHeight), VerticalAnchor.absolute(config.maxHeight)));
        list.add(CountPlacement.of(config.rarity));

        Holder<PlacedFeature> placedFeature = createPlacedFeature(config.name, featureHolder, list.toArray(new PlacementModifier[0]));
        ((OreVeinRegistry)IndustrialTech.configSync.oreVein.getFirst()).register(config);
        oreGenList.put(config.name, new MapWrapper(placedFeature, config.dimensions, config.validBiomes, config.invalidBiomes));
    }

    public static void addOreGeneration(String veinNameIn, int minRadiusIn, int numberOfSmallOreLayers, int minHeightIn, int maxHeightIn, int rarityIn, List<String> dimensions, List<String> validBiomes, List<String> invalidBiomes, Map<Block, Integer> materialOreIn) {
        List<Pair<String, Integer>> veinBlocksAndChances = new ArrayList<>();
        materialOreIn.forEach(((block, integer) -> {
            if (block.getRegistryName() != null) {
                Pair<String, Integer> veinBlockAndChance = new Pair<>(block.getRegistryName().toString(), integer);
                veinBlocksAndChances.add(veinBlockAndChance);
            }
        }));

        OreVeinGenFeatureConfig config = new OreVeinGenFeatureConfig(veinNameIn, rarityIn, minHeightIn, maxHeightIn, minRadiusIn, numberOfSmallOreLayers, dimensions, validBiomes, invalidBiomes, veinBlocksAndChances);

        Holder<ConfiguredFeature<OreVeinGenFeatureConfig, ?>> oreVeinFeature = register(config.name, new ConfiguredFeature<>(ore_vein.get(), config));


        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        list.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(config.minHeight), VerticalAnchor.absolute(config.maxHeight)));
        list.add(CountPlacement.of(config.rarity));

        Holder<PlacedFeature> placedFeature = createPlacedFeature(config.name, oreVeinFeature, list.toArray(new PlacementModifier[0]));
        ((OreVeinRegistry)IndustrialTech.configSync.oreVein.getFirst()).register(config);
        oreGenList.put(config.name, new MapWrapper(placedFeature, config.dimensions, config.validBiomes, config.invalidBiomes));
    }

    public static void addRegistrySmallOreVeinGeneration(SmallOreVeinGenFeatureConfig config) {
        Holder<ConfiguredFeature<SmallOreVeinGenFeatureConfig, ?>> smallOreFeature = register(config.name, new ConfiguredFeature<>(small_ore.get(), config));
        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        list.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(config.minHeight), VerticalAnchor.absolute(config.maxHeight)));
        list.add(CountPlacement.of(config.rarity));

        Holder<PlacedFeature> placedFeature = createPlacedFeature(config.name, smallOreFeature, list.toArray(new PlacementModifier[0]));
        ((SmallOreVeinRegistry)IndustrialTech.configSync.smallOre.getFirst()).register(config);
        smallOreGenList.put(config.name, new MapWrapper(placedFeature, config.dimensions, config.validBiomes, config.invalidBiomes));
    }

    public static void addSmallOreVeinGeneration(String smallOreNameIn, int minHeightIn, int maxHeightIn, int rarityIn, List<String> dimensions, List<String> validBiomes, List<String> invalidBiomes, Map<Object, Integer> materialOreIn) {
        List<Pair<String, Integer>> veinBlocksAndChances = new ArrayList<>();
        SmallOreVeinGenFeatureConfig config = new SmallOreVeinGenFeatureConfig(smallOreNameIn, rarityIn, minHeightIn, maxHeightIn, dimensions, validBiomes, invalidBiomes, veinBlocksAndChances);

        materialOreIn.forEach(((object, integer) -> {
            if(object instanceof Block) {
                if (((Block) object).getRegistryName() != null) {
                    Pair<String, Integer> veinBlockAndChance = new Pair<>(((Block) object).getRegistryName().toString(), integer);
                    veinBlocksAndChances.add(veinBlockAndChance);
                }
            }

            if(object instanceof ITMaterial) {
                if (((ITMaterial) object).getRegistryName() != null) {
                    Pair<String, Integer> veinBlockAndChance = new Pair<>(((ITMaterial) object).getRegistryName().toString(), integer);
                    veinBlocksAndChances.add(veinBlockAndChance);
                }
            }
        }));


        Holder<ConfiguredFeature<SmallOreVeinGenFeatureConfig, ?>> smallOreFeature = register(config.name, new ConfiguredFeature<>(small_ore.get(), config));

        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        list.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(config.minHeight), VerticalAnchor.absolute(config.maxHeight)));
        list.add(CountPlacement.of(config.rarity));

        Holder<PlacedFeature> placedFeature = createPlacedFeature(config.name, smallOreFeature, list.toArray(new PlacementModifier[0]));
        ((SmallOreVeinRegistry)IndustrialTech.configSync.smallOre.getFirst()).register(config);
        smallOreGenList.put(config.name, new MapWrapper(placedFeature, config.dimensions, config.validBiomes, config.invalidBiomes));
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        if (oreGenList.size() > 0) {
            BiomeGenerationSettingsBuilder builder = event.getGeneration();
            oreGenList.forEach(((s, mapWrapper) -> {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, mapWrapper.feature());
            }));
        }

        if (smallOreGenList.size() > 0) {
            BiomeGenerationSettingsBuilder builder = event.getGeneration();
            smallOreGenList.forEach(((s, mapWrapper) -> {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, mapWrapper.feature());
            }));
        }

        return true;
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String id, ConfiguredFeature<FC, F> cf) {
        ResourceLocation realId = new ResourceLocation(Ref.mod_id, id);
        Preconditions.checkState(!BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(realId), "Duplicate ID: %s", id);
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, realId.toString(), cf);
    }

    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> createPlacedFeature(String id, Holder<ConfiguredFeature<FC, ?>> feature, PlacementModifier... placementModifiers) {
        ResourceLocation realID = new ResourceLocation(Ref.mod_id, id);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(realID))
            throw new IllegalStateException("Placed Feature ID: \"" + realID.toString() + "\" already exists in the Placed Features registry!");

        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, realID, new PlacedFeature(Holder.hackyErase(feature), List.of(placementModifiers)));
    }

    record MapWrapper(Holder<PlacedFeature> feature, List<String> dimensions, List<String> validBiomes, List<String> invalidBiomes){}


}
