package mightydanp.techcore.common.datagen.recipes;

import com.mojang.datafixers.util.Either;
import mightydanp.techcore.common.datagen.recipes.builder.CustomCookingRecipeBuilder;
import mightydanp.techcore.common.datagen.JsonDataProvider;
import mightydanp.techascension.common.datagen.recipes.ModFurnaceRecipes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by MightyDanp on 6/6/2021.
 */
public class GenFurnaceRecipes extends RecipeProvider {
    public JsonDataProvider<CustomCookingRecipeBuilder> cookingRecipes;
    public String modID;
    public String recipesName;

    public List<CustomCookingRecipeBuilder> customCookingRecipeList = new ArrayList<>();


    public GenFurnaceRecipes(DataGenerator generator, String modID, String recipesName){
        super(generator);
        cookingRecipes = new JsonDataProvider<>(generator, JsonDataProvider.ResourceType.DATA, "recipes", CustomCookingRecipeBuilder.CODEC);
        this.modID = modID;
        this.recipesName = recipesName;
        furnaceRecipes();
        new ModFurnaceRecipes(this);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
    }

    public void furnaceRecipes(){
        //addFurnaceRecipe("test", Ingredient.of(Items.DIRT), Items.BAKED_POTATO, 0.35F, 200);
    }

    public void addFurnaceRecipe(String recipeName, Ingredient ingredients, Either<Item, ItemStack> result, float xp, int cookTime){
        cookingRecipes.with(new ResourceLocation(modID,recipeName),  result.left().isPresent() ? CustomCookingRecipeBuilder.forItemResult(new ResourceLocation("minecraft:smelting") , ingredients, result.left().get(), xp, cookTime) : result.right().isPresent() ? CustomCookingRecipeBuilder.forItemStackResult(new ResourceLocation("minecraft:smelting") , ingredients, result.right().get(), xp, cookTime) : null);
    }
}
