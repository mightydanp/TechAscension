package mightydanp.industrialtech.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.tool.part.HandleItem;

public class ITHandleItem extends HandleItem {
    public Pair<String, String> prefixAndSuffix = new Pair<>("", "_handle");

    public ITHandleItem() {
        super(new Properties(), new Pair<>("", "_handle"));
    }
}
