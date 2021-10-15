package mightydanp.industrialtech.api.common.datagen.recipes;

import mightydanp.industrialtech.api.common.datagen.recipes.builder.HoleRecipeBuilder;
import mightydanp.industrialtech.api.common.datagen.JsonDataProvider;
import mightydanp.industrialtech.common.tools.ModTools;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;

import java.util.function.Consumer;

/**
 * Created by MightyDanp on 10/14/2021.
 */
public class GenHoleRecipes extends RecipeProvider {
    public String modID;
    public String recipeName;

    public GenHoleRecipes(DataGenerator generator, String modID, String recipeNameIn){
        super(generator);
        this.modID = modID;
        this.recipeName = recipeNameIn;
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        HoleRecipeBuilder.holeRecipe(Blocks.OAK_LOG.asItem(), 1, 1)
                .setHoleColor(1111).setResinColor(5555).requiredIngredientItem(ModTools.chisel.toolItem.get()).setConsumeIngredients(false).setIngredientItemDamage(0).setResult(Blocks.DIRT.asItem()).setMinResult(1).setMaxResult(1).unlockedBy("item_in_inventory", InventoryChangeTrigger.Instance.hasItems(Items.STICK)).save(consumer);
    }

    @Override
    public String getName() {
        return recipeName;
    }

}