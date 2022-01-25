package mightydanp.industrialtech.api.common.material;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.blocks.DenseOreBlock;
import mightydanp.industrialtech.api.common.blocks.OreBlock;
import mightydanp.industrialtech.api.common.blocks.SmallOreBlock;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.handler.StoneLayerHandler;
import mightydanp.industrialtech.api.common.items.*;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.DefaultFluidState;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.IFluidState;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.jsonconfig.flag.IMaterialFlag;
import mightydanp.industrialtech.api.common.material.fluid.ITFluid;
import mightydanp.industrialtech.api.common.material.fluid.ITFluidBlock;
import mightydanp.industrialtech.api.common.jsonconfig.icons.ITextureIcon;
import mightydanp.industrialtech.api.common.jsonconfig.ore.DefaultOreType;
import mightydanp.industrialtech.api.common.jsonconfig.ore.IOreType;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.IToolPart;
import mightydanp.industrialtech.common.stonelayers.ModStoneLayers;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.ArrayList;
import java.util.List;

import static mightydanp.industrialtech.api.common.jsonconfig.flag.DefaultMaterialFlag.*;
import static mightydanp.industrialtech.api.common.jsonconfig.flag.DefaultMaterialFlag.INGOT;
import static mightydanp.industrialtech.api.common.jsonconfig.tool.part.DefaultToolPart.*;

/**
 * Created by MightyDanp on 12/1/2021.
 */
public class ITMaterial extends net.minecraftforge.registries.ForgeRegistryEntry<ITMaterial> {
    public final String name;
    public final int color;
    public Pair<String, ITextureIcon> textureIcon;

    public Integer denseOreDensity = 8;
    public String symbol = null;
    public IOreType oreType = null;
    public Integer meltingPoint = null;
    public Integer boilingPoint = null;

    public Float fluidAcceleration = 0.014F;
    public IFluidState fluidState = null;
    public Integer fluidDensity = null;
    public Integer fluidViscosity = null;
    public Integer fluidLuminosity = null;
    public Integer durability = null;
    public Integer attackSpeed = null;
    public Float attackDamage = null;
    public Float weight = null;
    public List<Pair<ToolType, Integer>> toolTypes;
    public List<IToolPart> toolParts = new ArrayList<>();

    public  List<IMaterialFlag> materialFlags = new ArrayList<>();
    public List<Block> oreList = new ArrayList<>();
    public List<Block> smallOreList = new ArrayList<>();
    public List<Block> denseOreList = new ArrayList<>();
    public List<Item> oreItemList = new ArrayList<>();
    public List<Item> smallOreItemList = new ArrayList<>();
    public List<Item> denseOreItemList = new ArrayList<>();
    public Item ingot, gem, chippedGem, flawedGem, flawlessGem, legendaryGem, crushedOre, purifiedOre, centrifugedOre, dust, smallDust, tinyDust;
    public FlowingFluid fluid, fluid_flowing;
    public Block fluidBlock;
    public Item bucket, dullAxeHead, dullBuzzSawHead, dullChiselHead, dullHoeHead, dullPickaxe, dullArrowHead, dullSawHead, dullSwordHead;
    public Item drillHead, axeHead, buzzSawHead, chiselHead, fileHead, hammerHead, hoeHead, pickaxeHead, arrowHead, sawHead, shovelHead, swordHead, screwdriverHead;
    public Item wedge, wedgeHandle;


    public ITMaterial(String materialNameIn, int colorIn, Pair<String, ITextureIcon> textureIconLocationIn) {
        name = materialNameIn;
        color = colorIn;
        textureIcon = textureIconLocationIn;
    }

    public ITMaterial setElementalLocalization(String elementIn){
        symbol = elementIn;
        return this;
    }

    public ITMaterial setTemperatureProperties(int meltingPointIn, int boilingPointIn){
        meltingPoint = meltingPointIn;
        boilingPoint = boilingPointIn;
        return this;
    }

    public ITMaterial setOreType(IOreType oreTypeIn){
        oreType = oreTypeIn;

        if(oreTypeIn == DefaultOreType.ORE) {
            materialFlags.add(ORE);
        }

        if(oreTypeIn == DefaultOreType.GEM) {
            materialFlags.add(GEM);
        }

        if(oreTypeIn == DefaultOreType.CRYSTAL) {

        }
        return this;
    }

    public ITMaterial setDenseOreDensity(int densityIn){
        denseOreDensity = densityIn;
        return this;
    }

    public ITMaterial setFluidProperties(IFluidState stateIn, float accelerationIn, Integer densityIn, Integer luminosityIn, Integer viscosityIn){
        fluidState = stateIn;
        fluidAcceleration = accelerationIn;
        if(densityIn != null) fluidDensity = densityIn;
        if(luminosityIn != null) fluidLuminosity = luminosityIn;
        if(viscosityIn != null) fluidViscosity = viscosityIn;

        if(stateIn == DefaultFluidState.FLUID){
            materialFlags.add(FLUID);
        }

        if(stateIn == DefaultFluidState.GAS){
            materialFlags.add(GAS);
        }

        return this;
    }

    public ITMaterial setToolProperties(int attackSpeedIn, int durabilityIn, float attackDamageIn, float weightIn, List<Pair<ToolType, Integer>> toolTypesIn, List<IToolPart> toolPartIn){
        attackSpeed = attackSpeedIn;
        durability = durabilityIn;
        attackDamage = attackDamageIn;
        weight = weightIn;
        toolTypes = toolTypesIn;
        toolParts = toolPartIn ;
        return this;
    }



    public ITMaterial save() {
        for(IMaterialFlag flag : materialFlags){
            if(flag == ORE || flag == GEM){
                for(StoneLayerHandler stoneLayerHandler : ModStoneLayers.stoneLayerList){
                    Block ore = RegistryHandler.registerBlock(Ref.mod_id, stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore", new OreBlock(name + "_ore", AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE), stoneLayerHandler.layerBlock.defaultBlockState()));
                    oreList.add(ore);
                    Item oreItemR = RegistryHandler.registerItem(Ref.mod_id, stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore", 
                            new BlockOreItem(ore, new Item.Properties().tab(ModItemGroups.ore_tab), boilingPoint, meltingPoint, symbol));
                    oreItemList.add(oreItemR);
                    Block smallOreBlockR = RegistryHandler.registerBlock(Ref.mod_id, "small_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore", 
                            new SmallOreBlock("small_" + name + "_ore", AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE), stoneLayerHandler.layerBlock.defaultBlockState()));
                    smallOreList.add(smallOreBlockR);
                    Item smallOreItemR = RegistryHandler.registerItem(Ref.mod_id, "small_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore", 
                            new BlockItem(smallOreBlockR, new Item.Properties().tab(ModItemGroups.ore_tab)));
                    smallOreItemList.add(smallOreItemR);
                    Block denseOreBlockR = RegistryHandler.registerBlock(Ref.mod_id, "dense_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore", 
                            new DenseOreBlock("dense_" + name + "_ore", AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE), denseOreDensity, stoneLayerHandler.layerBlock.defaultBlockState(), oreItemList));
                    denseOreList.add(denseOreBlockR);
                    Item denseOreItemR = RegistryHandler.registerItem(Ref.mod_id, "dense_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore", 
                            new BlockOreItem(denseOreBlockR, new Item.Properties().tab(ModItemGroups.ore_tab), boilingPoint, meltingPoint, symbol));
                    denseOreItemList.add(denseOreItemR);
                }
                crushedOre = RegistryHandler.registerItem(Ref.mod_id,  "crushed_" + name + "_ore", new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                purifiedOre = RegistryHandler.registerItem(Ref.mod_id,  "purified_" + name + "_ore", new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                centrifugedOre = RegistryHandler.registerItem(Ref.mod_id,  "centrifuged_" + name + "_ore", new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
            }

            if(flag == ORE){}

            if(flag == GEM){
                gem = RegistryHandler.registerItem(Ref.mod_id,  name + "_gem", new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), symbol));
                chippedGem = RegistryHandler.registerItem(Ref.mod_id,  "chipped_" + name + "_gem", new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), symbol));
                flawedGem = RegistryHandler.registerItem(Ref.mod_id,  "flawed_" + name + "_gem", new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), symbol));
                flawlessGem = RegistryHandler.registerItem(Ref.mod_id,  "flawless_" + name + "_gem", new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), symbol));
                legendaryGem = RegistryHandler.registerItem(Ref.mod_id,  "legendary_" + name + "_gem", new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), symbol));
            }

            if(flag == DUST){
                dust = RegistryHandler.registerItem(Ref.mod_id,  "" + name + "_dust",  new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                smallDust = RegistryHandler.registerItem(Ref.mod_id,  "small_" + name + "_dust", new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                tinyDust = RegistryHandler.registerItem(Ref.mod_id,  "tiny_" + name + "_dust", new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
            }

            if(flag == FLUID || flag == GAS) {
                FluidAttributes.Builder attributes;

                if (flag == FLUID) {
                    attributes = FluidAttributes.builder(new ResourceLocation(Ref.mod_id, "fluid/" + name), new ResourceLocation(Ref.mod_id, "fluid/" + name + "_flowing")).temperature(meltingPoint).color(color);
                    if(fluidDensity != null) attributes.density(fluidDensity);
                    if(fluidLuminosity != null)attributes.luminosity(fluidLuminosity);
                    if(fluidViscosity != null) attributes.viscosity(fluidViscosity);
                    ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> fluid, () -> fluid_flowing, attributes);
                    fluid = (FlowingFluid) RegistryHandler.registerFluid(Ref.mod_id, name + "_still", new ITFluid(properties, true, color));
                    fluid_flowing = (FlowingFluid) RegistryHandler.registerFluid(Ref.mod_id, name + "_flowing", new ITFluid(properties, false, color));

                    fluidBlock = RegistryHandler.registerBlock(Ref.mod_id, name, new ITFluidBlock(()-> fluid, fluidAcceleration, color));
                }

                if (flag == GAS) {
                    attributes = FluidAttributes.builder(new ResourceLocation(Ref.mod_id, "fluid/" + name), new ResourceLocation(Ref.mod_id, "fluid/" + name)).temperature(boilingPoint).color(color).gaseous();
                    if(fluidDensity != null) attributes.density(fluidDensity);
                    if(fluidLuminosity != null)attributes.luminosity(fluidLuminosity);
                    if(fluidViscosity != null) attributes.viscosity(fluidViscosity);
                    ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> fluid, () -> fluid_flowing, attributes);
                    fluid = (FlowingFluid) RegistryHandler.registerFluid(Ref.mod_id, name + "_still", new ITFluid(properties, true, color));
                    fluid_flowing = (FlowingFluid) RegistryHandler.registerFluid(Ref.mod_id, name + "_flowing", new ITFluid(properties, false, color));

                    fluidBlock = RegistryHandler.registerBlock(Ref.mod_id, name, new ITFluidBlock(()-> fluid, fluidAcceleration, color));
                }
            }

            if(flag == INGOT){
                ingot = RegistryHandler.registerItem(Ref.mod_id, name + "_" + INGOT.name(),  new IngotItem(new Item.Properties().tab(ModItemGroups.item_tab), boilingPoint, meltingPoint, symbol));
            }

        }

        for(IToolPart flag : toolParts){
            if(flag == TOOL_HEAD){
                dullPickaxe = RegistryHandler.registerItem(Ref.mod_id, "dull_" + name + "_pickaxe_head", 
                        dullPickaxe = new DullToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab)));
                pickaxeHead = RegistryHandler.registerItem(Ref.mod_id,  name + "_pickaxe_head", 
                        pickaxeHead = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, attackSpeed, durability, attackDamage, weight, toolTypes));
                hammerHead = RegistryHandler.registerItem(Ref.mod_id,  name + "_hammer_head", 
                        hammerHead = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, attackSpeed, durability, attackDamage, weight, toolTypes));
                dullChiselHead = RegistryHandler.registerItem(Ref.mod_id, "dull_" + name + "_chisel_head", 
                        dullChiselHead = new DullToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab)));
                chiselHead = RegistryHandler.registerItem(Ref.mod_id,  name + "_chisel_head", 
                        chiselHead = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, attackSpeed, durability, attackDamage, weight, toolTypes));

            }

            if(flag == TOOL_WEDGE){
                wedge = RegistryHandler.registerItem(Ref.mod_id,  name + "_wedge", 
                        wedge = new ToolBindingItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, durability, weight));
            }

            if(flag == TOOL_WEDGE_HANDLE){
                wedgeHandle = RegistryHandler.registerItem(Ref.mod_id,  name + "_wedge_handle", 
                        wedgeHandle = new ToolHandleItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, durability, weight));
            }
        }
        return this;
    }

    public void registerColorForBlock() {
        for (Block block : oreList) {
            setupABlockColor(block);
        }
        for (Block block : smallOreList) {
            setupABlockColor(block);
        }
        for (Block block : denseOreList) {
            setupABlockColor(block);
        }
    }

    public void setupABlockColor(Block block){
        RenderTypeLookup.setRenderLayer(block, RenderType.cutout());
        Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) -> {
            if (tintIndex != 0)
                return 0xFFFFFFFF;
            return color;
        }, block);
    }

    public void registerColorForItem(){
        for (Item item : oreItemList) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return color;
            }, item);
        }
        for (Item item : smallOreItemList) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return color;
            }, item);
        }

        for (Item item : denseOreItemList) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return color;
            }, item);
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

    public void registerAItemColor(Item item, int layerNumberIn){
        if(item != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == layerNumberIn)
                    return color;
                else
                    return 0xFFFFFFFF;
            }, item);
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