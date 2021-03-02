package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeatureConfig;
import mightydanp.industrialtech.api.common.world.gen.feature.SmallOreGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.SmallOreGenFeatureConfig;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
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
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 9/29/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreGenerationHandler {

    //public static final OreGenFeature ore_vein = register("ore_vein", new OreGenFeature(OreGenFeatureConfig.field_236566_a_));
    public static final RegistryObject<Feature<OreGenFeatureConfig>> ore_vein = RegistryHandler.createFeature("ore_vein", () -> new OreGenFeature(OreGenFeatureConfig.field_236566_a_));
    public static final RegistryObject<Feature<SmallOreGenFeatureConfig>> small_ore = RegistryHandler.createFeature("small_ore", () -> new SmallOreGenFeature(SmallOreGenFeatureConfig.field_236566_a_));

    protected static List<ConfiguredFeature<?, ?>> oreVeinList = new ArrayList<>();
    protected static List<List<Biome.Category>> biomeVeinList = new ArrayList<>();

    protected static List<ConfiguredFeature<?, ?>> smallOreGenList = new ArrayList<>();
    protected static List<List<Biome.Category>> biomeSmallOreList = new ArrayList<>();

    public static void addOreGeneration(String veinNameIn, int veinSizeIn, int minHeightIn, int maxHeightIn, int rarityIn, int outOfIn, List<Object> materialOreIn, Biome.Category[] worldsIn){
        List<Integer> intList = new ArrayList<>();
        List<BlockState> vein_blocks = new ArrayList<>();

        for(Object obj :materialOreIn){
            if(obj instanceof Integer){
                for(BlockState state :MaterialHandler.stone_variants){
                    intList.add((Integer)obj);
                }
            }

            if(obj instanceof MaterialHandler) {
                for (RegistryObject<Block> ore : ((MaterialHandler) obj).blockOre) {
                    vein_blocks.add(ore.get().getDefaultState());
                }
            }
        }

        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        OreGenFeatureConfig oreGenFeatureConfig = new OreGenFeatureConfig(veinNameIn, vein_blocks, intList, veinSizeIn, rarityIn, outOfIn);
        ConfiguredFeature<?, ?> oreVeinFeature = ore_vein.get().withConfiguration(oreGenFeatureConfig).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(minHeightIn, 0, maxHeightIn)));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, oreGenFeatureConfig.veinName), oreVeinFeature);

        oreVeinList.add(oreVeinFeature);
        biomeVeinList.add(Arrays.asList(worldsIn));
    }

    public static void addSmallOreGeneration(String SmallOreNameIn, int minHeightIn, int maxHeightIn, int rarityIn, List<Object> materialOreIn, Biome.Category[] worldsIn){
        List<Integer> intList = new ArrayList<>();
        List<List<BlockState>> smallOreBlocks = new ArrayList<>();

        for(Object obj : materialOreIn){
            if(obj instanceof Integer){
                intList.add((Integer)obj);
            }

            if(obj instanceof MaterialHandler) {
                List<BlockState> tempList2 = new ArrayList<>();
                for (RegistryObject<Block> ore : ((MaterialHandler) obj).blockSmallOre) {
                    tempList2.add(ore.get().getDefaultState());
                }
                smallOreBlocks.add(tempList2);
            }
        }

        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        SmallOreGenFeatureConfig smallOreGenFeatureConfig = new SmallOreGenFeatureConfig(SmallOreNameIn, smallOreBlocks, intList, rarityIn);
        ConfiguredFeature<?, ?> oreVeinFeature = small_ore.get().withConfiguration(smallOreGenFeatureConfig).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(minHeightIn, 0, maxHeightIn)));
        Registry.register(registry, new ResourceLocation(Ref.mod_id, smallOreGenFeatureConfig.smallOreName), oreVeinFeature);

        smallOreGenList.add(oreVeinFeature);
        biomeSmallOreList.add(Arrays.asList(worldsIn));
    }

    @SubscribeEvent(priority= EventPriority.HIGH)
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        boolean canSpawnCrop = false;

         for (int i = 0; i < oreVeinList.size(); i++) {
             if (biomeCheck(biomeVeinList, event)) {
                 canSpawnCrop = true;
                 event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, oreVeinList.get(i));
             }
         }

        for (int i = 0; i < smallOreGenList.size(); i++) {
            if (biomeCheck(biomeSmallOreList, event)) {
                canSpawnCrop = true;
                event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, smallOreGenList.get(i));
            }
        }
        return canSpawnCrop;
    }

    public static boolean biomeCheck(List<List<Biome.Category>> biomeListVeinIn, BiomeLoadingEvent event){
        for(List<Biome.Category> biomes : biomeListVeinIn) {
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
