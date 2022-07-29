package mightydanp.techascension.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.tool.part.DullHeadItem;
import net.minecraft.world.item.Item;

public class ITDullHeadItem extends DullHeadItem {
    public Pair<String, String> prefixAndSuffix = new Pair<>("dull_", "_head");

    public ITDullHeadItem() {
        super(new Item.Properties(), new Pair<>("dull_", "_head"));
    }
}