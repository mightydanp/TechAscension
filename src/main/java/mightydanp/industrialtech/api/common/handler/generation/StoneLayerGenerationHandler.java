package mightydanp.industrialtech.api.common.handler.generation;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.fmllegacy.RegistryObject;

/**
 * Created by MightyDanp on 9/8/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StoneLayerGenerationHandler {

    public static final RegistryObject<Feature<ThinSlabGenFeatureConfig>> thin_slab = RegistryHandler.createFeature("thin_slab", () -> new ThinSlabGenFeature(ThinSlabGenFeatureConfig.CODEC));

    protected static List<ConfiguredFeature<?, ?>> thinSlabGenerateList = new ArrayList<>();
    protected static List<List<Biome.BiomeCategory>> thinSlabBiomesGenerateList = new ArrayList<>();

    public static void addThinSlabGenerate(String slabNameIn, List<BlockState> slabsIn, int rarityIn, boolean inWaterIn, boolean outOfWaterIn, Biome.BiomeCategory... biomesIn){
        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        ThinSlabGenFeatureConfig thinSlabConfig = new ThinSlabGenFeatureConfig(slabNameIn, slabsIn, rarityIn, inWaterIn, outOfWaterIn);
        ConfiguredFeature<?, ?> thinSlabFeature = thin_slab.get().configured(thinSlabConfig);
        Registry.register(registry, new ResourceLocation(Ref.mod_id, thinSlabConfig.generationName), thinSlabFeature);

        thinSlabGenerateList.add(thinSlabFeature);
        thinSlabBiomesGenerateList.add(Arrays.asList(biomesIn));
    }

    @SubscribeEvent(priority= EventPriority.HIGH)
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        for(int i = 0; i < thinSlabGenerateList.size(); i++){
            if(thinSlabBiomesGenerateList.get(i).contains(event.getCategory())){
                event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, thinSlabGenerateList.get(i));
                return true;
            }
        }

        return false;
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(String key, F value) {
        return Registry.register(Registry.FEATURE, key, value);
    }
}
