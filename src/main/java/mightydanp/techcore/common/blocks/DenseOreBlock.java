package mightydanp.techcore.common.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by MightyDanp on 3/15/2021.
 */
public class DenseOreBlock extends Block {
    public String name;
    public String stoneLayerBlock;
    public int density;

    public static IntegerProperty densityProperty = IntegerProperty.create("density", 0, 64);

    public DenseOreBlock(String nameIn, Properties properties, int densityIn, String stoneLayerBlockIn) {
        super(properties);
        name = nameIn;
        stoneLayerBlock = stoneLayerBlockIn;
        density = densityIn;
        this.registerDefaultState(this.stateDefinition.any().setValue(densityProperty, density));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(densityProperty);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState iBlockState) {
        return RenderShape.MODEL;
    }

    @Override
    public void destroy(@NotNull LevelAccessor worldIn, @NotNull BlockPos pos, @NotNull BlockState state) {
        super.destroy(worldIn, pos, state);
        if(state.getValue(densityProperty) > 1) {
            worldIn.setBlock(pos, state.setValue(densityProperty, state.getValue(densityProperty) - 1), 2);
        }else{
            worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        }
    }
    public int getDensity(){
        return density;
    }

    @Override
    public @NotNull List<ItemStack> getDrops(@NotNull BlockState p_60537_, LootContext.@NotNull Builder p_60538_) {
        return super.getDrops(p_60537_, p_60538_);
    }
}