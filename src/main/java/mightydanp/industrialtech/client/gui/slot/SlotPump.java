package mightydanp.industrialtech.client.gui.slot;

import static mightydanp.industrialtech.common.data.IndustrialTechData.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by MightyDanp on 7/24/2020.
 */
public class SlotPump extends SlotItemHandler {
    private static Set<Item> validItems  = new HashSet<Item>();

    public SlotPump(IItemHandler stackHandler, int index, int x, int y) {
        super(stackHandler, index, x, y);
    }

    public SlotPump(Set<Item> itemArrayIn, IItemHandler stackHandler, int index, int x, int y) {
        super(stackHandler, index, x, y);
        validItems = itemArrayIn;
    }

    public static Set<Item> getValidItems(){
        return validItems;
    }
    //validItems.addAll(Arrays.asList(itemSetIn).subList(0, itemSetIn.length));


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