package mightydanp.industrialtech.common.tool;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.IToolType;
import mightydanp.industrialtech.api.common.jsonconfig.tool.type.ToolTypeRegistry;
import mightydanp.industrialtech.api.common.tool.ITTool;
import mightydanp.industrialtech.api.common.items.*;
import mightydanp.industrialtech.api.common.material.tool.ITTools;
import mightydanp.industrialtech.common.IndustrialTech;
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
        IToolType normal = ((ToolTypeRegistry) IndustrialTech.configSync.toolType.getFirst()).getToolTypeByFixes(new Pair<>("", "_tool"));

        tools.add(chisel = new ITTool("chisel", 1, normal, ChiselToolItem::new));
        tools.add(hammer = new ITTool("hammer", 1, normal, HammerToolItem::new));
        tools.add(pickaxe = new ITTool("pickaxe", 1, normal, PickaxeToolItem::new));
        tools.add(knife = new ITTool("knife", 1, normal, KnifeToolItem::new));
    }

    public static void handCraftingInit(PlayerInteractEvent.RightClickItem event){
        for(ITTool tool : tools) {
            ITTool.handToolCrafting((ITToolItem)tool.toolItem.get(), event, tool.hitDamage, ((ITToolItem)tool.toolItem.get()).toolsNeeded);
        }
    }

    public static void clientInit(){
        for(ITTool material : tools) {
            material.registerColorForItem();
        }
    }
}
