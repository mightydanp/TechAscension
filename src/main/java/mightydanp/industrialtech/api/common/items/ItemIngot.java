package mightydanp.industrialtech.api.common.items;

import mightydanp.industrialtech.api.common.libs.EnumMaterialFlags;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by MightyDanp on 10/2/2020.
 */
public class ItemIngot extends Item {
    public String elementToolTip;
    public EnumMaterialFlags ingotType;
    public int maxStackSize;
    public int boilingPoint;
    public int meltingPoint;

    public ItemIngot(Properties properties, EnumMaterialFlags ingotStateIn) {
        super(properties);
        if(ingotStateIn == EnumMaterialFlags.HOTINGOT){
            maxStackSize = 1;
        }else{
            maxStackSize = 64;
        }
        properties.maxStackSize(maxStackSize);
        ingotType = ingotStateIn;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(ITextComponent.getTextComponentOrEmpty(elementToolTip));
        if(ingotType == EnumMaterialFlags.HOTINGOT){
            tooltip.add(ITextComponent.getTextComponentOrEmpty("§5" + ingotType.getSufixString().split("_")[1]));
        }else if(ingotType == EnumMaterialFlags.SOFTENEDINGOT){
            tooltip.add(ITextComponent.getTextComponentOrEmpty("§b" + ingotType.getSufixString().split("_")[1]));
        }else if(ingotType == EnumMaterialFlags.HARDENEDINGOT){
            tooltip.add(ITextComponent.getTextComponentOrEmpty("§8" + ingotType.getSufixString().split("_")[1]));
        }
        if(meltingPoint != 0){
            tooltip.add(ITextComponent.getTextComponentOrEmpty("Melting Point of" + ingotType.getSufixString().split("_")[1] + " §5" + meltingPoint));
        }
        if(boilingPoint != 0){
            tooltip.add(ITextComponent.getTextComponentOrEmpty("Boiling Point of" + ingotType.getSufixString().split("_")[1] + " §5" + boilingPoint));
        }
    }

    public int setBoilingPoint(int boilingPointIn){
        return boilingPoint = boilingPointIn;
    }

    public int setMeltingPoint(int meltingPointIn){
        return meltingPoint = meltingPointIn;
    }
}
