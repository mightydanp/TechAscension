package mightydanp.techascension.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.items.TCCreativeModeTab;
import mightydanp.techcore.common.tool.part.DullHeadItem;
import net.minecraft.world.item.Item;

public class TADullHeadItem extends DullHeadItem {

    public TADullHeadItem() {
        super(new Item.Properties().tab(TCCreativeModeTab.tool_parts_tab));
    }
}