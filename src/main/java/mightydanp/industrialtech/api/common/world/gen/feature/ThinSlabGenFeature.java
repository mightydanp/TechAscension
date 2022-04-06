package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.serialization.Codec;
import mightydanp.industrialtech.api.common.blocks.ThinSlabBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by MightyDanp on 9/8/2021.
 */
public class ThinSlabGenFeature extends Feature<ThinSlabGenFeatureConfig> {
    public ThinSlabGenFeature(Codec<ThinSlabGenFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ThinSlabGenFeatureConfig> context)  {
        boolean canSpawn = false;
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        int groundHeight = context.chunkGenerator().getSpawnHeight(context.level());
        int x = context.origin().getX();
        int z = context.origin().getZ();
        for (int xx = 0; xx <= 16; xx++) {
            int x2 = xx + x;
            for (int zz = 0; zz <= 16; zz++) {
                int z2 = zz + z;
                for (int yy = 0; yy <= 180; yy++) {
                    blockpos$mutable.set(x2, yy, z2);
                    BlockPos blockpos$Changed = new BlockPos(blockpos$mutable);
                    BlockState blockState = context.level().getBlockState(blockpos$mutable);
                    BlockState blockStateDown = context.level().getBlockState(blockpos$mutable.below());
                    //BlockState blockThatCanBePlace = canReplaceStone(randomlyOnSurfaceGenFeatureConfigIn, blockStateDown);
                    if (context.random().nextInt(context.config().rarity) == 0) {
                        if(blockStateDown != Blocks.AIR.defaultBlockState() && blockStateDown != Blocks.WATER.defaultBlockState() && blockStateDown != Blocks.LAVA.defaultBlockState() && blockState == Blocks.WATER.defaultBlockState()){
                            List<BlockState> unwantedBlock = new ArrayList<BlockState>(){{
                                add(Blocks.SAND.defaultBlockState());
                                add(Blocks.RED_SAND.defaultBlockState());
                                add(Blocks.GRAVEL.defaultBlockState());
                                add(Blocks.DIRT.defaultBlockState());
                                add(Blocks.GRASS.defaultBlockState());
                                add(Blocks.PODZOL.defaultBlockState());
                            }};

                            BlockState blockStateDownNew = blockStateDown;

                            while(unwantedBlock.contains(blockStateDownNew)){
                                blockpos$Changed = blockpos$Changed.below();
                                blockStateDownNew = context.level().getBlockState(blockpos$Changed);
                            }

                            List<BlockState> blockToSpawn = new ArrayList<>();

                            for(int i = 0; i< context.config().blocks.size(); i++){
                                ThinSlabBlock thinSlabBlock = (ThinSlabBlock)context.config().blocks.get(i).getBlock();
                                String modID = thinSlabBlock.stoneLayerBlock.split(":")[0];
                                String stoneLayerBlock = thinSlabBlock.stoneLayerBlock.split(":")[1];

                                Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modID, stoneLayerBlock));
                                if(block != null) {
                                    if(block.defaultBlockState() == blockStateDownNew){
                                        blockToSpawn.clear();
                                        blockToSpawn.add(thinSlabBlock.defaultBlockState().setValue(ThinSlabBlock.WATERLOGGED, true));
                                    }
                                }
                            }

                            if(blockToSpawn.size() > 0) {
                                context.level().setBlock(blockpos$mutable, blockToSpawn.get(0), 2);
                                System.out.println(blockpos$mutable.getX() + " " + blockpos$mutable.getY() + " " + blockpos$mutable.getZ() + " " + "/" + "thin_slab");
                                canSpawn = true;
                            }
                        }
                    }
                }
            }
        }
        return canSpawn;
    }
}