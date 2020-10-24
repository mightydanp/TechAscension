package mightydanp.industrialtech.common;

import mightydanp.industrialtech.api.common.packet.PacketHandler;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class CommonEvent {
    public static void init(FMLCommonSetupEvent event) {
        PacketHandler.registerMessages();
    }
}
