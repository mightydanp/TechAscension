package mightydanp.techcore.common.jsonconfig;

import com.mojang.datafixers.util.Pair;
import mightydanp.techapi.common.jsonconfig.JsonConfigMultiFile;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techapi.common.jsonconfig.sync.JsonConfigServer;
import mightydanp.techcore.common.jsonconfig.flag.MaterialFlagRegistry;
import mightydanp.techcore.common.jsonconfig.flag.MaterialFlagServer;
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
import mightydanp.techcore.common.jsonconfig.stonelayer.StoneLayerRegistry;
import mightydanp.techcore.common.jsonconfig.stonelayer.StoneLayerServer;
import mightydanp.techcore.common.jsonconfig.tool.ToolRegistry;
import mightydanp.techcore.common.jsonconfig.tool.ToolServer;
import mightydanp.techcore.common.jsonconfig.tool.part.ToolPartRegistry;
import mightydanp.techcore.common.jsonconfig.tool.part.ToolPartServer;
import mightydanp.techcore.common.jsonconfig.tool.type.ToolTypeRegistry;
import mightydanp.techcore.common.jsonconfig.tool.type.ToolTypeServer;

public class TCJsonConfigs extends ConfigSync {
    public static int materialFlagID = 0;
    public static int fluidStateID = 1;
    public static int oreTypeID = 2;
    public static int textureIconID = 3;
    public static int toolPartID = 4;
    public static int toolTypeID = 5;
    public static int stoneLayerID = 6;
    public static int toolID = 7;
    public static int materialID = 8;
    public static int oreVeinID = 9;
    public static int smallOreID = 10;
    public static int blocksInWaterID = 11;
    public static int randomSurfaceID = 12;


    public static Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> materialFlag = configs.getOrDefault(materialFlagID, new Pair<>(new MaterialFlagRegistry(),  new MaterialFlagServer()));
    public static Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>> fluidState = configs.getOrDefault(fluidStateID, new Pair<>(new FluidStateRegistry(),  new FluidStateServer()));
    public static Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  oreType = configs.getOrDefault(oreTypeID, new Pair<>(new OreTypeRegistry(),  new OreTypeServer()));
    public static Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  textureIcon = configs.getOrDefault(textureIconID, new Pair<>(new TextureIconRegistry(),  new TextureIconServer()));
    public static Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  toolPart = configs.getOrDefault(toolPartID, new Pair<>(new ToolPartRegistry(),  new ToolPartServer()));
    public static Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  toolType = configs.getOrDefault(toolTypeID, new Pair<>(new ToolTypeRegistry(),  new ToolTypeServer()));
    public static Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  stoneLayer = configs.getOrDefault(stoneLayerID, new Pair<>(new StoneLayerRegistry(),  new StoneLayerServer()));
    public static Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  tool = configs.getOrDefault(toolID, new Pair<>(new ToolRegistry(),  new ToolServer()));
    public static Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  material = configs.getOrDefault(materialID, new Pair<>(new MaterialRegistry(),  new MaterialServer()));
    public static Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  oreVein = configs.getOrDefault(oreVeinID, new Pair<>(new OreVeinRegistry(),  new OreVeinServer()));
    public static Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  smallOre = configs.getOrDefault(smallOreID, new Pair<>(new SmallOreVeinRegistry(),  new SmallOreVeinServer()));
    public static Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  blocksInWater = configs.getOrDefault(blocksInWaterID, new Pair<>(new BlocksInWaterRegistry(),  new BlocksInWaterServer()));
    public static Pair<? extends JsonConfigMultiFile<?>, ? extends JsonConfigServer<?>>  randomSurface = configs.getOrDefault(randomSurfaceID, new Pair<>(new RandomSurfaceRegistry(),  new RandomSurfaceServer()));

    public static void init() {
        configs.put(materialFlagID, materialFlag);
        configs.put(fluidStateID, fluidState);
        configs.put(oreTypeID, oreType);
        configs.put(textureIconID, textureIcon);
        configs.put(toolPartID, toolPart);
        configs.put(toolTypeID, toolType);
        configs.put(stoneLayerID, stoneLayer);
        configs.put(toolID, tool);
        configs.put(materialID, material);
        configs.put(oreVeinID, oreVein);
        configs.put(smallOreID, smallOre);
        configs.put(blocksInWaterID, blocksInWater);
        configs.put(randomSurfaceID, randomSurface);
    }
}
