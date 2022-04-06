package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

/**
 * Created by MightyDanp on 9/8/2021.
 */
public class ThinSlabGenFeatureConfig implements FeatureConfiguration {
    public String generationName;
    public List<BlockState> blocks;
    public Integer rarity;
    public boolean inWater;
    public boolean outOfWater;

    public static final Codec<ThinSlabGenFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> p_236568_0_.group(
            Codec.STRING.fieldOf("generation_name").forGetter(z -> z.generationName),
            BlockState.CODEC.listOf().fieldOf("blocks").forGetter((a) -> a.blocks),
            Codec.INT.fieldOf("spawn_rarity").forGetter((a) -> a.rarity),
            Codec.BOOL.fieldOf("in_water").forGetter((a) -> a.inWater),
            Codec.BOOL.fieldOf("out_of_water").forGetter((a) -> a.outOfWater)
    ).apply(p_236568_0_, ThinSlabGenFeatureConfig::new));

    public ThinSlabGenFeatureConfig(String generationNameIn, List<BlockState> blockStatesIn, int rarityIn, boolean inWaterIn, boolean outOfWaterIn) {
        this.generationName = generationNameIn;
        this.blocks = blockStatesIn;
        this.rarity = rarityIn;
        inWater = inWaterIn;
        outOfWater = outOfWaterIn;

    }

    public static final class FillerBlockType {
        public static final RuleTest NATURAL_STONE = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchTest(Blocks.NETHERRACK);
        public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchTest(BlockTags.BASE_STONE_NETHER);
    }
}