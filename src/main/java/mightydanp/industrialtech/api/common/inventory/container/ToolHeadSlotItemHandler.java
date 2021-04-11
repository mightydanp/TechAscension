package mightydanp.industrialtech.api.common.inventory.container;

import mightydanp.industrialtech.api.common.items.ToolHeadItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Created by MightyDanp on 4/9/2021.
 */
public class ToolHeadSlotItemHandler extends SlotItemHandler {
    public ToolHeadSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        if(stack.getItem() instanceof ToolHeadItem){
            return true;
        }
        return false;
    }
}