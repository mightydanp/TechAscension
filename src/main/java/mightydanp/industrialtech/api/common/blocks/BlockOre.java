package mightydanp.industrialtech.api.common.blocks;

import mightydanp.industrialtech.api.common.libs.EnumStoneVariant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.OreBlock;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class BlockOre extends Block {
    //public static final EnumProperty stoneVariant = EnumProperty.create("variant", EnumStoneVariant.class);
    public BlockState replaceableBlock;

    public BlockOre(Properties properties, BlockState replaceableBlockIn) {
        super(properties);
        this.replaceableBlock = replaceableBlockIn;
        //this.setDefaultState(this.stateContainer.getBaseState().with(stoneVariant, EnumStoneVariant.stone));
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
