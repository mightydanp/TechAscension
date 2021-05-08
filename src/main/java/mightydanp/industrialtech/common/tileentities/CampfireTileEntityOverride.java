package mightydanp.industrialtech.common.tileentities;

import mightydanp.industrialtech.common.blocks.ModBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

/**
 * Created by MightyDanp on 5/6/2021.
 */
public class CampfireTileEntityOverride extends TileEntity {
    public CampfireTileEntityOverride() {
        super(ModTileEntities.campfire_tile_entity.get());
    }
}
