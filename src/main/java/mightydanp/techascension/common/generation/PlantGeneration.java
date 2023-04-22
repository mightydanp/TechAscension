package mightydanp.techascension.common.generation;

import mightydanp.techcore.common.handler.generation.PlantGenerationHandler;
import mightydanp.techascension.common.blocks.ModBlocks;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.biome.Biome.BiomeCategory.*;

public class PlantGeneration {
    public static List<Biome.BiomeCategory> OverWorldBiomes = new ArrayList<>() {{
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

    public static List<BlockState> cattailValidBlocks = new ArrayList<>() {{
        add(Blocks.DIRT.defaultBlockState());
        add(Blocks.COARSE_DIRT.defaultBlockState());
        add(Blocks.PODZOL.defaultBlockState());
    }};

    public static List<BlockState> rockSoilBlocks = new ArrayList<>() {{
        add(Blocks.DIRT.defaultBlockState());
        add(Blocks.COARSE_DIRT.defaultBlockState());
        add(Blocks.PODZOL.defaultBlockState());
        add(Blocks.STONE.defaultBlockState());
        add(Blocks.GRASS_BLOCK.defaultBlockState());
        add(Blocks.GRAVEL.defaultBlockState());
        add(Blocks.SAND.defaultBlockState());
    }};



    public static void init() {
        PlantGenerationHandler.addBlockInWaterGenerate("cattail", 100, 1, true, true, List.of(Level.OVERWORLD.location().toString()), List.of(), List.of(), cattailValidBlocks, ModBlocks.cattail_plant_top_block.get().defaultBlockState(), ModBlocks.cattail_plant_bottom_block.get().defaultBlockState());
    }
}
