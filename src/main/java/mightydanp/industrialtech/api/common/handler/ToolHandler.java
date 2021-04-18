package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.items.*;
import mightydanp.industrialtech.api.common.items.handler.ITToolItemItemStackHandler;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by MightyDanp on 3/8/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class ToolHandler {
    public static List<RegistryObject<Item>> tools = new ArrayList<>();

    public static RegistryObject<Item> pickaxeItem, hammerItem, fileItem;
    public static ITToolItem pickaxe, hammer, file;


    public ToolHandler() {

        tools.add(pickaxeItem = RegistryHandler.ITEMS.register("pickaxe", () -> pickaxe = new PickaxeToolItem(new Item.Properties().tab(ModItemGroups.tool_tab), 3)));
        tools.add(hammerItem = RegistryHandler.ITEMS.register("hammer", () -> hammer = new HammerToolItem(new Item.Properties().tab(ModItemGroups.tool_tab), 3)));
        tools.add(fileItem = RegistryHandler.ITEMS.register("file", () -> file = new FileToolItem(new Item.Properties().tab(ModItemGroups.tool_tab), 2)));

    }

    public static void registerColorForItem() {
        registerAToolItemColor(pickaxeItem);
        registerAToolItemColor(hammerItem);
        registerAToolItemColor(fileItem);
    }

    public static void registerAToolItemColor(RegistryObject<Item> item) {
        if (item != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0) {
                    return (((ITToolItem) stack.getItem()).getHeadColor(stack));
                } else if (tintIndex == 1) {
                    return ((ITToolItem) stack.getItem()).getBindingColor(stack);
                } else if (tintIndex == 2) {
                    return ((ITToolItem) stack.getItem()).getHandleColor(stack);
                } else return 0;
            }, item.get());
        }
    }

    private static int ColorToInt(int r, int g, int b) {
        int ret = 0;
        ret += r;
        ret = ret << 8;
        ret += g;
        ret = ret << 8;
        ret += b;
        return ret;
    }

    public static void clientInit(FMLClientSetupEvent event) {
        registerColorForItem();
        ItemModelsProperties.register(pickaxeItem.get(), new ResourceLocation(Ref.mod_id, "head_texture_flag"), (stack, world, living) -> {
            Item item = stack.getItem();
            ItemStack itemStack = stack;
            if (item instanceof ITToolItem) {
                ITToolItem toolItem = (ITToolItem) item;
                ITToolItemItemStackHandler itToolItemItemStackHandler = toolItem.getItemStackHandler(itemStack);
                ToolHeadItem toolHeadItem = (ToolHeadItem) itToolItemItemStackHandler.getToolHead().getItem();

                if (toolHeadItem != null) {
                    return toolHeadItem.textureFlag.getID();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        });
        ItemModelsProperties.register(pickaxeItem.get(), new ResourceLocation(Ref.mod_id, "binding_texture_flag"), (stack, world, living) -> {
            Item item = stack.getItem();
            ItemStack itemStack = stack;
            if (item instanceof ITToolItem) {
                ITToolItem toolItem = (ITToolItem) item;
                ITToolItemItemStackHandler itToolItemItemStackHandler = toolItem.getItemStackHandler(itemStack);
                ToolBindingItem toolBindingItem = (ToolBindingItem) itToolItemItemStackHandler.getToolBinding().getItem();

                if (toolBindingItem != null) {
                    return toolBindingItem.textureFlag.getID();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        });
        ItemModelsProperties.register(pickaxeItem.get(), new ResourceLocation(Ref.mod_id, "handle_texture_flag"), (stack, world, living) -> {
            Item item = stack.getItem();
            ItemStack itemStack = stack;
            if (item instanceof ITToolItem) {
                ITToolItem toolItem = (ITToolItem) item;
                ITToolItemItemStackHandler itToolItemItemStackHandler = toolItem.getItemStackHandler(itemStack);
                ToolHandleItem toolHandleItem = (ToolHandleItem) itToolItemItemStackHandler.getToolHandle().getItem();

                if (toolHandleItem != null) {
                    return toolHandleItem.textureFlag.getID();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        });
    }

    public static void commonInit() {
    }

    @SubscribeEvent
    public static void onItemRightClickEvent(final PlayerInteractEvent.RightClickItem event) {
        handToolCrafting(pickaxe, event, 1, hammerItem.get());;
    }

    public static void handToolCrafting(ITToolItem toolItemIn, PlayerInteractEvent.RightClickItem event, int toolNeededDamage, Item... toolNeededIn) {
        PlayerEntity playerEntity = event.getPlayer();
        ItemStack mainHand = playerEntity.getMainHandItem();
        ItemStack offHand = playerEntity.getOffhandItem();
        ItemStack toolItem = new ItemStack(toolItemIn.getItem());
        ITToolItemItemStackHandler itemStackHandler = toolItemIn.getItemStackHandler(toolItem);

        if (toolItemIn.getPartsToWork() == 3) {
            if ((mainHand.getItem() instanceof ToolHeadItem && offHand.getItem() instanceof ToolHandleItem) || (offHand.getItem() instanceof ToolHeadItem && mainHand.getItem() instanceof ToolHandleItem)) {
                for (Item toolNeeded : toolNeededIn) {
                    if (inventoryToolCheck(playerEntity, toolNeededIn)){
                        toolItemIn.damageToolsNeededInPlayerInventory(playerEntity, event.getWorld(), toolNeededDamage, toolNeededIn);
                        itemStackHandler.setToolHead(mainHand.getItem() instanceof ToolHeadItem ? mainHand : offHand);
                        itemStackHandler.setToolHandle(mainHand.getItem() instanceof ToolHandleItem ? mainHand : offHand);

                        ItemStack handleItemStack = itemStackHandler.getToolHandle();
                        ItemStack headItemStack = itemStackHandler.getToolHead();

                        toolItemIn.setHandleColor(toolItem, ((ToolHandleItem) handleItemStack.getItem()).color);
                        toolItemIn.setHeadColor(toolItem, ((ToolHeadItem) headItemStack.getItem()).color);

                        playerEntity.setItemInHand(Hand.MAIN_HAND, toolItem);
                        playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof ITToolItem ? Hand.OFF_HAND : Hand.MAIN_HAND, ItemStack.EMPTY);
                    }
                }
            }


            if ((mainHand.getItem() instanceof ITToolItem && offHand.getItem() instanceof ToolBindingItem)) {
                if (inventoryToolCheck(playerEntity, toolNeededIn)) {
                    ItemStack newToolItemStack = (mainHand.getItem() instanceof ITToolItem ? mainHand : offHand);
                    ITToolItem newToolItem = (ITToolItem) newToolItemStack.getItem();
                    ITToolItemItemStackHandler itemStackHandlerNew = newToolItem.getItemStackHandler(mainHand.getItem() instanceof ITToolItem ? mainHand : offHand);
                    itemStackHandlerNew.setToolBinding(mainHand.getItem() instanceof ToolBindingItem ? mainHand : offHand);
                    ItemStack handleItemStack = itemStackHandlerNew.getToolHandle();
                    ItemStack headItemStack = itemStackHandlerNew.getToolHead();
                    ItemStack bindingItemStack = itemStackHandlerNew.getToolBinding();
                    ToolHandleItem handleItem = (ToolHandleItem) handleItemStack.getItem();
                    ToolHeadItem headItem = (ToolHeadItem) headItemStack.getItem();
                    ToolBindingItem bindingItem = (ToolBindingItem) bindingItemStack.getItem();

                    newToolItem.setBindingColor(newToolItemStack, bindingItem.color);
                    newToolItem.setAttackDamage(newToolItemStack, headItem.attackDamage);
                    newToolItem.setEfficiency(newToolItemStack, headItem.efficiency);
                    newToolItem.setAttackSpeed(newToolItemStack, handleItem.weight + headItem.weight + bindingItem.weight);
                    newToolItem.setHarvestLevel(newToolItemStack, headItem.getItToolType());

                    playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof ITToolItem ? Hand.MAIN_HAND : Hand.OFF_HAND, newToolItemStack);
                    playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof ITToolItem ? Hand.OFF_HAND : Hand.MAIN_HAND, ItemStack.EMPTY);
                }
            }
        }

        if (toolItemIn.getPartsToWork() == 2) {
            if (inventoryToolCheck(playerEntity, toolNeededIn)) {
                itemStackHandler.setToolHead(mainHand.getItem() instanceof ToolHeadItem ? mainHand : offHand);
                itemStackHandler.setToolHandle(mainHand.getItem() instanceof ToolHandleItem ? mainHand : offHand);
                ItemStack handleItemStack = itemStackHandler.getToolHandle();
                ItemStack headItemStack = itemStackHandler.getToolHead();
                ItemStack bindingItemStack = itemStackHandler.getToolBinding();
                ToolHandleItem handleItem = (ToolHandleItem) handleItemStack.getItem();
                ToolHeadItem headItem = (ToolHeadItem) headItemStack.getItem();
                ToolBindingItem bindingItem = (ToolBindingItem) bindingItemStack.getItem();

                toolItemIn.setHandleColor(toolItem, handleItem.color);
                toolItemIn.setHeadColor(toolItem, headItem.color);
                toolItemIn.setBindingColor(toolItem, bindingItem.color);
                toolItemIn.setAttackDamage(toolItem, headItem.attackDamage);
                toolItemIn.setEfficiency(toolItem, headItem.efficiency);
                toolItemIn.setAttackSpeed(toolItem, handleItem.weight + headItem.weight + bindingItem.weight);
                toolItemIn.setHarvestLevel(toolItem, headItem.getItToolType());

                playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof ITToolItem ? Hand.MAIN_HAND : Hand.OFF_HAND, toolItem);
                playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof ITToolItem ? Hand.OFF_HAND : Hand.MAIN_HAND, ItemStack.EMPTY);
            }
        }
    }

    public static boolean inventoryToolCheck(PlayerEntity playerIn, Item... toolNeededIn){
        List<Item> newList = Arrays.asList(toolNeededIn.clone());
        for(int i = 9; i <= 45; i++){
            ItemStack toolNeeded = playerIn.inventory.getItem(i);
            if(newList.contains(toolNeeded.getItem())){
                if(toolNeeded.getItem() instanceof ITToolItem){
                    ITToolItem toolNeededItem = (ITToolItem)playerIn.inventory.getItem(i).getItem();
                    ITToolItemItemStackHandler itemStackHandler = toolNeededItem.getItemStackHandler(toolNeeded);

                    if((toolNeededItem.partsToWork == 1 || toolNeededItem.partsToWork == 2 || toolNeededItem.partsToWork == 3) & itemStackHandler.getToolHead() != null){
                        if(itemStackHandler.getToolHead().getDamageValue() < itemStackHandler.getToolHead().getMaxDamage()) {
                            newList.remove(toolNeeded.getItem());
                        }
                    }

                    if((toolNeededItem.partsToWork == 2 || toolNeededItem.partsToWork == 3) & itemStackHandler.getToolHandle() != null){
                        if(itemStackHandler.getToolHandle().getDamageValue() < itemStackHandler.getToolHandle().getDamageValue()){
                            newList.remove(toolNeeded.getItem());
                        }
                    }

                    if(toolNeededItem.partsToWork == 3 & itemStackHandler.getToolBinding() != null){
                        if(itemStackHandler.getToolBinding().getDamageValue() < itemStackHandler.getToolBinding().getDamageValue()){
                            newList.remove(toolNeeded.getItem());
                        }
                    }
                }else{
                    if(toolNeeded.getDamageValue() < toolNeeded.getMaxDamage()) {
                        newList.remove(toolNeeded.getItem());
                    }
                }
            }
        }
        return newList.size() == 0;
    }
}
