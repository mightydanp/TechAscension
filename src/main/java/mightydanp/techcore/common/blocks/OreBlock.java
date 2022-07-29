package mightydanp.techcore.common.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.List;

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

    @Override
    public List<ItemStack> getDrops(BlockState p_60537_, LootContext.Builder p_60538_) {
        return super.getDrops(p_60537_, p_60538_);
    }
}
