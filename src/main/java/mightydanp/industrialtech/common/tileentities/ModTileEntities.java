package mightydanp.industrialtech.common.tileentities;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.client.rendering.tileentities.CampfireTileEntityRenderer;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Created by MightyDanp on 5/6/2021.
 */
public class ModTileEntities {
    public static RegistryObject<TileEntityType<CampfireTileEntityOverride>> campfire_tile_entity;

    public static void init(){
        campfire_tile_entity = RegistryHandler.TILES.register("campfire_override", ()-> TileEntityType.Builder.of(CampfireTileEntityOverride::new, Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE).build(null));
    }

    public static void clientInit(){
        ClientRegistry.bindTileEntityRenderer(campfire_tile_entity.get(), CampfireTileEntityRenderer::new);
    }

}
