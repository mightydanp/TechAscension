package mightydanp.techcore.common.material.tool;

import mightydanp.techcore.common.handler.itemstack.TCToolItemInventoryHelper;
import mightydanp.techcore.common.tool.part.HandleItem;
import mightydanp.techcore.common.items.TCToolItem;
import mightydanp.techcore.common.tool.part.BindingItem;
import mightydanp.techcore.common.tool.part.HeadItem;
import mightydanp.techcore.common.libs.Ref;
import mightydanp.techcore.common.tool.TCTool;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class TCToolHandler {
    @SubscribeEvent
    public static void handCraftingInit(final PlayerInteractEvent.RightClickItem event) {
        for(TCTool tool : TCTools.tools) {//redo crashes on rightclick anything
            //ITTool.handToolCrafting((ITToolItem)tool.toolItem.get(), event, tool.hitDamage, ((ITToolItem)tool.toolItem.get()).toolsNeeded);
        }
    }

    @SubscribeEvent
    public static void onItemRightClickEvent(final PlayerInteractEvent.RightClickItem event) {
        if(event.getItemStack().getItem() == Items.STICK){
            //Minecraft.getInstance().setScreen(new SyncScreen(materialServer));
        }

        Item offHandItem = event.getPlayer().getOffhandItem().getItem();
        Item mainHandItem = event.getPlayer().getMainHandItem().getItem();

        if(mainHandItem instanceof HeadItem toolHead) {
            List<TCTool> tool = TCTools.tools.stream().filter(itTool -> Objects.equals(itTool.toolName, toolHead.suggestedCraftedTool)).toList();

            if (tool.size() > 0) {
                TCToolItem toolItem = (TCToolItem)tool.get(0).toolItem.get();
                handToolCrafting(toolItem, event, 1, toolItem.assembleItems);
            }
        }

        if(offHandItem instanceof HeadItem toolHead){
            List<TCTool> tool = TCTools.tools.stream().filter(itTool -> Objects.equals(itTool.toolName, toolHead.suggestedCraftedTool)).toList();

            if (tool.size() > 0) {
                TCToolItem toolItem = (TCToolItem)tool.get(0).toolItem.get();
                handToolCrafting(toolItem, event, 1, toolItem.assembleItems);
            }
        }

        if(mainHandItem instanceof TCToolItem tool){
            handToolCrafting(tool, event, 1, tool.assembleItems);
        }

        if(offHandItem instanceof TCToolItem tool){
            handToolCrafting(tool, event, 1, tool.assembleItems);
        }
    }

    public static void handToolCrafting(TCToolItem toolItemIn, PlayerInteractEvent.RightClickItem event, int toolNeededDamage, Map<String, Integer> toolNeededIn) {
        Player playerEntity = event.getPlayer();
        ItemStack mainHand = playerEntity.getMainHandItem();
        ItemStack offHand = playerEntity.getOffhandItem();
        ItemStack toolItem = new ItemStack(toolItemIn.asItem());
        TCToolItemInventoryHelper itemStackHandler = toolItemIn.inventory;
        List<String> firstItemsNeeded = compareAndAddToNewArray(1, toolNeededIn);
        List<String> firstItemsThatCanBeUsed = compareAndAddToNewArray(-1, toolNeededIn);
        List<String> secondItemsNeeded = compareAndAddToNewArray(2, toolNeededIn);
        List<String> secondItemsThatCanBeUsed = compareAndAddToNewArray(-2, toolNeededIn);

        if (toolItemIn.getParts() == 3 || toolItemIn.getParts() == 2) {

            ItemStack mainHandCheck = toolItemIn.heads.containsValue(mainHand.getItem()) ? playerEntity.getMainHandItem() : (toolItemIn.handles.containsValue(mainHand.getItem()) ? playerEntity.getMainHandItem() : null);
            ItemStack offHandCheck = toolItemIn.heads.containsValue(offHand.getItem()) ? playerEntity.getOffhandItem() : (toolItemIn.handles.containsValue(offHand.getItem()) ? playerEntity.getOffhandItem() : null);
            if (mainHandCheck != null && offHandCheck != null) {

                if (inventoryToolCheck(playerEntity, firstItemsNeeded) || inventoryToolCheck(playerEntity, firstItemsThatCanBeUsed)) {
                    if(inventoryToolCheck(playerEntity, firstItemsNeeded)){
                        toolItemIn.damageToolsNeededInPlayerInventory(playerEntity, event.getWorld(), toolNeededDamage, firstItemsNeeded);
                    }

                    if(inventoryToolCheck(playerEntity, firstItemsThatCanBeUsed)){
                        toolItemIn.damageToolsNeededInPlayerInventory(playerEntity, event.getWorld(), toolNeededDamage, firstItemsThatCanBeUsed);
                    }

                    itemStackHandler.setToolHead(mainHandCheck.getItem() instanceof HeadItem ? mainHandCheck : offHandCheck);
                    itemStackHandler.setToolHandle(mainHandCheck.getItem() instanceof HandleItem ? mainHandCheck : offHandCheck);

                    ItemStack handleItemStack = itemStackHandler.getToolHandle();
                    ItemStack headItemStack = itemStackHandler.getToolHead();

                    HandleItem toolHandleItem = (HandleItem) handleItemStack.getItem();
                    HeadItem toolHeadItem = (HeadItem) headItemStack.getItem();

                    toolItemIn.setHandleColor(toolItem, toolHandleItem.color);
                    toolItemIn.setHeadColor(toolItem, toolHeadItem.color);

                    toolItemIn.setAttackDamage(toolItem, toolHeadItem.attackDamage);
                    toolItemIn.setEfficiency(toolItem, toolHeadItem.efficiency);
                    toolItemIn.setToolLevel(toolItem, toolHeadItem.itToolType);

                    if (toolItemIn.getParts() == 2) {
                        toolItemIn.setAttackSpeed(toolItem, toolHandleItem.weight + toolHeadItem.weight);
                    }

                    playerEntity.setItemInHand(InteractionHand.MAIN_HAND, toolItem);
                    playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof TCToolItem ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                }
            }
        }

        if (toolItemIn.getParts() == 3){
            ItemStack mainHandNew = playerEntity.getMainHandItem();
            ItemStack offHandNew = playerEntity.getOffhandItem();

            ItemStack mainHandCheck = toolItemIn.bindings.containsValue(mainHand.getItem()) ? playerEntity.getMainHandItem() : (mainHandNew.getItem() instanceof TCToolItem ? playerEntity.getMainHandItem() : null);
            ItemStack offHandCheck = toolItemIn.bindings.containsValue(offHand.getItem()) ? playerEntity.getOffhandItem() : (offHandNew.getItem() instanceof TCToolItem ? playerEntity.getOffhandItem() : null);

            if ((mainHandCheck != null && offHandCheck!= null)) {
                if (inventoryToolCheck(playerEntity, secondItemsNeeded) || inventoryToolCheck(playerEntity, secondItemsThatCanBeUsed)) {
                    if(inventoryToolCheck(playerEntity, secondItemsNeeded)){
                        toolItemIn.damageToolsNeededInPlayerInventory(playerEntity, event.getWorld(), toolNeededDamage, secondItemsNeeded);
                    }

                    if(inventoryToolCheck(playerEntity, secondItemsThatCanBeUsed)){
                        toolItemIn.damageToolsNeededInPlayerInventory(playerEntity, event.getWorld(), toolNeededDamage, secondItemsThatCanBeUsed);
                    }

                    ItemStack newToolItemStack = (mainHandCheck.getItem() instanceof TCToolItem ? mainHandCheck : offHandCheck);
                    TCToolItem newToolItem = (TCToolItem)newToolItemStack.getItem();

                    TCToolItemInventoryHelper itemStackHandlerNew;
                    if(mainHandCheck.getItem() instanceof TCToolItem itToolItem){
                        itemStackHandlerNew = itToolItem.inventory;
                    }else{
                        TCToolItem itToolItem = (TCToolItem) offHandCheck.getItem();
                        itemStackHandlerNew = itToolItem.inventory;
                    }

                    itemStackHandlerNew.setToolBinding(mainHandCheck.getItem() instanceof BindingItem ? mainHandCheck : offHandCheck);
                    ItemStack handleItemStack = itemStackHandlerNew.getToolHandle();
                    ItemStack headItemStack = itemStackHandlerNew.getToolHead();
                    ItemStack bindingItemStack = itemStackHandlerNew.getToolBinding();
                    HandleItem handleItem = (HandleItem) handleItemStack.getItem();
                    HeadItem headItem = (HeadItem) headItemStack.getItem();
                    BindingItem bindingItem = (BindingItem) bindingItemStack.getItem();

                    newToolItem.setBindingColor(newToolItemStack, bindingItem.color);
                    newToolItem.setAttackDamage(newToolItemStack, headItem.attackDamage);
                    newToolItem.setEfficiency(newToolItemStack, headItem.efficiency);
                    newToolItem.setAttackSpeed(newToolItemStack, handleItem.weight + headItem.weight + bindingItem.weight);
                    newToolItem.setToolLevel(newToolItemStack, headItem.itToolType);

                    playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof TCToolItem ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND, newToolItemStack);
                    playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof TCToolItem ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                }
            }
        }
    }

    public static boolean inventoryToolCheck(Player playerIn, List<String> toolNeededIn){
        List<Item> toolNeededList = convertToItem(toolNeededIn);

        for(int i = 0; i <= 35; i++){

            ItemStack toolNeeded = playerIn.getInventory().getItem(i);
            if(toolNeededList.contains(toolNeeded.getItem())){
                if(toolNeeded.getItem() instanceof TCToolItem){
                    TCToolItem toolNeededItem = (TCToolItem)playerIn.getInventory().getItem(i).getItem();
                    TCToolItemInventoryHelper itemStackHandler = toolNeededItem.inventory;

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
