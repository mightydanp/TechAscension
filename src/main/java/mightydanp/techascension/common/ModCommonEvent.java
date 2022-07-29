package mightydanp.techascension.common;

import mightydanp.techcore.common.libs.Ref;
import mightydanp.techascension.common.generation.OreGeneration;
import mightydanp.techascension.common.generation.PlantGeneration;
import mightydanp.techascension.common.generation.StoneLayerGeneration;
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
