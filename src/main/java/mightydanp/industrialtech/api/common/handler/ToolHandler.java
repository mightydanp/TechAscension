package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.common.items.ITToolItem;
import mightydanp.industrialtech.api.common.items.ModItemGroups;
import mightydanp.industrialtech.api.common.items.PickaxeToolItem;
import mightydanp.industrialtech.api.common.libs.ITToolType;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import javax.tools.Tool;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 3/8/2021.
 */
public class ToolHandler {

    public static List<RegistryObject<Item>> tools = new ArrayList<>();

    public static RegistryObject<Item> pickaxeItem;
    public static ITToolItem pickaxe = new PickaxeToolItem(new Item.Properties().tab(ModItemGroups.tool_tab));

    private int redHeadLayer, greenHeadLayer, blueHeadLayer, alphaHeadLayer;
    private int redBindingLayer, greenBindingLayer, blueBindingLayer, alphaBindingLayer;
    private int redHandleLayer, greenHandleLayer, blueHandleLayer, alphaHandleLayer;

    private float headDamage, bindingDamage, handleDamage;
    private int headDurability, bindingDurability, handleDurability;

    private int miningSpeed;

    public ToolHandler(){
        tools.add(pickaxeItem = RegistryHandler.ITEMS.register("pickaxe" ,() -> {
            pickaxe.setHarvestLevel(ToolType.PICKAXE, 0);
            return pickaxe;
        }));
    }

    public static void registerColorForItem(){
        registerAToolItemColor(pickaxeItem, ((ITToolItem)pickaxeItem.get()).getHeadColor(null), ((ITToolItem)pickaxeItem.get()).getBindingColor(null), ((ITToolItem)pickaxeItem.get()).getHandleColor(null));
    }

    public static void registerAToolItemColor(RegistryObject<Item> item, int headColorIn, int bindingColorIn, int handleColorIn){
        if(item != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0) {
                    return headColorIn;
                }else if (tintIndex == 1) {
                    return bindingColorIn;
                }else if (tintIndex == 2) {
                    return handleColorIn;
                }else return 0xFFFFFFFF;
            }, item.get());
        }
    }

    private static int ColorToInt(int r, int g, int b) {
        int ret = 0;
        ret += r; ret = ret << 8; ret += g; ret = ret << 8; ret += b;
        return ret;
    }

    public static void clientInit() {
            registerColorForItem();

    }
}
