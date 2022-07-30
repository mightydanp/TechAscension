package mightydanp.techascension.common.tileentities;

import mightydanp.techcore.common.blocks.TCBlocks;
import mightydanp.techcore.common.tileentities.HoleTileEntity;
import mightydanp.techcore.common.tileentities.TileEntities;
import mightydanp.techascension.common.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 5/6/2021.
 */
public class ModBlockEntity extends TileEntities {
    public static RegistryObject<BlockEntityType<CampfireBlockEntityOverride>> campfire_block_entity;
    public static RegistryObject<BlockEntityType<HoleTileEntity>> hole_block_entity;

    public static void init(){
        hole_block_entity = register("hole", HoleTileEntity::new, TCBlocks.hole_block);
        campfire_block_entity = register("campfire_override", CampfireBlockEntityOverride::new, ModBlocks.campfire_override);
    }

    public static void clientInit(){

    }

}