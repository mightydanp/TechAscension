package mightydanp.industrialcore.common.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

/**
 * Created by MightyDanp on 3/1/2021.
 */
public class SmallOreBlock extends Block {
    public String name;
    public String stoneLayerBlock;

    public SmallOreBlock(String nameIn, Properties properties, String stoneLayerBlockIn) {
        super(properties);
        this.name = nameIn;
        this.stoneLayerBlock = stoneLayerBlockIn;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public RenderShape getRenderShape(BlockState iBlockState) {
        return RenderShape.MODEL;
    }
}