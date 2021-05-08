package mightydanp.industrialtech.common.tools;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.handler.MaterialHandler;
import mightydanp.industrialtech.api.common.handler.ToolHandler;
import mightydanp.industrialtech.api.common.items.*;
import mightydanp.industrialtech.api.common.libs.EnumToolFlags;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MightyDanp on 4/23/2021.
 */
public class ModTools {
    public static List<ToolHandler> tools = new ArrayList<>();
    public static ToolHandler hammer, chisel, pickaxe;

    public static void init(){
        tools.add(chisel = new ToolHandler("chisel", 1, EnumToolFlags.NORMAL, new ChiselToolItem()));
        tools.add(hammer = new ToolHandler("hammer", 1, EnumToolFlags.NORMAL, new HammerToolItem()));
        tools.add(pickaxe = new ToolHandler("pickaxe", 1, EnumToolFlags.NORMAL, new PickaxeToolItem()));
    }

    public static void handCraftingInit(PlayerInteractEvent.RightClickItem event){
        for(ToolHandler tool : tools) {
            ToolHandler.handToolCrafting((ITToolItem)tool.tool, event, tool.damageTool, tool.toolCraftingTools);
        }
    }

    public static void clientInit(){
        for(ToolHandler material : tools) {
            material.registerColorForItem();
        }
    }
}
