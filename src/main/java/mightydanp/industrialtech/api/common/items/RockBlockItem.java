package mightydanp.industrialtech.api.common.items;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

/**
 * Created by MightyDanp on 3/6/2021.
 */
public class RockBlockItem extends BlockItem {

    public RockBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        return false;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 1;
    }

    @Override
    public void inventoryTick(ItemStack itemStackIn, Level p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        super.inventoryTick(itemStackIn, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
        if(itemStackIn.getDamageValue() == 0){
            itemStackIn.shrink(1);
        }
    }
}