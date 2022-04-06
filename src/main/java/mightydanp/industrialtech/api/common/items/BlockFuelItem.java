package mightydanp.industrialtech.api.common.items;

import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

/**
 * Created by MightyDanp on 7/31/2021.
 */
public class BlockFuelItem extends BlockItem {
    public int burnTime;

    public BlockFuelItem(Block blockIn, Properties propertiesIn, int burnTimeIn) {
        super(blockIn, propertiesIn);
        burnTime = burnTimeIn;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (burnTime != 0) {
            tooltip.add(Component.nullToEmpty("Burn Time:" + burnTime));
        }
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return super.getBurnTime(itemStack, recipeType);
    }
}
