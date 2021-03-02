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
 * Created by MightyDanp on 3/1/2021.
 */
public class SmallOreGenFeatureConfig implements IFeatureConfig {
    public static final Codec<SmallOreGenFeatureConfig> field_236566_a_ = RecordCodecBuilder.create((p_236568_0_) -> {
        return p_236568_0_.group(Codec.STRING.fieldOf("small_ore_name").forGetter(z -> {
                    return z.smallOreName;
                }), BlockState.CODEC.listOf().listOf().fieldOf("small_ore_blocks").forGetter((a) -> {
                    return a.blocks;
                }),Codec.intRange(0, 100).listOf().fieldOf("ore_spawn_chance").forGetter(a -> {
                    return a.chance;
                }),Codec.intRange(0, 100).fieldOf("ore_spawn_rarity").forGetter((a) -> {
                    return a.rarity;
                })).apply(p_236568_0_, (p_i241989_1_, p_i241989_2_, p_i241989_3_, p_i241989_4_) -> new SmallOreGenFeatureConfig(p_i241989_1_, p_i241989_2_, p_i241989_3_, p_i241989_4_));
    });
    public String smallOreName;
    public List<List<BlockState>> blocks;
    public List<Integer> chance;
    public Integer rarity;


    public SmallOreGenFeatureConfig(String smallOreNameIn, List<List<BlockState>> blockStatesIn, List<Integer> chanceIn, int rarityIn) {
        this.smallOreName = smallOreNameIn;
        this.blocks = blockStatesIn;
        this.chance = chanceIn;
        this.rarity = rarityIn;

    }

    public static final class FillerBlockType {
        public static final RuleTest field_241882_a = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest field_241883_b = new BlockMatchRuleTest(Blocks.NETHERRACK);
        public static final RuleTest field_241884_c = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
    }
}