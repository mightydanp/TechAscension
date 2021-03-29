package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.RandomlyOnSurfaceGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.RandomlyOnSurfaceGenFeatureConfig;
import mightydanp.industrialtech.api.common.world.gen.feature.TopWaterCrop;
import mightydanp.industrialtech.api.common.world.gen.feature.TopWaterCropConfig;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MightyDanp on 2/18/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlantGenerationHandler {
    public static final RegistryObject<Feature<TopWaterCropConfig>> topWaterCrop = RegistryHandler.createFeature("top_water_crop", () -> new TopWaterCrop(TopWaterCropConfig.field_236566_a_));

    public static final RegistryObject<Feature<RandomlyOnSurfaceGenFeatureConfig>> topBlockSurface = RegistryHandler.createFeature("top_block_surface", () -> new RandomlyOnSurfaceGenFeature(RandomlyOnSurfaceGenFeatureConfig.field_236566_a_));

    protected static List<ConfiguredFeature<?, ?>> topWaterGenerateList = new ArrayList<>();
    protected static List<List<Biome.Category>> topWaterBiomesGenerateList = new ArrayList<>();

    protected static List<ConfiguredFeature<?, ?>> topBlockSurfaceGenerateList = new ArrayList<>();
    protected static List<List<Biome.Category>> topBlockSurfaceBiomesGenerateList = new ArrayList<>();

    public static void addTopCrop(String cropNameIn, BlockState TopStateIn, BlockState BellowStateIn, List<BlockState>  soilsIn, int rarityIn, boolean shallowWaterIn, boolean goesAboveWaterIn, int howTallIn, Biome.Category... biomesIn){
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        TopWaterCropConfig topWaterCropConfig = new TopWaterCropConfig(cropNameIn, TopStateIn, BellowStateIn, soilsIn, rarityIn, shallowWaterIn, goesAboveWaterIn, howTallIn);
        ConfiguredFeature<?, ?> topWaterCropFeature = topWaterCrop.get().withConfiguration(topWaterCropConfig);
        Registry.register(registry, new ResourceLocation(Ref.mod_id, topWaterCropConfig.cropName), topWaterCropFeature);
        topWaterGenerateList.add(topWaterCropFeature);
        topWaterBiomesGenerateList.add(Arrays.asList(biomesIn));
    }

    public static void addtopBlockSurfaceGenerate(String cropNameIn, BlockState blockStateIn, List<BlockState> soilsIn, int rarityIn, Biome.Category... biomesIn){
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        RandomlyOnSurfaceGenFeatureConfig topBlockSurfaceConfig = new RandomlyOnSurfaceGenFeatureConfig(cropNameIn, blockStateIn, soilsIn, 100, rarityIn);
        ConfiguredFeature<?, ?> topBlockSurfaceFeature = topBlockSurface.get().withConfiguration(topBlockSurfaceConfig);
        Registry.register(registry, new ResourceLocation(Ref.mod_id, topBlockSurfaceConfig.generationName), topBlockSurfaceFeature);

        topBlockSurfaceGenerateList.add(topBlockSurfaceFeature);
        topBlockSurfaceBiomesGenerateList.add(Arrays.asList(biomesIn));
    }

    @SubscribeEvent(priority= EventPriority.HIGH)
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        for(int i = 0; i < topWaterGenerateList.size(); i++){
            if(topWaterBiomesGenerateList.get(i).contains(event.getCategory())){
                event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, topWaterGenerateList.get(i));
                return true;
            }
        }

        for(int i = 0; i < topBlockSurfaceGenerateList.size(); i++){
            if(topBlockSurfaceBiomesGenerateList.get(i).contains(event.getCategory())){
                event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, topBlockSurfaceGenerateList.get(i));
                return true;
            }
        }

        return false;
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
        return Registry.register(Registry.FEATURE, key, value);
    }
}
