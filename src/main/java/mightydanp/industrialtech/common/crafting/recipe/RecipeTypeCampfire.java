package mightydanp.industrialtech.common.crafting.recipe;

import mightydanp.industrialcore.common.libs.Ref;
import net.minecraft.world.item.crafting.RecipeType;

public class RecipeTypeCampfire implements RecipeType<CampfireOverrideRecipe> {

    @Override
    public String toString() {
        return Ref.mod_id + ":campfire";
    }
}