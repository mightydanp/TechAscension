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
    public static Object Cattail;
    public static List<BlockState> cattailSoilBlocks = new ArrayList<>();

    public static void init() {
        cattailSoilBlocks.add(Blocks.DIRT.getDefaultState());
        cattailSoilBlocks.add(Blocks.COARSE_DIRT.getDefaultState());
        cattailSoilBlocks.add(Blocks.PODZOL.getDefaultState());
        PlantGenerationHandler.addTopCrop("cattail", (ModBlocks.CatTailPlantTopBlock.get().getDefaultState().with(CatTailPlantTopBlock.AGE, 7)), (ModBlocks.CatTailPlantBottomBlock.get().getDefaultState().with(CatTailPlantBottomBlock.AGE, 15)), cattailSoilBlocks, 10, true, true, 1, Biome.Category.RIVER, Biome.Category.OCEAN, Biome.Category.BEACH);
    }
}
