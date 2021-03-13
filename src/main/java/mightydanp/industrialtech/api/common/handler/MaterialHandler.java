package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.blocks.OreBlock;
import mightydanp.industrialtech.api.common.blocks.SmallOreBlock;
import mightydanp.industrialtech.api.common.items.*;

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

import java.rmi.registry.Registry;
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
    public List<RegistryObject<Block>> blockOre = new ArrayList<>();
    public List<RegistryObject<Block>> blockSmallOre = new ArrayList<>();
    public List<RegistryObject<Item>> itemOre = new ArrayList<>();
    public List<RegistryObject<Item>> itemSmallOre = new ArrayList<>();
    public RegistryObject<Item> itemIngot, itemGem, itemChippedGem, itemFlawedGem, itemFlawlessGem, itemLegendaryGem, crushed_ore, purified_ore, centrifuged_ore;

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

    public MaterialHandler(String materialNameIn, int redIn, int greenIn, int blueIn, int alphaIn, String elementIn, EnumMaterialFlags... flagsIn) {
        materialName = materialNameIn;
        this.red = redIn;
        this.green = greenIn;
        this.blue = blueIn;
        this.alpha = alphaIn;
        this.element = elementIn;
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
        for(EnumMaterialFlags flag : flagsIn){
            if(flag == ORE){
                for(BlockState stone : stone_variants){
                    RegistryObject<Block> block = RegistryHandler.BLOCKS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new OreBlock(materialName + "_ore", AbstractBlock.Properties.create(Material.ROCK), stone));
                    blockOre.add(block);
                    RegistryObject<Item> item = RegistryHandler.ITEMS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new ItemBlockOre(block.get(), new Item.Properties().group(ModItemGroups.ore_tab), boilingPoint, meltingPoint, element));
                    itemOre.add(item);
                }


            }
            if(flag == GEM){
                for(BlockState stone : stone_variants){
                    RegistryObject<Block> block = RegistryHandler.BLOCKS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new OreBlock(materialName + "_ore", AbstractBlock.Properties.create(Material.ROCK), stone));
                    blockOre.add(block);
                    RegistryObject<Item> item = RegistryHandler.ITEMS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new ItemBlockOre(block.get(), new Item.Properties().group(ModItemGroups.ore_tab), boilingPoint, meltingPoint, element));
                    itemOre.add(item);
                }

                itemGem = RegistryHandler.ITEMS.register( materialName + "_gem", () -> new ItemGem(new Item.Properties()
                        .group(ModItemGroups.gem_tab), element));
                itemChippedGem = RegistryHandler.ITEMS.register( "chipped_" + materialName + "_gem", () -> new ItemGem(new Item.Properties()
                        .group(ModItemGroups.gem_tab), element));
                itemFlawedGem = RegistryHandler.ITEMS.register( "flawed_" + materialName + "_gem", () -> new ItemGem(new Item.Properties()
                        .group(ModItemGroups.gem_tab), element));
                itemFlawlessGem = RegistryHandler.ITEMS.register( "flawless_" + materialName + "_gem", () -> new ItemGem(new Item.Properties()
                        .group(ModItemGroups.gem_tab), element));
                itemLegendaryGem = RegistryHandler.ITEMS.register( "legendary_" + materialName + "_gem", () -> new ItemGem(new Item.Properties()
                        .group(ModItemGroups.gem_tab), element));
            }

            if(flag == SMALL_ORE){
                for(BlockState stone : stone_variants){
                    RegistryObject<Block> block = RegistryHandler.BLOCKS.register("small_" + stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new SmallOreBlock("small_" + materialName + "_ore", AbstractBlock.Properties.create(Material.ROCK), stone));
                    blockSmallOre.add(block);
                    RegistryObject<Item> item = RegistryHandler.ITEMS.register("small_" + stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new BlockItem(block.get(), new Item.Properties().group(ModItemGroups.ore_tab)));
                    itemSmallOre.add(item);
                }
            }

            if(flag == ORE || flag == GEM){
                crushed_ore = RegistryHandler.ITEMS.register( "crushed_" + materialName + "_ore", () -> new OreProductsItem(new Item.Properties()
                        .group(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));
                purified_ore = RegistryHandler.ITEMS.register( "purified_" + materialName + "_ore", () -> new OreProductsItem(new Item.Properties()
                        .group(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));
                centrifuged_ore = RegistryHandler.ITEMS.register( "centrifuged_" + materialName + "_ore", () -> new OreProductsItem(new Item.Properties()
                        .group(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));
            }

            if(flag == INGOT){
                itemIngot = RegistryHandler.ITEMS.register(materialName + "_" + INGOT.name(), () ->
                        new ItemIngot(new Item.Properties().group(ModItemGroups.item_tab), boilingPoint, meltingPoint, element));
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

        if(itemIngot != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0)
                    return ColorToInt();
                else
                    return 0xFFFFFFFF;
            }, itemIngot.get());
        }

        if(itemGem != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0)
                    return ColorToInt();
                else
                    return 0xFFFFFFFF;
            }, itemGem.get());
        }

        if(itemChippedGem != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0)
                    return ColorToInt();
                else
                    return 0xFFFFFFFF;
            }, itemChippedGem.get());
        }

        if(itemFlawedGem != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0)
                    return ColorToInt();
                else
                    return 0xFFFFFFFF;
            }, itemFlawedGem.get());
        }

        if(itemFlawedGem != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0)
                    return ColorToInt();
                else
                    return 0xFFFFFFFF;
            }, itemFlawedGem.get());
        }

        if(itemFlawlessGem != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0)
                    return ColorToInt();
                else
                    return 0xFFFFFFFF;
            }, itemFlawlessGem.get());
        }

        if(itemLegendaryGem != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0)
                    return ColorToInt();
                else
                    return 0xFFFFFFFF;
            }, itemLegendaryGem.get());
        }
        if(crushed_ore != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0)
                    return ColorToInt();
                else
                    return 0xFFFFFFFF;
            }, crushed_ore.get());
        }
        if(purified_ore != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0)
                    return ColorToInt();
                else
                    return 0xFFFFFFFF;
            }, purified_ore.get());
        }
        if(centrifuged_ore != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0)
                    return ColorToInt();
                else
                    return 0xFFFFFFFF;
            }, centrifuged_ore.get());
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