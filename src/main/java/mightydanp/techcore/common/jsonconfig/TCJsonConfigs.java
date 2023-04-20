package mightydanp.techcore.common.jsonconfig;

import com.mojang.datafixers.util.Pair;
import mightydanp.techapi.common.jsonconfig.IJsonConfig;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techcore.common.jsonconfig.definedstructure.DefinedStructureRegistry;
import mightydanp.techcore.common.jsonconfig.definedstructure.DefinedStructureServer;
import mightydanp.techcore.common.jsonconfig.materialflag.MaterialFlagRegistry;
import mightydanp.techcore.common.jsonconfig.materialflag.MaterialFlagServer;
import mightydanp.techcore.common.jsonconfig.fluidstate.FluidStateRegistry;
import mightydanp.techcore.common.jsonconfig.fluidstate.FluidStateServer;
import mightydanp.techcore.common.jsonconfig.generation.blocksinwater.BlocksInWaterRegistry;
import mightydanp.techcore.common.jsonconfig.generation.blocksinwater.BlocksInWaterServer;
import mightydanp.techcore.common.jsonconfig.generation.orevein.OreVeinRegistry;
import mightydanp.techcore.common.jsonconfig.generation.orevein.OreVeinServer;
import mightydanp.techcore.common.jsonconfig.generation.randomsurface.RandomSurfaceRegistry;
import mightydanp.techcore.common.jsonconfig.generation.randomsurface.RandomSurfaceServer;
import mightydanp.techcore.common.jsonconfig.generation.smallore.SmallOreVeinRegistry;
import mightydanp.techcore.common.jsonconfig.generation.smallore.SmallOreVeinServer;
import mightydanp.techcore.common.jsonconfig.icons.TextureIconRegistry;
import mightydanp.techcore.common.jsonconfig.icons.TextureIconServer;
import mightydanp.techcore.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.techcore.common.jsonconfig.material.data.MaterialServer;
import mightydanp.techcore.common.jsonconfig.material.ore.OreTypeRegistry;
import mightydanp.techcore.common.jsonconfig.material.ore.OreTypeServer;
import mightydanp.techcore.common.jsonconfig.recipe.handcrafting.HandCraftingRegistry;
import mightydanp.techcore.common.jsonconfig.recipe.handcrafting.HandCraftingServer;
import mightydanp.techcore.common.jsonconfig.stonelayer.StoneLayerRegistry;
import mightydanp.techcore.common.jsonconfig.stonelayer.StoneLayerServer;
import mightydanp.techcore.common.jsonconfig.tool.ToolRegistry;
import mightydanp.techcore.common.jsonconfig.tool.ToolServer;
import mightydanp.techcore.common.jsonconfig.tool.part.ToolPartRegistry;
import mightydanp.techcore.common.jsonconfig.tool.part.ToolPartServer;
import mightydanp.techcore.common.jsonconfig.trait.block.BlockTraitRegistry;
import mightydanp.techcore.common.jsonconfig.trait.block.BlockTraitServer;
import mightydanp.techcore.common.jsonconfig.trait.item.ItemTraitRegistry;
import mightydanp.techcore.common.jsonconfig.trait.item.ItemTraitServer;

public class TCJsonConfigs extends ConfigSync {
    public static int materialFlagID = 0;
    public static int fluidStateID = 1;
    public static int oreTypeID = 2;
    public static int textureIconID = 3;
    public static int stoneLayerID = 4;

    public static int toolPartID = 5;
    public static int toolID = 6;
    public static int materialID = 7;
    public static int oreVeinID = 8;
    public static int smallOreID = 9;
    public static int blocksInWaterID = 10;
    public static int randomSurfaceID = 11;

    public static int blockTraitID = 12;

    public static int itemTraitID = 13;

    public static int handCraftingID = 14;
    public static int definedStructureID = 15;


    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>> materialFlag = configs.getOrDefault(materialFlagID, new Pair<>(new MaterialFlagRegistry(),  new MaterialFlagServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>> fluidState = configs.getOrDefault(fluidStateID, new Pair<>(new FluidStateRegistry(),  new FluidStateServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  oreType = configs.getOrDefault(oreTypeID, new Pair<>(new OreTypeRegistry(),  new OreTypeServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  textureIcon = configs.getOrDefault(textureIconID, new Pair<>(new TextureIconRegistry(),  new TextureIconServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  toolPart = configs.getOrDefault(toolPartID, new Pair<>(new ToolPartRegistry(),  new ToolPartServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  tool = configs.getOrDefault(toolID, new Pair<>(new ToolRegistry(),  new ToolServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  stoneLayer = configs.getOrDefault(stoneLayerID, new Pair<>(new StoneLayerRegistry(),  new StoneLayerServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  material = configs.getOrDefault(materialID, new Pair<>(new MaterialRegistry(),  new MaterialServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  oreVein = configs.getOrDefault(oreVeinID, new Pair<>(new OreVeinRegistry(),  new OreVeinServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  smallOre = configs.getOrDefault(smallOreID, new Pair<>(new SmallOreVeinRegistry(),  new SmallOreVeinServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  blocksInWater = configs.getOrDefault(blocksInWaterID, new Pair<>(new BlocksInWaterRegistry(),  new BlocksInWaterServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  randomSurface = configs.getOrDefault(randomSurfaceID, new Pair<>(new RandomSurfaceRegistry(),  new RandomSurfaceServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  blockTrait = configs.getOrDefault(blockTraitID, new Pair<>(new BlockTraitRegistry(),  new BlockTraitServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  itemTrait = configs.getOrDefault(itemTraitID, new Pair<>(new ItemTraitRegistry(),  new ItemTraitServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  handCrafting = configs.getOrDefault(handCraftingID, new Pair<>(new HandCraftingRegistry(),  new HandCraftingServer()));
    public static Pair<? extends IJsonConfig<?>, ? extends JsonConfigServer<?>>  definedStructure = configs.getOrDefault(definedStructureID, new Pair<>(new DefinedStructureRegistry(),  new DefinedStructureServer()));

    public static void init() {
        configs.put(materialFlagID, materialFlag);
        configs.put(fluidStateID, fluidState);
        configs.put(oreTypeID, oreType);
        configs.put(textureIconID, textureIcon);
        configs.put(toolPartID, toolPart);
        configs.put(toolID, tool);
        configs.put(stoneLayerID, stoneLayer);
        configs.put(materialID, material);
        configs.put(oreVeinID, oreVein);
        configs.put(smallOreID, smallOre);
        configs.put(blocksInWaterID, blocksInWater);
        configs.put(randomSurfaceID, randomSurface);
        configs.put(blockTraitID, blockTrait);
        configs.put(itemTraitID, itemTrait);
        configs.put(handCraftingID, handCrafting);
        configs.put(definedStructureID, definedStructure);
    }
}
