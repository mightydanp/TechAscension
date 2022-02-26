package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.serialization.Codec;
import mightydanp.industrialtech.api.common.blocks.OreBlock;
import mightydanp.industrialtech.api.common.blocks.SmallOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MightyDanp on 3/1/2021.
 */
public class SmallOreGenFeature extends Feature<SmallOreGenFeatureConfig> {
    public SmallOreGenFeature(Codec<SmallOreGenFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(ISeedReader iSeedReaderIn, ChunkGenerator chunkGeneratorIn, Random randomIn, BlockPos blockPosIn, SmallOreGenFeatureConfig SmallOreGenFeatureIn) {
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
                    BlockState blockState = iSeedReaderIn.getBlockState(blockpos$mutable);
                    BlockState blockThatCanBePlace = canReplaceStone(SmallOreGenFeatureIn, blockState);
                    if(randomIn.nextInt(2000) < SmallOreGenFeatureIn.rarity) {
                        iSeedReaderIn.setBlock(blockpos$mutable, blockThatCanBePlace, 2);
                        canSpawn = true;
                        if(blockThatCanBePlace.getBlock() instanceof SmallOreBlock) {
                            //System.out.println(blockpos$mutable.getX() + " " + blockpos$mutable.getY() + " " + blockpos$mutable.getZ() + " " + "/" + ((SmallOreBlock) blockThatCanBePlace.getBlock()).name);
                        }
                    }
                }
            }

        }
        return canSpawn;
    }

    public BlockState canReplaceStone(SmallOreGenFeatureConfig config, BlockState blockToBeReplacedIn) {
        Random rand = new Random();
        BlockState blockToBePlaced = Blocks.AIR.defaultBlockState();
        while(blockToBePlaced == Blocks.AIR.defaultBlockState()) {
            int chance200 = rand.nextInt(100);
            int randomPick = rand.nextInt(config.blocks.size());
            if (chance200 < config.chance.get(randomPick)) {
                List<BlockState> small_ore_list = config.blocks.get(randomPick);
                int i = 0;
                for (BlockState small_ore : small_ore_list) {
                    if (small_ore.getBlock() instanceof SmallOreBlock) {
                        Block replaceableBlock = ForgeRegistries.BLOCKS.getValue(((SmallOreBlock) small_ore.getBlock()).replaceBlock);
                        if(replaceableBlock != null){
                            if (blockToBeReplacedIn.getBlock() == replaceableBlock) {
                                return small_ore;
                            } else {
                                i++;
                                if (i == small_ore_list.size()) {
                                    return blockToBeReplacedIn;
                                }
                            }
                        }
                    }
                }
            }
        }
        return blockToBePlaced;
    }
}
