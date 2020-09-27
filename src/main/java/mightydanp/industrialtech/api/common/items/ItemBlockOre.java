package mightydanp.industrialtech.api.common.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class ItemBlockOre extends BlockItem {
    public String toolTip;

    public ItemBlockOre(Block blockIn, Properties builder, String elementIn) {
        super(blockIn, builder);
        toolTip = elementIn;
        this.setRegistryName(Objects.requireNonNull(blockIn.getRegistryName()));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(ITextComponent.func_244388_a(toolTip));
    }
}
