package mightydanp.industrialtech.api.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by MightyDanp on 3/29/2021.
 */
public class ToolHeadItem extends Item {
    public String element;
    public int temperature;
    public int meltingPoint;
    public int boilingPoint;
    public int speed;
    public int durability;

    public ToolHeadItem(Item.Properties properties, int boilingPointIn, int meltingPointIn, int speedIn, int durabilityIn, String elementIn) {
        super(properties);
        meltingPoint = meltingPointIn;
        boilingPoint = boilingPointIn;
        element = elementIn;
        speed = speedIn;
        durability = durabilityIn;

        properties.stacksTo(1);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(ITextComponent.nullToEmpty(element));
        if(speed != 0) {
            tooltip.add(ITextComponent.nullToEmpty("speed:" + speed));
        }
        if(durability != 0) {
            tooltip.add(ITextComponent.nullToEmpty("max durability:" + durability));
        }

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