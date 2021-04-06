package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
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
public class OreGenFeatureConfig implements IFeatureConfig {

    public static final Codec<OreGenFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> {
        return p_236568_0_.group(Codec.STRING.fieldOf("vein_name").forGetter(z -> {
            return z.veinName;
        }), BlockState.CODEC.listOf().fieldOf("small_ore").forGetter((a) -> {
            return a.smallOre;
        }), BlockState.CODEC.listOf().fieldOf("ore").forGetter((a) -> {
            return a.ore;
        }), BlockState.CODEC.listOf().fieldOf("dense_ore").forGetter((a) -> {
            return a.denseOre;
        }), Codec.intRange(0, 100).listOf().fieldOf("ore_spawn_chance").forGetter(a -> {
            return a.veinBlockChances;
        }), Codec.BOOL.listOf().fieldOf("primary_check").forGetter(a -> {
            return a.primaryChecks;
        }), Codec.INT.fieldOf("rarity").forGetter((a) -> {
            return a.rarity;
        }), Codec.intRange(0, 24).fieldOf("minRadius").forGetter((a) -> {
            return a.minRadius;
        }), Codec.intRange(0, 8).fieldOf("minNumberOfSmallOreLayers").forGetter((a) -> {
            return a.minNumberOfSmallOreLayers;
        }), Codec.intRange(0, 8).fieldOf("minHeight").forGetter((a) -> {
            return a.minHeight;
        }), Codec.intRange(0, 8).fieldOf("maxHeight").forGetter((a) -> {
            return a.maxHeight;
        })).apply(p_236568_0_, OreGenFeatureConfig::new);

    });
    public final int minNumberOfSmallOreLayers;
    public final int rarity;
    public final int minRadius;
    public String veinName;
    public List<BlockState> smallOre;
    public List<BlockState> ore;
    public List<BlockState> denseOre;
    public List<Integer> veinBlockChances;
    public List<Boolean> primaryChecks;
    public int minHeight;
    public int maxHeight;


    public OreGenFeatureConfig(String VeinNameIn, List<BlockState> smallOreIn, List<BlockState> oreIn, List<BlockState> denseOreIn, List<Integer> veinBlockChancesIn, List<Boolean> primaryChecksIn, int minRadiusIn, int rarityIn, int minNumberOfSmallOreLayersIn, int minHeightIn, int maxHeightIn) {
        veinName = VeinNameIn;
        this.minNumberOfSmallOreLayers = minNumberOfSmallOreLayersIn;
        this.smallOre = smallOreIn;
        this.ore = oreIn;
        this.denseOre = denseOreIn;
        this.veinBlockChances = veinBlockChancesIn;
        this.primaryChecks = primaryChecksIn;
        this.rarity = rarityIn;
        this.minRadius = minRadiusIn;
        this.minHeight = minHeightIn;
        this.maxHeight = maxHeightIn;
    }

    public static final class FillerBlockType {
        public static final RuleTest NATURAL_STONE = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchRuleTest(Blocks.NETHERRACK);
        public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
    }
}