package mightydanp.industrialtech.common.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 9/5/2020.
 */
public class TileEntityBasicMachineFrame extends TileEntity implements ITickableTileEntity {
    private ItemStackHandler itemHandler = createHandler();
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public TileEntityBasicMachineFrame() {
        super(ModTileEntity.basicMachineFrameTileEntityType.get());
    }

    @Override
    public void remove() {
        super.remove();
        handler.invalidate();
    }

    @Override
    public void tick() {
        if (world.isRemote) {
            return;
        }
    }

    @Override
    public void read(CompoundNBT tag) {
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("inv", itemHandler.serializeNBT());
        return super.write(tag);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(10) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() == Items.DIAMOND;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (stack.getItem() != Items.DIAMOND) {
                    return stack;
                }
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    public ItemStackHandler getItemHandler(){ return itemHandler;};
}
