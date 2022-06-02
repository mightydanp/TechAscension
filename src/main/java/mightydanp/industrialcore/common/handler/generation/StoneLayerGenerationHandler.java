package mightydanp.industrialcore.common.handler.generation;

import com.google.common.base.Preconditions;
import mightydanp.industrialcore.common.handler.RegistryHandler;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialcore.common.world.gen.feature.StoneLayerTopSurfaceGenFeatureConfig;
import mightydanp.industrialcore.common.world.gen.feature.StoneLayerTopSurfaceGenFeature;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.GenerationStep;
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
 * Created by MightyDanp on 9/8/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StoneLayerGenerationHandler {

    public static final RegistryObject<Feature<StoneLayerTopSurfaceGenFeatureConfig>> stoneLayerTop = RegistryHandler.createFeature("stone_layer_top", () -> new StoneLayerTopSurfaceGenFeature(StoneLayerTopSurfaceGenFeatureConfig.CODEC));

    protected static Map<String, MapWrapper> stoneLayerTopGenerateList = new HashMap<>();

    public static void addStoneLayerTopGenerate(String slabNameIn, int rarityIn, boolean inWaterIn, boolean onlySurfaceIn, List<ResourceKey<Level>> dimensions, List<BiomeDictionary.Type> validBiomes, List<BiomeDictionary.Type> invalidBiomes, List<String> blocksIn, List<String> whiteListedBlocksIn, List<String> blackListedBlocksIn){
        StoneLayerTopSurfaceGenFeatureConfig config = new StoneLayerTopSurfaceGenFeatureConfig(slabNameIn, rarityIn, inWaterIn, onlySurfaceIn, dimensions, blocksIn, whiteListedBlocksIn, blackListedBlocksIn);

        if (validBiomes.size() > 0){
            config.setValidBiomes(validBiomes);
        }
        if (invalidBiomes.size() > 0){
            config.setValidBiomes(invalidBiomes);
        }

        Holder<ConfiguredFeature<StoneLayerTopSurfaceGenFeatureConfig, ?>> featureHolder = register(slabNameIn, new ConfiguredFeature<>(stoneLayerTop.get(), config));

        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        //list.add(CountPlacement.of(config.rarity));

        Holder<PlacedFeature> placedFeature = createPlacedFeature(slabNameIn, featureHolder, list.toArray(new PlacementModifier[0]));
        stoneLayerTopGenerateList.put(slabNameIn, new MapWrapper(placedFeature, config.dimensions, config.biomeTypes, config.invalidBiomeTypes));
    }

    @SubscribeEvent(priority= EventPriority.HIGH)
    public static void checkAndInitBiome(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        stoneLayerTopGenerateList.forEach(((s, mapWrapper) -> {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, mapWrapper.feature());
        }));
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String id, ConfiguredFeature<FC, F> cf) {
        ResourceLocation realId = new ResourceLocation(Ref.mod_id, id);
        Preconditions.checkState(!BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(realId), "Duplicate ID: %s", id);
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, realId.toString(), cf);
    }

    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> createPlacedFeature(String id, Holder<ConfiguredFeature<FC, ?>> feature, PlacementModifier... placementModifiers) {
        ResourceLocation realID = new ResourceLocation(Ref.mod_id, id);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(realID))
            throw new IllegalStateException("Placed Feature ID: \"" + realID + "\" already exists in the Placed Features registry!");

        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, realID, new PlacedFeature(Holder.hackyErase(feature), List.of(placementModifiers)));
    }

    record MapWrapper(Holder<PlacedFeature> feature, List<ResourceKey<Level>> dimensions, List<BiomeDictionary.Type> validBiomes, List<BiomeDictionary.Type> invalidBiomes){}
}
