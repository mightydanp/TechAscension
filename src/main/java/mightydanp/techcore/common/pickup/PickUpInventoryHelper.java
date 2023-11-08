package mightydanp.techcore.common.pickup;;
import mightydanp.techapi.common.world.UndefinedContainerHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PickUpInventoryHelper {
    public final List<ItemStack> inventory = new ArrayList<>();

    public PickUpInventoryHelper(ItemStack itemStackIn) {
        CompoundTag inventoryCompound = itemStackIn.getOrCreateTag().getCompound("inventory");
        if(itemStackIn.getOrCreateTag().contains("inventory")) {
            UndefinedContainerHelper.loadAllItems(inventoryCompound, inventory);
        }else{
            UndefinedContainerHelper.saveAllItems(inventoryCompound, inventory);
        }
    }

    public List<ItemStack> getInventory(ItemStack toolIn){
        List<ItemStack> inventory = new ArrayList<>();

        CompoundTag inventoryCompound = toolIn.getOrCreateTag().getCompound("inventory");
        if(toolIn.getOrCreateTag().contains("inventory")) {
            UndefinedContainerHelper.loadAllItems(inventoryCompound, inventory);
        }

        return inventory;
    }
}
