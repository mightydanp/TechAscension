package mightydanp.industrialtech.api.common.items;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.material.icons.DefaultTextureIcon;
import mightydanp.industrialtech.api.common.material.icons.ITextureIcon;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by MightyDanp on 4/9/2021.
 */
public class ToolHandleItem extends Item {
    public String material;
    public String element;
    public Integer color;
    public Integer temperature;
    public Integer meltingPoint;
    public Integer boilingPoint;
    public Integer durability;
    public Float weight;
    public Integer maxDamage;
    public Pair<String, ITextureIcon> textureFlag;

    public ToolHandleItem(Properties properties, String materialIn, String elementIn, Integer colorIn, Pair<String, ITextureIcon> textureFlagIn, Integer boilingPointIn, Integer meltingPointIn, Integer durabilityIn, Float weightIn) {
        super(properties);
        material = materialIn;
        color = colorIn;
        textureFlag = textureFlagIn;
        meltingPoint = meltingPointIn;
        boilingPoint = boilingPointIn;
        element = elementIn;
        durability = durabilityIn;
        weight = weightIn;
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
        if(material != null) {
            tooltip.add(ITextComponent.nullToEmpty("material: " + material));
        }

        if(element != null) {
            tooltip.add(ITextComponent.nullToEmpty(element));
        }

        if(durability != null) {
            tooltip.add(ITextComponent.nullToEmpty("durability: " + (durability -stack.getDamageValue()) + "/" + durability));
        }

        if (meltingPoint != null) {
            tooltip.add(ITextComponent.nullToEmpty("Melting Point of " + " §5" + meltingPoint));
        }
        if (boilingPoint != null) {
            tooltip.add(ITextComponent.nullToEmpty("Boiling Point of " + " §5" + boilingPoint));
        }

        if (meltingPoint != null && boilingPoint != null) {
            if (temperature.equals(boilingPoint)) {
                tooltip.add(ITextComponent.nullToEmpty("§5" + "Hot"));
            }
        }
        
    }
    public void setElement(String elementIn) {
        element = elementIn;
    }

    public void setBoilingPoint(Integer boilingPointIn){
        this.boilingPoint = boilingPointIn;
    }

    public void setMeltingPoint (Integer meltingPointIn){
        meltingPoint = meltingPointIn;
    }

    public Integer getTemperature(){
        return temperature;
    }
}
