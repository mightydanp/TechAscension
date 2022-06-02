package mightydanp.industrialcore.common.world.gen.feature;

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
public class SmallOreVeinGenFeatureConfig implements FeatureConfiguration {
    public String name;
    public Integer rarity;
    public int minHeight,maxHeight;
    public List<String> dimensions, validBiomes, invalidBiomes, biomeTypesID, invalidBiomeTypesID;
    public List<Pair<String, Integer>> blocksAndChances;

    public static final Codec<SmallOreVeinGenFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> p_236568_0_.group(
            Codec.STRING.fieldOf("name").forGetter(featureConfig -> featureConfig.name),
            Codec.INT.fieldOf("rarity").forGetter(config -> config.rarity),
            Codec.INT.fieldOf("minHeight").forGetter(config -> config.minHeight),
            Codec.INT.fieldOf("maxHeight").forGetter(config -> config.maxHeight),
            Codec.STRING.listOf().fieldOf("dimension").forGetter(config -> config.dimensions),
            Codec.STRING.listOf().fieldOf("biomeTypesID").forGetter(config -> config.biomeTypesID),
            Codec.STRING.listOf().fieldOf("invalidBiomeTypesID").forGetter(config -> config.invalidBiomeTypesID),
            Codec.pair(Codec.STRING, Codec.INT).listOf().fieldOf("blocks_and_chances").forGetter(config -> config.blocksAndChances)
    ).apply(p_236568_0_, SmallOreVeinGenFeatureConfig::new));

    public SmallOreVeinGenFeatureConfig(String nameIn, int rarityIn, int minHeightIn, int maxHeightIn, List<String> dimensionsIn, List<String> validBiomesIn, List<String> invalidBiomesIn, List<Pair<String, Integer>> blocksAndChancesIn) {
        name = nameIn;
        rarity = rarityIn;
        minHeight = minHeightIn;
        maxHeight = maxHeightIn;
        dimensions = dimensionsIn;
        validBiomes = validBiomesIn;
        invalidBiomes = invalidBiomesIn;
        blocksAndChances = blocksAndChancesIn;
    }

    public static final class FillerBlockType {
        public static final RuleTest NATURAL_STONE = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchTest(Blocks.NETHERRACK);
        public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchTest(BlockTags.BASE_STONE_NETHER);
    }
}