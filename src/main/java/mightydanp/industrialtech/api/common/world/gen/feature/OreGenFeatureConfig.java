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
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 9/30/2020.
 */
public class OreGenFeatureConfig implements IFeatureConfig {

    public String veinName;
    public final int rarity;
    public int minHeight;
    public int maxHeight;
    public final int minRadius;
    public final int minNumberOfSmallOreLayers;
    public List<Pair<String, Integer>> veinBlocksAndChances;

    public OreGenFeatureConfig(String VeinNameIn, int rarityIn, int minHeightIn, int maxHeightIn, int minRadiusIn, int minNumberOfSmallOreLayersIn, List<Pair<String, Integer>> veinBlocksAndChancesIn) {
        veinName = VeinNameIn;
        rarity = rarityIn;
        minHeight = minHeightIn;
        maxHeight = maxHeightIn;
        minRadius = minRadiusIn;
        minNumberOfSmallOreLayers = minNumberOfSmallOreLayersIn;
        veinBlocksAndChances = veinBlocksAndChancesIn;
    }

    public static final Codec<OreGenFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> {
        return p_236568_0_.group(Codec.STRING.fieldOf("vein_name").forGetter(z -> z.veinName), Codec.INT.fieldOf("rarity").forGetter((a) -> {
            return a.rarity;
        }), Codec.intRange(0, 8).fieldOf("minHeight").forGetter((a) -> {
            return a.minHeight;
        }), Codec.intRange(0, 8).fieldOf("maxHeight").forGetter((a) -> {
            return a.maxHeight;
        }), Codec.intRange(0, 24).fieldOf("minRadius").forGetter((a) -> {
            return a.minRadius;
        }), Codec.intRange(0, 8).fieldOf("minNumberOfSmallOreLayers").forGetter((a) -> {
            return a.minNumberOfSmallOreLayers;
        }), Codec.pair(Codec.STRING, Codec.INT).listOf().fieldOf("vein_blocks_and_chances").forGetter((a) -> {
            return a.veinBlocksAndChances;
        })).apply(p_236568_0_, OreGenFeatureConfig::new);
    });





    public static final class FillerBlockType {
        public static final RuleTest NATURAL_STONE = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchRuleTest(Blocks.NETHERRACK);
        public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
    }
}