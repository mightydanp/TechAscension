package mightydanp.industrialtech.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.tool.part.HandleItem;

public class ITHandleItem extends HandleItem {
    public Pair<String, String> prefixAndSuffix;

    public ITHandleItem(Pair<String, String> prefixAndSuffix) {
        super(new Properties(), prefixAndSuffix);
        this.prefixAndSuffix = prefixAndSuffix;
    }
}
