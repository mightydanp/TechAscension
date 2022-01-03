package mightydanp.industrialtech.api.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by MightyDanp on 3/9/2021.
 */
public class GemItem extends Item {
    public String element;

    public GemItem(Item.Properties properties, String elementIn) {
        super(properties);
        element = elementIn;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if(element != null) {
            tooltip.add(ITextComponent.nullToEmpty(element));
        }
    }

    public void setElement(String elementIn) {
        element = elementIn;
    }
}
