package mightydanp.industrialtech.common;

import mightydanp.industrialtech.common.handler.IndustrialTechEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class CommonProxy {
    public static void init(){
        MinecraftForge.EVENT_BUS.register(IndustrialTechEventHandler.class);
    }


}
