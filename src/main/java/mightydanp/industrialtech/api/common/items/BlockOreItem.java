package mightydanp.industrialtech.api.common.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class BlockOreItem extends BlockItem {
    public String element;
    public int meltingPoint;
    public int boilingPoint;

    public BlockOreItem(Block blockIn, Properties builder, int boilingPointIn, int meltingPointIn, String elementIn) {
        super(blockIn, builder);
        meltingPoint = meltingPointIn;
        boilingPoint = boilingPointIn;
        element = elementIn;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (element != null) {
            tooltip.add(ITextComponent.getTextComponentOrEmpty(element));
        }

        if (meltingPoint != 0) {
            tooltip.add(ITextComponent.getTextComponentOrEmpty("Melting Point of" + " §5" + meltingPoint));
        }
        if (boilingPoint != 0) {
            tooltip.add(ITextComponent.getTextComponentOrEmpty("Boiling Point of" + " §5" + boilingPoint));
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
}