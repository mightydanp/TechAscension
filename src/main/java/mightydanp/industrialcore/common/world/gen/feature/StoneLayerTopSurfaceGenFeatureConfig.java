package mightydanp.industrialcore.common.world.gen.feature;

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
 * Created by MightyDanp on 9/8/2021.
 */
public class StoneLayerTopSurfaceGenFeatureConfig implements FeatureConfiguration {
    public String generationName;
    public Integer rarity;
    public boolean inWater;
    public boolean onlySurface;
    public List<ResourceKey<Level>> dimensions;
    public Set<ResourceLocation> dimensionLocations;
    public List<BiomeDictionary.Type> biomeTypes = new ArrayList<>();
    public List<BiomeDictionary.Type> invalidBiomeTypes = new ArrayList<>();
    public List<String> biomeTypesID = new ArrayList<>();
    public List<String> invalidBiomeTypesID = new ArrayList<>();
    public List<String> blocks;
    public List<String> whiteListedBlocks;
    public List<String> blackListedBlocks;

    public static final Codec<StoneLayerTopSurfaceGenFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> p_236568_0_.group(
            Codec.STRING.fieldOf("generation_name").forGetter(z -> z.generationName),
            Codec.INT.fieldOf("spawn_rarity").forGetter((a) -> a.rarity),
            Codec.BOOL.fieldOf("in_water").forGetter((a) -> a.inWater),
            Codec.BOOL.fieldOf("only_surface").forGetter((a) -> a.inWater),
            Codec.list(Level.RESOURCE_KEY_CODEC).fieldOf("dimension").forGetter((config) -> config.dimensions),
            Codec.list(Codec.STRING).fieldOf("biomeTypesID").forGetter((config) -> config.biomeTypesID),
            Codec.list(Codec.STRING).fieldOf("invalidBiomeTypesID").forGetter((config) -> config.invalidBiomeTypesID),
            Codec.list(Codec.STRING).fieldOf("blocks").forGetter((a) -> a.blocks),
            Codec.list(Codec.STRING).fieldOf("white_listed_blocks").forGetter((a) -> a.whiteListedBlocks),
            Codec.list(Codec.STRING).fieldOf("black_listed_blocks").forGetter((a) -> a.blackListedBlocks)
    ).apply(p_236568_0_, StoneLayerTopSurfaceGenFeatureConfig::new));

    public StoneLayerTopSurfaceGenFeatureConfig(String generationNameIn, int rarityIn, boolean inWaterIn, boolean onlySurfaceIn, List<ResourceKey<Level>> dimensionsIn, List<String> validBiomesIn, List<String> invalidBiomesIn, List<String> blocksIn, List<String> whiteListedBlocksIn, List<String> blackListedBlocksIn) {
        generationName = generationNameIn;
        rarity = rarityIn;
        inWater = inWaterIn;
        onlySurface = onlySurfaceIn;
        dimensions = dimensionsIn;
        biomeTypes = validBiomesIn.stream().map(BiomeDictionary.Type::getType).collect(Collectors.toList());
        invalidBiomeTypes = invalidBiomesIn.stream().map(BiomeDictionary.Type::getType).collect(Collectors.toList());
        biomeTypesID = validBiomesIn;
        invalidBiomeTypesID = invalidBiomesIn;
        blocks = blocksIn;
        whiteListedBlocks = whiteListedBlocksIn;
        blackListedBlocks = blackListedBlocksIn;
    }

    public StoneLayerTopSurfaceGenFeatureConfig(String generationNameIn, int rarityIn, boolean inWaterIn, boolean onlySurfaceIn, List<ResourceKey<Level>> dimensionsIn, List<String> blocksIn, List<String> whiteListedBlocksIn, List<String> blackListedBlocksIn) {
        generationName = generationNameIn;
        rarity = rarityIn;
        inWater = inWaterIn;
        onlySurface = onlySurfaceIn;
        dimensions = dimensionsIn;
        dimensionLocations = this.dimensions.stream().map(ResourceKey::location).collect(Collectors.toCollection(ObjectOpenHashSet::new));
        blocks = blocksIn;
        whiteListedBlocks = whiteListedBlocksIn;
        blackListedBlocks = blackListedBlocksIn;
    }

    public StoneLayerTopSurfaceGenFeatureConfig setValidBiomes(List<BiomeDictionary.Type> types){
        biomeTypes = types;
        biomeTypesID = types.stream().map(BiomeDictionary.Type::getName).collect(Collectors.toList());
        return this;
    }

    public StoneLayerTopSurfaceGenFeatureConfig setInvalidBiomes(List<BiomeDictionary.Type> types){
        invalidBiomeTypes = types;
        invalidBiomeTypesID = types.stream().map(BiomeDictionary.Type::getName).collect(Collectors.toList());
        return this;
    }

    public static final class FillerBlockType {
        public static final RuleTest NATURAL_STONE = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchTest(Blocks.NETHERRACK);
        public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchTest(BlockTags.BASE_STONE_NETHER);
    }
}