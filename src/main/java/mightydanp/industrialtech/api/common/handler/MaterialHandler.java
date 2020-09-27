package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.blocks.BlockOre;
import mightydanp.industrialtech.api.common.items.ItemBlockOre;
import mightydanp.industrialtech.api.common.items.ModItemGroups;
import mightydanp.industrialtech.api.common.libs.MaterialFlags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.Properties;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class MaterialHandler {
    private final String materialName;
    private final int color;
    private final String element;
    private static Block blockOre;
    private static Item itemOre;

    protected static MaterialFlags fluid = MaterialFlags.FLUID;
    protected static MaterialFlags gas = MaterialFlags.GAS;
    protected static MaterialFlags dust = MaterialFlags.DUST;
    protected static MaterialFlags ore = MaterialFlags.ORE;
    protected static MaterialFlags gem = MaterialFlags.GEM;
    protected static MaterialFlags ingot = MaterialFlags.INGOT;
    protected static MaterialFlags blockMetal = MaterialFlags.BLOCKMETAL;
    protected static MaterialFlags blockGem = MaterialFlags.BLOCKGEM;
    protected static MaterialFlags empty = MaterialFlags.NULL;

    public MaterialHandler(String materialNameIn, int colorIn, String elementIn, Object... flag) {
        this.materialName = materialNameIn;
        this.color = colorIn;
        this.element = elementIn;
        this.addFlag(flag);
    }

    protected void addFlag(Object... flagsIn) {
        Object[] o = flagsIn;
        int i = flagsIn.length;

        for(int j = 0; j < i; ++j) {
            Object obj = o[j];
            if(obj == ore){
                RegistryObject<Block> block = RegistryHandler.getBlocks().register(materialName, () ->
                        new BlockOre(materialName + "_ore", AbstractBlock.Properties.create(Material.ROCK), color));
                blockOre = block.get();
                RegistryObject<Item> item = RegistryHandler.getItems().register(materialName, () ->
                        new ItemBlockOre(block.get(), new Item.Properties().group(ModItemGroups.item_tab), element));
                itemOre = item.get();
            }
        }
    }

    public void registerColorHandlerForBlock(){
        Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) ->{
            if (tintIndex != 0)
                return 0xFFFFFFFF;
            return color;
        }, blockOre);
    }

    public void registerColorForItem(){
        Minecraft.getInstance().getItemColors().register(
                (stack, tintIndex) -> {
                    if (tintIndex != 0)
                        return 0xFFFFFFFF;
                    return color;
                }, itemOre);
    }
}