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
 * Created by MightyDanp on 2/19/2021.
 */
public class TopWaterCropConfig implements IFeatureConfig {
    public static final Codec<TopWaterCropConfig> CODEC = RecordCodecBuilder.create((a) -> {
        return a.group(Codec.STRING.fieldOf("crop_name").forGetter(z -> {
            return z.cropName;
                }),BlockState.CODEC.fieldOf("top_state").forGetter(z -> {
                    return z.topState;
                }), BlockState.CODEC.fieldOf("bellow_state").forGetter(z -> {
                    return z.bellowState;
                }), BlockState.CODEC.listOf().fieldOf("soils").forGetter(z -> {
                    return z.soils;
                }), Codec.intRange(0, 100).fieldOf("rarity").forGetter(z -> {
                    return z.rarity;
                }), Codec.BOOL.fieldOf("shallow_water").forGetter(z -> {
                    return z.shallowWater;
                }), Codec.BOOL.fieldOf("go_above_water").forGetter(z -> {
                    return z.goAboveWater;
                }), Codec.INT.fieldOf("how_tall").forGetter(z -> {
                    return z.howTall;
                })).apply(a, (b, c, d, e, f, g, h, i) -> new TopWaterCropConfig(b, c, d, e, f, g, h, i));
    });
    public String cropName;
    public BlockState topState;
    public BlockState bellowState;
    public List<BlockState> soils;
    public int rarity;
    public boolean shallowWater;
    public boolean goAboveWater;
    public int howTall;



    public TopWaterCropConfig(String cropNameIn, BlockState topStateIn, BlockState bellowStateIn, List<BlockState> soilsIn, int rarityIn, boolean shallowWaterIn, boolean goAboveWaterIn, int howTallIn) {
        this.cropName = cropNameIn;
        this.topState = topStateIn;
        this.bellowState = bellowStateIn;
        this.soils = soilsIn;
        this.rarity = rarityIn;
        this.shallowWater = shallowWaterIn;
        this.goAboveWater = goAboveWaterIn;
        this.howTall = howTallIn;
    }

    public static final class FillerBlockType {
        public static final RuleTest NATURAL_STONE = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
        public static final RuleTest NETHERRACK = new BlockMatchRuleTest(Blocks.NETHERRACK);
        public static final RuleTest NETHER_ORE_REPLACEABLES = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
    }
}