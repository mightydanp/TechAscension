package mightydanp.techcore.common.jsonconfig.recipe.handcrafting;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mightydanp.techcore.common.jsonconfig.codec.CodecHelpers;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public record HandCraftingCodec(String name, Integer input1Amount, List<Ingredient> input1, Integer input2Amount, List<Ingredient> input2, Integer outputAmount, List<Ingredient> output) {
    public static String codecName = "hand_crafting";

    public static final Codec<HandCraftingCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(HandCraftingCodec::name),
            Codec.INT.fieldOf("input_1_amount").forGetter(HandCraftingCodec::input1Amount),
            Codec.list(CodecHelpers.INGREDIENT_CODEC).fieldOf("input_1").forGetter(HandCraftingCodec::input1),
            Codec.INT.fieldOf("input_2_amount").forGetter(HandCraftingCodec::input2Amount),
            Codec.list(CodecHelpers.INGREDIENT_CODEC).optionalFieldOf("input_2_amount", List.of()).forGetter(HandCraftingCodec::input2),
            Codec.INT.optionalFieldOf("output_amount", 0).forGetter(HandCraftingCodec::outputAmount),
            Codec.list(CodecHelpers.INGREDIENT_CODEC).fieldOf("output").forGetter(HandCraftingCodec::output)
    ).apply(instance, HandCraftingCodec::new));
}
