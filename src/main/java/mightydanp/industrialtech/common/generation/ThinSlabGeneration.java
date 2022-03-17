package mightydanp.industrialtech.common.generation;

import mightydanp.industrialtech.api.common.blocks.SmallOreBlock;
import mightydanp.industrialtech.api.common.handler.generation.PlantGenerationHandler;
import mightydanp.industrialtech.api.common.handler.generation.ThinSlabGenerationHandler;
import mightydanp.industrialtech.api.common.jsonconfig.generation.orevein.OreVeinRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.stonelayer.IStoneLayer;
import mightydanp.industrialtech.api.common.jsonconfig.stonelayer.StoneLayerRegistry;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import mightydanp.industrialtech.common.IndustrialTech;
import mightydanp.industrialtech.common.blocks.CatTailPlantBottomBlock;
import mightydanp.industrialtech.common.blocks.CatTailPlantTopBlock;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.stonelayers.ModStoneLayers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.world.biome.Biome.Category.*;
import static net.minecraft.world.biome.Biome.Category.MUSHROOM;

/**
 * Created by MightyDanp on 9/8/2021.
 */
public class ThinSlabGeneration {
    public static Biome.Category[] OverWorldBiomes = {
            TAIGA, EXTREME_HILLS, JUNGLE, MESA, PLAINS, SAVANNA, ICY, BEACH, FOREST, OCEAN, DESERT, RIVER, SWAMP, MUSHROOM
    };

    public static Biome.Category[] OverWorldFlowBiomes = {
            BEACH, OCEAN, RIVER, SWAMP
    };

    public static List<BlockState> thinSlabBlocks = new ArrayList<>();

    public static void init() {
        List<ITMaterial> stoneLayerList =  ((MaterialRegistry) IndustrialTech.configSync.material.getFirst()).getAllValues().stream().filter(i -> i.isStoneLayer != null && i.isStoneLayer).collect(Collectors.toList());
        if(thinSlabBlocks.size() != stoneLayerList.size()){
            for(int i = 0; i < stoneLayerList.size(); i++){
                ITMaterial iStoneLayer = stoneLayerList.get(i);
                Block replaceableBlock = iStoneLayer.thinSlabBlock;

                if(replaceableBlock != null) {
                    thinSlabBlocks.add(replaceableBlock.defaultBlockState());
                }
            }
        }
        ThinSlabGenerationHandler.addThinSlabGenerate("thin_slab", thinSlabBlocks, 5, OverWorldFlowBiomes);
    }


}