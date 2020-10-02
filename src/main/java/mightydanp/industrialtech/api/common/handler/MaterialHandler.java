package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.blocks.BlockOre;
import mightydanp.industrialtech.api.common.items.ItemBlockOre;
import mightydanp.industrialtech.api.common.items.ModItemGroups;
import mightydanp.industrialtech.api.common.libs.MaterialFlags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.common.Tags;
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
    public List<RegistryObject<Block>>  blockOre = new ArrayList<>();
    public List<RegistryObject<Item>>  itemOre = new ArrayList<>();

    private static final List<BlockState> stone_varients = new ArrayList<BlockState>(){{
        add(Blocks.STONE.getDefaultState());
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
                for(BlockState stone : stone_varients){
                    RegistryObject<Block> block = RegistryHandler.BLOCKS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new BlockOre(AbstractBlock.Properties.create(Material.ROCK), stone, color));
                    blockOre.add(block);
                    RegistryObject<Item> item = RegistryHandler.ITEMS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new ItemBlockOre(block.get(), new Item.Properties().group(ModItemGroups.item_tab), element));
                    itemOre.add(item);
                }
            }
        }
    }

    public void registerColorHandlerForBlock() {
        if (blockOre != null) {
            for (RegistryObject<Block> block : blockOre) {
                RenderTypeLookup.setRenderLayer(block.get(), RenderType.getCutout());
                Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) -> {
                    if (tintIndex != 0)
                        return 0xFFFFFFFF;
                    return color;
                }, block.get());
            }
        }
    }

    public void registerColorForItem(){
        if(itemOre != null) {
            for (RegistryObject<Item> item : itemOre) {
                Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                    if (tintIndex != 0)
                        return 0xFFFFFFFF;
                    return color;
                }, item.get());
            }
        }
    }

    public static boolean addStoneVariant(BlockState blockStateIn){
        return stone_varients.add(blockStateIn);
    }
}