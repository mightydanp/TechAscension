package mightydanp.industrialtech.common.handler.itemstack;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

/**
 * Created by MightyDanp on 5/10/2021.
 */
public class CampfireItemStackHandler extends ItemStackHandler {
    public static int numberOfSlots;
    private boolean isDirty = true;

    public ItemStack logs = addNewSlotOrGetSlotItemStack(0);

    public CampfireItemStackHandler(int numberOfSlotsIn) {
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

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        /*
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
        */
        return true;
    }

    public ItemStack addNewSlotOrGetSlotItemStack(int slotNumber){
        if(this.getSlots() <= slotNumber + 1){
            this.stacks.add(slotNumber, ItemStack.EMPTY);
        }

        return this.stacks.get(slotNumber);
    }


}