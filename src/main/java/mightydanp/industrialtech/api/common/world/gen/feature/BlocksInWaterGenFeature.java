package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MightyDanp on 2/19/2021.
 */
public class BlocksInWaterGenFeature extends Feature<BlocksInWaterGenFeatureConfig> {


    public BlocksInWaterGenFeature(Codec<BlocksInWaterGenFeatureConfig> codecIn) {
        super(codecIn);
    }

    public boolean place(ISeedReader iSeedReaderIn, ChunkGenerator chunkGeneratorIn, Random randomIn, BlockPos blockPosIn, BlocksInWaterGenFeatureConfig waterGenConfigIn) {
        boolean canSpawn = false;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int x = blockPosIn.getX();
        int z = blockPosIn.getZ();
        for(int xx = 0; xx <= 16 ; xx++){
            int x2=  xx + x;
            for(int zz = 0; zz <= 16; zz++){
                int z2 = zz + z;
                int groundHeight = iSeedReaderIn.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, x2, z2);
                for(int yy = groundHeight; yy <= 256; yy++){
                    blockpos$mutable.set(x2, yy, z2);
                    BlockState blockStateUp = iSeedReaderIn.getBlockState(blockpos$mutable.above());
                    BlockState blockStateMiddle = iSeedReaderIn.getBlockState(blockpos$mutable);
                    BlockState blockStateDown = iSeedReaderIn.getBlockState(blockpos$mutable.below());

                    List<BlockState> validBlocks = getBlockStates(waterGenConfigIn.validBlocks);

                    if(blockStateMiddle == Blocks.WATER.defaultBlockState() && validBlocks.contains(blockStateDown)){
                        if(waterGenConfigIn.shallowWater && blockStateUp.isAir() && waterGenConfigIn.goAboveWater && blockStateUp == Blocks.AIR.defaultBlockState()){
                            if(0 == randomIn.nextInt(waterGenConfigIn.rarity)) {
                                BlockState topState = getBlockState(waterGenConfigIn.topState);
                                BlockState bellowState = getBlockState(waterGenConfigIn.bellowState);

                                if(bellowState != null) {
                                    for (int i = 0; i <= waterGenConfigIn.height; i++) {
                                        if (blockStateDown == Blocks.WATER.defaultBlockState() || validBlocks.contains(blockStateDown)) {
                                            iSeedReaderIn.setBlock(blockpos$mutable.above(i), bellowState, 2);
                                        }
                                    }
                                }else{
                                    return false;
                                }

                                if(topState != null) {
                                    iSeedReaderIn.setBlock(blockpos$mutable.above(waterGenConfigIn.height), topState, 2);
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

    public BlockState getBlockState(String block) {
        String modID = block.split(":")[0];
        String blockLocalization = block.split(":")[1];

        Block replaceableBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modID, blockLocalization));

        if (replaceableBlock != null && replaceableBlock != Blocks.AIR) {
            return replaceableBlock.defaultBlockState();
        }else {
            return null;
        }
    }

    public List<Block> getBlocks(List<String> blocks){
        List<Block> blockStateList = new ArrayList<>();
        for(String block : blocks){
            String modID = block.split(":")[0];
            String blockLocalization = block.split(":")[1];
            Block replaceableBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modID, blockLocalization));

            if(replaceableBlock != null && replaceableBlock != Blocks.AIR){
                blockStateList.add(replaceableBlock);
            }
        }

        return blockStateList;
    }

    public List<BlockState> getBlockStates(List<String> blocks){
        List<BlockState> blockStateList = new ArrayList<>();
        for(String block : blocks){
            String modID = block.split(":")[0];
            String blockLocalization = block.split(":")[1];
            Block replaceableBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modID, blockLocalization));

            if(replaceableBlock != null && replaceableBlock != Blocks.AIR){
                blockStateList.add(replaceableBlock.defaultBlockState());
            }
        }

        return blockStateList;
    }
}
