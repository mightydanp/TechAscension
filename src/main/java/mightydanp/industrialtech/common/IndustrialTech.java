package mightydanp.industrialtech.common;

import mightydanp.industrialtech.api.client.ClientEvent;
import mightydanp.industrialtech.api.client.jsonconfig.JsonConfigClient;
import mightydanp.industrialtech.api.common.CommonEvent;
import mightydanp.industrialtech.api.common.ISidedReference;
import mightydanp.industrialtech.api.common.blocks.ITBlocks;
import mightydanp.industrialtech.api.common.crafting.recipe.Recipes;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.inventory.container.Containers;
import mightydanp.industrialtech.api.common.items.ITItems;
import mightydanp.industrialtech.api.common.jsonconfig.datapack.DataPackRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.flag.MaterialFlagRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.fluidstate.FluidStateRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.generation.orevein.OreVeinRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.icons.TextureIconRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.main.data.MainJsonConfigSingleFile;
import mightydanp.industrialtech.api.common.jsonconfig.ore.OreTypeRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.stonelayer.StoneLayerRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.jsonconfig.tool.part.ToolPartRegistry;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.ToolTypeRegistry;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.jsonconfig.material.data.MaterialRegistry;
import mightydanp.industrialtech.api.common.tileentities.TileEntities;
import mightydanp.industrialtech.api.common.material.tool.ITTools;
import mightydanp.industrialtech.api.server.DedicatedServerReference;
import mightydanp.industrialtech.client.ModClientEvent;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.crafting.recipe.ModRecipes;
import mightydanp.industrialtech.common.items.ModItems;
import mightydanp.industrialtech.common.stonelayers.ModStoneLayers;
import mightydanp.industrialtech.common.tileentities.ModTileEntities;
import mightydanp.industrialtech.common.tools.ModTools;
import mightydanp.industrialtech.common.trees.ModTrees;
import mightydanp.industrialtech.data.config.DataConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by MightyDanp on 9/26/2020.
 */
@Mod(Ref.mod_id)
public class IndustrialTech {
    public IndustrialTech INSTANCE;
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ISidedReference SIDED_SYSTEM = DistExecutor.safeRunForDist(() -> ModClientEvent::new, () -> DedicatedServerReference::new);


    public static MaterialFlagRegistry materialFlagRegistry = new MaterialFlagRegistry();
    public static FluidStateRegistry fluidStateRegistry = new FluidStateRegistry();
    public static TextureIconRegistry textureIconRegistry = new TextureIconRegistry();
    public static OreTypeRegistry oreTypeRegistry = new OreTypeRegistry();
    public static ToolPartRegistry toolPartRegistry = new ToolPartRegistry();
    public static ToolTypeRegistry toolTypeRegistry = new ToolTypeRegistry();
    public static StoneLayerRegistry stoneLayerRegistry = new StoneLayerRegistry();
    public static MaterialRegistry materialRegistryInstance = new MaterialRegistry();

    public static MainJsonConfigSingleFile mainJsonConfig = new MainJsonConfigSingleFile();
    public static ConfigSync configSync = new ConfigSync();

    public static OreVeinRegistry oreVeinRegistry = new OreVeinRegistry();

    public static DataPackRegistry dataPackRegistry = new DataPackRegistry();

    public IndustrialTech(){
        INSTANCE = this;
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus(), forge = MinecraftForge.EVENT_BUS;
        SIDED_SYSTEM.setup(modEventBus, forge);

        mainJsonConfig.initiate();

        MinecraftForge.EVENT_BUS.register(ConfigSync.class);

        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, DataConfig.SERVER_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, DataConfig.CLIENT_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, DataConfig.COMMON_SPEC);

        RegistryHandler.init(modEventBus);
        ModStoneLayers.init();

        configSync.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(JsonConfigClient::init);


        ITTools.init();
        ModTools.init();
        ITItems.init();
        ModItems.init();
        ITBlocks.init();
        ModBlocks.init();
        ITItems.initBlockItems();
        ModItems.initBlockItems();
        ModTrees.commonInit();
        Containers.init();
        TileEntities.init();
        ModTileEntities.init();

        Recipes.init();
        ModRecipes.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(CommonEvent::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModCommonEvent::init);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEvent::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModClientEvent::init);
    }
}
