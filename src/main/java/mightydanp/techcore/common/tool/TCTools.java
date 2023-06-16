package mightydanp.techcore.common.tool;

import net.minecraft.world.item.Item;
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
}