package mightydanp.industrialtech.api.common.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by MightyDanp on 3/6/2021.
 */
public class RockBlockItem extends BlockItem {

    public RockBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    protected boolean placeBlock(BlockItemUseContext context, BlockState state) {
        return false;
    }
}