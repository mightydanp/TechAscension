package mightydanp.techascension.common.crafting.recipe;

import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 6/6/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id, bus=Mod.EventBusSubscriber.Bus.MOD)
public class ModRecipes{
    //RecipeType
    public static RecipeType<CampfireOverrideRecipe> campfireType;
    public static RecipeType<CampfireOverrideCharRecipe> campfireCharType;
    public static RegistryObject<RecipeSerializer<?>> campfireSerializer = RegistryHandler.RECIPE_SERIALIZER.register("campfire", CampfireOverrideRecipeSerializer::new);
    public static RegistryObject<RecipeSerializer<?>> campfireCharSerializer = RegistryHandler.RECIPE_SERIALIZER.register("campfire_char", CampfireOverrideCharRecipeSerializer::new);

    @SubscribeEvent
    public static void registerRecipeType(RegistryEvent.Register<Block> event) {
        campfireType = RecipeType.register(Ref.mod_id + ":campfire");
        campfireCharType = RecipeType.register(Ref.mod_id + ":campfire_char");
    }
}
