package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import mightydanp.industrialtech.api.common.blocks.SmallOreBlock;
import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.material.ITMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.CrashReport;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MightyDanp on 3/1/2021.
 */
public class SmallOreVeinGenFeature extends Feature<SmallOreVeinGenFeatureConfig> {
    public SmallOreVeinGenFeature(Codec<SmallOreVeinGenFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(WorldGenLevel iSeedReaderIn, ChunkGenerator chunkGeneratorIn, Random randomIn, BlockPos blockPosIn, SmallOreVeinGenFeatureConfig SmallOreGenFeatureIn) {
        boolean canSpawn = false;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
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
                    BlockState blockThatCanBePlace = replacementStoneLayer(randomIn, SmallOreGenFeatureIn, blockState);
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

    /*
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
                        SmallOreBlock smallOreBlock = (SmallOreBlock) small_ore.getBlock();

                        String modID = smallOreBlock.stoneLayerBlock.split(":")[0];
                        String blockLocalization = smallOreBlock.stoneLayerBlock.split(":")[1];


                        Block replaceableBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modID, blockLocalization));
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

     */

    public BlockState replacementStoneLayer(Random rand, SmallOreVeinGenFeatureConfig config, BlockState blockToBeReplacedIn) {
        BlockState blockToBePlaced = Blocks.AIR.defaultBlockState();

        List<Pair<String, Integer>> veinBlocksAndChances = config.blocksAndChances;

        List<Pair<Block, Integer>> veinBlocksAndChancesThatCanReplace = new ArrayList<>();

        for (Pair<String, Integer> veinBlocksAndChance : veinBlocksAndChances) {
            ITMaterial material = RegistryHandler.MATERIAL.getValue(ResourceLocation.tryParse(veinBlocksAndChance.getFirst()));
            int rarity = veinBlocksAndChance.getSecond();

            if (material != null) {
                    if (material.smallOreList != null) {
                        for (Block block : material.smallOreList) {
                            if (block instanceof SmallOreBlock) {
                                SmallOreBlock ore = (SmallOreBlock) block;
                                String modID = ore.stoneLayerBlock.split(":")[0];
                                String blockLocalization = ore.stoneLayerBlock.split(":")[1];
                                Block replaceableBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modID, blockLocalization));
                                if (replaceableBlock != null && blockToBeReplacedIn.getBlock() == replaceableBlock) {
                                    veinBlocksAndChancesThatCanReplace.add(new Pair<>(ore, rarity));
                                }
                            }
                        }
                    } else {
                        Minecraft.crash(new CrashReport("Your material, (" + material.name + "), is not set for ores", new Throwable()));
                        return null;
                    }
            } else {
                Block replaceableBlock = ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(veinBlocksAndChance.getFirst()));
                if (replaceableBlock != null && blockToBeReplacedIn.getBlock() == replaceableBlock) {
                    veinBlocksAndChancesThatCanReplace.add(new Pair<>(replaceableBlock, rarity));
                }
            }
        }

        while (blockToBePlaced == Blocks.AIR.defaultBlockState()) {
            int randomInt = rand.nextInt(veinBlocksAndChancesThatCanReplace.size());

            if (rand.nextInt(20) <= veinBlocksAndChancesThatCanReplace.get(randomInt).getSecond()) {
                blockToBePlaced = veinBlocksAndChancesThatCanReplace.get(randomInt).getFirst().defaultBlockState();
                return blockToBePlaced;
            }
        }

        return null;
    }
}
