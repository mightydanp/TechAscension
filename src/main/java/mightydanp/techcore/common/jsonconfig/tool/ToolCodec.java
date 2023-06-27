package mightydanp.techcore.common.jsonconfig.tool;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mightydanp.techcore.common.jsonconfig.codec.MapCodecHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Map;

public record ToolCodec(String name, Integer useDamage, List<BlockState> effectiveOn, List<Either<String, ItemStack>> handleItems, List<Either<String, ItemStack>> headItems, List<Either<String, ItemStack>> bindingItems, Map<Integer, List<List<Either<String, ItemStack>>>> assembleStepsItems, List<List<Either<String, ItemStack>>> disassembleItems){
    public static String codecName = "tool";

    public static final Codec<ToolCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(ToolCodec::name),
            Codec.INT.fieldOf("use_damage").forGetter(ToolCodec::useDamage),
            BlockState.CODEC.listOf().fieldOf("effective_on").forGetter(ToolCodec::effectiveOn),
            Codec.either(Codec.STRING, ItemStack.CODEC).listOf().optionalFieldOf("handle_items", List.of(Either.right(ItemStack.EMPTY))).forGetter(ToolCodec::handleItems),
            Codec.either(Codec.STRING, ItemStack.CODEC).listOf().optionalFieldOf("head_items", List.of(Either.right(ItemStack.EMPTY))).forGetter(ToolCodec::headItems),
            Codec.either(Codec.STRING, ItemStack.CODEC).listOf().optionalFieldOf("binding_items", List.of(Either.right(ItemStack.EMPTY))).forGetter(ToolCodec::bindingItems),
            MapCodecHelper.makeEntryListCodec(Codec.INT, Codec.list(Codec.either(Codec.STRING, ItemStack.CODEC).listOf())).fieldOf("assemble_steps_items").forGetter(ToolCodec::assembleStepsItems),
            Codec.list(Codec.either(Codec.STRING, ItemStack.CODEC).listOf()).fieldOf("disassemble_steps_items").forGetter(ToolCodec::disassembleItems)
    ).apply(instance, ToolCodec::new));

    public static void something(){
        //Ingredient.
        //ForgeRegistries.ITEMS.getEntries().stream().filter(resourceKeyItemEntry -> r)
    }
}