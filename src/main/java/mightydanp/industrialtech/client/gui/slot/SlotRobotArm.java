package mightydanp.industrialtech.client.gui.slot;

import mightydanp.industrialtech.common.data.IndustrialTechData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class SlotRobotArm extends SlotItemHandler {

    public SlotRobotArm(IItemHandler stackHandler, int index, int x, int y) {
        super(stackHandler, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if(stack.getItem() == IndustrialTechData.RobotArmLV || stack.getItem() == IndustrialTechData.RobotArmMV || stack.getItem() == IndustrialTechData.RobotArmHV || stack.getItem() == IndustrialTechData.RobotArmEV || stack.getItem() == IndustrialTechData.RobotArmIV){
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