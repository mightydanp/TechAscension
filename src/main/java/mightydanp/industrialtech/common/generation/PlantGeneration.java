package mightydanp.industrialtech.common.generation;

import mightydanp.industrialtech.api.common.handler.PlantGenerationHandler;
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
        add(Blocks.DIRT.getDefaultState());
        add(Blocks.COARSE_DIRT.getDefaultState());
        add(Blocks.PODZOL.getDefaultState());
        }};

    public static List<BlockState> rockSoilBlocks = new ArrayList<BlockState>(){{
        add(Blocks.DIRT.getDefaultState());
        add(Blocks.COARSE_DIRT.getDefaultState());
        add(Blocks.PODZOL.getDefaultState());
        add(Blocks.STONE.getDefaultState());
        add(Blocks.GRASS_BLOCK.getDefaultState());
        add(Blocks.GRAVEL.getDefaultState());
        add(Blocks.SAND.getDefaultState());
    }};

    public static void init() {
        PlantGenerationHandler.addTopCrop("cattail", (ModBlocks.cattail_plant_top_block.get().getDefaultState().with(CatTailPlantTopBlock.AGE, 7)), (ModBlocks.cattail_plant_bottom_block.get().getDefaultState().with(CatTailPlantBottomBlock.AGE, 15)), cattailSoilBlocks, 10, true, true, 1, Biome.Category.RIVER, Biome.Category.OCEAN, Biome.Category.BEACH);
        PlantGenerationHandler.addtopBlockSurfaceGenerate("rocks", ModBlocks.rock_block.get().getDefaultState(), rockSoilBlocks, 50, OverWorldBiomes);
    }
}
