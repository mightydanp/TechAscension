package mightydanp.techcore.common.handler.itemstack;

import mightydanp.techcore.common.tool.part.BindingItem;
import mightydanp.techcore.common.tool.part.HandleItem;
import mightydanp.techcore.common.tool.part.HeadItem;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by MightyDanp on 4/7/2021.
 */
public class ITToolItemInventoryHelper extends NonNullList<ItemStack>{

    public ITToolItemInventoryHelper() {
        super(new ArrayList<>(){{
            add(ItemStack.EMPTY);
            add(ItemStack.EMPTY);
            add(ItemStack.EMPTY);
        }}, ItemStack.EMPTY);
    }

    public ItemStack getToolHandle() {
        ItemStack itemStack = get(0);
        if(itemStack.getItem() instanceof HandleItem){
            return itemStack;
        }
        return null;
    }

    public void setToolHandle(ItemStack itemStackIn) {
        if(itemStackIn.getItem() instanceof HandleItem){
            set(0, itemStackIn);
        }
    }

    public ItemStack getToolHead() {
        ItemStack itemStack = get(1);
        if(itemStack.getItem() instanceof HeadItem){
            return itemStack;
        }
        return null;
    }

    public void setToolHead(ItemStack itemStackIn) {
        if(itemStackIn.getItem() instanceof HeadItem){
            set(1, itemStackIn);
        }
    }

    public ItemStack getToolBinding() {
        ItemStack itemStack = get(2);
        if(itemStack.getItem() instanceof BindingItem){
            return itemStack;
        }
        return null;
    }

    public void setToolBinding(ItemStack itemStackIn) {
        if(itemStackIn.getItem() instanceof BindingItem){
            set(2, itemStackIn);
        }
    }
}
