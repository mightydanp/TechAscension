package mightydanp.industrialtech.api.common.items;

import mightydanp.industrialtech.api.common.libs.EnumMaterialFlags;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.item.Item.Properties;

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
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(ITextComponent.nullToEmpty(element));
        if (meltingPoint != 0) {
            tooltip.add(ITextComponent.nullToEmpty("Melting Point of" + " §5" + meltingPoint));
        }
        if (boilingPoint != 0) {
            tooltip.add(ITextComponent.nullToEmpty("Boiling Point of" + " §5" + boilingPoint));
        }

        if (temperature == boilingPoint) {
            tooltip.add(ITextComponent.nullToEmpty("§5" + "Hot"));
        }

        if(element != null) {
            tooltip.add(ITextComponent.nullToEmpty(element));
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
