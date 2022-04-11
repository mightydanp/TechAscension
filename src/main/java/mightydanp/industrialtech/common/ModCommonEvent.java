package mightydanp.industrialtech.common;

import mightydanp.industrialtech.api.common.crafting.recipe.Recipes;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.crafting.recipe.ModRecipes;
import mightydanp.industrialtech.common.generation.OreGeneration;
import mightydanp.industrialtech.common.generation.PlantGeneration;
import mightydanp.industrialtech.common.generation.StoneLayerGeneration;
import mightydanp.industrialtech.common.tool.ModTools;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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

    @SubscribeEvent
    public static void onItemRightClickEvent(final PlayerInteractEvent.RightClickItem event) {
        ModTools.handCraftingInit(event);
    }
}
