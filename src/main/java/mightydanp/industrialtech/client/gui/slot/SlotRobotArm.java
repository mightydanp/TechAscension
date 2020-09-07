package mightydanp.industrialtech.client.gui.slot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static mightydanp.industrialtech.common.data.IndustrialTechData.*;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class SlotRobotArm extends SlotItemHandler {
    private static List<Item> validItems  = new ArrayList<>();

    public SlotRobotArm(IItemHandler stackHandler, int index, int x, int y) {
        super(stackHandler, index, x, y);
        addValidSlotItem(RobotArmLV);
        addValidSlotItem(RobotArmMV);
        addValidSlotItem(RobotArmHV);
        addValidSlotItem(RobotArmEV);
        addValidSlotItem(RobotArmIV);
    }

    public static List<Item> getValidItems(){
        return validItems;
    }

    public static void addValidSlotItem(Item item) {
        validItems.add(item);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if (validItems.contains(stack.getItem())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canTakeStack(PlayerEntity player) {
        return false;
    }
}