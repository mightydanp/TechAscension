package mightydanp.techcore.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 2/19/2021.
 */
public class BlocksInWaterGenFeature extends Feature<BlocksInWaterGenFeatureConfig> {


    public BlocksInWaterGenFeature(Codec<BlocksInWaterGenFeatureConfig> codecIn) {
        super(codecIn);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlocksInWaterGenFeatureConfig> context)  {
        boolean canSpawn = false;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        int x = context.origin().getX();
        int z = context.origin().getZ();
        for(int xx = 0; xx <= 16 ; xx++){
            int x2=  xx + x;
            for(int zz = 0; zz <= 16; zz++){
                int z2 = zz + z;
                int groundHeight = context.level().getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x2, z2);
                DimensionType worldGenLevel = context.level().dimensionType();
                for(int yy = groundHeight; yy <= 256; yy++){
                    blockpos$mutable.set(x2, yy, z2);
                    BlockState blockStateUp = context.level().getBlockState(blockpos$mutable.above());
                    BlockState blockStateMiddle = context.level().getBlockState(blockpos$mutable);
                    BlockState blockStateDown = context.level().getBlockState(blockpos$mutable.below());

                    List<BlockState> validBlocks = context.config().validBlockStates();

                    if(blockStateMiddle == Blocks.WATER.defaultBlockState() && validBlocks.contains(blockStateDown)){
                        if(context.config().shallowWater() && blockStateUp.isAir() && context.config().goAboveWater() && blockStateUp == Blocks.AIR.defaultBlockState()){
                            if(0 == context.random().nextInt(context.config().rarity())) {
                                BlockState topState = context.config().topBlockState();
                                BlockState bellowState = context.config().bottomBlockState();

                                if(bellowState != null) {
                                    for (int i = 0; i <= context.config().height(); i++) {
                                        if (blockStateDown == Blocks.WATER.defaultBlockState() || validBlocks.contains(blockStateDown)) {
                                            context.level().setBlock(blockpos$mutable.above(i), bellowState, 2);
                                        }
                                    }
                                }else{
                                    return false;
                                }

                                if(topState != null) {
                                    context.level().setBlock(blockpos$mutable.above(context.config().height()), topState, 2);
                                }else{
                                    return false;
                                }

                            }
                        }
                    }
                }
            }

        }

        return canSpawn;
    }
}
