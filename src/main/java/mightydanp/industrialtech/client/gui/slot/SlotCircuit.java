package mightydanp.industrialtech.client.gui.slot;

import mightydanp.industrialtech.common.data.IndustrialTechData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class SlotCircuit extends SlotItemHandler {

    public SlotCircuit(IItemHandler stackHandler, int index, int x, int y) {
        super(stackHandler, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(stack.getItem() == IndustrialTechData.CircuitBasic || stack.getItem() == IndustrialTechData.CircuitAdv || stack.getItem() == IndustrialTechData.CircuitGood || stack.getItem() == IndustrialTechData.CircuitDataStorage || stack.getItem() == IndustrialTechData.CircuitDataControl || stack.getItem() == IndustrialTechData.CircuitEnergyFlow || stack.getItem() == IndustrialTechData.CircuitDataOrb){
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