package mightydanp.techcore.common.pickup;

import mightydanp.techapi.common.world.UndefinedContainerHelper;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.trait.block.BlockTraitCodec;
import mightydanp.techcore.common.jsonconfig.trait.block.DefaultBlockTrait;
import mightydanp.techcore.common.libs.Ref;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class PickUpHandler {
    public final NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);
    @SubscribeEvent
    public static void playerPickup(EntityItemPickupEvent event) {
        if (!event.getPlayer().getInventory().getItem(41).isEmpty()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onItemRightClickBlockEvent(PlayerInteractEvent.RightClickBlock event) {
        BlockState blockState = event.getWorld().getBlockState(event.getPos());

        if (!event.getPlayer().isShiftKeyDown() || !event.getPlayer().getOffhandItem().isEmpty() || !event.getPlayer().getMainHandItem().isEmpty()){
            return;
        }

        if (!event.getPlayer().getInventory().getItem(41).isEmpty()) {
            if (event.getPlayer().getInventory().getItem(41).getItem() instanceof BlockItem blockItem) {
                if (Objects.requireNonNull(blockItem.getRegistryName()).toString().equals(new ResourceLocation("chest").toString())){
                    event.getWorld().setBlock(event.getPos().relative(Objects.requireNonNull(event.getFace())), blockItem.getBlock().defaultBlockState().setValue(ChestBlock.FACING, Objects.requireNonNull(Direction.byName(event.getPlayer().getDirection().getOpposite().getName()))), 2);

                    if (event.getWorld().getBlockEntity(event.getPos().relative(Objects.requireNonNull(event.getFace()))) instanceof ChestBlockEntity chestBlockEntity){
                        CompoundTag tag = event.getPlayer().getInventory().getItem(41).getOrCreateTag();
                        List<ItemStack> chestInventory = new ArrayList<>();

                        UndefinedContainerHelper.loadAllItems(tag, chestInventory);

                        if(!event.getWorld().isClientSide()) {
                            chestBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                                for (int i = 0; i < chestInventory.size(); i++) {
                                    iItemHandler.insertItem(i, chestInventory.get(i), false);
                                }
                            });
                        }
                        event.getPlayer().getInventory().setItem(41, ItemStack.EMPTY);

                        return;
                    }
                }

                event.getWorld().setBlock(event.getPos().relative(Objects.requireNonNull(event.getFace())), blockItem.getBlock().defaultBlockState(), 2);
                event.getPlayer().getInventory().setItem(41, ItemStack.EMPTY);

                event.setCanceled(true);
            }

        } else {
            if(TCJsonConfigs.blockTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(blockState.getBlock().getRegistryName()).toString())){
                String registryName = Objects.requireNonNull(blockState.getBlock().getRegistryName()).toString();
                if(((BlockTraitCodec)TCJsonConfigs.blockTrait.getFirst().registryMap.get(registryName)).canPickUp()) {
                    if(event.getWorld().getBlockEntity(event.getPos()) != null) {
                        ItemStack chestItemStack = new ItemStack(blockState.getBlock());
                        AtomicReference<CompoundTag> tag = new AtomicReference<>(chestItemStack.getOrCreateTag());

                        if (registryName.equals(DefaultBlockTrait.chest.getCodec().registry()) && event.getWorld().getBlockEntity(event.getPos()) instanceof Container){
                            if  (event.getWorld().getBlockEntity(event.getPos()) instanceof ChestBlockEntity chestBlockEntity && blockState.getBlock() instanceof ChestBlock) {
                                List<ItemStack> chestInventory = new ArrayList<>();

                                if(!event.getWorld().isClientSide()) {
                                    chestBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                                        for (int i = 0; i < iItemHandler.getSlots(); i++) {
                                            chestInventory.add(iItemHandler.getStackInSlot(i));
                                        }
                                    });
                                }

                                if(chestInventory.size() > 0) {
                                    UndefinedContainerHelper.saveAllItems(tag.get(), chestInventory, true);
                                    chestItemStack.setTag(tag.get());

                                    event.getPlayer().getInventory().setItem(41, chestItemStack);

                                    event.getWorld().removeBlockEntity(event.getPos());
                                    event.getWorld().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 2);
                                }

                                return;
                            }
                        }
                    }

                    event.getPlayer().getInventory().setItem(41, event.getWorld().getBlockState(event.getPos()).getBlock().asItem().getDefaultInstance());
                    event.getWorld().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 2);
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
