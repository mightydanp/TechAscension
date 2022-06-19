package mightydanp.industrialtech.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.tool.part.HeadItem;

public class ITHeadItem extends HeadItem {
    public Pair<String, String> prefixAndSuffix;

    public ITHeadItem(Pair<String, String> prefixAndSuffix) {
        super(new Properties(), prefixAndSuffix);
        this.prefixAndSuffix = prefixAndSuffix;
    }
}