package mightydanp.industrialtech.common.tileentities;

import mightydanp.industrialtech.common.blocks.CampfireBlockOverride;
import mightydanp.industrialtech.common.blocks.state.CampfireStateController;
import mightydanp.industrialtech.common.crafting.recipe.CampfireOverrideRecipe;
import mightydanp.industrialtech.common.crafting.recipe.ModRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
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
    private CampfireStateController campfireStateController;

    public static int numberOfFuelSlots = 1;
    private final NonNullList<ItemStack> logItemSlots = NonNullList.withSize(numberOfFuelSlots, ItemStack.EMPTY);

    private int burnProgress;
    private int burnTime;

    public static int numberOfCookSlots = 4;
    private final NonNullList<ItemStack> cookItemSlots = NonNullList.withSize(numberOfCookSlots, ItemStack.EMPTY);
    private final int[] cookingProgresses = new int[4];
    private final int[] cookingTimes = new int[4];

    public CampfireTileEntityOverride() {
        super(ModTileEntities.campfire_tile_entity.get());
        campfireStateController = new CampfireStateController(new CompoundNBT());
    }

    public void tick() {
        boolean isLit = this.campfireStateController.getIsLit();

        if (level.isClientSide) {
            if (isLit) {
                this.makeParticles();
            }
        } else {
            if (isLit) {
                cook();
                burn();

                if(logItemSlots.get(0).isEmpty()){
                    campfireStateController.putIsLit(false);
                    dowse();
                }
            } else {
                for(int i = 0; i < cookItemSlots.size(); ++i) {
                    if (this.cookingProgresses[i] > 0) {
                        this.cookingProgresses[i] = MathHelper.clamp(this.cookingProgresses[i] - 2, 0, this.cookingTimes[i]);
                    }
                }
            }
        }
    }

    private void cook() {
        for(int i = 0; i < cookItemSlots.size(); ++i) {
            ItemStack itemstack = cookItemSlots.get(i);
            if (!itemstack.isEmpty()) {
                if (this.cookingProgresses[i] >= this.cookingTimes[i]) {
                    IInventory iinventory = new Inventory(itemstack);
                    //change
                    ItemStack itemstack1 = this.level.getRecipeManager().getRecipeFor(IRecipeType.CAMPFIRE_COOKING, iinventory, this.level).map((p_213979_1_) -> p_213979_1_.assemble(iinventory)).orElse(itemstack);
                    BlockPos blockpos = this.getBlockPos();
                    InventoryHelper.dropItemStack(this.level, blockpos.getX(), blockpos.getY(), blockpos.getZ(), itemstack1);
                    this.cookItemSlots.set(i, ItemStack.EMPTY);
                    this.markUpdated();
                }
            }
        }
    }

    private void burn() {
            ItemStack itemstack = logItemSlots.get(0);
            if (!itemstack.isEmpty()) {
                int j = this.burnProgress++;
                burnTime = getItemBurnTime(this.getLevel(), itemstack);
                if (this.burnProgress >= this.burnTime) {
                    logItemSlots.get(0).shrink(1);
                    this.markUpdated();
                }
            }else{
                campfireStateController.putIsLit(false);
                this.markUpdated();
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

            Direction directionA = Direction.byName(this.getCampfireNBT().getDirection());


            int l = directionA.get2DDataValue();

            for(int j = 0; j < cookItemSlots.size(); ++j) {
                if (!cookItemSlots.get(j).isEmpty() && random.nextFloat() < 0.2F) {
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

    public NonNullList<net.minecraft.item.ItemStack> getCookItemSlots() {
        return cookItemSlots;
    }
    public NonNullList<net.minecraft.item.ItemStack> getLogItemSlots() {
        return logItemSlots;
    }

    @Override
    public void load(BlockState blockState, CompoundNBT nbt) {
        super.load(blockState, nbt);
        cookItemSlots.clear();
        campfireStateController = new CampfireStateController(nbt.getCompound("campfire"));

        this.burnTime = campfireStateController.getBurnTime();
        this.burnProgress = campfireStateController.getBurnProgress();

        ItemStackHelper.loadAllItems(nbt, this.cookItemSlots);
        if (nbt.contains("cooking_times", 11)) {
            int[] aint = campfireStateController.getCookingTimes();
            System.arraycopy(aint, 0, this.cookingProgresses, 0, Math.min(this.cookingTimes.length, aint.length));
        }

        if (nbt.contains("cooking_total_times", 11)) {
            int[] aint1 = campfireStateController.getCookingTotalTimes();
            System.arraycopy(aint1, 0, this.cookingTimes, 0, Math.min(this.cookingTimes.length, aint1.length));
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.put("campfire", campfireStateController.getCampfireNBT());
        campfireStateController.putBurnTime(this.burnTime);
        campfireStateController.putBurnProgress(this.burnProgress);
        campfireStateController.putCookingTimes(this.cookingProgresses);
        campfireStateController.putCookingTotalTimes(this.cookingTimes);
        this.saveMetadataAndItems(nbt);

        return super.save(nbt);
    }

    private CompoundNBT saveMetadataAndItems(CompoundNBT nbt) {
        super.save(nbt);
        ItemStackHelper.saveAllItems(nbt, this.cookItemSlots, true);
        ItemStackHelper.saveAllItems(nbt, this.logItemSlots, true);
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

    public Optional<CampfireOverrideRecipe> getCookableRecipe(ItemStack p_213980_1_) {
        return this.cookItemSlots.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.level.getRecipeManager().getRecipeFor(ModRecipes.campfireType, new Inventory(p_213980_1_), this.level);
    }

    public boolean placeFood(ItemStack p_213984_1_, int p_213984_2_) {
        for(int i = 0; i < this.cookItemSlots.size(); ++i) {
            ItemStack itemstack = this.cookItemSlots.get(i);
            if (itemstack.isEmpty()) {
                this.cookingTimes[i] = p_213984_2_;
                this.cookingProgresses[i] = 0;
                this.cookItemSlots.set(i, p_213984_1_.split(1));
                this.markUpdated();
                return true;
            }
        }

        return false;
    }


    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public void clearContent() {
        this.cookItemSlots.clear();
        this.logItemSlots.clear();
    }

    public void dowse() {
        if (this.level != null) {
            if (!this.level.isClientSide) {
                InventoryHelper.dropContents(this.level, this.getBlockPos(), this.getCookItemSlots());
            }

            this.markUpdated();
        }

    }

    public CampfireBlockOverride getCampfireBlock(){
        return (CampfireBlockOverride)getBlockState().getBlock();
    }

    public CampfireStateController getCampfireNBT(){
        return campfireStateController;
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
        if(!campfireStateController.getKeepLogsFormed()){
            boolean keepFormed = campfireStateController.getKeepLogsFormed();
            if(logItemSlots.get(0).getCount() >= 4){
                campfireStateController.putKeepLogsFormed(true);
                return keepFormed;
            }
        }
        return false;
    }


}
