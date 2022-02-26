package mightydanp.industrialtech.api.common.handler.generation;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.generation.orevein.OreVeinRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.stonelayer.IStoneLayer;
import mightydanp.industrialtech.api.common.jsonconfig.stonelayer.StoneLayerRegistry;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.EnumVeinRarityFlags;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeatureConfig;
import mightydanp.industrialtech.api.common.world.gen.feature.SmallOreGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.SmallOreGenFeatureConfig;
import mightydanp.industrialtech.common.stonelayers.ModStoneLayers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
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
import java.util.stream.Collectors;

/**
 * Created by MightyDanp on 9/29/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreGenerationHandler {

    public static final RegistryObject<Feature<OreGenFeatureConfig>> ore_vein = RegistryHandler.createFeature("ore_vein", () -> new OreGenFeature(OreGenFeatureConfig.CODEC));
    public static final RegistryObject<Feature<SmallOreGenFeatureConfig>> small_ore = RegistryHandler.createFeature("small_ore", () -> new SmallOreGenFeature(SmallOreGenFeatureConfig.CODEC));

    protected static Map<ConfiguredFeature<?, ?>, List<Biome.Category>> oreGenList = new HashMap<>();
    protected static Map<ConfiguredFeature<?, ?>, List<Biome.Category>> smallOreGenList = new HashMap<>();

    public static void addOreGeneration(String veinNameIn, int minRadiusIn, int numberOfSmallOreLayers, int minHeightIn, int maxHeightIn, int rarityIn, Map<Block, Integer> materialOreIn, Biome.Category[] worldsIn){
        List<Pair<String, Integer>> veinBlocksAndChances = new ArrayList<>();

        materialOreIn.forEach(((block, integer) -> {
            if(block.getRegistryName() != null) {
                Pair<String, Integer> veinBlockAndChance = new Pair<>(block.getRegistryName().toString(), integer);
                veinBlocksAndChances.add(veinBlockAndChance);
            }
        }));

        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        OreGenFeatureConfig oreGenFeatureConfig = new OreGenFeatureConfig(veinNameIn, rarityIn, minHeightIn, maxHeightIn, minRadiusIn, numberOfSmallOreLayers, veinBlocksAndChances);
        ConfiguredFeature<?, ?> oreVeinFeature = ore_vein.get().configured(oreGenFeatureConfig).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeightIn, 0, maxHeightIn)));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, oreGenFeatureConfig.veinName), oreVeinFeature);

        OreVeinRegistry.register(oreGenFeatureConfig);
        oreGenList.put(oreVeinFeature, Arrays.asList(worldsIn));
    }

    public static void addSmallOreGeneration(String SmallOreNameIn, int minHeightIn, int maxHeightIn, int rarityIn, List<Object> materialOreIn, Biome.Category[] worldsIn){
        List<Integer> intList = new ArrayList<>();
        List<List<BlockState>> smallOreBlocks = new ArrayList<>();

        for(Object obj : materialOreIn){
            if(obj instanceof Integer){
                intList.add((Integer)obj);
            }

            if(obj instanceof ITMaterial) {
                List<BlockState> tempList2 = new ArrayList<>();
                for (Block ore : ((ITMaterial) obj).smallOreList) {
                    tempList2.add(ore.defaultBlockState());
                }

                smallOreBlocks.add(tempList2);
            }
        }

        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        SmallOreGenFeatureConfig smallOreGenFeatureConfig = new SmallOreGenFeatureConfig(SmallOreNameIn, smallOreBlocks, intList, rarityIn);
        ConfiguredFeature<?, ?> oreVeinFeature = small_ore.get().configured(smallOreGenFeatureConfig).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeightIn, 0, maxHeightIn)));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, smallOreGenFeatureConfig.smallOreName), oreVeinFeature);

        smallOreGenList.put(oreVeinFeature, Arrays.asList(worldsIn));
    }

    @SubscribeEvent(priority= EventPriority.HIGH)
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        Random rand = new Random();
        boolean hasSpawnedVein = false;

        while(!hasSpawnedVein){
            int i = rand.nextInt(oreGenList.size());
            List<ConfiguredFeature<?, ?>> configuredFeatures = new ArrayList<>(oreGenList.keySet());
            List<List<Biome.Category>> biomes = new ArrayList<>(oreGenList.values());

            if (biomeCheck(biomes.get(i), event)) {
                event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, configuredFeatures.get(i));
                hasSpawnedVein = true;
            }
        }

        hasSpawnedVein = false;

        while(!hasSpawnedVein){
            int i = rand.nextInt(smallOreGenList.size());
            List<ConfiguredFeature<?, ?>> configuredFeatures = new ArrayList<>(smallOreGenList.keySet());
            List<List<Biome.Category>> biomes = new ArrayList<>(smallOreGenList.values());

            if (biomeCheck(biomes.get(i), event)) {
                event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, configuredFeatures.get(i));
                hasSpawnedVein = true;
            }
        }

        return true;
    }

    public static boolean biomeCheck(List<Biome.Category> biomeListVeinIn, BiomeLoadingEvent event){
        for(Biome.Category biomes : biomeListVeinIn) {
            if(biomes == event.getCategory()){
                return true;
            }
        }
        return false;
    }

    public static boolean biomeCheck(Collection<List<Biome.Category>> biomeListVeinIn, BiomeLoadingEvent event){

        return biomeListVeinIn.contains(event.getCategory());
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
        return Registry.register(Registry.FEATURE, key, value);
    }

    public static void overrideFeatures(Biome biome){
        List<ConfiguredFeature> features = new ArrayList<ConfiguredFeature>();

        for (List<Supplier<ConfiguredFeature<?, ?>>> f : biome.getGenerationSettings().features()) {
            for ( Supplier<ConfiguredFeature<?, ?>> d : f) {
                if(d.get().feature instanceof OreFeature) {
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
