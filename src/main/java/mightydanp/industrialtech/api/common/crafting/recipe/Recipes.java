package mightydanp.industrialtech.api.common.crafting.recipe;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 10/10/2021.
 */
public class Recipes {
    public static RecipeType<HoleRecipe> holeType = new RecipeTypeHole();
    public static RegistryObject<RecipeSerializer<?>> holeSerializer;


    public static void init() {
        holeType = Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(Ref.mod_id, "hole"), holeType);
        //holeType = RecipeType.register(Ref.mod_id + ":hole");
        holeSerializer = RegistryHandler.RECIPE_SERIALIZER.register("hole", HoleRecipeSerializer::new);
    }
}
