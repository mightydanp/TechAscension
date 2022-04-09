package mightydanp.industrialtech.api.common.handler.generation;

import com.google.common.base.Preconditions;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.jsonconfig.generation.orevein.OreVeinRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.generation.smallore.SmallOreVeinRegistry;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.*;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.biome.Biome;
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
 * Created by MightyDanp on 9/8/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StoneLayerGenerationHandler {

    public static final RegistryObject<Feature<ThinSlabGenFeatureConfig>> thin_slab = RegistryHandler.createFeature("thin_slab", () -> new ThinSlabGenFeature(ThinSlabGenFeatureConfig.CODEC));

    protected static Map<String, MapWrapper> thinSlabGenerateList = new HashMap<>();

    public static void addThinSlabGenerate(String slabNameIn, List<BlockState> slabsIn, int rarityIn, boolean inWaterIn, boolean outOfWaterIn, List<ResourceKey<Level>> dimensions, List<BiomeDictionary.Type> validBiomes, List<BiomeDictionary.Type> invalidBiomes){
        ThinSlabGenFeatureConfig config = new ThinSlabGenFeatureConfig(slabNameIn, slabsIn, rarityIn, dimensions, inWaterIn, outOfWaterIn);

        if (!validBiomes.isEmpty()){
            config.setValidBiomes(validBiomes);
        }
        if (!invalidBiomes.isEmpty()){
            config.setValidBiomes(invalidBiomes);
        }

        Holder<ConfiguredFeature<ThinSlabGenFeatureConfig, ?>> featureHolder = register(slabNameIn, new ConfiguredFeature<>(thin_slab.get(), config));

        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        list.add(CountPlacement.of(config.rarity));

        Holder<PlacedFeature> placedFeature = createPlacedFeature(slabNameIn, featureHolder, list.toArray(new PlacementModifier[0]));
        thinSlabGenerateList.put(slabNameIn, new MapWrapper(placedFeature, config.dimensions, config.biomeTypes, config.invalidBiomeTypes));
    }

    @SubscribeEvent(priority= EventPriority.HIGH)
    public static void checkAndInitBiome(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        thinSlabGenerateList.forEach(((s, mapWrapper) -> {
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
            throw new IllegalStateException("Placed Feature ID: \"" + realID.toString() + "\" already exists in the Placed Features registry!");

        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, realID, new PlacedFeature(Holder.hackyErase(feature), List.of(placementModifiers)));
    }

    record MapWrapper(Holder<PlacedFeature> feature, List<ResourceKey<Level>> dimensions, List<BiomeDictionary.Type> validBiomes, List<BiomeDictionary.Type> invalidBiomes){}
}
