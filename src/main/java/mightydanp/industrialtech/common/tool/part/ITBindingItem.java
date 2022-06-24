package mightydanp.industrialtech.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.industrialcore.common.tool.part.BindingItem;
import net.minecraft.world.item.Item;

public class ITBindingItem extends BindingItem {
    public Pair<String, String> prefixAndSuffix = new Pair<>("", "_binding");

    public ITBindingItem() {
        super(new Item.Properties(), new Pair<>("", "_binding"));
    }
}