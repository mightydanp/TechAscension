package mightydanp.industrialtech.api.common.handler.generation;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.jsonconfig.generation.blocksinwater.BlocksInWaterRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.generation.randomsurface.RandomSurfaceRegistry;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.RandomSurfaceGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.RandomSurfaceGenFeatureConfig;
import mightydanp.industrialtech.api.common.world.gen.feature.BlocksInWaterGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.BlocksInWaterGenFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

/**
 * Created by MightyDanp on 2/18/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlantGenerationHandler {
    public static final RegistryObject<Feature<BlocksInWaterGenFeatureConfig>> topWaterCrop = RegistryHandler.createFeature("block_in_water", () -> new BlocksInWaterGenFeature(BlocksInWaterGenFeatureConfig.CODEC));

    public static final RegistryObject<Feature<RandomSurfaceGenFeatureConfig>> randomSurface = RegistryHandler.createFeature("random_surface", () -> new RandomSurfaceGenFeature(RandomSurfaceGenFeatureConfig.CODEC));

    private static final Map<ConfiguredFeature<?, ?>, List<Biome.Category>> blockInWaterGenerationList = new HashMap<>();
    private static final Map<ConfiguredFeature<?, ?>, List<Biome.Category>> randomSurfaceGenList = new HashMap<>();

    public static void addRegistryBlockInWaterGenerate(BlocksInWaterGenFeatureConfig blocksInWaterGenConfigInFeature){
        List<Biome.Category> biomes = new ArrayList<>();
        for(String biomeName : blocksInWaterGenConfigInFeature.biomes){
            biomes.add(Biome.Category.byName(biomeName));
        }

        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        ConfiguredFeature<?, ?> topWaterCropFeature = topWaterCrop.get().configured(blocksInWaterGenConfigInFeature);
        Registry.register(registry, new ResourceLocation(Ref.mod_id, blocksInWaterGenConfigInFeature.name), topWaterCropFeature);
        blockInWaterGenerationList.put(topWaterCropFeature, biomes);
    }

    public static void addBlockInWaterGenerate(String nameIn, int rarityIn, int heightIn, boolean shallowWaterIn, boolean goesAboveWaterIn, List<Biome.Category> biomesIn, List<Block>  validBlocksIn, Block topStateIn, Block bellowStateIn){
        List<String> validBlocks = new ArrayList<>();
        List<String> biomes = new ArrayList<>();

        String topState = "";
        String bellowState = "";

        validBlocksIn.forEach((block) -> {
            if (block.getRegistryName() != null) {
                validBlocks.add(block.getRegistryName().toString());
            }
        });

        biomesIn.forEach(o -> biomes.add(o.getName()));

        if (topStateIn.getRegistryName() != null) {
            topState = topStateIn.getRegistryName().toString();
        }

        if (bellowStateIn.getRegistryName() != null) {
            bellowState = bellowStateIn.getRegistryName().toString();
        }


        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        BlocksInWaterGenFeatureConfig waterGenConfig = new BlocksInWaterGenFeatureConfig(nameIn, rarityIn, heightIn, shallowWaterIn, goesAboveWaterIn, biomes, validBlocks, topState, bellowState);
        ConfiguredFeature<?, ?> topWaterCropFeature = topWaterCrop.get().configured(waterGenConfig);
        Registry.register(registry, new ResourceLocation(Ref.mod_id, waterGenConfig.name), topWaterCropFeature);
        BlocksInWaterRegistry.register(waterGenConfig);
        //blockInWaterGenerationList.put(topWaterCropFeature, biomesIn);
    }

    public static void addRegistryRandomSurfaceGenerate(RandomSurfaceGenFeatureConfig randomSurfaceGenFeatureConfigIn){
        List<Biome.Category> biomes = new ArrayList<>();
        for(String biomeName : randomSurfaceGenFeatureConfigIn.biomes){
            biomes.add(Biome.Category.byName(biomeName));
        }

        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        ConfiguredFeature<?, ?> randomSurfaceFeature = randomSurface.get().configured(randomSurfaceGenFeatureConfigIn);
        Registry.register(registry, new ResourceLocation(Ref.mod_id, randomSurfaceGenFeatureConfigIn.name), randomSurfaceFeature);
        randomSurfaceGenList.put(randomSurfaceFeature, biomes);
    }

    public static void addRandomSurfaceGenerate(String nameIn, int rarityIn, List<Biome.Category> biomesIn, List<Block> validBlocksIn, List<Block> blocksIn){
        List<String> validBlocks = new ArrayList<>();
        List<String> blocks = new ArrayList<>();
        List<String> biomes = new ArrayList<>();

        validBlocksIn.forEach((block) -> {
            if (block.getRegistryName() != null) {
                validBlocks.add(block.getRegistryName().toString());
            }
        });

        blocksIn.forEach((block) -> {
            if (block.getRegistryName() != null) {
                blocks.add(block.getRegistryName().toString());
            }
        });

        biomesIn.forEach(o -> biomes.add(o.getName()));

        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        RandomSurfaceGenFeatureConfig topBlockSurfaceConfig = new RandomSurfaceGenFeatureConfig(nameIn, rarityIn, biomes, blocks, validBlocks);
        ConfiguredFeature<?, ?> randomSurfaceFeature = randomSurface.get().configured(topBlockSurfaceConfig);
        Registry.register(registry, new ResourceLocation(Ref.mod_id, topBlockSurfaceConfig.name), randomSurfaceFeature);
        RandomSurfaceRegistry.register(topBlockSurfaceConfig);
        //randomSurfaceGenList.put(randomSurfaceFeature, biomesIn);
    }

    @SubscribeEvent(priority= EventPriority.HIGH)
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        Random rand = new Random();

        if(blockInWaterGenerationList.size() > 0) {
            List<ConfiguredFeature<?, ?>> configuredFeatures = new ArrayList<>(blockInWaterGenerationList.keySet());
            List<List<Biome.Category>> biomes = new ArrayList<>(blockInWaterGenerationList.values());
            for(int i = 0; i < blockInWaterGenerationList.size(); i++) {
                if (biomeCheck(biomes.get(i), event) || biomes.size() == 0) {
                    event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, configuredFeatures.get(i));
                }
            }
        }

        if(randomSurfaceGenList.size() > 0) {
            List<ConfiguredFeature<?, ?>> configuredFeatures = new ArrayList<>(randomSurfaceGenList.keySet());
            List<List<Biome.Category>> biomes = new ArrayList<>(randomSurfaceGenList.values());

            for(int i = 0; i < blockInWaterGenerationList.size(); i++) {
                if (biomeCheck(biomes.get(i), event) || biomes.size() == 0) {
                    event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, configuredFeatures.get(i));
                }
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
}
