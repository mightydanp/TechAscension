package mightydanp.industrialcore.common.items;

import com.mojang.datafixers.util.Pair;;
import mightydanp.industrialcore.common.jsonconfig.icons.ITextureIcon;
import mightydanp.industrialcore.common.jsonconfig.icons.ITextureIcon;
import mightydanp.industrialcore.common.jsonconfig.tool.type.IToolType;
import mightydanp.industrialtech.common.IndustrialTech;
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
public class ToolHeadItem extends Item {
    public String suggestedCraftedTool;
    public String material;
    public String element;
    public Integer color;
    public Pair<String, ITextureIcon> textureFlag;
    public Integer temperature;
    public Integer meltingPoint;
    public Integer boilingPoint;
    public Integer efficiency;
    public Integer durability;
    public Map<IToolType, Integer> itToolType = new HashMap<>();
    public Float attackDamage;
    public Float weight;
    public Integer maxDamage;

    public ToolHeadItem(Properties properties, String suggestedCraftedToolIn, String materialIn, String elementIn, Integer colorIn, Pair<String, ITextureIcon> textureFlagIn, Integer boilingPointIn, Integer meltingPointIn, Integer efficiencyIn, Integer durabilityIn, Float attackDamageIn, Float weightIn, Map<String, Integer> itToolTypeIn) {
        super(properties);
        suggestedCraftedTool = suggestedCraftedToolIn;
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

        itToolTypeIn.forEach(((name, toolLevel) -> itToolType.put((IToolType)IndustrialTech.configSync.toolType.getFirst().registryMap.get(name), toolLevel)));

        properties.stacksTo(1);
        maxDamage = durabilityIn;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return maxDamage;
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

    public Map<IToolType, Integer> getItToolType() {
        return itToolType;
    }
}