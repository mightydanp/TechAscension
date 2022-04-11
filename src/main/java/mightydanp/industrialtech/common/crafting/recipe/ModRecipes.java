package mightydanp.industrialtech.common.crafting.recipe;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 6/6/2021.
 */
public class ModRecipes{
    //RecipeType
    public static RecipeType<CampfireOverrideRecipe> campfireType = new RecipeTypeCampfire();
    public static RecipeType<CampfireOverrideCharRecipe> campfireCharType = new RecipeTypeCampfireChar();
    public static RegistryObject<RecipeSerializer<?>> campfireSerializer;
    public static RegistryObject<RecipeSerializer<?>> campfireCharSerializer;


    public static void init() {
        campfireType = Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(Ref.mod_id, "campfire"), campfireType);
        campfireCharType = Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(Ref.mod_id, "campfire_char"), campfireCharType);
        //campfireType = RecipeType.register(Ref.mod_id + ":campfire");
        //campfireCharType = RecipeType.register(Ref.mod_id + ":campfire_char");
        campfireSerializer = RegistryHandler.RECIPE_SERIALIZER.register("campfire", CampfireOverrideRecipeSerializer::new);
        campfireCharSerializer = RegistryHandler.RECIPE_SERIALIZER.register("campfire_char", CampfireOverrideCharRecipeSerializer::new);
    }
}
