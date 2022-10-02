package mightydanp.techcore.common.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

/**
 * Created by MightyDanp on 3/15/2021.
 */
public class DenseOreBlock extends Block {
    public String name;
    public String stoneLayerBlock;
    public int density;

    public DenseOreBlock(String nameIn, Properties properties, int densityIn, String stoneLayerBlockIn) {
        super(properties);
        name = nameIn;
        stoneLayerBlock = stoneLayerBlockIn;
        density = densityIn;
    }

    @Override
    public RenderShape getRenderShape(BlockState iBlockState) {
        return RenderShape.MODEL;
    }
    public int getDensity(){
        return density;
    }
}