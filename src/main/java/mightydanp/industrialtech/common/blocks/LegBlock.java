package mightydanp.industrialtech.common.blocks;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

/**
 * Created by MightyDanp on 7/3/2021.
 */
public class LegBlock extends Block{
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D)};

    public LegBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[0];
    }

    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState blockStateTop = worldIn.getBlockState(pos.above());
        BlockState blockstate = worldIn.getBlockState(pos.below());
        if (blockstate == Blocks.AIR.defaultBlockState()) {
            return false;
        }

        return true;
    }

}