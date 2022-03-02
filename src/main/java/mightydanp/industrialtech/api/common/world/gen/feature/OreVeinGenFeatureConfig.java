package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;

import java.util.List;

/**
 * Created by MightyDanp on 9/30/2020.
 */
public class OreVeinGenFeatureConfig implements IFeatureConfig {

    public String name;
    public final int rarity;
    public int minHeight;
    public int maxHeight;
    public final int minRadius;
    public final int minNumberOfSmallOreLayers;
    public List<String> biomes;
    public List<Pair<String, Integer>> blocksAndChances;

    public OreVeinGenFeatureConfig(String nameIn, int rarityIn, int minHeightIn, int maxHeightIn, int minRadiusIn, int minNumberOfSmallOreLayersIn, List<String> biomesIn, List<Pair<String, Integer>> blocksAndChancesIn) {
        name = nameIn;
        rarity = rarityIn;
        minHeight = minHeightIn;
        maxHeight = maxHeightIn;
        minRadius = minRadiusIn;
        minNumberOfSmallOreLayers = minNumberOfSmallOreLayersIn;
        biomes = biomesIn;
        blocksAndChances = blocksAndChancesIn;
    }

    public static final Codec<OreVeinGenFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> p_236568_0_.group(
              Codec.STRING.fieldOf("name").forGetter(z -> z.name)
            , Codec.INT.fieldOf("rarity").forGetter((a) -> a.rarity)
            , Codec.intRange(0, 8).fieldOf("minHeight").forGetter((a) -> a.minHeight)
            , Codec.intRange(0, 8).fieldOf("maxHeight").forGetter((a) -> a.maxHeight)
            , Codec.intRange(0, 24).fieldOf("minRadius").forGetter((a) -> a.minRadius)
            , Codec.intRange(0, 8).fieldOf("minNumberOfSmallOreLayers").forGetter((a) -> a.minNumberOfSmallOreLayers)
            , Codec.STRING.listOf().fieldOf("biomes").forGetter((a) -> a.biomes)
            , Codec.pair(Codec.STRING, Codec.INT).listOf().fieldOf("blocks_and_chances").forGetter((a) -> a.blocksAndChances)
    ).apply(p_236568_0_, OreVeinGenFeatureConfig::new));





    public static final class FillerBlockType {
        public static final RuleTest NATURAL_STONE = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchRuleTest(Blocks.NETHERRACK);
        public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
    }
}