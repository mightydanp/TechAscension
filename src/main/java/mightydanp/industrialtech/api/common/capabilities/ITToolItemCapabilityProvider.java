package mightydanp.industrialtech.api.common.capabilities;

import mightydanp.industrialtech.api.common.handler.itemstack.ITToolItemItemStackHandler;
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
 * Created by MightyDanp on 4/7/2021.
 */
public class ITToolItemCapabilityProvider implements ICapabilitySerializable<INBT> {

    private final Direction numberOfSlots = null;
    private static final int maxSlots = 3;
    private ITToolItemItemStackHandler itToolItemItemStackHandler;

    private ITToolItemItemStackHandler getCachedInventory() {
        if (itToolItemItemStackHandler == null) {
            itToolItemItemStackHandler = new ITToolItemItemStackHandler(maxSlots);
        }
        return itToolItemItemStackHandler;
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capabilityIn, @Nullable Direction sideIn) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capabilityIn) return (LazyOptional<T>)(lazyInitialisionSupplier);
        return LazyOptional.empty();
    }

    private final LazyOptional<IItemHandler> lazyInitialisionSupplier = LazyOptional.of(this::getCachedInventory);

    @Override
    public INBT serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedInventory(), numberOfSlots);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(getCachedInventory(), numberOfSlots, nbt);
    }
}
