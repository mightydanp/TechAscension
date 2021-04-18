package mightydanp.industrialtech.api.common.items.handler;

import mightydanp.industrialtech.api.common.inventory.container.ITToolItemContainer;
import mightydanp.industrialtech.api.common.items.ToolBindingItem;
import mightydanp.industrialtech.api.common.items.ToolHandleItem;
import mightydanp.industrialtech.api.common.items.ToolHeadItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

/**
 * Created by MightyDanp on 4/7/2021.
 */
public class ITToolItemItemStackHandler extends ItemStackHandler {
    public static int numberOfSlots;
    private boolean isDirty = true;

    public ITToolItemItemStackHandler(int numberOfSlotsIn) {
        super(numberOfSlotsIn);
        numberOfSlots = numberOfSlotsIn;
    }

    public int getNumberOfEmptySlots() {
        final int numberOfSlots = getSlots();

        int emptySlotCount = 0;
        for (int i = 0; i < numberOfSlots; ++i) {
            if (getStackInSlot(i) == ItemStack.EMPTY) {
                ++emptySlotCount;
            }
        }
        return emptySlotCount;
    }

    public boolean isDirty() {
        boolean currentState = isDirty;
        isDirty = false;
        return currentState;
    }

    protected void onContentsChanged(int slot) {
        isDirty = true;
    }

    public ItemStack getToolHandle() {
        ItemStack itemStack = getStackInSlot(0);
        if(itemStack.getItem() instanceof ToolHandleItem){
            return itemStack;
        }
        return null;
    }

    public void setToolHandle(ItemStack itemStackIn) {
        if(itemStackIn.getItem() instanceof ToolHandleItem){
            setStackInSlot(0, itemStackIn);
        }
    }

    public ItemStack getToolHead() {
        ItemStack itemStack = getStackInSlot(1);
        if(itemStack.getItem() instanceof ToolHeadItem){
            return itemStack;
        }
        return null;
    }

    public void setToolHead(ItemStack itemStackIn) {
        if(itemStackIn.getItem() instanceof ToolHeadItem){
            setStackInSlot(1, itemStackIn);
        }
    }

    public ItemStack getToolBinding() {
        ItemStack itemStack = getStackInSlot(2);
        if(itemStack.getItem() instanceof ToolBindingItem){
            return itemStack;
        }
        return null;
    }

    public void setToolBinding(ItemStack itemStackIn) {
        if(itemStackIn.getItem() instanceof ToolBindingItem){
            setStackInSlot(2, itemStackIn);
        }
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if(slot == 0 && stack.getItem() instanceof ToolHandleItem){
            return true;
        }
        if(slot == 1 && stack.getItem() instanceof ToolHeadItem){
            return true;
        }
        if(slot == 2 && stack.getItem() instanceof ToolBindingItem){
            return true;
        }
        return false;
    }
}
