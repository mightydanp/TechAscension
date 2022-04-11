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
import mightydanp.industrialtech.api.common.jsonconfig.main.data.MainJsonConfigSingleFile;
import mightydanp.industrialtech.api.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.tileentities.TileEntities;
import mightydanp.industrialtech.api.common.material.tool.ITTools;
import mightydanp.industrialtech.api.server.DedicatedServerReference;
import mightydanp.industrialtech.client.ModClientEvent;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.crafting.recipe.ModRecipes;
import mightydanp.industrialtech.common.items.ModItems;
import mightydanp.industrialtech.common.tileentities.ModBlockEntity;
import mightydanp.industrialtech.common.tool.ModTools;
import mightydanp.industrialtech.common.trees.ModTrees;
import mightydanp.industrialtech.data.config.DataConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by MightyDanp on 9/26/2020.
 */
@Mod(Ref.mod_id)
public class IndustrialTech {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ISidedReference SIDED_SYSTEM = DistExecutor.safeRunForDist(() -> ModClientEvent::new, () -> DedicatedServerReference::new);

    public static MainJsonConfigSingleFile mainJsonConfig = new MainJsonConfigSingleFile();
    public static ConfigSync configSync = new ConfigSync();

    public static DataPackRegistry dataPackRegistry = new DataPackRegistry();

    public IndustrialTech(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus(), forge = MinecraftForge.EVENT_BUS;
        bus.register(this);
        SIDED_SYSTEM.setup(bus, forge);

        RegistryHandler.init(bus);

        mainJsonConfig.initiate();

        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, DataConfig.SERVER_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, DataConfig.CLIENT_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, DataConfig.COMMON_SPEC);

        configSync.init();
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
        ModBlockEntity.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(JsonConfigClient::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(CommonEvent::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModCommonEvent::init);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEvent::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModClientEvent::init);

        MinecraftForge.EVENT_BUS.register(ConfigSync.class);
        bus.addListener(this::event);
    }

    public void event(FMLCommonSetupEvent event){
        event.enqueueWork(Recipes::init);

        event.enqueueWork(ModRecipes::init);
    }
}
