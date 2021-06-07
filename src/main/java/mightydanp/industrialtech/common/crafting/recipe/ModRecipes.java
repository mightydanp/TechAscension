package mightydanp.industrialtech.common.crafting.recipe;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;

/**
 * Created by MightyDanp on 6/6/2021.
 */
public class ModRecipes{
    //RecipeType
    public static final IRecipeType<CampfireOverrideRecipe> campfireType = IRecipeType.register(Ref.mod_id + ":campfire");

    //public CookingRecipeSerializer<CampfireCookingRecipe> CAMPFIRE_COOKING_RECIPE = RegistryHandler.RECIPE_SERIALIZER.register("campfire_cooking", new CookingRecipeSerializer<>(CampFireOverrideRecipe::new, 100));

    public static RegistryObject<IRecipeSerializer<?>> CampfireSerializer;

    public static void init() {
        CampfireSerializer = RegistryHandler.RECIPE_SERIALIZER.register("campfire", CampfireOverrideRecipeSerializer::new);
    }
}
