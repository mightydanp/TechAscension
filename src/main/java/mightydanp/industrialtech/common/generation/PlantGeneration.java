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

/**
 * Created by MightyDanp on 2/18/2021.
 */
public class PlantGeneration {
    public static Biome.Category[] OverWorldBiomes = {
            Biome.Category.JUNGLE, Biome.Category.BEACH, Biome.Category.DESERT, Biome.Category.EXTREME_HILLS,
            Biome.Category.FOREST, Biome.Category.ICY, Biome.Category.MESA, Biome.Category.MUSHROOM,
            Biome.Category.OCEAN, Biome.Category.PLAINS, Biome.Category.RIVER, Biome.Category.SAVANNA,
            Biome.Category.SWAMP, Biome.Category.TAIGA};

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
    }};

    public static void init() {
        PlantGenerationHandler.addTopCrop("cattail", (ModBlocks.cattail_plant_top_block.get().getDefaultState().with(CatTailPlantTopBlock.AGE, 7)), (ModBlocks.cattail_plant_bottom_block.get().getDefaultState().with(CatTailPlantBottomBlock.AGE, 15)), cattailSoilBlocks, 10, true, true, 1, Biome.Category.RIVER, Biome.Category.OCEAN, Biome.Category.BEACH);
        PlantGenerationHandler.addtopBlockSurfaceGenerate("rocks", ModBlocks.rock_block.get().getDefaultState(), rockSoilBlocks, 50, OverWorldBiomes);
    }
}
