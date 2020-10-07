package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.libs.EnumGenerationWorlds;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeature;
import mightydanp.industrialtech.api.common.world.gen.feature.OreGenFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MightyDanp on 9/29/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreGenerationHandler {

    public static final OreGenFeature ore_vain = register("ore_vain", new OreGenFeature(OreGenFeatureConfig.field_236566_a_));
    protected static List<Object> vain_nether = new ArrayList<Object>(){{add(addOresToVainAndChance(0, 0, 0, null, null)); add(0);}};
    protected static final List<Object> vain_over_world= new ArrayList<Object>(){{add(addOresToVainAndChance(0, 0, 0, null, null)); add(0);}};
    protected static final List<Object> vain_end = new ArrayList<Object>(){{add(addOresToVainAndChance(0, 0, 0, null, null)); add(0);}};

    public static void addOreGeneration(int vainSizeIn, int maxHeightIn, int rarityIn, int outOfIn, List<MaterialHandler> materialOreIn, List<EnumGenerationWorlds> worldsIn, Integer... chanceIn){
        if(worldsIn != null) {
            for (EnumGenerationWorlds world : worldsIn) {
                if (world == EnumGenerationWorlds.end) {
                    vain_end.add(addOresToVainAndChance(vainSizeIn, rarityIn, outOfIn, Arrays.asList(chanceIn), materialOreIn));
                    vain_end.add(maxHeightIn);
                }
                if (world == EnumGenerationWorlds.overworld) {
                    vain_over_world.add(addOresToVainAndChance(vainSizeIn, rarityIn, outOfIn, Arrays.asList(chanceIn), materialOreIn));
                    vain_over_world.add(maxHeightIn);
                }
                if (world == EnumGenerationWorlds.nether) {
                    vain_nether.add(addOresToVainAndChance(vainSizeIn, rarityIn, outOfIn, Arrays.asList(chanceIn), materialOreIn));
                    vain_nether.add(maxHeightIn);
                }
            }
        }
    }


    public static void init() {
        //addOreToVain(ModMaterials.iron.blockOre, magnitie_vain_blocks, magnitie_vain_blocks_chances, 50);
        //magnitie_vain = registerNewOre(magnitie_vain_blocks, magnitie_vain_blocks_chances, 50, 5, 1000);
    }

    @SubscribeEvent(priority= EventPriority.HIGH)
    public static boolean checkAndInitBiome(BiomeLoadingEvent event) {
        if (event.getCategory() != Biome.Category.NETHER || event.getCategory() != Biome.Category.THEEND) {
            if (event.getCategory() == Biome.Category.NETHER) {
                generateNether(event);
                return true;
            } else if (event.getCategory() == Biome.Category.THEEND) {
                generateEnd(event);
                return true;
            } else {
                generateOverworld(event);
                return true;
            }
        }
        return false;
    }

    private static void generateNether(BiomeLoadingEvent event) {
        List<OreGenFeatureConfig> configList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();;
        int i = -1;
        for(Object nether : vain_nether) {
            if (nether instanceof OreGenFeatureConfig) {
                configList.add((OreGenFeatureConfig) nether);
            }
            if (nether instanceof Integer) {
                integerList.add((Integer) nether);
            }
        }
        for(OreGenFeatureConfig config : configList) {
            i++;
            if(integerList.get(i) != 0)
                event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, ore_vain.withConfiguration(config).func_242733_d(integerList.get(i)));
        }
    }

    private static void generateEnd(BiomeLoadingEvent event) {
        List<OreGenFeatureConfig> configList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();;
        int i = -1;
        for(Object end : vain_end) {
            if (end instanceof OreGenFeatureConfig) {
                configList.add((OreGenFeatureConfig) end);
            }
            if (end instanceof Integer) {
                integerList.add((Integer) end);
            }
        }
        for(OreGenFeatureConfig config : configList) {
            i++;
            if(integerList.get(i) != 0)
                event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, ore_vain.withConfiguration(config).func_242733_d(integerList.get(i)));
        }
    }

    private static void generateOverworld(BiomeLoadingEvent event) {
        List<OreGenFeatureConfig> configList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();;
        int i = -1;
        for(Object overWorld : vain_over_world) {
            if (overWorld instanceof OreGenFeatureConfig) {
                configList.add((OreGenFeatureConfig) overWorld);
            }
            if (overWorld instanceof Integer) {
                integerList.add((Integer)overWorld);
            }
        }
        for(OreGenFeatureConfig config : configList) {
            i++;
            if(integerList.get(i) != 0)
                event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, ore_vain.withConfiguration(config).func_242733_d(integerList.get(i)));
        }
    }

    private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
        return Registry.register(Registry.FEATURE, key, value);
    }

    public static OreGenFeatureConfig addOresToVainAndChance(int vainSizeIn, int rarityIn, int outOfIn, List<Integer> chanceIn, List<MaterialHandler> materialOreIn){
        List<Integer> ore_spawn_chances = new ArrayList<>();
        List<BlockState> vain_blocks = new ArrayList<>();
        int i=-1;
        if(materialOreIn != null)
        for (MaterialHandler material : materialOreIn) {
            i++;
                for(RegistryObject<Block> ore: material.blockOre) {
                    vain_blocks.add(ore.get().getDefaultState());
                    ore_spawn_chances.add(chanceIn.get(i));
                }
        }

        return new OreGenFeatureConfig(vain_blocks, ore_spawn_chances, vainSizeIn, rarityIn, outOfIn);
    }
}
