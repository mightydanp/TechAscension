package mightydanp.techascension.common.crafting.recipe;

import mightydanp.techcore.common.libs.Ref;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypeCampfireChar  implements RecipeType<CampfireOverrideCharRecipe> {

    @Override
    public String toString() {
        return Ref.mod_id + ":campfire_char";
    }
}