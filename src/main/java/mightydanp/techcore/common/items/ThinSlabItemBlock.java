package mightydanp.techcore.common.items;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

/**
 * Created by MightyDanp on 7/3/2021.
 */
public class ThinSlabItemBlock extends BlockItem {
    public ThinSlabItemBlock(Block block, Properties properties) {
        super(block, properties);
        properties.tab(TCCreativeModeTab.item_tab);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, Level world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        super.inventoryTick(itemStack, world, entity, p_77663_4_, p_77663_5_);
        if (entity instanceof Player) {
            Player playerEntity = (Player)entity;

            if(!playerEntity.isCreative() && playerEntity.getInventory().contains(new ItemStack(this))){
                MobEffectInstance effect = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1, 1);
                effect.setNoCounter(true);
                playerEntity.addEffect(effect);
                playerEntity.setSprinting(false);
            }else{
                playerEntity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            }
        }
    }
}
