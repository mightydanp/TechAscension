package mightydanp.industrialtech.api.common.tileentities;

import mightydanp.industrialtech.api.common.blocks.HoleBlock;
import mightydanp.industrialtech.api.common.crafting.recipe.HoleRecipe;
import mightydanp.industrialtech.api.common.crafting.recipe.Recipes;
import mightydanp.industrialtech.common.tileentities.ModBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by MightyDanp on 10/10/2021.
 */
public class HoleTileEntity extends BlockEntity implements MenuProvider, TickableBlockEntity {

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
        super(ModBlockEntity.hole_block_entity.get());
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

        return inventoryCopy.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.level.getRecipeManager().getRecipeFor(Recipes.holeType, new SimpleContainer(p_213980_1_), this.level);
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

    @Override
    public void load( CompoundTag nbt) {
        loadMetadataAndItems(nbt);
    }

    private CompoundTag loadMetadataAndItems(CompoundTag nbt) {
        super.load(nbt);
        Direction directionNew = Direction.byName(nbt.getString("direction"));

        desiredBlockState = NbtUtils.readBlockState(nbt.getCompound("desired_block_state"));
        progress = nbt.getInt("progress");
        finishedProgress = nbt.getInt("finished_progress");
        minTicksForHoleToFill = nbt.getInt("min_ticks");
        maxTicksForHoleToFill = nbt.getInt("max_ticks");
        minResult = nbt.getInt("min_result");
        maxResult = nbt.getInt("max_result");
        holeColor = nbt.getInt("hole_color");
        resinColor = nbt.getInt("resin_color");
        ingredientItemDamage = nbt.getInt("harvest_tool_damage");

        ContainerHelper.loadAllItems(nbt, this.inventory);
        CompoundTag outputFluidCompound = nbt.getCompound("output_fluid");

        outputFluid = FluidStack.loadFluidStackFromNBT(outputFluidCompound);

        return nbt;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        this.saveMetadataAndItems(nbt);
        return super.save(nbt);
    }

    private CompoundTag saveMetadataAndItems(CompoundTag nbt) {
        super.save(nbt);

        nbt.put("desired_block_state", NbtUtils.writeBlockState(desiredBlockState));
        nbt.putInt("progress", progress);
        nbt.putInt("finished_progress", finishedProgress);
        nbt.putInt("min_ticks", minTicksForHoleToFill);
        nbt.putInt("max_ticks", maxTicksForHoleToFill);
        nbt.putInt("min_result", minResult);
        nbt.putInt("max_result", maxResult);
        nbt.putInt("hole_color", holeColor);
        nbt.putInt("resin_color", resinColor);
        nbt.putInt("harvest_tool_damage", ingredientItemDamage);

        ContainerHelper.saveAllItems(nbt, this.inventory, true);

        CompoundTag outputTankCompound = outputFluid.writeToNBT(new CompoundTag());
        nbt.put("output_tank", outputTankCompound);

        return nbt;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 13, this.getUpdateTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveMetadataAndItems(new CompoundTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
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
