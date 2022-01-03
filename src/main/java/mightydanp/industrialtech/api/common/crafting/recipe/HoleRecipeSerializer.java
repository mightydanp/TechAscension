package mightydanp.industrialtech.api.common.crafting.recipe;

import com.google.gson.*;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

/**
 * Created by MightyDanp on 10/10/2021.
 */
public class HoleRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<HoleRecipe> {
    public static ItemStack itemFromJson(JsonObject p_199798_0_) {
        String s = JSONUtils.getAsString(p_199798_0_, "item");
        if (p_199798_0_.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
        } else {
            int i = JSONUtils.getAsInt(p_199798_0_, "count", 1);
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
        String fluidName = JSONUtils.getAsString(json, "item");

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
        String group = JSONUtils.getAsString(jsonObject, "group", "");

        if (!jsonObject.has("desired_block")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
        ItemStack desiredBlockItem;

        if (jsonObject.get("desired_block").isJsonObject()) desiredBlockItem = itemFromJson(JSONUtils.getAsJsonObject(jsonObject, "desired_block"));
        else {
            String s1 = JSONUtils.getAsString(jsonObject, "desired_block");
            ResourceLocation resourcelocation = new ResourceLocation(s1);
            desiredBlockItem = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));
        }

        NonNullList<Ingredient> ingredientItems = itemsFromJson(JSONUtils.getAsJsonArray(jsonObject, "ingredient_items"));

        if (ingredientItems.isEmpty()) {
            throw new JsonParseException("No harvest tools for recipe");
        } else if (ingredientItems.size() > 2) {
            throw new JsonParseException("Too many harvest tools for recipe the max is " + 2);
        }

        int harvestToolDamage = JSONUtils.getAsInt(jsonObject, "ingredient_item_damage", 0);

        boolean consumeIngredients = JSONUtils.getAsBoolean(jsonObject, "consume_ingredients", false);

        int minTicks = JSONUtils.getAsInt(jsonObject, "min_ticks", 100);
        int maxTicks = JSONUtils.getAsInt(jsonObject, "max_ticks", 100);

        ItemStack resultItem = null;
        if(jsonObject.has("result_item")) {
            if (jsonObject.get("result_item").isJsonObject())
                resultItem = itemFromJson(JSONUtils.getAsJsonObject(jsonObject, "result_item"));
            else {
                String s1 = JSONUtils.getAsString(jsonObject, "result_item");
                ResourceLocation resourcelocation = new ResourceLocation(s1);
                resultItem = new ItemStack(Registry.ITEM.getOptional(resourcelocation).orElseThrow(() -> new IllegalStateException("Item: " + s1 + " does not exist")));
            }
        }

        Fluid resultFluid = null;
        if(jsonObject.has("result_fluid")) {
            if (jsonObject.get("result_fluid").isJsonObject())
                resultFluid = fluidFromJson(JSONUtils.getAsJsonObject(jsonObject, "result_fluid"));
            else {
                String s1 = JSONUtils.getAsString(jsonObject, "result_fluid");
                ResourceLocation resourcelocation = new ResourceLocation(s1);
                resultFluid = Registry.FLUID.getOptional(resourcelocation).orElseThrow(() -> new IllegalStateException("Fluid: " + s1 + " does not exist"));
            }
        }

        int minResult = JSONUtils.getAsInt(jsonObject, "min_result", 1);
        int maxResult = JSONUtils.getAsInt(jsonObject, "max_result", 1);

        int holeColor = JSONUtils.getAsInt(jsonObject, "hole_color", 0);
        int resinColor = JSONUtils.getAsInt(jsonObject, "resin_color", 0);

        return new HoleRecipe(resourceLocation, group, desiredBlockItem, ingredientItems, harvestToolDamage, consumeIngredients, minTicks, maxTicks, resultItem, resultFluid, minResult, maxResult, holeColor, resinColor);
    }

    public HoleRecipe fromNetwork(ResourceLocation resourceLocation, PacketBuffer packetBuffer) {
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

    public void toNetwork(PacketBuffer packetBuffer, HoleRecipe holeRecipe) {

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