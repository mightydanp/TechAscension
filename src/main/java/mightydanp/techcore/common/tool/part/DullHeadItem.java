package mightydanp.techcore.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.jsonconfig.icons.ITextureIcon;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * Created by MightyDanp on 3/28/2021.
 */
public class DullHeadItem extends Item {
    public Pair<String, String> prefixAndSuffix;
    public Boolean disabled;
    public String material;
    public String element;
    public Integer color;
    public Pair<String, ITextureIcon> textureFlag;
    public Integer temperature;
    public Integer meltingPoint;
    public Integer boilingPoint;

    public DullHeadItem(Properties properties, Pair<String, String> prefixAndSuffix) {
        super(properties.stacksTo(1));
        this.prefixAndSuffix = prefixAndSuffix;
    }

    public DullHeadItem disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public DullHeadItem setMaterial(String material) {
        this.material = material;
        return this;
    }

    public DullHeadItem setElement(String element) {
        this.element = element;
        return this;
    }

    public DullHeadItem setColor(Integer color) {
        this.color = color;
        return this;
    }

    public DullHeadItem setTextureFlag(Pair<String, ITextureIcon> textureFlag) {
        this.textureFlag = textureFlag;
        return this;
    }

    public DullHeadItem setTemperature(Integer temperature) {
        this.temperature = temperature;
        return this;
    }

    public DullHeadItem setMeltingPoint(Integer meltingPoint) {
        this.meltingPoint = meltingPoint;
        return this;
    }

    public DullHeadItem setBoilingPoint(Integer boilingPoint) {
        this.boilingPoint = boilingPoint;
        return this;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.nullToEmpty("this is to dull to use."));

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
    }
}
