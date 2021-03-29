package mightydanp.industrialtech.api.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;

/**
 * Created by MightyDanp on 3/15/2021.
 */
public class DenseOreBlock extends Block {
    public String name;
    public BlockState replaceableBlock;
    public List<RegistryObject<Item>> blockDrop;
    public int density;

    public static IntegerProperty densityProperty = IntegerProperty.create("density", 0, 64);

    public DenseOreBlock(String nameIn, Properties properties, int densityIn, BlockState replaceableBlockIn, List<RegistryObject<Item>> blockDropIn) {
        super(properties);
        name = nameIn;
        replaceableBlock = replaceableBlockIn;
        blockDrop = blockDropIn;
        density = densityIn;
        this.setDefaultState(this.stateContainer.getBaseState().with(densityProperty, density));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(densityProperty);
    }

    @Override
    public BlockRenderType getRenderType(BlockState iBlockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        super.onPlayerDestroy(worldIn, pos, state);
        if(state.getBlockState().get(densityProperty) > 1) {
            worldIn.setBlockState(pos, state.with(densityProperty, state.getBlockState().get(densityProperty) - 1), 2);
        }else{
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
        }
    }
    public int getDensity(){
        return density;
    }
}