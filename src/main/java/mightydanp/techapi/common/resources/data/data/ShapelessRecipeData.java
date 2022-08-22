package mightydanp.techapi.common.resources.data.data;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.function.Consumer;

public class ShapelessRecipeData {
    private final ResourceLocation name;
    private ShapelessRecipeBuilder.Result shapelessRecipe;
    
    public ShapelessRecipeData(ResourceLocation name) {
        this.name = name;
    }
    
    public void createShapelessRecipe(ResourceLocation id, Item result, int count, String group, List<Ingredient> ingredients, Advancement.Builder advancement) {
        if (advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
        advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
        shapelessRecipe = new ShapelessRecipeBuilder.Result(id, result, count, group == null ? "" : group, ingredients, advancement, new ResourceLocation(id.getNamespace(), "recipes/" + result.getItemCategory().getRecipeFolderName() + "/" + id.getPath()));
    }

    public ResourceLocation getName() {
        return name;
    }

    public JsonObject createJson(){
        return shapelessRecipe.serializeRecipe();
    }
}
