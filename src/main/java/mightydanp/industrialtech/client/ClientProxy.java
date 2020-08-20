package mightydanp.industrialtech.client;

import mightydanp.industrialtech.common.blocks.BlockCasingMachine;
import mightydanp.industrialtech.common.data.IndustrialTechData;
import muramasa.antimatter.AntimatterAPI;
import muramasa.antimatter.gui.container.ContainerMachine;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy {

    public static void setup(FMLClientSetupEvent e) {
        AntimatterAPI.runLaterClient(() -> {
            RenderTypeLookup.setRenderLayer(IndustrialTechData.RUBBER_SAPLING, RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(IndustrialTechData.RUBBER_LEAVES, RenderType.getCutout());
            //ScreenManager.registerFactory(StartupCommon.containerTypeContainerFurnace, ContainerMachine::new);
            AntimatterAPI.all(BlockCasingMachine.class, b -> RenderTypeLookup.setRenderLayer(b, RenderType.getCutout()));
        });
    }
}
