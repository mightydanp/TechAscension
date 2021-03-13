package mightydanp.industrialtech.common;

import mightydanp.industrialtech.api.common.ISidedReference;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.items.ModItemGroups;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.server.DedicatedServerReference;
import mightydanp.industrialtech.client.ClientEvent;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.generation.OreGeneration;
import mightydanp.industrialtech.common.generation.PlantGeneration;
import mightydanp.industrialtech.common.items.ModItems;
import mightydanp.industrialtech.common.materials.ModMaterials;
import mightydanp.industrialtech.data.config.DataConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
    public static final ISidedReference SIDED_SYSTEM = DistExecutor.safeRunForDist(() -> ClientEvent::new, () -> DedicatedServerReference::new);

    public IndustrialTech(){
        INSTANCE = this;
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus(), forge = MinecraftForge.EVENT_BUS;
        SIDED_SYSTEM.setup(modEventBus, forge);
        RegistryHandler.init(modEventBus);
        ModMaterials.commonInit();
        ModItems.init();
        ModBlocks.init();
        ModItems.initBlockItems();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::common_event);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client_event);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, DataConfig.SERVER_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, DataConfig.CLIENT_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, DataConfig.COMMON_SPEC);
    }

    private void common_event(final FMLCommonSetupEvent event) {
        CommonEvent.init(event);
        OreGeneration.init();
        PlantGeneration.init();
    }

    private void client_event(final FMLClientSetupEvent event) {
        ClientEvent.init(event);
        ModMaterials.clientInit();
    }

}
