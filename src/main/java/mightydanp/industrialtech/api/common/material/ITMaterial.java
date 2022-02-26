package mightydanp.industrialtech.api.common.material;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.blocks.DenseOreBlock;
import mightydanp.industrialtech.api.common.blocks.OreBlock;
import mightydanp.industrialtech.api.common.blocks.SmallOreBlock;
import mightydanp.industrialtech.api.common.blocks.ThinSlabBlock;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.items.*;
import mightydanp.industrialtech.api.common.jsonconfig.datapack.Data.BlockModelData;
import mightydanp.industrialtech.api.common.jsonconfig.datapack.Data.BlockStateData;
import mightydanp.industrialtech.api.common.jsonconfig.datapack.Data.ItemModelData;
import mightydanp.industrialtech.api.common.jsonconfig.datapack.Data.LangData;
import mightydanp.industrialtech.api.common.jsonconfig.datapack.DataPackRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.DefaultFluidState;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.IFluidState;
import mightydanp.industrialtech.api.common.jsonconfig.icons.TextureIconRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.stonelayer.IStoneLayer;
import mightydanp.industrialtech.api.common.jsonconfig.stonelayer.StoneLayerRegistry;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.jsonconfig.flag.IMaterialFlag;
import mightydanp.industrialtech.api.common.material.fluid.ITFluid;
import mightydanp.industrialtech.api.common.material.fluid.ITFluidBlock;
import mightydanp.industrialtech.api.common.jsonconfig.icons.ITextureIcon;
import mightydanp.industrialtech.api.common.jsonconfig.ore.DefaultOreType;
import mightydanp.industrialtech.api.common.jsonconfig.ore.IOreType;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.IToolPart;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public String symbol = null;

    public Integer denseOreDensity = 8;
    public IOreType oreType = null;

    public Boolean isStoneLayer = null;
    public String stoneLayerTextureLocation = null;
    public Integer miningLevel = null;

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

    public List<Block> thinSlabList = new ArrayList<>();

    public List<Item> oreItemList = new ArrayList<>();
    public List<Item> smallOreItemList = new ArrayList<>();
    public List<Item> denseOreItemList = new ArrayList<>();

    public List<Item> thinSlabItemList = new ArrayList<>();


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

    public ITMaterial setStoneLayerProperties(Boolean isStoneLayerIn, String stoneLayerTextureLocationIn) {
        isStoneLayer = isStoneLayerIn;
        stoneLayerTextureLocation = stoneLayerTextureLocationIn;

        if(isStoneLayerIn) {
            materialFlags.add(STONE_LAYER);
        }

        return this;
    }

    public ITMaterial setBlockProperties(int miningLevelIn) {
        miningLevel = miningLevelIn;
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
        toolParts = toolPartIn;
        return this;
    }



    public ITMaterial save() {
        LangData enLang = DataPackRegistry.langDataMap.getOrDefault("en_us", new LangData());
        List<ITMaterial> stoneLayerList = MaterialRegistry.materials().values().stream().filter(i -> i.isStoneLayer != null && i.isStoneLayer).collect(Collectors.toList());


        for(IMaterialFlag flag : materialFlags){
            if(flag == ORE || flag == GEM || flag == STONE_LAYER){

                for(ITMaterial  stoneLayer : stoneLayerList){
                    String stoneLayerModId = stoneLayer.stoneLayerTextureLocation.split(":")[0].equals("minecraft") ? "" : stoneLayer.stoneLayerTextureLocation.split(":")[0];
                    String stoneLayerBlock = stoneLayer.stoneLayerTextureLocation.split(":")[1];
                    //String resourceID = stoneLayerModId.equals("resourceID") ? "" : Ref.mod_id;



                    if(flag == ORE || flag == GEM) {
                        //--//
                        Block ore = RegistryHandler.registerBlock(Ref.mod_id, stoneLayer.name + "_" + name + "_ore", new OreBlock(name + "_ore", AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE), new ResourceLocation(stoneLayerModId, stoneLayer.name)));
                        oreList.add(ore);
                        //
                        DataPackRegistry.blockStateDataMap.put(stoneLayer.name + "_" + name + "_ore", new BlockStateData().setBlockStateModelLocation("", new ResourceLocation(Ref.mod_id, "block/ore/" + stoneLayer.name + "_ore")));

                        DataPackRegistry.blockModelDataMap.put(stoneLayer.name + "_ore", new BlockModelData().setParent(new ResourceLocation(Ref.mod_id, "block/ore/state/ore"))
                                .setParentFolder("/ore").setTexturesLocation("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).setTexturesLocation("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock)));
                        //--
                        Item oreItemR = RegistryHandler.registerItem(Ref.mod_id, stoneLayer.name + "_" + name + "_ore",
                                new BlockOreItem(ore, new Item.Properties().tab(ModItemGroups.ore_tab), boilingPoint, meltingPoint, symbol));
                        oreItemList.add(oreItemR);
                        //
                        DataPackRegistry.itemModelDataHashMap.put(stoneLayer.name + "_" + name + "_ore", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "block/ore/" + stoneLayer.name + "_ore")));
                        enLang.addTranslation(stoneLayer.name + "_" + name + "_ore", LangData.translateUpperCase(stoneLayer.name + "_" + name + "_ore"));
                        //--//
                        Block smallOreBlockR = RegistryHandler.registerBlock(Ref.mod_id, "small_" + stoneLayer.name + "_" + name + "_ore",
                                new SmallOreBlock("small_" + name + "_ore", AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE), new ResourceLocation(stoneLayerModId, stoneLayer.name)));
                        smallOreList.add(smallOreBlockR);
                        //
                        DataPackRegistry.blockStateDataMap.put("small_" + stoneLayer.name + "_" + name + "_ore", new BlockStateData().setBlockStateModelLocation("", new ResourceLocation(Ref.mod_id, "block/small_ore/" + "small_" + stoneLayer.name + "_ore")));
                        DataPackRegistry.blockModelDataMap.put("small_" + stoneLayer.name + "_ore", new BlockModelData().setParent(new ResourceLocation(Ref.mod_id, "block/ore/state/small_ore"))
                                .setParentFolder("/small_ore").setTexturesLocation("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).setTexturesLocation("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock)));
                        //--
                        Item smallOreItemR = RegistryHandler.registerItem(Ref.mod_id, "small_" + stoneLayer.name + "_" + name + "_ore",
                                new BlockItem(smallOreBlockR, new Item.Properties().tab(ModItemGroups.ore_tab)));
                        smallOreItemList.add(smallOreItemR);
                        //
                        DataPackRegistry.itemModelDataHashMap.put("small_" + stoneLayer.name + "_" + name + "_ore", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "block/small_ore/" + "small_" + stoneLayer.name + "_ore")));
                        enLang.addTranslation("small_" + stoneLayer.name + "_" + name + "_ore", LangData.translateUpperCase("small_" + stoneLayer.name + "_" + name + "_ore"));
                        //--//
                        Block denseOreBlockR = RegistryHandler.registerBlock(Ref.mod_id, "dense_" + stoneLayer.name + "_" + name + "_ore",
                                new DenseOreBlock("dense_" + name + "_ore", AbstractBlock.Properties.of(net.minecraft.block.material.Material.STONE), denseOreDensity, new ResourceLocation(stoneLayerModId, stoneLayer.name)));
                        denseOreList.add(denseOreBlockR);
                        //
                        DataPackRegistry.blockStateDataMap.put("dense_" + stoneLayer.name + "_" + name + "_ore", new BlockStateData().setBlockStateModelLocation(Ref.mod_id, new ResourceLocation(Ref.mod_id, "block/dense_ore/" + "dense_" + stoneLayer.name + "_ore")));
                        DataPackRegistry.blockModelDataMap.put("dense_" + stoneLayer.name + "_ore", new BlockModelData().setParent(new ResourceLocation(Ref.mod_id, "block/ore/state/dense_ore"))
                                .setParentFolder("/dense_ore").setTexturesLocation("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).setTexturesLocation("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock)));
                        //--
                        Item denseOreItemR = RegistryHandler.registerItem(Ref.mod_id, "dense_" + stoneLayer.name + "_" + name + "_ore",
                                new BlockOreItem(denseOreBlockR, new Item.Properties().tab(ModItemGroups.ore_tab), boilingPoint, meltingPoint, symbol));
                        denseOreItemList.add(denseOreItemR);
                        //
                        DataPackRegistry.itemModelDataHashMap.put("dense_" + stoneLayer.name + "_" + name + "_ore", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "block/dense_ore/" + "dense_" + stoneLayer.name + "_ore")));
                        enLang.addTranslation("dense_" + stoneLayer.name + "_" + name + "_ore", LangData.translateUpperCase("dense_" + stoneLayer.name + "_" + name + "_ore"));
                        //--//
                    }
                }

                if(flag == ORE || flag == GEM){
                    //--//
                    crushedOre = RegistryHandler.registerItem(Ref.mod_id,  "crushed_" + name + "_ore", new OreProductsItem(new Item.Properties()
                            .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                    DataPackRegistry.itemModelDataHashMap.put("crushed_" + name + "_ore", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/crushed_ore")));
                    DataPackRegistry.itemModelDataHashMap.put(name + ":crushed_ore", new ItemModelData().setParentFolder("/material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id,"item/material_icons/" +  textureIcon.getSecond().getName().toLowerCase() + "/crushed_ore"))
                            .setTexturesLocation("layer1",  new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/crushed_ore_overlay")));
                    enLang.addTranslation("crushed_" + name + "_ore", LangData.translateUpperCase("crushed_" + name + "_ore"));
                    //--//
                    purifiedOre = RegistryHandler.registerItem(Ref.mod_id,  "purified_" + name + "_ore", new OreProductsItem(new Item.Properties()
                            .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                    DataPackRegistry.itemModelDataHashMap.put("purified_" + name + "_ore", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/purified_ore")));
                    DataPackRegistry.itemModelDataHashMap.put(name + ":purified_ore", new ItemModelData().setParentFolder("/material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/purified_ore"))
                            .setTexturesLocation("layer1",  new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/purified_ore_overlay")));
                    enLang.addTranslation("purified_" + name + "_ore", LangData.translateUpperCase("purified_" + name + "_ore"));
                    //--//
                    centrifugedOre = RegistryHandler.registerItem(Ref.mod_id,  "centrifuged_" + name + "_ore", new OreProductsItem(new Item.Properties()
                            .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                    DataPackRegistry.itemModelDataHashMap.put("centrifuged_" + name + "_ore", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/centrifuged_ore")));
                    DataPackRegistry.itemModelDataHashMap.put(name + ":centrifuged_ore", new ItemModelData().setParentFolder("/material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/centrifuged_ore"))
                            .setTexturesLocation("layer1",  new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/centrifuged_ore_overlay")));
                    enLang.addTranslation("centrifuged_" + name + "_ore", LangData.translateUpperCase("centrifuged_" + name + "_ore"));
                    //--//
                }

                if(flag == GEM){
                    //--//
                    gem = RegistryHandler.registerItem(Ref.mod_id,  name + "_gem", new GemItem(new Item.Properties()
                            .tab(ModItemGroups.gem_tab), symbol));
                    DataPackRegistry.itemModelDataHashMap.put(name + "_gem", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/gem")));
                    DataPackRegistry.itemModelDataHashMap.put(name + ":gem", new ItemModelData().setParentFolder("/material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/gem"))
                            .setTexturesLocation("layer1",  new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/gem_overlay")));
                    enLang.addTranslation(name + "_gem", LangData.translateUpperCase(name + "_gem"));
                    //--//
                    chippedGem = RegistryHandler.registerItem(Ref.mod_id,  "chipped_" + name + "_gem", new GemItem(new Item.Properties()
                            .tab(ModItemGroups.gem_tab), symbol));
                    DataPackRegistry.itemModelDataHashMap.put("chipped_" + name + "_gem", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/chipped_gem")));
                    DataPackRegistry.itemModelDataHashMap.put(name + ":chipped_gem", new ItemModelData().setParentFolder("/material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/chipped_gem"))
                            .setTexturesLocation("layer1",  new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/chipped_gem_overlay")));
                    enLang.addTranslation("chipped_" + name + "_gem", LangData.translateUpperCase("chipped_" + name + "_gem"));
                    //--//
                    flawedGem = RegistryHandler.registerItem(Ref.mod_id,  "flawed_" + name + "_gem", new GemItem(new Item.Properties()
                            .tab(ModItemGroups.gem_tab), symbol));
                    DataPackRegistry.itemModelDataHashMap.put("flawed_" + name + "_gem", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/flawed_gem")));
                    DataPackRegistry.itemModelDataHashMap.put(name + ":flawed_gem", new ItemModelData().setParentFolder("/material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/flawed_gem"))
                            .setTexturesLocation("layer1",  new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/flawed_gem_overlay")));
                    enLang.addTranslation("flawed_" + name + "_gem", LangData.translateUpperCase("flawed_" + name + "_gem"));
                    //--//
                    flawlessGem = RegistryHandler.registerItem(Ref.mod_id,  "flawless_" + name + "_gem", new GemItem(new Item.Properties()
                            .tab(ModItemGroups.gem_tab), symbol));
                    DataPackRegistry.itemModelDataHashMap.put("flawless_" + name + "_gem", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/flawless_gem")));
                    DataPackRegistry.itemModelDataHashMap.put(name + ":flawless_gem", new ItemModelData().setParentFolder("/material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/flawless_gem"))
                            .setTexturesLocation("layer1",  new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/flawless_gem_overlay")));
                    enLang.addTranslation("flawless_" + name + "_gem", LangData.translateUpperCase("flawless_" + name + "_gem"));
                    //--//
                    legendaryGem = RegistryHandler.registerItem(Ref.mod_id,  "legendary_" + name + "_gem", new GemItem(new Item.Properties()
                            .tab(ModItemGroups.gem_tab), symbol));
                    DataPackRegistry.itemModelDataHashMap.put("legendary_" + name + "_gem", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/legendary_gem")));
                    DataPackRegistry.itemModelDataHashMap.put(name + ":legendary_gem", new ItemModelData().setParentFolder("/material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/legendary_gem"))
                            .setTexturesLocation("layer1",  new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/legendary_gem_overlay")));
                    enLang.addTranslation("legendary_" + name + "_gem", LangData.translateUpperCase("legendary_" + name + "_gem"));
                    //--//
                }

                //--//
                dust = RegistryHandler.registerItem(Ref.mod_id,  "" + name + "_dust",  new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                DataPackRegistry.itemModelDataHashMap.put(name + "_dust", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/dust")));
                DataPackRegistry.itemModelDataHashMap.put(name + ":dust", new ItemModelData().setParentFolder("/material_icons/" + textureIcon.getSecond().getName()).setParent(new ResourceLocation("item/generated"))
                        .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName() + "/dust"))
                        .setTexturesLocation("layer1",  new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName() + "/dust_overlay")));
                enLang.addTranslation(name + "_dust", LangData.translateUpperCase(name + "_dust"));
                //--//
                smallDust = RegistryHandler.registerItem(Ref.mod_id,  "small_" + name + "_dust", new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                DataPackRegistry.itemModelDataHashMap.put("small_" + name + "_dust", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/small_dust")));
                DataPackRegistry.itemModelDataHashMap.put(name + ":small_dust", new ItemModelData().setParentFolder("/material_icons/" + textureIcon.getSecond().getName()).setParent(new ResourceLocation("item/generated"))
                        .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName() + "/small_dust"))
                        .setTexturesLocation("layer1",  new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName() + "/small_dust_overlay")));
                enLang.addTranslation("small_" + name + "_dust", LangData.translateUpperCase("small_" + name + "_dust"));
                //--//
                tinyDust = RegistryHandler.registerItem(Ref.mod_id,  "tiny_" + name + "_dust", new OreProductsItem(new Item.Properties()
                        .tab(ModItemGroups.ore_products_tab), boilingPoint, meltingPoint, symbol));
                DataPackRegistry.itemModelDataHashMap.put("tiny_" + name + "_dust", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/tiny_dust")));
                DataPackRegistry.itemModelDataHashMap.put(name + ":tiny_dust", new ItemModelData().setParentFolder("/material_icons/" + textureIcon.getSecond().getName()).setParent(new ResourceLocation("item/generated"))
                        .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName() + "/tiny_dust"))
                        .setTexturesLocation("layer1",  new ResourceLocation(Ref.mod_id,"item/material_icons/" + textureIcon.getSecond().getName() + "/tiny_dust_overlay")));
                enLang.addTranslation("tiny_" + name + "_dust", LangData.translateUpperCase("tiny_" + name + "_dust"));
                //--//
            }

            if(flag == STONE_LAYER){
                String stoneLayerModId = stoneLayerTextureLocation.split(":")[0].equals("minecraft") ? "" : stoneLayerTextureLocation.split(":")[0];

                String stoneLayerBlock = stoneLayerTextureLocation.split(":")[1];
                //String resourceID = useMinecraftResource ? "" : Ref.mod_id;

                DataPackRegistry.blockModelDataMap.put(name + "_ore", new BlockModelData().setParent(new ResourceLocation(Ref.mod_id, "block/ore"))
                        .setParentFolder("/ore").setTexturesLocation("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).setTexturesLocation("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock)));
                DataPackRegistry.blockModelDataMap.put("small_" + name + "_ore", new BlockModelData().setParent(new ResourceLocation(Ref.mod_id, "block/small_ore"))
                        .setParentFolder("/small_ore").setTexturesLocation("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).setTexturesLocation("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock)));
                DataPackRegistry.blockModelDataMap.put("dense_" + name + "_ore", new BlockModelData().setParent(new ResourceLocation(Ref.mod_id, "block/dense_ore"))
                        .setParentFolder("/small_ore").setTexturesLocation("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).setTexturesLocation("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock)));


                //--//
                Block thin_slab_block = RegistryHandler.registerBlock(Ref.mod_id,"thin_" + name + "_slab", new ThinSlabBlock(AbstractBlock.Properties.of(Material.STONE), new ResourceLocation(stoneLayerModId, stoneLayerBlock)));
                thinSlabList.add(thin_slab_block);
                //
                DataPackRegistry.blockStateDataMap.put("thin_" + name + "_slab", new BlockStateData().setBlockStateModelLocation("", new ResourceLocation(Ref.mod_id, "block/stone_layer/thin_slab/" + "thin_" + name + "_slab")));
                DataPackRegistry.blockModelDataMap.put("thin_" + name + "_slab", new BlockModelData().setParent(new ResourceLocation(Ref.mod_id, "block/stone_layer/thin_slab"))
                        .setParentFolder("/stone_layer").setTexturesLocation("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).setTexturesLocation("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock))
                        .setTexturesLocation("texture", new ResourceLocation(stoneLayerModId, stoneLayerBlock)));
                //--
                Item thin_slab_item_block = RegistryHandler.registerItem(Ref.mod_id,"thin_" + name + "_slab", new ThinSlabItemBlock(thin_slab_block, new Item.Properties().stacksTo(1)));
                thinSlabItemList.add(thin_slab_item_block);
                //
                DataPackRegistry.itemModelDataHashMap.put("thin_" + name + "_slab", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "block/stone_layer/thin_slab/thin_" + name + "_slab")));
                enLang.addTranslation("thin_" + name + "_slab", LangData.translateUpperCase("thin_" + name + "_slab"));
                //--//
                        /*
                        Block leg_block = RegistryHandler.registerBlock(Ref.mod_id,name + "_leg", new LegBlock(AbstractBlock.Properties.of(Material.STONE), new ResourceLocation("textures/" + stoneLayer.getBlock())));
                        Item leg_item_block = RegistryHandler.registerItem(Ref.mod_id, name + "_leg", new LegItemBlock(leg_block, new Item.Properties().stacksTo(1)));
                         */
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
                //--//
                ingot = RegistryHandler.registerItem(Ref.mod_id, name + "_" + INGOT.name(),  new IngotItem(new Item.Properties().tab(ModItemGroups.item_tab), boilingPoint, meltingPoint, symbol));
                DataPackRegistry.itemModelDataHashMap.put(name + "_" + INGOT.name(), new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "/material_icons/" + textureIcon.getSecond().getName() + "/ingot")));
                enLang.addTranslation(name + "_" + INGOT.name(), LangData.translateUpperCase(name + "_" + INGOT.name()));
                //--//
            }

        }

        for(IToolPart flag : toolParts){
            if(flag == TOOL_HEAD){
                //--//
                dullPickaxe = RegistryHandler.registerItem(Ref.mod_id, "dull_" + name + "_pickaxe_head",
                        dullPickaxe = new DullToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab)));
                DataPackRegistry.itemModelDataHashMap.put("dull_" + name + "_pickaxe_head", new ItemModelData().setParent(new ResourceLocation("item/generated")).setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "/material_icons/" + textureIcon.getSecond().getName() + "dull_pickaxe_head")));
                enLang.addTranslation("dull_" + name + "_pickaxe_head", LangData.translateUpperCase("dull_" + name + "_pickaxe_head"));
                //--//
                pickaxeHead = RegistryHandler.registerItem(Ref.mod_id,  name + "_pickaxe_head", 
                        pickaxeHead = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, attackSpeed, durability, attackDamage, weight, toolTypes));
                DataPackRegistry.itemModelDataHashMap.put(name + "_pickaxe_head", new ItemModelData().setParent(new ResourceLocation("item/generated")).setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "/material_icons/" + textureIcon.getSecond().getName() + "pickaxe_head")));
                enLang.addTranslation(name + "_pickaxe_head", LangData.translateUpperCase(name + "_pickaxe_head"));
                //--//
                hammerHead = RegistryHandler.registerItem(Ref.mod_id,  name + "_hammer_head", 
                        hammerHead = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, attackSpeed, durability, attackDamage, weight, toolTypes));
                DataPackRegistry.itemModelDataHashMap.put(name + "_hammer_head", new ItemModelData().setParent(new ResourceLocation("item/generated")).setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "/material_icons/" + textureIcon.getSecond().getName() + "hammer_head")));
                enLang.addTranslation(name + "_hammer_head", LangData.translateUpperCase(name + "_hammer_head"));
                //--//
                dullChiselHead = RegistryHandler.registerItem(Ref.mod_id, "dull_" + name + "_chisel_head",
                        dullChiselHead = new DullToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab)));
                DataPackRegistry.itemModelDataHashMap.put("dull_" + name + "_chisel_head", new ItemModelData().setParent(new ResourceLocation("item/generated")).setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "/material_icons/" + textureIcon.getSecond().getName() + "dull_chisel_head")));
                enLang.addTranslation("dull_" + name + "_chisel_head", LangData.translateUpperCase("dull_" + name + "_chisel_head"));
                //--//
                chiselHead = RegistryHandler.registerItem(Ref.mod_id,  name + "_chisel_head", 
                        chiselHead = new ToolHeadItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, attackSpeed, durability, attackDamage, weight, toolTypes));
                DataPackRegistry.itemModelDataHashMap.put(name + "_chisel_head", new ItemModelData().setParent(new ResourceLocation("item/generated")).setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "/material_icons/" + textureIcon.getSecond().getName() + "chisel_head")));
                enLang.addTranslation(name + "_chisel_head", LangData.translateUpperCase(name + "_chisel_head"));
                //--//
            }

            if(flag == TOOL_WEDGE){
                //--//
                wedge = RegistryHandler.registerItem(Ref.mod_id,  name + "_wedge", 
                        wedge = new ToolBindingItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, durability, weight));
                DataPackRegistry.itemModelDataHashMap.put(name + "_wedge", new ItemModelData().setParent(new ResourceLocation("item/generated")).setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "/material_icons/" + textureIcon.getSecond().getName() + "wedge")));
                enLang.addTranslation(name + "_wedge", LangData.translateUpperCase(name + "_wedge"));
                //--//
            }

            if(flag == TOOL_WEDGE_HANDLE){
                //--//
                wedgeHandle = RegistryHandler.registerItem(Ref.mod_id,  name + "_wedge_handle", 
                        wedgeHandle = new ToolHandleItem(new Item.Properties().tab(ModItemGroups.tool_parts_tab), name, symbol, color, textureIcon, boilingPoint, meltingPoint, durability, weight));
                DataPackRegistry.itemModelDataHashMap.put(name + "_wedge_handle", new ItemModelData().setParent(new ResourceLocation("item/generated")).setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "/material_icons/" + textureIcon.getSecond().getName() + "wedge_handle")));
                enLang.addTranslation(name + "_wedge_handle", LangData.translateUpperCase(name + "_wedge_handle"));
                //--//
            }
        }
        
        return this;
    }

    public void clientRenderLayerInit(){
        for(Block block : thinSlabList) {
            RenderTypeLookup.setRenderLayer(block, RenderType.cutout());
        }
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