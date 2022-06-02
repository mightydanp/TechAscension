package mightydanp.industrialcore.common.crafting.recipe;

import mightydanp.industrialcore.common.libs.Ref;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypeHole implements RecipeType<HoleRecipe> {

    @Override
    public String toString() {
        return Ref.mod_id + ":hole";
    }
}
