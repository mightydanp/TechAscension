package mightydanp.techcore.common.jsonconfig.codec;

import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.world.item.crafting.Ingredient;

/* from 50ap5ud5
 * @ https://gist.github.com/50ap5ud5/581a325c8e7d75a463248e058a0623c9
 */
public class IngredientCodec{
    /** Ingredient to Json only codec*/
    public static final Codec<Ingredient> INGREDIENT_TO_JSON_CODEC = Codec.PASSTHROUGH.comapFlatMap(
            json -> DataResult.error("Deserializing of ingredients not implemented in this codec. Use another Codec for that."),
            ingredient -> new Dynamic<>(JsonOps.INSTANCE, ingredient.toJson()));

    /**
     * Ingredient from Json only Codec
     * <br> The pass through codec is the codec for jsons, nbt, etc. themselves
     * <br> comapFlatMap is similar to xmap but is used for things that can't always be converted
     */
    public static final Codec<Ingredient> INGREDIENT_FROM_JSON_CODEC = Codec.PASSTHROUGH.flatXmap(dynamic ->
            {
                try
                {
                    Ingredient ingredient = Ingredient.fromJson(dynamic.convert(JsonOps.INSTANCE).getValue());
                    return DataResult.success(ingredient);
                }
                catch(JsonSyntaxException e)
                {
                    return DataResult.error(e.getMessage());
                }
            },
            ingredient -> DataResult.error("Cannot serialize ingredients to json with this Codec! Use another codec for that."));

    /** A codec that allows {@linkplain Ingredient} to be Serialised and Deserialized to and from Json*/
    public static final Codec<Ingredient> INGREDIENT_CODEC = Codec.of(INGREDIENT_TO_JSON_CODEC, INGREDIENT_FROM_JSON_CODEC);
}
