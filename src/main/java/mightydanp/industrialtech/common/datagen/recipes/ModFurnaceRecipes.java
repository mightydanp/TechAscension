package mightydanp.industrialtech.common.datagen.recipes;

import mightydanp.industrialcore.common.datagen.recipes.builder.CustomCookingRecipeBuilder;
import mightydanp.industrialcore.common.datagen.recipes.GenFurnaceRecipes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;

/**
 * Created by MightyDanp on 6/6/2021.
 */
public class ModFurnaceRecipes{

    public GenFurnaceRecipes genFurnaceRecipes;

    public ModFurnaceRecipes(GenFurnaceRecipes genFurnaceRecipes){
        this.genFurnaceRecipes = genFurnaceRecipes;
        furnaceRecipes();
    }

    public static void furnaceRecipes(){

    }

    public void addFurnaceRecipe(String modID, String recipeName, Ingredient ingredients, Item result, float xp, int cookTime){
        genFurnaceRecipes.cookingRecipes.with(new ResourceLocation(modID,recipeName), CustomCookingRecipeBuilder.forItemResult(new ResourceLocation("minecraft:smelting"), ingredients, result, xp, cookTime));
    }
}
