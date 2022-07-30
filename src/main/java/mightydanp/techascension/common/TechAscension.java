package mightydanp.techascension.common;

import mightydanp.techcore.client.ClientEvent;
import mightydanp.techcore.client.jsonconfig.JsonConfigClient;
import mightydanp.techcore.common.CommonEvent;
import mightydanp.techcore.common.ISidedReference;
import mightydanp.techcore.common.blocks.TCBlocks;
import mightydanp.techcore.common.crafting.recipe.Recipes;
import mightydanp.techcore.common.handler.EventHandler;
import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.inventory.container.Containers;
import mightydanp.techcore.common.items.TCItems;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.material.tool.TCToolHandler;
import mightydanp.techapi.common.jsonconfig.sync.gui.screen.main.MainJsonConfig;
import mightydanp.techapi.common.resources.asset.TAAssetHolder;
import mightydanp.techapi.common.resources.data.TADataHolder;
import mightydanp.techapi.common.resources.ResourcePackEventHandler;
import mightydanp.techapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.tileentities.TileEntities;
import mightydanp.techcore.common.material.tool.TCTools;
import mightydanp.techcore.server.DedicatedServerReference;
import mightydanp.techcore.server.client.ModClientEvent;
import mightydanp.techascension.common.blocks.ModBlocks;
import mightydanp.techascension.common.crafting.recipe.ModRecipes;
import mightydanp.techascension.common.items.ModItems;
import mightydanp.techascension.common.materials.ModMaterials;
import mightydanp.techascension.common.tileentities.ModBlockEntity;
import mightydanp.techascension.common.tool.ModTools;
import mightydanp.techascension.common.trees.ModTrees;
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
public class TechAscension {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final ISidedReference SIDED_SYSTEM = DistExecutor.safeRunForDist(() -> ModClientEvent::new, () -> DedicatedServerReference::new);

    public static MainJsonConfig mainJsonConfig = new MainJsonConfig();

    public static TAAssetHolder assetHolder = new TAAssetHolder();
    public static TADataHolder dataHolder = new TADataHolder();

    public TechAscension(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus(), forge = MinecraftForge.EVENT_BUS;
        bus.register(this);
        SIDED_SYSTEM.setup(bus, forge);

        RegistryHandler.init(bus);

        mainJsonConfig.initiate();

        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        ModTools.init();
        TCTools.init();

        ModMaterials.commonInit();

        TCJsonConfigs.init();
        ConfigSync.init();
        
        TCItems.init();
        ModItems.init();
        TCBlocks.init();
        ModBlocks.init();
        TCItems.initBlockItems();
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
        MinecraftForge.EVENT_BUS.register(TCToolHandler.class);

        bus.addListener(ResourcePackEventHandler::addResourcePack);
    }
}
