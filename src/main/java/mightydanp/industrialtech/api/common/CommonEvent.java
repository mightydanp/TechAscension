package mightydanp.industrialtech.api.common;

import mightydanp.industrialtech.api.common.crafting.recipe.Recipes;
import mightydanp.industrialtech.api.common.handler.NetworkHandler;
import mightydanp.industrialtech.api.common.inventory.container.ITToolItemContainer;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.crafting.recipe.ModRecipes;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Created by MightyDanp on 4/7/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class CommonEvent {

    public static MenuType<ITToolItemContainer> itToolItemContainerContainerType;

    public static void init(FMLCommonSetupEvent event) {
        NetworkHandler.onCommonSetup();
    }
}
