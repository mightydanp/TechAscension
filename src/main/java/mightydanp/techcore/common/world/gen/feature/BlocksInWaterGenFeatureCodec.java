package mightydanp.techcore.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;

/**
 * Created by MightyDanp on 2/19/2021.
 */
public record BlocksInWaterGenFeatureCodec(String name, int rarity, int height, boolean shallowWater, boolean goAboveWater, List<String> dimensions, List<String> validBiomes, List<String> invalidBiomes, List<BlockState> validBlockStates, BlockState topBlockState, BlockState bottomBlockState) implements FeatureConfiguration {
    public static String codecName = "blocks_in_water";

    public static final Codec<BlocksInWaterGenFeatureCodec> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Codec.STRING.fieldOf("name").forGetter(BlocksInWaterGenFeatureCodec::name),
                Codec.INT.fieldOf("rarity").forGetter(BlocksInWaterGenFeatureCodec::rarity),
                Codec.INT.fieldOf("height").forGetter(BlocksInWaterGenFeatureCodec::height),
                Codec.BOOL.fieldOf("shallow_water").forGetter(BlocksInWaterGenFeatureCodec::shallowWater),
                Codec.BOOL.fieldOf("go_above_water").forGetter(BlocksInWaterGenFeatureCodec::goAboveWater),
                Codec.list(Codec.STRING).fieldOf("dimensions").forGetter(BlocksInWaterGenFeatureCodec::dimensions),
                Codec.list(Codec.STRING).fieldOf("valid_biomes").forGetter(BlocksInWaterGenFeatureCodec::validBiomes),
                Codec.list(Codec.STRING).fieldOf("invalid_biomes").forGetter(BlocksInWaterGenFeatureCodec::invalidBiomes),
                Codec.list(BlockState.CODEC).fieldOf("valid_block_states").forGetter(BlocksInWaterGenFeatureCodec::validBlockStates),
                BlockState.CODEC.fieldOf("top_block_state").forGetter(BlocksInWaterGenFeatureCodec::topBlockState),
                BlockState.CODEC.fieldOf("bottom_block_state").forGetter(BlocksInWaterGenFeatureCodec::bottomBlockState)
        ).apply(instance, BlocksInWaterGenFeatureCodec::new));
    }