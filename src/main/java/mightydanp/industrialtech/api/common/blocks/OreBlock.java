package mightydanp.industrialtech.api.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class OreBlock extends Block {
    public String name;
    public BlockState replaceableBlock;

    public OreBlock(String nameIn, Properties properties, BlockState replaceableBlockIn) {
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
