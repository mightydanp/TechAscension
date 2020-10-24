package mightydanp.industrialtech.api.client.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by MightyDanp on 10/14/2020.
 */
public class SlotKnapsack extends Slot {

    public SlotKnapsack(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return super.isItemValid(stack);
    }
}