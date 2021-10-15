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
    public static final IRecipeType<CampfireOverrideCharRecipe> campfireCharType = IRecipeType.register(Ref.mod_id + ":campfire_char");;
    public static RegistryObject<IRecipeSerializer<?>> campfireSerializer;
    public static RegistryObject<IRecipeSerializer<?>> campfireCharSerializer;


    public static void init() {
        campfireSerializer = RegistryHandler.RECIPE_SERIALIZER.register("campfire", CampfireOverrideRecipeSerializer::new);
        campfireCharSerializer = RegistryHandler.RECIPE_SERIALIZER.register("campfire_char", CampfireOverrideCharRecipeSerializer::new);
    }
}
