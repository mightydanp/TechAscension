package mightydanp.techascension.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.tool.part.HeadItem;

public class TAHeadItem extends HeadItem {
    public Pair<String, String> prefixAndSuffix = new Pair<>("", "_head");

    public TAHeadItem() {
        super(new Properties(), new Pair<>("", "_head"));
    }
}