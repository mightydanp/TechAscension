package mightydanp.industrialtech.api.common.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.Item.Properties;

/**
 * Created by MightyDanp on 3/6/2021.
 */
public class RockBlockItem extends BlockItem {

    public RockBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    protected boolean placeBlock(BlockItemUseContext context, BlockState state) {
        return false;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return 1;
    }

    @Override
    public void inventoryTick(ItemStack itemStackIn, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        super.inventoryTick(itemStackIn, p_77663_2_, p_77663_3_, p_77663_4_, p_77663_5_);
        if(itemStackIn.getDamageValue() == 0){
            itemStackIn.shrink(1);
        }
    }
}