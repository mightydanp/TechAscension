package mightydanp.industrialtech.api.common.items;

import com.google.common.collect.ImmutableSet;
import mightydanp.industrialtech.api.common.blocks.SmallOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.item.Item.Properties;

/**
 * Created by MightyDanp on 3/8/2021.
 */
public class HammerToolItem extends ToolItem {
    private static Set<Block> effective_on = new HashSet<>();

    public HammerToolItem(float attackDamageIn, float attackSpeedIn, IItemTier tier, Set<Block> effectiveBlocksIn, Properties builderIn) {
        super(attackDamageIn, attackSpeedIn, tier, effectiveBlocksIn, builderIn);
        effective_on = effectiveBlocksIn;
    }

    /**
     * Check whether this Item can harvest the given Block
     */
    public boolean isCorrectToolForDrops(BlockState blockIn) {
        boolean canHarvest = false;

        if(blockIn.getBlock() instanceof OreBlock || blockIn.getBlock() instanceof SmallOreBlock){
            canHarvest = true;
        }

        return canHarvest;
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return state.getBlock() instanceof OreBlock || state.getBlock() instanceof SmallOreBlock ? super.getDestroySpeed(stack, state) : this.speed;
    }
}
