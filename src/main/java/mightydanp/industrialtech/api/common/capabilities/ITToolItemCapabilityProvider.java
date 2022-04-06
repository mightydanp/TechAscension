package mightydanp.industrialtech.api.common.capabilities;

import mightydanp.industrialtech.api.common.handler.itemstack.ITToolItemInventoryHelper;
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
 * Created by MightyDanp on 4/7/2021.
 */
public class ITToolItemCapabilityProvider //implements ICapabilitySerializable<Tag>
 {
/*
    private final Direction numberOfSlots = null;
    private static final int maxSlots = 3;
    private ITToolItemInventoryHelper itToolItemInventoryHelper;

    private ITToolItemInventoryHelper getCachedInventory() {
        if (itToolItemInventoryHelper == null) {
            //itToolItemInventoryHelper = new ITToolItemInventoryHelper(maxSlots);
        }

        return itToolItemInventoryHelper;
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capabilityIn, @Nullable Direction sideIn) {
        //if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capabilityIn) return (LazyOptional<T>)(lazyInitialisationSupplier);
        //return LazyOptional.empty();
    }

    //private final LazyOptional<IItemHandler> lazyInitialisationSupplier = LazyOptional.of(this::getCachedInventory);


    @Override
    public Tag serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedInventory(), numberOfSlots);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(getCachedInventory(), numberOfSlots, nbt);
    }

     */
}
