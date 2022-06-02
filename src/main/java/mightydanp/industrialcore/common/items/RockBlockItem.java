package mightydanp.industrialcore.common.items;

import mightydanp.industrialcore.common.blocks.OreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by MightyDanp on 3/6/2021.
 */
public class RockBlockItem extends BlockItem {
    public String stoneLayerBlock;
    public BlockPos pickUpLocation;
    public Block oreUnderneath;
    public String rgbOreUnderneath;
    public boolean hasBeenIdentified;
    public Integer meltingPoint;
    public Integer boilingPoint;

    public RockBlockItem(Block blockIn, String stoneLayerBlockIn, Properties builder) {
        super(blockIn, builder);
        stoneLayerBlock = stoneLayerBlockIn;
    }

    public void setPickUp(BlockPos pickUpLocationIn, Block oreUnderneathIn, String rgbOreUnderneathIn){
        pickUpLocation = pickUpLocationIn;
        oreUnderneath = oreUnderneathIn;
        rgbOreUnderneath = rgbOreUnderneathIn;
    }

    public void setHasBeenIdentified(boolean hasBeenIdentified) {
        this.hasBeenIdentified = hasBeenIdentified;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        if (pickUpLocation != null) {
            tooltip.add(Component.nullToEmpty(pickUpLocation.toString()));
        }


        if (hasBeenIdentified) {
            if (oreUnderneath != null) {
                if(oreUnderneath instanceof OreBlock) {
                    OreBlock ore = (OreBlock)oreUnderneath;
                    tooltip.add(Component.nullToEmpty("This rock contains " + " §5" + ore.name + " §5" + "inside of it"  + " §5"));
                }
            }

            if (meltingPoint != null) {
                tooltip.add(Component.nullToEmpty("Melting Point of" + " §5" + meltingPoint));
            }

            if (boilingPoint != null) {
                tooltip.add(Component.nullToEmpty("Boiling Point of" + " §5" + boilingPoint));
            }
        }
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        return false;
    }
}