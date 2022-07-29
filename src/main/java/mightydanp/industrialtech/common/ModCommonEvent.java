package mightydanp.industrialtech.common;

import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialtech.common.generation.OreGeneration;
import mightydanp.industrialtech.common.generation.PlantGeneration;
import mightydanp.industrialtech.common.generation.StoneLayerGeneration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Created by MightyDanp on 9/26/2020.
 */

@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class ModCommonEvent {
    public static void init(FMLCommonSetupEvent event) {
        OreGeneration.init();
        PlantGeneration.init();
        StoneLayerGeneration.init();
    }
}
