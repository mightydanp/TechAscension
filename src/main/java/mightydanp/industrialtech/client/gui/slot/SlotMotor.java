package mightydanp.industrialtech.client.gui.slot;

import static mightydanp.industrialtech.common.data.IndustrialTechData.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class SlotMotor extends SlotItemHandler {

    public SlotMotor(IItemHandler stackHandler, int index, int x, int y) {
        super(stackHandler, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(stack.getItem() == MotorLV || stack.getItem() == MotorMV || stack.getItem() == MotorHV || stack.getItem() == MotorEV || stack.getItem() == MotorIV){
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