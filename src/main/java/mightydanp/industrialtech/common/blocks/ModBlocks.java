package mightydanp.industrialtech.common.blocks;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.libs.BlockRef;
import mightydanp.industrialtech.common.libs.ItemRef;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ObjectHolder;

/**
 * Created by MightyDanp on 9/30/2020.
 */
public class ModBlocks {
    @ObjectHolder(Ref.mod_id + ":" + "stone_iron_ore")
    public static Block stone_iron_ore = null;
    public static RegistryObject<Block> CatTailPlantBottomBlock;
    public static RegistryObject<Block> CatTailPlantTopBlock;

    public static void init() {
        CatTailPlantBottomBlock = RegistryHandler.BLOCKS.register(BlockRef.cattail_bottom_name, () -> new CatTailPlantBottomBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
        CatTailPlantTopBlock = RegistryHandler.BLOCKS.register(BlockRef.cattail_top_name, () -> new CatTailPlantTopBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
    }

    public static void setRenderType(){
        RenderTypeLookup.setRenderLayer(CatTailPlantBottomBlock.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(CatTailPlantTopBlock.get(), RenderType.getCutout());
    }
}
