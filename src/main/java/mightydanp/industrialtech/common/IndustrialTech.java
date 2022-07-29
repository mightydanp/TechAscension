package mightydanp.industrialtech.common;

import mightydanp.industrialcore.client.ClientEvent;
import mightydanp.industrialcore.client.jsonconfig.JsonConfigClient;
import mightydanp.industrialcore.common.CommonEvent;
import mightydanp.industrialcore.common.ISidedReference;
import mightydanp.industrialcore.common.blocks.ITBlocks;
import mightydanp.industrialcore.common.crafting.recipe.Recipes;
import mightydanp.industrialcore.common.handler.EventHandler;
import mightydanp.industrialcore.common.handler.RegistryHandler;
import mightydanp.industrialcore.common.inventory.container.Containers;
import mightydanp.industrialcore.common.items.ITItems;
import mightydanp.industrialcore.common.jsonconfig.ICJsonConfigs;
import mightydanp.industrialcore.common.material.tool.ITToolHandler;
import mightydanp.industrialapi.common.jsonconfig.main.MainJsonConfig;
import mightydanp.industrialapi.common.resources.asset.ITAssetHolder;
import mightydanp.industrialapi.common.resources.data.ITDataHolder;
import mightydanp.industrialapi.common.resources.ResourcePackEventHandler;
import mightydanp.industrialapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialcore.common.tileentities.TileEntities;
import mightydanp.industrialcore.common.material.tool.ITTools;
import mightydanp.industrialcore.server.DedicatedServerReference;
import mightydanp.industrialcore.server.client.ModClientEvent;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.crafting.recipe.ModRecipes;
import mightydanp.industrialtech.common.items.ModItems;
import mightydanp.industrialtech.common.materials.ModMaterials;
import mightydanp.industrialtech.common.tileentities.ModBlockEntity;
import mightydanp.industrialtech.common.tool.ModTools;
import mightydanp.industrialtech.common.trees.ModTrees;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
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

    public static MainJsonConfig mainJsonConfig = new MainJsonConfig();

    public static ITAssetHolder assetHolder = new ITAssetHolder();
    public static ITDataHolder dataHolder = new ITDataHolder();

    public IndustrialTech(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus(), forge = MinecraftForge.EVENT_BUS;
        bus.register(this);
        SIDED_SYSTEM.setup(bus, forge);

        RegistryHandler.init(bus);

        mainJsonConfig.initiate();

        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        ModTools.init();
        ITTools.init();

        ModMaterials.commonInit();

        ICJsonConfigs.init();
        ConfigSync.init();
        
        ITItems.init();
        ModItems.init();
        ITBlocks.init();
        ModBlocks.init();
        ITItems.initBlockItems();
        ModItems.initBlockItems();
        ModTrees.commonInit();
        Containers.init();
        ModBlockEntity.init();
        TileEntities.init();

        bus.addListener(JsonConfigClient::init);
        bus.addListener(CommonEvent::init);
        bus.addListener(ModCommonEvent::init);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientEvent::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModClientEvent::init);

        MinecraftForge.EVENT_BUS.register(ConfigSync.class);
        MinecraftForge.EVENT_BUS.register(Recipes.class);
        MinecraftForge.EVENT_BUS.register(ModRecipes.class);
        MinecraftForge.EVENT_BUS.register(EventHandler.class);
        MinecraftForge.EVENT_BUS.register(ITToolHandler.class);

        bus.addListener(ResourcePackEventHandler::addResourcePack);
    }
}
