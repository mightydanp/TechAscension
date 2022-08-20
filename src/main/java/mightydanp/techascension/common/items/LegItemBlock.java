package mightydanp.techascension.common.items;

import mightydanp.techcore.common.items.TCCreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Created by MightyDanp on 7/3/2021.
 */
public class LegItemBlock extends BlockItem {
    public LegItemBlock(Block block, Properties properties) {
        super(block, properties);
        properties.tab(TCCreativeModeTab.item_tab);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        super.inventoryTick(itemStack, world, entity, p_77663_4_, p_77663_5_);
        if (entity instanceof Player) {
            Player playerEntity = (Player)entity;

            if(!playerEntity.isCreative() && playerEntity.getInventory().contains(new ItemStack(this))){
                playerEntity.getSpeed();

                playerEntity.setSpeed(0.1F);
            }else{
                playerEntity.setSpeed(1F);
            }
        }
    }
}
