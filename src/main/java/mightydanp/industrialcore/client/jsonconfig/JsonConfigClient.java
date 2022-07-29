package mightydanp.industrialcore.client.jsonconfig;

import mightydanp.industrialapi.common.jsonconfig.sync.ConfigSync;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Created by MightyDanp on 1/16/2022.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JsonConfigClient {
    public static void init(FMLClientSetupEvent event){
        ConfigSync.initClient();
    }
}
