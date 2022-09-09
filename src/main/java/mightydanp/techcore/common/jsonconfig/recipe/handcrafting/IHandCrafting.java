package mightydanp.techcore.common.jsonconfig.recipe.handcrafting;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;

public interface IHandCrafting {
    String getName();

    Integer getInput1Amount();

    NonNullList<Ingredient> getInput1();

    Integer getInput2Amount();

    NonNullList<Ingredient> getInput2();

    Integer getOutputAmount();

    NonNullList<Ingredient> getOutput();
}
