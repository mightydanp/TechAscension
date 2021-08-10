package mightydanp.industrialtech.common.blocks;

import mightydanp.industrialtech.api.common.blocks.RockBlock;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.common.libs.BlockRef;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.RegistryObject;

/**
 * Created by MightyDanp on 9/30/2020.
 */
public class ModBlocks {
    //@ObjectHolder(Ref.mod_id + ":" + "stone_iron_ore")
    //public static Block stone_iron_ore = null;
    public static RegistryObject<Block> cattail_plant_bottom_block;
    public static RegistryObject<Block> cattail_plant_top_block;

    public static RegistryObject<Block> rock_block;
    public static RegistryObject<Block> campfire_override;


    public static void init() {
        cattail_plant_bottom_block = RegistryHandler.BLOCKS.register(BlockRef.cattail_bottom_name, () -> new CatTailPlantBottomBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
        cattail_plant_top_block = RegistryHandler.BLOCKS.register(BlockRef.cattail_top_name, () -> new CatTailPlantTopBlock(AbstractBlock.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
        rock_block = RegistryHandler.BLOCKS.register(BlockRef.rock_block_name, RockBlock::new);
        campfire_override = RegistryHandler.BLOCKS.register(BlockRef.campfire_override_name, CampfireBlockOverride::new);
    }

    public static void setRenderType(){
        RenderTypeLookup.setRenderLayer(cattail_plant_bottom_block.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(cattail_plant_top_block.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(rock_block.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(campfire_override.get(), RenderType.cutout());
    }
}
