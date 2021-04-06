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
 * Created by MightyDanp on 3/3/2021.
 */
public class RandomlyOnSurfaceGenFeatureConfig implements IFeatureConfig {
    public static final Codec<RandomlyOnSurfaceGenFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> {
        return p_236568_0_.group(Codec.STRING.fieldOf("generation_name").forGetter(z -> {
            return z.generationName;
        }), BlockState.CODEC.fieldOf("blocks").forGetter((a) -> {
            return a.blocks;
        }),BlockState.CODEC.listOf().fieldOf("soil_blocks").forGetter((a) -> {
            return a.soilBlocks;
        }),Codec.intRange(0, 100).fieldOf("spawn_chance").forGetter(a -> {
            return a.chance;
        }),Codec.intRange(0, 100).fieldOf("spawn_rarity").forGetter((a) -> {
            return a.rarity;
        })).apply(p_236568_0_, RandomlyOnSurfaceGenFeatureConfig::new);
    });
    public String generationName;
    public BlockState blocks;
    public List<BlockState> soilBlocks;
    public Integer chance;
    public Integer rarity;

    public RandomlyOnSurfaceGenFeatureConfig(String generationNameIn, BlockState blockStatesIn, List<BlockState> soilBlockStatesIn, Integer chanceIn, int rarityIn) {
        this.generationName = generationNameIn;
        this.soilBlocks = soilBlockStatesIn;
        this.blocks = blockStatesIn;
        this.chance = chanceIn;
        this.rarity = rarityIn;

    }

    public static final class FillerBlockType {
        public static final RuleTest NATURAL_STONE = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchRuleTest(Blocks.NETHERRACK);
        public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
    }
}