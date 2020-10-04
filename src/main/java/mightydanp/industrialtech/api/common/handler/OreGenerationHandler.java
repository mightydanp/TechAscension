package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeatureConfig;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 9/29/2020.
 */
@Mod.EventBusSubscriber
public class OreGenerationHandler {

    public static final OreGenFeature ore_vain = register("ore_vain", new OreGenFeature(OreGenFeatureConfig.field_236566_a_));
    protected static OreGenFeatureConfig magnitie_vain;
    public static List<BlockState> magnitie_vain_blocks = new ArrayList<>();
    public static List<Integer> magnitie_vain_blocks_chances = new ArrayList<>();

    
    public static void init() {
        //addOreToVain(ModMaterials.iron.blockOre, magnitie_vain_blocks, magnitie_vain_blocks_chances, 50);
        magnitie_vain = registerNewOre(magnitie_vain_blocks, magnitie_vain_blocks_chances, 50, 5, 1000);
    }

    @SubscribeEvent
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        if (event.getCategory() != Biome.Category.NETHER || event.getCategory() != Biome.Category.THEEND) {
            if (event.getCategory() == Biome.Category.NETHER) {
                initNetherFeatures(event);
                return true;
            } else if (event.getCategory() == Biome.Category.THEEND) {
                initTheEndFeatures(event);
                return true;
            } else {
                initOverworldFeatures(event);
                return true;
            }

        }
        return false;
    }

    private static void initTheEndFeatures(BiomeLoadingEvent event) {
    }

    public static void initOverworldFeatures(BiomeLoadingEvent event) {
        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,
                ore_vain.withConfiguration(magnitie_vain).func_242733_d(80));
    }

    public static void initNetherFeatures(BiomeLoadingEvent evt) {
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
        return Registry.register(Registry.FEATURE, key, value);
    }

    public static void addOreToVain(List<RegistryObject<Block>> materialOreIn, List<BlockState> vainBlocksIn, List<Integer> oreChanceIn, int chanceIn){
        for (RegistryObject<Block> ore : materialOreIn) {
            vainBlocksIn.add(ore.get().getDefaultState());
            oreChanceIn.add(chanceIn);
        }
    }

    public static OreGenFeatureConfig registerNewOre(List<BlockState> vainBlocksIn, List<Integer> vainBlocksChancesIn, int vainSizeIn, int rarityIn, int outOfIn){
        return new OreGenFeatureConfig(vainBlocksIn, vainBlocksChancesIn, vainSizeIn, rarityIn, outOfIn);
    }
}
