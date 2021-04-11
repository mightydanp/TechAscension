package mightydanp.industrialtech.api.common.inventory.container;

import mightydanp.industrialtech.api.common.items.ToolBindingItem;
import mightydanp.industrialtech.api.common.items.ToolHandleItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Created by MightyDanp on 4/9/2021.
 */
public class ToolHandleSlotHandler extends SlotItemHandler {
    public ToolHandleSlotHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        if (stack.getItem() instanceof ToolHandleItem) {
            return true;
        }
        return false;
    }
}