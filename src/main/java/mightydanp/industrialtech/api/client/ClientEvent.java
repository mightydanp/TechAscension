package mightydanp.industrialtech.api.client;

import mightydanp.industrialtech.api.client.inventory.container.ITToolItemContainerScreen;
import mightydanp.industrialtech.api.client.models.tools.ToolModelLoader;
import mightydanp.industrialtech.api.common.handler.KeyBindingHandler;
import mightydanp.industrialtech.api.common.handler.ToolHandler;
import mightydanp.industrialtech.api.common.inventory.container.Containers;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.tileentities.TileEntities;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Created by MightyDanp on 4/7/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvent {
    public static void init(FMLClientSetupEvent event){
        ScreenManager.register(Containers.itToolItemContainer, ITToolItemContainerScreen::new);
        KeyBindingHandler.clientInit();
        TileEntities.clientInit();
    }

    @SubscribeEvent
    public static void registerModelLoaders(ModelRegistryEvent event) {
        //ModelLoaderRegistry.registerLoader(new ResourceLocation(Ref.mod_id, "tool"), ToolModelLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event){
        //ModelResourceLocation itemModelResourceLocation = ChessboardModel.modelResourceLocation;
    }
}
