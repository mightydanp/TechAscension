package mightydanp.industrialtech.api.common.world.gen.feature;

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
 * Created by MightyDanp on 3/3/2021.
 */
public class RandomSurfaceGenFeatureConfig implements IFeatureConfig {
    public String name;
    public Integer rarity;
    public List<String> biomes;
    public List<String> validBlocks;
    public List<String> blocks;

    public static final Codec<RandomSurfaceGenFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> p_236568_0_.group(
            Codec.STRING.fieldOf("name").forGetter(z -> z.name),
            Codec.INT.fieldOf("rarity").forGetter((a) -> a.rarity),
            Codec.STRING.listOf().fieldOf("biomes").forGetter((a) -> a.biomes),
            Codec.STRING.listOf().fieldOf("valid_blocks").forGetter((a) -> a.validBlocks),
            Codec.STRING.listOf().fieldOf("blocks").forGetter((a) -> a.blocks)
    ).apply(p_236568_0_, RandomSurfaceGenFeatureConfig::new));



    public RandomSurfaceGenFeatureConfig(String nameIn, int rarityIn, List<String> biomesIn, List<String> validBlocksIn, List<String> blocksIn) {
        name = nameIn;
        rarity = rarityIn;
        biomes = biomesIn;
        validBlocks = validBlocksIn;
        blocks = blocksIn;

    }

    public static final class FillerBlockType {
        public static final RuleTest NATURAL_STONE = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchRuleTest(Blocks.NETHERRACK);
        public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
    }
}