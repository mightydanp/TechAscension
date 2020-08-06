package mightydanp.industrialtech.client.gui.slot;

import mightydanp.industrialtech.common.data.IndustrialTechData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import static mightydanp.industrialtech.common.data.IndustrialTechData.*;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class SlotFieldGenerator extends SlotItemHandler {

    public SlotFieldGenerator(IItemHandler stackHandler, int index, int x, int y) {
        super(stackHandler, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(stack.getItem() == FieldGenLV || stack.getItem() == FieldGenMV || stack.getItem() == FieldGenHV || stack.getItem() == FieldGenEV || stack.getItem() == FieldGenIV){
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