package mightydanp.industrialtech.api.common.items;

import com.mojang.datafixers.util.Pair;;
import mightydanp.industrialtech.api.common.material.icons.DefaultTextureIcon;
import mightydanp.industrialtech.api.common.material.icons.ITextureIcon;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by MightyDanp on 3/29/2021.
 */
public class ToolHeadItem extends Item {
    public String material;
    public String element;
    public Integer color;
    public Pair<String, ITextureIcon> textureFlag;
    public Integer temperature;
    public Integer meltingPoint;
    public Integer boilingPoint;
    public Integer efficiency;
    public Integer durability;
    public List<Pair<ToolType,Integer>> itToolType;
    public Float attackDamage;
    public Float weight;
    public Integer maxDamage;

    public ToolHeadItem(Properties properties, String materialIn, String elementIn, Integer colorIn, Pair<String, ITextureIcon> textureFlagIn, Integer boilingPointIn, Integer meltingPointIn, Integer efficiencyIn, Integer durabilityIn, Float attackDamageIn, Float weightIn, List<Pair<ToolType, Integer>> itToolTypeIn) {
        super(properties);
        material = materialIn;
        color = colorIn;
        textureFlag = textureFlagIn;
        meltingPoint = meltingPointIn;
        boilingPoint = boilingPointIn;
        element = elementIn;
        efficiency = efficiencyIn;
        durability = durabilityIn;
        attackDamage = attackDamageIn;
        weight = weightIn;
        itToolType = itToolTypeIn;
        properties.stacksTo(1);
        maxDamage = durabilityIn;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return maxDamage;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(ITextComponent.nullToEmpty(element));
        if(durability != null) {
            tooltip.add(ITextComponent.nullToEmpty("durability:" + (durability -stack.getDamageValue()) + "/" + durability));
        }
        if(efficiency != null) {
            tooltip.add(ITextComponent.nullToEmpty("efficiency:" + efficiency));
        }
        if (meltingPoint != null) {
            tooltip.add(ITextComponent.nullToEmpty("Melting Point of" + " §5" + meltingPoint));
        }
        if (boilingPoint != null) {
            tooltip.add(ITextComponent.nullToEmpty("Boiling Point of" + " §5" + boilingPoint));
        }

        if (meltingPoint != null && boilingPoint != null) {
            if (temperature.equals(boilingPoint)) {
                tooltip.add(ITextComponent.nullToEmpty("§5" + "Hot"));
            }
        }


        if(element != null) {
            tooltip.add(ITextComponent.nullToEmpty(element));
        }

        if(itToolType != null) {
            for(Pair<ToolType,Integer> toolType : itToolType) {
                tooltip.add(ITextComponent.nullToEmpty(toolType.getFirst().getName() + " level: " + toolType.getSecond()));
            }
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

    public List<Pair<ToolType, Integer>> getItToolType() {
        return itToolType;
    }
}