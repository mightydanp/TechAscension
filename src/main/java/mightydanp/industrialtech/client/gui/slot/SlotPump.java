package mightydanp.industrialtech.client.gui.slot;

import mightydanp.industrialtech.common.data.IndustrialTechData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class SlotPump extends SlotItemHandler {

    public SlotPump(IItemHandler stackHandler, int index, int x, int y) {
        super(stackHandler, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(stack.getItem() == IndustrialTechData.PumpLV || stack.getItem() == IndustrialTechData.PumpMV || stack.getItem() == IndustrialTechData.PumpHV || stack.getItem() == IndustrialTechData.PumpEV || stack.getItem() == IndustrialTechData.PumpIV){
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