package mightydanp.industrialtech.api.common.handler;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.handler.itemstack.ITToolItemItemStackHandler;
import mightydanp.industrialtech.api.common.items.*;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.DefaultToolType;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 3/8/2021.
 */
@Mod.EventBusSubscriber(modid = Ref.mod_id)
public class ToolHandler {
    public static List<ToolHandler> registeredTools = new ArrayList<>();
    public List<DefaultToolType> flags = new ArrayList<>();

    public String toolName;

    public RegistryObject<Item> toolItem;
    public Item tool;

    public List<Pair<Item, Integer>> toolParts;
    public List<Item> toolDisassembleTools;
    public List<Pair<Item, Integer>> toolCraftingTools;
    public int damageTool;


    public ToolHandler(String toolNameIn, int damageToolIn, DefaultToolType flagIn, ITToolItem tool) {
        toolItem = RegistryHandler.ITEMS.register(toolNameIn, () -> tool);
        toolName = toolNameIn;
        toolCraftingTools = tool.craftingToolsNeeded;
        toolParts = tool.toolParts;
        toolDisassembleTools = tool.toolsNeededForDisassemble;
        damageTool = damageToolIn;
        this.tool = tool;
        this.flags.add(flagIn);
        this.addFlag(flagIn);
        registeredTools.add(this);
    }

    protected void addFlag(DefaultToolType... flagsIn) {

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
        //handToolCrafting(pickaxe, event, 1, pickaxeToolsNeeded);
    }

    public static void handToolCrafting(ITToolItem toolItemIn, PlayerInteractEvent.RightClickItem event, int toolNeededDamage, List<Pair<Item, Integer>> toolNeededIn) {
        PlayerEntity playerEntity = event.getPlayer();
        ItemStack mainHand = playerEntity.getMainHandItem();
        ItemStack offHand = playerEntity.getOffhandItem();
        ItemStack toolItem = new ItemStack(toolItemIn.getItem());
        ITToolItemItemStackHandler itemStackHandler = toolItemIn.getItemStackHandler(toolItem);
        List<Item> firstItemsNeeded = compareAndAddToNewArray(1, toolNeededIn);
        List<Item> firstItemsThatCanBeUsed = compareAndAddToNewArray(-1, toolNeededIn);
        List<Item> secondItemsNeeded = compareAndAddToNewArray(2, toolNeededIn);
        List<Item> secondItemsThatCanBeUsed = compareAndAddToNewArray(-2, toolNeededIn);


        if (toolItemIn.getPartsToWork() == 3 || toolItemIn.getPartsToWork() == 2) {
            ItemStack mainHandCheck = playerEntity.getMainHandItem().getItem() == toolItemIn.toolParts.get(0).getFirst() ? playerEntity.getMainHandItem() : (playerEntity.getMainHandItem().getItem() == toolItemIn.toolParts.get(1).getFirst() ? playerEntity.getMainHandItem() : null);
            ItemStack offHandCheck = playerEntity.getOffhandItem().getItem() == toolItemIn.toolParts.get(0).getFirst() ? playerEntity.getOffhandItem() : (playerEntity.getOffhandItem().getItem() == toolItemIn.toolParts.get(1).getFirst() ? playerEntity.getOffhandItem() : null);
            if (mainHandCheck != null && offHandCheck != null) {
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
                    toolItemIn.setHarvestLevel(toolItem, toolHeadItem.getItToolType());

                    if (toolItemIn.getPartsToWork() == 2) {
                        toolItemIn.setAttackSpeed(toolItem, toolHandleItem.weight + toolHeadItem.weight);
                    }

                    playerEntity.setItemInHand(Hand.MAIN_HAND, toolItem);
                    playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof ITToolItem ? Hand.OFF_HAND : Hand.MAIN_HAND, ItemStack.EMPTY);
                }
            }
        }

        if (toolItemIn.getPartsToWork() == 3){
            ItemStack mainHandNew = playerEntity.getMainHandItem();
            ItemStack offHandNew = playerEntity.getOffhandItem();
            ItemStack mainHandCheck = mainHandNew.getItem() == toolItemIn.toolParts.get(2).getFirst() ? mainHandNew : (mainHandNew.getItem() instanceof ITToolItem ? playerEntity.getMainHandItem() : null);
            ItemStack offHandCheck = offHandNew.getItem() == toolItemIn.toolParts.get(2).getFirst() ? offHandNew : (offHandNew.getItem() instanceof ITToolItem ? playerEntity.getOffhandItem() : null);

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
                    ITToolItemItemStackHandler itemStackHandlerNew = newToolItem.getItemStackHandler(mainHandCheck.getItem() instanceof ITToolItem ? mainHandCheck : offHandCheck);
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
                    newToolItem.setHarvestLevel(newToolItemStack, headItem.getItToolType());

                    playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof ITToolItem ? Hand.MAIN_HAND : Hand.OFF_HAND, newToolItemStack);
                    playerEntity.setItemInHand(playerEntity.getMainHandItem().getItem() instanceof ITToolItem ? Hand.OFF_HAND : Hand.MAIN_HAND, ItemStack.EMPTY);
                }
            }
        }
    }

    public static boolean inventoryToolCheck(PlayerEntity playerIn, List<Item> toolNeededIn){
        for(int i = 9; i <= 45; i++){
            ItemStack toolNeeded = playerIn.inventory.getItem(i);
            if(toolNeededIn.contains(toolNeeded.getItem())){
                if(toolNeeded.getItem() instanceof ITToolItem){
                    ITToolItem toolNeededItem = (ITToolItem)playerIn.inventory.getItem(i).getItem();
                    ITToolItemItemStackHandler itemStackHandler = toolNeededItem.getItemStackHandler(toolNeeded);

                    if((toolNeededItem.partsToWork == 1 || toolNeededItem.partsToWork == 2 || toolNeededItem.partsToWork == 3) & itemStackHandler.getToolHead() != null){
                        if(itemStackHandler.getToolHead().getDamageValue() < itemStackHandler.getToolHead().getMaxDamage()) {
                            toolNeededIn.remove(toolNeeded.getItem());
                        }
                    }

                    if((toolNeededItem.partsToWork == 2 || toolNeededItem.partsToWork == 3) & itemStackHandler.getToolHandle() != null){
                        if(itemStackHandler.getToolHandle().getDamageValue() < itemStackHandler.getToolHandle().getDamageValue()){
                            toolNeededIn.remove(toolNeeded.getItem());
                        }
                    }

                    if(toolNeededItem.partsToWork == 3 & itemStackHandler.getToolBinding() != null){
                        if(itemStackHandler.getToolBinding().getDamageValue() < itemStackHandler.getToolBinding().getDamageValue()){
                            toolNeededIn.remove(toolNeeded.getItem());
                        }
                    }
                }else{
                    if(toolNeeded.getDamageValue() < toolNeeded.getMaxDamage()) {
                        toolNeededIn.remove(toolNeeded.getItem());
                    }
                }
            }
        }
        return toolNeededIn.size() == 0;
    }

    public static List<Item> compareAndAddToNewArray(int numberIn, List<Pair<Item, Integer>> pairArrayIn){
        List<Item> newArray = new ArrayList<>();

        for(Pair<Item, Integer> pair : pairArrayIn){
            if(pair.getSecond() == numberIn) {
                newArray.add(pair.getFirst());
            }
        }

        return newArray;
    }
}
