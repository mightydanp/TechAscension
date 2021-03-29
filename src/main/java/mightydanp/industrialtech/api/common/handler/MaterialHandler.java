package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.blocks.DenseOreBlock;
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
    private int density;
    private String element;
    private int meltingPoint = 0;
    private int boilingPoint = 0;
    private int durability;
    private int speed;
    public static List<MaterialHandler> registeredMaterials = new ArrayList<>();
    public EnumMaterialFlags[] flags;
    public List<RegistryObject<Block>> oreBlock = new ArrayList<>();
    public List<RegistryObject<Block>> smallOreBlock = new ArrayList<>();
    public List<RegistryObject<Block>> denseOreBlock = new ArrayList<>();
    public List<RegistryObject<Item>> oreItem = new ArrayList<>();
    public List<RegistryObject<Item>> smallOreItem = new ArrayList<>();
    public List<RegistryObject<Item>> denseOreItem = new ArrayList<>();
    public RegistryObject<Item> ingotItem, gemItem, chippedGemItem, flawedGemItem, flawlessGemItem, legendaryGemItem, crushedOreItem, purifiedOreItem, centrifugedOreItem, dustItem, smallDustItem, tinyDustItem;
    public RegistryObject<Item> dullAxeHeadItem, dullBuzzSawHeadItem, dullChiselHeadItem, dullHoeHeadItem, dullPickaxeItem, dullArrowHeadItem, dullSawHeadItem, dullSwordHeadItem;
    public RegistryObject<Item> drillHeadItem, axeHeadItem, buzzSawHeadItem, chiselHeadItem, fileHeadItem, hammerHeadItem, hoeHeadItem, pickaxeHeadItem, arrowHeadItem, sawHeadItem, shovelHeadItem, swordHeadItem, screwdriverHeadItem;

    public static final List<BlockState> stone_variants = new ArrayList<BlockState>(){{
        add(Blocks.STONE.getDefaultState());
        add(Blocks.ANDESITE.getDefaultState());
        add(Blocks.GRANITE.getDefaultState());
        add(Blocks.DIORITE.getDefaultState());
    }};

    public MaterialHandler(String materialNameIn, int redIn, int greenIn, int blueIn, int alphaIn, int densityIn, String elementIn, int meltingPointIn, int boilingPointIn, EnumMaterialFlags... flagsIn) {
        materialName = materialNameIn;
        this.red = redIn;
        this.green = greenIn;
        this.blue = blueIn;
        this.alpha = alphaIn;
        this.density = densityIn;
        this.element = elementIn;
        this.meltingPoint = meltingPointIn;
        this.boilingPoint = boilingPointIn;
        this.flags = flagsIn;
        this.addFlag(flagsIn);
        registeredMaterials.add(this);
    }

    public MaterialHandler(String materialNameIn, int redIn, int greenIn, int blueIn, int alphaIn, int densityIn, String elementIn, EnumMaterialFlags... flagsIn) {
        materialName = materialNameIn;
        this.red = redIn;
        this.green = greenIn;
        this.blue = blueIn;
        this.alpha = alphaIn;
        this.density = densityIn;
        this.element = elementIn;
        this.flags = flagsIn;
        this.addFlag(flagsIn);
        registeredMaterials.add(this);
    }

    public MaterialHandler(String materialNameIn, int redIn, int greenIn, int blueIn, int alphaIn, int densityIn, EnumMaterialFlags... flagsIn) {
        materialName = materialNameIn;
        this.red = redIn;
        this.green = greenIn;
        this.blue = blueIn;
        this.alpha = alphaIn;
        this.density = densityIn;
        this.flags = flagsIn;
        this.addFlag(flagsIn);
        registeredMaterials.add(this);
    }

    public MaterialHandler(String materialNameIn, int redIn, int greenIn, int blueIn, int alphaIn, int speedIn, int durabilityIn, EnumMaterialFlags... flagsIn) {
        materialName = materialNameIn;
        this.red = redIn;
        this.green = greenIn;
        this.blue = blueIn;
        this.alpha = alphaIn;
        this.flags = flagsIn;
        this.addFlag(flagsIn);
        registeredMaterials.add(this);
    }

    protected void addFlag(EnumMaterialFlags... flagsIn) {
        for(EnumMaterialFlags flag : flagsIn){
            if(flag == ORE || flag == GEM){
                for(BlockState stone : stone_variants){
                    RegistryObject<Block> oreBlockR = RegistryHandler.BLOCKS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new OreBlock(materialName + "_ore", AbstractBlock.Properties.create(Material.ROCK), stone));
                    oreBlock.add(oreBlockR);
                    RegistryObject<Item> oreItemR = RegistryHandler.ITEMS.register(stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new BlockOreItem(oreBlockR.get(), new Item.Properties().group(ModItemGroups.ore_tab), boilingPoint, meltingPoint, element));
                    oreItem.add(oreItemR);
                    RegistryObject<Block> smallOreBlockR = RegistryHandler.BLOCKS.register("small_" + stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new SmallOreBlock("small_" + materialName + "_ore", AbstractBlock.Properties.create(Material.ROCK), stone));
                    smallOreBlock.add(smallOreBlockR);
                    RegistryObject<Item> smallOreItemR = RegistryHandler.ITEMS.register("small_" + stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new BlockItem(smallOreBlockR.get(), new Item.Properties().group(ModItemGroups.ore_tab)));
                    smallOreItem.add(smallOreItemR);
                    RegistryObject<Block> denseOreBlockR = RegistryHandler.BLOCKS.register("dense_" + stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new DenseOreBlock("dense_" + materialName + "_ore", AbstractBlock.Properties.create(Material.ROCK), density, stone, oreItem));
                    denseOreBlock.add(denseOreBlockR);
                    RegistryObject<Item> denseOreItemR = RegistryHandler.ITEMS.register("dense_" + stone.getBlock().getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new BlockOreItem(denseOreBlockR.get(), new Item.Properties().group(ModItemGroups.ore_tab), boilingPoint, meltingPoint, element));
                    denseOreItem.add(denseOreItemR);
                }
                crushedOreItem = RegistryHandler.ITEMS.register( "crushed_" + materialName + "_ore", () -> new OreProductsItem(new Item.Properties()
                        .group(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));
                purifiedOreItem = RegistryHandler.ITEMS.register( "purified_" + materialName + "_ore", () -> new OreProductsItem(new Item.Properties()
                        .group(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));
                centrifugedOreItem = RegistryHandler.ITEMS.register( "centrifuged_" + materialName + "_ore", () -> new OreProductsItem(new Item.Properties()
                        .group(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));
                dustItem = RegistryHandler.ITEMS.register( "" + materialName + "_dust", () -> new OreProductsItem(new Item.Properties()
                        .group(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));
                smallDustItem = RegistryHandler.ITEMS.register( "small_" + materialName + "_dust", () -> new OreProductsItem(new Item.Properties()
                        .group(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));
                tinyDustItem = RegistryHandler.ITEMS.register( "tiny_" + materialName + "_dust", () -> new OreProductsItem(new Item.Properties()
                        .group(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));


            }

            if(flag == ORE){}

            if(flag == GEM){
                gemItem = RegistryHandler.ITEMS.register( materialName + "_gem", () -> new GemItem(new Item.Properties()
                        .group(ModItemGroups.gem_tab), element));
                chippedGemItem = RegistryHandler.ITEMS.register( "chipped_" + materialName + "_gem", () -> new GemItem(new Item.Properties()
                        .group(ModItemGroups.gem_tab), element));
                flawedGemItem = RegistryHandler.ITEMS.register( "flawed_" + materialName + "_gem", () -> new GemItem(new Item.Properties()
                        .group(ModItemGroups.gem_tab), element));
                flawlessGemItem = RegistryHandler.ITEMS.register( "flawless_" + materialName + "_gem", () -> new GemItem(new Item.Properties()
                        .group(ModItemGroups.gem_tab), element));
                legendaryGemItem = RegistryHandler.ITEMS.register( "legendary_" + materialName + "_gem", () -> new GemItem(new Item.Properties()
                        .group(ModItemGroups.gem_tab), element));
            }

            if(flag == INGOT){
                ingotItem = RegistryHandler.ITEMS.register(materialName + "_" + INGOT.name(), () ->
                        new IngotItem(new Item.Properties().group(ModItemGroups.item_tab), boilingPoint, meltingPoint, element));
            }

            if(flag == TOOL_HEAD){
                dullPickaxeItem = RegistryHandler.ITEMS.register("dull_" + materialName + "_pickaxe_head", () ->
                        new DullToolHeadItem(new Item.Properties().group(ModItemGroups.tool_parts_tab)));
                pickaxeHeadItem = RegistryHandler.ITEMS.register( materialName + "_pickaxe_head", () ->
                        new ToolHeadItem(new Item.Properties().group(ModItemGroups.tool_parts_tab), boilingPoint, meltingPoint, speed, durability, element));
            }
        }
    }

    public void registerColorHandlerForBlock() {
        for (RegistryObject<Block> block : oreBlock) {
            setupABlockColor(block);
        }
        for (RegistryObject<Block> block : smallOreBlock) {
            setupABlockColor(block);
        }
        for (RegistryObject<Block> block : denseOreBlock) {
            setupABlockColor(block);
        }
    }

    public void setupABlockColor(RegistryObject<Block> block){
            RenderTypeLookup.setRenderLayer(block.get(), RenderType.getCutout());
            Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return ColorToInt();
            }, block.get());
    }

    public void registerColorForItem(){
        for (RegistryObject<Item> item : oreItem) {
                Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                    if (tintIndex != 0)
                        return 0xFFFFFFFF;
                    return ColorToInt();
                }, item.get());
            }
        for (RegistryObject<Item> item : smallOreItem) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return ColorToInt();
            }, item.get());
        }

        for (RegistryObject<Item> item : denseOreItem) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return ColorToInt();
            }, item.get());
        }
        registerAItemColor(dustItem, 0);
        registerAItemColor(smallDustItem, 0);
        registerAItemColor(tinyDustItem, 0);
        registerAItemColor(ingotItem, 0);
        registerAItemColor(gemItem, 0);
        registerAItemColor(chippedGemItem, 0);
        registerAItemColor(flawedGemItem, 0);
        registerAItemColor(flawedGemItem, 0);
        registerAItemColor(flawlessGemItem, 0);
        registerAItemColor(legendaryGemItem, 0);
        registerAItemColor(crushedOreItem, 0);
        registerAItemColor(purifiedOreItem, 0);
        registerAItemColor(centrifugedOreItem, 0);
    }

    public void registerAItemColor(RegistryObject<Item> item, int layerNumberIn){
        if(item != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == layerNumberIn)
                    return ColorToInt();
                else
                    return 0xFFFFFFFF;
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