package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.blocks.BlockOre;
import mightydanp.industrialtech.api.common.items.ItemBlockOre;
import mightydanp.industrialtech.api.common.items.ModItemGroups;
import mightydanp.industrialtech.api.common.libs.MaterialFlags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class MaterialHandler {
    private final String materialName;
    private final int color;
    private final String element;

    public RegistryObject<Block> blockOre;
    public RegistryObject<Item> itemOre;

    private static final List<String> stone_varients = new ArrayList<String>(){{
        add("stone");
    }};

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
        for(Object obj : flagsIn){
            if(obj == ore){
                for(String stone : stone_varients){
                    blockOre = RegistryHandler.BLOCKS.register(stone + "_" + materialName + "_ore", () ->
                            new BlockOre(stone +"_" + materialName + "_ore", AbstractBlock.Properties.create(Material.ROCK), color));
                    itemOre = RegistryHandler.ITEMS.register(stone + "_" + materialName + "_ore", () ->
                            new ItemBlockOre(blockOre.get(), new Item.Properties().group(ModItemGroups.item_tab), element));

                }
            }
        }
    }

    public void registerColorHandlerForBlock(Block block){
        Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) ->{
            if (tintIndex != 0)
                return 0xFFFFFFFF;
            return color;
        }, block);
        RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
    }

    public void registerColorForItem(Item item){
        Minecraft.getInstance().getItemColors().register(
                (stack, tintIndex) -> {
                    if (tintIndex != 0)
                        return 0xFFFFFFFF;
                    return color;
                }, item);
    }

    public static boolean addStoneVarient(String nameIn){
        return stone_varients.add(nameIn);
    }
}