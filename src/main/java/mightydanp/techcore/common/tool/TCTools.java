package mightydanp.techcore.common.tool;

import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.jsonconfig.TCJsonConfigs;
import mightydanp.techcore.common.jsonconfig.tool.ITool;
import mightydanp.techcore.common.jsonconfig.tool.ToolRegistry;
import mightydanp.techcore.common.tool.part.PartHolders;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

/**
 * Created by MightyDanp on 11/23/2021.
 */
public class TCTools {
    private static final Map<String, TCTool> tools = new HashMap<>();
    public static Map<String, RegistryObject<Item>>  toolItems = new HashMap<>();

    public static void init(){

    }

    public static Map<String, TCTool> getTools() {
        return tools;
    }

    public static TCTool addTool(String name, PartHolders.handlePartHolder handle, PartHolders.dullHeadPartHolder dullHead, PartHolders.headPartHolder head, PartHolders.bindingPartHolder binding, Class<? extends TCToolItem>  toolItem) {
        TCTool tool;
            ITool iTool = ((ToolRegistry)TCJsonConfigs.tool.getFirst()).registryMap.get(name);

            tools.put(name, tool = new TCTool(name, iTool.getUseDamage(), iTool.getEffectiveOn(), iTool.getAssembleStepsItems(), iTool.getDisassembleItems(), handle, dullHead, head, binding, toolItems.put(name, RegistryHandler.ITEMS.register(name, () -> {
                try {
                    return toolItem.newInstance().setName(name);
                } catch (InstantiationException | IllegalAccessException e) {
                    return null;
                }
            }))));

        return tool;
    }
}