package mightydanp.techcore.common.items;

import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by MightyDanp on 10/2/2020.
 */
public class IngotItem extends Item {
    public String element;
    public int temperature;
    public int maxStackSize;
    public int meltingPoint;
    public int boilingPoint;

    public IngotItem(Properties properties, int boilingPointIn, int meltingPointIn, String elementIn) {
        super(properties);
        meltingPoint = meltingPointIn;
        boilingPoint = boilingPointIn;
        element = elementIn;

        if(temperature == boilingPoint){
            maxStackSize = 1;
        }else{
            maxStackSize = 64;
        }
        properties.stacksTo(maxStackSize);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.nullToEmpty(element));
        if (meltingPoint != 0) {
            tooltip.add(Component.nullToEmpty("Melting Point of" + " §5" + meltingPoint));
        }
        if (boilingPoint != 0) {
            tooltip.add(Component.nullToEmpty("Boiling Point of" + " §5" + boilingPoint));
        }

        if (temperature == boilingPoint) {
            tooltip.add(Component.nullToEmpty("§5" + "Hot"));
        }

        if(element != null) {
            tooltip.add(Component.nullToEmpty(element));
        }
    }
    public void setElement(String elementIn) {
        element = elementIn;
    }

    public void setBoilingPoint(int boilingPointIn){
        this.boilingPoint = boilingPointIn;
    }

    public void setMeltingPoint (int meltingPointIn){
        meltingPoint = meltingPointIn;
    }

    public int getTemperature(){
        return temperature;
    }
}
