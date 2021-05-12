package mightydanp.industrialtech.common.generation;

import mightydanp.industrialtech.api.common.handler.generation.PlantGenerationHandler;
import mightydanp.industrialtech.common.blocks.CatTailPlantBottomBlock;
import mightydanp.industrialtech.common.blocks.CatTailPlantTopBlock;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.biome.Biome.Category.*;
import static net.minecraft.world.biome.Biome.Category.MUSHROOM;

/**
 * Created by MightyDanp on 2/18/2021.
 */
public class PlantGeneration {
    public static Biome.Category[] OverWorldBiomes = {
            TAIGA, EXTREME_HILLS, JUNGLE, MESA, PLAINS, SAVANNA, ICY, BEACH, FOREST, OCEAN, DESERT, RIVER, SWAMP, MUSHROOM
    };

    public static List<BlockState> cattailSoilBlocks = new ArrayList<BlockState>(){{
        add(Blocks.DIRT.defaultBlockState());
        add(Blocks.COARSE_DIRT.defaultBlockState());
        add(Blocks.PODZOL.defaultBlockState());
        }};

    public static List<BlockState> rockSoilBlocks = new ArrayList<BlockState>(){{
        add(Blocks.DIRT.defaultBlockState());
        add(Blocks.COARSE_DIRT.defaultBlockState());
        add(Blocks.PODZOL.defaultBlockState());
        add(Blocks.STONE.defaultBlockState());
        add(Blocks.GRASS_BLOCK.defaultBlockState());
        add(Blocks.GRAVEL.defaultBlockState());
        add(Blocks.SAND.defaultBlockState());
    }};

    public static void init() {
        PlantGenerationHandler.addTopCrop("cattail", (ModBlocks.cattail_plant_top_block.get().defaultBlockState().setValue(CatTailPlantTopBlock.AGE, 7)), (ModBlocks.cattail_plant_bottom_block.get().defaultBlockState().setValue(CatTailPlantBottomBlock.AGE, 15)), cattailSoilBlocks, 10, true, true, 1, Biome.Category.RIVER, Biome.Category.OCEAN, Biome.Category.BEACH);
        PlantGenerationHandler.addtopBlockSurfaceGenerate("rocks", ModBlocks.rock_block.get().defaultBlockState(), rockSoilBlocks, 50, OverWorldBiomes);
    }
}
