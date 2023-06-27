package mightydanp.techcore.common.jsonconfig.codec;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import mightydanp.techascension.common.TechAscension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;

public class CodecHelpers {
    public static UnboundedMapCodec<BlockPos, BlockState> blockPosBlockStateUnboundedMapCodec = Codec.unboundedMap(BlockPos.CODEC.fieldOf("block_pos").codec(), BlockState.CODEC.fieldOf("block_state").codec());


    /* from Ratatosk
     * @ https://github.com/FoundryMC/Alembic/blob/main/src/main/java/foundry/alembic/util/CodecUtil.java#L86-L99
     */
    public static Codec<Ingredient> INGREDIENT_CODEC = Codec.of(
            new Encoder<>() {
                @Override
                public <T> DataResult<T> encode(Ingredient input, DynamicOps<T> ops, T prefix) {
                    return DataResult.success(JsonOps.INSTANCE.convertTo(ops, input.toJson()));
                }
            },
            new Decoder<>() {
                @Override
                public <T> DataResult<Pair<Ingredient, T>> decode(DynamicOps<T> ops, T input) {
                    TechAscension.LOGGER.info(ops);
                    return DataResult.success(Pair.of(Ingredient.fromJson((JsonElement)input), input));
                }
            }
    );
}
