package mightydanp.techascension.common.tileentities;

import mightydanp.techascension.common.blocks.CampfireBlockOverride;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;

import static mightydanp.techascension.common.blocks.CampfireBlockOverride.LOG;
import static mightydanp.techascension.common.blocks.CampfireBlockOverride.isLit;

/**
 * Created by MightyDanp on 5/6/2021.
 */

public class CampfireBlockEntityOverride extends BlockEntity implements MenuProvider, BlockEntityTicker<CampfireBlockEntityOverride> {

    @Nullable
    private BlockState cachedBlockState;

    public static int numberOfAshSlots = 1;
    public static int numberOfTinderSlots = 1;
    public static int numberOfSlots = + numberOfAshSlots + numberOfTinderSlots;

    private int fuelBurnProgress = 0;
    private int fuelBurnTime = 1;

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(numberOfSlots, ItemStack.EMPTY);

    public final int tinderSlotNumber= 0;
    public final int ashSlotNumber   = 1;

    public boolean signalFire = false;
    public boolean keepLogsFormed = false;
    public boolean canPlaceRecipeItems = false;

    public CampfireBlockEntityOverride(BlockPos blockPosIn, BlockState blockStateIn) {
        super(ModBlockEntity.campfire_block_entity.get(), blockPosIn, blockStateIn);
    }

    public ItemStack getTinderSlot(){
        return inventory.get(tinderSlotNumber);
    }

    public ItemStack getAshSlot(){
        return inventory.get(ashSlotNumber);
    }


    @Override
    public void tick(Level levelIn, BlockPos blockPosIn, BlockState blockStateIn, CampfireBlockEntityOverride campfireBlockEntityOverrideIn) {
        if (levelIn.isClientSide) {
            if (isLit(blockStateIn)) {
                campfireBlockEntityOverrideIn.makeParticles();
            }
        } else {
            if (isLit(blockStateIn)) {
                campfireBlockEntityOverrideIn.burnLogs();
                campfireBlockEntityOverrideIn.ash();

                if (blockStateIn.getValue(LOG) == 0) {
                    campfireBlockEntityOverrideIn.dowse();
                    campfireBlockEntityOverrideIn.canPlaceRecipeItems = false;
                }
            }
        }

    }

    private void burnLogs() {
            if(level != null) {
                if (getBlockState().getValue(LOG) > 0) {
                    //fuelBurnTime = 600;
                    fuelBurnTime = 160;
                    fuelBurnProgress++;
                    if (this.fuelBurnProgress >= this.fuelBurnTime) {
                        CampfireBlockOverride.shrinkLogs(level, getBlockPos(), getBlockState(), 1);

                        this.fuelBurnProgress = 0;
                        this.markUpdated();
                    }
                } else {
                    CampfireBlockOverride.setLit(level, getBlockPos(), getBlockState(), false);

                    this.markUpdated();
                }
            }
    }

    private void ash() {
        /*
        ItemStack itemstack = getFuelSlot();
        Random rand = new Random();
        if (rand.nextInt(2000) == 0) {
            if (!itemstack.isEmpty()) {
                itemstack.shrink(1);
                //needs ash
                //ashItemSlots.set(ashSlotNumber, itemstack);
                this.markUpdated();
            }else {
                this.markUpdated();
            }
        }
         */
    }

    private void makeParticles() {
        Level world = this.getLevel();
        if (world != null) {
            BlockPos blockpos = this.getBlockPos();
            Random random = world.random;
            if (random.nextFloat() < 0.11F) {
                for(int i = 0; i < random.nextInt(2) + 2; ++i) {
                    CampfireBlockOverride.makeParticles(world, blockpos);
                }
            }
        }
    }

    public NonNullList<net.minecraft.world.item.ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public void load(CompoundTag nbt) {
        loadMetadataAndItems(nbt);
    }

    private CompoundTag loadMetadataAndItems(CompoundTag nbt) {
        super.load(nbt);
        Direction directionNew = Direction.byName(nbt.getString("direction"));

        fuelBurnTime = nbt.getInt("fuel_burn_time");
        fuelBurnProgress = nbt.getInt("fuel_burn_progress");
        signalFire = nbt.getBoolean("signal_fire");
        keepLogsFormed = nbt.getBoolean("keep_logs_formed");
        canPlaceRecipeItems = nbt.getBoolean("can_place_recipe_items");

        ContainerHelper.loadAllItems(nbt, this.inventory);
        return nbt;
    }

    @Override
    public void saveAdditional(CompoundTag tag){
        this.saveMetadataAndItems(tag);
        super.saveAdditional(tag);
    }

    private void saveMetadataAndItems(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("fuel_burn_time", fuelBurnTime);
        tag.putInt("fuel_burn_progress", fuelBurnProgress);
        tag.putBoolean("signal_fire", signalFire);
        tag.putBoolean("keep_logs_formed", keepLogsFormed);
        tag.putBoolean("can_place_recipe_items", canPlaceRecipeItems);

        ContainerHelper.saveAllItems(tag, this.inventory, true);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public boolean placeItemStack(ItemStack p_213984_1_, int slotNumber) {
        if (inventory.get(slotNumber).isEmpty()) {
            this.inventory.set(slotNumber, p_213984_1_.split(1));
            this.markUpdated();
            return true;
        }
        return false;
    }


    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void clearContent() {
        this.inventory.clear();
    }

    public void dowse() {
        if (this.level != null) {
            BlockState blockState1 = getBlockState().setValue(CampfireBlockOverride.LIT, false);
            level.setBlock(this.getBlockPos(), blockState1, 3);

            if (!this.level.isClientSide) {
                NonNullList<ItemStack> inventoryCopy = NonNullList.withSize(numberOfSlots, ItemStack.EMPTY);
                Containers.dropContents(this.level, this.getBlockPos(), inventoryCopy);
            }

            this.markUpdated();
        }
    }

    public BlockState getBlockState() {
        return this.level.getBlockState(getBlockPos());
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_createMenu_1_, Inventory p_createMenu_2_, Player p_createMenu_3_) {
        return null;
    }
}
