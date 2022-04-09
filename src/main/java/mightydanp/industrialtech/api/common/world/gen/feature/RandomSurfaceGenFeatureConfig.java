package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by MightyDanp on 3/3/2021.
 */
public class RandomSurfaceGenFeatureConfig implements FeatureConfiguration {
    public String name;
    public Integer rarity;
    public List<String> dimensions, validBiomes, invalidBiomes, validBlocks, blocks;

    public static final Codec<RandomSurfaceGenFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> p_236568_0_.group(
            Codec.STRING.fieldOf("name").forGetter(z -> z.name),
            Codec.INT.fieldOf("rarity").forGetter((a) -> a.rarity),
            Codec.STRING.listOf().fieldOf("dimension").forGetter((config) -> config.dimensions),
            Codec.STRING.listOf().fieldOf("biomeTypesID").forGetter((config) -> config.validBiomes),
            Codec.STRING.listOf().fieldOf("invalidBiomeTypesID").forGetter((config) -> config.invalidBiomes),
            Codec.STRING.listOf().fieldOf("valid_blocks").forGetter((a) -> a.validBlocks),
            Codec.STRING.listOf().fieldOf("blocks").forGetter((a) -> a.blocks)
    ).apply(p_236568_0_, RandomSurfaceGenFeatureConfig::new));

    public RandomSurfaceGenFeatureConfig(String nameIn, int rarityIn, List<String> dimensionsIn, List<String> validBiomesIn, List<String> invalidBiomesIn, List<String> validBlocksIn, List<String> blocksIn) {
        name = nameIn;
        rarity = rarityIn;
        dimensions = dimensionsIn;
        validBiomes = validBiomesIn;
        invalidBiomes = invalidBiomesIn;
        validBlocks = validBlocksIn;
        blocks = blocksIn;
    }

    public static final class FillerBlockType {
        public static final RuleTest NATURAL_STONE = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchTest(Blocks.NETHERRACK);
        public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchTest(BlockTags.BASE_STONE_NETHER);
    }
}