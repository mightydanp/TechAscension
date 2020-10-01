package mightydanp.industrialtech.common.handler;

import mightydanp.industrialtech.api.common.generation.OreGeneration;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Created by MightyDanp on 9/30/2020.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    public void init(){

    }

    @SubscribeEvent(priority= EventPriority.HIGH)
    public static void onBiomeLoading(BiomeLoadingEvent evt)
    {
        if (!OreGeneration.checkAndInitBiome(evt)) return;

        if (evt.getCategory() == Biome.Category.NETHER)
        {
            OreGeneration.initNetherFeatures(evt);
        }
        else {
            OreGeneration.initOverworldFeatures(evt);
        }
    }
}
