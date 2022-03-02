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
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 9/29/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreGenerationHandler {

    public static final RegistryObject<Feature<OreVeinGenFeatureConfig>> ore_vein = RegistryHandler.createFeature("ore_vein", () -> new OreVeinGenFeature(OreVeinGenFeatureConfig.CODEC));
    public static final RegistryObject<Feature<SmallOreVeinGenFeatureConfig>> small_ore = RegistryHandler.createFeature("small_ore", () -> new SmallOreVeinGenFeature(SmallOreVeinGenFeatureConfig.CODEC));

    private static final Map<ConfiguredFeature<?, ?>, List<Biome.Category>> oreGenList = new HashMap<>();
    private static final Map<ConfiguredFeature<?, ?>, List<Biome.Category>> smallOreGenList = new HashMap<>();

    public static void addRegistryOreGeneration(OreVeinGenFeatureConfig oreVeinGenFeatureConfigIn) {
        List<Biome.Category> biomes = new ArrayList<>();
        for(String biomeName : oreVeinGenFeatureConfigIn.biomes){
            biomes.add(Biome.Category.byName(biomeName));
        }

        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        ConfiguredFeature<?, ?> oreVeinFeature = ore_vein.get().configured(oreVeinGenFeatureConfigIn).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(oreVeinGenFeatureConfigIn.maxHeight, 0, oreVeinGenFeatureConfigIn.maxHeight)));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, oreVeinGenFeatureConfigIn.name), oreVeinFeature);

        OreVeinRegistry.register(oreVeinGenFeatureConfigIn);
        oreGenList.put(oreVeinFeature, biomes);
    }

    public static void addOreGeneration(String veinNameIn, int minRadiusIn, int numberOfSmallOreLayers, int minHeightIn, int maxHeightIn, int rarityIn, List<Biome.Category> biomesIn, Map<Block, Integer> materialOreIn) {
        List<Pair<String, Integer>> veinBlocksAndChances = new ArrayList<>();
        List<String> biomes = new ArrayList<>();

        biomesIn.forEach(o -> biomes.add(o.getName()));

        materialOreIn.forEach(((block, integer) -> {
            if (block.getRegistryName() != null) {
                Pair<String, Integer> veinBlockAndChance = new Pair<>(block.getRegistryName().toString(), integer);
                veinBlocksAndChances.add(veinBlockAndChance);
            }
        }));

        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        OreVeinGenFeatureConfig oreVeinGenFeatureConfig = new OreVeinGenFeatureConfig(veinNameIn, rarityIn, minHeightIn, maxHeightIn, minRadiusIn, numberOfSmallOreLayers, biomes, veinBlocksAndChances);
        ConfiguredFeature<?, ?> oreVeinFeature = ore_vein.get().configured(oreVeinGenFeatureConfig).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeightIn, 0, maxHeightIn)));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, oreVeinGenFeatureConfig.name), oreVeinFeature);

        OreVeinRegistry.register(oreVeinGenFeatureConfig);
        oreGenList.put(oreVeinFeature, biomesIn);
    }

    public static void addRegistrySmallOreVeinGeneration(SmallOreVeinGenFeatureConfig oreGenFeatureConfigIn) {
        List<Biome.Category> biomes = new ArrayList<>();
        for(String biomeName : oreGenFeatureConfigIn.biomes){
            biomes.add(Biome.Category.byName(biomeName));
        }

        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        ConfiguredFeature<?, ?> oreVeinFeature = small_ore.get().configured(oreGenFeatureConfigIn).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(oreGenFeatureConfigIn.minHeight, 0, oreGenFeatureConfigIn.maxHeight)));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, oreGenFeatureConfigIn.name), oreVeinFeature);

        smallOreGenList.put(oreVeinFeature, biomes);
    }

    public static void addSmallOreVeinGeneration(String smallOreNameIn, int minHeightIn, int maxHeightIn, int rarityIn, List<Biome.Category> biomesIn, Map<Object, Integer> materialOreIn) {
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

        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        SmallOreVeinGenFeatureConfig smallOreVeinGenFeatureConfig = new SmallOreVeinGenFeatureConfig(smallOreNameIn, rarityIn, minHeightIn, maxHeightIn, biomes, veinBlocksAndChances);
        ConfiguredFeature<?, ?> oreVeinFeature = small_ore.get().configured(smallOreVeinGenFeatureConfig).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeightIn, 0, maxHeightIn)));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, smallOreVeinGenFeatureConfig.name), oreVeinFeature);

        smallOreGenList.put(oreVeinFeature, biomesIn);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        Random rand = new Random();

        if (oreGenList.size() > 0) {
            int i = rand.nextInt(oreGenList.size());
            List<ConfiguredFeature<?, ?>> configuredFeatures = new ArrayList<>(oreGenList.keySet());
            List<List<Biome.Category>> biomes = new ArrayList<>(oreGenList.values());

            if (biomeCheck(biomes.get(i), event) || biomes.size() == 0) {
                event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, configuredFeatures.get(i));
            }
        }

        if (smallOreGenList.size() > 0) {

            int i = rand.nextInt(smallOreGenList.size());
            List<ConfiguredFeature<?, ?>> configuredFeatures = new ArrayList<>(smallOreGenList.keySet());
            List<List<Biome.Category>> biomes = new ArrayList<>(smallOreGenList.values());

            if (biomeCheck(biomes.get(i), event) || biomes.size() == 0) {
                event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, configuredFeatures.get(i));
            }
        }

        return true;
    }

    public static boolean biomeCheck(List<Biome.Category> biomeListVeinIn, BiomeLoadingEvent event) {
        for (Biome.Category biomes : biomeListVeinIn) {
            if (biomes == event.getCategory()) {
                return true;
            }
        }
        return false;
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
        return Registry.register(Registry.FEATURE, key, value);
    }

    public static void overrideFeatures(Biome biome) {
        List<ConfiguredFeature> features = new ArrayList<ConfiguredFeature>();

        for (List<Supplier<ConfiguredFeature<?, ?>>> f : biome.getGenerationSettings().features()) {
            for (Supplier<ConfiguredFeature<?, ?>> d : f) {
                if (d.get().feature instanceof OreFeature) {
                    if (((OreFeatureConfig) ((DecoratedFeatureConfig) d).feature.get().config).state.getBlock() == Blocks.COAL_ORE) {
                        features.add(d.get());
                    }
                    if (((OreFeatureConfig) ((DecoratedFeatureConfig) d).feature.get().config).state.getBlock() == Blocks.IRON_ORE) {
                        features.add(d.get());
                    }
                    if (((OreFeatureConfig) ((DecoratedFeatureConfig) d).feature.get().config).state.getBlock() == Blocks.GOLD_ORE) {
                        features.add(d.get());
                    }
                    if (((OreFeatureConfig) ((DecoratedFeatureConfig) d).feature.get().config).state.getBlock() == Blocks.DIAMOND_ORE) {
                        features.add(d.get());
                    }
                    if (((OreFeatureConfig) ((DecoratedFeatureConfig) d).feature.get().config).state.getBlock() == Blocks.EMERALD_ORE) {
                        features.add(d.get());
                    }
                    if (((OreFeatureConfig) ((DecoratedFeatureConfig) d).feature.get().config).state.getBlock() == Blocks.LAPIS_ORE) {
                        features.add(d.get());
                    }
                    if (((OreFeatureConfig) ((DecoratedFeatureConfig) d).feature.get().config).state.getBlock() == Blocks.REDSTONE_ORE) {
                        features.add(d.get());
                    }
                }
            }
        }
        biome.getGenerationSettings().features().removeAll(features);
    }
}
