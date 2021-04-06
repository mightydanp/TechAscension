package mightydanp.industrialtech.api.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;

import net.minecraft.block.AbstractBlock.Properties;

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
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState iBlockState) {
        return BlockRenderType.MODEL;
    }
}