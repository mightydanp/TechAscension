package mightydanp.industrialtech.client.gui.slot;

import mightydanp.industrialtech.common.data.IndustrialTechData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class SlotConveyor extends SlotItemHandler {

    public SlotConveyor(IItemHandler stackHandler, int index, int x, int y) {
        super(stackHandler, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(stack.getItem() == IndustrialTechData.ConveyorLV || stack.getItem() == IndustrialTechData.ConveyorMV || stack.getItem() == IndustrialTechData.ConveyorHV || stack.getItem() == IndustrialTechData.ConveyorEV || stack.getItem() == IndustrialTechData.ConveyorIV){
            return true;
        }else{
            return false;
        }


    }

    @Override
    public boolean canTakeStack(PlayerEntity player) {
        return false;
    }
}