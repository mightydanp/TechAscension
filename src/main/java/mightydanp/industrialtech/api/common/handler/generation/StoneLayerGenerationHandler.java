package mightydanp.industrialtech.api.common.handler.generation;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.*;
import net.minecraft.block.BlockState;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MightyDanp on 9/8/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StoneLayerGenerationHandler {

    public static final RegistryObject<Feature<ThinSlabGenFeatureConfig>> thin_slab = RegistryHandler.createFeature("thin_slab", () -> new ThinSlabGenFeature(ThinSlabGenFeatureConfig.CODEC));

    protected static List<ConfiguredFeature<?, ?>> thinSlabGenerateList = new ArrayList<>();
    protected static List<List<Biome.Category>> thinSlabBiomesGenerateList = new ArrayList<>();

    public static void addThinSlabGenerate(String slabNameIn, List<BlockState> slabsIn, int rarityIn, boolean inWaterIn, boolean outOfWaterIn, Biome.Category... biomesIn){
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
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
                event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, thinSlabGenerateList.get(i));
                return true;
            }
        }

        return false;
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
        return Registry.register(Registry.FEATURE, key, value);
    }
}
