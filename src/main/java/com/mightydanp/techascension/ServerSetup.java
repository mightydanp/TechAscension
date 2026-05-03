package com.mightydanp.techascension;

import com.mightydanp.techascension.client.ref.ModRef;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

import static com.mightydanp.techcore.TechCore.LOGGER;


@Mod.EventBusSubscriber(modid = ModRef.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.DEDICATED_SERVER)
public class ServerSetup {

    @SubscribeEvent
    public static void onServerSetup(final FMLDedicatedServerSetupEvent event) {
        LOGGER.info("Tech Ascension server setup is starting");

        //enqueueWork is only needed for things that aren't thread-safe
        event.enqueueWork(() ->{

        });

        LOGGER.info("Tech Ascension server setup is finished ");
    }
}
