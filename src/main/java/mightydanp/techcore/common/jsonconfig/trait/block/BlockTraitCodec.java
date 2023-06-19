package mightydanp.techcore.common.jsonconfig.trait.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record BlockTraitCodec(String registry, Integer color, Double kilograms) {
    public static String codecName ="block_trait";

    public static final Codec<BlockTraitCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("registry").forGetter(BlockTraitCodec::registry),
            Codec.INT.fieldOf("color").forGetter(BlockTraitCodec::color),
            Codec.DOUBLE.fieldOf("kilograms").forGetter(BlockTraitCodec::kilograms)
    ).apply(instance, BlockTraitCodec::new));

    //size
    public Double meters() {
        return 1.0D;
    }
}
