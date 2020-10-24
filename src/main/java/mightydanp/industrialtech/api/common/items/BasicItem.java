package mightydanp.industrialtech.api.common.items;

import net.minecraft.item.Item;

/**
 * Created by MightyDanp on 10/10/2020.
 */
public class BasicItem extends Item {
    public BasicItem(Properties properties) {
        super(properties);
        properties.group(ModItemGroups.item_tab);
    }
}
