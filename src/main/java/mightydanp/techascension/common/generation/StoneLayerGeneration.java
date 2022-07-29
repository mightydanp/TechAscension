package mightydanp.techascension.common.generation;

import mightydanp.techcore.common.handler.generation.StoneLayerGenerationHandler;
import mightydanp.techcore.common.jsonconfig.ICJsonConfigs;
import mightydanp.techcore.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.techcore.common.material.ITMaterial;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.biome.Biome.BiomeCategory.*;

public class StoneLayerGeneration {
    public static Biome.BiomeCategory[] OverWorldBiomes = {
            TAIGA, EXTREME_HILLS, JUNGLE, MESA, PLAINS, SAVANNA, ICY, BEACH, FOREST, OCEAN, DESERT, RIVER, SWAMP, MUSHROOM
    };

    public static Biome.BiomeCategory[] OverWorldFlowBiomes = {
            BEACH, OCEAN, RIVER, SWAMP
    };

    public static List<String> thinSlabBlocksBlackListedBlocks = List.of(Blocks.SAND.getRegistryName().toString(), Blocks.RED_SAND.getRegistryName().toString(), Blocks.GRAVEL.getRegistryName().toString(), Blocks.DIRT.getRegistryName().toString(), Blocks.GRASS.getRegistryName().toString(), Blocks.PODZOL.getRegistryName().toString());

    public static List<String> thinSlabBlocks = new ArrayList<>();

    public static List<String> rockBlocks = new ArrayList<>();

    public static void init() {
        List<ITMaterial> stoneLayerList = ((MaterialRegistry) ICJsonConfigs.material.getFirst()).getAllValues().stream().filter(i -> i.isStoneLayer != null && i.isStoneLayer).toList();
        if(thinSlabBlocks.size() != stoneLayerList.size()){
            for (ITMaterial iStoneLayer : stoneLayerList) {
                Block replaceableBlock = iStoneLayer.thinSlabBlock.get();

                thinSlabBlocks.add(String.valueOf(replaceableBlock.getRegistryName()));
            }
        }

        if(rockBlocks.size() != stoneLayerList.size()){
            for (ITMaterial iStoneLayer : stoneLayerList) {
                Block replaceableBlock = iStoneLayer.rockBlock.get();

                rockBlocks.add(String.valueOf(replaceableBlock.getRegistryName()));
            }
        }

        StoneLayerGenerationHandler.addStoneLayerTopGenerate("thin_slab", 100, true, true, List.of(Level.OVERWORLD), new ArrayList<>(), new ArrayList<>(), thinSlabBlocks, new ArrayList<>(), new ArrayList<>());

        StoneLayerGenerationHandler.addStoneLayerTopGenerate("rock_block", 100, false, true, List.of(Level.OVERWORLD), new ArrayList<>(), new ArrayList<>(), rockBlocks, new ArrayList<>(), thinSlabBlocksBlackListedBlocks);
    }


}