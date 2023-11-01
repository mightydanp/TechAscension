package mightydanp.techcore.common.handler.generation;

import com.google.common.base.Preconditions;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.generation.orevein.OreVeinRegistry;
import mightydanp.techcore.common.jsonconfig.generation.smallore.SmallOreVeinRegistry;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.world.gen.feature.OreVeinGenFeatureCodec;
import mightydanp.techcore.common.world.gen.feature.SmallOreVeinGenFeature;
import mightydanp.techcore.common.world.gen.feature.SmallOreVeinGenFeatureCodec;
import mightydanp.techcore.common.material.TCMaterial;
import mightydanp.techcore.common.world.gen.feature.OreVeinGenFeature;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
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
 * Created by MightyDanp on 9/29/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreGenerationHandler {

    public static final RegistryObject<Feature<OreVeinGenFeatureCodec>> ore_vein = RegistryHandler.createFeature("ore_vein", () -> new OreVeinGenFeature(OreVeinGenFeatureCodec.CODEC));
    public static final RegistryObject<Feature<SmallOreVeinGenFeatureCodec>> small_ore = RegistryHandler.createFeature("small_ore", () -> new SmallOreVeinGenFeature(SmallOreVeinGenFeatureCodec.CODEC));

    private static final Map<String, MapWrapper> oreGenList = new HashMap<>();
    private static final Map<String, MapWrapper> smallOreGenList = new HashMap<>();

    public static void addRegistryOreGeneration(OreVeinGenFeatureCodec config) {
        Holder<ConfiguredFeature<OreVeinGenFeatureCodec, ?>> featureHolder = register(config.name(), new ConfiguredFeature<>(ore_vein.get(), config));

        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        list.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(config.minHeight()), VerticalAnchor.absolute(config.maxHeight())));
        //list.add(CountPlacement.of(config.rarity));

        Holder<PlacedFeature> placedFeature = createPlacedFeature(config.name(), featureHolder, list.toArray(new PlacementModifier[0]));
        ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).register(config);
        oreGenList.put(config.name(), new MapWrapper(placedFeature, config.dimensions(), config.validBiomes(), config.invalidBiomes()));
    }

    public static void addOreGeneration(String veinNameIn, int minRadiusIn, int numberOfSmallOreLayers, int minHeightIn, int maxHeightIn, int rarityIn, List<String> dimensions, List<String> validBiomes, List<String> invalidBiomes, Map<Either<BlockState, String>, Integer> veinBlocksAndChances) {
        OreVeinGenFeatureCodec config = new OreVeinGenFeatureCodec(veinNameIn, rarityIn, minHeightIn, maxHeightIn, minRadiusIn, numberOfSmallOreLayers, dimensions, validBiomes, invalidBiomes, veinBlocksAndChances);

        Holder<ConfiguredFeature<OreVeinGenFeatureCodec, ?>> oreVeinFeature = register(config.name(), new ConfiguredFeature<>(ore_vein.get(), config));


        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        list.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(config.minHeight()), VerticalAnchor.absolute(config.maxHeight())));
        //list.add(CountPlacement.of(config.rarity));

        Holder<PlacedFeature> placedFeature = createPlacedFeature(config.name(), oreVeinFeature, list.toArray(new PlacementModifier[0]));
        ((OreVeinRegistry) TCJsonConfigs.oreVein.getFirst()).register(config);
        oreGenList.put(config.name(), new MapWrapper(placedFeature, config.dimensions(), config.validBiomes(), config.invalidBiomes()));
    }

    public static void addRegistrySmallOreVeinGeneration(SmallOreVeinGenFeatureCodec config) {
        Holder<ConfiguredFeature<SmallOreVeinGenFeatureCodec, ?>> smallOreFeature = register(config.name(), new ConfiguredFeature<>(small_ore.get(), config));

        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        list.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(config.minHeight()), VerticalAnchor.absolute(config.maxHeight())));
        //list.add(CountPlacement.of(config.rarity));

        Holder<PlacedFeature> placedFeature = createPlacedFeature(config.name(), smallOreFeature, list.toArray(new PlacementModifier[0]));
        ((SmallOreVeinRegistry) TCJsonConfigs.smallOre.getFirst()).register(config);
        smallOreGenList.put(config.name(), new MapWrapper(placedFeature, config.dimensions(), config.biomeTypesID(), config.invalidBiomeTypesID()));
    }

    public static void addSmallOreVeinGeneration(String smallOreNameIn, int minHeightIn, int maxHeightIn, int rarityIn, List<String> dimensions, List<String> validBiomes, List<String> invalidBiomes, Map<Object, Integer> materialOreIn) {
        List<Pair<String, Integer>> veinBlocksAndChances = new ArrayList<>();
        SmallOreVeinGenFeatureCodec config = new SmallOreVeinGenFeatureCodec(smallOreNameIn, rarityIn, minHeightIn, maxHeightIn, dimensions, validBiomes, invalidBiomes, veinBlocksAndChances);

        materialOreIn.forEach(((object, integer) -> {
            if(object instanceof Block) {
                if (((Block) object).getRegistryName() != null) {
                    Pair<String, Integer> veinBlockAndChance = new Pair<>(((Block) object).getRegistryName().toString(), integer);
                    veinBlocksAndChances.add(veinBlockAndChance);
                }
            }

            if(object instanceof TCMaterial) {
                if (((TCMaterial) object).getRegistryName() != null) {
                    Pair<String, Integer> veinBlockAndChance = new Pair<>(((TCMaterial) object).getRegistryName().toString(), integer);
                    veinBlocksAndChances.add(veinBlockAndChance);
                }
            }
        }));


        Holder<ConfiguredFeature<SmallOreVeinGenFeatureCodec, ?>> smallOreFeature = register(config.name(), new ConfiguredFeature<>(small_ore.get(), config));

        List<PlacementModifier> list = new ArrayList<>(List.of(BiomeFilter.biome(), InSquarePlacement.spread()));
        list.add(HeightRangePlacement.uniform(VerticalAnchor.absolute(config.minHeight()), VerticalAnchor.absolute(config.maxHeight())));
        //list.add(CountPlacement.of(config.rarity));

        Holder<PlacedFeature> placedFeature = createPlacedFeature(config.name(), smallOreFeature, list.toArray(new PlacementModifier[0]));
        ((SmallOreVeinRegistry) TCJsonConfigs.smallOre.getFirst()).register(config);
        smallOreGenList.put(config.name(), new MapWrapper(placedFeature, config.dimensions(), config.biomeTypesID(), config.invalidBiomeTypesID()));
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static boolean checkAndInitBiomes(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        if (oreGenList.size() > 0) {
            oreGenList.forEach(((s, mapWrapper) -> {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, mapWrapper.feature());
            }));
        }

        if (smallOreGenList.size() > 0) {
            smallOreGenList.forEach(((s, mapWrapper) -> {
                builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, mapWrapper.feature());
            }));
        }

        return true;
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

    record MapWrapper(Holder<PlacedFeature> feature, List<String> dimensions, List<String> validBiomes, List<String> invalidBiomes){}


}
