package mightydanp.techcore.common.world.gen.feature;

import com.mojang.serialization.Codec;
import mightydanp.techcore.common.blocks.ThinSlabBlock;
import mightydanp.techcore.common.blocks.RockBlock;
import mightydanp.techascension.common.IndustrialTech;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MightyDanp on 9/8/2021.
 */
public class StoneLayerTopSurfaceGenFeature extends Feature<StoneLayerTopSurfaceGenFeatureConfig> {
    public StoneLayerTopSurfaceGenFeature(Codec<StoneLayerTopSurfaceGenFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<StoneLayerTopSurfaceGenFeatureConfig> context) {
        boolean canSpawn = false;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        //int groundHeight = context.chunkGenerator().getSpawnHeight(context.level());
        int x = context.origin().getX();
        int z = context.origin().getZ();
        for (int xx = 0; xx <= 16; xx++) {
            int x2 = xx + x;
            for (int zz = 0; zz <= 16; zz++) {
                int z2 = zz + z;
                int groundHeight = context.level().getHeight(Heightmap.Types.OCEAN_FLOOR_WG, x2, z2);
                for (int yy = context.config().onlySurface ? groundHeight : 0; yy <= groundHeight; yy++) {
                    blockpos$mutable.set(x2, yy, z2);
                    BlockState blockState = context.level().getBlockState(blockpos$mutable);
                    BlockState blockStateDown = context.level().getBlockState(blockpos$mutable.below());
                    //BlockState blockThatCanBePlace = canReplaceStone(randomlyOnSurfaceGenFeatureConfigIn, blockStateDown);
                    if (context.random().nextInt(context.config().rarity) == 0) {
                        List<BlockState> unwantedBlock = new ArrayList<>() {{
                            add(Blocks.AIR.defaultBlockState());
                            add(Blocks.WATER.defaultBlockState());
                            add(Blocks.LAVA.defaultBlockState());

                            if (context.config().blackListedBlocks.size() > 0) {
                                for (String id : context.config().blackListedBlocks) {
                                    String blackListedBlocksModID = id.split(":")[0];
                                    String blackListedBlocks = id.split(":")[1];

                                    Block stoneLayer = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(blackListedBlocksModID, blackListedBlocks));
                                    if (stoneLayer != null) {
                                        add(stoneLayer.defaultBlockState());
                                    }
                                }
                            }
                        }};

                        List<BlockState> whiteListList = new ArrayList<>() {{
                            if (context.config().whiteListedBlocks.size() > 0) {
                                for (String id : context.config().whiteListedBlocks) {
                                    String whiteListedBlocksModID = id.split(":")[0];
                                    String whiteListedBlocks = id.split(":")[1];

                                    Block stoneLayer = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(whiteListedBlocksModID, whiteListedBlocks));
                                    if (stoneLayer != null) {
                                        add(stoneLayer.defaultBlockState());
                                    }
                                }
                            }
                        }};

                        if (!unwantedBlock.contains(blockStateDown)) {
                            if((whiteListList.size() > 0 && whiteListList.contains(blockStateDown)) || whiteListList.size() == 0) {
                                while (unwantedBlock.contains(blockStateDown)) {
                                    blockStateDown = context.level().getBlockState(blockpos$mutable.below());
                                }

                                List<BlockState> blockStatesToPlace = context.config().blocks.stream().map(s -> {
                                    String placeableBlocksModID = s.split(":")[0];
                                    String placeableBlocks = s.split(":")[1];
                                    Block placeableBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(placeableBlocksModID, placeableBlocks));

                                    if (placeableBlock != null) {
                                        return placeableBlock.defaultBlockState();
                                    }
                                    return null;
                                }).toList();

                                BlockState blockToSet = downwardStoneLayerFinder(blockStatesToPlace, blockpos$mutable, context.level(), context.config().inWater);

                                if(blockToSet != context.level().getBlockState(blockpos$mutable)) {
                                    context.level().setBlock(blockpos$mutable, blockToSet, 2);
                                }
                            }
                        }
                    }
                }
            }
        }
        return canSpawn;
    }

    public BlockState downwardStoneLayerFinder(List<BlockState> blockStatesToPlace, BlockPos blockPos, WorldGenLevel worldGenLevel, Boolean inWater) {
        List<Block> blocksToSpawn = blockStatesToPlace.stream().map(BlockBehaviour.BlockStateBase::getBlock).toList();

        List<ThinSlabBlock> thinSlabBlockList = blocksToSpawn.stream().filter(block -> block instanceof ThinSlabBlock).map(block -> (ThinSlabBlock)block).toList();

        List<RockBlock> rockBlockList = blocksToSpawn.stream().filter(block -> block instanceof RockBlock).map(block -> (RockBlock) block).toList();

        BlockState stateDown = worldGenLevel.getBlockState(blockPos.below());

        if(stateDown.getMaterial().isSolid()) {
            for (int i = 0; i < blockPos.below().getY(); i++) {
                BlockState foundBlockIn = worldGenLevel.getBlockState(new BlockPos(blockPos).below(i));

                if (thinSlabBlockList.size() > 0) {
                    List<ThinSlabBlock> acceptedState = thinSlabBlockList.stream().filter(thinSlabBlock -> {
                        if (Arrays.stream(thinSlabBlock.stoneLayerBlock.split(":")).toList().size() == 1) {
                            IndustrialTech.LOGGER.fatal("[ " + thinSlabBlock.stoneLayerBlock + " ]. Will crash your game now!");
                            return false;
                        }

                        String placeableBlocksModID = thinSlabBlock.stoneLayerBlock.split(":")[0];
                        String placeableBlocks = thinSlabBlock.stoneLayerBlock.split(":")[1];
                        Block placeableBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(placeableBlocksModID, placeableBlocks));

                        if (placeableBlock != null && placeableBlock.defaultBlockState() == foundBlockIn) {
                            return true;
                        }
                        return false;
                    }).toList();

                    if (acceptedState.size() > 0) {
                        BlockState state = acceptedState.get(0).defaultBlockState();

                        if (inWater) {
                            if (worldGenLevel.getBlockState(blockPos).getBlock() == Blocks.WATER) {
                                return state.setValue(ThinSlabBlock.WATERLOGGED, true);
                            }
                        } else {
                            return state;
                        }
                    }
                }

                if (rockBlockList.size() > 0) {
                    List<RockBlock> acceptedState = rockBlockList.stream().filter(rockBlock -> {
                        if (Arrays.stream(rockBlock.stoneLayerBlock.split(":")).toList().size() == 1) {
                            IndustrialTech.LOGGER.fatal("[ " + rockBlock.stoneLayerBlock + " ]. Will crash your game now!");
                            return false;
                        }

                        String placeableBlocksModID = rockBlock.stoneLayerBlock.split(":")[0];
                        String placeableBlocks = rockBlock.stoneLayerBlock.split(":")[1];
                        Block placeableBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(placeableBlocksModID, placeableBlocks));

                        if (placeableBlock != null && placeableBlock.defaultBlockState() == foundBlockIn) {
                            return true;
                        }
                        return false;
                    }).toList();

                    if (acceptedState.size() > 0) {
                        BlockState state = acceptedState.get(0).defaultBlockState();

                            return state;
                    }
                }
            }
        }

        return worldGenLevel.getBlockState(new BlockPos(blockPos));
    }
}