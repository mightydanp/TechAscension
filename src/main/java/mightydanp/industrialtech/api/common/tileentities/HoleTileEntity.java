package mightydanp.industrialtech.api.common.tileentities;

import mightydanp.industrialtech.api.common.blocks.HoleBlock;
import mightydanp.industrialtech.api.common.crafting.recipe.HoleRecipe;
import mightydanp.industrialtech.api.common.crafting.recipe.Recipes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by MightyDanp on 10/10/2021.
 */
public class HoleTileEntity extends TileEntity implements INamedContainerProvider, ITickableTileEntity {

    public static int numberOfLogSlots = 1;
    public static int numberOfOutputSlots = 1;

    public static int numberOfSlots = numberOfLogSlots + numberOfOutputSlots;
    public static int desiredBlockSlot = 0;
    public static int outputSlot = 1;
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(numberOfSlots, ItemStack.EMPTY);
    private FluidStack outputFluid = new FluidStack(Fluids.EMPTY, 0);

    private HoleRecipe recipe;

    public BlockState desiredBlockState;

    public static final ModelProperty<BlockState> desiredBlock = new ModelProperty<>();

    public int minTicksForHoleToFill;
    public int maxTicksForHoleToFill;

    public int minResult;
    public int maxResult;

    public int holeColor;
    public int resinColor;

    public int ingredientItemDamage;

    public int progress;
    public int finishedProgress;

    public Random random = new Random();

    public HoleTileEntity() {
        super(TileEntities.hole_tile_entity.get());
    }

    public ItemStack getDesiredBlockSlot() {
        return inventory.get(desiredBlockSlot);
    }

    public void setDesiredBlockSlot(ItemStack stackIn) {
        inventory.set(desiredBlockSlot, stackIn);
    }

    public ItemStack getOutputSlot() {
        return inventory.get(outputSlot);
    }

    public void setOutputSlot(ItemStack stackIn) {
        inventory.set(outputSlot, stackIn);
    }

    public FluidStack getOutputFluid() {
        return outputFluid;
    }

    public void setOutputFluid(FluidStack outputTank) {
        this.outputFluid = outputTank;
    }

    public void setDesiredBlockState(BlockState desiredBlockStateIn) {
        this.desiredBlockState = desiredBlockStateIn;
        setChanged();
        if(level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
        }
    }


    @Override
    public void tick() {
        HoleBlock holeBlock = (HoleBlock) this.getBlockState().getBlock();
        if (level != null){
            if(recipe == null){
                Optional<HoleRecipe> validRecipe = getValidRecipe(new ItemStack(getDesiredBlockSlot().getItem()));
                if(validRecipe.isPresent()){
                    recipe = validRecipe.get();
                    minTicksForHoleToFill = recipe.getMinTicks();
                    maxTicksForHoleToFill = recipe.getMaxTicks();
                    minResult = recipe.getMinResult();
                    maxResult = recipe.getMaxResult();
                    holeColor = recipe.getHoleColor();
                    resinColor = recipe.getResinColor();
                    ingredientItemDamage = recipe.getIngredientItemDamage();
                }
            }else {
                progress++;
                    if (progress >= minTicksForHoleToFill) {
                        int randomTickNumber = random.nextInt((maxTicksForHoleToFill + 1 - minTicksForHoleToFill) + minTicksForHoleToFill);
                        if (0 == randomTickNumber) {
                            ItemStack output = recipe.getResultItem();
                            FluidStack outputFluid = new FluidStack(recipe.getResultFluid(), 0);
                            level.setBlockAndUpdate(getBlockPos(), getBlockState().setValue(HoleBlock.RESIN, true));

                            progress = 0;

                            int randomResultNumber = random.nextInt(maxResult + 1 - minResult) + minResult;

                            if(!output.isEmpty()) {
                                if (getOutputSlot().isEmpty()) {
                                    setOutputSlot(new ItemStack(output.getItem(), randomResultNumber));
                                } else {
                                    getOutputSlot().setCount(getOutputSlot().getCount() + randomResultNumber);
                                }
                            }

                            if(!outputFluid.isEmpty()) {
                                if (getOutputFluid().isEmpty()) {
                                    setOutputFluid(outputFluid);
                                } else {
                                    getOutputFluid().grow(outputFluid.getAmount());
                                }
                            }
                        }
                    }
                }
            }
        }

    public Optional<HoleRecipe> getValidRecipe(ItemStack p_213980_1_) {
        NonNullList<ItemStack> inventoryCopy = NonNullList.withSize(numberOfLogSlots, ItemStack.EMPTY);
        for (int i = 0; i == numberOfLogSlots; i++) {
            inventoryCopy.set(i, inventory.get(i));
        }

        return inventoryCopy.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.level.getRecipeManager().getRecipeFor(Recipes.holeType, new Inventory(p_213980_1_), this.level);
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

    @Override
    public void load(BlockState blockState, CompoundNBT nbt) {
        loadMetadataAndItems(blockState, nbt);
    }

    private CompoundNBT loadMetadataAndItems(BlockState blockState, CompoundNBT nbt) {
        super.load(blockState, nbt);
        Direction directionNew = Direction.byName(nbt.getString("direction"));

        desiredBlockState = NBTUtil.readBlockState(nbt.getCompound("desired_block_state"));
        progress = nbt.getInt("progress");
        finishedProgress = nbt.getInt("finished_progress");
        minTicksForHoleToFill = nbt.getInt("min_ticks");
        maxTicksForHoleToFill = nbt.getInt("max_ticks");
        minResult = nbt.getInt("min_result");
        maxResult = nbt.getInt("max_result");
        holeColor = nbt.getInt("hole_color");
        resinColor = nbt.getInt("resin_color");
        ingredientItemDamage = nbt.getInt("harvest_tool_damage");

        ItemStackHelper.loadAllItems(nbt, this.inventory);
        CompoundNBT outputFluidCompound = nbt.getCompound("output_fluid");

        outputFluid = FluidStack.loadFluidStackFromNBT(outputFluidCompound);

        return nbt;
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        this.saveMetadataAndItems(nbt);
        return super.save(nbt);
    }

    private CompoundNBT saveMetadataAndItems(CompoundNBT nbt) {
        super.save(nbt);

        nbt.put("desired_block_state", NBTUtil.writeBlockState(desiredBlockState));
        nbt.putInt("progress", progress);
        nbt.putInt("finished_progress", finishedProgress);
        nbt.putInt("min_ticks", minTicksForHoleToFill);
        nbt.putInt("max_ticks", maxTicksForHoleToFill);
        nbt.putInt("min_result", minResult);
        nbt.putInt("max_result", maxResult);
        nbt.putInt("hole_color", holeColor);
        nbt.putInt("resin_color", resinColor);
        nbt.putInt("harvest_tool_damage", ingredientItemDamage);

        ItemStackHelper.saveAllItems(nbt, this.inventory, true);

        CompoundNBT outputTankCompound = outputFluid.writeToNBT(new CompoundNBT());
        nbt.put("output_tank", outputTankCompound);

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

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(desiredBlock, desiredBlockState)
                .build();
    }
}
