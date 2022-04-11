package mightydanp.industrialtech.common.crafting.recipe;

import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypeCampfireChar  implements RecipeType<CampfireOverrideCharRecipe> {

    @Override
    public String toString() {
        return Ref.mod_id + ":campfire_char";
    }
}