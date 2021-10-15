package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.blocks.ThinSlabBlock;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.items.ThinSlabItemBlock;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.items.LegItemBlock;
import mightydanp.industrialtech.common.libs.ItemRef;
import mightydanp.industrialtech.common.libs.StoneLayerFlagsEnum;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by MightyDanp on 7/5/2021.
 */
public class StoneLayerHandler {

    public String name;
    public Block layerBlock;
    public List<StoneLayerFlagsEnum> flags = new ArrayList<>();

    public RegistryObject<Block> four_split_long_block;
    public RegistryObject<Block> eight_split_cube_block;
    public RegistryObject<Block> thin_slab_block;
    public RegistryObject<Block> leg_block;

    public StoneLayerHandler(Block layerBlockIn, StoneLayerFlagsEnum... flagsIn){
        name = Objects.requireNonNull(layerBlockIn.toString().split(":")[layerBlockIn.toString().split(":").length - 1].replace("}", ""));
        layerBlock = layerBlockIn;

        Collections.addAll(flags, flagsIn);
        registerFlags(flagsIn);
    }

    public void registerFlags(StoneLayerFlagsEnum[] flagsIn){
        for(StoneLayerFlagsEnum flag : flagsIn){
            if(flag == StoneLayerFlagsEnum.thinSlab){
                thin_slab_block = RegistryHandler.BLOCKS.register("thin_" + name + "_slab", ()-> new ThinSlabBlock(AbstractBlock.Properties.of(Material.STONE), layerBlock));
                RegistryObject<Item> thin_slab_item_block = RegistryHandler.BLOCK_ITEMS.register("thin_" + name + "_slab", () -> new ThinSlabItemBlock(thin_slab_block.get(), new Item.Properties().stacksTo(1)));
            }

            if(flag == StoneLayerFlagsEnum.leg){
                leg_block = RegistryHandler.BLOCKS.register(name + "_leg", ()-> new ThinSlabBlock(AbstractBlock.Properties.of(Material.STONE), layerBlock));
                RegistryObject<Item> leg_item_block = RegistryHandler.ITEMS.register(name + "_leg", () -> new LegItemBlock(leg_block.get(), new Item.Properties().stacksTo(1)));
            }
        }
    }

    public void clientInit(){
        if(thin_slab_block != null) {
            RenderTypeLookup.setRenderLayer(thin_slab_block.get(), RenderType.cutout());
        }

        if(leg_block != null) {
            RenderTypeLookup.setRenderLayer(leg_block.get(), RenderType.cutout());
        }
    }
}
