package mightydanp.industrialtech.common.blocks;

import mightydanp.industrialtech.api.common.blocks.RockBlock;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.libs.BlockRef;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ObjectHolder;

/**
 * Created by MightyDanp on 9/30/2020.
 */
public class ModBlocks {
    @ObjectHolder(Ref.mod_id + ":" + "stone_iron_ore")
    public static Block stone_iron_ore = null;
    public static RegistryObject<Block> cattail_plant_bottom_block;
    public static RegistryObject<Block> cattail_plant_top_block;

    public static RegistryObject<Block> rock_block;

    public static void init() {
        cattail_plant_bottom_block = RegistryHandler.BLOCKS.register(BlockRef.cattail_bottom_name, () -> new CatTailPlantBottomBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
        cattail_plant_top_block = RegistryHandler.BLOCKS.register(BlockRef.cattail_top_name, () -> new CatTailPlantTopBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
        rock_block = RegistryHandler.BLOCKS.register(BlockRef.rock_block_name, () -> new RockBlock());
    }

    public static void setRenderType(){
        RenderTypeLookup.setRenderLayer(cattail_plant_bottom_block.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(cattail_plant_top_block.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(rock_block.get(), RenderType.getCutout());
    }
}
