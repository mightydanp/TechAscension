package mightydanp.techcore.common.world.gen.feature;

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
 * Created by MightyDanp on 2/19/2021.
 */
public class BlocksInWaterGenFeatureConfig implements FeatureConfiguration {
    public String name;
    public int rarity, height;
    public boolean shallowWater, goAboveWater;
    public List<String> dimensions, validBiomes, invalidBiomes, validBlocks;
    public String topState, bellowState;


    public static final Codec<BlocksInWaterGenFeatureConfig> CODEC = RecordCodecBuilder.create((a) -> a.group(
            Codec.STRING.fieldOf("name").forGetter(z -> z.name),
            Codec.INT.fieldOf("rarity").forGetter(z -> z.rarity),
            Codec.INT.fieldOf("height").forGetter(z -> z.height),
            Codec.BOOL.fieldOf("shallow_water").forGetter(z -> z.shallowWater),
            Codec.BOOL.fieldOf("go_above_water").forGetter(z -> z.goAboveWater),
            Codec.STRING.listOf().fieldOf("dimension").forGetter((config) -> config.dimensions),
            Codec.STRING.listOf().fieldOf("valid_biomes").forGetter((config) -> config.validBiomes),
            Codec.STRING.listOf().fieldOf("invalid_biomes").forGetter((config) -> config.invalidBiomes),
            Codec.STRING.listOf().fieldOf("valid_blocks").forGetter(z -> z.validBlocks),
            Codec.STRING.fieldOf("top_state").forGetter(z -> z.topState),
            Codec.STRING.fieldOf("bellow_state").forGetter(z -> z.bellowState)
    ).apply(a, BlocksInWaterGenFeatureConfig::new));



    public BlocksInWaterGenFeatureConfig(String nameIn, int rarityIn, int heightIn, boolean shallowWaterIn, boolean goAboveWaterIn, List<String> dimensionsIn, List<String> validBiomesIn, List<String> invalidBiomesIn, List<String> validBlocksIn, String topStateIn, String bellowStateIn) {
        name = nameIn;
        rarity = rarityIn;
        height = heightIn;
        shallowWater = shallowWaterIn;
        goAboveWater = goAboveWaterIn;
        dimensions = dimensionsIn;
        validBiomes = validBiomesIn;
        invalidBiomes = invalidBiomesIn;
        validBlocks = validBlocksIn;
        topState = topStateIn;
        bellowState = bellowStateIn;
    }

    public static final class FillerBlockType {
        public static final RuleTest NATURAL_STONE = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchTest(Blocks.NETHERRACK);
        public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchTest(BlockTags.BASE_STONE_NETHER);
    }
}