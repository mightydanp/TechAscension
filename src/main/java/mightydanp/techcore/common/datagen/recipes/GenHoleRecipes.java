package mightydanp.techcore.common.datagen.recipes;

import mightydanp.techcore.common.datagen.recipes.builder.HoleRecipeBuilder;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techascension.common.tool.ModTools;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by MightyDanp on 10/14/2021.
 */
public class GenHoleRecipes extends RecipeProvider {
    public String modID;
    public String recipeName;
    public static List<HoleRecipeBuilder> holeRecipeList = new ArrayList<HoleRecipeBuilder>();

    public GenHoleRecipes(DataGenerator generator, String modID, String recipeNameIn){
        super(generator);
        this.modID = modID;
        this.recipeName = recipeNameIn;
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer){
        for(HoleRecipeBuilder recipe : holeRecipeList){
            recipe.init();
        }

        HoleRecipeBuilder.holeRecipe(Blocks.OAK_LOG.asItem(), 100, 100).setHoleColor(1111).setResinColor(5555).requiredIngredientItem(ModTools.chisel.toolItem.get()).setConsumeIngredients(false).setIngredientItemDamage(0).setResultFluid(Fluids.WATER).setMinResult(1).setMaxResult(1).unlockedBy("item_in_inventory", InventoryChangeTrigger.TriggerInstance.hasItems(Items.STICK)).save(consumer, new ResourceLocation(Ref.mod_id, "oak_nudz"));
    }

    @Override
    public String getName() {
        return recipeName;
    }
}