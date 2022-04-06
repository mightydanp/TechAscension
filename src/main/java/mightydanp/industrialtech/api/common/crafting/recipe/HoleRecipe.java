package mightydanp.industrialtech.api.common.crafting.recipe;

import mightydanp.industrialtech.api.common.blocks.ITBlocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 10/9/2021.
 */
public class HoleRecipe implements Recipe<Container> {
    protected final ResourceLocation id;
    protected final String group;
    protected final ItemStack desiredBlock;
    private final NonNullList<Ingredient> ingredientItems;
    protected final int ingredientItemDamage;
    protected final boolean consumeIngredients;
    protected final int minTicks;
    protected final int maxTicks;
    protected final ItemStack result;
    protected Fluid resultFluid;
    protected final int minResult;
    protected final int maxResult;
    protected final int holeColor;
    protected final int resinColor;

    public HoleRecipe(ResourceLocation resourceLocationIn, String groupIn, ItemStack desiredBlockIn, NonNullList<Ingredient> ingredientItemsIn, int ingredientItemDamageIn, boolean consumeIngredientsIn, int minTicksIn, int maxTicksIn, @Nullable ItemStack resultIn, @Nullable Fluid resultFluidIn, int minResultIn, int maxResultIn, int holeColorIn, int resinColorIn) {
        id = resourceLocationIn;
        group = groupIn;
        desiredBlock = desiredBlockIn;
        ingredientItems = ingredientItemsIn;
        ingredientItemDamage = ingredientItemDamageIn;
        consumeIngredients = consumeIngredientsIn;
        minTicks = minTicksIn;
        maxTicks = maxTicksIn;
        if(resultIn != null) {
            result = resultIn;
        }else{
            result = ItemStack.EMPTY;
        }
        if(resultFluidIn != null) {
            resultFluid = resultFluidIn;
        }else{
            resultFluid = Fluids.EMPTY;
        }
        minResult = minResultIn;
        maxResult = maxResultIn;
        holeColor = holeColorIn;
        resinColor = resinColorIn;
    }

    public boolean matches(Container inventory, Level world) {
        return inventory.getItem(0).sameItem(desiredBlock);
    }

    public ItemStack assemble(Container p_77572_1_) {
        return result.copy();
    }

    public boolean canCraftInDimensions(int p_194133_1_, int p_194133_2_) {
        return true;
    }

    @Override
    public RecipeType<?> getType() {
        return Recipes.holeType;
    }

    public ResourceLocation getId() {
        return id;
    }

    public String getGroup() {
        return group;
    }

    public ItemStack getDesiredBlock() {
        return desiredBlock;
    }
    
    public int getIngredientItemDamage(){
        return ingredientItemDamage;
    }

    public boolean getConsumeIngredients() {
        return consumeIngredients;
    }

    public int getMinTicks() {
        return minTicks;
    }

    public int getMaxTicks() {
        return maxTicks;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredientItems;
    }

    public ItemStack getResultItem() {
        return result;
    }

    public int getMinResult() {
        return minResult;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public int getHoleColor() {
        return holeColor;
    }

    public int getResinColor() {
        return resinColor;
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(ITBlocks.hole_block.get());
    }

    public RecipeSerializer<?> getSerializer() {
        return Recipes.holeSerializer.get();
    }

    public Fluid getResultFluid() {
        return resultFluid;
    }
}

