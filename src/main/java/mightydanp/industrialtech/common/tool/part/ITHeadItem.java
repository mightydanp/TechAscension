package mightydanp.industrialtech.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.tool.part.HeadItem;

public class ITHeadItem extends HeadItem {
    public Pair<String, String> prefixAndSuffix = new Pair<>("", "_head");

    public ITHeadItem() {
        super(new Properties(), new Pair<>("", "_head"));
    }
}