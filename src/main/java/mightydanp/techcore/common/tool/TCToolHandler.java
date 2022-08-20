package mightydanp.techcore.common.tool;

import mightydanp.techcore.client.settings.keybindings.KeyBindings;
import mightydanp.techcore.common.handler.itemstack.TCToolItemInventoryHelper;
import mightydanp.techcore.common.tool.part.HandleItem;
import mightydanp.techcore.common.items.TCToolItem;
import mightydanp.techcore.common.tool.part.BindingItem;
import mightydanp.techcore.common.tool.part.HeadItem;
import mightydanp.techcore.common.libs.Ref;
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

import java.util.*;

@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class TCToolHandler {

    @SubscribeEvent
    @SuppressWarnings("ALL")
    public static void onItemRightClickEvent(final PlayerInteractEvent.RightClickItem event) {
        if(event.getItemStack().getItem() == Items.STICK){
            //Minecraft.getInstance().setScreen(new SyncScreen(materialServer));
        }

        if(KeyBindings.handCrafting.isDown()){

            Item offHandItem = event.getPlayer().getOffhandItem().getItem();
            Item mainHandItem = event.getPlayer().getMainHandItem().getItem();

            if (mainHandItem instanceof HeadItem toolHead) {
                List<TCTool> tool = TCTools.tools.stream().filter(itTool -> Objects.equals(itTool.toolName, toolHead.suggestedCraftedTool)).toList();

                if (tool.size() > 0) {
                    TCToolItem toolItem = (TCToolItem) tool.get(0).toolItem.get();
                    handToolCrafting(toolItem, event, 1, toolItem.assembleItems);
                }
            }

            if (offHandItem instanceof HeadItem toolHead) {
                List<TCTool> tool = TCTools.tools.stream().filter(itTool -> Objects.equals(itTool.toolName, toolHead.suggestedCraftedTool)).toList();

                if (tool.size() > 0) {
                    TCToolItem toolItem = (TCToolItem) tool.get(0).toolItem.get();
                    handToolCrafting(toolItem, event, 1, toolItem.assembleItems);
                }
            }

            if (mainHandItem instanceof TCToolItem tool) {
                handToolCrafting(tool, event, 1, tool.assembleItems);
            }

            if (offHandItem instanceof TCToolItem tool) {
                handToolCrafting(tool, event, 1, tool.assembleItems);
            }
        }
    }

    @SuppressWarnings("ALL")
    public static void handToolCrafting(TCToolItem toolItemIn, PlayerInteractEvent.RightClickItem event, int toolNeededDamage, Map<String, Integer> toolNeededIn) {
        Player playerEntity = event.getPlayer();
        ItemStack mainHand = playerEntity.getMainHandItem();
        ItemStack offHand = playerEntity.getOffhandItem();
        ItemStack toolItem = new ItemStack(toolItemIn.asItem());
        TCToolItemInventoryHelper itemStackHandler = toolItemIn.getInventory(toolItem);
        List<String> firstItemsNeeded = compareAndAddToNewArray(1, toolNeededIn);
        List<String> firstItemsThatCanBeUsed = compareAndAddToNewArray(-1, toolNeededIn);
        List<String> secondItemsNeeded = compareAndAddToNewArray(2, toolNeededIn);
        List<String> secondItemsThatCanBeUsed = compareAndAddToNewArray(-2, toolNeededIn);

        ItemStack mainHandCheck = toolItemIn.heads.containsValue(mainHand.getItem()) ? playerEntity.getMainHandItem() : (toolItemIn.handles.containsValue(mainHand.getItem()) ? playerEntity.getMainHandItem() : null);
        ItemStack offHandCheck = toolItemIn.heads.containsValue(offHand.getItem()) ? playerEntity.getOffhandItem() : (toolItemIn.handles.containsValue(offHand.getItem()) ? playerEntity.getOffhandItem() : null);

        if (toolItemIn.getParts() >= 2) {
            if (mainHandCheck != null && offHandCheck != null && mainHandCheck.getDamageValue() != mainHandCheck.getMaxDamage() && offHandCheck.getDamageValue() != offHandCheck.getMaxDamage()) {

                if (inventoryToolCheck(playerEntity, firstItemsNeeded) || (firstItemsThatCanBeUsed.size() != 0 && inventoryToolCheck(playerEntity, firstItemsThatCanBeUsed))) {
                    if(inventoryToolCheck(playerEntity, firstItemsNeeded)){
                        toolItemIn.damageToolsNeededInPlayerInventory(playerEntity, event.getWorld(), toolNeededDamage, firstItemsNeeded);
                    }

                    if(inventoryToolCheck(playerEntity, firstItemsThatCanBeUsed)){
                        toolItemIn.damageToolsNeededInPlayerInventory(playerEntity, event.getWorld(), toolNeededDamage, firstItemsThatCanBeUsed);
                    }

                    itemStackHandler.setToolHead(toolItem, mainHandCheck.getItem() instanceof HeadItem ? mainHandCheck : offHandCheck);
                    itemStackHandler.setToolHandle(toolItem, mainHandCheck.getItem() instanceof HandleItem ? mainHandCheck : offHandCheck);

                    ItemStack handleItemStack = itemStackHandler.getToolHandle(toolItem);
                    ItemStack headItemStack = itemStackHandler.getToolHead(toolItem);

                    HandleItem toolHandleItem = (HandleItem) handleItemStack.getItem();
                    HeadItem toolHeadItem = (HeadItem) headItemStack.getItem();

                    toolItemIn.setHandleColor(toolItem, toolHandleItem.color);
                    toolItemIn.setHeadColor(toolItem, toolHeadItem.color);

                    toolItemIn.setAttackDamage(toolItem, toolHeadItem.attackDamage);
                    toolItemIn.setEfficiency(toolItem, toolHeadItem.efficiency);
                    toolItemIn.setToolLevel(toolItem, toolHeadItem.tools);

                    if (toolItemIn.getParts() == 2) {
                        toolItemIn.setAttackSpeed(toolItem, toolHandleItem.weight + toolHeadItem.weight);
                    }

                    Random random = new Random();

                    //playerEntity.playSound(SoundEvents., 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));

                    playerEntity.setItemInHand(InteractionHand.MAIN_HAND, toolItem);
                    playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof TCToolItem ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                }
            }
        }

        if (toolItemIn.getParts() >= 3){
            mainHand = playerEntity.getMainHandItem();
            offHand = playerEntity.getOffhandItem();

            mainHandCheck = toolItemIn.bindings.containsValue(mainHand.getItem()) ? playerEntity.getMainHandItem() : (mainHand.getItem() instanceof TCToolItem ? playerEntity.getMainHandItem() : null);
            offHandCheck = toolItemIn.bindings.containsValue(offHand.getItem()) ? playerEntity.getOffhandItem() : (offHand.getItem() instanceof TCToolItem ? playerEntity.getOffhandItem() : null);

            itemStackHandler = mainHand.getItem() instanceof TCToolItem ? ((TCToolItem)mainHand.getItem()).getInventory(mainHand) : offHand.getItem() instanceof TCToolItem ? ((TCToolItem)offHand.getItem()).getInventory(offHand) : null;

            if(itemStackHandler != null) {
                if (mainHandCheck != null && offHandCheck != null && mainHandCheck.getDamageValue() != mainHandCheck.getMaxDamage() && offHandCheck.getDamageValue() != offHandCheck.getMaxDamage()) {
                    if (inventoryToolCheck(playerEntity, secondItemsNeeded) || (secondItemsThatCanBeUsed.size() != 0 && inventoryToolCheck(playerEntity, secondItemsThatCanBeUsed))) {
                        if (inventoryToolCheck(playerEntity, secondItemsNeeded)) {
                            toolItemIn.damageToolsNeededInPlayerInventory(playerEntity, event.getWorld(), toolNeededDamage, secondItemsNeeded);
                        }

                        if (inventoryToolCheck(playerEntity, secondItemsThatCanBeUsed)) {
                            toolItemIn.damageToolsNeededInPlayerInventory(playerEntity, event.getWorld(), toolNeededDamage, secondItemsThatCanBeUsed);
                        }

                        toolItem = (mainHandCheck.getItem() instanceof TCToolItem ? mainHandCheck : offHandCheck);
                        TCToolItem newToolItem = (TCToolItem) toolItem.getItem();

                        itemStackHandler.setToolBinding(toolItem, mainHandCheck.getItem() instanceof BindingItem ? mainHandCheck : offHandCheck);
                        ItemStack handleItemStack = itemStackHandler.getToolHandle(toolItem);
                        ItemStack headItemStack = itemStackHandler.getToolHead(toolItem);
                        ItemStack bindingItemStack = itemStackHandler.getToolBinding(toolItem);
                        HandleItem handleItem = (HandleItem) handleItemStack.getItem();
                        HeadItem headItem = (HeadItem) headItemStack.getItem();
                        BindingItem bindingItem = (BindingItem) bindingItemStack.getItem();

                        newToolItem.setBindingColor(toolItem, bindingItem.color);
                        newToolItem.setAttackDamage(toolItem, headItem.attackDamage);
                        newToolItem.setEfficiency(toolItem, headItem.efficiency);
                        newToolItem.setAttackSpeed(toolItem, handleItem.weight + headItem.weight + bindingItem.weight);
                        newToolItem.setToolLevel(toolItem, headItem.tools);

                        if(playerEntity.getMainHandItem().getItem() instanceof TCToolItem){
                            playerEntity.setItemInHand(InteractionHand.MAIN_HAND, toolItem);
                            playerEntity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
                        }else{
                            playerEntity.setItemInHand(InteractionHand.OFF_HAND, toolItem);
                            playerEntity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("ALL")
    public static boolean inventoryToolCheck(Player playerIn, List<String> toolNeededIn){
        List<Item> toolNeededList = convertToItem(toolNeededIn);

        boolean debug = true;

        for(int i = 0; i <= 35; i++){
            ItemStack toolNeeded = playerIn.getInventory().getItem(i);
            if(toolNeededList.contains(toolNeeded.getItem())){
                if(toolNeeded.getItem() instanceof TCToolItem){
                    TCToolItem toolNeededItem = (TCToolItem)playerIn.getInventory().getItem(i).getItem();
                    TCToolItemInventoryHelper itemStackHandler = toolNeededItem.getInventory(toolNeeded);

                    List<Boolean> test = new ArrayList<>();

                    if((toolNeededItem.getParts() >= 1) & itemStackHandler.getToolHead(toolNeeded) != null){
                        if(itemStackHandler.getToolHead(toolNeeded).getDamageValue() < itemStackHandler.getToolHead(toolNeeded).getMaxDamage()) {
                            test.add(true);
                        }
                    }

                    if((toolNeededItem.getParts() >= 2) & itemStackHandler.getToolHandle(toolNeeded) != null){
                        if(itemStackHandler.getToolHandle(toolNeeded).getDamageValue() < itemStackHandler.getToolHandle(toolNeeded).getDamageValue()){
                            test.add(true);
                        }
                    }

                    if(toolNeededItem.getParts() >= 3 & itemStackHandler.getToolBinding(toolNeeded) != null){
                        if(itemStackHandler.getToolBinding(toolNeeded).getDamageValue() < itemStackHandler.getToolBinding(toolNeeded).getDamageValue()){
                            test.add(true);
                        }
                    }

                    if(test.size() == toolNeededItem.getParts() || debug){
                        toolNeededList.remove(toolNeeded.getItem());
                    }
                }else{
                    if(toolNeeded.getDamageValue() < toolNeeded.getMaxDamage() || debug) {
                        toolNeededList.remove(toolNeeded.getItem());
                    }
                }
            }

            if(toolNeededList.isEmpty()){
                break;
            }
        }
        return toolNeededList.isEmpty();
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
