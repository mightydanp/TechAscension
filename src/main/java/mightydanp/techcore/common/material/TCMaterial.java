package mightydanp.techcore.common.material;

import com.mojang.datafixers.util.Pair;
import mightydanp.techapi.common.resources.asset.AssetPackRegistry;
import mightydanp.techapi.common.resources.asset.data.*;
import mightydanp.techapi.common.resources.data.DataPackRegistry;
import mightydanp.techcore.common.blocks.*;
import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.holder.MCMaterialHolder;
import mightydanp.techcore.common.items.*;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.fluidstate.FluidStateCodec;
import mightydanp.techcore.common.jsonconfig.materialflag.MaterialFlagCodec;
import mightydanp.techcore.common.jsonconfig.fluidstate.DefaultFluidState;
import mightydanp.techcore.common.jsonconfig.icons.TextureIconCodec;
import mightydanp.techcore.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.techcore.common.jsonconfig.material.ore.DefaultOreType;
import mightydanp.techcore.common.jsonconfig.material.ore.OreTypeCodec;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.material.fluid.TCFluid;
import mightydanp.techcore.common.material.fluid.TCFluidBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

import static mightydanp.techcore.common.jsonconfig.materialflag.DefaultMaterialFlag.*;

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

    public Pair<String, TextureIconCodec> textureIcon;

    public String symbol = null;

    public Integer denseOreDensity = 8;
    public OreTypeCodec oreType = null;

    public Boolean isStoneLayer = null;
    public String stoneLayerBlock = null;
    public String stoneLayerTextureLocation = null;

    public Integer harvestLevel = null;

    public Integer meltingPoint = null;
    public Integer boilingPoint = null;

    public Float fluidAcceleration = 0.014F;
    public FluidStateCodec fluidState = null;
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

    public List<MaterialFlagCodec> materialFlags = new ArrayList<>();
    public List<RegistryObject<Block>> oreList = new ArrayList<>();
    public List<RegistryObject<Block>> smallOreList = new ArrayList<>();
    public List<RegistryObject<Block>> denseOreList = new ArrayList<>();

    public List<TCMaterialHolders.itemStoneLayerColorHolder> rawOreItemList = new ArrayList<>();
    public List<TCMaterialHolders.itemStoneLayerColorHolder> oreItemList = new ArrayList<>();
    public List<TCMaterialHolders.itemStoneLayerColorHolder> smallOreItemList = new ArrayList<>();
    public List<TCMaterialHolders.itemStoneLayerColorHolder> denseOreItemList = new ArrayList<>();


    public RegistryObject<Item> ingot, gem, chippedGem, flawedGem, flawlessGem, legendaryGem, crushedOre, purifiedOre, centrifugedOre, dust, smallDust, tinyDust;
    public RegistryObject<FlowingFluid> fluid, fluid_flowing;
    public RegistryObject<Block> fluidBlock;

    public RegistryObject<Block> layerBlock, rockBlock, thinSlabBlock;

    public RegistryObject<Item> layerItemBlock, rockItemBlock, thinSlabItemBlock;


    public TCMaterial(String materialNameIn, int colorIn, Pair<String, TextureIconCodec> textureIconLocationIn) {
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
            materialFlags.add(STONE_LAYER.getCodec());
        }

        return this;
    }

    public TCMaterial setBlockProperties(int miningLevelIn) {
        harvestLevel = miningLevelIn;
        return this;
    }

    public TCMaterial setOreType(OreTypeCodec oreTypeIn) {
        oreType = oreTypeIn;

        if (oreTypeIn == DefaultOreType.ORE.getCodec()) {
            materialFlags.add(ORE.getCodec());
        }

        if (oreTypeIn == DefaultOreType.GEM.getCodec()) {
            materialFlags.add(GEM.getCodec());
        }

        if (oreTypeIn == DefaultOreType.CRYSTAL.getCodec()) {

        }
        return this;
    }

    public TCMaterial setDenseOreDensity(int densityIn) {
        denseOreDensity = densityIn;
        return this;
    }

    public TCMaterial setFluidProperties(FluidStateCodec stateIn, float accelerationIn, Integer densityIn, Integer luminosityIn, Integer viscosityIn) {
        fluidState = stateIn;
        fluidAcceleration = accelerationIn;
        if (densityIn != null) fluidDensity = densityIn;
        if (luminosityIn != null) fluidLuminosity = luminosityIn;
        if (viscosityIn != null) fluidViscosity = viscosityIn;

        if (stateIn == DefaultFluidState.FLUID.fluidState) {
            materialFlags.add(FLUID.getCodec());
        }

        if (stateIn == DefaultFluidState.GAS.fluidState) {
            materialFlags.add(GAS.getCodec());
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
        materialFlags.add(TOOL.getCodec());
        return this;
    }


    public TCMaterial save() {
        List<TCMaterial> stoneLayerList = ((MaterialRegistry) TCJsonConfigs.material.getFirst()).getAllValues().stream().filter(i -> i.isStoneLayer != null && i.isStoneLayer).toList();
        //--
        for (MaterialFlagCodec flag : materialFlags) {
            if (flag == ORE.getCodec() || flag == GEM.getCodec() || flag == STONE_LAYER.getCodec()) {
                if (flag == STONE_LAYER.getCodec()) {
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

                if (flag == ORE.getCodec() || flag == GEM.getCodec()) {
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

                if (flag == GEM.getCodec()) {
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

                    if (flag == ORE.getCodec() || flag == GEM.getCodec()) {
                        String stoneLayerBlockName = stoneLayer.stoneLayerBlock.equals("") ? String.valueOf(layerBlock.get().getRegistryName()) : stoneLayer.stoneLayerBlock;
//--//--//--//--//--//--//--//--//
                        RegistryObject<Block> ore = RegistryHandler.BLOCKS.register(stoneLayer.name + "_" + name + "_ore", () -> new OreBlock(name + "_ore", BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE), stoneLayerBlockName));
                        oreList.add(ore);
                        //--
                        RegistryObject<Item> oreItem = RegistryHandler.ITEMS.register(stoneLayer.name + "_" + name + "_ore", () ->
                                new BlockOreItem(ore, new Item.Properties().tab(TCCreativeModeTab.ore_tab)
                                        , boilingPoint, meltingPoint, symbol));
                        oreItemList.add(new TCMaterialHolders.itemStoneLayerColorHolder(oreItem, stoneLayer.color));
                        //--
                        RegistryObject<Item> rawOre = RegistryHandler.ITEMS.register("raw_" + stoneLayer.name + "_" + name + "_ore", () ->
                                new OreProductsItem(new Item.Properties().tab(TCCreativeModeTab.ore_products_tab), boilingPoint, meltingPoint, symbol));
                        rawOreItemList.add(new TCMaterialHolders.itemStoneLayerColorHolder(rawOre, stoneLayer.color));
//--//--//--//--//--//--//--//--//
                        RegistryObject<Block> smallOreBlock = RegistryHandler.BLOCKS.register("small_" + stoneLayer.name + "_" + name + "_ore", () ->
                                new SmallOreBlock("small_" + name + "_ore", BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE), stoneLayerBlockName));
                        smallOreList.add(smallOreBlock);
                        //--
                        RegistryObject<Item> smallOreItem = RegistryHandler.ITEMS.register("small_" + stoneLayer.name + "_" + name + "_ore", () ->
                                new BlockOreItem(smallOreBlock, new Item.Properties().tab(TCCreativeModeTab.ore_tab), boilingPoint, meltingPoint, symbol));
                        smallOreItemList.add(new TCMaterialHolders.itemStoneLayerColorHolder(smallOreItem, stoneLayer.color));
//--//--//--//--//--//--//--//--//
                        RegistryObject<Block> denseOreBlock = RegistryHandler.BLOCKS.register("dense_" + stoneLayer.name + "_" + name + "_ore", () ->
                                new DenseOreBlock("dense_" + name + "_ore", BlockBehaviour.Properties.of(net.minecraft.world.level.material.Material.STONE), denseOreDensity, stoneLayerBlockName));
                        denseOreList.add(denseOreBlock);
                        //--
                        RegistryObject<Item> denseOreItem = RegistryHandler.ITEMS.register("dense_" + stoneLayer.name + "_" + name + "_ore", () ->
                                new DenseOreBlockItem(denseOreBlock, new Item.Properties().tab(TCCreativeModeTab.ore_tab), boilingPoint, meltingPoint, symbol, denseOreDensity));
                        denseOreItemList.add(new TCMaterialHolders.itemStoneLayerColorHolder(denseOreItem, stoneLayer.color));
//--//--//--//--//--//--//--//--//
                    }
                }
            }

            if (flag == FLUID.getCodec() || flag == GAS.getCodec()) {
                FluidAttributes.Builder attributes;

                if (flag == FLUID.getCodec()) {
                    attributes = FluidAttributes.builder(new ResourceLocation("fluid/" + name), new ResourceLocation("fluid/" + name + "_flowing")).temperature(meltingPoint).color(color);
                    if (fluidDensity != null) attributes.density(fluidDensity);
                    if (fluidLuminosity != null) attributes.luminosity(fluidLuminosity);
                    if (fluidViscosity != null) attributes.viscosity(fluidViscosity);
                    ForgeFlowingFluid.Properties properties = new ForgeFlowingFluid.Properties(() -> fluid.get(), () -> fluid_flowing.get(), attributes);
                    fluid = RegistryHandler.FLUIDS.register(name + "_still", () -> new TCFluid(properties, true, color));
                    fluid_flowing = RegistryHandler.FLUIDS.register(name + "_flowing", () -> new TCFluid(properties, false, color));

                    fluidBlock = RegistryHandler.BLOCKS.register(name, () -> new TCFluidBlock(() -> fluid.get(), fluidAcceleration, color));
                }

                if (flag == GAS.getCodec()) {
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

            if (flag == INGOT.getCodec()) {
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

    //to-do put all once one things in here.
    public static void saveOnceResources() {
        List<TCMaterial> stoneLayerList = ((MaterialRegistry) TCJsonConfigs.material.getFirst()).getAllValues().stream().filter(i -> i.isStoneLayer != null && i.isStoneLayer).toList();

        {
            ModelData modelData = new ModelData("ore", ModelData.BLOCK_FOLDER, "ore/state");
            modelData.getModel()
                    .setParent(TAModelBuilder.ExistingBlockModels.block.model)
                    .texture("overlay", new ResourceLocation(Ref.mod_id, "block/material_icons/ore"));

            modelData.getModel().element().from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.DOWN).texture("#sourceblock").cullface(Direction.DOWN).end()
                    .face(Direction.UP).texture("#sourceblock").cullface(Direction.UP).end()
                    .face(Direction.NORTH).texture("#sourceblock").cullface(Direction.NORTH).end()
                    .face(Direction.WEST).texture("#sourceblock").cullface(Direction.WEST).end()
                    .face(Direction.EAST).texture("#sourceblock").cullface(Direction.EAST);

            modelData.getModel().element().from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.DOWN).texture("#overlay").cullface(Direction.DOWN).tintindex(0).end()
                    .face(Direction.UP).texture("#overlay").cullface(Direction.UP).tintindex(0).end()
                    .face(Direction.NORTH).texture("#overlay").cullface(Direction.NORTH).tintindex(0).end()
                    .face(Direction.WEST).texture("#overlay").cullface(Direction.WEST).tintindex(0).end()
                    .face(Direction.EAST).texture("#overlay").cullface(Direction.EAST).tintindex(0).end();

            AssetPackRegistry.blockModelDataMap.put("ore", modelData);
        }
/////////
        {
            ModelData modelData = new ModelData("small_ore", ModelData.BLOCK_FOLDER, "ore/state");
            modelData.getModel()
                    .setParent(TAModelBuilder.ExistingBlockModels.block.model)
                    .texture("overlay", new ResourceLocation(Ref.mod_id, "block/material_icons/small_ore"));

            modelData.getModel().element().from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.DOWN).texture("#sourceblock").cullface(Direction.DOWN).end()
                    .face(Direction.UP).texture("#sourceblock").cullface(Direction.UP).end()
                    .face(Direction.NORTH).texture("#sourceblock").cullface(Direction.NORTH).end()
                    .face(Direction.WEST).texture("#sourceblock").cullface(Direction.WEST).end()
                    .face(Direction.EAST).texture("#sourceblock").cullface(Direction.EAST);

            modelData.getModel().element().from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.DOWN).texture("#overlay").cullface(Direction.DOWN).tintindex(0).end()
                    .face(Direction.UP).texture("#overlay").cullface(Direction.UP).tintindex(0).end()
                    .face(Direction.NORTH).texture("#overlay").cullface(Direction.NORTH).tintindex(0).end()
                    .face(Direction.WEST).texture("#overlay").cullface(Direction.WEST).tintindex(0).end()
                    .face(Direction.EAST).texture("#overlay").cullface(Direction.EAST).tintindex(0).end();

            AssetPackRegistry.blockModelDataMap.put("small_ore", modelData);
        }

/////////
        {
            ModelData modelData = new ModelData("dense_ore", ModelData.BLOCK_FOLDER, "ore/state");
            modelData.getModel()
                    .setParent(TAModelBuilder.ExistingBlockModels.block.model)
                    .texture("overlay", new ResourceLocation(Ref.mod_id, "block/material_icons/dense_ore"));

            modelData.getModel().element().from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.DOWN).texture("#sourceblock").cullface(Direction.DOWN).end()
                    .face(Direction.UP).texture("#sourceblock").cullface(Direction.UP).end()
                    .face(Direction.NORTH).texture("#sourceblock").cullface(Direction.NORTH).end()
                    .face(Direction.WEST).texture("#sourceblock").cullface(Direction.WEST).end()
                    .face(Direction.EAST).texture("#sourceblock").cullface(Direction.EAST);

            modelData.getModel().element().from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.DOWN).texture("#overlay").cullface(Direction.DOWN).tintindex(0).end()
                    .face(Direction.UP).texture("#overlay").cullface(Direction.UP).tintindex(0).end()
                    .face(Direction.NORTH).texture("#overlay").cullface(Direction.NORTH).tintindex(0).end()
                    .face(Direction.WEST).texture("#overlay").cullface(Direction.WEST).tintindex(0).end()
                    .face(Direction.EAST).texture("#overlay").cullface(Direction.EAST).tintindex(0).end();

            AssetPackRegistry.blockModelDataMap.put("dense_ore", modelData);
        }

/////////
        {
            ModelData modelData = new ModelData("gem", ModelData.BLOCK_FOLDER, "ore/state");
            modelData.getModel()
                    .setParent(TAModelBuilder.ExistingBlockModels.block.model)
                    .texture("overlay", new ResourceLocation(Ref.mod_id, "block/material_icons/gem"));

            modelData.getModel().element().from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.DOWN).texture("#sourceblock").cullface(Direction.DOWN).end()
                    .face(Direction.UP).texture("#sourceblock").cullface(Direction.UP).end()
                    .face(Direction.NORTH).texture("#sourceblock").cullface(Direction.NORTH).end()
                    .face(Direction.WEST).texture("#sourceblock").cullface(Direction.WEST).end()
                    .face(Direction.EAST).texture("#sourceblock").cullface(Direction.EAST);

            modelData.getModel().element().from(0, 0, 0).to(16, 16, 16)
                    .face(Direction.DOWN).texture("#overlay").cullface(Direction.DOWN).tintindex(0).end()
                    .face(Direction.UP).texture("#overlay").cullface(Direction.UP).tintindex(0).end()
                    .face(Direction.NORTH).texture("#overlay").cullface(Direction.NORTH).tintindex(0).end()
                    .face(Direction.WEST).texture("#overlay").cullface(Direction.WEST).tintindex(0).end()
                    .face(Direction.EAST).texture("#overlay").cullface(Direction.EAST).tintindex(0).end();

            AssetPackRegistry.blockModelDataMap.put("gem", modelData);
        }

/////////



        for (TCMaterial stoneLayer : stoneLayerList) {

        }
    }

    public void saveResources() throws Exception {
        LangData enLang = AssetPackRegistry.langDataMap.getOrDefault("en_us", new LangData());
        List<TCMaterial> stoneLayerList = ((MaterialRegistry) TCJsonConfigs.material.getFirst()).getAllValues().stream().filter(i -> i.isStoneLayer != null && i.isStoneLayer).toList();
        //--

        for (MaterialFlagCodec flag : materialFlags) {
            if (flag == ORE.getCodec() || flag == GEM.getCodec() || flag == STONE_LAYER.getCodec()) {
                if (flag == STONE_LAYER.getCodec()) {
                    Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).getTag(BlockTags.NEEDS_STONE_TOOL).stream().forEach(block ->
                            DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "tool_level/" + 1)).add(block)));

                    String stoneLayerBlockName = stoneLayerBlock.equals("") ? String.valueOf(layerBlock.get().getRegistryName()) : stoneLayerBlock;
                    String stoneLayerModId = stoneLayerTextureLocation.split(":")[0].equals("minecraft") ? "" : stoneLayerTextureLocation.split(":")[0];

                    String stoneLayerBlock = stoneLayerTextureLocation.split(":")[1];
                    //String resourceID = useMinecraftResource ? "" : Ref.mod_id;
                    {
                        String modelName = name + "_ore";
                        ModelData modelData = new ModelData("ore", ModelData.BLOCK_FOLDER, "ore/state");
                        TAModelBuilder oreModel = modelData.getModel();
                        
                        //to-do
                        ////----start from here for registering models that exist inside model folder

                        ModelData model = new ModelData(modelName, ModelData.BLOCK_FOLDER, "ore");

                        model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "block/ore/state/ore")))
                                .texture("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).texture("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock));
                        AssetPackRegistry.blockModelDataMap.put(modelName, model);
                    }
                    {
                        String modelName = "small_" + name + "_ore";
                        ModelData model = new ModelData(modelName, ModelData.BLOCK_FOLDER, "small_ore");
                        model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "block/ore/state/small_ore")))
                                .texture("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).texture("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock));
                        AssetPackRegistry.blockModelDataMap.put(modelName, model);
                    }
                    {
                        String modelName = "dense_" + name + "_ore";
                        ModelData model = new ModelData(modelName, ModelData.BLOCK_FOLDER, "dense_ore");
                        model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "block/ore/state/dense_ore")))
                                .texture("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).texture("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock));
                        AssetPackRegistry.blockModelDataMap.put(modelName, model);
                    }
//--//--//--//--//--//--//--//--//
                    {
                        String objectName = name + "_rock";
                        //--Block--\\
                        //--Resources
                        {
                            BlockStateData data = new BlockStateData();
                            VariantBlockStateBuilder builder = data.getVariantBuilder(rockBlock.get());
                            ModelFile model = new TAModelBuilder(new ResourceLocation(Ref.mod_id, "block/stone_layer/rock/" + objectName));
                            builder.partialState().setModels(new ConfiguredModel(model));
                            data.setBlockState(builder);
                            AssetPackRegistry.blockStateDataMap.put(objectName, data);
                        }
                        {

                            ModelData model = new ModelData(objectName, ModelData.BLOCK_FOLDER, "stone_layer/rock");
                            model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "block/stone_layer/state/rock")))
                                    .texture("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).texture("particle", new ResourceLocation(stoneLayerModId, stoneLayerBlock));
                            AssetPackRegistry.blockModelDataMap.put(objectName, model);
                        }
                        enLang.addTranslation("block." + Ref.mod_id + "." + objectName, LangData.translateUpperCase(objectName));
                        //--Tags
                        DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "rocks/" + name)).add(rockBlock.get()));
                        DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "rocks")).add(rockBlock.get()));
                        DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("minecraft", "mineable/pickaxe")).add(rockBlock.get()));
                        if (harvestLevel != null) {
                            DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "harvest_level/" + harvestLevel)).add(rockBlock.get()));
                        }

                        //--LootTables
                        DataPackRegistry.saveBlockLootTableDataMap(DataPackRegistry.getBlockLootTableData(new ResourceLocation(Ref.mod_id, objectName)).setLootTable(
                                LootTable.lootTable().withPool(
                                        LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.AIR).setWeight(100))
                                                .setBonusRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.FLINT).setWeight(10))).build()));

                        //--Item--\\
                        //--Resources
                        {
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                            model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name() + "/rock")));
                            AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                        }
                        {
                            String modelName = name + ":rock";
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, "material_icons/" + textureIcon.getSecond().name().toLowerCase());
                            model.getModel().setParent(TAModelBuilder.ExistingItemModels.item_generated.model)
                                    .texture("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/rock"))
                                    .texture("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/rock_overlay")
                            );
                            AssetPackRegistry.itemModelDataHashMap.put(modelName, model);
                        }
                        enLang.addTranslation("item." + Ref.mod_id + "." + name + "_rock", LangData.translateUpperCase(name + "_rock"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "rocks/" + name)).add(rockItemBlock.get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "rocks")).add(rockItemBlock.get()));
                        //--LootTable
                    }
//--//--//--//--//--//--//--//--//
                    {
                        //--Block--\\
                        //--Resources
                        String objectName = "thin_" + name + "_slab";
                        {
                            BlockStateData data = new BlockStateData();
                            VariantBlockStateBuilder builder = data.getVariantBuilder(thinSlabBlock.get());
                            ModelFile model = new TAModelBuilder(new ResourceLocation(Ref.mod_id, "block/stone_layer/thin_slab/" + objectName));
                            builder.partialState().setModels(new ConfiguredModel(model));
                            data.setBlockState(builder);
                            AssetPackRegistry.blockStateDataMap.put(objectName, data);
                        }
                        {
                            ModelData model = new ModelData(objectName, ModelData.BLOCK_FOLDER, "stone_layer/thin_slab");
                            model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "block/stone_layer/state/thin_slab")))
                                    .texture("sourceblock", new ResourceLocation(stoneLayerModId, stoneLayerBlock)).texture("texture", new ResourceLocation(stoneLayerModId, stoneLayerBlock));
                            AssetPackRegistry.blockModelDataMap.put(objectName, model);
                        }
                        //--Tags
                        DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "thin_slabs/" + name)).add(rockBlock.get()));
                        DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "thin_slabs")).add(rockBlock.get()));
                        //--LootTables

                        //--Item--\\
                        //--Resources
                        {
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                            model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "block/stone_layer/thin_slab/thin_" + name + "_slab")));
                            AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                        }
                        enLang.addTranslation("item." + Ref.mod_id + ".thin_" + name + "_slab", LangData.translateUpperCase("thin_" + name + "_slab"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "thin_slabs/" + name)).add(rockItemBlock.get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "thin_slabs")).add(rockItemBlock.get()));
                        //--LootTable
                        //DataPackRegistry.saveBlockLootTableDataMap(DataPackRegistry.getBlockLootTableData(new ResourceLocation(Ref.mod_id, "thin_" + name + "_slab")).setLootTable(LootTableData.standardDropTable(thinSlabBlock.get())));
                    }
//--//--//--//--//--//--//--//--//
                        /*
                        Block leg_block = RegistryHandler.BLOCKS.register(name + "_leg", new LegBlock(AbstractBlock.Properties.of(Material.STONE), new ResourceLocation("textures/" + stoneLayer.getBlock())));
                        Item leg_item_block = RegistryHandler.ITEMS.register( name + "_leg", new LegItemBlock(leg_block, new Item.Properties().stacksTo(1)));
                         */
                }

                //-- Item --\\

                if (flag == ORE.getCodec() || flag == GEM.getCodec()) {
//--//--//--//--//--//--//--//--//
                    {
                        //-- Item --\\
                        //--Resources
                        String objectName = "crushed_" + name + "_ore";
                        {
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                            model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name() + "/crushed_ore")));
                            AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                        }
                        {
                            String modelName = name + ":crushed_ore";
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, "material_icons/" + textureIcon.getSecond().name().toLowerCase());
                            model.getModel().setParent(TAModelBuilder.ExistingItemModels.item_generated.model)
                                    .texture("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/crushed_ore"))
                                    .texture("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/crushed_ore_overlay")
                            );
                            AssetPackRegistry.itemModelDataHashMap.put(modelName, model);
                        }
                        enLang.addTranslation("item." + Ref.mod_id + "." + "crushed_" + name + "_ore", LangData.translateUpperCase("crushed_" + name + "_ore"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "crushed_ores/" + name)).add(crushedOre.get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "crushed_ores")).add(crushedOre.get()));
                        //--LootTable
                    }
//--//--//--//--//--//--//--//--//
                    {
                        //-- Item --\\
                        //--Resources
                        String objectName = "purified_" + name + "_ore";
                        {
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                            model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name() + "/purified_ore")));
                            AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                        }
                        {
                            String modelName = name + ":purified_ore";
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, "material_icons/" + textureIcon.getSecond().name().toLowerCase());
                            model.getModel().setParent(TAModelBuilder.ExistingItemModels.item_generated.model)
                                    .texture("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/purified_ore"))
                                    .texture("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/purified_ore_overlay")
                            );
                            AssetPackRegistry.itemModelDataHashMap.put(modelName, model);
                        }
                        enLang.addTranslation("item." + Ref.mod_id + "." + "purified_" + name + "_ore", LangData.translateUpperCase("purified_" + name + "_ore"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "purified_ores/" + name)).add(purifiedOre.get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "purified_ores")).add(purifiedOre.get()));
                        //--LootTable
                    }
//--//--//--//--//--//--//--//--//
                    {

                        // -- Item --\\
                        //--Resources
                        String objectName = "centrifuged_" + name + "_ore";
                        {
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                            model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name() + "/centrifuged_ore")));
                            AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                        }
                        {
                            String modelName = name + ":centrifuged_ore";
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, "material_icons/" + textureIcon.getSecond().name().toLowerCase());
                            model.getModel().setParent(TAModelBuilder.ExistingItemModels.item_generated.model)
                                    .texture("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/centrifuged_ore"))
                                    .texture("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/centrifuged_ore_overlay")
                            );
                            AssetPackRegistry.itemModelDataHashMap.put(modelName, model);
                        }

                        enLang.addTranslation("item." + Ref.mod_id + "." + "centrifuged_" + name + "_ore", LangData.translateUpperCase("centrifuged_" + name + "_ore"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "centrifuged_ores/" + name)).add(centrifugedOre.get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "centrifuged_ores")).add(centrifugedOre.get()));
                        //--LootTable

                    }

//--//--//--//--//--//--//--//--//
                }

                if (flag == GEM.getCodec()) {
//--//--//--//--//--//--//--//--//
                    {
                        // -- Item --\\
                        //--Resources
                        String objectName = name + "_gem";
                        {
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                            model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name() + "/gem")));
                            AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                        }
                        {
                            String modelName = name + ":gem";
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, "material_icons/" + textureIcon.getSecond().name().toLowerCase());
                            model.getModel().setParent(TAModelBuilder.ExistingItemModels.item_generated.model)
                                    .texture("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/gem"))
                                    .texture("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/gem_overlay")
                            );
                            AssetPackRegistry.itemModelDataHashMap.put(modelName, model);
                        }
                        enLang.addTranslation("item." + Ref.mod_id + "." + name + "_gem", LangData.translateUpperCase(name + "_gem"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "gems/" + name)).add(gem.get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "gems")).add(gem.get()));
                        //--LootTable
                    }
//--//--//--//--//--//--//--//--//
                    {
                        // -- Item --\\
                        //--Resources
                        String objectName = "chipped_" + name + "_gem";
                        {
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                            model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name() + "/chipped_gem")));
                            AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                        }
                        {
                            String modelName = name + ":chipped_gem";
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, "material_icons/" + textureIcon.getSecond().name().toLowerCase());
                            model.getModel().setParent(TAModelBuilder.ExistingItemModels.item_generated.model)
                                    .texture("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/chipped_gem"))
                                    .texture("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/chipped_gem_overlay")
                            );
                            AssetPackRegistry.itemModelDataHashMap.put(modelName, model);
                        }
                        enLang.addTranslation("item." + Ref.mod_id + "." + "chipped_" + name + "_gem", LangData.translateUpperCase("chipped_" + name + "_gem"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "chipped_gems/" + name)).add(chippedGem.get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "chipped_gems")).add(chippedGem.get()));
                        //--LootTable
                    }
//--//--//--//--//--//--//--//--//
                    {
                        // -- Item --\\
                        //--Resources
                        String objectName = "flawed_" + name + "_gem";
                        {
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                            model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name() + "/flawed_gem")));
                            AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                        }
                        {
                            String modelName = name + ":flawed_gem";
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, "material_icons/" + textureIcon.getSecond().name().toLowerCase());
                            model.getModel().setParent(TAModelBuilder.ExistingItemModels.item_generated.model)
                                    .texture("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/flawed_gem"))
                                    .texture("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/flawed_gem_overlay")
                            );
                            AssetPackRegistry.itemModelDataHashMap.put(modelName, model);
                        }
                        enLang.addTranslation("item." + Ref.mod_id + "." + "flawed_" + name + "_gem", LangData.translateUpperCase("flawed_" + name + "_gem"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "flawed_gems/" + name)).add(flawedGem.get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "flawed_gems")).add(flawedGem.get()));
                        //--LootTable
                    }
//--//--//--//--//--//--//--//--//
                    {
                        // -- Item --\\
                        //--Resources
                        String objectName = "flawless_" + name + "_gem";
                        {
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                            model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name() + "/flawless_gem")));
                            AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                        }
                        {
                            String modelName = name + ":flawless_gem";
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, "material_icons/" + textureIcon.getSecond().name().toLowerCase());
                            model.getModel().setParent(TAModelBuilder.ExistingItemModels.item_generated.model)
                                    .texture("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/flawless_gem"))
                                    .texture("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/flawless_gem_overlay")
                            );
                            AssetPackRegistry.itemModelDataHashMap.put(modelName, model);
                        }
                        enLang.addTranslation("item." + Ref.mod_id + "." + "flawless_" + name + "_gem", LangData.translateUpperCase("flawless_" + name + "_gem"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "flawless_gems/" + name)).add(flawlessGem.get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "flawless_gems")).add(flawlessGem.get()));
                        //--LootTable
                    }
//--//--//--//--//--//--//--//--//
                    {
                        // -- Item --\\
                        //--Resources
                        String objectName = "legendary_" + name + "_gem";
                        {
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                            model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name() + "/legendary_gem")));
                            AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                        }
                        {
                            String modelName = name + ":legendary_gem";
                            ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, "material_icons/" + textureIcon.getSecond().name().toLowerCase());
                            model.getModel().setParent(TAModelBuilder.ExistingItemModels.item_generated.model)
                                    .texture("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/legendary_gem"))
                                    .texture("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/legendary_gem_overlay")
                            );
                            AssetPackRegistry.itemModelDataHashMap.put(modelName, model);
                        }

                        enLang.addTranslation("item." + Ref.mod_id + "." + "legendary_" + name + "_gem", LangData.translateUpperCase("legendary_" + name + "_gem"));
                        //--Tags
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "legendary_gems/" + name)).add(legendaryGem.get()));
                        DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "legendary_gems")).add(legendaryGem.get()));
                        //--LootTable
                    }
//--//--//--//--//--//--//--//--//
                }
//--//--//--//--//--//--//--//--//
                {
                    // -- Item --\\
                    //--Resources
                    String objectName = name + "_dust";
                    {
                        ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                        model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name() + "/dust")));
                        AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                    }
                    {
                        String modelName = name + ":dust";
                        ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, "material_icons/" + textureIcon.getSecond().name().toLowerCase());
                        model.getModel().setParent(TAModelBuilder.ExistingItemModels.item_generated.model)
                                .texture("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/dust"))
                                .texture("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/dust_overlay")
                        );
                        AssetPackRegistry.itemModelDataHashMap.put(modelName, model);
                    }
                    enLang.addTranslation("item." + Ref.mod_id + "." + name + "_dust", LangData.translateUpperCase(name + "_dust"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "dusts/" + name)).add(dust.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "dusts")).add(dust.get()));
                    //--LootTable
                }
//--//--//--//--//--//--//--//--//
                {
                    // -- Item --\\
                    //--Resources
                    String objectName = "small_" + name + "_dust";
                    {
                        ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                        model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name() + "/small_dust")));
                        AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                    }
                    {
                        String modelName = name + ":small_dust";
                        ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, "material_icons/" + textureIcon.getSecond().name().toLowerCase());
                        model.getModel().setParent(TAModelBuilder.ExistingItemModels.item_generated.model)
                                .texture("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/small_dust"))
                                .texture("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/small_dust_overlay")
                        );
                        AssetPackRegistry.itemModelDataHashMap.put(modelName, model);
                    }
                    enLang.addTranslation("item." + Ref.mod_id + "." + "small_" + name + "_dust", LangData.translateUpperCase("small_" + name + "_dust"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "small_dusts/" + name)).add(smallDust.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "small_dusts")).add(smallDust.get()));
                    //--LootTable
                }
//--//--//--//--//--//--//--//--//
                {
                    // -- Item --\\
                    //--Resources
                    String objectName = "tiny_" + name + "_dust";
                    {
                        ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                        model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name() + "/tiny_dust")));
                        AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                    }
                    {
                        String modelName = name + ":tiny_dust";
                        ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, "material_icons/" + textureIcon.getSecond().name().toLowerCase());
                        model.getModel().setParent(TAModelBuilder.ExistingItemModels.item_generated.model)
                                .texture("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/tiny_dust"))
                                .texture("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/tiny_dust_overlay")
                        );
                        AssetPackRegistry.itemModelDataHashMap.put(modelName, model);
                    }
                    enLang.addTranslation("item." + Ref.mod_id + "." + "tiny_" + name + "_dust", LangData.translateUpperCase("tiny_" + name + "_dust"));
                    //--Tags
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "tiny_dusts/" + name)).add(tinyDust.get()));
                    DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "tiny_dusts")).add(tinyDust.get()));
                    //--LootTable
                }
//--//--//--//--//--//--//--//--//

                for (TCMaterial stoneLayer : stoneLayerList) {
                    if (flag == ORE.getCodec() || flag == GEM.getCodec()) {
//--//--//--//--//--//--//--//--//
                        {
                            String objectName = stoneLayer.name + "_" + name + "_ore";
                            // -- Block --\\
                            //--Resources

                            oreList.forEach(object -> {
                                BlockStateData data = new BlockStateData();
                                VariantBlockStateBuilder builder;
                                try {
                                    builder = data.getVariantBuilder(object.get());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }

                                ModelFile model = new TAModelBuilder(new ResourceLocation(Ref.mod_id, "block/ore/" + stoneLayer.name + "_ore"));
                                builder.partialState().setModels(new ConfiguredModel(model));
                                data.setBlockState(builder);
                                AssetPackRegistry.blockStateDataMap.put(objectName, data);
                            });
                            enLang.addTranslation("block." + Ref.mod_id + "." + objectName, LangData.translateUpperCase(objectName));
                            //--Tags
                            oreList.forEach(object -> {
                                DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "ores/" + name)).add(object.get()));
                                DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "ores")).add(object.get()));
                                DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("minecraft", "mineable/pickaxe")).add(object.get()));
                            });
                            //--LootTable

                            // -- Item --\\
                            //--Resources
                            {
                                ModelData model = new ModelData("raw_" + objectName, ModelData.ITEM_FOLDER, null);
                                model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name() + "/raw_ore"))
                                );
                                AssetPackRegistry.itemModelDataHashMap.put("raw_" + objectName, model);
                            }{

                                ModelData model = new ModelData("raw_" + objectName, ModelData.ITEM_FOLDER, "material_icons/" + textureIcon.getSecond().name().toLowerCase());
                                model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation("item/generated")))
                                        .texture("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/raw_ore"))
                                        .texture("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/" + textureIcon.getSecond().name().toLowerCase() + "/raw_ore_overlay")
                                );
                                AssetPackRegistry.itemModelDataHashMap.put(stoneLayer.name + "_" + name + ":raw_ore", model);
                            }

                            enLang.addTranslation("item." + Ref.mod_id + "." + "raw_" + objectName, LangData.translateUpperCase("raw_" + objectName));
                            //--
                            {
                                ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                                model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "block/ore/" + stoneLayer.name + "_ore")));
                                AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                            }
                            enLang.addTranslation("item." + Ref.mod_id + "." + objectName, LangData.translateUpperCase(objectName));
                            //--Tags
                            rawOreItemList.forEach(object -> {
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "raw_ores/" + stoneLayer.name + "_" + name)).add(object.item().get()));
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "raw_ores")).add(object.item().get()));
                            });

                            oreItemList.forEach(object -> {
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "ores/" + name)).add(object.item().get()));
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "ores")).add(object.item().get()));
                            });
                            //--LootTable
                            rawOreItemList.forEach(object -> {
                            /*DataPackRegistry.saveBlockLootTableDataMap(DataPackRegistry.getBlockLootTableData(object.item().getId()).setLootTable(LootTable.lootTable()
                                    .withPool(LootPool.lootPool().when(new AlternativeLootItemCondition(new LootItemCondition[]{
                                            new MatchTool(new ItemPredicate())
                                    })))
                            ));
                             */
                            });
                        }
//--//--//--//--//--//--//--//--//
                        {
                            String objectName = "small_" + stoneLayer.name + "_" + name + "_ore";
                            // -- Block --\\

                            //--Resources
                            smallOreList.forEach(object -> {
                                BlockStateData data = new BlockStateData();
                                VariantBlockStateBuilder builder;
                                try {
                                    builder = data.getVariantBuilder(object.get());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }

                                //todo make all ores base models here not in resources

                                ModelFile model = new TAModelBuilder(new ResourceLocation(Ref.mod_id, "block/small_ore/" + "small_" + stoneLayer.name + "_ore"));
                                builder.partialState().setModels(new ConfiguredModel(model));
                                data.setBlockState(builder);
                                AssetPackRegistry.blockStateDataMap.put(objectName, data);
                            });
                            enLang.addTranslation("block." + Ref.mod_id + "." + objectName, LangData.translateUpperCase(objectName));
                            //--Tags
                            smallOreList.forEach(object -> {
                                DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "small_ores/" + stoneLayer.name + "_" + name)).add(object.get()));
                                DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "small_ores")).add(object.get()));
                                DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("minecraft", "mineable/pickaxe")).add(object.get()));
                            });
                            //--LootTable

                            // -- Item --\\

                            //--Resources
                            {
                                ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                                model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "block/small_ore/" + "small_" + stoneLayer.name + "_ore")));
                                AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                            }
                            enLang.addTranslation("item." + Ref.mod_id + "." + objectName, LangData.translateUpperCase(objectName));
                            //--Tags
                            smallOreItemList.forEach(object -> {
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "small_ores/" + stoneLayer.name + "_" + name)).add(object.item().get()));
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "small_ores")).add(object.item().get()));
                            });
                            //--LootTable
                        }
//--//--//--//--//--//--//--//--//
                        {
                            String objectName = "dense_" + stoneLayer.name + "_" + name + "_ore";
                            // -- Block --\\
                            //--Resources

                            denseOreList.forEach(object -> {
                                BlockStateData data = new BlockStateData();
                                VariantBlockStateBuilder builder;

                                try {
                                    builder = data.getVariantBuilder(object.get());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }

                                ModelFile model = new TAModelBuilder(new ResourceLocation(Ref.mod_id, "block/dense_ore/" + "dense_" + stoneLayer.name + "_ore"));
                                builder.partialState().setModels(new ConfiguredModel(model));
                                data.setBlockState(builder);
                                AssetPackRegistry.blockStateDataMap.put(objectName, data);
                            });
                            enLang.addTranslation("block." + Ref.mod_id + "." + objectName, LangData.translateUpperCase(objectName));
                            //--Tags
                            denseOreList.forEach(object -> {
                                DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "dense_ores/" + stoneLayer.name + "_" + name)).add(object.get()));
                                DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("forge", "dense_ores")).add(object.get()));
                                DataPackRegistry.saveBlockTagData(DataPackRegistry.getBlockTagData(new ResourceLocation("minecraft", "mineable/pickaxe")).add(object.get()));
                            });
                            //--LootTable

                            // -- Item --\\
                            //--Resources
                            {
                                ModelData model = new ModelData(objectName, ModelData.ITEM_FOLDER, null);
                                model.getModel().setParent(new ModelFile.UncheckedModelFile(new ResourceLocation(Ref.mod_id, "block/dense_ore/" + "dense_" + stoneLayer.name + "_ore")));
                                AssetPackRegistry.itemModelDataHashMap.put(objectName, model);
                            }
                            enLang.addTranslation("item." + Ref.mod_id + "." + objectName, LangData.translateUpperCase(objectName));
                            //--Tags
                            denseOreItemList.forEach(object -> {
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "dense_ores/" + stoneLayer.name + "_" + name)).add(object.item().get()));
                                DataPackRegistry.saveItemTagData(DataPackRegistry.getItemTagData(new ResourceLocation("forge", "dense_ores")).add(object.item().get()));
                            });
                            //--LootTable
                            for (int i = 0; i < denseOreItemList.size(); i++) {
                                DenseOreBlockItem denseOreBlockItem = (DenseOreBlockItem) denseOreItemList.get(i).item().get();
                                DenseOreBlock denseOreBlock = (DenseOreBlock) denseOreBlockItem.getBlock();

                                DataPackRegistry.saveBlockLootTableDataMap(DataPackRegistry.getBlockLootTableData(denseOreItemList.get(i).item().getId()).setLootTable(LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(rawOreItemList.get(i).item().get()))).build()));
                                //DataPackRegistry.saveBlockLootTableDataMap(DataPackRegistry.getBlockLootTableData(denseOreItemList.get(i).item().getId()).setLootTable(LootTable.lootTable().withPool(LootPool.lootPool().setRolls(UniformGenerator.between(1, denseOreBlock.getDensity())).add(LootItem.lootTableItem(rawOreItemList.get(i).item().get()))).build()));
                            }
                        }
//--//--//--//--//--//--//--//--//
                    }
                }
            }

            if (flag == FLUID.getCodec() || flag == GAS.getCodec()) {

                if (flag == FLUID.getCodec()) {

                }

                if (flag == GAS.getCodec()) {

                }
            }

            if (flag == INGOT.getCodec()) {
//--//--//--//--//--//--//--//--//
                // -- Item --\\
                //--Resources
                //AssetPackRegistry.itemModelDataHashMap.put(name + "_" + INGOT.getCodec().name(), new ItemModelData().getModel().setParent(new ResourceLocation(Ref.mod_id, "/material_icons/" + textureIcon.getSecond().name() + "/ingot")));
                //enLang.addTranslation("item." + Ref.mod_id + "." + name + "_" + INGOT.getCodec().name(), LangData.translateUpperCase(name + "_" + INGOT.getCodec().name()));
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

        for (TCMaterialHolders.itemStoneLayerColorHolder item : rawOreItemList) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0)
                    return color;

                if (tintIndex == 1)
                    return item.stoneLayerColor();
                return 0xFFFFFFFF;
            }, item.item().get());
        }

        for (TCMaterialHolders.itemStoneLayerColorHolder item : oreItemList) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return color;
            }, item.item().get());
        }

        for (TCMaterialHolders.itemStoneLayerColorHolder item : smallOreItemList) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return color;
            }, item.item().get());
        }

        for (TCMaterialHolders.itemStoneLayerColorHolder item : denseOreItemList) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex != 0)
                    return 0xFFFFFFFF;
                return color;
            }, item.item().get());
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