package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.blocks.OreBlock;
import mightydanp.industrialtech.api.common.blocks.SmallOreBlock;
import mightydanp.industrialtech.api.common.items.ItemBlockOre;
import mightydanp.industrialtech.api.common.items.ItemIngot;
import mightydanp.industrialtech.api.common.items.ModItemGroups;
import static mightydanp.industrialtech.api.common.libs.EnumMaterialFlags.*;

import mightydanp.industrialtech.api.common.libs.EnumMaterialFlags;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class MaterialHandler {
    public final String materialName;
    private final int red;
    private final int green;
    private final int blue;
    private final int alpha;
    private String element;
    private int meltingPoint = 0;
    private int boilingPoint = 0;
    public static List<MaterialHandler> registeredMaterials = new ArrayList<>();
    public EnumMaterialFlags[] flags;
    public List<RegistryObject<Block>>  blockOre = new ArrayList<>();
    public List<RegistryObject<Block>>  blockSmallOre = new ArrayList<>();
    public List<RegistryObject<Item>>  itemOre = new ArrayList<>();
    public List<RegistryObject<Item>>  itemSmallOre = new ArrayList<>();
    public List<RegistryObject<Item>>  itemIngot = new ArrayList<>();

    public static final List<BlockState> stone_variants = new ArrayList<BlockState>(){{
        add(Blocks.STONE.getDefaultState());
        add(Blocks.ANDESITE.getDefaultState());
        add(Blocks.GRANITE.getDefaultState());
        add(Blocks.DIORITE.getDefaultState());
    }};

    public MaterialHandler(String materialNameIn, int redIn, int greenIn, int blueIn, int alphaIn, String elementIn, int meltingPointIn, int boilingPointIn, EnumMaterialFlags... flagsIn) {
        materialName = materialNameIn;
        this.red = redIn;
        this.green = greenIn;
        this.blue = blueIn;
        this.alpha = alphaIn;
        this.element = elementIn;
        this.meltingPoint = meltingPointIn;
        this.boilingPoint = boilingPointIn;
        this.flags = flagsIn;
        this.addFlag(flagsIn);
        registeredMaterials.add(this);
    }

    public MaterialHandler(String materialNameIn, int redIn, int greenIn, int blueIn, int alpha, EnumMaterialFlags... flagsIn) {
        materialName = materialNameIn;
        this.red = redIn;
        this.green = greenIn;
        this.blue = blueIn;
        this.alpha = alpha;
        this.flags = flagsIn;
        this.addFlag(flagsIn);
        registeredMaterials.add(this);
    }

    protected void addFlag(EnumMaterialFlags... flagsIn) {
        for(EnumMaterialFlags obj : flagsIn){
            if(obj == ORE){
                for(BlockState stone : stone_variants){
                    RegistryObject<Block> block = RegistryHandler.BLOCKS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new OreBlock(materialName + "_ore", AbstractBlock.Properties.create(Material.ROCK), stone));
                    blockOre.add(block);
                    RegistryObject<Item> item = RegistryHandler.ITEMS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new ItemBlockOre(block.get(), new Item.Properties().group(ModItemGroups.ore_tab), element));
                    itemOre.add(item);
                }
            }
            if(obj == GEM){
                for(BlockState stone : stone_variants){
                    RegistryObject<Block> block = RegistryHandler.BLOCKS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new OreBlock(materialName + "_ore", AbstractBlock.Properties.create(Material.ROCK), stone));
                    blockOre.add(block);
                    RegistryObject<Item> item = RegistryHandler.ITEMS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new ItemBlockOre(block.get(), new Item.Properties().group(ModItemGroups.ore_tab), element));
                    itemOre.add(item);
                }
            }
            if(obj == SMALL_ORE){
                for(BlockState stone : stone_variants){
                    RegistryObject<Block> block = RegistryHandler.BLOCKS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_small_ore", () ->
                            new SmallOreBlock(materialName + "_small_ore", AbstractBlock.Properties.create(Material.ROCK), stone));
                    blockSmallOre.add(block);
                    RegistryObject<Item> item = RegistryHandler.ITEMS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_small_ore", () ->
                            new BlockItem(block.get(), new Item.Properties().group(ModItemGroups.ore_tab)));
                    itemSmallOre.add(item);
                }
            }
            if(obj == INGOT){
                RegistryObject<Item> item = RegistryHandler.ITEMS.register(materialName + INGOT.getSufixString(), () ->
                        new ItemIngot(new Item.Properties().group(ModItemGroups.item_tab), INGOT));
                ((ItemIngot)item.get()).setMeltingPoint(meltingPoint);
                ((ItemIngot)item.get()).setBoilingPoint(boilingPoint);
                itemIngot.add(item);
            }
            if(obj == HOT_INGOT){
                RegistryObject<Item> item = RegistryHandler.ITEMS.register(HOT_INGOT.getPrefixString() + materialName + HOT_INGOT.getSufixString(), () ->
                        new ItemIngot(new Item.Properties().group(ModItemGroups.item_tab), HOT_INGOT));
                ((ItemIngot)item.get()).setMeltingPoint(meltingPoint);
                itemIngot.add(item);
            }
            if(obj == SOFTENED_INGOT){
                RegistryObject<Item> item = RegistryHandler.ITEMS.register(SOFTENED_INGOT.getPrefixString() + materialName + SOFTENED_INGOT.getSufixString(), () ->
                        new ItemIngot(new Item.Properties().group(ModItemGroups.item_tab), SOFTENED_INGOT));
                ((ItemIngot)item.get()).setMeltingPoint(meltingPoint);
                itemIngot.add(item);
            }
            if(obj == HARDENED_INGOT){
                RegistryObject<Item> item = RegistryHandler.ITEMS.register(HARDENED_INGOT.getPrefixString() + materialName + HARDENED_INGOT.getSufixString(), () ->
                        new ItemIngot(new Item.Properties().group(ModItemGroups.item_tab), HARDENED_INGOT));
                ((ItemIngot)item.get()).setMeltingPoint(meltingPoint);
                itemIngot.add(item);
            }
        }
    }

    public void registerColorHandlerForBlock() {
        for (RegistryObject<Block> block : blockOre) {
            RenderTypeLookup.setRenderLayer(block.get(), RenderType.getCutout());
            Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) -> {
                    if (tintIndex != 0)
                        return 0xFFFFFFFF;
                    return ColorToInt();
                }, block.get());
        }
        for (RegistryObject<Block> block : blockSmallOre) {
            RenderTypeLookup.setRenderLayer(block.get(), RenderType.getCutout());
            Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return ColorToInt();
            }, block.get());
        }
    }

    public void registerColorForItem(){
        for (RegistryObject<Item> item : itemOre) {
                Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                    if (tintIndex != 0)
                        return 0xFFFFFFFF;
                    return ColorToInt();
                }, item.get());
            }
        for (RegistryObject<Item> item : itemSmallOre) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return ColorToInt();
            }, item.get());
        }
        for (RegistryObject<Item> item : itemIngot) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return ColorToInt();
            }, item.get());
        }
    }

    public static boolean addStoneVariant(BlockState blockStateIn){
        return stone_variants.add(blockStateIn);
    }

    public int ColorToInt() {
        int ret = 0;
        ret += red; ret = ret << 8; ret += green; ret = ret << 8; ret += blue;
        return ret;
    }
}