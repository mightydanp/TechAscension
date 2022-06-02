package mightydanp.industrialcore.common.handler.itemstack;

import mightydanp.industrialcore.common.items.ToolBindingItem;
import mightydanp.industrialcore.common.items.ToolHandleItem;
import mightydanp.industrialcore.common.items.ToolHeadItem;
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
        if(itemStack.getItem() instanceof ToolHandleItem){
            return itemStack;
        }
        return null;
    }

    public void setToolHandle(ItemStack itemStackIn) {
        if(itemStackIn.getItem() instanceof ToolHandleItem){
            set(0, itemStackIn);
        }
    }

    public ItemStack getToolHead() {
        ItemStack itemStack = get(1);
        if(itemStack.getItem() instanceof ToolHeadItem){
            return itemStack;
        }
        return null;
    }

    public void setToolHead(ItemStack itemStackIn) {
        if(itemStackIn.getItem() instanceof ToolHeadItem){
            set(1, itemStackIn);
        }
    }

    public ItemStack getToolBinding() {
        ItemStack itemStack = get(2);
        if(itemStack.getItem() instanceof ToolBindingItem){
            return itemStack;
        }
        return null;
    }

    public void setToolBinding(ItemStack itemStackIn) {
        if(itemStackIn.getItem() instanceof ToolBindingItem){
            set(2, itemStackIn);
        }
    }
}
