package mightydanp.industrialtech.api.common;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Created by MightyDanp on 8/24/2020.
 */
public class CommonProxy {
    public static void init(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new RegistryHandler());
    }
}