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

    public static void addTool(String name, int hitDamage, Set<String> effectiveBlocks, Map<Integer, List<Map<Ingredient, Integer>>> assembleItems, List<Map<Ingredient, Integer>> disassembleItems, PartHolders.handlePartHolder handle, PartHolders.dullHeadPartHolder dullHead, PartHolders.headPartHolder head, PartHolders.bindingPartHolder binding, Class<? extends TCToolItem>  toolItem) {
        if(((ToolRegistry)TCJsonConfigs.tool.getFirst()).registryMap.containsKey(name)) {
            ITool tool = ((ToolRegistry)TCJsonConfigs.tool.getFirst()).registryMap.get(name);

            tools.put(name, new TCTool(name, tool.getUseDamage(), tool.getEffectiveOn(), tool.getAssembleStepsItems(), tool.getDisassembleItems(), handle, dullHead, head, binding, toolItems.put(name, RegistryHandler.ITEMS.register(name, () -> {
                try {
                    return toolItem.newInstance().setName(name).setEffectiveBlocks(effectiveBlocks).setAssembleItems(assembleItems).setDisassembleItems(disassembleItems);
                } catch (InstantiationException | IllegalAccessException e) {
                    return null;
                }
            }))));
        }else{
            ToolRegistry registry = ((ToolRegistry)TCJsonConfigs.tool.getFirst());

            registry.buildAndRegisterTool(new ITool() {
                @Override
                public String getName() {
                    return name;
                }

                @Override
                public Integer getUseDamage() {
                    return hitDamage;
                }

                @Override
                public List<String> getEffectiveOn() {
                    return effectiveBlocks.stream().toList();
                }

                @Override
                public Map<Integer, List<Map<Ingredient, Integer>>> getAssembleStepsItems() {
                    return assembleItems;
                }

                @Override
                public List<Map<Ingredient, Integer>> getDisassembleItems() {
                    return disassembleItems;
                }
            });
            tools.put(name, new TCTool(name, hitDamage, effectiveBlocks.stream().toList(), assembleItems, disassembleItems, handle, dullHead, head, binding, toolItems.put(name, RegistryHandler.ITEMS.register(name, () -> {
                try {
                    return toolItem.newInstance().setName(name).setEffectiveBlocks(effectiveBlocks).setAssembleItems(assembleItems).setDisassembleItems(disassembleItems);
                } catch (InstantiationException | IllegalAccessException e) {
                    return null;
                }
            }))));
        }
    }


}