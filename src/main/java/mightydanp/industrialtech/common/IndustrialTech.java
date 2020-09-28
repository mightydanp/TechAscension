package mightydanp.industrialtech.common;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.client.ClientEvent;

import mightydanp.industrialtech.common.libs.Ref;
import mightydanp.industrialtech.common.materials.ModMaterials;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
    public static final Logger LOGGER = LogManager.getLogger();

    public IndustrialTech(){
        ModMaterials.commonInit();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::common_event);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client_event);
    }

    private void common_event(final FMLCommonSetupEvent event) {
        CommonEvent.init(event);
    }

    private void client_event(final FMLClientSetupEvent event) {
        ClientEvent.init(event);
        ModMaterials.clientInit();
    }
}
