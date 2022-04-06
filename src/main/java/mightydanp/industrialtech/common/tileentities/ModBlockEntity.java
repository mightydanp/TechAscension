package mightydanp.industrialtech.common.tileentities;

import mightydanp.industrialtech.api.common.blocks.ITBlocks;
import mightydanp.industrialtech.api.common.tileentities.HoleTileEntity;
import mightydanp.industrialtech.api.common.tileentities.TileEntities;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;

/**
 * Created by MightyDanp on 5/6/2021.
 */
public class ModBlockEntity extends TileEntities {
    public static RegistryObject<BlockEntityType<CampfireBlockEntityOverride>> campfire_block_entity;
    public static RegistryObject<BlockEntityType<HoleTileEntity>> hole_block_entity;

    public static void init(){
        hole_block_entity = register("hole", HoleTileEntity::new, ITBlocks.hole_block);
        campfire_block_entity = register("campfire_override", CampfireBlockEntityOverride::new, ModBlocks.campfire_override);
    }

    public static void clientInit(){

    }

}