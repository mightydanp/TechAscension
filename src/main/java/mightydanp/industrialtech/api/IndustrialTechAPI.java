package mightydanp.industrialtech.api;

import mightydanp.industrialtech.api.client.ClientProxy;
import mightydanp.industrialtech.api.common.CommonProxy;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * Created by MightyDanp on 9/26/2020.
 */
@Mod(Ref.mod_id)
public class IndustrialTechAPI {
    public static final Logger LOGGER = LogManager.getLogger();

    public IndustrialTechAPI(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::common_proxy);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::client_proxy);
        RegistryHandler.getBlocks().register(FMLJavaModLoadingContext.get().getModEventBus());
        RegistryHandler.getItems().register(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void common_proxy(final FMLCommonSetupEvent event) {
        CommonProxy.init(event);
    }

    private void client_proxy(final FMLClientSetupEvent event) {
        ClientProxy.init(event);
    }
}
