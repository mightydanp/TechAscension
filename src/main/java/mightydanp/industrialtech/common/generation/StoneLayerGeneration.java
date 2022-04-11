package mightydanp.industrialtech.common.generation;

import mightydanp.industrialtech.api.common.handler.generation.StoneLayerGenerationHandler;
import mightydanp.industrialtech.api.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
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

    public static List<BlockState> thinSlabBlocks = new ArrayList<>();

    public static void init() {
        List<ITMaterial> stoneLayerList = ((MaterialRegistry) IndustrialTech.configSync.material.getFirst()).getAllValues().stream().filter(i -> i.isStoneLayer != null && i.isStoneLayer).toList();
        if(thinSlabBlocks.size() != stoneLayerList.size()){
            for (ITMaterial iStoneLayer : stoneLayerList) {
                Block replaceableBlock = iStoneLayer.thinSlabBlock.get();

                thinSlabBlocks.add(replaceableBlock.defaultBlockState());
            }
        }
        StoneLayerGenerationHandler.addThinSlabGenerate("thin_slab", thinSlabBlocks, 2, true, false, List.of(Level.OVERWORLD), List.of(), List.of());
    }


}