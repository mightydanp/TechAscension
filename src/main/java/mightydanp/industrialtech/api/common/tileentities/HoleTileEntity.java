package mightydanp.industrialtech.api.common.tileentities;

import mightydanp.industrialtech.api.common.blocks.HoleBlock;
import mightydanp.industrialtech.api.common.crafting.recipe.HoleRecipe;
import mightydanp.industrialtech.api.common.crafting.recipe.Recipes;
import mightydanp.industrialtech.common.IndustrialTech;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

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

    public int minTicksForHoleToFill;
    public int maxTicksForHoleToFill;

    public int minResult;
    public int maxResult;

    public int holeColor;
    public int resinColor;

    public int ingredientItemDamage;

    public List<ItemStack> ingredientItems = new ArrayList<>();

    public int progress;
    public int finishedProgress;
    public Direction direction = Direction.NORTH;
    public boolean hasResin;

    public Random random = new Random();

    public HoleTileEntity() {
        super(TileEntities.hole_tile_entity.get());
    }

    public ItemStack getDesiredBlockSlot() {
        return inventory.get(desiredBlockSlot);
    }

    //public HoleBlock holeBlock = (HoleBlock)this.getBlockState().getBlock();

    public void setDesiredBlockSlot(ItemStack stackIn) {
        inventory.set(desiredBlockSlot, stackIn);
    }

    public ItemStack getOutputSlot() {
        return inventory.get(outputSlot);
    }

    public void setOutputSlot(ItemStack stackIn) {
        inventory.set(outputSlot, stackIn);
    }



    @Override
    public void tick() {
        HoleBlock holeBlock = (HoleBlock) this.getBlockState().getBlock();
        if (level != null){
            Optional<HoleRecipe> validRecipe = getValidRecipe(new ItemStack(getDesiredBlockSlot().getItem()));
                if (validRecipe.isPresent()){
                        if (ingredientItems.isEmpty() && validRecipe.get().getIngredients() != null) {
                            for (Ingredient ingredient : validRecipe.get().getIngredients()) {
                                ingredientItems.addAll(Arrays.asList(ingredient.getItems()));
                            }
                        }

                        if (progress >= minTicksForHoleToFill && !ingredientItems.isEmpty()) {
                            if (0 == (random.nextInt(maxTicksForHoleToFill - minTicksForHoleToFill)) + minTicksForHoleToFill || progress == maxTicksForHoleToFill) {
                                ItemStack output = validRecipe.get().getResultItem();
                                hasResin = true;
                                progress = 0;

                                if(getOutputSlot() == null) {
                                    setOutputSlot(new ItemStack(output.getItem(), random.nextInt(maxResult - minResult) + minResult));
                                }else{
                                    getOutputSlot().setCount(getOutputSlot().getCount() + 1);
                                }
                            }
                        }
                } else {
                    IndustrialTech.LOGGER.warn("hole at cords(" + getBlockPos() + ")does not have valid recipe for the hole. Removing hole");
                    level.setBlockAndUpdate(this.worldPosition, Blocks.AIR.defaultBlockState());
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

        direction = directionNew;
        hasResin = nbt.getBoolean("has_resin");

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
        return nbt;
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        this.saveMetadataAndItems(nbt);

        return super.save(nbt);
    }

    private CompoundNBT saveMetadataAndItems(CompoundNBT nbt) {
        super.save(nbt);

        nbt.putString("direction", direction.getName());
        nbt.putBoolean("has_resin", hasResin);
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
}
