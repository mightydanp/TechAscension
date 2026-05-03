package com.mightydanp.techascension;

import com.mightydanp.techascension.client.ref.ModRef;
import com.mightydanp.techcore.network.TCNetworkChannel;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.mightydanp.techcore.TechCore.LOGGER;

@Mod.EventBusSubscriber(modid = ModRef.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetup {

    @SubscribeEvent
    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Tech Ascension common setup is starting");

        //enqueueWork is only needed for things that aren't thread-safe
        event.enqueueWork(() ->{

        });

        TCNetworkChannel.init();

        LOGGER.info("Tech Ascension common setup is finished ");
    }
}
