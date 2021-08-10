package mightydanp.industrialtech.common.crafting.recipe;

import mightydanp.industrialtech.common.blocks.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

/**
 * Created by MightyDanp on 6/11/2021.
 */
public class CampfireOverrideCharRecipe extends AbstractCookingRecipe {
    public CampfireOverrideCharRecipe(ResourceLocation resourceLocation, String group, Ingredient ingredient, ItemStack result, float p_i50030_5_, int p_i50030_6_) {
        super(ModRecipes.campfireCharType, resourceLocation, group, ingredient, result, p_i50030_5_, p_i50030_6_);
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.campfire_override.get());
    }

    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.CampfireCharSerializer.get();
    }

}