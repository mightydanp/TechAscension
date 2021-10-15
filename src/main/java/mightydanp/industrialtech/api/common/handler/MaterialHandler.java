package mightydanp.industrialtech.api.common.handler;

import com.mojang.datafixers.util.Pair;;
import mightydanp.industrialtech.api.common.blocks.DenseOreBlock;
import mightydanp.industrialtech.api.common.blocks.OreBlock;
import mightydanp.industrialtech.api.common.blocks.SmallOreBlock;
import mightydanp.industrialtech.api.common.items.*;

import static mightydanp.industrialtech.api.common.libs.EnumMaterialFlags.*;

import mightydanp.industrialtech.api.common.libs.EnumMaterialFlags;
import mightydanp.industrialtech.api.common.libs.EnumMaterialTextureFlags;
import mightydanp.industrialtech.common.stonelayers.ModStoneLayers;
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
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class MaterialHandler {
    public final String materialName;
    private final int color;
    private int density;
    private String element;
    private int meltingPoint = 0;
    private int boilingPoint = 0;
    public final EnumMaterialTextureFlags textureFlag;
    private int durability;
    private int speed;
    private int damage;
    private float attackDamage;
    private float weight;
    private List<Pair<ToolType, Integer>> toolTypes;

    public static List<MaterialHandler> registeredMaterials = new ArrayList<>();
    public EnumMaterialFlags[] flags;
    public List<RegistryObject<Block>> ore = new ArrayList<>();
    public List<RegistryObject<Block>> smallOre = new ArrayList<>();
    public List<RegistryObject<Block>> denseOre = new ArrayList<>();
    public List<RegistryObject<Item>> oreItem = new ArrayList<>();
    public List<RegistryObject<Item>> smallOreItem = new ArrayList<>();
    public List<RegistryObject<Item>> denseOreItem = new ArrayList<>();
    public RegistryObject<Item> ingot, gem, chippedGem, flawedGem, flawlessGem, legendaryGem, crushedOre, purifiedOre, centrifugedOre, dust, smallDust, tinyDust;
    public static Item ingotItem, gemItem, chippedGemItem, flawedGemItem, flawlessGemItem, legendaryGemItem, crushedOreItem, purifiedOreItem, centrifugedOreItem, dustItem, smallDustItem, tinyDustItem;
    public RegistryObject<Item> dullAxeHead, dullBuzzSawHead, dullChiselHead, dullHoeHead, dullPickaxe, dullArrowHead, dullSawHead, dullSwordHead;
    public static Item dullAxeHeadItem, dullBuzzSawHeadItem, dullChiselHeadItem, dullHoeHeadItem, dullPickaxeItem, dullArrowHeadItem, dullSawHeadItem, dullSwordHeadItem;
    public RegistryObject<Item> drillHead, axeHead, buzzSawHead, chiselHead, fileHead, hammerHead, hoeHead, pickaxeHead, arrowHead, sawHead, shovelHead, swordHead, screwdriverHead;
    public static Item drillHeadItem, axeHeadItem, buzzSawHeadItem, chiselHeadItem, fileHeadItem, hammerHeadItem, hoeHeadItem, pickaxeHeadItem, arrowHeadItem, sawHeadItem, shovelHeadItem, swordHeadItem, screwdriverHeadItem;
    public RegistryObject<Item> wedge, wedgeHandle;
    public static Item wedgeItem, wedgeHandleItem;

    public MaterialHandler(String materialNameIn, int colorIn, EnumMaterialTextureFlags textureFlagIn, int densityIn, String elementIn, int meltingPointIn, int boilingPointIn, EnumMaterialFlags... flagsIn) {
        materialName = materialNameIn;
        this.color = colorIn;
        this.density = densityIn;
        this.element = elementIn;
        this.textureFlag = textureFlagIn;
        this.meltingPoint = meltingPointIn;
        this.boilingPoint = boilingPointIn;
        this.flags = flagsIn;
        this.addFlag(flagsIn);
        registeredMaterials.add(this);
    }

    public MaterialHandler(String materialNameIn, int colorIn, EnumMaterialTextureFlags textureFlagIn, int densityIn, String elementIn, EnumMaterialFlags... flagsIn) {
        materialName = materialNameIn;
        this.color = colorIn;
        this.density = densityIn;
        this.element = elementIn;
        this.textureFlag = textureFlagIn;
        this.flags = flagsIn;
        this.addFlag(flagsIn);
        registeredMaterials.add(this);
    }

    public MaterialHandler(String materialNameIn, int colorIn, EnumMaterialTextureFlags textureFlagIn, int densityIn, EnumMaterialFlags... flagsIn) {
        materialName = materialNameIn;
        this.color = colorIn;
        this.density = densityIn;
        this.textureFlag = textureFlagIn;
        this.flags = flagsIn;
        this.addFlag(flagsIn);
        registeredMaterials.add(this);
    }

    public MaterialHandler(String materialNameIn, int colorIn, EnumMaterialTextureFlags textureFlagIn, int speedIn, int durabilityIn, int damageIn, float attackDamageIn, float weightIn, List<Pair<ToolType, Integer>> toolTypesIn, EnumMaterialFlags... flagsIn) {
        materialName = materialNameIn;
        this.color = colorIn;
        this.speed = speedIn;
        this.durability = durabilityIn;
        this.damage = damageIn;
        this.attackDamage = attackDamageIn;
        this.weight = weightIn;
        this.toolTypes = toolTypesIn;
        this.textureFlag = textureFlagIn;
        this.flags = flagsIn;
        this.addFlag(flagsIn);
        registeredMaterials.add(this);
    }

    protected void addFlag(EnumMaterialFlags... flagsIn) {
        for(EnumMaterialFlags flag : flagsIn){
            if(flag == ORE || flag == GEM){
                for(StoneLayerHandler stoneLayerHandler : ModStoneLayers.stoneLayerList){
                    RegistryObject<Block> oreBlockR = RegistryHandler.BLOCKS.register(stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new OreBlock(materialName + "_ore", AbstractBlock.Properties.of(Material.STONE), stoneLayerHandler.layerBlock.defaultBlockState()));
                    ore.add(oreBlockR);
                    RegistryObject<Item> oreItemR = RegistryHandler.ITEMS.register(stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new BlockOreItem(oreBlockR.get(), new Item.Properties().tab(ModItemGroups.ore_tab), boilingPoint, meltingPoint, element));
                    oreItem.add(oreItemR);
                    RegistryObject<Block> smallOreBlockR = RegistryHandler.BLOCKS.register("small_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new SmallOreBlock("small_" + materialName + "_ore", AbstractBlock.Properties.of(Material.STONE), stoneLayerHandler.layerBlock.defaultBlockState()));
                    smallOre.add(smallOreBlockR);
                    RegistryObject<Item> smallOreItemR = RegistryHandler.ITEMS.register("small_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new BlockItem(smallOreBlockR.get(), new Item.Properties().tab(ModItemGroups.ore_tab)));
                    smallOreItem.add(smallOreItemR);
                    RegistryObject<Block> denseOreBlockR = RegistryHandler.BLOCKS.register("dense_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new DenseOreBlock("dense_" + materialName + "_ore", AbstractBlock.Properties.of(Material.STONE), density, stoneLayerHandler.layerBlock.defaultBlockState(), oreItem));
                    denseOre.add(denseOreBlockR);
                    RegistryObject<Item> denseOreItemR = RegistryHandler.ITEMS.register("dense_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + materialName + "_ore", () ->
                            new BlockOreItem(denseOreBlockR.get(), new Item.Properties().tab(ModItemGroups.ore_tab), boilingPoint, meltingPoint, element));
                    denseOreItem.add(denseOreItemR);
                }
                crushedOre = RegistryHandler.ITEMS.register( "crushed_" + materialName + "_ore", () -> crushedOreItem = new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));
                purifiedOre = RegistryHandler.ITEMS.register( "purified_" + materialName + "_ore", () -> purifiedOreItem = new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));
                centrifugedOre = RegistryHandler.ITEMS.register( "centrifuged_" + materialName + "_ore", () -> centrifugedOreItem = new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));
                dust = RegistryHandler.ITEMS.register( "" + materialName + "_dust", () -> dustItem = new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));
                smallDust = RegistryHandler.ITEMS.register( "small_" + materialName + "_dust", () -> smallDustItem = new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));
                tinyDust = RegistryHandler.ITEMS.register( "tiny_" + materialName + "_dust", () -> tinyDustItem = new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, element));


            }

            if(flag == ORE){}

            if(flag == GEM){
                gem = RegistryHandler.ITEMS.register( materialName + "_gem", () -> gemItem = new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), element));
                chippedGem = RegistryHandler.ITEMS.register( "chipped_" + materialName + "_gem", () -> chippedGemItem = new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), element));
                flawedGem = RegistryHandler.ITEMS.register( "flawed_" + materialName + "_gem", () -> flawedGemItem = new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), element));
                flawlessGem = RegistryHandler.ITEMS.register( "flawless_" + materialName + "_gem", () -> flawlessGemItem = new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), element));
                legendaryGem = RegistryHandler.ITEMS.register( "legendary_" + materialName + "_gem", () -> legendaryGemItem = new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), element));
            }

            if(flag == INGOT){
                ingot = RegistryHandler.ITEMS.register(materialName + "_" + INGOT.name(), () ->
                        ingotItem = new IngotItem(new Item.Properties().tab(ModItemGroups.item_tab), boilingPoint, meltingPoint, element));
            }

            if(flag == TOOL_HEAD){
                dullPickaxe = RegistryHandler.ITEMS.register("dull_" + materialName + "_pickaxe_head", () ->
                        dullPickaxeItem = new DullToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab)));
                pickaxeHead = RegistryHandler.ITEMS.register( materialName + "_pickaxe_head", () ->
                        pickaxeHeadItem = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), materialName, element, color, textureFlag, boilingPoint, meltingPoint, speed, durability, attackDamage, weight, toolTypes));
                hammerHead = RegistryHandler.ITEMS.register( materialName + "_hammer_head", () ->
                        hammerHeadItem = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), materialName, element, color, textureFlag, boilingPoint, meltingPoint, speed, durability, attackDamage, weight, toolTypes));
                dullChiselHead = RegistryHandler.ITEMS.register("dull_" + materialName + "_chisel_head", () ->
                        dullChiselHeadItem = new DullToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab)));
                chiselHead = RegistryHandler.ITEMS.register( materialName + "_chisel_head", () ->
                        chiselHeadItem = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), materialName, element, color, textureFlag, boilingPoint, meltingPoint, speed, durability, attackDamage, weight, toolTypes));

            }

            if(flag == TOOL_WEDGE){
                wedge = RegistryHandler.ITEMS.register( materialName + "_wedge", () ->
                        wedgeItem = new ToolBindingItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), materialName, element, color, textureFlag, boilingPoint, meltingPoint, durability, weight));
            }

            if(flag == TOOL_WEDGE_HANDLE){
                wedgeHandle = RegistryHandler.ITEMS.register( materialName + "_wedge_handle", () ->
                        wedgeHandleItem = new ToolHandleItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), materialName, element, color, textureFlag, boilingPoint, meltingPoint, durability, weight));
            }
        }
    }

    public void registerColorHandlerForBlock() {
        for (RegistryObject<Block> block : ore) {
            setupABlockColor(block);
        }
        for (RegistryObject<Block> block : smallOre) {
            setupABlockColor(block);
        }
        for (RegistryObject<Block> block : denseOre) {
            setupABlockColor(block);
        }
    }

    public void setupABlockColor(RegistryObject<Block> block){
            RenderTypeLookup.setRenderLayer(block.get(), RenderType.cutout());
            Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return color;
            }, block.get());
    }

    public void registerColorForItem(){
        for (RegistryObject<Item> item : oreItem) {
                Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                    if (tintIndex != 0)
                        return 0xFFFFFFFF;
                    return color;
                }, item.get());
            }
        for (RegistryObject<Item> item : smallOreItem) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return color;
            }, item.get());
        }

        for (RegistryObject<Item> item : denseOreItem) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return color;
            }, item.get());
        }
        registerAItemColor(dust, 0);
        registerAItemColor(smallDust, 0);
        registerAItemColor(tinyDust, 0);
        registerAItemColor(ingot, 0);
        registerAItemColor(gem, 0);
        registerAItemColor(chippedGem, 0);
        registerAItemColor(flawedGem, 0);
        registerAItemColor(flawedGem, 0);
        registerAItemColor(flawlessGem, 0);
        registerAItemColor(legendaryGem, 0);
        registerAItemColor(crushedOre, 0);
        registerAItemColor(purifiedOre, 0);
        registerAItemColor(centrifugedOre, 0);
        registerAItemColor(dullPickaxe, 0);
        registerAItemColor(pickaxeHead, 0);
        registerAItemColor(hammerHead, 0);
        registerAItemColor(wedge, 0);
        registerAItemColor(wedgeHandle, 0);
        registerAItemColor(dullChiselHead, 0);
        registerAItemColor(chiselHead, 0);

    }

    public void registerAItemColor(RegistryObject<Item> item, int layerNumberIn){
        if(item != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == layerNumberIn)
                    return color;
                else
                    return 0xFFFFFFFF;
            }, item.get());
        }
    }

    /*
    public int ColorToInt() {
        int ret = 0;
        ret += red; ret = ret << 8; ret += green; ret = ret << 8; ret += blue;
        return ret;
    }
     */
}