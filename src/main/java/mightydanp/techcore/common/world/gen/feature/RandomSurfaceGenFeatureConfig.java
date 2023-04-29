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
 * Created by MightyDanp on 3/3/2021.
 */
public record RandomSurfaceGenFeatureConfig(String name, int rarity, List<String> dimensions, List<String> validBiomes, List<String> invalidBiomes, List<BlockState> validBlockStates, List<BlockState> blockStates) implements FeatureConfiguration {

    public static final Codec<RandomSurfaceGenFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> p_236568_0_.group(
            Codec.STRING.fieldOf("name").forGetter(z -> z.name),
            Codec.INT.fieldOf("rarity").forGetter((a) -> a.rarity),
            Codec.STRING.listOf().fieldOf("dimension").forGetter((config) -> config.dimensions),
            Codec.STRING.listOf().fieldOf("biomeTypesID").forGetter((config) -> config.validBiomes),
            Codec.STRING.listOf().fieldOf("invalidBiomeTypesID").forGetter((config) -> config.invalidBiomes),
            BlockState.CODEC.listOf().fieldOf("valid_blocks").forGetter((a) -> a.validBlockStates),
            BlockState.CODEC.listOf().fieldOf("blocks").forGetter((a) -> a.blockStates)
    ).apply(p_236568_0_, RandomSurfaceGenFeatureConfig::new));
}