package mightydanp.industrialtech.common.capabilities;

import mightydanp.industrialtech.common.handler.itemstack.CampfireItemStackHandler;
import mightydanp.industrialtech.common.tileentities.CampfireBlockEntityOverride;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 5/10/2021.
 */

public class CampfireCapabilityProvider implements ICapabilitySerializable<Tag> {

    private final Direction numberOfSlots = null;
    private final int maxSlots = putItemStackAndGetNumberOfSlots(ItemStack.EMPTY);;
    public CampfireItemStackHandler campFireItemStackHandler;
    private CampfireBlockEntityOverride campfireTileEntityOverride;

    public CampfireCapabilityProvider(CampfireBlockEntityOverride tileEntityOverride){
        campfireTileEntityOverride = tileEntityOverride;
    }

    private CampfireItemStackHandler getCachedInventory() {
        if (campFireItemStackHandler == null) {
            campFireItemStackHandler = new CampfireItemStackHandler(maxSlots);
        }
        return campFireItemStackHandler;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capabilityIn, @Nullable Direction sideIn) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capabilityIn) {
            return (LazyOptional<T>) (lazyInitialisionSupplier);
        }
        return LazyOptional.empty();
    }

    private final LazyOptional<IItemHandler> lazyInitialisionSupplier = LazyOptional.of(this::getCachedInventory);

    @Override
    public Tag serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedInventory(), null);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(getCachedInventory(), numberOfSlots, nbt);
    }

    private int putItemStackAndGetNumberOfSlots(ItemStack itemstack){
        CampfireItemStackHandler itemStackHandler = this.campFireItemStackHandler;

        if(itemStackHandler != null) {
            int numberOfSlots = itemStackHandler.getSlots();

            if (!itemstack.isEmpty()) {
                if (this.campFireItemStackHandler.getStackInSlot(numberOfSlots) == ItemStack.EMPTY) {
                    itemStackHandler.setStackInSlot(numberOfSlots, itemstack);
                } else {
                    itemStackHandler.setStackInSlot(numberOfSlots + 1, itemstack);
                }
            }
            return numberOfSlots;
        }else{
            return 1;
        }
    }
}