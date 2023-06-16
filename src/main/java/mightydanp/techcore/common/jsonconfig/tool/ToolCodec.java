package mightydanp.techcore.common.jsonconfig.tool;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mightydanp.techcore.common.jsonconfig.codec.CodecHelpers;
import mightydanp.techcore.common.jsonconfig.codec.MapCodecHelper;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;

public record ToolCodec(String name, Integer useDamage, List<BlockState> effectiveOn, List<Ingredient> handleItems, List<Ingredient> headItems, List<Ingredient> bindingItems, Map<Integer, List<Map<Ingredient, Integer>>> assembleStepsItems, List<Map<Ingredient, Integer>> disassembleItems){
    public static String codecName = "tool";

    public static final Codec<ToolCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(ToolCodec::name),
            Codec.INT.fieldOf("use_damage").forGetter(ToolCodec::useDamage),
            BlockState.CODEC.listOf().fieldOf("effective_on").forGetter(ToolCodec::effectiveOn),
            CodecHelpers.INGREDIENT_CODEC.listOf().optionalFieldOf("handle_items", List.of(Ingredient.EMPTY)).forGetter(ToolCodec::handleItems),
            CodecHelpers.INGREDIENT_CODEC.listOf().optionalFieldOf("head_items", List.of(Ingredient.EMPTY)).forGetter(ToolCodec::headItems),
            CodecHelpers.INGREDIENT_CODEC.listOf().optionalFieldOf("binding_items", List.of(Ingredient.EMPTY)).forGetter(ToolCodec::bindingItems),
            MapCodecHelper.makeEntryListCodec(Codec.INT, Codec.list(MapCodecHelper.makeEntryListCodec(CodecHelpers.INGREDIENT_CODEC, Codec.INT))).fieldOf("assemble_steps_items").forGetter(ToolCodec::assembleStepsItems),
            MapCodecHelper.makeEntryListCodec(CodecHelpers.INGREDIENT_CODEC, Codec.INT).listOf().fieldOf("disassemble_steps_items").forGetter(ToolCodec::disassembleItems)
    ).apply(instance, ToolCodec::new));
}