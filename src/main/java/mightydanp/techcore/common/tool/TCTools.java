package mightydanp.techcore.common.tool;

import mightydanp.techcore.common.handler.RegistryHandler;
import mightydanp.techcore.common.jsonconfig.tool.ITool;
import mightydanp.techcore.common.tool.part.PartHolders;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

/**
 * Created by MightyDanp on 11/23/2021.
 */
public class TCTools {
    public static Map<String, TCTool> tools = new HashMap<>();
    public static Map<String, RegistryObject<Item>>  toolItems = new HashMap<>();

    public static void init(){

    }

    public static Map<String, TCTool> getTools() {
        return tools;
    }

    public static ITool toITool(String name, Integer useDamage, List<String> effectiveOn, List<Ingredient> handleItems, List<Ingredient> headItems, List<Ingredient> bindingItems, Map<Integer, List<Map<Ingredient, Integer>>> assembleStepsItems, List<Map<Ingredient, Integer>> disassembleItems){
        return new ITool() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public Integer getUseDamage() {
                return useDamage;
            }

            @Override
            public List<String> getEffectiveOn() {
                return effectiveOn;
            }

            @Override
            public List<Ingredient> getHandleItems() {
                return handleItems;
            }

            @Override
            public List<Ingredient> getHeadItems() {
                return headItems;
            }

            @Override
            public List<Ingredient> getBindingItems() {
                return bindingItems;
            }

            @Override
            public Map<Integer, List<Map<Ingredient, Integer>>> getAssembleStepsItems() {
                return assembleStepsItems;
            }

            @Override
            public List<Map<Ingredient, Integer>> getDisassembleItems() {
                return disassembleItems;
            }
        };
    }
}