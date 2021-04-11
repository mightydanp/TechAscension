package mightydanp.industrialtech.api.common.handler;

import mightydanp.industrialtech.api.client.models.tools.ToolModelLoader;
import mightydanp.industrialtech.api.common.inventory.container.ITToolItemContainer;
import mightydanp.industrialtech.api.common.items.*;
import mightydanp.industrialtech.api.common.items.handler.ITToolItemItemStackHandler;
import mightydanp.industrialtech.api.common.libs.ITToolType;
import mightydanp.industrialtech.api.common.libs.Ref;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.tools.Tool;
import java.awt.*;
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

    public ToolHandler(){
        tools.add(pickaxeItem = RegistryHandler.ITEMS.register("pickaxe" ,() -> pickaxe));
    }

    public static void registerColorForItem(){
        registerAToolItemColor(pickaxeItem);
    }

    public static void registerAToolItemColor(RegistryObject<Item> item){
        if(item != null) {
            Minecraft.getInstance().getItemColors().register((stack, tintIndex) -> {
                if (tintIndex == 0) {
                    return (((ITToolItem)stack.getItem()).getHeadColor(stack));
                }else if (tintIndex == 1) {
                    return ((ITToolItem)stack.getItem()).getBindingColor(stack);
                }else if (tintIndex == 2) {
                    return ((ITToolItem)stack.getItem()).getHandleColor(stack);
                }else return 0;
            }, item.get());
        }
    }

    private static int ColorToInt(int r, int g, int b) {
        int ret = 0;
        ret += r; ret = ret << 8; ret += g; ret = ret << 8; ret += b;
        return ret;
    }

    public static void clientInit(FMLClientSetupEvent event) {
        registerColorForItem();
        event.enqueueWork(() ->{
            ItemModelsProperties.register(pickaxeItem.get(), new ResourceLocation(Ref.mod_id, "head_texture_flag"), (stack, world, living) -> {
                Item item = stack.getItem();
                ItemStack itemStack = stack;
                if(item instanceof ITToolItem) {
                    ITToolItem toolItem = (ITToolItem)item;
                    ITToolItemItemStackHandler itToolItemItemStackHandler = toolItem.getItemStackHandler(itemStack);
                    ToolHeadItem toolHeadItem = itToolItemItemStackHandler.getToolHead();

                    if(toolHeadItem != null) {
                        return toolHeadItem.textureFlag.getID();
                    }else{
                        return 0;
                    }
                }else {
                    return 0;
                }
            });
        });
        event.enqueueWork(() ->{
            ItemModelsProperties.register(pickaxeItem.get(), new ResourceLocation(Ref.mod_id, "binding_texture_flag"), (stack, world, living) -> {
                Item item = stack.getItem();
                ItemStack itemStack = stack;
                if(item instanceof ITToolItem) {
                    ITToolItem toolItem = (ITToolItem)item;
                    ITToolItemItemStackHandler itToolItemItemStackHandler = toolItem.getItemStackHandler(itemStack);
                    ToolBindingItem toolBindingItem = itToolItemItemStackHandler.getToolBinding();

                    if(toolBindingItem != null) {
                        return toolBindingItem.textureFlag.getID();
                    }else{
                        return 0;
                    }
                }else {
                    return 0;
                }
            });
        });
        event.enqueueWork(() ->{
            ItemModelsProperties.register(pickaxeItem.get(), new ResourceLocation(Ref.mod_id, "handle_texture_flag"), (stack, world, living) -> {
                Item item = stack.getItem();
                ItemStack itemStack = stack;
                if(item instanceof ITToolItem) {
                    ITToolItem toolItem = (ITToolItem)item;
                    ITToolItemItemStackHandler itToolItemItemStackHandler = toolItem.getItemStackHandler(itemStack);
                    ToolHandleItem toolHandleItem = itToolItemItemStackHandler.getToolHandle();

                    if(toolHandleItem != null) {
                        return toolHandleItem.textureFlag.getID();
                    }else{
                        return 0;
                    }
                }else {
                    return 0;
                }
            });
        });

    }

    public static void commonInit() {}
}
