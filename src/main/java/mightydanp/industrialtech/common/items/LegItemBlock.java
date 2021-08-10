package mightydanp.industrialtech.common.items;

import mightydanp.industrialtech.api.common.items.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by MightyDanp on 7/3/2021.
 */
public class LegItemBlock extends BlockItem {
    public LegItemBlock(Block block, Properties properties) {
        super(block, properties);
        properties.tab(ModItemGroups.item_tab);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        super.inventoryTick(itemStack, world, entity, p_77663_4_, p_77663_5_);
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)entity;

            if(!playerEntity.isCreative() && playerEntity.inventory.contains(new ItemStack(this))){
                playerEntity.getSpeed();

                playerEntity.setSpeed(0.1F);
            }else{
                playerEntity.setSpeed(1F);
            }
        }
    }
}
