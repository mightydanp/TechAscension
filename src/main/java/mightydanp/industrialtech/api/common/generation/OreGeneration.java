package mightydanp.industrialtech.api.common.generation;

import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeatureConfig;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 9/29/2020.
 */
@Mod.EventBusSubscriber
public class OreGeneration {

    public static List<BlockState> magnitie_vain_blocks;

    public static final OreGenFeature ore_vain = register("ore_vain", new OreGenFeature(OreGenFeatureConfig.field_236566_a_));
    protected static OreGenFeatureConfig magnitie_vain = new OreGenFeatureConfig(OreGenFeatureConfig.FillerBlockType.field_241882_a, magnitie_vain_blocks , 100);

    public static void init(){
        magnitie_vain_blocks = new ArrayList<BlockState>(){{
        add(ModBlocks.stone_iron_ore.getDefaultState());
        }};
    }

    @SubscribeEvent
    public static boolean checkAndInitBiome(BiomeLoadingEvent event){
        if(event.getCategory() != Biome.Category.NETHER || event.getCategory() !=  Biome.Category.THEEND){
            if (event.getCategory() == Biome.Category.NETHER){
                initNetherFeatures(event);
                return true;
            }
            else if (event.getCategory() == Biome.Category.THEEND){
                initTheEndFeatures(event);
                return true;
            }else{
                initOverworldFeatures(event);
                return true;
            }

        }
        return false;
    }

    private static void initTheEndFeatures(BiomeLoadingEvent event) {
    }

    public  static void initOverworldFeatures(BiomeLoadingEvent event) {
        event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION,
                ore_vain.withConfiguration(magnitie_vain));
    }

    public  static void initNetherFeatures(BiomeLoadingEvent evt) {
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
        return Registry.register(Registry.FEATURE, key, value);
    }
}
