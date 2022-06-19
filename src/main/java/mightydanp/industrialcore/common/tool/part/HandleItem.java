package mightydanp.industrialcore.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.jsonconfig.icons.ITextureIcon;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by MightyDanp on 4/9/2021.
 */
public class HandleItem extends Item {
    public Pair<String, String> prefixAndSuffix;
    public Boolean disabled;
    public String material;
    public String element;
    public Integer color;
    public Integer temperature;
    public Integer meltingPoint;
    public Integer boilingPoint;
    public Integer durability;
    public Float weight;
    public Pair<String, ITextureIcon> textureFlag;

    public HandleItem(Properties propertiesIn, Pair<String, String> prefixAndSuffix) {
        super(propertiesIn.stacksTo(1));
        this.prefixAndSuffix = prefixAndSuffix;
    }

    public HandleItem disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }


    public HandleItem setMaterial(String material) {
        this.material = material;
        return this;
    }

    public HandleItem setElement(String element) {
        this.element = element;
        return this;
    }

    public HandleItem setColor(Integer color) {
        this.color = color;
        return this;
    }

    public HandleItem setTemperature(Integer temperature) {
        this.temperature = temperature;
        return this;
    }

    public HandleItem setMeltingPoint(Integer meltingPoint) {
        this.meltingPoint = meltingPoint;
        return this;
    }

    public HandleItem setBoilingPoint(Integer boilingPoint) {
        this.boilingPoint = boilingPoint;
        return this;
    }

    public HandleItem setDurability(Integer durability) {
        this.durability = durability;
        return this;
    }

    public HandleItem setWeight(Float weight) {
        this.weight = weight;
        return this;
    }

    public HandleItem setTextureFlag(Pair<String, ITextureIcon> textureFlag) {
        this.textureFlag = textureFlag;
        return this;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return durability;
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
            tooltip.add(Component.nullToEmpty("durability: " + (durability -stack.getDamageValue()) + "/" + durability));
        }

        if (meltingPoint != null) {
            tooltip.add(Component.nullToEmpty("Melting Point of " + " §5" + meltingPoint));
        }
        if (boilingPoint != null) {
            tooltip.add(Component.nullToEmpty("Boiling Point of " + " §5" + boilingPoint));
        }

        if (meltingPoint != null && boilingPoint != null) {
            if (temperature.equals(boilingPoint)) {
                tooltip.add(Component.nullToEmpty("§5" + "Hot"));
            }
        }
        
    }
}
