package mightydanp.industrialtech.api.common.tool;

import mightydanp.industrialtech.api.common.handler.RegistryHandler;
import mightydanp.industrialtech.api.common.handler.itemstack.ITToolItemInventoryHelper;
import mightydanp.industrialtech.api.common.items.*;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.IToolType;
import mightydanp.industrialtech.api.common.libs.Ref;
import mightydanp.industrialtech.api.common.material.tool.ITTools;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created by MightyDanp on 3/8/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class ITTool {
    public IToolType toolType = null;

    public String toolName;

    public int hitDamage;

    public RegistryObject<Item> toolItem;


    public ITTool(String nameIn, int hitDamageIn, IToolType toolTypeIn, Supplier<ITToolItem> toolIn) {
        toolItem = RegistryHandler.ITEMS.register(nameIn, toolIn);
        toolName = nameIn;
        hitDamage = hitDamageIn;
        toolType = toolTypeIn;

    }

    public void registerColorForItem() {
        registerAToolItemColor(toolItem);
    }

    public void registerAToolItemColor(RegistryObject<Item> item) {
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

    @SubscribeEvent
    public static void onItemRightClickEvent(final PlayerInteractEvent.RightClickItem event) {
        if(event.getItemStack().getItem() == Items.STICK){
            //Minecraft.getInstance().setScreen(new SyncScreen(materialServer));
        }

        Item offHandItem = event.getPlayer().getOffhandItem().getItem();
        Item mainHandItem = event.getPlayer().getMainHandItem().getItem();



        if(mainHandItem instanceof ToolHeadItem toolHead) {
            List<ITTool> tool = ITTools.tools.stream().filter(itTool -> Objects.equals(itTool.toolName, toolHead.suggestedCraftedTool)).toList();

            if (tool.size() > 0) {
                ITToolItem toolItem = (ITToolItem)tool.get(0).toolItem.get();
                handToolCrafting(toolItem, event, 1, toolItem.toolsNeeded);
            }
        }

        if(offHandItem instanceof ToolHeadItem toolHead){
            List<ITTool> tool = ITTools.tools.stream().filter(itTool -> Objects.equals(itTool.toolName, toolHead.suggestedCraftedTool)).toList();

            if (tool.size() > 0) {
                ITToolItem toolItem = (ITToolItem)tool.get(0).toolItem.get();
                handToolCrafting(toolItem, event, 1, toolItem.toolsNeeded);
            }
        }

        if(mainHandItem instanceof ITToolItem tool){
            handToolCrafting(tool, event, 1, tool.toolsNeeded);
        }

        if(offHandItem instanceof ITToolItem tool){
            handToolCrafting(tool, event, 1, tool.toolsNeeded);
        }
    }

    public static void handToolCrafting(ITToolItem toolItemIn, PlayerInteractEvent.RightClickItem event, int toolNeededDamage, Map<String, Integer> toolNeededIn) {
        Player playerEntity = event.getPlayer();
        ItemStack mainHand = playerEntity.getMainHandItem();
        ItemStack offHand = playerEntity.getOffhandItem();
        ItemStack toolItem = new ItemStack(toolItemIn.asItem());
        ITToolItemInventoryHelper itemStackHandler = toolItemIn.inventory;
        List<String> firstItemsNeeded = compareAndAddToNewArray(1, toolNeededIn);
        List<String> firstItemsThatCanBeUsed = compareAndAddToNewArray(-1, toolNeededIn);
        List<String> secondItemsNeeded = compareAndAddToNewArray(2, toolNeededIn);
        List<String> secondItemsThatCanBeUsed = compareAndAddToNewArray(-2, toolNeededIn);


        if (toolItemIn.getParts() == 3 || toolItemIn.getParts() == 2) {
            Item toolParts0 = ForgeRegistries.ITEMS.getValue(stringToResourceLocation(new ArrayList<>(toolItemIn.parts.keySet()).get(0)));
            Item toolParts1 = ForgeRegistries.ITEMS.getValue(stringToResourceLocation(new ArrayList<>(toolItemIn.parts.keySet()).get(1)));

            ItemStack mainHandCheck = playerEntity.getMainHandItem().getItem() == toolParts0 ? playerEntity.getMainHandItem() : (playerEntity.getMainHandItem().getItem() == toolParts1 ? playerEntity.getMainHandItem() : null);
            ItemStack offHandCheck = playerEntity.getOffhandItem().getItem() == toolParts0 ? playerEntity.getOffhandItem() : (playerEntity.getOffhandItem().getItem() == toolParts1 ? playerEntity.getOffhandItem() : null);
            if (mainHandCheck != null && offHandCheck != null) {
                //Item toolParts1 = ForgeRegistries.ITEMS.getValue(stringToResourceLocation(new ArrayList<>(toolItemIn.toolParts.keySet()).get(1)));

                if (inventoryToolCheck(playerEntity, firstItemsNeeded) || inventoryToolCheck(playerEntity, firstItemsThatCanBeUsed)) {
                    if(inventoryToolCheck(playerEntity, firstItemsNeeded)){
                        toolItemIn.damageToolsNeededInPlayerInventory(playerEntity, event.getWorld(), toolNeededDamage, firstItemsNeeded);
                    }

                    if(inventoryToolCheck(playerEntity, firstItemsThatCanBeUsed)){
                        toolItemIn.damageToolsNeededInPlayerInventory(playerEntity, event.getWorld(), toolNeededDamage, firstItemsThatCanBeUsed);
                    }

                    itemStackHandler.setToolHead(mainHandCheck.getItem() instanceof ToolHeadItem ? mainHandCheck : offHandCheck);
                    itemStackHandler.setToolHandle(mainHandCheck.getItem() instanceof ToolHandleItem ? mainHandCheck : offHandCheck);

                    ItemStack handleItemStack = itemStackHandler.getToolHandle();
                    ItemStack headItemStack = itemStackHandler.getToolHead();

                    ToolHandleItem toolHandleItem = (ToolHandleItem) handleItemStack.getItem();
                    ToolHeadItem toolHeadItem = (ToolHeadItem) headItemStack.getItem();

                    toolItemIn.setHandleColor(toolItem, toolHandleItem.color);
                    toolItemIn.setHeadColor(toolItem, toolHeadItem.color);

                    toolItemIn.setAttackDamage(toolItem, toolHeadItem.attackDamage);
                    toolItemIn.setEfficiency(toolItem, toolHeadItem.efficiency);
                    toolItemIn.setToolLevel(toolItem, toolHeadItem.getItToolType());

                    if (toolItemIn.getParts() == 2) {
                        toolItemIn.setAttackSpeed(toolItem, toolHandleItem.weight + toolHeadItem.weight);
                    }

                    playerEntity.setItemInHand(InteractionHand.MAIN_HAND, toolItem);
                    playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof ITToolItem ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                }
            }
        }

        if (toolItemIn.getParts() == 3){
            ItemStack mainHandNew = playerEntity.getMainHandItem();
            ItemStack offHandNew = playerEntity.getOffhandItem();

            Item toolParts2 = ForgeRegistries.ITEMS.getValue(stringToResourceLocation(new ArrayList<>(toolItemIn.parts.keySet()).get(2)));

            ItemStack mainHandCheck = mainHandNew.getItem() == toolParts2 ? mainHandNew : (mainHandNew.getItem() instanceof ITToolItem ? playerEntity.getMainHandItem() : null);
            ItemStack offHandCheck = offHandNew.getItem() == toolParts2 ? offHandNew : (offHandNew.getItem() instanceof ITToolItem ? playerEntity.getOffhandItem() : null);

            if ((mainHandCheck != null && offHandCheck!= null)) {
                if (inventoryToolCheck(playerEntity, secondItemsNeeded) || inventoryToolCheck(playerEntity, secondItemsThatCanBeUsed)) {
                    if(inventoryToolCheck(playerEntity, secondItemsNeeded)){
                        toolItemIn.damageToolsNeededInPlayerInventory(playerEntity, event.getWorld(), toolNeededDamage, secondItemsNeeded);
                    }

                    if(inventoryToolCheck(playerEntity, secondItemsThatCanBeUsed)){
                        toolItemIn.damageToolsNeededInPlayerInventory(playerEntity, event.getWorld(), toolNeededDamage, secondItemsThatCanBeUsed);
                    }

                    ItemStack newToolItemStack = (mainHandCheck.getItem() instanceof ITToolItem ? mainHandCheck : offHandCheck);
                    ITToolItem newToolItem = (ITToolItem)newToolItemStack.getItem();

                    ITToolItemInventoryHelper itemStackHandlerNew;
                    if(mainHandCheck.getItem() instanceof ITToolItem itToolItem){
                        itemStackHandlerNew = itToolItem.inventory;
                    }else{
                        ITToolItem itToolItem = (ITToolItem) offHandCheck.getItem();
                        itemStackHandlerNew = itToolItem.inventory;
                    }

                    itemStackHandlerNew.setToolBinding(mainHandCheck.getItem() instanceof ToolBindingItem ? mainHandCheck : offHandCheck);
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
                    newToolItem.setToolLevel(newToolItemStack, headItem.getItToolType());

                    playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof ITToolItem ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND, newToolItemStack);
                    playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof ITToolItem ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                }
            }
        }
    }

    public static boolean inventoryToolCheck(Player playerIn, List<String> toolNeededIn){
        List<Item> toolNeededList = convertToItem(toolNeededIn);

        for(int i = 0; i <= 35; i++){

            ItemStack toolNeeded = playerIn.getInventory().getItem(i);
            if(toolNeededList.contains(toolNeeded.getItem())){
                if(toolNeeded.getItem() instanceof ITToolItem){
                    ITToolItem toolNeededItem = (ITToolItem)playerIn.getInventory().getItem(i).getItem();
                    ITToolItemInventoryHelper itemStackHandler = toolNeededItem.inventory;

                    if((toolNeededItem.getParts() == 1 || toolNeededItem.getParts() == 2 || toolNeededItem.getParts() == 3) & itemStackHandler.getToolHead() != null){
                        if(itemStackHandler.getToolHead().getDamageValue() < itemStackHandler.getToolHead().getMaxDamage()) {
                            toolNeededList.remove(toolNeeded.getItem());
                        }
                    }

                    if((toolNeededItem.getParts() == 2 || toolNeededItem.getParts() == 3) & itemStackHandler.getToolHandle() != null){
                        if(itemStackHandler.getToolHandle().getDamageValue() < itemStackHandler.getToolHandle().getDamageValue()){
                            toolNeededList.remove(toolNeeded.getItem());
                        }
                    }

                    if(toolNeededItem.getParts() == 3 & itemStackHandler.getToolBinding() != null){
                        if(itemStackHandler.getToolBinding().getDamageValue() < itemStackHandler.getToolBinding().getDamageValue()){
                            toolNeededList.remove(toolNeeded.getItem());
                        }
                    }
                }else{
                    if(toolNeeded.getDamageValue() < toolNeeded.getMaxDamage()) {
                        toolNeededList.remove(toolNeeded.getItem());
                    }
                }
            }
        }
        return toolNeededList.size() == 0;
    }

    public static List<String> compareAndAddToNewArray(int numberIn, Map<String, Integer> pairArrayIn){
        List<String> newArray = new ArrayList<>();

        pairArrayIn.forEach((string, integer) -> {
            if(integer == numberIn) {
                newArray.add(string);
            }
        });

        return newArray;
    }

    public static ResourceLocation stringToResourceLocation(String stringIn){
        String modID = stringIn.split(":")[0];
        String item = stringIn.split(":")[1];

        return new ResourceLocation(modID, item);
    }

    public static List<Item> convertToItem(List<String> listIn){
        List<Item> list = new ArrayList<>();

        listIn.forEach(o -> {
            Item item = ForgeRegistries.ITEMS.getValue(stringToResourceLocation(o));

            if(item != null){
                list.add(item);
            }
        });

        return list;
    }
}
