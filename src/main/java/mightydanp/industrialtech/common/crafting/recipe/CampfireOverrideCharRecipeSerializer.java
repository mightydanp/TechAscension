package mightydanp.industrialtech.common.crafting.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Created by MightyDanp on 6/11/2021.
 */
public class CampfireOverrideCharRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CampfireOverrideCharRecipe> {
    private final int defaultCookingTime = 100;

    public CampfireOverrideCharRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
        String s = JSONUtils.getAsString(jsonObject, "group", "");
        JsonElement jsonelement = (JsonElement)(JSONUtils.isArrayNode(jsonObject, "ingredient") ? JSONUtils.getAsJsonArray(jsonObject, "ingredient") : JSONUtils.getAsJsonObject(jsonObject, "ingredient"));
        Ingredient ingredient = Ingredient.fromJson(jsonelement);
        //Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
        if (!jsonObject.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        ItemStack itemstack;
        if (jsonObject.get("result").isJsonObject()) itemstack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(jsonObject, "result"));
        else {
            String s1 = JSONUtils.getAsString(jsonObject, "result");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            itemstack = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> {
                return new IllegalStateException("Item: " + s1 + " does not exist");
            }));
        }
        float f = JSONUtils.getAsFloat(jsonObject, "experience", 0.0F);
        int i = JSONUtils.getAsInt(jsonObject, "cookingtime", this.defaultCookingTime);
        return new CampfireOverrideCharRecipe(resourceLocation, s, ingredient, itemstack, f, i);
    }

    public CampfireOverrideCharRecipe fromNetwork(ResourceLocation p_199426_1_, PacketBuffer p_199426_2_) {
        String s = p_199426_2_.readUtf(32767);
        Ingredient ingredient = Ingredient.fromNetwork(p_199426_2_);
        ItemStack itemstack = p_199426_2_.readItem();
        float f = p_199426_2_.readFloat();
        int i = p_199426_2_.readVarInt();
        return new CampfireOverrideCharRecipe(p_199426_1_, s, ingredient, itemstack, f, i);
    }

    public void toNetwork(PacketBuffer packetBuffer, CampfireOverrideCharRecipe campfireOverrideCharRecipe) {
        packetBuffer.writeUtf(campfireOverrideCharRecipe.getGroup());
        for(Ingredient ingredient: campfireOverrideCharRecipe.getIngredients()){
            ingredient.toNetwork(packetBuffer);
        }
        packetBuffer.writeItem(campfireOverrideCharRecipe.getResultItem());
        packetBuffer.writeFloat(campfireOverrideCharRecipe.getExperience());
        packetBuffer.writeVarInt(campfireOverrideCharRecipe.getCookingTime());
    }
}