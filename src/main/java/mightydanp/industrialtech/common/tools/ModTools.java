package mightydanp.industrialtech.common.tools;

import mightydanp.industrialtech.api.common.handler.ToolHandler;
import mightydanp.industrialtech.api.common.items.*;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.DefaultToolType;
import mightydanp.industrialtech.api.common.material.tool.ITTools;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * Created by MightyDanp on 4/23/2021.
 */
public class ModTools extends ITTools {
    public static ToolHandler hammer, chisel, pickaxe, knife;

    public static void init(){
        tools.add(chisel = new ToolHandler("chisel", 1, DefaultToolType.NORMAL, new ChiselToolItem()));
        tools.add(hammer = new ToolHandler("hammer", 1, DefaultToolType.NORMAL, new HammerToolItem()));
        tools.add(pickaxe = new ToolHandler("pickaxe", 1, DefaultToolType.NORMAL, new PickaxeToolItem()));
        tools.add(knife = new ToolHandler("knife", 1, DefaultToolType.NORMAL, new KnifeToolItem()));
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
