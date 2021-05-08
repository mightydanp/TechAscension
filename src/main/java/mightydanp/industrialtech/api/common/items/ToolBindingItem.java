package mightydanp.industrialtech.api.common.items;

import mightydanp.industrialtech.api.common.libs.EnumMaterialTextureFlags;
import mightydanp.industrialtech.api.common.libs.ITToolType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MightyDanp on 4/9/2021.
 */
public class ToolBindingItem extends Item {
    public String material;
    public String element;
    public int temperature;
    public int meltingPoint;
    public int boilingPoint;
    public int durability;
    public ITToolType[] itToolType;
    public float weight;
    public int color;
    public int maxDamage;
    public EnumMaterialTextureFlags textureFlag;

    public ToolBindingItem(Item.Properties properties, String materialIn, String elementIn, int colorIn, EnumMaterialTextureFlags textureFlagIn, int boilingPointIn, int meltingPointIn, int durabilityIn, float weightIn) {
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
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(ITextComponent.nullToEmpty(element));
        if(material != null) {
            tooltip.add(ITextComponent.nullToEmpty("material: " + material));
        }

        if(element != null) {
            tooltip.add(ITextComponent.nullToEmpty(element));
        }

        if(durability != 0) {
            tooltip.add(ITextComponent.nullToEmpty("durability: " + (durability - stack.getDamageValue()) + "/" + durability));
        }

        if (meltingPoint != 0) {
            tooltip.add(ITextComponent.nullToEmpty("Melting Point of " + " §5" + meltingPoint));
        }
        if (boilingPoint != 0) {
            tooltip.add(ITextComponent.nullToEmpty("Boiling Point of " + " §5" + boilingPoint));
        }

        if (temperature == boilingPoint) {
            tooltip.add(ITextComponent.nullToEmpty("5" + "Hot"));
        }
    }
    public void setElement(String elementIn) {
        element = elementIn;
    }
}