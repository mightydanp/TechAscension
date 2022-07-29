package mightydanp.techascension.common.datagen.recipes;

import mightydanp.techcore.common.datagen.recipes.builder.CustomCookingRecipeBuilder;
import mightydanp.techcore.common.datagen.JsonDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;

/**
 * Created by MightyDanp on 6/6/2021.
 */
public class ModGenCampfireRecipes {
    public JsonDataProvider<CustomCookingRecipeBuilder> campfireRecipes;
    public String modID;
    public String recipesName;


    public ModGenCampfireRecipes(DataGenerator generator, String modID, String recipesName){
        campfireRecipes = new JsonDataProvider<>(generator, JsonDataProvider.ResourceType.DATA, "recipes", CustomCookingRecipeBuilder.CODEC);
        this.modID = modID;
        this.recipesName = recipesName;
        campfireRecipes();
    }

    public void campfireRecipes(){
        addCampfireRecipe("test", Ingredient.of(Items.DIRT), Items.BAKED_POTATO, 0.35F, 200);
    }

    public void addCampfireRecipe(String recipeName, Ingredient ingredients, Item result, float xp, int cookTime){
        campfireRecipes.with(new ResourceLocation(modID,recipeName), CustomCookingRecipeBuilder.forItemResult(new ResourceLocation(modID, recipesName), ingredients, result, xp, cookTime));
    }
}