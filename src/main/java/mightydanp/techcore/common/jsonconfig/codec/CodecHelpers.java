package mightydanp.techcore.common.jsonconfig.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CodecHelpers {
    public static UnboundedMapCodec<BlockPos, BlockState> blockPosBlockStateUnboundedMapCodec = Codec.unboundedMap(BlockPos.CODEC.fieldOf("block_pos").codec(), BlockState.CODEC.fieldOf("block_state").codec());
}
