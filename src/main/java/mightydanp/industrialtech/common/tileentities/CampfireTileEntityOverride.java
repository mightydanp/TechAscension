package mightydanp.industrialtech.common.tileentities;

import mightydanp.industrialtech.api.common.handler.itemstack.ITToolItemItemStackHandler;
import mightydanp.industrialtech.common.IndustrialTech;
import mightydanp.industrialtech.common.blocks.CampfireBlockOverride;
import mightydanp.industrialtech.common.blocks.ModBlocks;
import mightydanp.industrialtech.common.blocks.state.CampfireStateController;
import mightydanp.industrialtech.common.handler.itemstack.CampFireItemStackHandler;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 5/6/2021.
 */
public class CampfireTileEntityOverride extends TileEntity {
    private CampfireBlockOverride campfireBlock;
    private CampfireStateController campfireStateController;
    private int numberOfSlots = putItemStackAndGetNumberOfSlots(ItemStack.EMPTY);

    private CampFireItemStackHandler campFireItemStackHandler;

    public CampfireTileEntityOverride() {
        super(ModTileEntities.campfire_tile_entity.get());
        campfireStateController = new CampfireStateController(new CompoundNBT());
    }

    @Override
    public void load(BlockState blockState, CompoundNBT nbt) {
        super.load(blockState, nbt);
        campfireStateController = new CampfireStateController(nbt.getCompound("campfire"));
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(getCachedInventory(), null, nbt);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("campfire", campfireStateController.getCampfireNBT());
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedInventory(), null);
        return super.save(nbt);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.getBlockPos(), 3, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        handleUpdateTag(getBlockState(), packet.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.save(new CompoundNBT());
    }

    public CampfireBlockOverride getCampfireBlock(){
        return (CampfireBlockOverride)getBlockState().getBlock();
    }

    public CampfireStateController getCampfireNBT(){
        return campfireStateController;
    }

    private final LazyOptional<IItemHandler> lazyInitialisionSupplier = LazyOptional.of(this::getCachedInventory);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capabilityIn, @Nullable Direction side) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == capabilityIn)
            return (LazyOptional<T>) (lazyInitialisionSupplier);
        return LazyOptional.empty();
    }

    private CampFireItemStackHandler getCachedInventory() {
        if (campFireItemStackHandler == null) {
            campFireItemStackHandler = new CampFireItemStackHandler(numberOfSlots);
        }
        return campFireItemStackHandler;
    }


    public CampFireItemStackHandler initItemStackHandler(int amountOfSlots) {
        IItemHandler flowerBag = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        if (!(flowerBag instanceof CampFireItemStackHandler)) {
            IndustrialTech.LOGGER.error("ITToolItem did not have the expected ITEM_HANDLER_CAPABILITY");
            return new CampFireItemStackHandler(amountOfSlots);
        }
        return (CampFireItemStackHandler)flowerBag;
    }

    public CampFireItemStackHandler getItemStackHandler() {
        IItemHandler flowerBag = this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        if (!(flowerBag instanceof CampFireItemStackHandler)) {
            IndustrialTech.LOGGER.error("ITToolItem did not have the expected ITEM_HANDLER_CAPABILITY");
            return new CampFireItemStackHandler(1);
        }
        return (CampFireItemStackHandler)flowerBag;
    }

    private int putItemStackAndGetNumberOfSlots(ItemStack itemstack){
        CampFireItemStackHandler itemStackHandler = getItemStackHandler();
        int numberOfSlots = itemStackHandler.getSlots();

        if(!itemstack.isEmpty()) {
            if (itemStackHandler.getStackInSlot(numberOfSlots) == ItemStack.EMPTY) {
                itemStackHandler.setStackInSlot(numberOfSlots, itemstack);
            } else {
                itemStackHandler.setStackInSlot(numberOfSlots + 1, itemstack);
            }
        }
        return numberOfSlots;
    }

    //Start of main code\\
    public boolean keepLogsFormed(){
        if(!campfireStateController.getKeepLogsFormed()){
            boolean keepFormed = campfireStateController.getKeepLogsFormed();
            if(campFireItemStackHandler.logs.getCount() >= 4){
                campfireStateController.putKeepLogsFormed(true);
                return keepFormed;
            }
        }
        return false;
    }
}
