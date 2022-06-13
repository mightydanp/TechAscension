package mightydanp.industrialcore.common.tileentities;

import mightydanp.industrialcore.common.blocks.HoleBlock;
import mightydanp.industrialcore.common.crafting.recipe.HoleRecipe;
import mightydanp.industrialcore.common.crafting.recipe.Recipes;
import mightydanp.industrialtech.common.IndustrialTech;
import mightydanp.industrialtech.common.tileentities.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
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
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * Created by MightyDanp on 10/10/2021.
 */
public class HoleTileEntity extends BlockEntity implements MenuProvider {

    public static int numberOfLogSlots = 1;
    public static int numberOfOutputSlots = 1;

    public static int numberOfSlots = numberOfLogSlots + numberOfOutputSlots;
    public static int desiredBlockSlot = 0;
    public static int outputSlot = 1;
    private final NonNullList<ItemStack> inventory = NonNullList.withSize(numberOfSlots, ItemStack.EMPTY);
    private FluidStack outputFluid = new FluidStack(Fluids.EMPTY, 0);

    public HoleRecipe recipe;

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

    public HoleTileEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntity.hole_block_entity.get(), blockPos, blockState);
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

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T blockEntity) {
        if (level != null && blockEntity instanceof HoleTileEntity holeTileEntity){
            if(holeTileEntity.recipe == null){
                Optional<HoleRecipe> validRecipe = holeTileEntity.getValidRecipe(new ItemStack(holeTileEntity.getDesiredBlockSlot().getItem()));
                if(validRecipe.isPresent()){
                    holeTileEntity.recipe = validRecipe.get();
                    holeTileEntity.minTicksForHoleToFill = holeTileEntity.recipe.getMinTicks();
                    holeTileEntity.maxTicksForHoleToFill = holeTileEntity.recipe.getMaxTicks();
                    holeTileEntity.minResult = holeTileEntity.recipe.getMinResult();
                    holeTileEntity.maxResult = holeTileEntity.recipe.getMaxResult();
                    holeTileEntity.holeColor = holeTileEntity.recipe.getHoleColor();
                    holeTileEntity.resinColor = holeTileEntity.recipe.getResinColor();
                    holeTileEntity.ingredientItemDamage = holeTileEntity.recipe.getIngredientItemDamage();
                }
            }else{
                holeTileEntity.progress++;
                    if (holeTileEntity.progress >= holeTileEntity.minTicksForHoleToFill) {
                        int randomTickNumber = holeTileEntity.random.nextInt((holeTileEntity.maxTicksForHoleToFill + 1 - holeTileEntity.minTicksForHoleToFill) + holeTileEntity.minTicksForHoleToFill);
                        if (0 == randomTickNumber) {
                            ItemStack output = holeTileEntity.recipe.getResultItem();
                            FluidStack outputFluid = new FluidStack(holeTileEntity.recipe.getResultFluid(), 0);
                            level.setBlockAndUpdate(holeTileEntity.getBlockPos(), holeTileEntity.getBlockState().setValue(HoleBlock.RESIN, true));

                            holeTileEntity.progress = 0;

                            int randomResultNumber = holeTileEntity.random.nextInt(holeTileEntity.maxResult + 1 - holeTileEntity.minResult) + holeTileEntity.minResult;

                            if(output.getItem() != Items.AIR) {
                                if (holeTileEntity.getOutputSlot().isEmpty()) {
                                    holeTileEntity.setOutputSlot(new ItemStack(output.getItem(), randomResultNumber));
                                } else {
                                    holeTileEntity.getOutputSlot().setCount(holeTileEntity.getOutputSlot().getCount() + randomResultNumber);
                                }

                            }

                            if(holeTileEntity.recipe.getResultFluid() != Fluids.EMPTY) {
                                if (holeTileEntity.getOutputFluid().isEmpty()) {
                                    holeTileEntity.setOutputFluid(outputFluid);
                                } else {
                                    holeTileEntity.getOutputFluid().grow(outputFluid.getAmount());
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
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return null;
    }



    @Override
    public void load(CompoundTag nbt) {
        loadMetadataAndItems(nbt);
        super.load(nbt);
    }

    private CompoundTag loadMetadataAndItems(CompoundTag nbt) {
        if(nbt != null) {
            desiredBlockState = NbtUtils.readBlockState(nbt.getCompound("desired_block_state"));
            getModelData().setData(desiredBlock, desiredBlockState);
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
        }else{
            nbt = new CompoundTag();
            super.load(nbt);
            return nbt;
        }

    }



    @Override
    public void saveAdditional(CompoundTag tag){
        saveMetadataAndItems(tag);
    }

    private void saveMetadataAndItems(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("desired_block_state", NbtUtils.writeBlockState(desiredBlockState));
        tag.putInt("progress", progress);
        tag.putInt("finished_progress", finishedProgress);
        tag.putInt("min_ticks", minTicksForHoleToFill);
        tag.putInt("max_ticks", maxTicksForHoleToFill);
        tag.putInt("min_result", minResult);
        tag.putInt("max_result", maxResult);
        tag.putInt("hole_color", holeColor);
        tag.putInt("resin_color", resinColor);
        tag.putInt("harvest_tool_damage", ingredientItemDamage);

        ContainerHelper.saveAllItems(tag, this.inventory, true);
        CompoundTag outputTankCompound = outputFluid.writeToNBT(new CompoundTag());
        tag.put("output_tank", outputTankCompound);
    }


    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag compoundtag = new CompoundTag();
        this.saveAdditional(compoundtag);
        return compoundtag;
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        if(pkt.getTag() != null) {
            load(pkt.getTag());
        }
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

    @Override
    public void requestModelDataUpdate() {
        super.requestModelDataUpdate();
    }
}
