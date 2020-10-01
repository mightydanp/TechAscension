package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 9/30/2020.
 */
public class OreGenFeatureConfig implements IFeatureConfig {

    public final RuleTest target;
    public final int size;
    public List<BlockState> blocks = new ArrayList<>();

    public static final Codec<OreGenFeatureConfig> field_236566_a_ = RecordCodecBuilder.create((p_236568_0_) -> {
        return p_236568_0_.group(RuleTest.field_237127_c_.fieldOf("target").forGetter((p_236570_0_) -> {
            return p_236570_0_.target;
        }), BlockState.CODEC.listOf().fieldOf("state").forGetter((a) -> {
            return a.blocks;
        })
        , Codec.intRange(0, 64).fieldOf("size").forGetter((p_236567_0_) -> {
            return p_236567_0_.size;
        })).apply(p_236568_0_, OreGenFeatureConfig::new);
    });



    public OreGenFeatureConfig(RuleTest p_i241989_1_, List<BlockState> p_i241989_2_, int p_i241989_3_) {
        this.size = p_i241989_3_;
        this.blocks = p_i241989_2_;
        this.target = p_i241989_1_;
    }

    public static final class FillerBlockType {
        public static final RuleTest field_241882_a = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest field_241883_b = new BlockMatchRuleTest(Blocks.NETHERRACK);
        public static final RuleTest field_241884_c = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
    }
}