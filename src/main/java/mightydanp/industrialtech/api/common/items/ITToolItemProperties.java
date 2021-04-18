package mightydanp.industrialtech.api.common.items;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;;
import mightydanp.industrialtech.api.common.libs.ITToolType;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.common.ToolType;

import java.util.List;

/**
 * Created by MightyDanp on 4/14/2021.
 */
public class ITToolItemProperties extends Item.Properties{
    private int maxStackSize = 64;
    private int maxDamage;
    private Item craftingRemainingItem;
    private ItemGroup category;
    private Rarity rarity = Rarity.COMMON;
    private Food foodProperties;
    private boolean isFireResistant;
    private boolean canRepair = true;
    private java.util.Map<net.minecraftforge.common.ToolType, Integer> toolClasses = Maps.newHashMap();
    private java.util.function.Supplier<java.util.concurrent.Callable<net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer>> ister;

    @Override
    public Item.Properties food(Food foodIn) {
        foodProperties = foodIn;
        return this;
    }

    @Override
    public Item.Properties stacksTo(int stackSizeIn) {
        if (maxDamage > 0) {
            throw new RuntimeException("Unable to have damage AND stack.");
        } else {
            maxStackSize = stackSizeIn;
            return this;
        }
    }

    @Override
    public Item.Properties defaultDurability(int durabilityIn) {
        return maxDamage == 0 ? durability(durabilityIn) : this;
    }

    @Override
    public Item.Properties durability(int durabilityIn) {
        this.maxDamage = durabilityIn;
        this.maxStackSize = 1;
        return this;
    }

    @Override
    public Item.Properties craftRemainder(Item itemIn) {
        craftingRemainingItem = itemIn;
        return this;
    }

    @Override
    public Item.Properties tab(ItemGroup itemGroupIn) {
        category = itemGroupIn;
        return this;
    }

    @Override
    public Item.Properties rarity(Rarity rarityIn) {
        rarity = rarityIn;
        return this;
    }

    @Override
    public Item.Properties fireResistant() {
        isFireResistant = true;
        return this;
    }

    @Override
    public Item.Properties setNoRepair() {
        canRepair = false;
        return this;
    }

    @Override
    public Item.Properties addToolType(net.minecraftforge.common.ToolType type, int level) {
        toolClasses.put(type, level);
        return this;
    }

    public Item.Properties addToolTypes(boolean shouldClearToolTypeIn, Pair<ITToolType, Integer>... toolTypesIn){
        if(shouldClearToolTypeIn){
            clearToolTypes();
        }

        for(Pair<ITToolType, Integer> toolType: toolTypesIn){
            toolClasses.put(ToolType.get(toolType.toString()), toolType.getSecond());
        }
        return this;
    }

    public Item.Properties clearToolTypes(){
        toolClasses.clear();
        return this;
    }

    @Override
    public Item.Properties setISTER(java.util.function.Supplier<java.util.concurrent.Callable<net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer>> isterIn) {
        ister = isterIn;
        return this;
    }
}
