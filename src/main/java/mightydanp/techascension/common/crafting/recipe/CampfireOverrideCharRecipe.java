package mightydanp.techascension.common.crafting.recipe;

import mightydanp.techascension.common.blocks.ModBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.resources.ResourceLocation;

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

    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.campfireCharSerializer.get();
    }

}