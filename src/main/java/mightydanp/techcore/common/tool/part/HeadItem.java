package mightydanp.techcore.common.tool.part;

import com.mojang.datafixers.util.Pair;;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.icons.ITextureIcon;
import mightydanp.techcore.common.jsonconfig.tool.type.IToolType;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MightyDanp on 3/29/2021.
 */
public class HeadItem extends Item {
    public Pair<String, String> prefixAndSuffix;
    public String suggestedCraftedTool;
    public Boolean disabled;
    public String material;
    public String element;
    public Integer color;
    public Pair<String, ITextureIcon> textureFlag;
    public Integer temperature;
    public Integer meltingPoint;
    public Integer boilingPoint;
    public Float efficiency;
    public Integer durability;
    public Map<IToolType, Integer> itToolType = new HashMap<>();
    public Float attackDamage;
    public Float weight;

    public HeadItem(Properties properties, Pair<String, String> prefixAndSuffix) {
        super(properties.stacksTo(1));
        this.prefixAndSuffix = prefixAndSuffix;
    }

    public HeadItem disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public HeadItem setSuggestedCraftedTool(String suggestedCraftedTool) {
        this.suggestedCraftedTool = suggestedCraftedTool;
        return this;
    }

    public HeadItem setMaterial(String material) {
        this.material = material;
        return this;
    }

    public HeadItem setElement(String element) {
        this.element = element;
        return this;
    }

    public HeadItem setColor(Integer color) {
        this.color = color;
        return this;
    }

    public HeadItem setTextureFlag(Pair<String, ITextureIcon> textureFlag) {
        this.textureFlag = textureFlag;
        return this;
    }

    public HeadItem setTemperature(Integer temperature) {
        this.temperature = temperature;
        return this;
    }

    public HeadItem setMeltingPoint(Integer meltingPoint) {
        this.meltingPoint = meltingPoint;
        return this;
    }

    public HeadItem setBoilingPoint(Integer boilingPoint) {
        this.boilingPoint = boilingPoint;
        return this;
    }

    public HeadItem setEfficiency(Float efficiency) {
        this.efficiency = efficiency;
        return this;
    }

    public HeadItem setDurability(Integer durability) {
        this.durability = durability;
        return this;
    }

    public HeadItem setItToolType(Map<String, Integer> itToolTypeIn) {
        itToolTypeIn.forEach(((name, toolLevel) -> itToolType.put(((IToolType) TCJsonConfigs.toolType.getFirst().registryMap.get(name)), toolLevel)));
        return this;
    }

    public HeadItem setAttackDamage(Float attackDamage) {
        this.attackDamage = attackDamage;
        return this;
    }

    public HeadItem setWeight(Float weight) {
        this.weight = weight;
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
        if(durability != null) {
            tooltip.add(Component.nullToEmpty("durability:" + (durability -stack.getDamageValue()) + "/" + durability));
        }
        if(efficiency != null) {
            tooltip.add(Component.nullToEmpty("efficiency:" + efficiency));
        }
        if (meltingPoint != null) {
            tooltip.add(Component.nullToEmpty("Melting Point of" + " §5" + meltingPoint));
        }
        if (boilingPoint != null) {
            tooltip.add(Component.nullToEmpty("Boiling Point of" + " §5" + boilingPoint));
        }

        if (meltingPoint != null && boilingPoint != null) {
            if (temperature.equals(boilingPoint)) {
                tooltip.add(Component.nullToEmpty("§5" + "Hot"));
            }
        }


        if(element != null) {
            tooltip.add(Component.nullToEmpty(element));
        }

        if(itToolType != null) {
            itToolType.forEach(((iToolType, integer) -> tooltip.add(
                    Component.nullToEmpty(iToolType.getName() + " level: " + integer))
            ));
        }
    }
}