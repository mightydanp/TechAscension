package mightydanp.techcore.common.jsonconfig.material.ore;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * Created by MightyDanp on 11/27/2021.
 */
public record OreTypeCodec (String name){
    public static String codecName = "ore_type";

    public static final Codec<OreTypeCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(OreTypeCodec::name)
    ).apply(instance, OreTypeCodec::new));
    
}
