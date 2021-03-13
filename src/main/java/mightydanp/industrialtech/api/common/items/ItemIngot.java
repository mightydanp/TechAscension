package mightydanp.industrialtech.api.common.items;

import mightydanp.industrialtech.api.common.libs.EnumMaterialFlags;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by MightyDanp on 10/2/2020.
 */
public class ItemIngot extends Item {
    public String element;
    public int temperature;
    public int maxStackSize;
    public int meltingPoint;
    public int boilingPoint;

    public ItemIngot(Properties properties, int boilingPointIn, int meltingPointIn, String elementIn) {
        super(properties);
        meltingPoint = meltingPointIn;
        boilingPoint = boilingPointIn;
        element = elementIn;

        if(temperature == boilingPoint){
            maxStackSize = 1;
        }else{
            maxStackSize = 64;
        }
        properties.maxStackSize(maxStackSize);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(ITextComponent.getTextComponentOrEmpty(element));
        if (meltingPoint != 0) {
            tooltip.add(ITextComponent.getTextComponentOrEmpty("Melting Point of" + " §5" + meltingPoint));
        }
        if (boilingPoint != 0) {
            tooltip.add(ITextComponent.getTextComponentOrEmpty("Boiling Point of" + " §5" + boilingPoint));
        }

        if (temperature == boilingPoint) {
            tooltip.add(ITextComponent.getTextComponentOrEmpty("§5" + "Hot"));
        }

        if(element != null) {
            tooltip.add(ITextComponent.getTextComponentOrEmpty(element));
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
