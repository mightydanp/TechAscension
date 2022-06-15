package mightydanp.industrialcore.common.tool;

import mightydanp.industrialcore.common.handler.itemstack.ITToolItemInventoryHelper;
import mightydanp.industrialcore.common.items.ITToolItem;
import mightydanp.industrialcore.common.items.ToolBindingItem;
import mightydanp.industrialcore.common.items.ToolHandleItem;
import mightydanp.industrialcore.common.libs.Ref;
import mightydanp.industrialcore.common.material.tool.ITTools;
import mightydanp.industrialcore.common.items.ToolHeadItem;
import mightydanp.industrialcore.common.resources.asset.AssetPackRegistry;
import mightydanp.industrialcore.common.resources.asset.data.ItemModelData;
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

import java.util.*;

/**
 * Created by MightyDanp on 3/8/2021.
 */

public class ITTool {

    public String toolName;
    public int hitDamage;

    public Set<String> effectiveBlocks;

    public Map<String, Integer> assembleItems;
    public Map<String, Integer> parts;
    public List<String> disassembleItems;

    public RegistryObject<Item> toolItem;


    public ITTool(String nameIn, int hitDamageIn, Set<String> effectiveBlocksIn, Map<String, Integer> assembleItemsIn, Map<String, Integer> partsIn, List<String> disassembleItemsIn, RegistryObject<Item> toolItemIn) {
        toolName = nameIn;
        hitDamage = hitDamageIn;

        effectiveBlocks = effectiveBlocksIn;

        assembleItems = assembleItemsIn;
        parts = partsIn;
        disassembleItems = disassembleItemsIn;
        toolItem = toolItemIn;
    }

    public void initResources(){
        ItemModelData data = new ItemModelData().setParent(new ResourceLocation("minecraft", "item/generated"));
        for(int i = 0; i < parts.size(); i++){
            if(i == 0){
                if(parts.size() == 1) {
                    data.setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + toolName));
                    data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + toolName + "_overlay"));
                }else{
                    data.setTexturesLocation("layer0", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + toolName + "_handle"));
                }
            }

            if(i == 1){
                if(parts.size() == 2) {
                    data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + toolName + "_head"));
                }else{
                    data.setTexturesLocation("layer1", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + toolName + "_binding"));
                }
            }

            if(i == 2){
                data.setTexturesLocation("layer2", new ResourceLocation(Ref.mod_id, "item/material_icons/none/" + toolName + "_head"));
            }
        }

        AssetPackRegistry.itemModelDataHashMap.put(toolName, data);
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
}
