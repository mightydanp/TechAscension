package mightydanp.techcore.common.jsonconfig.fluidstate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record FluidStateCodec(String name)implements FeatureConfiguration {
    public static final Codec<FluidStateCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(FluidStateCodec::name)
    ).apply(instance, FluidStateCodec::new));
}
