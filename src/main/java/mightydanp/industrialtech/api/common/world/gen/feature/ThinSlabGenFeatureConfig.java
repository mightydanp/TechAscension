package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
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
public class ThinSlabGenFeatureConfig implements FeatureConfiguration {
    public String generationName;
    public List<BlockState> blocks;
    public Integer rarity;
    public List<ResourceKey<Level>> dimensions;
    public Set<ResourceLocation> dimensionLocations;
    public List<BiomeDictionary.Type> biomeTypes = new ArrayList<>();
    public List<BiomeDictionary.Type> invalidBiomeTypes = new ArrayList<>();
    public List<String> biomeTypesID = new ArrayList<>();
    public List<String> invalidBiomeTypesID = new ArrayList<>();
    public boolean inWater;
    public boolean outOfWater;

    public static final Codec<ThinSlabGenFeatureConfig> CODEC = RecordCodecBuilder.create((p_236568_0_) -> p_236568_0_.group(
            Codec.STRING.fieldOf("generation_name").forGetter(z -> z.generationName),
            BlockState.CODEC.listOf().fieldOf("blocks").forGetter((a) -> a.blocks),
            Codec.INT.fieldOf("spawn_rarity").forGetter((a) -> a.rarity),
            Codec.list(Level.RESOURCE_KEY_CODEC).fieldOf("dimension").forGetter((config) -> config.dimensions),
            Codec.list(Codec.STRING).fieldOf("biomeTypesID").forGetter((config) -> config.biomeTypesID),
            Codec.list(Codec.STRING).fieldOf("invalidBiomeTypesID").forGetter((config) -> config.invalidBiomeTypesID),
            Codec.BOOL.fieldOf("in_water").forGetter((a) -> a.inWater),
            Codec.BOOL.fieldOf("out_of_water").forGetter((a) -> a.outOfWater)
    ).apply(p_236568_0_, ThinSlabGenFeatureConfig::new));

    public ThinSlabGenFeatureConfig(String generationNameIn, List<BlockState> blockStatesIn, int rarityIn, List<ResourceKey<Level>> dimensionsIn, List<String> validBiomesIn, List<String> invalidBiomesIn, boolean inWaterIn, boolean outOfWaterIn) {
        this.generationName = generationNameIn;
        this.blocks = blockStatesIn;
        this.rarity = rarityIn;
        dimensions = dimensionsIn;
        biomeTypes = validBiomesIn.stream().map(BiomeDictionary.Type::getType).collect(Collectors.toList());
        invalidBiomeTypes = invalidBiomesIn.stream().map(BiomeDictionary.Type::getType).collect(Collectors.toList());
        biomeTypesID = validBiomesIn;
        invalidBiomeTypesID = invalidBiomesIn;
        inWater = inWaterIn;
        outOfWater = outOfWaterIn;
    }

    public ThinSlabGenFeatureConfig(String generationNameIn, List<BlockState> blockStatesIn, int rarityIn, List<ResourceKey<Level>> dimensionsIn, boolean inWaterIn, boolean outOfWaterIn) {
        this.generationName = generationNameIn;
        this.blocks = blockStatesIn;
        this.rarity = rarityIn;
        dimensions = dimensionsIn;
        dimensionLocations = this.dimensions.stream().map(ResourceKey::location).collect(Collectors.toCollection(ObjectOpenHashSet::new));
        inWater = inWaterIn;
        outOfWater = outOfWaterIn;
    }

    public ThinSlabGenFeatureConfig setValidBiomes(List<BiomeDictionary.Type> types){
        biomeTypes = types;
        biomeTypesID = types.stream().map(BiomeDictionary.Type::getName).collect(Collectors.toList());
        return this;
    }

    public ThinSlabGenFeatureConfig setInvalidBiomes(List<BiomeDictionary.Type> types){
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