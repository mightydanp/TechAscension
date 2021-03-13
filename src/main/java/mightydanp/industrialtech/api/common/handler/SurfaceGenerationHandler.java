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
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MightyDanp on 3/3/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SurfaceGenerationHandler {
    public static final RegistryObject<Feature<RandomlyOnSurfaceGenFeatureConfig>> randomSurface = RegistryHandler.createFeature("random_surface", () -> new RandomlyOnSurfaceGenFeature(RandomlyOnSurfaceGenFeatureConfig.field_236566_a_));

    protected static List<Object> topWaterGenerateList = new ArrayList<>();

    public static void addRandomSurface(String cropNameIn, BlockState TopStateIn, BlockState BellowStateIn, List<BlockState>  soilsIn, int rarityIn, boolean shallowWaterIn, boolean goesAboveWaterIn, int howTallIn, Biome.Category... biomesIn){
        /*Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        RandomlyOnSurfaceGenFeatureConfig topWaterCropConfig = new RandomlyOnSurfaceGenFeatureConfig(cropNameIn, TopStateIn, BellowStateIn, soilsIn, rarityIn, shallowWaterIn, goesAboveWaterIn, howTallIn);
        ConfiguredFeature<?, ?> topWaterCropFeature = randomSurface.get().withConfiguration(topWaterCropConfig);
        Registry.register(registry, new ResourceLocation(Ref.mod_id, topWaterCropConfig.cropName), topWaterCropFeature);
        topWaterGenerateList.add(topWaterCropFeature);
        topWaterGenerateList.add(biomesIn);
        */
    }

    @SubscribeEvent(priority= EventPriority.HIGH)
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        /*
        List<ConfiguredFeature<?, ?>> topWaterGenerateNewList = new ArrayList<>();
        List<List<Biome.Category>> biomeList = new ArrayList<>();
        for(int i = 0; i <= topWaterGenerateList.size() - 1; i++){
            if(topWaterGenerateList.get(i) instanceof ConfiguredFeature<?, ?>){
                topWaterGenerateNewList.add((ConfiguredFeature<?, ?>)topWaterGenerateList.get(i));
            }else{
                if(topWaterGenerateList.get(i) instanceof Biome.Category[]) {
                    biomeList.add(Arrays.asList((Biome.Category[]) topWaterGenerateList.get(i)));
                }
            }
        }

        for(int i = 0; i < topWaterGenerateNewList.size(); i++){
            if(biomeList.get(i).contains(event.getCategory())){
                event.getGeneration().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, topWaterGenerateNewList.get(i));
                return true;
            }
        }

         */


        return false;
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
        return Registry.register(Registry.FEATURE, key, value);
    }
}