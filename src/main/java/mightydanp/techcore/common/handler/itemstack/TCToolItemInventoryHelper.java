package mightydanp.techcore.common.handler.itemstack;

import mightydanp.techcore.common.tool.part.BindingItem;
import mightydanp.techcore.common.tool.part.HandleItem;
import mightydanp.techcore.common.tool.part.HeadItem;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by MightyDanp on 4/7/2021.
 */
public class TCToolItemInventoryHelper{
    public final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);

    public TCToolItemInventoryHelper(ItemStack itemStackIn) {
        CompoundTag inventoryCompound = itemStackIn.getOrCreateTag().getCompound("inventory");
        if(itemStackIn.getOrCreateTag().contains("inventory")) {
            ContainerHelper.loadAllItems(inventoryCompound, inventory);
        }else{
            ContainerHelper.saveAllItems(inventoryCompound, inventory);
        }
    }

    public NonNullList<ItemStack> getInventory(ItemStack toolIn){
        NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);

        CompoundTag inventoryCompound = toolIn.getOrCreateTag().getCompound("inventory");
        if(toolIn.getOrCreateTag().contains("inventory")) {
            ContainerHelper.loadAllItems(inventoryCompound, inventory);
        }

        return inventory;
    }

    public ItemStack getToolHandle(ItemStack toolIn) {
        return getInventory(toolIn).get(0);
    }

    public void setToolHandle(ItemStack toolIn, ItemStack itemStackIn) {
        inventory.set(0, itemStackIn);

        CompoundTag tag = toolIn.getOrCreateTag().getCompound("inventory");
        ContainerHelper.saveAllItems(tag, inventory);
        toolIn.getOrCreateTag().put("inventory", tag);

    }

    public ItemStack getToolHead(ItemStack toolIn) {
        return getInventory(toolIn).get(1);
    }

    public void setToolHead(ItemStack toolIn, ItemStack itemStackIn) {
        inventory.set(1, itemStackIn);

        CompoundTag tag = toolIn.getOrCreateTag().getCompound("inventory");
        ContainerHelper.saveAllItems(tag, inventory);
        toolIn.getOrCreateTag().put("inventory", tag);
        toolIn.getOrCreateTag();
    }

    public ItemStack getToolBinding(ItemStack toolIn) {
        return getInventory(toolIn).get(2);
    }

    public void setToolBinding(ItemStack toolIn, ItemStack itemStackIn) {
        inventory.set(2, itemStackIn);

        CompoundTag tag = toolIn.getOrCreateTag().getCompound("inventory");
        ContainerHelper.saveAllItems(tag, inventory);
        toolIn.getOrCreateTag().put("inventory", tag);
    }

}
