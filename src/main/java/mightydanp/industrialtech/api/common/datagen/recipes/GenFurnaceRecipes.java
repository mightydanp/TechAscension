package mightydanp.industrialtech.api.common.datagen.recipes;

import mightydanp.industrialtech.api.common.datagen.recipes.builder.CustomCookingRecipeBuilder;
import mightydanp.industrialtech.api.common.datagen.JsonDataProvider;
import mightydanp.industrialtech.common.datagen.ModFurnaceRecipes;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

/**
 * Created by MightyDanp on 6/6/2021.
 */
public class GenFurnaceRecipes {
    public JsonDataProvider<CustomCookingRecipeBuilder> cookingRecipes;
    public String modID;
    public String recipesName;

    public GenFurnaceRecipes(DataGenerator generator, String modID, String recipesName){
        cookingRecipes = new JsonDataProvider<>(generator, JsonDataProvider.ResourceType.DATA, "recipes", CustomCookingRecipeBuilder.CODEC);
        this.modID = modID;
        this.recipesName = recipesName;
        furnaceRecipes();
        new ModFurnaceRecipes(this);
    }

    public void furnaceRecipes(){
        //addFurnaceRecipe("test", Ingredient.of(Items.DIRT), Items.BAKED_POTATO, 0.35F, 200);
    }

    public void addFurnaceRecipe(String recipeName, Ingredient ingredients, Item result, float xp, int cookTime){
        cookingRecipes.with(new ResourceLocation(modID,recipeName), CustomCookingRecipeBuilder.forItemResult(new ResourceLocation("minecraft:smelting"), ingredients, result, xp, cookTime));
    }
}
