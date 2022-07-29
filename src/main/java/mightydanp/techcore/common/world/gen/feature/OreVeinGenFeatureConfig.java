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
 * Created by MightyDanp on 9/30/2020.
 */
public class OreVeinGenFeatureConfig implements FeatureConfiguration {

    public String name;
    public int rarity, minHeight, maxHeight;
    public final int minRadius, minNumberOfSmallOreLayers;
    public List<String> dimensions, validBiomes, invalidBiomes;
    public List<Pair<String, Integer>> blocksAndChances;

    public static final Codec<OreVeinGenFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> p_236568_0_.group(
            Codec.STRING.fieldOf("name").forGetter(config -> config.name),
            Codec.INT.fieldOf("rarity").forGetter((config) -> config.rarity),
            Codec.intRange(0, 8).fieldOf("minHeight").forGetter((config) -> config.minHeight),
            Codec.intRange(0, 8).fieldOf("maxHeight").forGetter((config) -> config.maxHeight),
            Codec.intRange(0, 24).fieldOf("minRadius").forGetter((config) -> config.minRadius),
            Codec.intRange(0, 8).fieldOf("minNumberOfSmallOreLayers").forGetter((config) -> config.minNumberOfSmallOreLayers),
            Codec.STRING.listOf().fieldOf("dimension").forGetter((config) -> config.dimensions),
            Codec.STRING.listOf().fieldOf("biomeTypesID").forGetter((config) -> config.validBiomes),
            Codec.STRING.listOf().fieldOf("invalidBiomeTypesID").forGetter((config) -> config.invalidBiomes),
            Codec.pair(Codec.STRING, Codec.INT).listOf().fieldOf("blocks_and_chances").forGetter((a) -> a.blocksAndChances)
    ).apply(p_236568_0_, OreVeinGenFeatureConfig::new));

    public OreVeinGenFeatureConfig(String nameIn, int rarityIn, int minHeightIn, int maxHeightIn, int minRadiusIn, int minNumberOfSmallOreLayersIn, List<String> dimensionsIn, List<String> validBiomesIn, List<String> invalidBiomesIn, List<Pair<String, Integer>> blocksAndChancesIn) {
        name = nameIn;
        rarity = rarityIn;
        minHeight = minHeightIn;
        maxHeight = maxHeightIn;
        minRadius = minRadiusIn;
        minNumberOfSmallOreLayers = minNumberOfSmallOreLayersIn;
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