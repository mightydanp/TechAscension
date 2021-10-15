package mightydanp.industrialtech.api.common.crafting.recipe;

import com.google.gson.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Created by MightyDanp on 10/10/2021.
 */
public class HoleRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<HoleRecipe> {
    public HoleRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
        String group = JSONUtils.getAsString(jsonObject, "group", "");

        ItemStack desiredLog = itemFromJson(JSONUtils.getAsJsonObject(jsonObject, "desired_block"));

        NonNullList<Ingredient> ingredientItems = itemsFromJson(JSONUtils.getAsJsonArray(jsonObject, "ingredient_items"));

        if (ingredientItems.isEmpty()) {
            throw new JsonParseException("No harvest tools for recipe");
        } else if (ingredientItems.size() > 2) {
            throw new JsonParseException("Too many harvest tools for recipe the max is " + 2);
        }
            int harvestToolDamage = JSONUtils.getAsInt(jsonObject, "harvest_tool_damage", 0);

            boolean consumeIngredients = JSONUtils.getAsBoolean(jsonObject, "consume_ingredients", false);

            int minTicks = JSONUtils.getAsInt(jsonObject, "min_ticks", 100);
            int maxTicks = JSONUtils.getAsInt(jsonObject, "max_ticks", 100);

            ItemStack itemstack = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(jsonObject, "result"));

            int minResult = JSONUtils.getAsInt(jsonObject, "min_result", 1);
            int maxResult = JSONUtils.getAsInt(jsonObject, "max_result", 1);

            int holeColor = JSONUtils.getAsInt(jsonObject, "hole_color", 0);
            int resinColor = JSONUtils.getAsInt(jsonObject, "resin_color", 0);

            return new HoleRecipe(resourceLocation, group, desiredLog, ingredientItems, harvestToolDamage, consumeIngredients, minTicks, maxTicks, itemstack, minResult, maxResult, holeColor, resinColor);
    }

    public static ItemStack itemFromJson(JsonObject p_199798_0_) {
        String s = JSONUtils.getAsString(p_199798_0_, "item");
        if (p_199798_0_.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = JSONUtils.getAsInt(p_199798_0_, "count", 1);
            return CraftingHelper.getItemStack(p_199798_0_, true);
        }
    }

    private static NonNullList<Ingredient> itemsFromJson(JsonArray jsonArrayIn) {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();

        for(int i = 0; i < jsonArrayIn.size(); ++i) {
            Ingredient ingredient = Ingredient.fromJson(jsonArrayIn.get(i));
            if (!ingredient.isEmpty()) {
                nonnulllist.add(ingredient);
            }
        }

        return nonnulllist;
    }

    public HoleRecipe fromNetwork(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
        String group = packetBuffer.readUtf(32767);
        ItemStack desiredBlock = packetBuffer.readItem();

        int i = packetBuffer.readVarInt();
        NonNullList<Ingredient> ingredientItems = NonNullList.withSize(i, Ingredient.EMPTY);

        for(int j = 0; j < ingredientItems.size(); ++j) {
            ingredientItems.set(j, Ingredient.fromNetwork(packetBuffer));
        }

        int ingredientItemDamage = packetBuffer.readVarInt();

        boolean consumeIngredients = packetBuffer.readBoolean();
        int minTicks = packetBuffer.readVarInt();
        int maxTicks = packetBuffer.readVarInt();

        ItemStack itemstack = packetBuffer.readItem();

        int minResult = packetBuffer.readVarInt();
        int maxResult = packetBuffer.readVarInt();

        int holeColor = packetBuffer.readVarInt();
        int resinColor = packetBuffer.readVarInt();

        return new HoleRecipe(resourceLocation, group, desiredBlock, ingredientItems, ingredientItemDamage, consumeIngredients, minTicks, maxTicks, itemstack, minResult, maxResult, holeColor, resinColor);
    }

    public void toNetwork(PacketBuffer packetBuffer, HoleRecipe holeRecipe) {
        packetBuffer.writeUtf(holeRecipe.getGroup());

        packetBuffer.writeItem(holeRecipe.getDesiredBlock());

        for(Ingredient harvestTool: holeRecipe.getIngredients()){
            harvestTool.toNetwork(packetBuffer);
        }

        packetBuffer.writeVarInt(holeRecipe.getIngredientItemDamage());
        packetBuffer.writeBoolean(holeRecipe.getConsumeIngredients());
        packetBuffer.writeVarInt(holeRecipe.getMinTicks());
        packetBuffer.writeVarInt(holeRecipe.getMaxTicks());
        packetBuffer.writeItem(holeRecipe.getResultItem());
        packetBuffer.writeVarInt(holeRecipe.getMinResult());
        packetBuffer.writeVarInt(holeRecipe.getMaxResult());
        packetBuffer.writeVarInt(holeRecipe.getHoleColor());
        packetBuffer.writeVarInt(holeRecipe.getResinColor());
    }
}