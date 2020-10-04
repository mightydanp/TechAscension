package mightydanp.industrialtech.common;

import mightydanp.industrialtech.api.common.handler.OreGenerationHandler;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.client.ClientEvent;

import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
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

    public IndustrialTech(){
        INSTANCE = this;
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        RegistryHandler.init(modEventBus);
        ModMaterials.commonInit();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::common_event);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client_event);
    }

    private void common_event(final FMLCommonSetupEvent event) {
        CommonEvent.init(event);
        OreGenerationHandler.init();
    }

    private void client_event(final FMLClientSetupEvent event) {
        ClientEvent.init(event);
        ModMaterials.clientInit();
    }
}
