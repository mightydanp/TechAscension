package mightydanp.industrialtech.api.common.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

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
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (burnTime != 0) {
            tooltip.add(ITextComponent.nullToEmpty("Burn Time:" + burnTime));
        }
    }

    @Override
    public int getBurnTime(ItemStack itemStack) {
        return super.getBurnTime(itemStack);
    }
}
