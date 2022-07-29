package mightydanp.techcore.common.datagen.recipes.builder;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;

/**
 * Created by MightyDanp on 4/2/2021.
 */
public class CustomCookingRecipeBuilder {
    // result itemstack json format isn't the same as the standard format for itemstacks, stupidly
    public static final Codec<ItemStack> ITEMSTACK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Registry.ITEM.byNameCodec().fieldOf("item").forGetter(ItemStack::getItem),
            Codec.INT.optionalFieldOf("count",1).forGetter(ItemStack::getCount),
            CompoundTag.CODEC.optionalFieldOf("nbt", new CompoundTag()).forGetter(ItemStack::getTag)
    ).apply(instance, ItemStack::new));



    public static final Codec<Ingredient> INGREDIENT_CODEC = Codec.PASSTHROUGH.comapFlatMap(dynamic ->
            {
                try
                {
                    Ingredient ingredient = Ingredient.fromJson(dynamic.convert(JsonOps.INSTANCE).getValue());
                    return DataResult.success(ingredient);
                }
                catch(Exception e)
                {
                    return DataResult.error(e.getMessage());
                }
            },
            ingredient -> new Dynamic<JsonElement>(JsonOps.INSTANCE, ingredient.toJson()));

    public static final Codec<Either<Item,ItemStack>> ITEM_OR_STACK_CODEC = Codec.either(Registry.ITEM.byNameCodec(), ITEMSTACK_CODEC);

    public static final Codec<CustomCookingRecipeBuilder> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("type").forGetter(CustomCookingRecipeBuilder::getType),
            INGREDIENT_CODEC.fieldOf("ingredient").forGetter(CustomCookingRecipeBuilder::getIngredient),
            ITEM_OR_STACK_CODEC.fieldOf("result").forGetter(CustomCookingRecipeBuilder::getResult),
            Codec.FLOAT.fieldOf("experience").forGetter(CustomCookingRecipeBuilder::getExperience),
            Codec.INT.fieldOf("cookingtime").forGetter(CustomCookingRecipeBuilder::getCookingTime)
    ).apply(instance, CustomCookingRecipeBuilder::new));

    private final ResourceLocation type; public ResourceLocation getType() { return this.type; }
    private final Ingredient ingredient; public Ingredient getIngredient() { return this.ingredient; }
    private final Either<Item,ItemStack> result; public Either<Item,ItemStack> getResult() { return this.result; }
    private final float experience; public float getExperience() { return this.experience; }
    private final int cookingtime; public int getCookingTime() { return this.cookingtime; }



    public CustomCookingRecipeBuilder(ResourceLocation type, Ingredient ingredient, Either<Item,ItemStack> result, float experience, int cookingtime)
    {
        this.type = type;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.cookingtime = cookingtime;
    }

    public static CustomCookingRecipeBuilder forItemResult(ResourceLocation type, Ingredient ingredient, Item result, float experience, int cookingtime)
    {
        return new CustomCookingRecipeBuilder(type, ingredient, Either.left(result), experience, cookingtime);
    }

    public static CustomCookingRecipeBuilder forItemStackResult(ResourceLocation type, Ingredient ingredient, ItemStack result, float experience, int cookingtime)
    {
        return new CustomCookingRecipeBuilder(type, ingredient, Either.right(result), experience, cookingtime);
    }
}