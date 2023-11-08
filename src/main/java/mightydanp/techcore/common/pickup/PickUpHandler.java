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
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

        if (!event.getPlayer().getInventory().getItem(41).isEmpty()) {
            if (event.getPlayer().isShiftKeyDown() && event.getPlayer().getOffhandItem().isEmpty() && event.getPlayer().getMainHandItem().isEmpty()) {
                if (event.getPlayer().getInventory().getItem(41).getItem() instanceof BlockItem blockItem) {
                    if (Objects.requireNonNull(blockItem.getRegistryName()).toString().equals(new ResourceLocation("chest").toString())){
                        event.getWorld().setBlock(event.getPos().relative(Objects.requireNonNull(event.getFace())), blockItem.getBlock().defaultBlockState().setValue(ChestBlock.FACING, Objects.requireNonNull(Direction.byName(event.getPlayer().getDirection().getOpposite().getName()))), 2);

                        if (event.getWorld().getBlockEntity(event.getPos()) instanceof ChestBlockEntity chestBlockEntity){
                            chestBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                                CompoundTag tag = event.getPlayer().getInventory().getItem(41).getTag();
                                if(tag != null) {
                                    List<ItemStack> chestInventory = new ArrayList<>();

                                    UndefinedContainerHelper.loadAllItems(tag, chestInventory);

                                    for(int i = 0; i > chestInventory.size(); i++){
                                        iItemHandler.insertItem(i, chestInventory.get(i), false);
                                    }

                                    event.getPlayer().getInventory().setItem(41, ItemStack.EMPTY);
                                    return;
                                }
                            });
                        }




                    }

                    event.getWorld().setBlock(event.getPos().relative(Objects.requireNonNull(event.getFace())), blockItem.getBlock().defaultBlockState(), 2);
                    event.getPlayer().getInventory().setItem(41, ItemStack.EMPTY);
                }
            }
            event.setCanceled(true);
        } else {
            if(event.getPlayer().isShiftKeyDown() && event.getPlayer().getOffhandItem().isEmpty() && event.getPlayer().getMainHandItem().isEmpty()){
                if(TCJsonConfigs.blockTrait.getFirst().registryMap.containsKey(Objects.requireNonNull(blockState.getBlock().getRegistryName()).toString())){
                    String registryName = Objects.requireNonNull(blockState.getBlock().getRegistryName()).toString();
                    if(((BlockTraitCodec)TCJsonConfigs.blockTrait.getFirst().registryMap.get(registryName)).canPickUp()) {
                        if(event.getWorld().getBlockEntity(event.getPos()) != null) {
                            ItemStack chestItemStack = new ItemStack(blockState.getBlock());
                            CompoundTag tag = chestItemStack.getTag();

                            if (registryName.equals(DefaultBlockTrait.chest.getCodec().registry()) && event.getWorld().getBlockEntity(event.getPos()) instanceof ChestBlockEntity chestBlockEntity) {
                                chestBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(iItemHandler -> {
                                    List<ItemStack> chestInventory = new ArrayList<>();

                                    for(int i = 0; i > iItemHandler.getSlots(); i++){
                                        chestInventory.add(iItemHandler.getStackInSlot(i));
                                    }

                                    UndefinedContainerHelper.saveAllItems(tag, chestInventory, true);

                                    chestItemStack.setTag(tag);

                                    event.getPlayer().getInventory().setItem(41, chestItemStack);
                                    event.getWorld().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 2);
                                });

                                return;
                            }
                        }

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
