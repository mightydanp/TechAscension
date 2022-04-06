package mightydanp.industrialtech.common.crafting.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Created by MightyDanp on 6/11/2021.
 */
public class CampfireOverrideCharRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CampfireOverrideCharRecipe> {
    private final int defaultCookingTime = 100;

    public CampfireOverrideCharRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
        String s = GsonHelper.getAsString(jsonObject, "group", "");
        JsonElement jsonelement = GsonHelper.isArrayNode(jsonObject, "ingredient") ? GsonHelper.getAsJsonArray(jsonObject, "ingredient") : GsonHelper.getAsJsonObject(jsonObject, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(jsonelement);
        //Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
        if (!jsonObject.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        ItemStack itemstack;
        if (jsonObject.get("result").isJsonObject()) itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
        else {
            String s1 = GsonHelper.getAsString(jsonObject, "result");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            itemstack = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
                return new IllegalStateException("Item: " + s1 + " does not exist");
            }));
        }
        float f = GsonHelper.getAsFloat(jsonObject, "experience", 0.0F);
        int i = GsonHelper.getAsInt(jsonObject, "cookingtime", this.defaultCookingTime);
        return new CampfireOverrideCharRecipe(resourceLocation, s, ingredient, itemstack, f, i);
    }

    public CampfireOverrideCharRecipe fromNetwork(ResourceLocation p_199426_1_, FriendlyByteBuf p_199426_2_) {
        String s = p_199426_2_.readUtf(32767);
        Ingredient ingredient = Ingredient.fromNetwork(p_199426_2_);
        ItemStack itemstack = p_199426_2_.readItem();
        float f = p_199426_2_.readFloat();
        int i = p_199426_2_.readVarInt();
        return new CampfireOverrideCharRecipe(p_199426_1_, s, ingredient, itemstack, f, i);
    }

    public void toNetwork(FriendlyByteBuf packetBuffer, CampfireOverrideCharRecipe campfireOverrideCharRecipe) {
        packetBuffer.writeUtf(campfireOverrideCharRecipe.getGroup());
        for(Ingredient ingredient: campfireOverrideCharRecipe.getIngredients()){
            ingredient.toNetwork(packetBuffer);
        }
        packetBuffer.writeItem(campfireOverrideCharRecipe.getResultItem());
        packetBuffer.writeFloat(campfireOverrideCharRecipe.getExperience());
        packetBuffer.writeVarInt(campfireOverrideCharRecipe.getCookingTime());
    }
}