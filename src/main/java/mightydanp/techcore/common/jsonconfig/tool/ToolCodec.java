package mightydanp.techcore.common.jsonconfig.tool;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mightydanp.techcore.common.jsonconfig.codec.MapCodecHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public static List<ItemStack> giveItemStacks(Either<String, ItemStack> either){
        List<ItemStack> list = new ArrayList<>();

        either.ifLeft(string -> {
            list.addAll(new ArrayList<>(Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).getTag(TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(string))).stream().map(ItemStack::new).toList()));
        });

        either.ifRight(itemStack -> {
            if (!itemStack.isEmpty()) {
                list.add(itemStack);
            }
        });

        return list;
    }
}