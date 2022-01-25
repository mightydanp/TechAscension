package mightydanp.industrialtech.api.common.handler.generation;

import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.handler.StoneLayerHandler;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 9/29/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreGenerationHandler {

    public static final RegistryObject<Feature<OreGenFeatureConfig>> ore_vein = RegistryHandler.createFeature("ore_vein", () -> new OreGenFeature(OreGenFeatureConfig.CODEC));
    public static final RegistryObject<Feature<SmallOreGenFeatureConfig>> small_ore = RegistryHandler.createFeature("small_ore", () -> new SmallOreGenFeature(SmallOreGenFeatureConfig.CODEC));

    protected static List<ConfiguredFeature<?, ?>> smallOreGenList = new ArrayList<>();
    protected static List<List<Biome.Category>> smallOreBiomesList = new ArrayList<>();

    protected static List<ConfiguredFeature<?, ?>> commonVeinList = new ArrayList<>();
    protected static final List<List<Biome.Category>> commonVeinBiomesList = new ArrayList<>();
    protected static List<ConfiguredFeature<?, ?>> uncommonVeinList = new ArrayList<>();
    protected static final List<List<Biome.Category>> uncommonVeinBiomesList = new ArrayList<>();
    protected static List<ConfiguredFeature<?, ?>> rareVeinList = new ArrayList<>();
    protected static final List<List<Biome.Category>> rareVeinBiomesList = new ArrayList<>();

    public static void addOreGeneration(String veinNameIn, int minRadiusIn, int numberOfSmallOreLayers, int minHeightIn, int maxHeightIn, int rarityIn, EnumVeinRarityFlags rarityFlagIn, List<Object> materialOreIn, Biome.Category[] worldsIn){
        List<Integer> intList = new ArrayList<>();
        List<BlockState> veinSmallOreBlocks = new ArrayList<>();
        List<BlockState> veinOreBlocks = new ArrayList<>();
        List<BlockState> veinDenseOreBlocks = new ArrayList<>();
        List<Boolean> intBoolean = new ArrayList<>();

        for(Object obj :materialOreIn){
            if(obj instanceof Integer){
                for(StoneLayerHandler state : ModStoneLayers.stoneLayerList){
                    intList.add((Integer)obj);
                }
            }

            if(obj instanceof Boolean){
                for(StoneLayerHandler state : ModStoneLayers.stoneLayerList){
                    intBoolean.add((Boolean)obj);
                }
            }

            if(obj instanceof ITMaterial) {
                for (Block ore : ((ITMaterial) obj).smallOreList) {
                    veinSmallOreBlocks.add(ore.defaultBlockState());
                }
                for (Block ore : ((ITMaterial) obj).oreList) {
                    veinOreBlocks.add(ore.defaultBlockState());
                }
                for (Block ore : ((ITMaterial) obj).denseOreList) {
                    veinDenseOreBlocks.add(ore.defaultBlockState());
                }
            }
        }

        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        OreGenFeatureConfig oreGenFeatureConfig = new OreGenFeatureConfig(veinNameIn, veinSmallOreBlocks, veinOreBlocks, veinDenseOreBlocks, intList, intBoolean, minRadiusIn, rarityIn, numberOfSmallOreLayers, minHeightIn, maxHeightIn);
        ConfiguredFeature<?, ?> oreVeinFeature = ore_vein.get().configured(oreGenFeatureConfig).decorated(Placement.RANGE.configured(new TopSolidRangeConfig(minHeightIn, 0, maxHeightIn)));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, oreGenFeatureConfig.veinName), oreVeinFeature);

        if(rarityFlagIn == EnumVeinRarityFlags.common){
            commonVeinList.add(oreVeinFeature);
            commonVeinBiomesList.add(Arrays.asList(worldsIn));
        }
        if(rarityFlagIn == EnumVeinRarityFlags.uncommon){
            uncommonVeinList.add(oreVeinFeature);
            uncommonVeinBiomesList.add(Arrays.asList(worldsIn));
        }
        if(rarityFlagIn == EnumVeinRarityFlags.rare){
            rareVeinList.add(oreVeinFeature);
            rareVeinBiomesList.add(Arrays.asList(worldsIn));
        }
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

        smallOreGenList.add(oreVeinFeature);
        smallOreBiomesList.add(Arrays.asList(worldsIn));
    }

    @SubscribeEvent(priority= EventPriority.HIGH)
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        Random rand = new Random();
        boolean canSpawnCrop = false;
        int randomNumber = rand.nextInt(100);

        /*
        if(randomNumber > 0 && randomNumber <= 50) {
            int i = rand.nextInt(commonVeinList.size());
            if (biomeCheck(commonVeinBiomesList.get(i), event)) {
                canSpawnCrop = true;
                event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, commonVeinList.get(i));
            }

        }
        if(randomNumber > 50 && randomNumber <= 80) {
            int i = rand.nextInt(uncommonVeinList.size());
            if (biomeCheck(uncommonVeinBiomesList.get(i), event)) {
                canSpawnCrop = true;
                event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, uncommonVeinList.get(i));
            }

        }
        if(randomNumber > 90) {
            int i = rand.nextInt(rareVeinList.size());
            if (biomeCheck(rareVeinBiomesList.get(i), event)) {
                canSpawnCrop = true;
                event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, rareVeinList.get(i));
            }
        }

        for (int i = 0; i < smallOreGenList.size(); i++) {
            if (biomeCheck(smallOreBiomesList.get(i), event)) {
                canSpawnCrop = true;
                event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, smallOreGenList.get(i));
            }
        }
        */
        return canSpawnCrop;
    }

    public static boolean biomeCheck(List<Biome.Category> biomeListVeinIn, BiomeLoadingEvent event){
        for(Biome.Category biomes : biomeListVeinIn) {
            if(biomes == event.getCategory()){
                return true;
            }
        }
        return false;
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
