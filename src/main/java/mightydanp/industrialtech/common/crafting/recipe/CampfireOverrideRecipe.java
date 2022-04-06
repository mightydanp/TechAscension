package mightydanp.industrialtech.common.crafting.recipe;

import mightydanp.industrialtech.common.blocks.ModBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

/**
 * Created by MightyDanp on 6/6/2021.
 */
public class CampfireOverrideRecipe extends AbstractCookingRecipe {
    public CampfireOverrideRecipe(ResourceLocation resourceLocation, String group, Ingredient ingredient, ItemStack result, float p_i50030_5_, int p_i50030_6_) {
        super(ModRecipes.campfireType, resourceLocation, group, ingredient, result, p_i50030_5_, p_i50030_6_);
    }

    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.campfire_override.get());
    }

    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.campfireSerializer.get();
    }

}