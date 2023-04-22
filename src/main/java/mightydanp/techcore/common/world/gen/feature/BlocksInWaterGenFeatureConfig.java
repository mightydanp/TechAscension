package mightydanp.techcore.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

/**
 * Created by MightyDanp on 2/19/2021.
 */
public record BlocksInWaterGenFeatureConfig(String name, int rarity, int height, boolean shallowWater, boolean goAboveWater, List<String> dimensions, List<String> validBiomes, List<String> invalidBiomes, List<BlockState> validBlockStates, BlockState topBlockState, BlockState bottomBlockState) implements FeatureConfiguration {
        public static final Codec<BlocksInWaterGenFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Codec.STRING.fieldOf("name").forGetter(BlocksInWaterGenFeatureConfig::name),
                Codec.INT.fieldOf("rarity").forGetter(BlocksInWaterGenFeatureConfig::rarity),
                Codec.INT.fieldOf("height").forGetter(BlocksInWaterGenFeatureConfig::height),
                Codec.BOOL.fieldOf("shallow_water").forGetter(BlocksInWaterGenFeatureConfig::shallowWater),
                Codec.BOOL.fieldOf("go_above_water").forGetter(BlocksInWaterGenFeatureConfig::goAboveWater),
                Codec.list(Codec.STRING).fieldOf("dimensions").forGetter(BlocksInWaterGenFeatureConfig::dimensions),
                Codec.list(Codec.STRING).fieldOf("valid_biomes").forGetter(BlocksInWaterGenFeatureConfig::validBiomes),
                Codec.list(Codec.STRING).fieldOf("invalid_biomes").forGetter(BlocksInWaterGenFeatureConfig::invalidBiomes),
                Codec.list(BlockState.CODEC).fieldOf("valid_block_states").forGetter(BlocksInWaterGenFeatureConfig::validBlockStates),
                BlockState.CODEC.fieldOf("top_block_state").forGetter(BlocksInWaterGenFeatureConfig::topBlockState),
                BlockState.CODEC.fieldOf("bottom_block_state").forGetter(BlocksInWaterGenFeatureConfig::bottomBlockState)
        ).apply(instance, BlocksInWaterGenFeatureConfig::new));
    }