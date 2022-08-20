package mightydanp.techascension.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.items.TCCreativeModeTab;
import mightydanp.techcore.common.tool.part.BindingItem;
import net.minecraft.world.item.Item;

public class TABindingItem extends BindingItem {

    public TABindingItem() {
        super(new Item.Properties().tab(TCCreativeModeTab.tool_parts_tab));
    }
}