package mightydanp.industrialtech.common.tool;

import mightydanp.industrialtech.api.common.tool.ITTool;
import mightydanp.industrialtech.api.common.items.*;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.DefaultToolType;
import mightydanp.industrialtech.api.common.material.tool.ITTools;
import mightydanp.industrialtech.common.tool.tools.ChiselToolItem;
import mightydanp.industrialtech.common.tool.tools.HammerToolItem;
import mightydanp.industrialtech.common.tool.tools.KnifeToolItem;
import mightydanp.industrialtech.common.tool.tools.PickaxeToolItem;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * Created by MightyDanp on 4/23/2021.
 */
public class ModTools extends ITTools {
    public static ITTool hammer, chisel, pickaxe, knife;

    public static void init(){
        tools.add(chisel = new ITTool("chisel", 1, DefaultToolType.NORMAL, new ChiselToolItem()));
        tools.add(hammer = new ITTool("hammer", 1, DefaultToolType.NORMAL, new HammerToolItem()));
        tools.add(pickaxe = new ITTool("pickaxe", 1, DefaultToolType.NORMAL, new PickaxeToolItem()));
        tools.add(knife = new ITTool("knife", 1, DefaultToolType.NORMAL, new KnifeToolItem()));
    }

    public static void handCraftingInit(PlayerInteractEvent.RightClickItem event){
        for(ITTool tool : tools) {
            ITTool.handToolCrafting((ITToolItem)tool.tool, event, tool.hitDamage, tool.toolCraftingTools);
        }
    }

    public static void clientInit(){
        for(ITTool material : tools) {
            material.registerColorForItem();
        }
    }
}
