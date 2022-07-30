package mightydanp.techascension.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.tool.part.HandleItem;

public class TAHandleItem extends HandleItem {
    public Pair<String, String> prefixAndSuffix = new Pair<>("", "_handle");

    public TAHandleItem() {
        super(new Properties(), new Pair<>("", "_handle"));
    }
}
