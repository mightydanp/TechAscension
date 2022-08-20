package mightydanp.techcore.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.jsonconfig.icons.ITextureIcon;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ToolPart extends Item{
    public String material;
    public Integer durability;
    public String element;
    public Integer color;
    public Pair<String, ITextureIcon> textureFlag;
    public Integer temperature;
    public Integer meltingPoint;
    public Integer boilingPoint;

    public Float weight;

    public boolean isBroken = false;



    public ToolPart(Properties propertiesIn) {
        super(propertiesIn.stacksTo(1));
    }

    public ToolPart setMaterial(String material) {
        this.material = material;
        return this;
    }

    public ToolPart setElement(String element) {
        this.element = element;
        return this;
    }

    public ToolPart setColor(Integer color) {
        this.color = color;
        return this;
    }

    public ToolPart setTextureFlag(Pair<String, ITextureIcon> textureFlag) {
        this.textureFlag = textureFlag;
        return this;
    }

    public ToolPart setTemperature(Integer temperature) {
        this.temperature = temperature;
        return this;
    }

    public ToolPart setMeltingPoint(Integer meltingPoint) {
        this.meltingPoint = meltingPoint;
        return this;
    }

    public ToolPart setBoilingPoint(Integer boilingPoint) {
        this.boilingPoint = boilingPoint;
        return this;
    }

    public ToolPart setDurability(Integer durability) {
        this.durability = durability;
        return this;
    }

    public ToolPart setWeight(Float weight) {
        this.weight = weight;
        return this;
    }

    public ToolPart setBroken(boolean broken) {
        isBroken = broken;
        return this;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return durability;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);

        if(isBroken){
            tooltip.add(Component.nullToEmpty("§4" + "This part is broken!"));
        }

        if(material != null) {
            tooltip.add(Component.nullToEmpty("Material: " + material));
        }

        if(element != null) {
            tooltip.add(Component.nullToEmpty(element));
        }

        if (meltingPoint != null) {
            tooltip.add(Component.nullToEmpty("Melting Point of " + " §5" + meltingPoint));
        }
        if (boilingPoint != null) {
            tooltip.add(Component.nullToEmpty("Boiling Point of " + " §5" + boilingPoint));
        }

        if (meltingPoint != null && boilingPoint != null) {
            if (temperature == boilingPoint) {
                tooltip.add(Component.nullToEmpty("§5" + "Hot"));
            }
        }

        if(durability != null && stack.getDamageValue() == 0) {
            tooltip.add(Component.nullToEmpty("Durability: " + (durability - stack.getDamageValue()) + " / " + durability));
        }

    }
}
