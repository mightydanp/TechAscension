package mightydanp.industrialtech.common.capabilities;

import mightydanp.industrialtech.api.common.handler.itemstack.ITToolItemItemStackHandler;
import mightydanp.industrialtech.common.handler.itemstack.CampFireItemStackHandler;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
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
public class CampFireCapabilityProvider implements ICapabilitySerializable<INBT> {

    private final Direction numberOfSlots = null;
    private static final int maxSlots = 3;
    private CampFireItemStackHandler campFireItemStackHandler;

    private CampFireItemStackHandler getCachedInventory() {
        if (campFireItemStackHandler == null) {
            campFireItemStackHandler = new CampFireItemStackHandler(maxSlots);
        }
        return campFireItemStackHandler;
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capabilityIn, @Nullable Direction sideIn) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capabilityIn)
            return (LazyOptional<T>) (lazyInitialisionSupplier);
        return LazyOptional.empty();
    }

    private final LazyOptional<IItemHandler> lazyInitialisionSupplier = LazyOptional.of(this::getCachedInventory);

    @Override
    public INBT serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedInventory(), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(getCachedInventory(), numberOfSlots, nbt);
    }
}