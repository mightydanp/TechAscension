package mightydanp.industrialcore.common.crafting.recipe;

import com.google.gson.*;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Created by MightyDanp on 10/10/2021.
 */
public class HoleRecipeSerializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<HoleRecipe> {
    public static ItemStack itemFromJson(JsonObject p_199798_0_) {
        String s = GsonHelper.getAsString(p_199798_0_, "item");
        if (p_199798_0_.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = GsonHelper.getAsInt(p_199798_0_, "count", 1);
            return CraftingHelper.getItemStack(p_199798_0_, true);
        }
    }

    public static Fluid fluidFromJson(JsonObject p_199798_0_) {
        if (p_199798_0_.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            return getFluidStack(p_199798_0_, true);
        }
    }

    public static Fluid getFluidStack(JsonObject json, boolean readNBT)
    {
        String fluidName = GsonHelper.getAsString(json, "item");

        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidName));

        if (fluid == null) {
            throw new JsonSyntaxException("Unknown fluid '" + fluidName + "'");
        }else{
            return fluid;
        }
    }

    private static NonNullList<Ingredient> itemsFromJson(JsonArray jsonArrayIn) {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();

        for (int i = 0; i < jsonArrayIn.size(); ++i) {
            Ingredient ingredient = Ingredient.fromJson(jsonArrayIn.get(i));
            if (!ingredient.isEmpty()) {
                nonnulllist.add(ingredient);
            }
        }

        return nonnulllist;
    }

    public HoleRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
        String group = GsonHelper.getAsString(jsonObject, "group", "");

        if (!jsonObject.has("desired_block")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        ItemStack desiredBlockItem;

        if (jsonObject.get("desired_block").isJsonObject()) desiredBlockItem = itemFromJson(GsonHelper.getAsJsonObject(jsonObject, "desired_block"));
        else {
            String s1 = GsonHelper.getAsString(jsonObject, "desired_block");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            desiredBlockItem = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));
        }

        NonNullList<Ingredient> ingredientItems = itemsFromJson(GsonHelper.getAsJsonArray(jsonObject, "ingredient_items"));

        if (ingredientItems.isEmpty()) {
            throw new JsonParseException("No harvest tools for recipe");
        } else if (ingredientItems.size() > 2) {
            throw new JsonParseException("Too many harvest tools for recipe the max is " + 2);
        }

        int harvestToolDamage = GsonHelper.getAsInt(jsonObject, "ingredient_item_damage", 0);

        boolean consumeIngredients = GsonHelper.getAsBoolean(jsonObject, "consume_ingredients", false);

        int minTicks = GsonHelper.getAsInt(jsonObject, "min_ticks", 100);
        int maxTicks = GsonHelper.getAsInt(jsonObject, "max_ticks", 100);

        ItemStack resultItem = null;
        if(jsonObject.has("result_item")) {
            if (jsonObject.get("result_item").isJsonObject())
                resultItem = itemFromJson(GsonHelper.getAsJsonObject(jsonObject, "result_item"));
            else {
                String s1 = GsonHelper.getAsString(jsonObject, "result_item");
                ResourceLocation resourcelocation = new ResourceLocation(s1);
                resultItem = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));
            }
        }

        Fluid resultFluid = null;
        if(jsonObject.has("result_fluid")) {
            if (jsonObject.get("result_fluid").isJsonObject())
                resultFluid = fluidFromJson(GsonHelper.getAsJsonObject(jsonObject, "result_fluid"));
            else {
                String s1 = GsonHelper.getAsString(jsonObject, "result_fluid");
                ResourceLocation resourcelocation = new ResourceLocation(s1);
                resultFluid = Registry.FLUID.getOptional(resourcelocation).orElseThrow(() -> new IllegalStateException("Fluid: " + s1 + " does not exist"));
            }
        }

        int minResult = GsonHelper.getAsInt(jsonObject, "min_result", 1);
        int maxResult = GsonHelper.getAsInt(jsonObject, "max_result", 1);

        int holeColor = GsonHelper.getAsInt(jsonObject, "hole_color", 0);
        int resinColor = GsonHelper.getAsInt(jsonObject, "resin_color", 0);

        return new HoleRecipe(resourceLocation, group, desiredBlockItem, ingredientItems, harvestToolDamage, consumeIngredients, minTicks, maxTicks, resultItem, resultFluid, minResult, maxResult, holeColor, resinColor);
    }

    public HoleRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf packetBuffer) {
        String group = packetBuffer.readUtf(32767);

        ItemStack desiredBlock = packetBuffer.readItem();

        int f = packetBuffer.readVarInt();
        NonNullList<Ingredient> ingredientItems = NonNullList.withSize(f, Ingredient.EMPTY);

        for (int j = 0; j < ingredientItems.size(); ++j) {
            ingredientItems.set(j, Ingredient.fromNetwork(packetBuffer));
        }

        int ingredientItemDamage = packetBuffer.readVarInt();

        boolean consumeIngredients = packetBuffer.readBoolean();
        int minTicks = packetBuffer.readVarInt();
        int maxTicks = packetBuffer.readVarInt();

        ItemStack itemstack = packetBuffer.readItem();

        Fluid fluidStack = packetBuffer.readFluidStack().getFluid();

        int minResult = packetBuffer.readVarInt();
        int maxResult = packetBuffer.readVarInt();

        int holeColor = packetBuffer.readVarInt();
        int resinColor = packetBuffer.readVarInt();

        return new HoleRecipe(resourceLocation, group, desiredBlock, ingredientItems, ingredientItemDamage, consumeIngredients, minTicks, maxTicks, itemstack, fluidStack, minResult, maxResult, holeColor, resinColor);
    }

    public void toNetwork(FriendlyByteBuf packetBuffer, HoleRecipe holeRecipe) {

        packetBuffer.writeUtf(holeRecipe.getGroup());
        packetBuffer.writeItem(holeRecipe.getDesiredBlock());

        for (Ingredient harvestTool : holeRecipe.getIngredients()) {
            harvestTool.toNetwork(packetBuffer);
        }

        packetBuffer.writeVarInt(holeRecipe.getIngredientItemDamage());
        packetBuffer.writeBoolean(holeRecipe.getConsumeIngredients());
        packetBuffer.writeVarInt(holeRecipe.getMinTicks());
        packetBuffer.writeVarInt(holeRecipe.getMaxTicks());
        packetBuffer.writeItem(holeRecipe.getResultItem());
        packetBuffer.writeFluidStack(new FluidStack(holeRecipe.getResultFluid(), 0));
        packetBuffer.writeVarInt(holeRecipe.getMinResult());
        packetBuffer.writeVarInt(holeRecipe.getMaxResult());
        packetBuffer.writeVarInt(holeRecipe.getHoleColor());
        packetBuffer.writeVarInt(holeRecipe.getResinColor());
    }


}