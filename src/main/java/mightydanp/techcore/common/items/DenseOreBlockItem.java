package mightydanp.techcore.common.items;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by MightyDanp on 3/15/2021.
 */
public class DenseOreBlockItem extends BlockItem {
    public String element;
    public Integer meltingPoint;
    public Integer boilingPoint;

    public Integer density;

    public DenseOreBlockItem(RegistryObject<Block> blockIn, Properties builder, Integer boilingPointIn, Integer meltingPointIn, String elementIn, Integer densityIn) {
        super(blockIn.get(), builder);
        meltingPoint = meltingPointIn;
        boilingPoint = boilingPointIn;
        element = elementIn;
        density = densityIn;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (element != null) {
            tooltip.add(Component.nullToEmpty(element));
        }

        if (meltingPoint != null && meltingPoint != 0) {
            tooltip.add(Component.nullToEmpty("Melting Point of" + " §5" + meltingPoint));
        }

        if (boilingPoint != null && boilingPoint != 0) {
            tooltip.add(Component.nullToEmpty("Boiling Point of" + " §5" + boilingPoint));
        }

        if (density != null && density != 0) {
            tooltip.add(Component.nullToEmpty("Density of " + density));
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