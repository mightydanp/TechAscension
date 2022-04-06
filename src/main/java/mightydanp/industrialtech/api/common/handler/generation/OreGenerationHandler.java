package mightydanp.industrialtech.api.common.handler.generation;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.generation.orevein.OreVeinRegistry;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.OreVeinGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.OreVeinGenFeatureConfig;
import mightydanp.industrialtech.api.common.world.gen.feature.SmallOreVeinGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.SmallOreVeinGenFeatureConfig;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.function.Supplier;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratedFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraftforge.fmllegacy.RegistryObject;

/**
 * Created by MightyDanp on 9/29/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreGenerationHandler {

    public static final RegistryObject<Feature<OreVeinGenFeatureConfig>> ore_vein = RegistryHandler.createFeature("ore_vein", () -> new OreVeinGenFeature(OreVeinGenFeatureConfig.CODEC));
    public static final RegistryObject<Feature<SmallOreVeinGenFeatureConfig>> small_ore = RegistryHandler.createFeature("small_ore", () -> new SmallOreVeinGenFeature(SmallOreVeinGenFeatureConfig.CODEC));

    private static final Map<ConfiguredFeature<?, ?>, List<Biome.BiomeCategory>> oreGenList = new HashMap<>();
    private static final Map<ConfiguredFeature<?, ?>, List<Biome.BiomeCategory>> smallOreGenList = new HashMap<>();

    public static void addRegistryOreGeneration(OreVeinGenFeatureConfig oreVeinGenFeatureConfigIn) {
        List<Biome.BiomeCategory> biomes = new ArrayList<>();
        for(String biomeName : oreVeinGenFeatureConfigIn.biomes){
            biomes.add(Biome.BiomeCategory.byName(biomeName));
        }

        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        ConfiguredFeature<?, ?> oreVeinFeature = ore_vein.get().configured(oreVeinGenFeatureConfigIn).decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(
                BiasedToBottomHeight.of(VerticalAnchor.aboveBottom(oreVeinGenFeatureConfigIn.minHeight), VerticalAnchor.belowTop(oreVeinGenFeatureConfigIn.maxHeight), oreVeinGenFeatureConfigIn.maxHeight - oreVeinGenFeatureConfigIn.minHeight))));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, oreVeinGenFeatureConfigIn.name), oreVeinFeature);

        ((OreVeinRegistry)IndustrialTech.configSync.oreVein.getFirst()).register(oreVeinGenFeatureConfigIn);
        oreGenList.put(oreVeinFeature, biomes);
    }

    public static void addOreGeneration(String veinNameIn, int minRadiusIn, int numberOfSmallOreLayers, int minHeightIn, int maxHeightIn, int rarityIn, List<Biome.BiomeCategory> biomesIn, Map<Block, Integer> materialOreIn) {
        List<Pair<String, Integer>> veinBlocksAndChances = new ArrayList<>();
        List<String> biomes = new ArrayList<>();

        biomesIn.forEach(o -> biomes.add(o.getName()));

        materialOreIn.forEach(((block, integer) -> {
            if (block.getRegistryName() != null) {
                Pair<String, Integer> veinBlockAndChance = new Pair<>(block.getRegistryName().toString(), integer);
                veinBlocksAndChances.add(veinBlockAndChance);
            }
        }));

        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        OreVeinGenFeatureConfig oreVeinGenFeatureConfig = new OreVeinGenFeatureConfig(veinNameIn, rarityIn, minHeightIn, maxHeightIn, minRadiusIn, numberOfSmallOreLayers, biomes, veinBlocksAndChances);
        ConfiguredFeature<?, ?> oreVeinFeature = ore_vein.get().configured(oreVeinGenFeatureConfig).decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(
                BiasedToBottomHeight.of(VerticalAnchor.aboveBottom(minHeightIn), VerticalAnchor.belowTop(maxHeightIn), maxHeightIn - minHeightIn))));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, oreVeinGenFeatureConfig.name), oreVeinFeature);

        ((OreVeinRegistry)IndustrialTech.configSync.oreVein.getFirst()).register(oreVeinGenFeatureConfig);
        oreGenList.put(oreVeinFeature, biomesIn);
    }

    public static void addRegistrySmallOreVeinGeneration(SmallOreVeinGenFeatureConfig oreGenFeatureConfigIn) {
        List<Biome.BiomeCategory> biomes = new ArrayList<>();
        for(String biomeName : oreGenFeatureConfigIn.biomes){
            biomes.add(Biome.BiomeCategory.byName(biomeName));
        }

        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        ConfiguredFeature<?, ?> oreVeinFeature = small_ore.get().configured(oreGenFeatureConfigIn).decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(
                BiasedToBottomHeight.of(VerticalAnchor.aboveBottom(oreGenFeatureConfigIn.minHeight), VerticalAnchor.belowTop(oreGenFeatureConfigIn.maxHeight), oreGenFeatureConfigIn.maxHeight - oreGenFeatureConfigIn.minHeight))));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, oreGenFeatureConfigIn.name), oreVeinFeature);

        smallOreGenList.put(oreVeinFeature, biomes);
    }

    public static void addSmallOreVeinGeneration(String smallOreNameIn, int minHeightIn, int maxHeightIn, int rarityIn, List<Biome.BiomeCategory> biomesIn, Map<Object, Integer> materialOreIn) {
        List<Pair<String, Integer>> veinBlocksAndChances = new ArrayList<>();
        List<String> biomes = new ArrayList<>();

        biomesIn.forEach(o -> biomes.add(o.getName()));

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

        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        SmallOreVeinGenFeatureConfig smallOreVeinGenFeatureConfig = new SmallOreVeinGenFeatureConfig(smallOreNameIn, rarityIn, minHeightIn, maxHeightIn, biomes, veinBlocksAndChances);
        ConfiguredFeature<?, ?> oreVeinFeature = small_ore.get().configured(smallOreVeinGenFeatureConfig).decorated(FeatureDecorator.RANGE.configured(new RangeDecoratorConfiguration(
                BiasedToBottomHeight.of(VerticalAnchor.aboveBottom(minHeightIn), VerticalAnchor.belowTop(maxHeightIn), maxHeightIn - minHeightIn))));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, smallOreVeinGenFeatureConfig.name), oreVeinFeature);

        smallOreGenList.put(oreVeinFeature, biomesIn);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        Random rand = new Random();

        if (oreGenList.size() > 0) {
            int i = rand.nextInt(oreGenList.size());
            List<ConfiguredFeature<?, ?>> configuredFeatures = new ArrayList<>(oreGenList.keySet());
            List<List<Biome.BiomeCategory>> biomes = new ArrayList<>(oreGenList.values());

            if (biomeCheck(biomes.get(i), event) || biomes.size() == 0) {
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, configuredFeatures.get(i));
            }
        }

        if (smallOreGenList.size() > 0) {

            int i = rand.nextInt(smallOreGenList.size());
            List<ConfiguredFeature<?, ?>> configuredFeatures = new ArrayList<>(smallOreGenList.keySet());
            List<List<Biome.BiomeCategory>> biomes = new ArrayList<>(smallOreGenList.values());

            if (biomeCheck(biomes.get(i), event) || biomes.size() == 0) {
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, configuredFeatures.get(i));
            }
        }

        return true;
    }

    public static boolean biomeCheck(List<Biome.BiomeCategory> biomeListVeinIn, BiomeLoadingEvent event) {
        for (Biome.BiomeCategory biomes : biomeListVeinIn) {
            if (biomes == event.getCategory()) {
                return true;
            }
        }
        return false;
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String key, F value) {
        return Registry.register(Registry.FEATURE, key, value);
    }
}
