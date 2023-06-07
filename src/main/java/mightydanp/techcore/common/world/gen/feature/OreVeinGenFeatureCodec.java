package mightydanp.techcore.common.world.gen.feature;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;
import java.util.Map;

/**
 * Created by MightyDanp on 9/30/2020.
 */
public record OreVeinGenFeatureCodec(String name, int rarity, int minHeight, int maxHeight, int minRadius, int minNumberOfSmallOreLayers, List<String> dimensions, List<String> validBiomes, List<String> invalidBiomes, Map<Either<BlockState, String>, Integer> blockStatesAndChances) implements FeatureConfiguration {
    public static String codecName = "ore_vein";

    public static final Codec<OreVeinGenFeatureCodec> CODEC = RecordCodecBuilder.create((p_236568_0_) -> p_236568_0_.group(
            Codec.STRING.fieldOf("name").forGetter(config -> config.name),
            Codec.INT.fieldOf("rarity").forGetter((config) -> config.rarity),
            Codec.intRange(0, 8).fieldOf("minHeight").forGetter((config) -> config.minHeight),
            Codec.intRange(0, 8).fieldOf("maxHeight").forGetter((config) -> config.maxHeight),
            Codec.intRange(0, 24).fieldOf("minRadius").forGetter((config) -> config.minRadius),
            Codec.intRange(0, 8).fieldOf("minNumberOfSmallOreLayers").forGetter((config) -> config.minNumberOfSmallOreLayers),
            Codec.STRING.listOf().fieldOf("dimension").forGetter((config) -> config.dimensions),
            Codec.STRING.listOf().fieldOf("biomeTypesID").forGetter((config) -> config.validBiomes),
            Codec.STRING.listOf().fieldOf("invalidBiomeTypesID").forGetter((config) -> config.invalidBiomes),
            Codec.unboundedMap(Codec.either(BlockState.CODEC, Codec.STRING), Codec.INT).fieldOf("blocks_states_and_chances").forGetter((a) -> a.blockStatesAndChances)
    ).apply(p_236568_0_, OreVeinGenFeatureCodec::new));
}