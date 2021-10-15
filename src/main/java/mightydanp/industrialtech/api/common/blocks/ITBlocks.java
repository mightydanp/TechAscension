package mightydanp.industrialtech.api.common.blocks;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.common.blocks.CampfireBlockOverride;
import mightydanp.industrialtech.common.blocks.CatTailPlantBottomBlock;
import mightydanp.industrialtech.common.blocks.CatTailPlantTopBlock;
import mightydanp.industrialtech.common.libs.BlockRef;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.RegistryObject;

/**
 * Created by MightyDanp on 10/11/2021.
 */
public class ITBlocks {
    public static RegistryObject<Block> hole_block;


    public static void init() {
        hole_block = RegistryHandler.BLOCKS.register(BlockRef.hole_name, HoleBlock::new);
    }

    public static void setRenderType(){
    }
}