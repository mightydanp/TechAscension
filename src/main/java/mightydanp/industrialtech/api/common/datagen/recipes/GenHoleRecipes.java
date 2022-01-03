package mightydanp.industrialtech.api.common.datagen.recipes;

import mightydanp.industrialtech.api.common.datagen.recipes.builder.HoleRecipeBuilder;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.common.tools.ModTools;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

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
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        for(HoleRecipeBuilder recipe : holeRecipeList){
            recipe.init();
        }

        HoleRecipeBuilder.holeRecipe(Blocks.OAK_LOG.asItem(), 100, 100).setHoleColor(1111).setResinColor(5555).requiredIngredientItem(ModTools.chisel.toolItem.get()).setConsumeIngredients(false).setIngredientItemDamage(0).setResultFluid(Fluids.WATER).setMinResult(1).setMaxResult(1).unlockedBy("item_in_inventory", InventoryChangeTrigger.Instance.hasItems(Items.STICK)).save(consumer, new ResourceLocation(Ref.mod_id, "oak_nudz"));
    }

    @Override
    public String getName() {
        return recipeName;
    }
}