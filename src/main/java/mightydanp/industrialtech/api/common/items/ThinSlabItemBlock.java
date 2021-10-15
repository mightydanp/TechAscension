package mightydanp.industrialtech.api.common.items;

import mightydanp.industrialtech.api.common.items.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by MightyDanp on 7/3/2021.
 */
public class ThinSlabItemBlock extends BlockItem {
    public ThinSlabItemBlock(Block block, Properties properties) {
        super(block, properties);
        properties.tab(ModItemGroups.item_tab);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
        super.inventoryTick(itemStack, world, entity, p_77663_4_, p_77663_5_);
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)entity;

            if(!playerEntity.isCreative() && playerEntity.inventory.contains(new ItemStack(this))){
                EffectInstance effect = new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 1, 1);
                effect.setNoCounter(true);
                playerEntity.addEffect(effect);
                playerEntity.setSprinting(false);
            }else{
                playerEntity.removeEffect(Effects.MOVEMENT_SLOWDOWN);
            }
        }
    }
}
