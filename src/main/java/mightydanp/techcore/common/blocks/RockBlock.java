package mightydanp.techcore.common.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

/**
 * Created by MightyDanp on 3/3/2021.
 */
public class RockBlock extends Block {
    public String stoneLayerBlock;

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)};

    public RockBlock(String stoneLayerBlockIn, Properties properties) {
        super(properties.noCollission());
        stoneLayerBlock = stoneLayerBlockIn;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES[0];
    }
}
