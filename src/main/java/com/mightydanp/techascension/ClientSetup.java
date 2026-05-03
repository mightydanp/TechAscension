package com.mightydanp.techascension;

import com.mightydanp.techascension.client.ref.ModRef;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.mightydanp.techcore.TechCore.LOGGER;

@Mod.EventBusSubscriber(modid = ModRef.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("Tech Ascension client setup is starting");

        //enqueueWork is only needed for things that aren't thread-safe
        event.enqueueWork(() -> {

        });

        LOGGER.info("Tech Ascension client setup has finished");
    }
}
