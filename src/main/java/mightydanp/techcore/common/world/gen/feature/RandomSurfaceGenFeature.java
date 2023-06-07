package mightydanp.techcore.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 3/3/2021.
 */
public class RandomSurfaceGenFeature extends Feature<RandomSurfaceGenFeatureCodec> {
    public RandomSurfaceGenFeature(Codec<RandomSurfaceGenFeatureCodec> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RandomSurfaceGenFeatureCodec> context) {

        boolean canSpawn = false;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int x = context.origin().getX();
        int z = context.origin().getZ();
        for (int xx = 0; xx <= 16; xx++) {
            int x2 = xx + x;
            for (int zz = 0; zz <= 16; zz++) {
                int z2 = zz + z;

                int groundHeight = context.level().getHeight(Heightmap.Types.WORLD_SURFACE, x2, z2);
                mutableBlockPos.set(x2, groundHeight, z2);
                BlockState blockState = context.level().getBlockState(mutableBlockPos);
                BlockState blockStateDown = context.level().getBlockState(mutableBlockPos.below());
                //BlockState blockThatCanBePlace = canReplaceStone(randomlyOnSurfaceGenFeatureConfigIn, blockStateDown);
                if (0 == context.random().nextInt(context.config().rarity())) {
                    List<BlockState> validBlocks = context.config().validBlockStates();

                    if (validBlocks.contains(blockStateDown) && blockState == Blocks.AIR.defaultBlockState()) {
                        List<BlockState> blocks = context.config().blockStates();
                        for (int a = 0; a < blocks.size(); a++) {
                            context.level().setBlock(mutableBlockPos, blocks.get(a), 2);
                            canSpawn = true;
                            //System.out.println(mutableBlockPos.getX() + " " + mutableBlockPos.getY() + " " + mutableBlockPos.getZ() + " " + "/" + randomlyOnSurfaceGenFeatureConfigIn.blocks.getBlock().toString().split(":")[1]);
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
