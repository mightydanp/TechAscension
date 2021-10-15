package mightydanp.industrialtech.api.common.crafting.recipe;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;

/**
 * Created by MightyDanp on 10/10/2021.
 */
public class Recipes {
    public static final IRecipeType<HoleRecipe> holeType = IRecipeType.register(Ref.mod_id + ":hole");
    public static RegistryObject<IRecipeSerializer<?>> holeSerializer;


    public static void init() {
        holeSerializer = RegistryHandler.RECIPE_SERIALIZER.register("hole", HoleRecipeSerializer::new);
    }
}
