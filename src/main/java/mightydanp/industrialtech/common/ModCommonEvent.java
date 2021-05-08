package mightydanp.industrialtech.common;

import mightydanp.industrialtech.api.common.handler.ToolHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.tools.ModTools;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;

/**
 * Created by MightyDanp on 9/26/2020.
 */

@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class ModCommonEvent {
    public static void init(FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public static void onItemRightClickEvent(final PlayerInteractEvent.RightClickItem event) {
        ModTools.handCraftingInit(event);
    }
}
