package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MightyDanp on 3/3/2021.
 */
public class RandomSurfaceGenFeature extends Feature<RandomSurfaceGenFeatureConfig> {
    public RandomSurfaceGenFeature(Codec<RandomSurfaceGenFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(WorldGenLevel iSeedReaderIn, ChunkGenerator chunkGeneratorIn, Random randomIn, BlockPos blockPosIn, RandomSurfaceGenFeatureConfig randomSurfaceGenFeatureConfigIn) {
        boolean canSpawn = false;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        int x = blockPosIn.getX();
        int z = blockPosIn.getZ();
        for (int xx = 0; xx <= 16; xx++) {
            int x2 = xx + x;
            for (int zz = 0; zz <= 16; zz++) {
                int z2 = zz + z;

                int groundHeight = iSeedReaderIn.getHeight(Heightmap.Types.WORLD_SURFACE, x2, z2);
                blockpos$mutable.set(x2, groundHeight, z2);
                BlockState blockState = iSeedReaderIn.getBlockState(blockpos$mutable);
                BlockState blockStateDown = iSeedReaderIn.getBlockState(blockpos$mutable.below());
                //BlockState blockThatCanBePlace = canReplaceStone(randomlyOnSurfaceGenFeatureConfigIn, blockStateDown);
                if (0 == randomIn.nextInt(randomSurfaceGenFeatureConfigIn.rarity)) {
                    List<BlockState> validBlocks = getBlockStates(randomSurfaceGenFeatureConfigIn.validBlocks);

                    if (validBlocks.contains(blockStateDown) && blockState == Blocks.AIR.defaultBlockState()) {
                        List<BlockState> blocks = getBlockStates(randomSurfaceGenFeatureConfigIn.blocks);
                        for (int a = 0; a < blocks.size(); a++) {
                            iSeedReaderIn.setBlock(blockpos$mutable, blocks.get(a), 2);
                            canSpawn = true;
                            //System.out.println(blockpos$mutable.getX() + " " + blockpos$mutable.getY() + " " + blockpos$mutable.getZ() + " " + "/" + randomlyOnSurfaceGenFeatureConfigIn.blocks.getBlock().toString().split(":")[1]);
                        }
                    }
                }
            }
        }
        return canSpawn;
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
