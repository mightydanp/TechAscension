package mightydanp.industrialtech.api.common.material;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.blocks.DenseOreBlock;
import mightydanp.industrialtech.api.common.blocks.OreBlock;
import mightydanp.industrialtech.api.common.blocks.SmallOreBlock;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.handler.StoneLayerHandler;
import mightydanp.industrialtech.api.common.items.*;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.material.flag.IMaterialFlag;
import mightydanp.industrialtech.api.common.material.fluid.ITFluid;
import mightydanp.industrialtech.api.common.material.fluid.ITFluidBlock;
import mightydanp.industrialtech.api.common.material.fluidstate.DefaultFluidState;
import mightydanp.industrialtech.api.common.material.fluidstate.IFluidState;
import mightydanp.industrialtech.api.common.material.icons.ITextureIcon;
import mightydanp.industrialtech.api.common.material.ore.DefaultOreType;
import mightydanp.industrialtech.api.common.material.ore.IOreType;
import mightydanp.industrialtech.api.common.material.serializable.MaterialSerializable;
import mightydanp.industrialtech.api.common.material.tool.part.flag.IToolPart;
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
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;
import java.util.List;

import static mightydanp.industrialtech.api.common.material.flag.DefaultMaterialFlag.*;
import static mightydanp.industrialtech.api.common.material.flag.DefaultMaterialFlag.INGOT;
import static mightydanp.industrialtech.api.common.material.tool.part.flag.DefaultToolPart.*;
import static mightydanp.industrialtech.api.common.material.serializable.MaterialSerializable.*;

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

    public List<IMaterialFlag> materialFlags = new ArrayList<>();
    public List<IToolPart> toolFlags = new ArrayList<>();
    public List<RegistryObject<Block>> ore = new ArrayList<>();
    public List<RegistryObject<Block>> smallOre = new ArrayList<>();
    public List<RegistryObject<Block>> denseOre = new ArrayList<>();
    public List<RegistryObject<Item>> oreItem = new ArrayList<>();
    public List<RegistryObject<Item>> smallOreItem = new ArrayList<>();
    public List<RegistryObject<Item>> denseOreItem = new ArrayList<>();
    public RegistryObject<Item> ingot, gem, chippedGem, flawedGem, flawlessGem, legendaryGem, crushedOre, purifiedOre, centrifugedOre, dust, smallDust, tinyDust;
    public Item ingotItem, gemItem, chippedGemItem, flawedGemItem, flawlessGemItem, legendaryGemItem, crushedOreItem, purifiedOreItem, centrifugedOreItem, dustItem, smallDustItem, tinyDustItem;
    public RegistryObject<FlowingFluid> fluid, fluid_flowing;
    public RegistryObject<Item> bucket;
    public RegistryObject<Block> fluidBlock;
    public static Item bucketItem;
    public RegistryObject<Item> dullAxeHead, dullBuzzSawHead, dullChiselHead, dullHoeHead, dullPickaxe, dullArrowHead, dullSawHead, dullSwordHead;
    public static Item dullAxeHeadItem, dullBuzzSawHeadItem, dullChiselHeadItem, dullHoeHeadItem, dullPickaxeItem, dullArrowHeadItem, dullSawHeadItem, dullSwordHeadItem;
    public RegistryObject<Item> drillHead, axeHead, buzzSawHead, chiselHead, fileHead, hammerHead, hoeHead, pickaxeHead, arrowHead, sawHead, shovelHead, swordHead, screwdriverHead;
    public static Item drillHeadItem, axeHeadItem, buzzSawHeadItem, chiselHeadItem, fileHeadItem, hammerHeadItem, hoeHeadItem, pickaxeHeadItem, arrowHeadItem, sawHeadItem, shovelHeadItem, swordHeadItem, screwdriverHeadItem;
    public RegistryObject<Item> wedge, wedgeHandle;
    public static Item wedgeItem, wedgeHandleItem;


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

    public ITMaterial setToolProperties(int attackSpeedIn, int durabilityIn, float attackDamageIn, float weightIn, List<Pair<ToolType, Integer>> toolTypesIn, List<IToolPart> toolPartFlagsIn){
        attackSpeed = attackSpeedIn;
        durability = durabilityIn;
        attackDamage = attackDamageIn;
        weight = weightIn;
        toolTypes = toolTypesIn;
        toolFlags = toolPartFlagsIn ;
        return this;
    }



    public ITMaterial save() {
        for(IMaterialFlag flag : materialFlags){
            if(flag == ORE || flag == GEM){
                for(StoneLayerHandler stoneLayerHandler : ModStoneLayers.stoneLayerList){
                    RegistryObject<Block> oreBlockR = RegistryHandler.BLOCKS.register(stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore", () ->
                            new OreBlock(name + "_ore", AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE), stoneLayerHandler.layerBlock.defaultBlockState()));
                    ore.add(oreBlockR);
                    RegistryObject<Item> oreItemR = RegistryHandler.ITEMS.register(stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore", () ->
                            new BlockOreItem(oreBlockR.get(), new Item.Properties().tab(ModItemGroups.ore_tab), boilingPoint, meltingPoint, symbol));
                    oreItem.add(oreItemR);
                    RegistryObject<Block> smallOreBlockR = RegistryHandler.BLOCKS.register("small_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore", () ->
                            new SmallOreBlock("small_" + name + "_ore", AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE), stoneLayerHandler.layerBlock.defaultBlockState()));
                    smallOre.add(smallOreBlockR);
                    RegistryObject<Item> smallOreItemR = RegistryHandler.ITEMS.register("small_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore", () ->
                            new BlockItem(smallOreBlockR.get(), new Item.Properties().tab(ModItemGroups.ore_tab)));
                    smallOreItem.add(smallOreItemR);
                    RegistryObject<Block> denseOreBlockR = RegistryHandler.BLOCKS.register("dense_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore", () ->
                            new DenseOreBlock("dense_" + name + "_ore", AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE), denseOreDensity, stoneLayerHandler.layerBlock.defaultBlockState(), oreItem));
                    denseOre.add(denseOreBlockR);
                    RegistryObject<Item> denseOreItemR = RegistryHandler.ITEMS.register("dense_" + stoneLayerHandler.layerBlock.getRegistryName().toString().split(":")[1] + "_" + name + "_ore", () ->
                            new BlockOreItem(denseOreBlockR.get(), new Item.Properties().tab(ModItemGroups.ore_tab), boilingPoint, meltingPoint, symbol));
                    denseOreItem.add(denseOreItemR);
                }
                crushedOre = RegistryHandler.ITEMS.register( "crushed_" + name + "_ore", () -> crushedOreItem = new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                purifiedOre = RegistryHandler.ITEMS.register( "purified_" + name + "_ore", () -> purifiedOreItem = new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                centrifugedOre = RegistryHandler.ITEMS.register( "centrifuged_" + name + "_ore", () -> centrifugedOreItem = new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
            }

            if(flag == ORE){}

            if(flag == GEM){
                gem = RegistryHandler.ITEMS.register( name + "_gem", () -> gemItem = new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), symbol));
                chippedGem = RegistryHandler.ITEMS.register( "chipped_" + name + "_gem", () -> chippedGemItem = new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), symbol));
                flawedGem = RegistryHandler.ITEMS.register( "flawed_" + name + "_gem", () -> flawedGemItem = new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), symbol));
                flawlessGem = RegistryHandler.ITEMS.register( "flawless_" + name + "_gem", () -> flawlessGemItem = new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), symbol));
                legendaryGem = RegistryHandler.ITEMS.register( "legendary_" + name + "_gem", () -> legendaryGemItem = new GemItem(new Item.Properties()
                        .tab(ModItemGroups.gem_tab), symbol));
            }

            if(flag == DUST){
                dust = RegistryHandler.ITEMS.register( "" + name + "_dust", () -> dustItem = new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                smallDust = RegistryHandler.ITEMS.register( "small_" + name + "_dust", () -> smallDustItem = new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                tinyDust = RegistryHandler.ITEMS.register( "tiny_" + name + "_dust", () -> tinyDustItem = new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
            }

            if(flag == FLUID || flag == GAS) {
                FluidAttributes.Builder attributes;

                if (flag == FLUID) {
                    attributes = FluidAttributes.builder(new ResourceLocation(Ref.mod_id, "fluid/" + name), new ResourceLocation(Ref.mod_id, "fluid/" + name + "_flowing")).temperature(meltingPoint).color(color);
                    if(fluidDensity != null) attributes.density(fluidDensity);
                    if(fluidLuminosity != null)attributes.luminosity(fluidLuminosity);
                    if(fluidViscosity != null) attributes.viscosity(fluidViscosity);
                    ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(fluid, fluid_flowing, attributes);
                    fluid = RegistryHandler.FLUIDS.register(name + "_still", ()-> new ITFluid(properties, true, color));
                    fluid_flowing = RegistryHandler.FLUIDS.register(name + "_flowing", ()-> new ITFluid(properties, false, color));

                    fluidBlock = RegistryHandler.BLOCKS.register(name, ()-> new ITFluidBlock(()-> fluid.get(), fluidAcceleration, color));
                }

                if (flag == GAS) {
                    attributes = FluidAttributes.builder(new ResourceLocation(Ref.mod_id, "fluid/" + name), new ResourceLocation(Ref.mod_id, "fluid/" + name)).temperature(boilingPoint).color(color).gaseous();
                    if(fluidDensity != null) attributes.density(fluidDensity);
                    if(fluidLuminosity != null)attributes.luminosity(fluidLuminosity);
                    if(fluidViscosity != null) attributes.viscosity(fluidViscosity);
                    ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(fluid, fluid_flowing, attributes);
                    fluid = RegistryHandler.FLUIDS.register(name + "_still", ()-> new ITFluid(properties, true, color));
                    fluid_flowing = RegistryHandler.FLUIDS.register(name + "_flowing", ()-> new ITFluid(properties, false, color));

                    fluidBlock = RegistryHandler.BLOCKS.register(name, ()-> new ITFluidBlock(()-> fluid.get(), fluidAcceleration, color));
                }
            }

            if(flag == INGOT){
                ingot = RegistryHandler.ITEMS.register(name + "_" + INGOT.name(), () ->
                        ingotItem = new IngotItem(new Item.Properties().tab(ModItemGroups.item_tab), boilingPoint, meltingPoint, symbol));
            }

        }

        for(IToolPart flag : toolParts){
            if(flag == TOOL_HEAD){
                dullPickaxe = RegistryHandler.ITEMS.register("dull_" + name + "_pickaxe_head", () ->
                        dullPickaxeItem = new DullToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab)));
                pickaxeHead = RegistryHandler.ITEMS.register( name + "_pickaxe_head", () ->
                        pickaxeHeadItem = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, attackSpeed, durability, attackDamage, weight, toolTypes));
                hammerHead = RegistryHandler.ITEMS.register( name + "_hammer_head", () ->
                        hammerHeadItem = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, attackSpeed, durability, attackDamage, weight, toolTypes));
                dullChiselHead = RegistryHandler.ITEMS.register("dull_" + name + "_chisel_head", () ->
                        dullChiselHeadItem = new DullToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab)));
                chiselHead = RegistryHandler.ITEMS.register( name + "_chisel_head", () ->
                        chiselHeadItem = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, attackSpeed, durability, attackDamage, weight, toolTypes));

            }

            if(flag == TOOL_WEDGE){
                wedge = RegistryHandler.ITEMS.register( name + "_wedge", () ->
                        wedgeItem = new ToolBindingItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, durability, weight));
            }

            if(flag == TOOL_WEDGE_HANDLE){
                wedgeHandle = RegistryHandler.ITEMS.register( name + "_wedge_handle", () ->
                        wedgeHandleItem = new ToolHandleItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, durability, weight));
            }
        }
        return this;
    }

    public void registerColorForBlock() {
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