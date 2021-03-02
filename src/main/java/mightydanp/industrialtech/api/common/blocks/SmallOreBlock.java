package mightydanp.industrialtech.api.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;

/**
 * Created by MightyDanp on 3/1/2021.
 */
public class SmallOreBlock extends Block {
    public String name;
    public BlockState replaceableBlock;

    public SmallOreBlock(String nameIn, Properties properties, BlockState replaceableBlockIn) {
        super(properties);
        this.name = nameIn;
        this.replaceableBlock = replaceableBlockIn;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
    }

    @Override
    public BlockRenderType getRenderType(BlockState iBlockState) {
        return BlockRenderType.MODEL;
    }
}