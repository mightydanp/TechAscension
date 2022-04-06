package mightydanp.industrialtech.api.common.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import javax.annotation.Nullable;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class OreBlock extends Block {
    public String name;
    public String stoneLayerBlock;

    public OreBlock(String nameIn, Properties properties, String stoneLayerBlockIn) {
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
