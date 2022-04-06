package mightydanp.industrialtech.api.common.items;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.icons.ITextureIcon;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.item.Item.Properties;

/**
 * Created by MightyDanp on 4/9/2021.
 */
public class ToolBindingItem extends Item {
    public String material;
    public String element;
    public Integer temperature;
    public Integer meltingPoint;
    public Integer boilingPoint;
    public Integer durability;
    public Float weight;
    public Integer color;
    public Integer maxDamage;
    public Pair<String, ITextureIcon> textureFlag;

    public ToolBindingItem(Properties properties, String materialIn, String elementIn, Integer colorIn, Pair<String, ITextureIcon> textureFlagIn, Integer boilingPointIn, Integer meltingPointIn, Integer durabilityIn, Float weightIn) {
        super(properties);
        material = materialIn;
        color = colorIn;
        textureFlag = textureFlagIn;
        meltingPoint = meltingPointIn;
        boilingPoint = boilingPointIn;
        element = elementIn;
        durability = durabilityIn;
        weight = weightIn;
        maxDamage = durabilityIn;
        properties.stacksTo(1);
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return maxDamage;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.nullToEmpty(element));
        if(material != null) {
            tooltip.add(Component.nullToEmpty("material: " + material));
        }

        if(element != null) {
            tooltip.add(Component.nullToEmpty(element));
        }

        if(durability != null) {
            tooltip.add(Component.nullToEmpty("durability: " + (durability - stack.getDamageValue()) + "/" + durability));
        }

        if (meltingPoint != null) {
            tooltip.add(Component.nullToEmpty("Melting Point of " + " §5" + meltingPoint));
        }
        if (boilingPoint != null) {
            tooltip.add(Component.nullToEmpty("Boiling Point of " + " §5" + boilingPoint));
        }

        if (meltingPoint != null && boilingPoint != null) {
            if (temperature == boilingPoint) {
                tooltip.add(Component.nullToEmpty("5" + "Hot"));
            }
        }
        
    }
    public void setElement(String elementIn) {
        element = elementIn;
    }
}