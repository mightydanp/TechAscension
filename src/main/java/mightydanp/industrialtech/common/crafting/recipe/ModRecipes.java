package mightydanp.industrialtech.common.crafting.recipe;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 6/6/2021.
 */
public class ModRecipes{
    //RecipeType
    public static final RecipeType<CampfireOverrideRecipe> campfireType = RecipeType.register(Ref.mod_id + ":campfire");
    public static final RecipeType<CampfireOverrideCharRecipe> campfireCharType = RecipeType.register(Ref.mod_id + ":campfire_char");;
    public static RegistryObject<RecipeSerializer<?>> campfireSerializer;
    public static RegistryObject<RecipeSerializer<?>> campfireCharSerializer;


    public static void init() {
        campfireSerializer = RegistryHandler.RECIPE_SERIALIZER.register("campfire", CampfireOverrideRecipeSerializer::new);
        campfireCharSerializer = RegistryHandler.RECIPE_SERIALIZER.register("campfire_char", CampfireOverrideCharRecipeSerializer::new);
    }
}
