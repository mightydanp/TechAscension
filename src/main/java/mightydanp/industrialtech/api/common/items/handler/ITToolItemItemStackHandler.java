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
    public static final int numberOfSlots = 3;
    private boolean isDirty = true;

    public ITToolItemItemStackHandler(int numberOfSlots) {
        super(numberOfSlots);
        if (numberOfSlots > numberOfSlots) {
            throw new IllegalArgumentException("Invalid number of flower slots:"+numberOfSlots);
        }
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (numberOfSlots < slot) {
            throw new IllegalArgumentException("Invalid slot number:"+ slot);
        }

        if (stack.isEmpty()) {
            return false;
        }

        Item item = stack.getItem();
        if (item.is(ItemTags.SMALL_FLOWERS) || item.is(ItemTags.TALL_FLOWERS)) return true;
        return false;
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

    public ToolHeadItem getToolHead() {
        ItemStack itemStack = getStackInSlot(ITToolItemContainer.firstSlotStartingIndex + 1);
        if(itemStack.getItem() instanceof ToolHeadItem){
            return (ToolHeadItem)itemStack.getItem();
        }
        return null;
    }

    public ToolBindingItem getToolBinding() {
        ItemStack itemStack = getStackInSlot(ITToolItemContainer.firstSlotStartingIndex + 2);
        if(itemStack.getItem() instanceof ToolBindingItem){
            return (ToolBindingItem)itemStack.getItem();
        }
        return null;
    }

    public ToolHandleItem getToolHandle() {
        ItemStack itemStack = getStackInSlot(ITToolItemContainer.firstSlotStartingIndex + 3);
        if(itemStack.getItem() instanceof ToolHandleItem){
            return (ToolHandleItem)itemStack.getItem();
        }
        return null;
    }
}
