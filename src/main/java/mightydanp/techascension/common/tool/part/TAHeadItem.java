package mightydanp.techascension.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.items.TCCreativeModeTab;
import mightydanp.techcore.common.tool.part.HeadItem;

public class TAHeadItem extends HeadItem {

    public TAHeadItem() {
        super(new Properties().tab(TCCreativeModeTab.tool_parts_tab));
    }
}