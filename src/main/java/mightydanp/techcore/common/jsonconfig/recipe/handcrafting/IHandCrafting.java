package mightydanp.techcore.common.jsonconfig.recipe.handcrafting;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;

public interface IHandCrafting {
    String getName();
    NonNullList<Ingredient> getInput();
    NonNullList<Ingredient> getOutput();
}
