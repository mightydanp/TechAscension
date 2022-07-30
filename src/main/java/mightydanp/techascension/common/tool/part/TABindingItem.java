package mightydanp.techascension.common.tool.part;

import com.mojang.datafixers.util.Pair;
import mightydanp.techcore.common.tool.part.BindingItem;
import net.minecraft.world.item.Item;

public class TABindingItem extends BindingItem {
    public Pair<String, String> prefixAndSuffix = new Pair<>("", "_binding");

    public TABindingItem() {
        super(new Item.Properties(), new Pair<>("", "_binding"));
    }
}