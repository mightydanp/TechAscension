package mightydanp.industrialcore.common.items;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if(element != null) {
            tooltip.add(Component.nullToEmpty(element));
        }
    }

    public void setElement(String elementIn) {
        element = elementIn;
    }
}
