package mightydanp.industrialtech.common.blocks;

import mightydanp.industrialcore.common.handler.RegistryHandler;
import mightydanp.industrialtech.common.libs.BlockRef;
import net.minecraft.world.level.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 9/30/2020.
 */
public class ModBlocks {
    //@ObjectHolder(Ref.mod_id + ":" + "stone_iron_ore")
    //public static Block stone_iron_ore = null;
    public static RegistryObject<Block> cattail_plant_bottom_block;
    public static RegistryObject<Block> cattail_plant_top_block;

    public static RegistryObject<Block> campfire_override;


    public static void init() {
        cattail_plant_bottom_block = RegistryHandler.BLOCKS.register(BlockRef.cattail_bottom_name, () -> new CatTailPlantBottomBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
        cattail_plant_top_block = RegistryHandler.BLOCKS.register(BlockRef.cattail_top_name, () -> new CatTailPlantTopBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));
        campfire_override = RegistryHandler.BLOCKS.register(BlockRef.campfire_override_name, CampfireBlockOverride::new);
    }

    public static void setRenderType(){
        ItemBlockRenderTypes.setRenderLayer(cattail_plant_bottom_block.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(cattail_plant_top_block.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(campfire_override.get(), RenderType.cutout());
    }
}
