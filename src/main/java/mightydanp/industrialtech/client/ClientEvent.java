package mightydanp.industrialtech.client;

import mightydanp.industrialtech.api.client.gui.screen.CustomPlayerInventoryScreen;
import mightydanp.industrialtech.api.common.inventory.container.ModContainers;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class ClientEvent {
    public static void init(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ModContainers.customInventoryContainer.get(), CustomPlayerInventoryScreen::new);
    }
}
