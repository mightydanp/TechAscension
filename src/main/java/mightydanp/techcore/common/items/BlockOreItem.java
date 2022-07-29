package mightydanp.techcore.common.items;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraftforge.registries.RegistryObject;

/**
 * Created by MightyDanp on 9/26/2020.
 */
public class BlockOreItem extends BlockItem {
    public String element;
    public Integer meltingPoint;
    public Integer boilingPoint;

    public BlockOreItem(RegistryObject<Block> blockIn, Properties builder, Integer boilingPointIn, Integer meltingPointIn, String elementIn) {
        super(blockIn.get(), builder);
        meltingPoint = meltingPointIn;
        boilingPoint = boilingPointIn;
        element = elementIn;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (element != null) {
            tooltip.add(Component.nullToEmpty(element));
        }

        if (meltingPoint != null) {
            tooltip.add(Component.nullToEmpty("Melting Point of" + " §5" + meltingPoint));
        }
        if (boilingPoint != null) {
            tooltip.add(Component.nullToEmpty("Boiling Point of" + " §5" + boilingPoint));
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