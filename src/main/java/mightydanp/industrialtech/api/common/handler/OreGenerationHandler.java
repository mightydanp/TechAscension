package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
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
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 9/29/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreGenerationHandler {

    //public static final OreGenFeature ore_vain = register("ore_vain", new OreGenFeature(OreGenFeatureConfig.field_236566_a_));
    public static final RegistryObject<Feature<OreGenFeatureConfig>> ore_vain = RegistryHandler.createFeature("ore_vain", () -> new OreGenFeature(OreGenFeatureConfig.field_236566_a_));

    protected static List<ConfiguredFeature<?, ?>> oreVainList = new ArrayList<>();
    protected static List<List<Biome.Category>> biomeList = new ArrayList<>();

    public static void addOreGeneration(String vainNameIn, int vainSizeIn, int minHeightIn, int maxHeightIn, int rarityIn, int outOfIn, List<Object> materialOreIn, Biome.Category[] worldsIn){
        List<Integer> intList = new ArrayList<>();
        List<BlockState> vain_blocks = new ArrayList<>();

        for(Object obj :materialOreIn){
            if(obj instanceof Integer){
                for(BlockState state :MaterialHandler.stone_variants){
                    intList.add((Integer)obj);
                }
            }

            if(obj instanceof MaterialHandler) {
                for (RegistryObject<Block> ore : ((MaterialHandler) obj).blockOre) {
                    vain_blocks.add(ore.get().getDefaultState());
                }
            }
        }

        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        OreGenFeatureConfig oreGenFeatureConfig = new OreGenFeatureConfig(vainNameIn, vain_blocks, intList, vainSizeIn, rarityIn, outOfIn);
        ConfiguredFeature<?, ?> oreVainFeature = ore_vain.get().withConfiguration(oreGenFeatureConfig).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(minHeightIn, 0, maxHeightIn)));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, oreGenFeatureConfig.vainName), oreVainFeature);

        oreVainList.add(oreVainFeature);
        biomeList.add(Arrays.asList(worldsIn));
    }

    @SubscribeEvent(priority= EventPriority.HIGH)
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        boolean canSpawnCrop = false;

         for (int i = 0; i < oreVainList.size(); i++) {
             if (biomeCheck(biomeList, event)) {
                 canSpawnCrop = true;
                 event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, oreVainList.get(i));
             }
         }
        return canSpawnCrop;
    }

    public static boolean biomeCheck(List<List<Biome.Category>> biomeListIn, BiomeLoadingEvent event){
        for(List<Biome.Category> biomes : biomeListIn) {
            if (biomes.contains(event.getCategory()))return true;
            else return false;
        }
        return false;
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
        return Registry.register(Registry.FEATURE, key, value);
    }

    public static void overrideFeatures(Biome biome){
        List<ConfiguredFeature> features = new ArrayList<ConfiguredFeature>();

        for (List<Supplier<ConfiguredFeature<?, ?>>> f : biome.getGenerationSettings().getFeatures()) {
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
        biome.getGenerationSettings().getFeatures().removeAll(features);
    }
}
