package mightydanp.industrialtech.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.tool.part.DullHeadItem;
import net.minecraft.world.item.Item;

public class ITDullHeadItem extends DullHeadItem {
    public Pair<String, String> prefixAndSuffix;

    public ITDullHeadItem(Pair<String, String> prefixAndSuffix) {
        super(new Item.Properties(), prefixAndSuffix);
        this.prefixAndSuffix = prefixAndSuffix;
    }
}