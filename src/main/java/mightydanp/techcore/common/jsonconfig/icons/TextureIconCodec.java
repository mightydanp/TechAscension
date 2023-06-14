package mightydanp.techcore.common.jsonconfig.icons;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mightydanp.techcore.common.jsonconfig.fluidstate.FluidStateCodec;

/**
 * Created by MightyDanp on 12/29/2021.
 */


public record TextureIconCodec(String name) {
    public static String codecName = "texture_icon";

    public static final Codec<TextureIconCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(TextureIconCodec::name)
    ).apply(instance, TextureIconCodec::new));
}