package mightydanp.industrialtech.common.inventory.content;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;

import java.util.function.Predicate;

/**
 * Created by MightyDanp on 9/6/2020.
 */
public class ContentBasicMachineFrame implements IInventory {

    private Predicate<PlayerEntity> canPlayerAccessInventoryLambda = x-> true;

    private Notify markDirtyNotificationLambda = ()->{};

    private Notify openInventoryNotificationLambda = ()->{};

    private Notify closeInventoryNotificationLambda = ()->{};

    private final ItemStackHandler machineFrameContents;

    private ContentBasicMachineFrame(int size) {
        this.machineFrameContents = new ItemStackHandler(size);
    }

    private ContentBasicMachineFrame(int size, Predicate<PlayerEntity> canPlayerAccessInventoryLambda, Notify markDirtyNotificationLambda) {
        this.machineFrameContents = new ItemStackHandler(size);
        this.canPlayerAccessInventoryLambda = canPlayerAccessInventoryLambda;
        this.markDirtyNotificationLambda = markDirtyNotificationLambda;
    }

    public static ContentBasicMachineFrame createForTileEntity(int size,Predicate<PlayerEntity> canPlayerAccessInventoryLambda,Notify markDirtyNotificationLambda) {
        return new ContentBasicMachineFrame(size, canPlayerAccessInventoryLambda, markDirtyNotificationLambda);
    }

    public static ContentBasicMachineFrame createForClientSideContainer(int size) {
        return new ContentBasicMachineFrame(size);
    }


    public CompoundNBT serializeNBT()  {
        return machineFrameContents.serializeNBT();
    }


    public void deserializeNBT(CompoundNBT nbt)   {
        machineFrameContents.deserializeNBT(nbt);
    }

    public void setCanPlayerAccessInventoryLambda(Predicate<PlayerEntity> canPlayerAccessInventoryLambda) {
        this.canPlayerAccessInventoryLambda = canPlayerAccessInventoryLambda;
    }

    public void setMarkDirtyNotificationLambda(Notify markDirtyNotificationLambda) {
        this.markDirtyNotificationLambda = markDirtyNotificationLambda;
    }

    public void setOpenInventoryNotificationLambda(Notify openInventoryNotificationLambda) {
        this.openInventoryNotificationLambda = openInventoryNotificationLambda;
    }

    public void setCloseInventoryNotificationLambda(Notify closeInventoryNotificationLambda) {
        this.closeInventoryNotificationLambda = closeInventoryNotificationLambda;
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return canPlayerAccessInventoryLambda.test(player);  // on the client, this does nothing. on the server, ask our parent TileEntity.
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return machineFrameContents.isItemValid(index, stack);
    }

    @FunctionalInterface
    public interface Notify {
        void invoke();
    }

    @Override
    public void markDirty() {
        markDirtyNotificationLambda.invoke();
    }

    @Override
    public void openInventory(PlayerEntity player) {
        openInventoryNotificationLambda.invoke();
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        closeInventoryNotificationLambda.invoke();
    }

    @Override
    public int getSizeInventory() {
        return machineFrameContents.getSlots();
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < machineFrameContents.getSlots(); ++i) {
            if (!machineFrameContents.getStackInSlot(i).isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return machineFrameContents.getStackInSlot(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return machineFrameContents.extractItem(index, count, false);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        int maxPossibleItemStackSize = machineFrameContents.getSlotLimit(index);
        return machineFrameContents.extractItem(index, maxPossibleItemStackSize, false);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        machineFrameContents.setStackInSlot(index, stack);
    }

    @Override
    public void clear() {
        for (int i = 0; i < machineFrameContents.getSlots(); ++i) {
            machineFrameContents.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
}