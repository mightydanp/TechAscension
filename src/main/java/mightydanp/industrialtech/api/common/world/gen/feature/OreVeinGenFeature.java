package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import mightydanp.industrialtech.api.common.blocks.DenseOreBlock;
import mightydanp.industrialtech.api.common.blocks.OreBlock;
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
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OreVeinGenFeature extends Feature<OreVeinGenFeatureConfig> {
    public OreVeinGenFeature(Codec<OreVeinGenFeatureConfig> p_i231976_1_) {
        super(p_i231976_1_);
    }

    public static List<BlockPos> getNearbyVeins(long seed, int chunkX, int chunkZ, Random rand, int radius, OreVeinGenFeatureConfig oreVeinGenFeatureConfig) {
        List<BlockPos> veins = new ArrayList<>();
        for (int x = chunkX - radius; x <= chunkX + radius; x++) {
            for (int z = chunkZ - radius; z <= chunkZ + radius; z++) {
                if (getVeinAtChunk(seed, x, z, rand, oreVeinGenFeatureConfig) != null) {
                    veins.add(getVeinAtChunk(seed, x, z, rand, oreVeinGenFeatureConfig));
                }
            }
        }
        return veins;
    }

    private static BlockPos getVeinAtChunk(long seed, int chunkX, int chunkZ, Random rand, OreVeinGenFeatureConfig oreVeinGenFeatureConfig) {
        rand.setSeed(seed + chunkX * 341873128712L + chunkZ * 132897987541L);
        //rand.setSeed(worldSeed);
        int chance = rand.nextInt(20);
        if (chance == 0) {
            return new BlockPos(chunkX, 0, chunkZ);
        }
        return null;
    }

    @ParametersAreNonnullByDefault
    public boolean place(WorldGenLevel iSeedReaderIn, ChunkGenerator chunkGeneratorIn, Random randomIn, BlockPos blockPosIn, OreVeinGenFeatureConfig oreVeinGenFeatureConfig) {
        ChunkPos chunkPos = new ChunkPos(blockPosIn);
        List<BlockPos> getValidVeins = getNearbyVeins(iSeedReaderIn.getSeed(), chunkPos.x, chunkPos.z, randomIn, 9, oreVeinGenFeatureConfig);
        randomIn.setSeed(iSeedReaderIn.getSeed() + chunkPos.x * 341873128712L + chunkPos.z * 132897987541L);
        boolean canSpawn = false;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        int radius = oreVeinGenFeatureConfig.minRadius;//24;
        int diameter = radius * 2;
        int maxSmallOreBlocksExtend = oreVeinGenFeatureConfig.minNumberOfSmallOreLayers;//3;
        int x = blockPosIn.getX() + 8;
        int y = blockPosIn.getY();
        int z = blockPosIn.getZ() + 8;

        int groundHeight = iSeedReaderIn.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);

        int minHeight = 3 + (maxSmallOreBlocksExtend * 2);
        int maxHeight = 7 + (maxSmallOreBlocksExtend);

        BlockState block = iSeedReaderIn.getBlockState(blockPosIn);
        if(y > groundHeight){
            BlockPos pos = new BlockPos(x, y, z);
            y = oreVeinGenFeatureConfig.minHeight + maxHeight + maxHeight/2;
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
        if (randomIn.nextInt(oreVeinGenFeatureConfig.rarity) == 0) {
            /*
            if(y > groundHeight){
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
            System.out.println(x + " " + y + " " + z + " " + "/" + oreVeinGenFeatureConfig.name);


            for (BlockPos pos : cubePos) {
                int xBlockPos = pos.getX();
                int yBlockPos = pos.getY();
                int zBlockPos = pos.getZ();

                blockpos$mutable.set(xBlockPos, yBlockPos, zBlockPos);
                BlockState replacedBlock = iSeedReaderIn.getBlockState(blockpos$mutable);
                BlockState smallOreThatCanReplaceBlock = replacementStoneLayerOre(randomIn, oreVeinGenFeatureConfig, replacedBlock, "small_ore");
                BlockState oreThatCanReplaceBlock = replacementStoneLayerOre(randomIn, oreVeinGenFeatureConfig, replacedBlock, "ore");
                BlockState denseOreThatCanReplaceBlock = replacementStoneLayerOre(randomIn, oreVeinGenFeatureConfig, replacedBlock, "dense_ore");

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

    public BlockState replacementStoneLayerOre(Random rand, OreVeinGenFeatureConfig config, BlockState blockToBeReplacedIn, String oreType) {
        BlockState blockToBePlaced = Blocks.AIR.defaultBlockState();

        List<Pair<String, Integer>> veinBlocksAndChances = config.blocksAndChances;

        List<Pair<Block, Integer>> veinBlocksAndChancesThatCanReplace = new ArrayList<>();

        for (Pair<String, Integer> veinBlocksAndChance : veinBlocksAndChances) {
            ITMaterial material = RegistryHandler.MATERIAL.getValue(ResourceLocation.tryParse(veinBlocksAndChance.getFirst()));
            int rarity = veinBlocksAndChance.getSecond();

            if (material != null) {
                if (oreType.equals("small_ore")) {
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
                }

                if (oreType.equals("ore")) {
                    if (material.oreList != null) {
                        for (Block block : material.oreList) {
                            if (block instanceof OreBlock) {
                                OreBlock ore = (OreBlock) block;
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
                }

                if (oreType.equals("dense_ore")) {
                    if (material.denseOreList != null) {
                        for (Block block : material.denseOreList) {
                            if (block instanceof DenseOreBlock) {
                                DenseOreBlock ore = (DenseOreBlock) block;
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