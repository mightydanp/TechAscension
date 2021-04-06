package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.serialization.Codec;
import mightydanp.industrialtech.api.common.blocks.DenseOreBlock;
import mightydanp.industrialtech.api.common.blocks.OreBlock;
import mightydanp.industrialtech.api.common.blocks.SmallOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Random;

public class OreGenFeature extends Feature<OreGenFeatureConfig> {
    public OreGenFeature(Codec<OreGenFeatureConfig> p_i231976_1_) {
        super(p_i231976_1_);
    }

    public static List<BlockPos> getNearbyVeins(long seed, int chunkX, int chunkZ, Random rand, int radius, OreGenFeatureConfig oreGenFeatureConfig) {
        List<BlockPos> veins = new ArrayList<>();
        for (int x = chunkX - radius; x <= chunkX + radius; x++) {
            for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
                if (getVeinAtChunk(seed, x, z, rand, oreGenFeatureConfig) != null) {
                    veins.add(getVeinAtChunk(seed, x, z, rand, oreGenFeatureConfig));
                }
            }
        }
        return veins;
    }

    private static BlockPos getVeinAtChunk(long seed, int chunkX, int chunkZ, Random rand, OreGenFeatureConfig oreGenFeatureConfig) {
        rand.setSeed(seed + chunkX * 341873128712L + chunkZ * 132897987541L);
        //rand.setSeed(worldSeed);
        int chance = rand.nextInt(20);
        if (chance == 0) {
            return new BlockPos(chunkX, 0, chunkZ);
        }
        return null;
    }

    public boolean place(ISeedReader iSeedReaderIn, ChunkGenerator chunkGeneratorIn, Random randomIn, BlockPos blockPosIn, OreGenFeatureConfig oreGenFeatureConfig) {
        ChunkPos chunkPos = new ChunkPos(blockPosIn);
        List<BlockPos> getValidVeins = getNearbyVeins(iSeedReaderIn.getSeed(), chunkPos.x, chunkPos.z, randomIn, 9, oreGenFeatureConfig);
        randomIn.setSeed(iSeedReaderIn.getSeed() + chunkPos.x * 341873128712L + chunkPos.z * 132897987541L);
        boolean canSpawn = false;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int radius = oreGenFeatureConfig.minRadius;//24;
        int diameter = radius * 2;
        int maxSmallOreBlocksExtend = oreGenFeatureConfig.minNumberOfSmallOreLayers;//3;
        int x = blockPosIn.getX() + 8;
        int y = blockPosIn.getY();
        int z = blockPosIn.getZ() + 8;

        int minHeight = 3 + (maxSmallOreBlocksExtend * 2);
        int maxHeight = 7 + (maxSmallOreBlocksExtend);

        BlockState block = iSeedReaderIn.getBlockState(blockPosIn);
        if(y > chunkGeneratorIn.getSpawnHeight()){
            BlockPos pos = new BlockPos(x, y, z);
            y = oreGenFeatureConfig.minHeight + maxHeight + maxHeight/2;
        }

        BlockPos upperLeft = new BlockPos(x, maxHeight, z);
        BlockPos upperRight = new BlockPos(x - 1, maxHeight, z);
        BlockPos lowerLeft = new BlockPos(x, maxHeight, z - 1);
        BlockPos lowerRight = new BlockPos(x - 1, maxHeight, z - 1);


        int minX = x - (radius - randomIn.nextInt(radius));
        int maxX = x + (radius - randomIn.nextInt(radius));
        int minY = y;
        int maxY = y + minHeight + (randomIn.nextInt(maxHeight));
        int minZ = z - (radius - randomIn.nextInt(radius));
        int maxZ = z + (radius - randomIn.nextInt(radius));

        //class provides a iterator                                                             goes throw things to give you
        Iterable<BlockPos> cubePos = () -> BlockPos.betweenClosedStream(minX, minY, minZ, maxX, maxY, maxZ).iterator();
        List<BlockPos> veinBlockPos = new ArrayList<>();

        for (BlockPos pos : cubePos) {
            veinBlockPos.add(pos);
        }

        /*
        //                           this equals
        new Iterable<BlockPos>(){
            @Override//This
            //                         \/
            public Iterator<BlockPos> iterator() {
                return BlockPos.getAllInBox(0,0, 0, 16, 16, 16).iterator();
            }
        };

        BlockPos test = null;

        //everything here test will = null.

        for(BlockPos block : cubePos){
            if(test == null){
                //sets first Blockpos but makes new instance. Makes it to where its not changeable
                test = block.toImmutable();
                //test will always = last blockPos of the for loop. Makes it to where it can change.
                test = block;
            }
        }
        //if the for loop is never executed then test = null
        //everything here test will not = null.
        */

        //to-do fix the getVeinAtChunk and getNearbyVeins to make sure veins do not overlap.\\
        int chance = randomIn.nextInt(oreGenFeatureConfig.rarity);
        if (chance == 0) {
            if(y > chunkGeneratorIn.getSpawnHeight()){
                y = chunkGeneratorIn.getSpawnHeight() - maxHeight;
            }
            /*
            iSeedReaderIn.setBlockState(upperLeft, Blocks.COAL_BLOCK.getDefaultState(), 2);
            iSeedReaderIn.setBlockState(upperRight, Blocks.GOLD_BLOCK.getDefaultState(), 2);
            iSeedReaderIn.setBlockState(lowerLeft, Blocks.OBSIDIAN.getDefaultState(), 2);
            iSeedReaderIn.setBlockState(lowerRight, Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
            iSeedReaderIn.setBlockState(new BlockPos(minX, maxY, minZ), Blocks.COAL_BLOCK.getDefaultState(), 2);
            iSeedReaderIn.setBlockState(new BlockPos(minX, maxY, maxZ), Blocks.GOLD_BLOCK.getDefaultState(), 2);
            iSeedReaderIn.setBlockState(new BlockPos(maxX, maxY, minZ), Blocks.OBSIDIAN.getDefaultState(), 2);
            iSeedReaderIn.setBlockState(new BlockPos(maxX, maxY, maxZ), Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
            */
            System.out.println(x + " " + y + " " + z + " " + "/" + oreGenFeatureConfig.veinName);


            for (BlockPos pos : cubePos) {
                int xBlockPos = pos.getX();
                int yBlockPos = pos.getY();
                int zBlockPos = pos.getZ();

                blockpos$mutable.set(xBlockPos, yBlockPos, zBlockPos);
                BlockState replacedBlock = iSeedReaderIn.getBlockState(blockpos$mutable);
                BlockState smallOreThatCanReplaceBlock = canSmallOreReplaceStone(oreGenFeatureConfig, replacedBlock);
                BlockState oreThatCanReplaceBlock = canOreReplaceStone(oreGenFeatureConfig, replacedBlock);
                BlockState denseOreThatCanReplaceBlock = canDenseOreReplaceStone(oreGenFeatureConfig, replacedBlock);

                if (randomIn.nextInt(100) <= 20 && smallOreThatCanReplaceBlock != null && oreThatCanReplaceBlock != null && denseOreThatCanReplaceBlock != null) {
                    //to-do sides have ores fix bellow to make sure that it gets fixed\\
                    if (xBlockPos < minX + maxSmallOreBlocksExtend || yBlockPos < minY + maxSmallOreBlocksExtend || zBlockPos < minZ + maxSmallOreBlocksExtend ||
                            xBlockPos > maxX - maxSmallOreBlocksExtend || yBlockPos > maxY - maxSmallOreBlocksExtend || zBlockPos > maxZ - maxSmallOreBlocksExtend) {
                        for (int i = 0; i <= maxSmallOreBlocksExtend; i++) {
                            if (xBlockPos == minX + i || yBlockPos == minY + i || zBlockPos == minZ + i ||
                                    xBlockPos == maxX - i || yBlockPos == maxY - i || zBlockPos == maxZ - i) {
                                if (i == 0) {
                                    iSeedReaderIn.setBlock(pos, smallOreThatCanReplaceBlock, 2);
                                } else {
                                    if (randomIn.nextInt(100) <= (20 * i)) {
                                        iSeedReaderIn.setBlock(pos, oreThatCanReplaceBlock, 2);
                                    } else {
                                        iSeedReaderIn.setBlock(pos, denseOreThatCanReplaceBlock, 2);
                                    }
                                }
                            }
                        }
                    } else {
                        //To-do Dense ore is more dense in small vein, and less in bigger veins\\
                        if (randomIn.nextInt(75) == 0) {
                            iSeedReaderIn.setBlock(pos, denseOreThatCanReplaceBlock, 2);
                        } else {
                            iSeedReaderIn.setBlock(pos, oreThatCanReplaceBlock, 2);
                        }
                    }

                }
            }
            canSpawn = true;
        }
/*
            for (BlockPos pos : getValidVeins) {
                if (pos != null) {
                    //System.out.println(pos.getX() + " " + 120 + " " + pos.getY() + " a vein can generate");
                    if (getVeinAtChunk(pos.getX(), pos.getY(), randomIn, oreGenFeatureConfig) != null) {
                        //System.out.println(pos.getX() + " " + 120 + " " + pos.getY() + " true");
                    }
                }
            }
 */


        return canSpawn;
    }

    public List<BlockState> findReplacementOres(List<BlockState> blockStateListIn, BlockState replaceBlockStateIn) {
        List<BlockState> oresThatCanReplace = new ArrayList<>();
        for (BlockState blockState : blockStateListIn) {
            Block block = blockState.getBlock();
            Block replaceBlock = replaceBlockStateIn.getBlock();
            if (block instanceof OreBlock) {
                if (replaceBlock == ((OreBlock) block).replaceableBlock.getBlock()) {
                    oresThatCanReplace.add(blockState);
                }
            }
        }
        return oresThatCanReplace;
    }

    public BlockState canSmallOreReplaceStone(OreGenFeatureConfig config, BlockState blockToBeReplacedIn) {
        Random rand = new Random();
        List<BlockState> originalOres = config.smallOre;
        List<Integer> originalOresChances = config.veinBlockChances;
        List<BlockState> oreThatCanReplace = new ArrayList<>();
        List<Integer> oreChancesCanReplace = new ArrayList<>();
        BlockState blockToBePlaced = Blocks.AIR.defaultBlockState();
        if (originalOres.size() == originalOresChances.size()) {
            for (int i = 0; i < originalOres.size(); i++) {
                Block block = originalOres.get(i).getBlock();
                Block replaceBlock = blockToBeReplacedIn.getBlock();
                if (block instanceof SmallOreBlock) {
                    if (replaceBlock == ((SmallOreBlock) block).replaceableBlock.getBlock()) {
                        oreThatCanReplace.add(originalOres.get(i));
                        oreChancesCanReplace.add(originalOresChances.get(i));
                    }
                }
            }

            if (oreThatCanReplace.size() != 0 && oreChancesCanReplace.size() != 0) {
                while (blockToBePlaced == Blocks.AIR.defaultBlockState()) {
                    int randomInt = rand.nextInt(oreThatCanReplace.size());
                    if (rand.nextInt(20) <= oreChancesCanReplace.get(randomInt)) {
                        blockToBePlaced = oreThatCanReplace.get(randomInt);
                        return blockToBePlaced;
                    }
                }
            } else {
                return null;
            }
        }
        return null;
    }

    public BlockState canOreReplaceStone(OreGenFeatureConfig config, BlockState blockToBeReplacedIn) {
        Random rand = new Random();
        List<BlockState> originalOres = config.ore;
        List<Integer> originalOresChances = config.veinBlockChances;
        List<BlockState> oreThatCanReplace = new ArrayList<>();
        List<Integer> oreChancesCanReplace = new ArrayList<>();
        BlockState blockToBePlaced = Blocks.AIR.defaultBlockState();
        if (originalOres.size() == originalOresChances.size()) {
            for (int i = 0; i < originalOres.size(); i++) {
                Block block = originalOres.get(i).getBlock();
                Block replaceBlock = blockToBeReplacedIn.getBlock();
                if (block instanceof OreBlock) {
                    if (replaceBlock == ((OreBlock) block).replaceableBlock.getBlock()) {
                        oreThatCanReplace.add(originalOres.get(i));
                        oreChancesCanReplace.add(originalOresChances.get(i));
                    }
                }
            }

            if (oreThatCanReplace.size() != 0 && oreChancesCanReplace.size() != 0) {
                while (blockToBePlaced == Blocks.AIR.defaultBlockState()) {
                    int randomInt = rand.nextInt(oreThatCanReplace.size());
                    if (rand.nextInt(20) <= oreChancesCanReplace.get(randomInt)) {
                        blockToBePlaced = oreThatCanReplace.get(randomInt);
                        return blockToBePlaced;
                    }
                }
            } else {
                return null;
            }
        }
        return null;
    }

    public BlockState canDenseOreReplaceStone(OreGenFeatureConfig config, BlockState blockToBeReplacedIn) {
        Random rand = new Random();
        List<BlockState> originalOres = config.denseOre;
        List<Integer> originalOresChances = config.veinBlockChances;
        List<BlockState> oreThatCanReplace = new ArrayList<>();
        List<Integer> oreChancesCanReplace = new ArrayList<>();
        BlockState blockToBePlaced = Blocks.AIR.defaultBlockState();
        if (originalOres.size() == originalOresChances.size()) {
            for (int i = 0; i < originalOres.size(); i++) {
                Block block = originalOres.get(i).getBlock();
                Block replaceBlock = blockToBeReplacedIn.getBlock();
                if (block instanceof DenseOreBlock) {
                    if (replaceBlock == ((DenseOreBlock) block).replaceableBlock.getBlock()) {
                        oreThatCanReplace.add(originalOres.get(i));
                        oreChancesCanReplace.add(originalOresChances.get(i));
                    }
                }
            }

            if (oreThatCanReplace.size() != 0 && oreChancesCanReplace.size() != 0) {
                while (blockToBePlaced == Blocks.AIR.defaultBlockState()) {
                    int randomInt = rand.nextInt(oreThatCanReplace.size());
                    if (rand.nextInt(20) <= oreChancesCanReplace.get(randomInt)) {
                        blockToBePlaced = oreThatCanReplace.get(randomInt);
                        return blockToBePlaced;
                    }
                }
            } else {
                return null;
            }
        }
        return null;
    }

    public BlockPos getValidSpotToSpawn(ISeedReader iSeedReaderIn, BlockPos pos, int takeOff){
        for(int i = pos.getY(); i > 0; i--){
            BlockState newBlock = iSeedReaderIn.getBlockState(new BlockPos(pos.getX(), i, pos.getZ()));
            if(newBlock == Blocks.STONE.defaultBlockState() || newBlock == Blocks.ANDESITE.defaultBlockState()  || newBlock == Blocks.DIORITE.defaultBlockState() || newBlock == Blocks.GRANITE.defaultBlockState()){
                return new BlockPos(pos.getX(), i - takeOff, pos.getZ());
            }else{
                return null;
            }
        }
        return null;
    }
}