package mightydanp.industrialtech.api.common.tileentities;

import mightydanp.industrialtech.api.client.rendering.tileentities.HoleTileEntityRenderer;
import mightydanp.industrialtech.api.common.blocks.ITBlocks;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.client.rendering.tileentities.CampfireTileEntityRenderer;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.tileentities.CampfireTileEntityOverride;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Created by MightyDanp on 10/10/2021.
 */
public class TileEntities {
    public static RegistryObject<TileEntityType<HoleTileEntity>> hole_tile_entity;

    public static void init(){
       hole_tile_entity = RegistryHandler.TILES.register("hole", ()-> TileEntityType.Builder.of(HoleTileEntity::new, ITBlocks.hole_block.get()).build(null));
    }

    public static void clientInit(){
        //ClientRegistry.bindTileEntityRenderer(hole_tile_entity.get(), HoleTileEntityRenderer::new);
    }

}