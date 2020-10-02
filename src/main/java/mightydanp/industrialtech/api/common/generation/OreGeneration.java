package mightydanp.industrialtech.api.common.generation;

import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeatureConfig;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
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
public class OreGeneration {

    public static final OreGenFeature ore_vain = register("ore_vain", new OreGenFeature(OreGenFeatureConfig.field_236566_a_));
    public static List<BlockState> magnitie_vain_blocks;
    protected static OreGenFeatureConfig magnitie_vain;

    public static void init() {
        magnitie_vain_blocks = new ArrayList<BlockState>() {{}};
        for(RegistryObject<Block> ore : ModMaterials.iron.blockOre){
            magnitie_vain_blocks.add(ore.get().getDefaultState());
        }
        magnitie_vain = new OreGenFeatureConfig(magnitie_vain_blocks, 75, 5, 1000);
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
}
