package mightydanp.techcore.common.handler;

import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.trait.block.BlockTraitCodec;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class PickUpHandler {

    @SubscribeEvent
    public static void playerPickup(EntityItemPickupEvent event) {
        if (!event.getPlayer().getInventory().getItem(41).isEmpty()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onItemRightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        if (!event.getPlayer().getInventory().getItem(41).isEmpty()) {
            if (event.getPlayer().isShiftKeyDown() && event.getPlayer().getOffhandItem().isEmpty() && event.getPlayer().getMainHandItem().isEmpty()) {
                if (event.getPlayer().getInventory().getItem(41).getItem() instanceof BlockItem blockItem) {
                    event.getWorld().setBlock(event.getPos().relative(Objects.requireNonNull(event.getFace())), blockItem.getBlock().defaultBlockState(), 2);
                    event.getPlayer().getInventory().setItem(41, ItemStack.EMPTY);
                }
            }
            event.setCanceled(true);
        } else {
            if(event.getPlayer().isShiftKeyDown() && event.getPlayer().getOffhandItem().isEmpty() && event.getPlayer().getMainHandItem().isEmpty()){
                if(TCJsonConfigs.blockTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(event.getWorld().getBlockState(event.getPos()).getBlock().getRegistryName()).toString())){
                    if(((BlockTraitCodec)TCJsonConfigs.blockTrait.getFirst().registryMap.get(Objects.requireNonNull(event.getWorld().getBlockState(event.getPos()).getBlock().getRegistryName()).toString())).canPickUp()) {
                        event.getPlayer().getInventory().setItem(41, event.getWorld().getBlockState(event.getPos()).getBlock().asItem().getDefaultInstance());
                        event.getWorld().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 2);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onItemRightClickEntityEvent(EntityMountEvent event) {
        if(event.getEntity() instanceof Player player) {
            if (!player.getInventory().getItem(41).isEmpty()) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onItemRightClickEvent(PlayerInteractEvent.RightClickItem event) {
        if(event.getEntity() instanceof Player player) {
            if (!player.getInventory().getItem(41).isEmpty()) {
                event.setCanceled(true);
            }
        }
    }
}
