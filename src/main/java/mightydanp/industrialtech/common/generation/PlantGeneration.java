package mightydanp.industrialtech.common.generation;

import mightydanp.industrialtech.api.common.handler.generation.PlantGenerationHandler;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.biome.Biome.BiomeCategory.*;

public class PlantGeneration {
    public static List<Biome.BiomeCategory> OverWorldBiomes = new ArrayList<Biome.BiomeCategory>(){{
            add(TAIGA); 
            add(EXTREME_HILLS);
            add(JUNGLE);
            add(MESA);
            add(PLAINS);
            add(SAVANNA);
            add(ICY);
            add(BEACH);
            add(FOREST);
            add(OCEAN);
            add(DESERT);
            add(RIVER);
            add(SWAMP);
            add(MUSHROOM);
    }};

    public static List<Block> cattailValidBlocks = new ArrayList<Block>(){{
        add(Blocks.DIRT);
        add(Blocks.COARSE_DIRT);
        add(Blocks.PODZOL);
    }};

    public static List<Block> rockSoilBlocks = new ArrayList<Block>(){{
        add(Blocks.DIRT);
        add(Blocks.COARSE_DIRT);
        add(Blocks.PODZOL);
        add(Blocks.STONE);
        add(Blocks.GRASS_BLOCK);
        add(Blocks.GRAVEL);
        add(Blocks.SAND);
    }};



    public static void init() {
        PlantGenerationHandler.addBlockInWaterGenerate("cattail", 100, 1, true, true, List.of(Level.OVERWORLD.location().toString()), List.of(), List.of(), cattailValidBlocks, ModBlocks.cattail_plant_top_block.get(), ModBlocks.cattail_plant_bottom_block.get());
    }
}
