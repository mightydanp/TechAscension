package mightydanp.techcore.common.material;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.blocks.*;
import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.items.*;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.fluidstate.DefaultFluidState;
import mightydanp.techcore.common.jsonconfig.fluidstate.IFluidState;
import mightydanp.techcore.common.jsonconfig.icons.ITextureIcon;
import mightydanp.techcore.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.techcore.common.jsonconfig.material.ore.DefaultOreType;
import mightydanp.techcore.common.jsonconfig.material.ore.IOreType;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.material.fluid.TCFluid;
import mightydanp.techcore.common.material.fluid.TCFluidBlock;
import mightydanp.techapi.common.resources.asset.AssetPackRegistry;
import mightydanp.techapi.common.resources.asset.data.BlockModelData;
import mightydanp.techapi.common.resources.asset.data.BlockStateData;
import mightydanp.techapi.common.resources.asset.data.ItemModelData;
import mightydanp.techapi.common.resources.asset.data.LangData;
import mightydanp.techapi.common.resources.data.DataPackRegistry;
import mightydanp.techcore.common.holder.MCMaterialHolder;
import mightydanp.techcore.common.jsonconfig.flag.IMaterialFlag;
import mightydanp.techapi.common.resources.data.data.LootTableData;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.item.Item;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

import static mightydanp.techcore.common.jsonconfig.flag.DefaultMaterialFlag.*;
import static mightydanp.techcore.common.jsonconfig.flag.DefaultMaterialFlag.INGOT;

/**
 * Created by MightyDanp on 12/1/2021.
 */
public class TCMaterial extends net.minecraftforge.registries.ForgeRegistryEntry<TCMaterial> {
    public static Map<String, IMaterial> extraSave = new HashMap<>();
    public static Map<String, IMaterialResource> extraSaveResources = new HashMap<>();

    public Map<String, RegistryObject<Block>> extraSaveBlocks = new HashMap<>();
    public Map<String, RegistryObject<Item>> extraSaveItems = new HashMap<>();

    public final String name;
    public final int color;

    public Pair<String, ITextureIcon> textureIcon;

    public String symbol = null;

    public Integer denseOreDensity = 8;
    public IOreType oreType = null;

    public Boolean isStoneLayer = null;
    public String stoneLayerBlock = null;
    public String stoneLayerTextureLocation = null;

    public Integer harvestLevel = null;

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
    public Integer toolLevel = null;
    public Float efficiency = null;
    public List<Pair<String, String>> toolPartWhiteList = new ArrayList<>();
    public List<Pair<String, String>> toolPartBlackList = new ArrayList<>();

    public List<IMaterialFlag> materialFlags = new ArrayList<>();
    public List<RegistryObject<Block>> oreList = new ArrayList<>();
    public List<RegistryObject<Block>> smallOreList = new ArrayList<>();
    public List<RegistryObject<Block>> denseOreList = new ArrayList<>();

    public List<RegistryObject<Block>> thinSlabList = new ArrayList<>();

    public List<RegistryObject<Item>> oreItemList = new ArrayList<>();
    public List<RegistryObject<Item>> smallOreItemList = new ArrayList<>();
    public List<RegistryObject<Item>> denseOreItemList = new ArrayList<>();

    public List<RegistryObject<Item>> thinSlabItemList = new ArrayList<>();


    public RegistryObject<Item> ingot, gem, chippedGem, flawedGem, flawlessGem, legendaryGem, rawOre, crushedOre, purifiedOre, centrifugedOre, dust, smallDust, tinyDust;
    public RegistryObject<FlowingFluid> fluid, fluid_flowing;
    public RegistryObject<Block> fluidBlock;

    public RegistryObject<Block> layerBlock, rockBlock, thinSlabBlock;

    public RegistryObject<Item> layerItemBlock, rockItemBlock, thinSlabItemBlock;


    public TCMaterial(String materialNameIn, int colorIn, Pair<String, ITextureIcon> textureIconLocationIn) {
        name = materialNameIn;
        color = colorIn;
        textureIcon = textureIconLocationIn;
    }

    public TCMaterial setElementalLocalization(String elementIn) {
        symbol = elementIn;
        return this;
    }

    public TCMaterial setTemperatureProperties(int meltingPointIn, int boilingPointIn) {
        meltingPoint = meltingPointIn;
        boilingPoint = boilingPointIn;
        return this;
    }

    public TCMaterial setStoneLayerProperties(Boolean isStoneLayerIn, String stoneLayerBlockIn, String stoneLayerTextureLocationIn) {
        isStoneLayer = isStoneLayerIn;
        stoneLayerBlock = stoneLayerBlockIn;
        stoneLayerTextureLocation = stoneLayerTextureLocationIn;

        if (isStoneLayerIn) {
            materialFlags.add(STONE_LAYER);
        }

        return this;
    }

    public TCMaterial setBlockProperties(int miningLevelIn) {
        harvestLevel = miningLevelIn;
        return this;
    }

    public TCMaterial setOreType(IOreType oreTypeIn) {
        oreType = oreTypeIn;

        if (oreTypeIn == DefaultOreType.ORE) {
            materialFlags.add(ORE);
        }

        if (oreTypeIn == DefaultOreType.GEM) {
            materialFlags.add(GEM);
        }

        if (oreTypeIn == DefaultOreType.CRYSTAL) {

        }
        return this;
    }

    public TCMaterial setDenseOreDensity(int densityIn) {
        denseOreDensity = densityIn;
        return this;
    }

    public TCMaterial setFluidProperties(IFluidState stateIn, float accelerationIn, Integer densityIn, Integer luminosityIn, Integer viscosityIn) {
        fluidState = stateIn;
        fluidAcceleration = accelerationIn;
        if (densityIn != null) fluidDensity = densityIn;
        if (luminosityIn != null) fluidLuminosity = luminosityIn;
        if (viscosityIn != null) fluidViscosity = viscosityIn;

        if (stateIn == DefaultFluidState.FLUID) {
            materialFlags.add(FLUID);
        }

        if (stateIn == DefaultFluidState.GAS) {
            materialFlags.add(GAS);
        }

        return this;
    }

    public TCMaterial setToolProperties(int attackSpeedIn, int durabilityIn, float attackDamageIn, float weightIn, float efficiencyIn, Integer toolLevelIn, List<Pair<String, String>> toolPartWhiteListIn, List<Pair<String, String>> toolPartBlackListIn) {
        attackSpeed = attackSpeedIn;
        durability = durabilityIn;
        attackDamage = attackDamageIn;
        weight = weightIn;
        efficiency = efficiencyIn;
        toolLevel = toolLevelIn;
        toolPartWhiteList = toolPartWhiteListIn;
        toolPartBlackList = toolPartBlackListIn;
        materialFlags.add(TOOL);
        return this;
    }


    public TCMaterial save() {
        List<TCMaterial> stoneLayerList = ((MaterialRegistry) TCJsonConfigs.material.getFirst()).getAllValues().stream().filter(i -> i.isStoneLayer != null && i.isStoneLayer).toList();
        //--
        for (IMaterialFlag flag : materialFlags) {
            if (flag == ORE || flag == GEM || flag == STONE_LAYER) {
                if (flag == STONE_LAYER) {
                    String stoneLayerBlockName = stoneLayerBlock.equals("") ? String.valueOf(layerBlock.get().getRegistryName()) : stoneLayerBlock;
                    String stoneLayerModId = stoneLayerTextureLocation.split(":")[0].equals("minecraft") ? "" : stoneLayerTextureLocation.split(":")[0];

                    String stoneLayerBlock = stoneLayerTextureLocation.split(":")[1];
                    //String resourceID = useMinecraftResource ? "" : Ref.mod_id;
//--//--//--//--//--//--//--//--//
                    rockBlock = RegistryHandler.BLOCKS.register(name + "_rock", () -> new RockBlock(stoneLayerBlockName, BlockBehaviour.Properties.of(MCMaterialHolder.rock)));
                    //--
                    rockItemBlock = RegistryHandler.ITEMS.register(name + "_rock", () -> new RockBlockItem(rockBlock.get(), stoneLayerBlockName, new Item.Properties().stacksTo(64).tab(TCCreativeModeTab.stone_layer_tab)));

//--//--//--//--//--//--//--//--//
                    thinSlabBlock = RegistryHandler.BLOCKS.register("thin_" + name + "_slab", () -> new ThinSlabBlock(BlockBehaviour.Properties.of(MCMaterialHolder.rock), stoneLayerBlockName));
                    //--
                    thinSlabItemBlock = RegistryHandler.ITEMS.register("thin_" + name + "_slab", () -> new ThinSlabItemBlock(thinSlabBlock.get(), new Item.Properties().stacksTo(1).tab(TCCreativeModeTab.stone_layer_tab)));
//--//--//--//--//--//--//--//--//
                        /*
                        Block leg_block = RegistryHandler.BLOCKS.register(name + "_leg", new LegBlock(AbstractBlock.Properties.of(Material.STONE), new ResourceLocation("textures/" + stoneLayer.getBlock())));
                        Item leg_item_block = RegistryHandler.ITEMS.register( name + "_leg", new LegItemBlock(leg_block, new Item.Properties().stacksTo(1)));
                         */
                }

                //-- Item --\\

                if (flag == ORE || flag == GEM) {
//--//--//--//--//--//--//--//--//
                    rawOre = RegistryHandler.ITEMS.register("raw_" + name + "_ore", () -> new OreProductsItem(new Item.Properties()
                            .tab(TCCreativeModeTab.ore_products_tab), boilingPoint, meltingPoint, symbol));
//--//--//--//--//--//--//--//--//
                    crushedOre = RegistryHandler.ITEMS.register("crushed_" + name + "_ore", () -> new OreProductsItem(new Item.Properties()
                            .tab(TCCreativeModeTab.ore_products_tab), boilingPoint, meltingPoint, symbol));
//--//--//--//--//--//--//--//--//
                    purifiedOre = RegistryHandler.ITEMS.register("purified_" + name + "_ore", () -> new OreProductsItem(new Item.Properties()
                            .tab(TCCreativeModeTab.ore_products_tab), boilingPoint, meltingPoint, symbol));
//--//--//--//--//--//--//--//--//
                    centrifugedOre = RegistryHandler.ITEMS.register("centrifuged_" + name + "_ore", () -> new OreProductsItem(new Item.Properties()
                            .tab(TCCreativeModeTab.ore_products_tab), boilingPoint, meltingPoint, symbol));
//--//--//--//--//--//--//--//--//
                }

                if (flag == GEM) {
//--//--//--//--//--//--//--//--//
                    gem = RegistryHandler.ITEMS.register(name + "_gem", () -> new GemItem(new Item.Properties()
                            .tab(TCCreativeModeTab.gem_tab), symbol));
//--//--//--//--//--//--//--//--//
                    chippedGem = RegistryHandler.ITEMS.register("chipped_" + name + "_gem", () -> new GemItem(new Item.Properties()
                            .tab(TCCreativeModeTab.gem_tab), symbol));
//--//--//--//--//--//--//--//--//
                    flawedGem = RegistryHandler.ITEMS.register("flawed_" + name + "_gem", () -> new GemItem(new Item.Properties()
                            .tab(TCCreativeModeTab.gem_tab), symbol));
//--//--//--//--//--//--//--//--//
                    flawlessGem = RegistryHandler.ITEMS.register("flawless_" + name + "_gem", () -> new GemItem(new Item.Properties()
                            .tab(TCCreativeModeTab.gem_tab), symbol));
//--//--//--//--//--//--//--//--//
                    legendaryGem = RegistryHandler.ITEMS.register("legendary_" + name + "_gem", () -> new GemItem(new Item.Properties()
                            .tab(TCCreativeModeTab.gem_tab), symbol));
//--//--//--//--//--//--//--//--//
                }

//--//--//--//--//--//--//--//--//
                dust = RegistryHandler.ITEMS.register("" + name + "_dust", () -> new OreProductsItem(new Item.Properties()
                        .tab(TCCreativeModeTab.ore_products_tab), boilingPoint, meltingPoint, symbol));
//--//--//--//--//--//--//--//--//
                smallDust = RegistryHandler.ITEMS.register("small_" + name + "_dust", () -> new OreProductsItem(new Item.Properties()
                        .tab(TCCreativeModeTab.ore_products_tab), boilingPoint, meltingPoint, symbol));
//--//--//--//--//--//--//--//--//
                tinyDust = RegistryHandler.ITEMS.register("tiny_" + name + "_dust", () -> new OreProductsItem(new Item.Properties()
                        .tab(TCCreativeModeTab.ore_products_tab), boilingPoint, meltingPoint, symbol));
//--//--//--//--//--//--//--//--//

                //-- Blocks with Items -- \\
                for (TCMaterial stoneLayer : stoneLayerList) {
                    String stoneLayerModId = stoneLayer.stoneLayerTextureLocation.split(":")[0].equals("minecraft") ? "" : stoneLayer.stoneLayerTextureLocation.split(":")[0];
                    String stoneLayerBlock = stoneLayer.stoneLayerTextureLocation.split(":")[1];
                    //String resourceID = stoneLayerModId.equals("resourceID") ? "" : Ref.mod_id;

                    if (flag == ORE || flag == GEM) {
                        String stoneLayerBlockName = stoneLayer.stoneLayerBlock.equals("") ? String.valueOf(layerBlock.get().getRegistryName()) : stoneLayer.stoneLayerBlock;
//--//--//--//--//--//--//--//--//
                        RegistryObject<Block> ore = RegistryHandler.BLOCKS.register(stoneLayer.name + "_" + name + "_ore", () -> new OreBlock(name + "_ore", BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE), stoneLayerBlockName));
                        oreList.add(ore);
                        //--
                        RegistryObject<Item> oreItemR = RegistryHandler.ITEMS.register(stoneLayer.name + "_" + name + "_ore", () ->
                                new BlockOreItem(ore, new Item.Properties().tab(TCCreativeModeTab.ore_tab)
                                        , boilingPoint, meltingPoint, symbol));
                        oreItemList.add(oreItemR);
//--//--//--//--//--//--//--//--//
                        RegistryObject<Block> smallOreBlock = RegistryHandler.BLOCKS.register("small_" + stoneLayer.name + "_" + name + "_ore", () ->
                                new SmallOreBlock("small_" + name + "_ore", BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE), stoneLayerBlockName));
                        smallOreList.add(smallOreBlock);
                        //--
                        RegistryObject<Item> smallOreItemR = RegistryHandler.ITEMS.register("small_" + stoneLayer.name + "_" + name + "_ore", () ->
                                new BlockOreItem(smallOreBlock, new Item.Properties().tab(TCCreativeModeTab.ore_tab), boilingPoint, meltingPoint, symbol));
                        smallOreItemList.add(smallOreItemR);
//--//--//--//--//--//--//--//--//
                        RegistryObject<Block> denseOreBlock = RegistryHandler.BLOCKS.register("dense_" + stoneLayer.name + "_" + name + "_ore", () ->
                                new DenseOreBlock("dense_" + name + "_ore", BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE), denseOreDensity, stoneLayerBlockName));
                        denseOreList.add(denseOreBlock);
                        //--
                        RegistryObject<Item> denseOreItemR = RegistryHandler.ITEMS.register("dense_" + stoneLayer.name + "_" + name + "_ore", () ->
                                new BlockOreItem(denseOreBlock, new Item.Properties().tab(TCCreativeModeTab.ore_tab), boilingPoint, meltingPoint, symbol));
                        denseOreItemList.add(denseOreItemR);
//--//--//--//--//--//--//--//--//
                    }
                }
            }

            if (flag == FLUID || flag == GAS) {
                FluidAttributes.Builder attributes;

                if (flag == FLUID) {
                    attributes = FluidAttributes.builder(new ResourceLocation("fluid/" + name), new ResourceLocation("fluid/" + name + "_flowing")).temperature(meltingPoint).color(color);
                    if (fluidDensity != null) attributes.density(fluidDensity);
                    if (fluidLuminosity != null) attributes.luminosity(fluidLuminosity);
                    if (fluidViscosity != null) attributes.viscosity(fluidViscosity);
                    ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> fluid.get(), () -> fluid_flowing.get(), attributes);
                    fluid = RegistryHandler.FLUIDS.register(name + "_still", () -> new TCFluid(properties, true, color));
                    fluid_flowing = RegistryHandler.FLUIDS.register(name + "_flowing", () -> new TCFluid(properties, false, color));

                    fluidBlock = RegistryHandler.BLOCKS.register(name, () -> new TCFluidBlock(() -> fluid.get(), fluidAcceleration, color));
                }

                if (flag == GAS) {
                    attributes = FluidAttributes.builder(new ResourceLocation("fluid/" + name), new ResourceLocation("fluid/" + name)).temperature(boilingPoint).color(color).gaseous();
                    if (fluidDensity != null) attributes.density(fluidDensity);
                    if (fluidLuminosity != null) attributes.luminosity(fluidLuminosity);
                    if (fluidViscosity != null) attributes.viscosity(fluidViscosity);
                    ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> fluid.get(), () -> fluid_flowing.get(), attributes);
                    fluid = RegistryHandler.FLUIDS.register(name + "_still", () -> new TCFluid(properties, true, color));
                    fluid_flowing = RegistryHandler.FLUIDS.register(name + "_flowing", () -> new TCFluid(properties, false, color));

                    fluidBlock = RegistryHandler.BLOCKS.register(name, () -> new TCFluidBlock(() -> fluid.get(), fluidAcceleration, color));
                }
            }

            if (flag == INGOT) {
//--//--//--//--//--//--//--//--//
                ingot = RegistryHandler.ITEMS.register(name + "_" + INGOT.name(), () -> new IngotItem(new Item.Properties().tab(TCCreativeModeTab.item_tab), boilingPoint, meltingPoint, symbol));
//--//--//--//--//--//--//--//--//
            }

            extraSave.forEach((string, iMaterial) -> {
                try {
                    iMaterial.save(this);
                } catch (InstantiationException | NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        }

        return this;
    }

    public void saveResources() {
        LangData enLang = AssetPackRegistry.langDataMap.getOrDefault("en_us", new LangData());
        List<TCMaterial> stoneLayerList = ((MaterialRegistry) TCJsonConfigs.material.getFirst()).getAllValues().stream().filter(i -> i.isStoneLayer != null && i.isStoneLayer).toList();
        //--

        for (IMaterialFlag flag : materialFlags) {
            if (flag == ORE || flag == GEM || flag == STONE_LAYER) {
                if (flag == STONE_LAYER) {
                    Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).getTag(BlockTags.NEEDS_STONE_TOOL).stream().forEach(block ->
                            DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "tool_level/" + 1)).add(block)));
                    Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).getTag(BlockTags.NEEDS_STONE_TOOL).stream().forEach(block ->
                            DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "tool_level/" + 1)).add(block)));


                    String stoneLayerBlockName = stoneLayerBlock.equals("") ? String.valueOf(layerBlock.get().getRegistryName()) : stoneLayerBlock;
                    String stoneLayerModId = stoneLayerTextureLocation.split(":")[0].equals("minecraft") ? "" : stoneLayerTextureLocation.split(":")[0];

                    String stoneLayerBlock = stoneLayerTextureLocation.split(":")[1];
                    //String resourceID = useMinecraftResource ? "" : Ref.mod_id;
                    AssetPackRegistry.blockModelDataMap.put(name + "_ore", new BlockModelData().setParent(new ResourceLocation(Ref.mod_id, "block/ore/state/ore"))
                            .setParentFolder("ore").setTexturesLocation("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).setTexturesLocation("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock)));
                    AssetPackRegistry.blockModelDataMap.put("small_" + name + "_ore", new BlockModelData().setParent(new ResourceLocation(Ref.mod_id, "block/ore/state/small_ore"))
                            .setParentFolder("small_ore").setTexturesLocation("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).setTexturesLocation("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock)));
                    AssetPackRegistry.blockModelDataMap.put("dense_" + name + "_ore", new BlockModelData().setParent(new ResourceLocation(Ref.mod_id, "block/ore/state/dense_ore"))
                            .setParentFolder("dense_ore").setTexturesLocation("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).setTexturesLocation("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock)));
                    AssetPackRegistry.blockModelDataMap.put("dense_" + name + "_ore", new BlockModelData().setParent(new ResourceLocation(Ref.mod_id, "block/ore/state/dense_ore"))
                            .setParentFolder("dense_ore").setTexturesLocation("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).setTexturesLocation("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock)));
//--//--//--//--//--//--//--//--//

                    //--Block--\\
                    //--Resources
                    AssetPackRegistry.blockStateDataMap.put(name + "_rock", new BlockStateData().setBlockStateModelLocation("", new ResourceLocation(Ref.mod_id, "block/stone_layer/rock/" + name + "_rock")));
                    AssetPackRegistry.blockModelDataMap.put(name + "_rock", new BlockModelData().setParent(new ResourceLocation(Ref.mod_id, "block/stone_layer/state/rock"))
                                    .setParentFolder("stone_layer/rock").setTexturesLocation("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock))
                            //.setTexturesLocation("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock))
                            //.setTexturesLocation("texture", new ResourceLocation(stoneLayerModId, stoneLayerBlock))
                    );
                    enLang.addTranslation("block." + Ref.mod_id + "." + name + "_rock", LangData.translateUpperCase(name + "_rock"));
                    //--Tags
                    DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "rocks/" + name)).add(rockBlock.get()));
                    DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "rocks")).add(rockBlock.get()));
                    DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("minecraft", "mineable/pickaxe")).add(rockBlock.get()));
                    if (harvestLevel != null) {
                        DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "harvest_level/" + harvestLevel)).add(rockBlock.get()));
                    }
                    //--LootTables
                    DataPackRegistry.saveBlockLootTableDataMap(DataPackRegistry.getBlockLootTableData(new ResourceLocation(Ref.mod_id, name + "_rock")).setLootTable(
                            LootTable.lootTable().withPool(
                                    LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.AIR).setWeight(100))
                                            .setBonusRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.FLINT).setWeight(10))).build()));

                    //--Item--\\
                    //--Resources
                    AssetPackRegistry.itemModelDataHashMap.put(name + "_rock", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/rock")));
                    AssetPackRegistry.itemModelDataHashMap.put(name + ":rock", new ItemModelData().setParentFolder("material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/rock"))
                            .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/rock_overlay"))
                    );
                    enLang.addTranslation("item." + Ref.mod_id + "." + name + "_rock", LangData.translateUpperCase(name + "_rock"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "rocks/" + name)).add(rockItemBlock.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "rocks")).add(rockItemBlock.get()));
                    //--LootTable

//--//--//--//--//--//--//--//--//

                    //--Block--\\
                    //--Resources
                    AssetPackRegistry.blockStateDataMap.put("thin_" + name + "_slab", new BlockStateData().setBlockStateModelLocation("", new ResourceLocation(Ref.mod_id, "block/stone_layer/thin_slab/" + "thin_" + name + "_slab")));
                    AssetPackRegistry.blockModelDataMap.put("thin_" + name + "_slab", new BlockModelData().setParent(new ResourceLocation(Ref.mod_id, "block/stone_layer/state/thin_slab"))
                            .setParentFolder("stone_layer/thin_slab").setTexturesLocation("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).setTexturesLocation("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock))
                            .setTexturesLocation("texture", new ResourceLocation(stoneLayerModId, stoneLayerBlock)));
                    enLang.addTranslation("block." + Ref.mod_id + ".thin_" + name + "_slab", LangData.translateUpperCase("thin_" + name + "_slab"));
                    //--Tags
                    DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "thin_slabs/" + name)).add(rockBlock.get()));
                    DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "thin_slabs")).add(rockBlock.get()));
                    //--LootTables

                    //--Item--\\
                    //--Resources
                    AssetPackRegistry.itemModelDataHashMap.put("thin_" + name + "_slab", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "block/stone_layer/thin_slab/thin_" + name + "_slab")));
                    enLang.addTranslation("item." + Ref.mod_id + ".thin_" + name + "_slab", LangData.translateUpperCase("thin_" + name + "_slab"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "thin_slabs/" + name)).add(rockItemBlock.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "thin_slabs")).add(rockItemBlock.get()));
                    //--LootTable
                    //DataPackRegistry.saveBlockLootTableDataMap(DataPackRegistry.getBlockLootTableData(new ResourceLocation(Ref.mod_id, "thin_" + name + "_slab")).setLootTable(LootTableData.standardDropTable(thinSlabBlock.get())));
//--//--//--//--//--//--//--//--//
                        /*
                        Block leg_block = RegistryHandler.BLOCKS.register(name + "_leg", new LegBlock(AbstractBlock.Properties.of(Material.STONE), new ResourceLocation("textures/" + stoneLayer.getBlock())));
                        Item leg_item_block = RegistryHandler.ITEMS.register( name + "_leg", new LegItemBlock(leg_block, new Item.Properties().stacksTo(1)));
                         */
                }

                //-- Item --\\

                if (flag == ORE || flag == GEM) {
//--//--//--//--//--//--//--//--//
                    //-- Item --\\
                    //--Resources

                    AssetPackRegistry.itemModelDataHashMap.put("raw_" + name + "_ore", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/raw_ore")));
                    AssetPackRegistry.itemModelDataHashMap.put(name + ":raw_ore", new ItemModelData().setParentFolder("material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/raw_ore"))
                            .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/raw_ore_overlay")));
                    enLang.addTranslation("item." + Ref.mod_id + "." + "raw_" + name + "_ore", LangData.translateUpperCase("raw_" + name + "_ore"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "raw_ores/" + name)).add(rawOre.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "raw_ores")).add(rawOre.get()));
                    //--LootTable

//--//--//--//--//--//--//--//--//
                    //-- Item --\\
                    //--Resources
                    AssetPackRegistry.itemModelDataHashMap.put("crushed_" + name + "_ore", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/crushed_ore")));
                    AssetPackRegistry.itemModelDataHashMap.put(name + ":crushed_ore", new ItemModelData().setParentFolder("material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/crushed_ore"))
                            .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/crushed_ore_overlay")));
                    enLang.addTranslation("item." + Ref.mod_id + "." + "crushed_" + name + "_ore", LangData.translateUpperCase("crushed_" + name + "_ore"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "crushed_ores/" + name)).add(crushedOre.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "crushed_ores")).add(crushedOre.get()));
                    //--LootTable

//--//--//--//--//--//--//--//--//
                    //-- Item --\\
                    //--Resources
                    AssetPackRegistry.itemModelDataHashMap.put("purified_" + name + "_ore", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/purified_ore")));
                    AssetPackRegistry.itemModelDataHashMap.put(name + ":purified_ore", new ItemModelData().setParentFolder("material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/purified_ore"))
                            .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/purified_ore_overlay")));
                    enLang.addTranslation("item." + Ref.mod_id + "." + "purified_" + name + "_ore", LangData.translateUpperCase("purified_" + name + "_ore"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "purified_ores/" + name)).add(purifiedOre.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "purified_ores")).add(purifiedOre.get()));
                    //--LootTable

//--//--//--//--//--//--//--//--//
                    // -- Item --\\
                    //--Resources
                    AssetPackRegistry.itemModelDataHashMap.put("centrifuged_" + name + "_ore", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/centrifuged_ore")));
                    AssetPackRegistry.itemModelDataHashMap.put(name + ":centrifuged_ore", new ItemModelData().setParentFolder("material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/centrifuged_ore"))
                            .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/centrifuged_ore_overlay")));
                    enLang.addTranslation("item." + Ref.mod_id + "." + "centrifuged_" + name + "_ore", LangData.translateUpperCase("centrifuged_" + name + "_ore"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "centrifuged_ores/" + name)).add(centrifugedOre.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "centrifuged_ores")).add(centrifugedOre.get()));
                    //--LootTable

//--//--//--//--//--//--//--//--//
                }

                if (flag == GEM) {
//--//--//--//--//--//--//--//--//
                    // -- Item --\\
                    //--Resources
                    AssetPackRegistry.itemModelDataHashMap.put(name + "_gem", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/gem")));
                    AssetPackRegistry.itemModelDataHashMap.put(name + ":gem", new ItemModelData().setParentFolder("material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/gem"))
                            .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/gem_overlay")));
                    enLang.addTranslation("item." + Ref.mod_id + "." + name + "_gem", LangData.translateUpperCase(name + "_gem"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "gems/" + name)).add(gem.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "gems")).add(gem.get()));
                    //--LootTable

//--//--//--//--//--//--//--//--//
                    // -- Item --\\
                    //--Resources
                    AssetPackRegistry.itemModelDataHashMap.put("chipped_" + name + "_gem", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/chipped_gem")));
                    AssetPackRegistry.itemModelDataHashMap.put(name + ":chipped_gem", new ItemModelData().setParentFolder("material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/chipped_gem"))
                            .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/chipped_gem_overlay")));
                    enLang.addTranslation("item." + Ref.mod_id + "." + "chipped_" + name + "_gem", LangData.translateUpperCase("chipped_" + name + "_gem"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "chipped_gems/" + name)).add(chippedGem.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "chipped_gems")).add(chippedGem.get()));
                    //--LootTable

//--//--//--//--//--//--//--//--//
                    // -- Item --\\
                    //--Resources
                    AssetPackRegistry.itemModelDataHashMap.put("flawed_" + name + "_gem", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/flawed_gem")));
                    AssetPackRegistry.itemModelDataHashMap.put(name + ":flawed_gem", new ItemModelData().setParentFolder("material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/flawed_gem"))
                            .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/flawed_gem_overlay")));
                    enLang.addTranslation("item." + Ref.mod_id + "." + "flawed_" + name + "_gem", LangData.translateUpperCase("flawed_" + name + "_gem"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "flawed_gems/" + name)).add(flawedGem.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "flawed_gems")).add(flawedGem.get()));
                    //--LootTable

//--//--//--//--//--//--//--//--//
                    // -- Item --\\
                    //--Resources
                    AssetPackRegistry.itemModelDataHashMap.put("flawless_" + name + "_gem", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/flawless_gem")));
                    AssetPackRegistry.itemModelDataHashMap.put(name + ":flawless_gem", new ItemModelData().setParentFolder("material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/flawless_gem"))
                            .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/flawless_gem_overlay")));
                    enLang.addTranslation("item." + Ref.mod_id + "." + "flawless_" + name + "_gem", LangData.translateUpperCase("flawless_" + name + "_gem"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "flawless_gems/" + name)).add(flawlessGem.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "flawless_gems")).add(flawlessGem.get()));
                    //--LootTable

//--//--//--//--//--//--//--//--//
                    // -- Item --\\
                    //--Resources
                    AssetPackRegistry.itemModelDataHashMap.put("legendary_" + name + "_gem", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/legendary_gem")));
                    AssetPackRegistry.itemModelDataHashMap.put(name + ":legendary_gem", new ItemModelData().setParentFolder("material_icons/" + textureIcon.getSecond().getName().toLowerCase()).setParent(new ResourceLocation("item/generated"))
                            .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/legendary_gem"))
                            .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName().toLowerCase() + "/legendary_gem_overlay")));
                    enLang.addTranslation("item." + Ref.mod_id + "." + "legendary_" + name + "_gem", LangData.translateUpperCase("legendary_" + name + "_gem"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "legendary_gems/" + name)).add(legendaryGem.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "legendary_gems")).add(legendaryGem.get()));
                    //--LootTable

//--//--//--//--//--//--//--//--//
                }
//--//--//--//--//--//--//--//--//
                // -- Item --\\
                //--Resources
                AssetPackRegistry.itemModelDataHashMap.put(name + "_dust", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/dust")));
                AssetPackRegistry.itemModelDataHashMap.put(name + ":dust", new ItemModelData().setParentFolder("material_icons/" + textureIcon.getSecond().getName()).setParent(new ResourceLocation("item/generated"))
                        .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/dust"))
                        .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/dust_overlay")));
                enLang.addTranslation("item." + Ref.mod_id + "." + name + "_dust", LangData.translateUpperCase(name + "_dust"));
                //--Tags
                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "dusts/" + name)).add(dust.get()));
                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "dusts")).add(dust.get()));
                //--LootTable

//--//--//--//--//--//--//--//--//
                // -- Item --\\
                //--Resources
                AssetPackRegistry.itemModelDataHashMap.put("small_" + name + "_dust", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/small_dust")));
                AssetPackRegistry.itemModelDataHashMap.put(name + ":small_dust", new ItemModelData().setParentFolder("material_icons/" + textureIcon.getSecond().getName()).setParent(new ResourceLocation("item/generated"))
                        .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/small_dust"))
                        .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/small_dust_overlay")));
                enLang.addTranslation("item." + Ref.mod_id + "." + "small_" + name + "_dust", LangData.translateUpperCase("small_" + name + "_dust"));
                //--Tags
                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "small_dusts/" + name)).add(smallDust.get()));
                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "small_dusts")).add(smallDust.get()));
                //--LootTable

//--//--//--//--//--//--//--//--//
                // -- Item --\\
                //--Resources
                AssetPackRegistry.itemModelDataHashMap.put("tiny_" + name + "_dust", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/tiny_dust")));
                AssetPackRegistry.itemModelDataHashMap.put(name + ":tiny_dust", new ItemModelData().setParentFolder("material_icons/" + textureIcon.getSecond().getName()).setParent(new ResourceLocation("item/generated"))
                        .setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/tiny_dust"))
                        .setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().getName() + "/tiny_dust_overlay")));
                enLang.addTranslation("item." + Ref.mod_id + "." + "tiny_" + name + "_dust", LangData.translateUpperCase("tiny_" + name + "_dust"));
                //--Tags
                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "tiny_dusts/" + name)).add(tinyDust.get()));
                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "tiny_dusts")).add(tinyDust.get()));
                //--LootTable

//--//--//--//--//--//--//--//--//

                for (TCMaterial stoneLayer : stoneLayerList) {
                    if (flag == ORE || flag == GEM) {
//--//--//--//--//--//--//--//--//
                        // -- Block --\\
                        //--Resources
                        AssetPackRegistry.blockStateDataMap.put(stoneLayer.name + "_" + name + "_ore", new BlockStateData().setBlockStateModelLocation("", new ResourceLocation(Ref.mod_id, "block/ore/" + stoneLayer.name + "_ore")));
                        enLang.addTranslation("block." + Ref.mod_id + "." + stoneLayer.name + "_" + name + "_ore", LangData.translateUpperCase(stoneLayer.name + "_" + name + "_ore"));
                        //--Tags
                        oreList.forEach(object -> {
                            DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "ores/" + name)).add(object.get()));
                            DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "ores")).add(object.get()));
                            DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("minecraft", "mineable/pickaxe")).add(object.get()));
                        });
                        //--LootTable

                        // -- Item --\\
                        AssetPackRegistry.itemModelDataHashMap.put(stoneLayer.name + "_" + name + "_ore", new ItemModelData()
                                .setParent(new ResourceLocation(Ref.mod_id, "block/ore/" + stoneLayer.name + "_ore")));
                        enLang.addTranslation("item." + Ref.mod_id + "." + stoneLayer.name + "_" + name + "_ore", LangData.translateUpperCase(stoneLayer.name + "_" + name + "_ore"));
                        //--Tags
                        oreItemList.forEach(object -> {
                            DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "ores/" + name)).add(object.get()));
                            DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "ores")).add(object.get()));
                        });
                        //--LootTable

//--//--//--//--//--//--//--//--//
                        // -- Block --\\
                        //--Resources
                        AssetPackRegistry.blockStateDataMap.put("small_" + stoneLayer.name + "_" + name + "_ore", new BlockStateData().setBlockStateModelLocation("", new ResourceLocation(Ref.mod_id, "block/small_ore/" + "small_" + stoneLayer.name + "_ore")));
                        enLang.addTranslation("block." + Ref.mod_id + "." + "small_" + stoneLayer.name + "_" + name + "_ore", LangData.translateUpperCase("small_" + stoneLayer.name + "_" + name + "_ore"));
                        //--Tags
                        smallOreList.forEach(object -> {
                            DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "small_ores/" + stoneLayer.name + "_" + name)).add(object.get()));
                            DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "small_ores")).add(object.get()));
                            DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("minecraft", "mineable/pickaxe")).add(object.get()));
                        });
                        //--LootTable

                        // -- Item --\\
                        //--Resources
                        AssetPackRegistry.itemModelDataHashMap.put("small_" + stoneLayer.name + "_" + name + "_ore", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "block/small_ore/" + "small_" + stoneLayer.name + "_ore")));
                        enLang.addTranslation("item." + Ref.mod_id + "." + "small_" + stoneLayer.name + "_" + name + "_ore", LangData.translateUpperCase("small_" + stoneLayer.name + "_" + name + "_ore"));
                        //--Tags
                        smallOreItemList.forEach(object -> {
                            DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "small_ores/" + stoneLayer.name + "_" + name)).add(object.get()));
                            DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "small_ores")).add(object.get()));
                        });
                        //--LootTable

//--//--//--//--//--//--//--//--//
                        // -- Block --\\
                        //--Resources
                        AssetPackRegistry.blockStateDataMap.put("dense_" + stoneLayer.name + "_" + name + "_ore", new BlockStateData().setBlockStateModelLocation("", new ResourceLocation(Ref.mod_id, "block/dense_ore/" + "dense_" + stoneLayer.name + "_ore")));
                        enLang.addTranslation("block." + Ref.mod_id + "." + "dense_" + stoneLayer.name + "_" + name + "_ore", LangData.translateUpperCase("dense_" + stoneLayer.name + "_" + name + "_ore"));
                        //--Tags
                        denseOreList.forEach(object -> {
                            DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "dense_ores/" + stoneLayer.name + "_" + name)).add(object.get()));
                            DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "dense_ores")).add(object.get()));
                            DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("minecraft", "mineable/pickaxe")).add(object.get()));
                        });
                        //--LootTable

                        // -- Item --\\
                        //--Resources
                        AssetPackRegistry.itemModelDataHashMap.put("dense_" + stoneLayer.name + "_" + name + "_ore", new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "block/dense_ore/" + "dense_" + stoneLayer.name + "_ore")));
                        enLang.addTranslation("item." + Ref.mod_id + "." + "dense_" + stoneLayer.name + "_" + name + "_ore", LangData.translateUpperCase("dense_" + stoneLayer.name + "_" + name + "_ore"));
                        //--Tags
                        denseOreItemList.forEach(object -> {
                            DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "dense_ores/" + stoneLayer.name + "_" + name)).add(object.get()));
                            DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "dense_ores")).add(object.get()));
                        });
                        //--LootTable

//--//--//--//--//--//--//--//--//
                    }
                }
            }

            if (flag == FLUID || flag == GAS) {

                if (flag == FLUID) {

                }

                if (flag == GAS) {

                }
            }

            if (flag == INGOT) {
//--//--//--//--//--//--//--//--//
                // -- Item --\\
                //--Resources
                AssetPackRegistry.itemModelDataHashMap.put(name + "_" + INGOT.name(), new ItemModelData().setParent(new ResourceLocation(Ref.mod_id, "/material_icons/" + textureIcon.getSecond().getName() + "/ingot")));
                enLang.addTranslation("item." + Ref.mod_id + "." + name + "_" + INGOT.name(), LangData.translateUpperCase(name + "_" + INGOT.name()));
                //--Tags

                //--LootTable

//--//--//--//--//--//--//--//--//
            }
        }

        extraSaveResources.forEach((string, iMaterial) -> iMaterial.saveResources(this));

        AssetPackRegistry.langDataMap.put("en_us", enLang);
    }

    public void clientRenderLayerInit() {
        if (rockBlock != null) {
            ItemBlockRenderTypes.setRenderLayer(rockBlock.get(), RenderType.cutout());
        }

        if (thinSlabBlock != null) {
            ItemBlockRenderTypes.setRenderLayer(thinSlabBlock.get(), RenderType.cutout());
        }

        extraSave.forEach((string, iMaterial) -> iMaterial.clientRenderLayerInit(this));
    }

    public void registerColorForBlock() {
        for (RegistryObject<Block> block : oreList) {
            setupABlockColor(block.get());
        }
        for (RegistryObject<Block> block : smallOreList) {
            setupABlockColor(block.get());
        }
        for (RegistryObject<Block> block : denseOreList) {
            setupABlockColor(block.get());
        }

        if (rockBlock != null) {
            setupABlockColor(rockBlock.get());
        }

        extraSave.forEach((string, iMaterial) -> iMaterial.registerColorForBlock(this));
    }

    public void setupABlockColor(Block block) {
        ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
        Minecraft.getInstance().getBlockColors().register((state, world, pos, tintIndex) -> {
            if (tintIndex != 0)
                return 0xFFFFFFFF;
            return color;
        }, block);
    }

    public void registerColorForItem() {
        if (rockItemBlock != null) {
            registerAItemColor(rockItemBlock.get(), 0);
            registerAItemColor(rockItemBlock.get(), 1);
        }

        for (RegistryObject<Item> item : oreItemList) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return color;
            }, item.get());
        }
        for (RegistryObject<Item> item : smallOreItemList) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return color;
            }, item.get());
        }

        for (RegistryObject<Item> item : denseOreItemList) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return color;
            }, item.get());
        }

        if (rawOre != null) {
            registerAItemColor(rawOre.get(), 1);
        }

        if (dust != null) {
            registerAItemColor(dust.get(), 0);
        }

        if (smallDust != null) {
            registerAItemColor(smallDust.get(), 0);
        }

        if (tinyDust != null) {
            registerAItemColor(tinyDust.get(), 0);
        }
        if (ingot != null) {
            registerAItemColor(ingot.get(), 0);
        }
        if (gem != null) {
            registerAItemColor(gem.get(), 0);
        }
        if (chippedGem != null) {
            registerAItemColor(chippedGem.get(), 0);
        }
        if (flawedGem != null) {
            registerAItemColor(flawedGem.get(), 0);
        }
        if (flawlessGem != null) {
            registerAItemColor(flawlessGem.get(), 0);
        }
        if (legendaryGem != null) {
            registerAItemColor(legendaryGem.get(), 0);
        }
        if (crushedOre != null) {
            registerAItemColor(crushedOre.get(), 0);
        }
        if (purifiedOre != null) {
            registerAItemColor(purifiedOre.get(), 0);
        }
        if (centrifugedOre != null) {
            registerAItemColor(centrifugedOre.get(), 0);
        }

        extraSave.forEach((string, iMaterial) -> iMaterial.registerColorForItem(this));
    }

    public void registerAItemColor(Item item, int layerNumberIn) {
        if (item != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex <= layerNumberIn)
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