package mightydanp.industrialtech.common.crafting.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Created by MightyDanp on 6/6/2021.
 */
public class CampfireOverrideRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CampfireOverrideRecipe> {
    private final int defaultCookingTime = 100;

    public CampfireOverrideRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
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
        return new CampfireOverrideRecipe(resourceLocation, s, ingredient, itemstack, f, i);
    }

    public CampfireOverrideRecipe fromNetwork(ResourceLocation p_199426_1_, PacketBuffer p_199426_2_) {
        String s = p_199426_2_.readUtf(32767);
        Ingredient ingredient = Ingredient.fromNetwork(p_199426_2_);
        ItemStack itemstack = p_199426_2_.readItem();
        float f = p_199426_2_.readFloat();
        int i = p_199426_2_.readVarInt();
        return new CampfireOverrideRecipe(p_199426_1_, s, ingredient, itemstack, f, i);
    }

    public void toNetwork(PacketBuffer packetBuffer, CampfireOverrideRecipe campFireOverrideRecipe) {
        packetBuffer.writeUtf(campFireOverrideRecipe.getGroup());
        for(Ingredient ingredient: campFireOverrideRecipe.getIngredients()){
            ingredient.toNetwork(packetBuffer);
        }
        packetBuffer.writeItem(campFireOverrideRecipe.getResultItem());
        packetBuffer.writeFloat(campFireOverrideRecipe.getExperience());
        packetBuffer.writeVarInt(campFireOverrideRecipe.getCookingTime());
    }
}