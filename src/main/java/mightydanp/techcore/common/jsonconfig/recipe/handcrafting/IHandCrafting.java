package mightydanp.techcore.common.jsonconfig.recipe.handcrafting;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;

public interface IHandCrafting {
    String getName();

    Integer getInputAmount();
    NonNullList<Ingredient> getInput();

    Integer getOutputAmount();

    NonNullList<Ingredient> getOutput();
}
