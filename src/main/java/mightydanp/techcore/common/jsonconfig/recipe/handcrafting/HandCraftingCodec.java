package mightydanp.techcore.common.jsonconfig.recipe.handcrafting;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record HandCraftingCodec(String name, Integer input1Amount, Either<String, ItemStack> input1, Integer input2Amount, Either<String, ItemStack> input2, Integer outputAmount, Either<String, ItemStack> output) {
    public static String codecName = "hand_crafting";

    public static final Codec<HandCraftingCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(HandCraftingCodec::name),
            Codec.INT.fieldOf("input_1_amount").forGetter(HandCraftingCodec::input1Amount),
            Codec.either(Codec.STRING, ItemStack.CODEC).fieldOf("input_1").forGetter(HandCraftingCodec::input1),
            Codec.INT.fieldOf("input_2_amount").forGetter(HandCraftingCodec::input2Amount),
            Codec.either(Codec.STRING, ItemStack.CODEC).optionalFieldOf("input_2_amount", Either.right(ItemStack.EMPTY)).forGetter(HandCraftingCodec::input2),
            Codec.INT.optionalFieldOf("output_amount", 0).forGetter(HandCraftingCodec::outputAmount),
            Codec.either(Codec.STRING, ItemStack.CODEC).fieldOf("output").forGetter(HandCraftingCodec::output)
    ).apply(instance, HandCraftingCodec::new));
}
