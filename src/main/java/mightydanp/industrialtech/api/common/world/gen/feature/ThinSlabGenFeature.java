package mightydanp.industrialtech.api.common.world.gen.feature;

import com.mojang.serialization.Codec;
import mightydanp.industrialtech.api.common.blocks.ThinSlabBlock;
import mightydanp.industrialtech.common.IndustrialTech;
import mightydanp.industrialtech.common.stonelayers.ModStoneLayers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
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
    public boolean place(ISeedReader iSeedReaderIn, ChunkGenerator chunkGeneratorIn, Random randomIn, BlockPos blockPosIn, ThinSlabGenFeatureConfig thinSlabGenFeatureConfig) {
        boolean canSpawn = false;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
        int groundHeight = chunkGeneratorIn.getSpawnHeight();
        int x = blockPosIn.getX();
        int z = blockPosIn.getZ();
        for (int xx = 0; xx <= 16; xx++) {
            int x2 = xx + x;
            for (int zz = 0; zz <= 16; zz++) {
                int z2 = zz + z;
                for (int yy = 0; yy <= 180; yy++) {
                    blockpos$mutable.set(x2, yy, z2);
                    BlockPos blockpos$Changed = new BlockPos(blockpos$mutable);
                    BlockState blockState = iSeedReaderIn.getBlockState(blockpos$mutable);
                    BlockState blockStateDown = iSeedReaderIn.getBlockState(blockpos$mutable.below());
                    //BlockState blockThatCanBePlace = canReplaceStone(randomlyOnSurfaceGenFeatureConfigIn, blockStateDown);
                    if (randomIn.nextInt(5000) < thinSlabGenFeatureConfig.rarity) {
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
                                blockStateDownNew = iSeedReaderIn.getBlockState(blockpos$Changed);
                            }

                            List<BlockState> blockToSpawn = new ArrayList<>();

                            for(int i = 0; i< thinSlabGenFeatureConfig.blocks.size(); i++){
                                ThinSlabBlock thinSlabBlock = (ThinSlabBlock)thinSlabGenFeatureConfig.blocks.get(i).getBlock();
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
                                iSeedReaderIn.setBlock(blockpos$mutable, blockToSpawn.get(0), 2);
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