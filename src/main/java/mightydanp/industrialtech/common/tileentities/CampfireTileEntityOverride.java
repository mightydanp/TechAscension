package mightydanp.industrialtech.common.tileentities;

import mightydanp.industrialtech.common.blocks.CampfireBlockOverride;
import mightydanp.industrialtech.common.crafting.recipe.CampfireOverrideCharRecipe;
import mightydanp.industrialtech.common.crafting.recipe.CampfireOverrideRecipe;
import mightydanp.industrialtech.common.crafting.recipe.ModRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

/**
 * Created by MightyDanp on 5/6/2021.
 */

public class CampfireTileEntityOverride extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    @Nullable
    private BlockState cachedBlockState;

    public static int numberOfCookSlots = 4;
    public static int numberOfFuelSlots = 1;
    public static int numberOfAshSlots = 1;
    public static int numberOfTinderSlots = 1;
    public static int numberOfSlots = numberOfCookSlots + numberOfFuelSlots + numberOfAshSlots + numberOfTinderSlots;

    private int fuelBurnProgress = 0;
    private int fuelBurnTime = 1;

    private final int[] cookingProgresses = new int[]{0,0,0,0};
    private final int[] cookingTimes      = new int[]{1,1,1,1};
    private final int[] cookedSlotChecker = new int[]{0,0,0,0};

    private final NonNullList<ItemStack> inventory = NonNullList.withSize(numberOfSlots, ItemStack.EMPTY);

    public final int cookSlot1Number = 0;
    public final int cookSlot2Number = 1;
    public final int cookSlot3Number = 2;
    public final int cookSlot4Number = 3;
    public final int fuelSlotNumber  = 4;
    public final int ashSlotNumber   = 5;
    public final int tinderSlotNumber= 6;

    public boolean isLit = false;
    public Direction direction = Direction.NORTH;
    public boolean signalFire = false;
    public boolean keepLogsFormed = false;
    public boolean canPlaceRecipeItems = false;

    public CampfireTileEntityOverride() {
        super(ModTileEntities.campfire_tile_entity.get());
    }

    public ItemStack getCookSlot1(){
        return inventory.get(cookSlot1Number);
    }

    public ItemStack getCookSlot2(){
        return inventory.get(cookSlot2Number);
    }
    public ItemStack getCookSlot3(){
        return inventory.get(cookSlot3Number);
    }
    public ItemStack getCookSlot4(){
        return inventory.get(cookSlot4Number);
    }
    public ItemStack getFuelSlot(){
        return inventory.get(fuelSlotNumber);
    }
    public ItemStack getAshSlot(){
        return inventory.get(ashSlotNumber);
    }
    public ItemStack getTinderSlot(){
        return inventory.get(tinderSlotNumber);
    }

    public void tick() {

        if (level.isClientSide) {
            if (isLit) {
                this.makeParticles();
            }
        } else {
            if (isLit) {
                cook();
                burnLogs();
                ash();

                if(getFuelSlot().isEmpty()){
                    isLit = false;
                    dowse();
                    canPlaceRecipeItems = false;
                }
            } else {
                for(int i = 0; i < numberOfCookSlots; ++i) {
                    if (this.cookingProgresses[i] > 0) {
                        this.cookingProgresses[i] = MathHelper.clamp(this.cookingProgresses[i] - 2, 0, this.cookingTimes[i]);
                    }
                }
            }
        }
    }

    private void cook() {
        for(int i = 0; i < numberOfCookSlots; ++i) {
            ItemStack itemstack = inventory.get(i);
            if (!itemstack.isEmpty()) {
                this.cookingProgresses[i]++;
                if (this.cookingProgresses[i] >= this.cookingTimes[i] && this.cookedSlotChecker[i] == 0) {
                    IInventory iinventory = new Inventory(itemstack);
                    ItemStack itemStack1 = this.level.getRecipeManager().getRecipeFor(ModRecipes.campfireType, iinventory, this.level).map((p_213979_1_) -> p_213979_1_.assemble(iinventory)).orElse(itemstack);
                    BlockPos blockpos = this.getBlockPos();
                    //InventoryHelper.dropItemStack(this.level, blockpos.getX(), blockpos.getY(), blockpos.getZ(), itemStack1);
                    this.inventory.set(i, itemStack1);
                    this.cookedSlotChecker[i] = 1;
                    this.markUpdated();
                }else{
                    if(this.cookedSlotChecker[i] == 1){
                        IInventory iinventory = new Inventory(itemstack);
                        ItemStack itemStack1 = this.level.getRecipeManager().getRecipeFor(ModRecipes.campfireCharType, iinventory, this.level).map((p_213979_1_) -> p_213979_1_.assemble(iinventory)).orElse(itemstack);
                        this.inventory.set(i, itemStack1);
                    }
                }
            }else{
                this.cookedSlotChecker[i] = 0;
            }
        }
    }

    private void burnLogs() {
            ItemStack itemstack = getFuelSlot();
            if (!itemstack.isEmpty()) {
                int j = this.fuelBurnProgress++;
                fuelBurnTime = getItemBurnTime(this.getLevel(), itemstack);
                if (this.fuelBurnProgress >= this.fuelBurnTime) {
                    itemstack.shrink(1);
                    inventory.set(fuelSlotNumber, itemstack);
                    this.fuelBurnProgress = 0;
                    this.markUpdated();
                }
            }else{
                isLit = false;
                this.markUpdated();
            }
    }

    private void ash() {
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
    }

    private void makeParticles() {
        World world = this.getLevel();
        if (world != null) {
            BlockPos blockpos = this.getBlockPos();
            Random random = world.random;
            if (random.nextFloat() < 0.11F) {
                for(int i = 0; i < random.nextInt(2) + 2; ++i) {
                    CampfireBlockOverride.makeParticles(world, blockpos);
                }
            }

            Direction directionA = direction;


            int l = directionA.get2DDataValue();

            for(int j = 0; j < numberOfCookSlots; ++j) {
                if (!inventory.get(j).isEmpty() && random.nextFloat() < 0.2F) {
                    Direction direction = Direction.from2DDataValue(Math.floorMod(j + l, 4));
                    float f = 0.3125F;
                    double d0 = (double)blockpos.getX() + 0.5D - (double)((float)direction.getStepX() * 0.3125F) + (double)((float)direction.getClockWise().getStepX() * 0.3125F);
                    double d1 = (double)blockpos.getY() + 0.5D;
                    double d2 = (double)blockpos.getZ() + 0.5D - (double)((float)direction.getStepZ() * 0.3125F) + (double)((float)direction.getClockWise().getStepZ() * 0.3125F);

                    for(int k = 0; k < 4; ++k) {
                        world.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 5.0E-4D, 0.0D);
                    }
                }
            }
        }
    }

    public NonNullList<net.minecraft.item.ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public void load(BlockState blockState, CompoundNBT nbt) {
        loadMetadataAndItems(blockState, nbt);
    }

    private CompoundNBT loadMetadataAndItems(BlockState blockState, CompoundNBT nbt) {
        super.load(blockState, nbt);
        Direction directionNew = Direction.byName(nbt.getString("direction"));

        fuelBurnTime = nbt.getInt("fuel_burn_time");
        fuelBurnProgress = nbt.getInt("fuel_burn_progress");
        isLit = nbt.getBoolean("is_lit");
        direction = directionNew;
        signalFire = nbt.getBoolean("signal_fire");
        keepLogsFormed = nbt.getBoolean("keep_logs_formed");
        canPlaceRecipeItems = nbt.getBoolean("can_place_recipe_items");

        ItemStackHelper.loadAllItems(nbt, this.inventory);

        if (nbt.contains("cooking_times", 11)) {
            int[] aint = nbt.getIntArray("cooking_times");
            System.arraycopy(aint, 0, this.cookingProgresses, 0, Math.min(this.cookingProgresses.length, aint.length));
        }

        if (nbt.contains("cooking_total_times", 11)) {
            int[] aint1 = nbt.getIntArray("cooking_total_times");
            System.arraycopy(aint1, 0, this.cookingTimes, 0, Math.min(this.cookingTimes.length, aint1.length));
        }

        if (nbt.contains("cooked_checker", 11)) {
            int[] aint1 = nbt.getIntArray("cooked_checker");
            System.arraycopy(aint1, 0, this.cookedSlotChecker, 0, Math.min(this.cookedSlotChecker.length, aint1.length));
        }
        return nbt;
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        this.saveMetadataAndItems(nbt);

        return super.save(nbt);
    }

    private CompoundNBT saveMetadataAndItems(CompoundNBT nbt) {
        super.save(nbt);
        nbt.putInt("fuel_burn_time", fuelBurnTime);
        nbt.putInt("fuel_burn_progress", fuelBurnProgress);
        nbt.putIntArray("cooking_times", cookingProgresses);
        nbt.putIntArray("cooking_total_times", cookingTimes);
        nbt.putIntArray("cooked_checker", cookedSlotChecker);
        nbt.putBoolean("is_lit", isLit);
        nbt.putString("direction", direction.getName());
        nbt.putBoolean("signal_fire", signalFire);
        nbt.putBoolean("keep_logs_formed", keepLogsFormed);
        nbt.putBoolean("can_place_recipe_items", canPlaceRecipeItems);

        ItemStackHelper.saveAllItems(nbt, this.inventory, true);
        return nbt;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.worldPosition, 13, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.saveMetadataAndItems(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        load(this.getBlockState(), pkt.getTag());
    }

    public Optional<CampfireOverrideRecipe> getCookableRecipe(ItemStack p_213980_1_) {
        NonNullList<ItemStack> inventoryCopy = NonNullList.withSize(numberOfCookSlots, ItemStack.EMPTY);
        for(int i = 0; i == numberOfCookSlots; i++){
            inventoryCopy.set(i, inventory.get(i));
        }
        return inventoryCopy.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.level.getRecipeManager().getRecipeFor(ModRecipes.campfireType, new Inventory(p_213980_1_), this.level);
    }

    public Optional<CampfireOverrideCharRecipe> getCookedChardRecipe(ItemStack p_213980_1_) {
        NonNullList<ItemStack> inventoryCopy = NonNullList.withSize(numberOfCookSlots, ItemStack.EMPTY);
        for(int i = 0; i == numberOfCookSlots; i++){
            inventoryCopy.set(i, inventory.get(i));
        }
        return inventoryCopy.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.level.getRecipeManager().getRecipeFor(ModRecipes.campfireCharType, new Inventory(p_213980_1_), this.level);
    }

    public boolean placeFood(ItemStack p_213984_1_, int slotNumber, int cookTime) {
            if (inventory.get(slotNumber).isEmpty()) {
                this.cookingTimes[slotNumber] = cookTime;
                this.cookingProgresses[slotNumber] = 0;
                this.inventory.set(slotNumber, p_213984_1_.split(1));
                this.markUpdated();
                return true;
            }
        return false;
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
            if (!this.level.isClientSide) {
                NonNullList<ItemStack> inventoryCopy = NonNullList.withSize(numberOfCookSlots, ItemStack.EMPTY);
                for(int i = 0; i == numberOfCookSlots; i++){
                    inventoryCopy.set(i, inventory.get(i));
                    inventory.set(i, ItemStack.EMPTY);
                }
                InventoryHelper.dropContents(this.level, this.getBlockPos(), inventoryCopy);
            }

            this.markUpdated();
        }
    }

    public CampfireBlockOverride getCampfireBlock(){
        return (CampfireBlockOverride)getBlockState().getBlock();
    }

    public BlockState getBlockState() {
        if (this.cachedBlockState == null) {
            this.cachedBlockState = this.level.getBlockState(this.worldPosition);
        }

        return this.cachedBlockState;
    }

    public void updateContainingBlockInfo() {
        this.cachedBlockState = null;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return null;
    }

    public boolean canPlayerAccessInventory(PlayerEntity player) {
        if (this.level.getBlockEntity(this.getBlockPos()) != this) return false;
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.distanceToSqr(getBlockPos().getX() + X_CENTRE_OFFSET, getBlockPos().getY() + Y_CENTRE_OFFSET, getBlockPos().getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    }

    public void markDirty() {
        if (this.level != null) {
            this.cachedBlockState = this.level.getBlockState(this.worldPosition);
            this.level.blockEntityChanged(this.worldPosition, this);
            if (!this.cachedBlockState.isAir(this.level, this.worldPosition)) {
                this.level.updateNeighbourForOutputSignal(this.worldPosition, this.cachedBlockState.getBlock());
            }
        }

    }

    public static int getItemBurnTime(World world, ItemStack stack)
    {
        int burnTime = net.minecraftforge.common.ForgeHooks.getBurnTime(stack);
        return burnTime;
    }

    //Start of main code\\
    public boolean keepLogsFormed(){
        if(!keepLogsFormed){
            if(getFuelSlot().getCount() >= 4){
                keepLogsFormed = true;
                return true;
            }
        }
        return false;
    }


}
