package mightydanp.industrialtech.api.common.crafting.recipe;

import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypeHole implements RecipeType<HoleRecipe> {

    @Override
    public String toString() {
        return Ref.mod_id + ":hole";
    }
}
