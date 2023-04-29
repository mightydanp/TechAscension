package mightydanp.techcore.common.jsonconfig.definedstructure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mightydanp.techcore.common.jsonconfig.codec.CodecHelpers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Map;

public record DefinedStructureCodec(String name, Map<BlockPos, BlockState> blockPosBlockStateMap) {
    public static String codecName = "defined_structure";

    public static final Codec<DefinedStructureCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(DefinedStructureCodec::name),
            CodecHelpers.blockPosBlockStateUnboundedMapCodec.fieldOf("block_state_of_block_pos").forGetter(DefinedStructureCodec::blockPosBlockStateMap)
    ).apply(instance, DefinedStructureCodec::new));

}
