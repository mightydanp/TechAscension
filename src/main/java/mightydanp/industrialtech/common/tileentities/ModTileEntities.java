package mightydanp.industrialtech.common.tileentities;

import mightydanp.industrialtech.api.common.blocks.ITBlocks;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.tileentities.HoleTileEntity;
import mightydanp.industrialtech.api.common.tileentities.TileEntities;
import mightydanp.industrialtech.client.rendering.tileentities.CampfireTileEntityRenderer;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import net.minecraft.item.BlockItem;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * Created by MightyDanp on 5/6/2021.
 */
public class ModTileEntities extends TileEntities {
    public static RegistryObject<TileEntityType<CampfireTileEntityOverride>> campfire_tile_entity;
    public static RegistryObject<TileEntityType<HoleTileEntity>> hole_tile_entity;

    public static void init(){
        hole_tile_entity = register("hole", HoleTileEntity::new, ITBlocks.hole_block);
        campfire_tile_entity = register("campfire_override", CampfireTileEntityOverride::new, ModBlocks.campfire_override);
    }

    public static void clientInit(){

    }

}