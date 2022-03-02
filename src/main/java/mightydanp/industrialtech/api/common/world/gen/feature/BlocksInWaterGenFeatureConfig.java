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
 * Created by MightyDanp on 2/19/2021.
 */
public class BlocksInWaterGenFeatureConfig implements IFeatureConfig {
    public String name;
    public int rarity;
    public int height;
    public boolean shallowWater;
    public boolean goAboveWater;
    public List<String> biomes;
    public List<String> validBlocks;
    public String topState;
    public String bellowState;


    public static final Codec<BlocksInWaterGenFeatureConfig> CODEC = RecordCodecBuilder.create((a) -> a.group(
            Codec.STRING.fieldOf("name").forGetter(z -> z.name),
            Codec.INT.fieldOf("rarity").forGetter(z -> z.rarity),
            Codec.INT.fieldOf("height").forGetter(z -> z.height),
            Codec.BOOL.fieldOf("shallow_water").forGetter(z -> z.shallowWater),
            Codec.BOOL.fieldOf("go_above_water").forGetter(z -> z.goAboveWater),
            Codec.STRING.listOf().fieldOf("biomes").forGetter(z -> z.biomes),
            Codec.STRING.listOf().fieldOf("valid_blocks").forGetter(z -> z.validBlocks),
            Codec.STRING.fieldOf("top_state").forGetter(z -> z.topState),
            Codec.STRING.fieldOf("bellow_state").forGetter(z -> z.bellowState)
    ).apply(a, BlocksInWaterGenFeatureConfig::new));



    public BlocksInWaterGenFeatureConfig(String nameIn, int rarityIn, int heightIn, boolean shallowWaterIn, boolean goAboveWaterIn, List<String> biomesIn, List<String> validBlocksIn, String topStateIn, String bellowStateIn) {
        name = nameIn;
        rarity = rarityIn;
        height = heightIn;
        shallowWater = shallowWaterIn;
        goAboveWater = goAboveWaterIn;
        biomes = biomesIn;
        validBlocks = validBlocksIn;
        topState = topStateIn;
        bellowState = bellowStateIn;

    }

    public static final class FillerBlockType {
        public static final RuleTest NATURAL_STONE = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchRuleTest(Blocks.NETHERRACK);
        public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
    }
}