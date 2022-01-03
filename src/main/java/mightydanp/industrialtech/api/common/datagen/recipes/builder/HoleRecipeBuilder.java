package mightydanp.industrialtech.api.common.datagen.recipes.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import mightydanp.industrialtech.api.common.crafting.recipe.Recipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.IRequirementsStrategy;
import net.minecraft.advancements.criterion.RecipeUnlockedTrigger;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by MightyDanp on 10/12/2021.
 */
public class HoleRecipeBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Item desiredBlock;
    private final NonNullList<Ingredient> ingredientItems = new NonNullList<Ingredient>() {
    };
    private final int minTicks;
    private final int maxTicks;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private String group;
    private int ingredientItemDamage;
    private boolean consumeIngredients;
    private Item result;
    private Fluid resultFluid;
    private int minResult;
    private int maxResult;
    private int holeColor;
    private int resinColor;

    public HoleRecipeBuilder(Item desiredBlockIn, int minTicksIn, int maxTicksIn) {
        desiredBlock = desiredBlockIn;
        minTicks = minTicksIn;
        maxTicks = maxTicksIn;
    }

    public static HoleRecipeBuilder holeRecipe(Item desiredBlockIn, int minTicksIn, int maxTicksIn) {
        return new HoleRecipeBuilder(desiredBlockIn, minTicksIn, maxTicksIn);
    }

    @SafeVarargs
    public final HoleRecipeBuilder requiredIngredientItem(ITag<Item>... iTagsIn) {
        for (ITag<Item> itemITag : iTagsIn) {
            this.requiredIngredientItem(Ingredient.of(itemITag));
        }
        return this;
    }

    public HoleRecipeBuilder requiredIngredientItem(IItemProvider... iItemProvidersIn) {
        this.requiredIngredientItem(Ingredient.of(iItemProvidersIn));
        return this;
    }

    public HoleRecipeBuilder requiredIngredientItem(Ingredient... ingredientsIn) {
        ingredientItems.addAll(Arrays.asList(ingredientsIn));
        return this;
    }

    public HoleRecipeBuilder setIngredientItemDamage(int ingredientItemDamageIn) {
        ingredientItemDamage = ingredientItemDamageIn;

        return this;
    }

    public HoleRecipeBuilder setConsumeIngredients(boolean consumeIngredientsIn) {
        this.consumeIngredients = consumeIngredientsIn;
        return this;
    }

    public HoleRecipeBuilder setResult(Item result) {
        this.result = result;
        return this;
    }

    public HoleRecipeBuilder setResultFluid(FlowingFluid resultFluidIn) {
        this.resultFluid = resultFluidIn;
        return this;
    }

    public HoleRecipeBuilder setMinResult(int minResult) {
        this.minResult = minResult;

        return this;
    }

    public HoleRecipeBuilder setMaxResult(int maxResult) {
        this.maxResult = maxResult;

        return this;
    }

    public HoleRecipeBuilder setHoleColor(int holeColor) {
        this.holeColor = holeColor;

        return this;
    }

    public HoleRecipeBuilder setResinColor(int resinColor) {
        this.resinColor = resinColor;

        return this;
    }

    public HoleRecipeBuilder unlockedBy(String p_200483_1_, ICriterionInstance p_200483_2_) {
        this.advancement.addCriterion(p_200483_1_, p_200483_2_);
        return this;
    }

    public HoleRecipeBuilder group(String p_200490_1_) {
        this.group = p_200490_1_;
        return this;
    }

    public HoleRecipeBuilder init() {
        return this;
    }

    public void save(Consumer<IFinishedRecipe> p_200484_1_, String p_200484_2_) {
        ResourceLocation resourcelocation = Registry.ITEM.getKey(this.result.getItem());
        if ((new ResourceLocation(p_200484_2_)).equals(resourcelocation)) {
            throw new IllegalStateException("Hole Recipe " + p_200484_2_ + " should remove its 'save' argument");
        } else {
            this.save(p_200484_1_, new ResourceLocation(p_200484_2_));
        }
    }

    public void save(Consumer<IFinishedRecipe> p_200485_1_, ResourceLocation p_200485_2_) {
        this.ensureValid(p_200485_2_);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_hole_recipe", RecipeUnlockedTrigger.unlocked(p_200485_2_)).rewards(AdvancementRewards.Builder.recipe(p_200485_2_)).requirements(IRequirementsStrategy.OR);
        p_200485_1_.accept(new HoleRecipeBuilder.Result(p_200485_2_,
                this.group == null ? "" : this.group, desiredBlock, ingredientItems, ingredientItemDamage, consumeIngredients, minTicks, maxTicks, result, resultFluid, minResult, maxResult, holeColor, resinColor,
                this.advancement, new ResourceLocation(p_200485_2_.getNamespace(), "recipes/" + Objects.requireNonNull(this.result == null ? ItemGroup.TAB_SEARCH : this.result.getItem().getItemCategory()).getRecipeFolderName() + "/" + p_200485_2_.getPath())));
    }

    private void ensureValid(ResourceLocation p_200481_1_) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_200481_1_);
        }
    }

    public static class Result implements IFinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Item desiredBlock;
        private final NonNullList<Ingredient> ingredientItems;
        private final int ingredientItemDamage;
        private final boolean consumeIngredients;
        private final int minTicks;
        private final int maxTicks;
        private final Item result;
        private final Fluid resultFluid;
        private final int minResult;
        private final int maxResult;
        private final int holeColor;
        private final int resinColor;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation p_i48268_1_, String groupIn, Item desiredBlockIn, NonNullList<Ingredient> ingredientItemsIn, int ingredientItemDamageIn, boolean consumeIngredientsIn, int minTicksIn, int maxTicksIn, @Nullable Item resultIn, @Nullable Fluid resultFluidIn, int minResultIn, int maxResultIn, int holeColorIn, int resinColorIn, Advancement.Builder advancementIn, ResourceLocation advancementIdIn) {
            id = p_i48268_1_;
            group = groupIn;
            desiredBlock = desiredBlockIn;
            ingredientItems = ingredientItemsIn;
            ingredientItemDamage = ingredientItemDamageIn;
            consumeIngredients = consumeIngredientsIn;
            minTicks = minTicksIn;
            maxTicks = maxTicksIn;
            result = resultIn;
            resultFluid = resultFluidIn;
            minResult = minResultIn;
            maxResult = maxResultIn;
            holeColor = holeColorIn;
            resinColor = resinColorIn;
            advancement = advancementIn;
            advancementId = advancementIdIn;
        }

        public void serializeRecipeData(JsonObject jsonObject) {
            if (!this.group.isEmpty()) {
                jsonObject.addProperty("group", this.group);
            }

            jsonObject.addProperty("desired_block", Registry.ITEM.getKey(desiredBlock).toString());

            JsonArray jsonArrayIngredient = new JsonArray();
            for (Ingredient ingredient : ingredientItems) {
                jsonArrayIngredient.add(ingredient.toJson());
            }

            jsonObject.add("ingredient_items", jsonArrayIngredient);
            jsonObject.addProperty("ingredient_item_damage", ingredientItemDamage);
            jsonObject.addProperty("consume_ingredients", consumeIngredients);
            jsonObject.addProperty("min_ticks", minTicks);
            jsonObject.addProperty("max_ticks", maxTicks);
            if (result != null) {
                jsonObject.addProperty("result_item", Registry.ITEM.getKey(result).toString());
            }

            if (resultFluid != null) {
                jsonObject.addProperty("result_fluid", Registry.FLUID.getKey(resultFluid).toString());
            }
            jsonObject.addProperty("min_result", minResult);
            jsonObject.addProperty("max_result", maxResult);
            jsonObject.addProperty("hole_color", holeColor);
            jsonObject.addProperty("resin_color", resinColor);
        }

        public IRecipeSerializer<?> getType() {
            return Recipes.holeSerializer.get();
        }

        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}