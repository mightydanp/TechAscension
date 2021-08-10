package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

/**
 * Created by MightyDanp on 2/19/2021.
 */
public class TopWaterCrop extends Feature<TopWaterCropConfig> {


    public TopWaterCrop(Codec<TopWaterCropConfig> codecIn) {
        super(codecIn);
    }

    public boolean place(ISeedReader iSeedReaderIn, ChunkGenerator chunkGeneratorIn, Random randomIn, BlockPos blockPosIn, TopWaterCropConfig topWaterCropConfigIn) {
        boolean canSpawn = false;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int groundHeight = chunkGeneratorIn.getSpawnHeight();
        int x = blockPosIn.getX();
        int z = blockPosIn.getZ();
        for(int xx = 0; xx <= 16 ; xx++){
            int x2=  xx + x;
            for(int zz = 0; zz <= 16; zz++){
                int z2 = zz + z;
                for(int yy = 0; yy <= groundHeight; yy++){
                    blockpos$mutable.set(x2, yy, z2);
                    BlockState blockStateUp = iSeedReaderIn.getBlockState(blockpos$mutable.above());
                    BlockState blockStateMiddle = iSeedReaderIn.getBlockState(blockpos$mutable);
                    BlockState blockStateDown = iSeedReaderIn.getBlockState(blockpos$mutable.below());
                    if(blockStateMiddle == Blocks.WATER.defaultBlockState() && topWaterCropConfigIn.soils.contains(blockStateDown)){
                        if(topWaterCropConfigIn.shallowWater && blockStateUp.isAir() && topWaterCropConfigIn.goAboveWater){
                            int randomNumber = randomIn.nextInt(100);
                            if(randomNumber < topWaterCropConfigIn.rarity) {
                                iSeedReaderIn.setBlock(blockpos$mutable.above(topWaterCropConfigIn.howTall), topWaterCropConfigIn.topState, 2);
                                System.out.println(x2 + " " + yy + " " + z2 + " " + "/" + "cattail");
                                for (int i = 0; i <= topWaterCropConfigIn.howTall; i++) {
                                    if(blockStateDown == Blocks.WATER.defaultBlockState()) {
                                        iSeedReaderIn.setBlock(blockpos$mutable.above(i), topWaterCropConfigIn.bellowState, 2);
                                    }
                                }
                                canSpawn= true;
                            }
                        }
                    }
                }
            }

        }
        return canSpawn;
    }
}
