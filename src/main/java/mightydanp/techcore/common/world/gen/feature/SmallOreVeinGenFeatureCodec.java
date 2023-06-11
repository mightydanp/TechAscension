package mightydanp.techcore.common.world.gen.feature;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

/**
 * Created by MightyDanp on 3/1/2021.
 */
public record SmallOreVeinGenFeatureCodec(String name, int rarity, int minHeight, int maxHeight, List<String> dimensions, List<String> biomeTypesID, List<String> invalidBiomeTypesID, List<Pair<String, Integer>> blocksAndChances) implements FeatureConfiguration {

    public static String codecName = "small_ore";

    public static final Codec<SmallOreVeinGenFeatureCodec> CODEC = RecordCodecBuilder.create((p_236568_0_) -> p_236568_0_.group(
            Codec.STRING.fieldOf("name").forGetter(featureConfig -> featureConfig.name),
            Codec.INT.fieldOf("rarity").forGetter(config -> config.rarity),
            Codec.INT.fieldOf("minHeight").forGetter(config -> config.minHeight),
            Codec.INT.fieldOf("maxHeight").forGetter(config -> config.maxHeight),
            Codec.STRING.listOf().fieldOf("dimension").forGetter(config -> config.dimensions),
            Codec.STRING.listOf().fieldOf("biomeTypesID").forGetter(config -> config.biomeTypesID),
            Codec.STRING.listOf().fieldOf("invalidBiomeTypesID").forGetter(config -> config.invalidBiomeTypesID),
            Codec.pair(Codec.STRING, Codec.INT).listOf().fieldOf("blocks_and_chances").forGetter(config -> config.blocksAndChances)
    ).apply(p_236568_0_, SmallOreVeinGenFeatureCodec::new));
}