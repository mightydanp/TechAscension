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
 * Created by MightyDanp on 4/9/2021.
 */
public class BindingItem extends Item {
    public Pair<String, String> prefixAndSuffix;
    public Boolean disabled;
    public String material;
    public String element;
    public Integer temperature;
    public Integer meltingPoint;
    public Integer boilingPoint;
    public Integer durability;
    public Float weight;
    public Integer color;
    public Pair<String, ITextureIcon> textureFlag;

    public BindingItem(Properties properties, Pair<String, String> prefixAndSuffix) {
        super(properties.stacksTo(1));
        this.prefixAndSuffix = prefixAndSuffix;
    }

    public BindingItem disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public BindingItem setMaterial(String material) {
        this.material = material;
        return this;
    }

    public BindingItem setElement(String element) {
        this.element = element;
        return this;
    }

    public BindingItem setTemperature(Integer temperature) {
        this.temperature = temperature;
        return this;
    }

    public BindingItem setMeltingPoint(Integer meltingPoint) {
        this.meltingPoint = meltingPoint;
        return this;
    }

    public BindingItem setBoilingPoint(Integer boilingPoint) {
        this.boilingPoint = boilingPoint;
        return this;
    }

    public BindingItem setDurability(Integer durability) {
        this.durability = durability;
        return this;
    }

    public BindingItem setWeight(Float weight) {
        this.weight = weight;
        return this;
    }

    public BindingItem setColor(Integer color) {
        this.color = color;
        return this;
    }

    public BindingItem setTextureFlag(Pair<String, ITextureIcon> textureFlag) {
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
}